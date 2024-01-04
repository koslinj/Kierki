package koslin.jan.projekt.client;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;
import koslin.jan.projekt.Deck;
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
    public Label points1;
    public Label points2;
    public Label points3;
    public Label points4;
    public ImageView card1;
    public ImageView card2;
    public ImageView card3;
    public ImageView card4;
    public Polygon triangle1;
    public Polygon triangle2;
    public Polygon triangle3;
    public Polygon triangle4;
    public Pane root;
    public Label roundInfo;
    public HBox cardsContainer;
    public Group endingPanel;
    public Scene game;

    Client client;
    ArrayList<Label> playersNamesLabels = new ArrayList<>();
    ArrayList<Label> playersPointsLabels = new ArrayList<>();
    ArrayList<ImageView> cardsInGameImages = new ArrayList<>();
    ArrayList<Polygon> triangles = new ArrayList<>();

    @FXML
    private void handleBackToRooms(ActionEvent event) {
        client.leaveRoom();
        client.roomsController.changeScene(client.getPlayerName());
        clearAll();
    }

    private void clearAll() {
        for(Label l : playersPointsLabels){
            l.setText("");
        }
        for(Label l : playersNamesLabels){
            l.setText("");
        }
        for(ImageView iv : cardsInGameImages){
            iv.setVisible(false);
        }
        cardsContainer.getChildren().clear();
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void initialize() {
        playersNamesLabels.add(player1);
        playersNamesLabels.add(player2);
        playersNamesLabels.add(player3);
        playersNamesLabels.add(player4);

        playersPointsLabels.add(points1);
        playersPointsLabels.add(points2);
        playersPointsLabels.add(points3);
        playersPointsLabels.add(points4);

        cardsInGameImages.add(card1);
        cardsInGameImages.add(card2);
        cardsInGameImages.add(card3);
        cardsInGameImages.add(card4);

        triangles.add(triangle1);
        triangles.add(triangle2);
        triangles.add(triangle3);
        triangles.add(triangle4);

        game = new Scene(root);
    }

    public void updateUI(RoomManager roomManager) {
        Platform.runLater(() -> {
            Room room = roomManager.getRooms().get(client.getRoomId());
            if(room.getRoundNumber() == 8){
                endingPanel.setVisible(true);
            } else {
                endingPanel.setVisible(false);
            }
            roundInfo.setText("RUNDA " + room.getRoundNumber());

            ArrayList<Player> players = room.getPlayers();

            int i = getIndexOfPlayer(players);
            Player myPlayer = players.get(i);

            for (int k = 0; k < players.size(); k++) {
                int index = calculatePlace(i, k);
                playersNamesLabels.get(index).setText(players.get(k).getUsername());
                playersPointsLabels.get(index).setText("PUNKTY: " + players.get(k).getPoints());
                triangles.get(index).setVisible(players.get(k).isTurn());

                displayCardsInGame(room, players, k, index);
            }

            if (players.size() == NUMBER_OF_PLAYERS) {
                displayMyPlayerCards(room, myPlayer);
            }
        });
    }

    private void displayCardsInGame(Room room, ArrayList<Player> players, int k, int index) {
        String card = room.getCardsInGame().get(players.get(k).getPlayerId());
        if(card != null){
            Image image = new Image(getClass().getResource("cards/" + card).toString(), 80, 110, true, true);
            cardsInGameImages.get(index).setImage(image);
            cardsInGameImages.get(index).setVisible(true);
        } else {
            cardsInGameImages.get(index).setVisible(false);
        }
    }

    private void displayMyPlayerCards(Room room, Player myPlayer) {
        // cleaning UI
        cardsContainer.getChildren().clear();

        // count cards matching with actual color
        int count = 0;
        boolean hasActualColor = false;
        for (String card : myPlayer.getCards()){
            if(Deck.colorFromCard(card).equals(room.getActualColor())) count++;
        }
        if(count > 0) hasActualColor = true;

        // creating images for cards
        for (String card : myPlayer.getCards()) {
            ImageView imageView = getImageView(card);
            HBox imageViewWrapper = getHBox(card, room.getActualColor(), hasActualColor, imageView, myPlayer);

            cardsContainer.getChildren().add(imageViewWrapper);
        }
    }

    private static int calculatePlace(int i, int k) {
        int index = k - i;
        if (index < 0) index += NUMBER_OF_PLAYERS;
        return index;
    }

    private int getIndexOfPlayer(ArrayList<Player> players) {
        int i = 0;
        for (; i < players.size(); i++) {
            if (players.get(i).getPlayerId() == client.getPlayerId()) {
                break;
            }
        }
        return i;
    }

    private HBox getHBox(String card, String actualColor, boolean hasActualColor, ImageView imageView, Player myPlayer) {
        HBox imageViewWrapper = new HBox();
        imageViewWrapper.setStyle("-fx-border-color: black;-fx-border-width: 2;-fx-border-radius: 5");
        imageViewWrapper.getChildren().add(imageView);
        if(myPlayer.isTurn()){
            if(hasActualColor){
                if(Deck.colorFromCard(card).equals(actualColor)){
                    imageViewWrapper.setOpacity(1.0);
                    imageViewWrapper.setOnMouseClicked(event -> {
                        System.out.println(card);
                        client.chooseCard(card);
                    });
                } else {
                    imageViewWrapper.setOpacity(0.5);
                }
            } else {
                imageViewWrapper.setOpacity(1.0);
                imageViewWrapper.setOnMouseClicked(event -> {
                    System.out.println(card);
                    client.chooseCard(card);
                });
            }

        } else {
            imageViewWrapper.setOpacity(0.5);
        }

        HBox.setMargin(imageViewWrapper, new javafx.geometry.Insets(0, 0, 0, -25));
        return imageViewWrapper;
    }

    private ImageView getImageView(String card) {
        Image image = new Image(getClass().getResource("cards/" + card).toString(), 80, 90, true, true);
        ImageView imageView = new ImageView(image);
        HBox.setMargin(imageView, new javafx.geometry.Insets(0, 0, -1, 0));
        return imageView;
    }

    public void showGameBoard() {
        Platform.runLater(() -> {
            client.primaryStage.setScene(game);
        });
    }
}
