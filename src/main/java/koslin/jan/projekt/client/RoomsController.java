package koslin.jan.projekt.client;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import koslin.jan.projekt.Room;
import koslin.jan.projekt.RoomManager;
import java.io.IOException;
import java.util.HashMap;

/**
 * Controller class for the rooms view, handling UI interactions and updating room information.
 */
public class RoomsController {

    public Label welcomeLabel;
    public VBox root;
    public TextField roomNameInput;
    public Label infoLabel;
    Scene rooms;

    Client client;
    HashMap<Integer, Button> roomsButtons = new HashMap<>();

    /**
     * Initializes the RoomsController, creating the scene for the rooms view.
     */
    public void initialize(){
        rooms = new Scene(root);
    }

    /**
     * Handles the event of adding a room when the corresponding button is clicked.
     *
     * @param event The ActionEvent triggered by the button click.
     * @throws IOException If an IO exception occurs during room addition.
     */
    @FXML
    private void handleAddingRoom(ActionEvent event) throws IOException {
        String name = roomNameInput.getText();
        client.addRoom(name);
        roomNameInput.clear();
    }

    /**
     * Handles the key press event for the input field.
     * If the pressed key is the ENTER key, triggers the sendChatMessage method.
     *
     * @param event The KeyEvent associated with the key press.
     *              Contains information about the pressed key, such as the key code.
     */
    @FXML
    private void handleKeyPress(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            String name = roomNameInput.getText();
            client.addRoom(name);
            roomNameInput.clear();
        }
    }

    /**
     * Changes the scene to the rooms view, displaying the welcome label with the player's name.
     *
     * @param name The name of the player to be displayed in the welcome label.
     */
    public void changeScene(String name) {
        Platform.runLater(() -> {
            welcomeLabel.setText("Wiatj, " + name + "!");
            client.primaryStage.setScene(rooms);
        });
    }

    public void setClient(Client client) {
        this.client = client;
    }

    /**
     * Updates the UI with the latest room information from the RoomManager.
     *
     * @param roomManager The RoomManager containing the latest room information.
     */
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

    /**
     * Sets the information label's text on the UI.
     * For example when the room is full and player is unable to join.
     *
     * @param information The text to be set on the information label.
     */
    public void setInfoLabelText(String information) {
        Platform.runLater(() -> {
            infoLabel.setText(information);
        });
    }
}

