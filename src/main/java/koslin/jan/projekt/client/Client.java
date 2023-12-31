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

    public void startReceiver() {
        Receiver receiver = new Receiver(this, server);
        pool.execute(receiver);
    }

    public void login(String username, String password) {
        Message message = new Message.Builder(DataType.LOGIN)
                .username(username)
                .password(password)
                .build();
        sendMessage(message);
    }

    public void handleLoginResponse(Message message) {
        if (message.isSuccess()) {
            roomsController.changeScene(message.getUsername());
        } else {
            loginController.setErrorLabel("NIE UDAŁO SIĘ ZALOGOWAĆ");
        }
    }

    public void leaveRoom() {
        roomId = -1;
        Message message = new Message.Builder(DataType.LEAVE_ROOM)
                .build();
        sendMessage(message);
    }

    public void register(String username, String password) {
        Message message = new Message.Builder(DataType.REGISTER)
                .username(username)
                .password(password)
                .build();
        sendMessage(message);
    }

    public void addRoom(String roomName) {
        Message message = new Message.Builder(DataType.ROOM)
                .roomName(roomName)
                .build();
        sendMessage(message);
    }

    public void joinRoom(int roomId) {
        Message message = new Message.Builder(DataType.ROOM)
                .roomId(roomId)
                .join(true)
                .build();
        sendMessage(message);
    }

    public void chooseCard(String card) {
        Message message = new Message.Builder(DataType.GAME)
                .card(card)
                .build();
        sendMessage(message);
    }

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

    private void sendMessage(Message message) {
        try {
            out.writeObject(message);
            out.flush();
        } catch (IOException e) {
            System.out.println("FAILED TO SEND MESSAGE");
            e.printStackTrace();
        }
    }

}