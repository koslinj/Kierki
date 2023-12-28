package koslin.jan.projekt.server;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

public class Player implements Serializable {
    private final int playerId;
    private String username;
    private String password;
    private int roomId;
    private boolean turn;
    private List<String> cards;

    public Player(int id) throws IOException {
        this.playerId = id;
        this.roomId = -1;
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
