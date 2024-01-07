package koslin.jan.projekt.client;

import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import koslin.jan.projekt.DataType;
import koslin.jan.projekt.Message;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * The Client class represents the client-side of the multiplayer card game application.
 * It manages the communication with the server, handles user actions, and updates the GUI.
 * The class utilizes multiple controllers (LoginController, RoomsController, GameController)
 * for managing different views and user interactions.
 *
 * <p>The Client class includes methods to perform actions such as login, registration,
 * creating and joining rooms, choosing cards, leaving rooms, and quitting the application.
 * Additionally, it has a message receiver thread to handle incoming messages from the server.</p>
 *
 */
public class Client {
    private String playerName;
    private int playerId;
    private int roomId;
    private Socket server;
    private ObjectOutputStream out;
    private final ExecutorService pool = Executors.newFixedThreadPool(1);
    Stage primaryStage;
    LoginController loginController;
    RoomsController roomsController;
    GameController gameController;

    /**
     * Constructs a Client object for the multiplayer card game application.
     *
     * @param server          The Socket representing the connection to the server.
     * @param primaryStage    The primary stage for the JavaFX application.
     * @param loginController The controller for the login view.
     * @param roomsController The controller for the rooms view.
     * @param gameController  The controller for the game view.
     * @throws IOException If an error occurs during the creation of the Client object.
     */
    public Client(Socket server, Stage primaryStage, LoginController loginController, RoomsController roomsController, GameController gameController) throws IOException {
        try {
            this.playerId = -1;
            this.roomId = -1;
            this.server = server;
            this.out = new ObjectOutputStream(server.getOutputStream());
            this.primaryStage = primaryStage;
            this.loginController = loginController;
            this.roomsController = roomsController;
            this.gameController = gameController;
        } catch (IOException e) {
            System.out.println("Error creating Client");
            e.printStackTrace();
        }
    }

    public int getPlayerId() {
        return playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    /**
     * Constructs {@link Receiver} instance for this particular client.
     * Starts the message receiver thread for handling incoming messages from the server.
     */
    public void startReceiver() {
        Receiver receiver = new Receiver(this, server);
        pool.execute(receiver);
    }

    /**
     * Initiates the login process with the provided username and password.
     *
     * @param username The username for login.
     * @param password The password for login.
     */
    public void login(String username, String password) {
        Message message = new Message.Builder(DataType.LOGIN)
                .username(username)
                .password(password)
                .build();
        sendMessage(message);
    }

    /**
     * Handles the login response received from the server.
     *
     * @param message The Message object containing the login response.
     */
    public void handleLoginResponse(Message message) {
        if (message.isSuccess()) {
            playerName = message.getUsername();
            roomsController.changeScene(message.getUsername());
        } else {
            loginController.setErrorLabel("NIE UDAŁO SIĘ ZALOGOWAĆ");
        }
    }

    /**
     * Leaves the current room and notifies the server.
     */
    public void leaveRoom() {
        roomId = -1;
        Message message = new Message.Builder(DataType.LEAVE_ROOM)
                .build();
        sendMessage(message);
    }

    /**
     * Initiates the user registration process with the provided username and password.
     *
     * @param username The username for registration.
     * @param password The password for registration.
     */
    public void register(String username, String password) {
        Message message = new Message.Builder(DataType.REGISTER)
                .username(username)
                .password(password)
                .build();
        sendMessage(message);
    }

    /**
     * Creates a new room with the provided room name and notifies the server.
     *
     * @param roomName The name of the room to create.
     */
    public void addRoom(String roomName) {
        Message message = new Message.Builder(DataType.ROOM)
                .roomName(roomName)
                .build();
        sendMessage(message);
    }

    /**
     * Joins the specified room and notifies the server.
     *
     * @param roomId The ID of the room to join.
     */
    public void joinRoom(int roomId) {
        Message message = new Message.Builder(DataType.ROOM)
                .roomId(roomId)
                .join(true)
                .build();
        sendMessage(message);
    }

    /**
     * Chooses a card to put on table during the game and notifies the server.
     *
     * @param card The chosen card.
     */
    public void chooseCard(String card) {
        Message message = new Message.Builder(DataType.GAME)
                .card(card)
                .build();
        sendMessage(message);
    }

    /**
     * Initiates the quit process by sending a quit signal to the server,
     * shutting down the thread pool, and terminating the application.
     */
    public void quit() {
        //Message message = new Message(true, false);
        Message message = new Message.Builder(DataType.QUIT).build();
        try {
            out.writeObject(message);
            out.flush();
            pool.shutdown();
            pool.awaitTermination(5, TimeUnit.SECONDS);
            System.out.println("QUIT ENDED");
        } catch (IOException | InterruptedException e) {
            System.out.println("FAILED TO QUIT");
            e.printStackTrace();
        }
    }

    /**
     * General function sending a message to the server.
     * Made as helper function to abstract details of socket messaging.
     *
     * @param message The Message object to send.
     */
    private void sendMessage(Message message) {
        try {
            out.writeObject(message);
            out.flush();
        } catch (IOException e) {
            System.out.println("FAILED TO SEND MESSAGE");
            e.printStackTrace();
        }
    }

    /**
     * Handles the room response received from the server.
     *
     * @param message The Message object containing the room response.
     */
    public void handleRoomResponse(Message message) {
        if (!message.isSuccess()){
            roomsController.setInfoLabelText("Nie możesz dolączyć do pełnego pokoju");
        }
    }
}