package koslin.jan.projekt.client;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import koslin.jan.projekt.Message;

import java.util.HashMap;

public class GameController {

    @FXML
    private VBox root;

    Client client;
    HashMap<Integer, Label> playersNamesLabels = new HashMap<>();

    public VBox getRoot() {
        return root;
    }

    public void addToRoot(Node n){
        root.getChildren().add(n);
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @FXML
    private void handleReady(ActionEvent event) {
        System.out.println("SUPER");
    }

    public void updateUI(Message message) {
        Platform.runLater(() -> {
            Label playerNameLabel = new Label(message.getPlayerName());

            addToRoot(playerNameLabel);
            playersNamesLabels.put(message.getPlayerId(), playerNameLabel);
        });
    }
}
