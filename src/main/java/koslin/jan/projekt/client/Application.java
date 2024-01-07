package koslin.jan.projekt.client;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import koslin.jan.projekt.ConfigReader;

import java.net.Socket;

/**
 * The Application class is the main entry point for the JavaFX client application
 * of the multiplayer card game. It extends javafx.application.Application and
 * initializes the graphical user interface (GUI) by loading FXML files for login,
 * rooms, and game views. It also establishes a connection to the server using a
 * Client instance, manages the communication between controllers, and handles the
 * application exit action.
 *
 * <p>This class uses three controllers: {@link LoginController}, {@link RoomsController},
 * and {@link GameController}, to manage the corresponding views. The connection to the
 * server is established using the {@link Client} class.</p>
 *
 * <p>The client application supports the following features:</p>
 * <ul>
 *     <li>Login to the game with a username and password.</li>
 *     <li>Browse available rooms and join or create a room.</li>
 *     <li>Play the multiplayer card game in a dedicated game room.</li>
 *     <li>Properly handles application exit by sending a quit signal to the server.</li>
 * </ul>
 *
 */
public class Application extends javafx.application.Application {
    Client client;

    /**
     * Entry point for the JavaFX application. Initializes the GUI, sets up the connection
     * to the server, and manages the application's lifecycle events.
     *
     * @param primaryStage The primary stage for the JavaFX application.
     * @throws Exception If an error occurs during the application initialization.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("login.fxml"));
        loginLoader.load();
        LoginController loginController = loginLoader.getController();

        FXMLLoader roomsLoader = new FXMLLoader(getClass().getResource("rooms.fxml"));
        roomsLoader.load();
        RoomsController roomsController = roomsLoader.getController();

        FXMLLoader gameLoader = new FXMLLoader(getClass().getResource("game.fxml"));
        gameLoader.load();
        GameController gameController = gameLoader.getController();

        int port = ConfigReader.getPort();
        String ip = ConfigReader.getIp();

        client = new Client(new Socket(ip, port), primaryStage,loginController, roomsController, gameController);
        client.startReceiver();
        setClientInControllers(loginController, roomsController, gameController);

        primaryStage.setOnCloseRequest(event -> {
            event.consume();
            exitAction(event, client);
        });

        primaryStage.setTitle("Kierki");
        primaryStage.setScene(new Scene(loginController.root));
        primaryStage.show();
    }

    /**
     * Sets the client instance in the corresponding controllers.
     *
     * @param loginController The controller for the login view.
     * @param roomsController The controller for the rooms view.
     * @param gameController  The controller for the game view.
     */
    private void setClientInControllers(LoginController loginController, RoomsController roomsController, GameController gameController){
        loginController.setClient(client);
        roomsController.setClient(client);
        gameController.setClient(client);
    }

    /**
     * Handles the application exit action, ensuring a proper shutdown by sending a quit signal to the server.
     *
     * @param event  The window event triggered on application exit.
     * @param client The client instance associated with the application.
     */
    private void exitAction(WindowEvent event, Client client) {
        System.out.println("Exiting the application.");
        if (client != null) {
            client.quit();
        }
        System.out.println("KONIEC");
        System.exit(0);
    }

    /**
     * The main method to launch the JavaFX application.
     *
     * @param args Command-line arguments (not used in this application).
     */
    public static void main(String[] args) {
        launch();
    }
}