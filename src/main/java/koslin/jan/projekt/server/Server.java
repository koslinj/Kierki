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

/**
 * The {@code Server} class represents the main server for the card game application.
 * It manages everything in this client-server application.
 * It handles communication with clients, rules, in-game actions and room information.
 */
public class Server {
    /**
     * The number of players in room needed to start the game.
     */
    public static int NUMBER_OF_PLAYERS = ConfigReader.getNumberOfPlayers();
    /**
     * Rules for each round of the game.
     */
    public static Map<Integer, Rule> rulesForRounds = ConfigReader.getRules();
    private ServerSocket serverSocket;
    static HashMap<Integer, ObjectOutputStream> allOutputStreams = new HashMap<>();

    /**
     * Starts the server on the specified port and initializes the game.
     *
     * @param port          The port number to bind the server to.
     * @param websitePort   The port number for the embedded website.
     * @param roomManager   The room manager managing game rooms.
     * @throws IOException  If an I/O error occurs while starting the server.
     */
    public void start(int port, int websitePort, RoomManager roomManager) throws IOException {
        Website website = new Website(websitePort, roomManager);
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

    /**
     * The main method to start the server.
     *
     * @param args  Command-line arguments (not used).
     * @throws IOException  If an I/O error occurs while starting the server.
     */
    public static void main(String[] args) throws IOException {
        RoomManager roomManager = new RoomManager();
        try {
            Server server = new Server();
            int port = ConfigReader.getPort();
            int websitePort = ConfigReader.getWebsitePort();
            String ip = ConfigReader.getIp();
            System.out.println("Server starting on port: " + port +" and IP: " + ip);
            server.start(port, websitePort, roomManager);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
