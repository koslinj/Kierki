package koslin.jan.projekt.client;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import koslin.jan.projekt.Room;
import koslin.jan.projekt.RoomManager;
import koslin.jan.projekt.server.Player;
import java.util.ArrayList;

import static koslin.jan.projekt.server.Server.NUMBER_OF_PLAYERS;

public class GameController {

    public Label player1;
    public Label player2;
    public Label player3;
    public Label player4;
    public ImageView card1;
    public ImageView card2;
    public ImageView card3;
    public ImageView card4;
    public Pane root;
    public Label gameInfo;
    public HBox cardsContainer;

    Client client;
    ArrayList<Label> playersNamesLabels = new ArrayList<>();
    ArrayList<ImageView> cardsInGameImages = new ArrayList<>();
    ArrayList<HBox> imageViewWrappers = new ArrayList<>();

    public void setClient(Client client) {
        this.client = client;
    }

    public void initialize() {
        playersNamesLabels.add(player1);
        playersNamesLabels.add(player2);
        playersNamesLabels.add(player3);
        playersNamesLabels.add(player4);

        cardsInGameImages.add(card1);
        cardsInGameImages.add(card2);
        cardsInGameImages.add(card3);
        cardsInGameImages.add(card4);
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

                String card = room.getCardsInGame().get(players.get(k).getPlayerId());
                if(card != null){
                    Image image = new Image(getClass().getResource("cards/" + card).toString(), 80, 110, true, true);
                    cardsInGameImages.get(index).setImage(image);
                }
            }

            if (players.size() == NUMBER_OF_PLAYERS) {
                //gameInfo.setText("POKÓJ PEŁEN -> START GRY");
                // cleaning UI
                for(HBox node : imageViewWrappers){
                    cardsContainer.getChildren().remove(node);
                }
                imageViewWrappers.clear();

                // creating images for cards
                for (String card : players.get(i).getCards()) {

                    // Load and add 13 images to the HBox
                    Image image = new Image(getClass().getResource("cards/" + card).toString(), 80, 90, true, true);
                    ImageView imageView = new ImageView(image);

                    //Inner border
                    HBox imageViewWrapper = new HBox();
                    imageViewWrapper.setStyle("-fx-border-color: black;-fx-border-width: 2;-fx-border-radius: 5");
                    imageViewWrapper.getChildren().add(imageView);
                    imageViewWrapper.setOnMouseClicked(event -> {
                        System.out.println(card);
                        client.chooseCard(card);
                    });

                    // Set negative margin to overlap images by 15px
                    HBox.setMargin(imageView, new javafx.geometry.Insets(0, 0, -1, 0));
                    HBox.setMargin(imageViewWrapper, new javafx.geometry.Insets(0, 0, 0, -25));
                    cardsContainer.getChildren().add(imageViewWrapper);
                    imageViewWrappers.add(imageViewWrapper);
                }
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
