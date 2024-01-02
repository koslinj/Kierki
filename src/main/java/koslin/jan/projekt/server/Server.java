package koslin.jan.projekt.server;

import koslin.jan.projekt.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

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
            int port = ConfigReader.getPort();
            String ip = ConfigReader.getIp();
            System.out.println("Server starting on port: " + port +" and IP: " + ip);
            server.start(port, roomManager);
        } catch (IOException | ParserConfigurationException | SAXException e) {
            e.printStackTrace();
        }
    }
}
