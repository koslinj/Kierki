package koslin.jan.projekt.server;

import koslin.jan.projekt.Deck;
import koslin.jan.projekt.Room;
import koslin.jan.projekt.RoomManager;
import koslin.jan.projekt.Rule;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Server {
    public static final int NUMBER_OF_PLAYERS = 4;
    public static Map<Integer, Rule> rulesForRounds = Map.of(
            1, new Rule(-20, new ArrayList<>()),
            2, new Rule(-20, "hearts", new ArrayList<>()),
            3, new Rule(-60, "", List.of("queen")),
            4, new Rule(-30, "", Arrays.asList("jack", "king")),
            5, new Rule(-150, "hearts", List.of("king")),
            6, new Rule(-75, Arrays.asList(7, 13))
    );
    private ServerSocket serverSocket;
    private HashMap<Integer, ObjectOutputStream> allOutputStreams = new HashMap<>();

    public void start(int port, RoomManager roomManager) throws IOException {
        serverSocket = new ServerSocket(port);
        int clientId = 0;
        while (true) {
            Socket client = serverSocket.accept();
            ObjectOutputStream outputStream = new ObjectOutputStream(client.getOutputStream());
            Player player = new Player(clientId);
//            // TYMCZASOWO ŻEBY SZYBCIEJ SIE LOGOWAC ->>>>>>>
//            player.setRoomId(1);
//            Room room = roomManager.getRooms().get(1);
//            room.addPlayer(player);

            allOutputStreams.put(clientId, outputStream);

            new ClientHandler(player, client, outputStream, roomManager, allOutputStreams).start();
            clientId++;
        }
    }


    public static void main(String[] args) throws IOException {
        RoomManager roomManager = new RoomManager();
//        Room room = new Room("POKOJ", 1);
//        roomManager.getRooms().put(room.getRoomId(), room);
        try {
            Server server = new Server();
            server.start(6000, roomManager);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
