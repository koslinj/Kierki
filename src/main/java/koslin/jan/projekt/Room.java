package koslin.jan.projekt;

import koslin.jan.projekt.server.GameLogic;
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
    private String actualColor;
    private int roundNumber;
    private int lewaNumber;
    private int startingPlayerIndex;

    public Room(String roomName, int roomId) {
        this.roomName = roomName;
        this.roomId = roomId;
        this.players = new ArrayList<>();
        this.cardsInGame = new HashMap<>();
        this.actualColor = "";
        this.roundNumber = 6;
        this.lewaNumber = 1;
        this.startingPlayerIndex = 0;
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
            players.get(startingPlayerIndex).setTurn(true);

            for(int i = 0;i < NUMBER_OF_PLAYERS; i++){
                players.get(i).setCards(divided.get(i));
            }
        }
    }

    public void nextRound(){
        roundNumber++;
        List<List<String>> divided = Deck.divideIntoPortions(Deck.cards);
        startingPlayerIndex++;
        if(startingPlayerIndex == 4){
            startingPlayerIndex = 0;
        }
        for (Player p : players){
            p.setTurn(false);
        }
        players.get(startingPlayerIndex).setTurn(true);

        for(int i = 0;i < NUMBER_OF_PLAYERS; i++){
            players.get(i).setCards(divided.get(i));
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

    public void addPointsToWinner(Rule rule, int playerId){
        int points = GameLogic.calculatePoints(rule, cardsInGame, lewaNumber);
        for (Player p : players){
            if(p.getPlayerId() == playerId) p.addPoints(points);
        }
    }

    public void addPointsToWinnerInRound7(int playerId){
        for (Rule r : Server.rulesForRounds.values()){
            int points = GameLogic.calculatePoints(r, cardsInGame, lewaNumber);
            for (Player p : players){
                if(p.getPlayerId() == playerId) p.addPoints(points);
            }
        }
    }
}
