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
import koslin.jan.projekt.Room;
import koslin.jan.projekt.RoomManager;

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

    public void setClient(Client client) {
        this.client = client;
    }

    public void updateUI(RoomManager roomManager) {
        Platform.runLater(() -> {
            for (Button b : roomsButtons.values()) {
                root.getChildren().remove(b);
            }
        });


        Platform.runLater(() -> {
            for (Room r : roomManager.getRooms().values()) {
                Button roomButton = new Button(r.getRoomName() + " -> " + r.getPlayers().size() + " of 4");
                roomButton.setOnAction(event -> {
                    client.joinRoom(r.getRoomId(), r.getRoomName());
                });
                root.getChildren().add(roomButton);
                roomsButtons.put(r.getRoomId(), roomButton);
            }
        });


//        if (roomManager.isJoin()) {
//            Button roomButton = roomsButtons.get(roomManager.getRoomId());
//            roomButton.setText(roomManager.getRoomName() + " -> " + roomManager.getAmountOfPlayers() + " of 4");
//        } else {
//            Button roomButton = new Button(roomManager.getRoomName() + " -> " + roomManager.getAmountOfPlayers() + " of 4");
//            roomButton.setOnAction(event -> {
//                client.joinRoom(roomManager.getRoomId(), roomManager.getRoomName());
//            });
//            addToRoot(roomButton);
//            roomsButtons.put(roomManager.getRoomId(), roomButton);
//        }
    }
}

