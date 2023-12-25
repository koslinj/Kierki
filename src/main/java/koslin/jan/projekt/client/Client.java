package koslin.jan.projekt.client;

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
    private Socket server;
    private ObjectOutputStream out;
    private final ExecutorService pool = Executors.newFixedThreadPool(1);
    Stage primaryStage;
    LoginController loginController;
    RoomsController roomsController;
    GameController gameController;

    public Client(Socket server, Stage primaryStage, LoginController loginController, RoomsController roomsController, GameController gameController) throws IOException {
        try {
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

    public void startReceiver(){
        Receiver receiver = new Receiver(this, server);
        pool.execute(receiver);
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void addRoom(String name) {
        Message message = new Message.Builder(DataType.ROOM)
                .roomName(name)
                .build();
        try {
            out.writeObject(message);
            out.flush();
        } catch (IOException e) {
            System.out.println("FAILED TO SEND MESSAGE");
            e.printStackTrace();
        }
    }

    public void joinRoom(int id, String roomName) {
        Message message = new Message.Builder(DataType.ROOM)
                .roomName(roomName)
                .roomId(id)
                .playerName(playerName)
                .join(true)
                .build();
        try {
            out.writeObject(message);
            out.flush();

            Parent root = gameController.getRoot();

            Scene initialGameScene = new Scene(root);
            primaryStage.setScene(initialGameScene);
        } catch (IOException e) {
            System.out.println("FAILED TO SEND MESSAGE");
            e.printStackTrace();
        }
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

}