package koslin.jan.projekt.server;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

/**
 * The {@code Player} class represents a player in the game.
 * Players have unique identifiers, usernames, passwords, associated room IDs, and game-related information.
 */
public class Player implements Serializable {
    private final int playerId;
    private String username;
    private String password;
    private int roomId;
    private boolean turn;
    private List<String> cards;
    private int points;

    /**
     * Constructs a new {@code Player} instance with the given player ID.
     *
     * @param id The unique identifier for the player.
     * @throws IOException If an I/O error occurs.
     */
    public Player(int id) throws IOException {
        this.playerId = id;
        this.roomId = -1;
        this.turn = false;
    }

    public void addPoints(int amount) {
        points += amount;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getPoints() {
        return points;
    }

    public int getPlayerId() {
        return playerId;
    }

    public String getUsername() {
        return username;
    }

    public int getRoomId() {
        return roomId;
    }

    public boolean isTurn() {
        return turn;
    }

    public void setTurn(boolean turn) {
        this.turn = turn;
    }

    public List<String> getCards() {
        return cards;
    }

    public void setCards(List<String> cards) {
        this.cards = cards;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
