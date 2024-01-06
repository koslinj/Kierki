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
    public static int NUMBER_OF_PLAYERS = ConfigReader.getNumberOfPlayers();
    public static Map<Integer, Rule> rulesForRounds = ConfigReader.getRules();
    private ServerSocket serverSocket;
    private HashMap<Integer, ObjectOutputStream> allOutputStreams = new HashMap<>();

    public void start(int port, RoomManager roomManager) throws IOException {
        Website website = new Website(roomManager);
        website.startWebsite();

        serverSocket = new ServerSocket(port);
        int clientId = 0;
        while (true) {
            Socket client = serverSocket.accept();
            ObjectOutputStream outputStream = new ObjectOutputStream(client.getOutputStream());
            Player player = new Player(clientId);

            allOutputStreams.put(clientId, outputStream);

            new ClientHandler(player, client, outputStream, roomManager, allOutputStreams).start();
            clientId++;
        }
    }


    public static void main(String[] args) throws IOException {
        RoomManager roomManager = new RoomManager();
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
