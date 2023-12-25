package koslin.jan.projekt.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.Socket;

public class LoginController {

    @FXML
    private TextField nameInput;
    @FXML
    private VBox root;

    Client client;

    public VBox getRoot() {
        return root;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @FXML
    private void handleSubmit(ActionEvent event) {
        String name = nameInput.getText();
        client.setPlayerName(name);

        Parent root = client.roomsController.getRoot();
        client.roomsController.setWelcomeMessage(name);

        Scene welcomeScene = new Scene(root);
        client.primaryStage.setScene(welcomeScene);
    }
}

