package koslin.jan.projekt;

import koslin.jan.projekt.server.Player;

import java.util.HashMap;

public class Room {
    private String roomName;
    private int roomId;
    private int amountOfPlayers;
    private final HashMap<Integer, Player> players;

    public Room(String roomName, int roomId, int amountOfPlayers) {
        this.roomName = roomName;
        this.roomId = roomId;
        this.amountOfPlayers = amountOfPlayers;
        this.players = new HashMap<>();
    }

    public String getRoomName() {
        return roomName;
    }

    public int getRoomId() {
        return roomId;
    }

    public int getAmountOfPlayers() {
        return amountOfPlayers;
    }

    public HashMap<Integer, Player> getPlayers() {
        return players;
    }

    public void setAmountOfPlayers(int amountOfPlayers) {
        this.amountOfPlayers = amountOfPlayers;
    }

    public void addPlayer(Player player){
        players.put(player.getPlayerId(), player);
        setAmountOfPlayers(amountOfPlayers + 1);
    }
}
