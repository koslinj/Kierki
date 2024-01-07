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
import koslin.jan.projekt.server.GameLogic;
import koslin.jan.projekt.server.Player;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static koslin.jan.projekt.server.Server.NUMBER_OF_PLAYERS;

/**
 * The GameController class manages the graphical user interface (GUI) for the game view.
 * It displays information about the current round, players, and cards in the game.
 * The class also handles user interactions, such as choosing cards and navigating back to the rooms view.
 *
 * <p>The GameController class works in conjunction with the Client class to update the GUI based on server responses.
 * It utilizes JavaFX components like labels, images, and polygons to represent player information and cards on the screen.</p>
 *
 */
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

    /**
     * Initializes the GameController, setting up JavaFX components and data structures.
     * Important in order to handle GUI changes after in game actions
     */
    public void initialize() {
        playersNamesLabels.addAll(Arrays.asList(player1, player2, player3, player4));
        playersPointsLabels.addAll(Arrays.asList(points1, points2, points3, points4));
        cardsInGameImages.addAll(Arrays.asList(card1, card2, card3, card4));
        triangles.addAll(Arrays.asList(triangle1, triangle2, triangle3, triangle4));

        game = new Scene(root);
    }

    /**
     * Updates the whole game UI based on the provided RoomManager.
     *
     * @param roomManager The RoomManager containing information about rooms and players.
     */
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

    /**
     * Displays the cards in the game for a specific player.
     *
     * @param room    The current room.
     * @param players The list of players.
     * @param k       The index of the player in the list.
     * @param index   The index of place to display the cards for.
     */
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

    /**
     * Displays the cards that the current player has.
     *
     * @param room     The current room.
     * @param myPlayer The current player.
     */
    private void displayMyPlayerCards(Room room, Player myPlayer) {
        // cleaning UI
        cardsContainer.getChildren().clear();

        // count cards matching with actual color
        boolean hasActualColor = myPlayer.getCards().stream().anyMatch(card -> Deck.colorFromCard(card).equals(room.getActualColor()));
        int countOtherThanHearts = (int) myPlayer.getCards().stream().filter(card -> !Deck.colorFromCard(card).equals("hearts")).count();
        boolean hideHearts = List.of(2, 5, 7).contains(room.getRoundNumber())
                && countOtherThanHearts > 0
                && room.getActualColor().equals("");


        // creating images for cards
        for (String card : myPlayer.getCards()) {
            boolean validColor = GameLogic.isValidColor(card, hideHearts, room.getActualColor(), hasActualColor);
            ImageView imageView = getImageView(card);
            HBox imageViewWrapper = getHBox(card, imageView, myPlayer, validColor);

            cardsContainer.getChildren().add(imageViewWrapper);
        }
    }

    /**
     * Calculates the position to display a player's information based on the current player.
     *
     * @param i The index of the current player.
     * @param k The index of the player to calculate the position for.
     * @return The calculated index for displaying the player's information.
     */
    private static int calculatePlace(int i, int k) {
        int index = k - i;
        if (index < 0) index += NUMBER_OF_PLAYERS;
        return index;
    }

    /**
     * Retrieves the index of the current player in the list of players.
     *
     * @param players The list of players.
     * @return The index of the current player.
     */
    private int getIndexOfPlayer(ArrayList<Player> players) {
        int i = 0;
        for (; i < players.size(); i++) {
            if (players.get(i).getPlayerId() == client.getPlayerId()) {
                break;
            }
        }
        return i;
    }

    /**
     * Creates and configures an HBox containing an ImageView for a card.
     *
     * @param card       The card name.
     * @param imageView  The ImageView for the card.
     * @param myPlayer   The player associated with the card.
     * @param validColor Indicates if the card has a valid color.
     * @return The configured HBox containing the ImageView.
     */
    private HBox getHBox(String card, ImageView imageView, Player myPlayer, boolean validColor) {
        HBox imageViewWrapper = new HBox();
        imageViewWrapper.setStyle("-fx-border-color: black;-fx-border-width: 2;-fx-border-radius: 5");
        imageViewWrapper.getChildren().add(imageView);
        if (myPlayer.isTurn()) {
            imageViewWrapper.setOpacity(validColor ? 1.0 : 0.5);

            if (validColor) {
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

    /**
     * Creates an ImageView for a card with the specified name.
     * Loads image of specified card from the resources.
     *
     * @param card The name of the card.
     * @return The configured ImageView for the card.
     */
    private ImageView getImageView(String card) {
        Image image = new Image(getClass().getResource("cards/" + card).toString(), 80, 90, true, true);
        ImageView imageView = new ImageView(image);
        HBox.setMargin(imageView, new javafx.geometry.Insets(0, 0, -1, 0));
        return imageView;
    }

    /**
     * Shows the game board scene in the primary stage.
     */
    public void showGameBoard() {
        Platform.runLater(() -> {
            client.primaryStage.setScene(game);
        });
    }
}
