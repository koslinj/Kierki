package koslin.jan.projekt.client;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.net.Socket;

public class Application extends javafx.application.Application {
    Client client;

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

        client = new Client(new Socket("127.0.0.1", 6000), primaryStage,loginController, roomsController, gameController);
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

    private void setClientInControllers(LoginController loginController, RoomsController roomsController, GameController gameController){
        loginController.setClient(client);
        roomsController.setClient(client);
        gameController.setClient(client);
    }

    private void exitAction(WindowEvent event, Client client) {
        System.out.println("Exiting the application.");
        if (client != null) {
            client.quit();
        }
        System.out.println("KONIEC");
        System.exit(0);
    }

    public static void main(String[] args) {
        launch();
    }
}