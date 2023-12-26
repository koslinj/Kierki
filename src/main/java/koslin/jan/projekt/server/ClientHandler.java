package koslin.jan.projekt.server;

import koslin.jan.projekt.DataType;
import koslin.jan.projekt.Message;
import koslin.jan.projekt.Room;
import koslin.jan.projekt.RoomManager;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;

public class ClientHandler extends Thread {
    private Player player;
    private final Socket clientSocket;
    private final ObjectOutputStream outputStream;
    private ObjectInputStream in;
    private RoomManager roomManager;
    private HashMap<Integer, ObjectOutputStream> allOutputStreams;

    public ClientHandler(Player player, Socket clientSocket, ObjectOutputStream outputStream, RoomManager roomManager, HashMap<Integer, ObjectOutputStream> allOutputStreams) throws IOException {
        this.player = player;
        this.clientSocket = clientSocket;
        this.outputStream = outputStream;
        this.roomManager = roomManager;
        this.allOutputStreams = allOutputStreams;
    }

    public void run() {
        try {
            sendInitialMessage();

            in = new ObjectInputStream(clientSocket.getInputStream());

            while (clientSocket.isConnected()) {
                Message message = (Message) in.readObject();
                if(message.getType() == DataType.QUIT){
                    handleQuitMessage(message);
                    break;
                } else if (message.getType() == DataType.LOGIN) {
                    handleLoginMessage(message);
                } else if (message.getType() == DataType.REGISTER) {
                    handleRegisterMessage(message);
                } else if (message.getType() == DataType.ROOM){
                    handleRoomMessage(message);
                }
            }

            in.close();
            clientSocket.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void sendInitialMessage() throws IOException {
        Message message = new Message.Builder(DataType.REGISTER)
                .playerId(player.getPlayerId())
                .build();
        outputStream.writeObject(message);
        outputStream.flush();
    }

    private void handleQuitMessage(Message message) throws IOException {
        outputStream.writeObject(message);
        outputStream.flush();
        allOutputStreams.remove(player.getPlayerId());
    }

    private void handleLoginMessage(Message message) throws IOException {
        if(message.getUsername().equals(player.getUsername()) && message.getPassword().equals(player.getPassword())){
            Message res = new Message.Builder(DataType.LOGIN)
                    .success(true)
                    .username(player.getUsername())
                    .build();
            outputStream.writeObject(res);
            outputStream.flush();
        } else {
            Message res = new Message.Builder(DataType.LOGIN)
                    .success(false)
                    .build();
            outputStream.writeObject(res);
            outputStream.flush();
        }
    }

    private void handleRegisterMessage(Message message){
        player.setUsername(message.getUsername());
        player.setPassword(message.getPassword());
    }

    private void handleRoomMessage(Message message) throws IOException {
        if (message.isJoin()) {
            player.setRoomId(message.getRoomId());
            Room room = roomManager.getRooms().get(message.getRoomId());
            room.addPlayer(player);
        } else {
            roomManager.addRoom(message);
        }

        RoomManager res = (RoomManager) roomManager.clone();
        for(ObjectOutputStream os : allOutputStreams.values()){
            os.writeObject(res);
            os.flush();
        }
    }
}