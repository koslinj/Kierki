package koslin.jan.projekt.client;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import koslin.jan.projekt.Room;
import koslin.jan.projekt.RoomManager;
import java.io.IOException;
import java.util.HashMap;

public class RoomsController {

    public Label welcomeLabel;
    public VBox root;
    public TextField roomNameInput;

    Client client;
    HashMap<Integer, Button> roomsButtons = new HashMap<>();

    @FXML
    private void handleAddingRoom(ActionEvent event) throws IOException {
        String name = roomNameInput.getText();
        client.addRoom(name);
    }

    public void changeScene(String name) {
        Platform.runLater(() -> {
            welcomeLabel.setText("Wiatj, " + name + "!");
            Scene rooms = new Scene(root);
            client.primaryStage.setScene(rooms);
        });
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void updateUI(RoomManager roomManager) {
        Platform.runLater(() -> {
            // cleaning UI
            for (Button b : roomsButtons.values()) {
                root.getChildren().remove(b);
            }
            roomsButtons.clear();

            // creating buttons for rooms
            for (Room r : roomManager.getRooms().values()) {
                Button roomButton = new Button(r.getRoomName() + " -> " + r.getPlayers().size() + " of 4");
                roomButton.setOnAction(event -> {
                    client.joinRoom(r.getRoomId());
                });
                root.getChildren().add(roomButton);
                roomsButtons.put(r.getRoomId(), roomButton);
            }
        });
    }
}

