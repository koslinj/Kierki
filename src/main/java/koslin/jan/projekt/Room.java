package koslin.jan.projekt;

import koslin.jan.projekt.server.Player;
import koslin.jan.projekt.server.Server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static koslin.jan.projekt.server.Server.NUMBER_OF_PLAYERS;

public class Room implements Serializable {
    private String roomName;
    private int roomId;
    private final ArrayList<Player> players;
    private HashMap<Integer, String> cardsInGame;

    public Room(String roomName, int roomId) {
        this.roomName = roomName;
        this.roomId = roomId;
        this.players = new ArrayList<>();
        this.cardsInGame = new HashMap<>();
    }

    public String getRoomName() {
        return roomName;
    }

    public int getRoomId() {
        return roomId;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public HashMap<Integer, String> getCardsInGame() {
        return cardsInGame;
    }

    public void addPlayer(Player player){
        players.add(player);
        if(players.size() == NUMBER_OF_PLAYERS){
            List<List<String>> divided = Deck.divideIntoPortions(Deck.cards);

            for(int i = 0;i < NUMBER_OF_PLAYERS; i++){
                players.get(i).setCards(divided.get(i));
            }
        }
    }
}
