package koslin.jan.projekt.server;

import koslin.jan.projekt.DataType;
import koslin.jan.projekt.Message;
import koslin.jan.projekt.Room;
import koslin.jan.projekt.RoomManager;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;

public class ClientHandler extends Thread {
    private Player player;
    private ObjectInputStream in;
    private RoomManager roomManager;
    private HashMap<Integer, Player> allPlayers;

    public ClientHandler(Player player, RoomManager roomManager, HashMap<Integer, Player> players) {
        this.player = player;
        this.roomManager = roomManager;
        this.allPlayers = players;
    }

    public void run() {
        try {
            in = new ObjectInputStream(player.getClientSocket().getInputStream());

            while (player.getClientSocket().isConnected()) {
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
            player.getClientSocket().close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void handleQuitMessage(Message message) throws IOException {
        player.getOutputStream().writeObject(message);
        player.getOutputStream().flush();
        allPlayers.remove(player.getPlayerId());
    }

    private void handleLoginMessage(Message message) throws IOException {
        if(message.getUsername().equals(player.getUsername()) && message.getPassword().equals(player.getPassword())){
            Message res = new Message.Builder(DataType.LOGIN)
                    .success(true)
                    .username(player.getUsername())
                    .build();
            player.getOutputStream().writeObject(res);
            player.getOutputStream().flush();
        } else {
            Message res = new Message.Builder(DataType.LOGIN)
                    .success(false)
                    .build();
            player.getOutputStream().writeObject(res);
            player.getOutputStream().flush();
        }
    }

    private void handleRegisterMessage(Message message){
        player.setUsername(message.getUsername());
        player.setPassword(message.getPassword());
    }

    private void handleRoomMessage(Message message) throws IOException {
        if (message.isJoin()) {
            //player.setUsername(message.getUsername());
            player.setRoomId(message.getRoomId());
            Room room = roomManager.getRooms().get(message.getRoomId());
            room.addPlayer(player);

        } else {
            roomManager.addRoom(message);
        }

        RoomManager res = (RoomManager) roomManager.clone();
        for(Player p : allPlayers.values()){
            p.getOutputStream().writeObject(res);
            System.out.println("ID pokoju -> " + message.getRoomId());
            System.out.println("Pokój gracza " + p.getPlayerId() + " -> " + p.getRoomId());
            p.getOutputStream().flush();
        }
    }
}