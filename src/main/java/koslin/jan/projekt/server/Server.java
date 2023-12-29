package koslin.jan.projekt.server;

import koslin.jan.projekt.Deck;
import koslin.jan.projekt.Room;
import koslin.jan.projekt.RoomManager;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class Server {
    public static final int NUMBER_OF_PLAYERS = 4;
    private ServerSocket serverSocket;
    private HashMap<Integer, ObjectOutputStream> allOutputStreams = new HashMap<>();

    public void start(int port, RoomManager roomManager) throws IOException {
        serverSocket = new ServerSocket(port);
        int clientId = 0;
        while (true) {
            Socket client = serverSocket.accept();
            ObjectOutputStream outputStream = new ObjectOutputStream(client.getOutputStream());
            Player player = new Player(clientId);
            // TYMCZASOWO ŻEBY SZYBCIEJ SIE LOGOWAC ->>>>>>>
            player.setRoomId(1);
            Room room = roomManager.getRooms().get(1);
            room.addPlayer(player);

            allOutputStreams.put(clientId, outputStream);

            new ClientHandler(player, client, outputStream, roomManager, allOutputStreams).start();
            clientId++;
        }
    }


    public static void main(String[] args) throws IOException {
        RoomManager roomManager = new RoomManager();
        Room room = new Room("POKOJ", 1);
        roomManager.getRooms().put(room.getRoomId(), room);
        try {
            Server server = new Server();
            server.start(6000, roomManager);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
