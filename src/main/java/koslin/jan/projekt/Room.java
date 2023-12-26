package koslin.jan.projekt;

import koslin.jan.projekt.server.Player;

import java.io.Serializable;
import java.util.HashMap;

public class Room implements Serializable {
    private String roomName;
    private int roomId;
    private final HashMap<Integer, Player> players;

    public Room(String roomName, int roomId) {
        this.roomName = roomName;
        this.roomId = roomId;
        this.players = new HashMap<>();
    }

    public String getRoomName() {
        return roomName;
    }

    public int getRoomId() {
        return roomId;
    }

    public HashMap<Integer, Player> getPlayers() {
        return players;
    }

    public void addPlayer(Player player){
        players.put(player.getPlayerId(), player);
    }
}
