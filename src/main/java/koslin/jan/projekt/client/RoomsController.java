package koslin.jan.projekt.client;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import koslin.jan.projekt.DataType;
import koslin.jan.projekt.Message;

import java.io.IOException;
import java.util.HashMap;

public class RoomsController {

    @FXML
    private Label welcomeLabel;
    @FXML
    private VBox root;
    @FXML
    private TextField roomNameInput;

    Client client;
    HashMap<Integer, Button> roomsButtons = new HashMap<>();

    @FXML
    private void handleAddingRoom(ActionEvent event) throws IOException {
        String name = roomNameInput.getText();
        client.addRoom(name);
    }

    public VBox getRoot() {
        return root;
    }

    public void setWelcomeMessage(String name) {
        welcomeLabel.setText("Welcome, " + name + "!");
    }

    public void addToRoot(Node n){
        root.getChildren().add(n);
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void updateUI(Message message){
        if (message.isJoin()) {
            Platform.runLater(() -> {
                Button roomButton = roomsButtons.get(message.getRoomId());
                roomButton.setText(message.getRoomName() + " -> " + message.getAmountOfPlayers() + " of 4");
            });
        } else {
            Platform.runLater(() -> {
                Button roomButton = new Button(message.getRoomName() + " -> " + message.getAmountOfPlayers() + " of 4");
                roomButton.setOnAction(event -> {
                    client.joinRoom(message.getRoomId(), message.getRoomName());
                });
                addToRoot(roomButton);
                roomsButtons.put(message.getRoomId(), roomButton);
            });
        }
    }
}

