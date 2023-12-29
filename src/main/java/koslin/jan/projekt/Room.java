package koslin.jan.projekt;

import koslin.jan.projekt.server.Player;

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
    private String actualColor;
    private int roundNumber;
    private int lewaNumber;

    public Room(String roomName, int roomId) {
        this.roomName = roomName;
        this.roomId = roomId;
        this.players = new ArrayList<>();
        this.cardsInGame = new HashMap<>();
        this.actualColor = "";
        this.roundNumber = 1;
        this.lewaNumber = 1;
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public int getLewaNumber() {
        return lewaNumber;
    }

    public void nextLewa() {
        lewaNumber++;
        if(lewaNumber == 14) lewaNumber = 1;
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

    public String getActualColor() {
        return actualColor;
    }

    public void setActualColor(String actualColor) {
        this.actualColor = actualColor;
    }

    public void addPlayer(Player player){
        // TYMCZASOWO ŻEBY SZYBCIEJ SIE LOGOWAC ->>>>>>>
        player.setUsername("player" + (players.size()+1));

        players.add(player);
        if(players.size() == NUMBER_OF_PLAYERS){
            List<List<String>> divided = Deck.divideIntoPortions(Deck.cards);
            players.get(0).setTurn(true);

            for(int i = 0;i < NUMBER_OF_PLAYERS; i++){
                players.get(i).setCards(divided.get(i));
            }
        }
    }

    public void nextTurn(){
        int i=0;
        for (; i<NUMBER_OF_PLAYERS; i++){
            if(players.get(i).isTurn()) break;
        }
        players.get(i).setTurn(false);
        i++;
        if(i == NUMBER_OF_PLAYERS) i=0;
        players.get(i).setTurn(true);
    }

    public void setTurnForWinner(int playerId){
        for (int i = 0; i<NUMBER_OF_PLAYERS; i++){
            if (players.get(i).getPlayerId() == playerId){
                players.get(i).setTurn(true);
            } else {
                players.get(i).setTurn(false);
            }
        }
    }
}
