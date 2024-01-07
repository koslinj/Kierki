package koslin.jan.projekt.server;

import koslin.jan.projekt.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static koslin.jan.projekt.server.Server.NUMBER_OF_PLAYERS;

/**
 * extends {@link Thread} in order to work in the background.
 * This thread is started for each client that connects to the server.
 * Handles communication with a client, processing messages and managing the player's state in the server.
 */
public class ClientHandler extends Thread {
    private Player player;
    private final Socket clientSocket;
    private final ObjectOutputStream outputStream;
    private ObjectInputStream in;
    private RoomManager roomManager;
    private HashMap<Integer, ObjectOutputStream> allOutputStreams;

    /**
     * Constructs a ClientHandler with the given parameters.
     *
     * @param player            The associated player.
     * @param clientSocket      The client's socket.
     * @param outputStream      The output stream to send messages to the client.
     * @param roomManager       The room manager containing the current state of rooms.
     * @param allOutputStreams  All output streams for connected clients.
     * @throws IOException      If an I/O error occurs.
     */
    public ClientHandler(Player player, Socket clientSocket, ObjectOutputStream outputStream, RoomManager roomManager, HashMap<Integer, ObjectOutputStream> allOutputStreams) throws IOException {
        this.player = player;
        this.clientSocket = clientSocket;
        this.outputStream = outputStream;
        this.roomManager = roomManager;
        this.allOutputStreams = allOutputStreams;
    }

    /**
     * Runs the thread, processing incoming messages from the client.
     */
    public void run() {
        try {
            sendInitialMessage();

            in = new ObjectInputStream(clientSocket.getInputStream());

            while (clientSocket.isConnected()) {
                Message message = (Message) in.readObject();
                if (message.getType() == DataType.QUIT) {
                    handleQuitMessage(message);
                    break;
                } else if (message.getType() == DataType.LOGIN) {
                    handleLoginMessage(message);
                } else if (message.getType() == DataType.REGISTER) {
                    handleRegisterMessage(message);
                } else if (message.getType() == DataType.ROOM) {
                    handleRoomMessage(message);
                } else if (message.getType() == DataType.GAME) {
                    handleGameMessage(message);
                } else if (message.getType() == DataType.LEAVE_ROOM) {
                    handleLeaveRoomMessage();
                }
            }

            in.close();
            clientSocket.close();
        } catch (IOException | ClassNotFoundException | InterruptedException e) {
            e.printStackTrace();
        }

    }

    /**
     * Handles the message when a player leaves the room, updating room information,
     * managing the end of a round if needed, and broadcasting changes to all connected clients.
     *
     * @throws IOException If an I/O error occurs.
     */
    private void handleLeaveRoomMessage() throws IOException {
        int roomId = player.getRoomId();
        Room room = roomManager.getRooms().get(roomId);

        if(room.getRoundNumber() != 8 && room.getPlayers().size() == NUMBER_OF_PLAYERS){
            room.setRoundNumber(8);
        }
        room.removePlayer(player);
        player.setTurn(false);

        for (ObjectOutputStream os : allOutputStreams.values()) {
            os.reset();
            os.writeObject(roomManager);
            os.flush();
        }
    }

    /**
     * Sends the initial registration message to the client.
     *
     * @throws IOException If an I/O error occurs.
     */
    private void sendInitialMessage() throws IOException {
        Message message = new Message.Builder(DataType.REGISTER)
                .playerId(player.getPlayerId())
                .build();
        outputStream.writeObject(message);
        outputStream.flush();
    }

    /**
     * Handles the quit message, removing the player and closing the associated output stream.
     *
     * @param message The quit message.
     * @throws IOException If an I/O error occurs.
     */
    private void handleQuitMessage(Message message) throws IOException {
        outputStream.writeObject(message);
        outputStream.flush();
        allOutputStreams.remove(player.getPlayerId());
    }

    /**
     * Handles the login message, verifying credentials and sending the response to the client.
     *
     * @param message The login message.
     * @throws IOException If an I/O error occurs.
     */
    private void handleLoginMessage(Message message) throws IOException {
        if (message.getUsername().equals(player.getUsername()) && message.getPassword().equals(player.getPassword())) {
            Message res = new Message.Builder(DataType.LOGIN)
                    .success(true)
                    .username(message.getUsername())
                    .build();
            outputStream.writeObject(res);
            outputStream.flush();

            outputStream.reset();
            outputStream.writeObject(roomManager);
            outputStream.flush();
        } else {
            Message res = new Message.Builder(DataType.LOGIN)
                    .success(false)
                    .build();
            outputStream.writeObject(res);
            outputStream.flush();
        }
    }

    /**
     * Handles the registration message, setting the player's username and password.
     *
     * @param message The registration message.
     */
    private void handleRegisterMessage(Message message) {
        player.setUsername(message.getUsername());
        player.setPassword(message.getPassword());
    }

    /**
     * Handles the room-related messages (joining or creating), updating room information,
     * and broadcasting changes to all connected clients.
     *
     * @param message The room message.
     * @throws IOException If an I/O error occurs.
     */
    private void handleRoomMessage(Message message) throws IOException {
        if (message.isJoin()) {
            player.setRoomId(message.getRoomId());
            Room room = roomManager.getRooms().get(message.getRoomId());
            if(room.getPlayers().size() == NUMBER_OF_PLAYERS){
                Message res = new Message.Builder(DataType.ROOM)
                        .success(false)
                        .build();
                outputStream.writeObject(res);
                outputStream.flush();
            } else {
                room.addPlayer(player);
            }
        } else {
            roomManager.addRoom(message);
        }

        for (ObjectOutputStream os : allOutputStreams.values()) {
            os.reset();
            os.writeObject(roomManager);
            os.flush();
        }
    }

    /**
     * Handles the game-related messages (player moves), updating game state,
     * and broadcasting changes to all connected clients in this room.
     *
     * @param message The game message.
     * @throws IOException        If an I/O error occurs.
     * @throws InterruptedException If the thread is interrupted.
     */
    private void handleGameMessage(Message message) throws IOException, InterruptedException {
        Room room = roomManager.getRooms().get(player.getRoomId());
        HashMap<Integer, String> cardsInGame = room.getCardsInGame();

        makeMove(message, room, cardsInGame);

        if(cardsInGame.size() == 1){
            room.setActualColor(Deck.colorFromCard(message.getCard()));
        }
        else if(cardsInGame.size() == NUMBER_OF_PLAYERS){
            int playerId = Deck.getWinnerOfLewa(room.getActualColor(), cardsInGame);

            if(room.getRoundNumber() == 7){
                room.addPointsToWinnerInRound7(playerId);
            } else {
                Rule rule = Server.rulesForRounds.get(room.getRoundNumber());
                room.addPointsToWinner(rule, playerId);
            }
            // wyświetlenie graczom wszystkich kart i odczekanie 1s przed wyczyszczeniem stołu
            displayAndWait(room);
            //wyczyszczenie stołu
            clearAndGoToNextLewa(room, cardsInGame, playerId);
            if(player.getCards().size() == 0){
                room.nextRound();
            }
        }
        sendToPlayersInRoom(room);
    }

    /**
     * Clears the cards on the table, updates the turn, and prepares for the next round.
     *
     * @param room         The current room.
     * @param cardsInGame  The cards played in the current game.
     * @param playerId     The player ID who won the round.
     */
    private void clearAndGoToNextLewa(Room room, HashMap<Integer, String> cardsInGame, int playerId) {
        cardsInGame.clear();
        room.setTurnForWinner(playerId);
        room.setActualColor("");
        room.nextLewa();
    }

    /**
     * Displays the cards on the table and waits for a brief moment before clearing the table.
     * It is used to let players see all the cards on the table for a while, before proceeding to next Lewa.
     *
     * @param room The current room.
     * @throws IOException        If an I/O error occurs.
     * @throws InterruptedException If the thread is interrupted.
     */
    private void displayAndWait(Room room) throws IOException, InterruptedException {
        room.setTurnFalseForEveryone();
        sendToPlayersInRoom(room);
        sleep(1000);
    }

    /**
     * Helper function that sends the current room state to all players in the room.
     *
     * @param room The current room.
     * @throws IOException If an I/O error occurs.
     */
    private void sendToPlayersInRoom(Room room) throws IOException {
        for(Player p : room.getPlayers()){
            allOutputStreams.get(p.getPlayerId()).reset();
            allOutputStreams.get(p.getPlayerId()).writeObject(roomManager);
            allOutputStreams.get(p.getPlayerId()).flush();
        }
    }

    /**
     * Processes the player's move, removes the card from their hand,
     * updates the turn, and adds the card to the cards played in the current game.
     *
     * @param message      The game message containing the player's move.
     * @param room         The current room.
     * @param cardsInGame  The cards played in the current game.
     */
    private void makeMove(Message message, Room room, HashMap<Integer, String> cardsInGame) {
        player.getCards().remove(message.getCard());
        room.nextTurn();
        cardsInGame.put(player.getPlayerId(), message.getCard());
    }
}