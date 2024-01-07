package koslin.jan.projekt;

import koslin.jan.projekt.server.GameLogic;
import koslin.jan.projekt.server.Player;
import koslin.jan.projekt.server.Server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static koslin.jan.projekt.server.Server.NUMBER_OF_PLAYERS;

/**
 * The Room class represents a room in a multiplayer card game, containing information about players,
 * cards in the current game, and game state.
 */
public class Room implements Serializable {
    private String roomName;
    private int roomId;
    private final ArrayList<Player> players;
    private HashMap<Integer, String> cardsInGame;
    private String actualColor;
    private int roundNumber;
    private int lewaNumber;
    private int startingPlayerIndex;

    /**
     * Constructs a Room with the specified name and ID.
     *
     * @param roomName The name of the room.
     * @param roomId   The ID of the room.
     */
    public Room(String roomName, int roomId) {
        this.roomName = roomName;
        this.roomId = roomId;
        this.players = new ArrayList<>();
        this.cardsInGame = new HashMap<>();
        this.actualColor = "";
        this.roundNumber = 1;
        this.lewaNumber = 1;
        this.startingPlayerIndex = 0;
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public void setRoundNumber(int roundNumber) {
        this.roundNumber = roundNumber;
    }

    public int getLewaNumber() {
        return lewaNumber;
    }

    /**
     * Increments the lewa number.
     * If lewa number is 14 then revert to first lewa
     */
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

    /**
     * Adds a player to the room and distributes cards if the room is full.
     *
     * @param player The player to add.
     */
    public void addPlayer(Player player){
        players.add(player);
        if(players.size() == NUMBER_OF_PLAYERS){
            List<List<String>> divided = Deck.divideIntoPortions(Deck.cards);
            players.get(startingPlayerIndex).setTurn(true);

            for(int i = 0;i < NUMBER_OF_PLAYERS; i++){
                players.get(i).setCards(divided.get(i));
            }
        }
    }

    /**
     * Removes a player from the room and resets their points. If the room becomes empty,
     * resets game state variables.
     *
     * @param player The player to remove.
     */
    public void removePlayer(Player player){
        int toRemove = 0;
        for (; toRemove<players.size(); toRemove++){
            if(players.get(toRemove).getPlayerId() == player.getPlayerId()) break;
        }
        players.remove(toRemove);
        player.setPoints(0);

        if(players.size() == 0){
            this.actualColor = "";
            this.roundNumber = 1;
            this.lewaNumber = 1;
            this.startingPlayerIndex = 0;
        }
    }

    /**
     * Advances to the next round, redistributes cards, and sets the starting player.
     */
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

    /**
     * Advances to the next turn, updating the player with the current turn.
     */
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

    public void setTurnFalseForEveryone(){
        for (int i=0; i<NUMBER_OF_PLAYERS; i++){
            players.get(i).setTurn(false);
        }
    }

    /**
     * Sets the turn attribute to true for the winner of the previous round.
     *
     * @param playerId The ID of the winning player.
     */
    public void setTurnForWinner(int playerId){
        for (int i = 0; i<NUMBER_OF_PLAYERS; i++){
            if (players.get(i).getPlayerId() == playerId){
                players.get(i).setTurn(true);
            } else {
                players.get(i).setTurn(false);
            }
        }
    }

    /**
     * Adds points to the winner of the current round based on the specified rule.
     *
     * @param rule     The rule to calculate points.
     * @param playerId The ID of the winning player.
     */
    public void addPointsToWinner(Rule rule, int playerId){
        int points = GameLogic.calculatePoints(rule, cardsInGame, lewaNumber);
        for (Player p : players){
            if(p.getPlayerId() == playerId) p.addPoints(points);
        }
    }

    /**
     * Adds points to the winner of the round 7.
     * That means points are based on all rules
     *
     * @param playerId The ID of the winning player.
     */
    public void addPointsToWinnerInRound7(int playerId){
        for (Rule r : Server.rulesForRounds.values()){
            int points = GameLogic.calculatePoints(r, cardsInGame, lewaNumber);
            for (Player p : players){
                if(p.getPlayerId() == playerId) p.addPoints(points);
            }
        }
    }
}
