package koslin.jan.projekt.client;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import koslin.jan.projekt.Deck;
import koslin.jan.projekt.Room;
import koslin.jan.projekt.RoomManager;
import koslin.jan.projekt.server.Player;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static koslin.jan.projekt.server.Server.NUMBER_OF_PLAYERS;

public class GameController {

    public Label player1;
    public Label player2;
    public Label player3;
    public Label player4;
    public Pane root;
    public Label gameInfo;

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
            for (; i < players.size(); i++) {
                if (players.get(i).getPlayerId() == client.getPlayerId()) {
                    break;
                }
            }
            for (int k = 0; k < players.size(); k++) {
                int index = k - i;
                if (index < 0) index += NUMBER_OF_PLAYERS;
                playersNamesLabels.get(index).setText(players.get(k).getUsername());
            }

            if (players.size() == NUMBER_OF_PLAYERS) {
                gameInfo.setText("POKÓJ PEŁEN -> START GRY");

                HBox hbox = new HBox();
                hbox.setLayoutX(70);
                hbox.setLayoutY(400);
                for (String card : players.get(client.getPlayerId()).getCards()) {

                    // Load and add 13 images to the HBox
                    Image image = new Image(getClass().getResource("cards/" + card).toString(), 80, 90, true, true);
                    ImageView imageView = new ImageView(image);

                    //Inner border
                    HBox hBox_inner = new HBox();
                    hBox_inner.setStyle("-fx-border-color: black;-fx-border-width: 2;-fx-border-radius: 5");
                    hBox_inner.getChildren().add(imageView);

                    // Set negative margin to overlap images by 15px
                    HBox.setMargin(imageView, new javafx.geometry.Insets(0, 0, 0, 0));
                    HBox.setMargin(hBox_inner, new javafx.geometry.Insets(0, 0, 0, -25));
                    hbox.getChildren().add(hBox_inner);
                }
                root.getChildren().add(hbox);
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
