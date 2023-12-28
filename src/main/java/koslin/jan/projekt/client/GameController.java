package koslin.jan.projekt.client;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import koslin.jan.projekt.Room;
import koslin.jan.projekt.RoomManager;
import koslin.jan.projekt.server.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class GameController {

    public Label player1;
    public Label player2;
    public Label player3;
    public Label player4;
    public Pane root;

    Client client;
    ArrayList<Label> playersNamesLabels = new ArrayList<>();

    public void setClient(Client client) {
        this.client = client;
    }

    @FXML
    private void handleReady(ActionEvent event) {
        System.out.println("SUPER");
    }

    public void initialize() {
        playersNamesLabels.add(player1);
        playersNamesLabels.add(player2);
        playersNamesLabels.add(player3);
        playersNamesLabels.add(player4);
    }

    public void updateUI(RoomManager roomManager) {
        Platform.runLater(() -> {
            Room room = roomManager.getRooms().get(client.getRoomId());
            ArrayList<Player> players = room.getPlayers();

            int i = 0;
            for(;i < players.size(); i++){
                if(players.get(i).getPlayerId() == client.getPlayerId()){
                    break;
                }
            }
            for(int k = 0;k < players.size(); k++){
                int index = k-i;
                if(index < 0) index += 4;
                playersNamesLabels.get(index).setText(players.get(k).getUsername());
            }
        });
    }

    public void showGameBoard() {
        Platform.runLater(() -> {
            Scene game = new Scene(root);
            client.primaryStage.setScene(game);
        });
    }
}
