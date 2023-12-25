package koslin.jan.projekt.server;

import koslin.jan.projekt.DataType;
import koslin.jan.projekt.Message;
import koslin.jan.projekt.Room;
import koslin.jan.projekt.RoomManager;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class Server {
    private ServerSocket serverSocket;
    private HashMap<Integer, Player> allPlayers = new HashMap<>();

    public void start(int port, RoomManager roomManager) throws IOException {
        serverSocket = new ServerSocket(port);
        int clientId = 0;
        while (true) {
            Socket client = serverSocket.accept();
            Player player = new Player(client, clientId);
            allPlayers.put(clientId, player);

            sendStartingState(roomManager, player.getOutputStream());

            new ClientHandler(player, roomManager, allPlayers).start();
            clientId++;
        }
    }

    private void sendStartingState(RoomManager roomManager, ObjectOutputStream out) throws IOException {
        System.out.println(roomManager.getRooms().size());
        for (Room room : roomManager.getRooms().values()) {
            Message message = new Message.Builder(DataType.ROOM)
                    .roomName(room.getRoomName())
                    .roomId(room.getRoomId())
                    .amountOfPlayers(room.getAmountOfPlayers())
                    .build();
            out.writeObject(message);
            out.flush();
        }
    }


    public static void main(String[] args) {
        RoomManager roomManager = new RoomManager();
        try {
            Server server = new Server();
            server.start(6000, roomManager);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
