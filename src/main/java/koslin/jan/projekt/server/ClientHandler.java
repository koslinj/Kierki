package koslin.jan.projekt.server;

import koslin.jan.projekt.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Optional;

import static koslin.jan.projekt.server.Server.NUMBER_OF_PLAYERS;

public class ClientHandler extends Thread {
    private Player player;
    private final Socket clientSocket;
    private final ObjectOutputStream outputStream;
    private ObjectInputStream in;
    private RoomManager roomManager;
    private HashMap<Integer, ObjectOutputStream> allOutputStreams;

    public ClientHandler(Player player, Socket clientSocket, ObjectOutputStream outputStream, RoomManager roomManager, HashMap<Integer, ObjectOutputStream> allOutputStreams) throws IOException {
        this.player = player;
        this.clientSocket = clientSocket;
        this.outputStream = outputStream;
        this.roomManager = roomManager;
        this.allOutputStreams = allOutputStreams;
    }

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
                }
            }

            in.close();
            clientSocket.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void sendInitialMessage() throws IOException {
        Message message = new Message.Builder(DataType.REGISTER)
                .playerId(player.getPlayerId())
                .build();
        outputStream.writeObject(message);
        outputStream.flush();

        // TYMCZASOWO ŻEBY SZYBCIEJ SIE LOGOWAC ->>>>>>>
        RoomManager res2 = (RoomManager) roomManager.clone();
        for (ObjectOutputStream os : allOutputStreams.values()) {
            os.writeObject(res2);
            os.flush();
        }
    }

    private void handleQuitMessage(Message message) throws IOException {
        outputStream.writeObject(message);
        outputStream.flush();
        allOutputStreams.remove(player.getPlayerId());
    }

    private void handleLoginMessage(Message message) throws IOException {
        // TYMCZASOWO ŻEBY SZYBCIEJ SIE LOGOWAC ->>>>>>>
//        Message res = new Message.Builder(DataType.LOGIN)
//                .success(true)
//                .username(message.getUsername())
//                .build();
//        outputStream.writeObject(res);
//        outputStream.flush();
//
//        RoomManager res2 = (RoomManager) roomManager.clone();
//        outputStream.writeObject(res2);
//        outputStream.flush();
        if (message.getUsername().equals(player.getUsername()) && message.getPassword().equals(player.getPassword())) {
            Message res = new Message.Builder(DataType.LOGIN)
                    .success(true)
                    .username(message.getUsername())
                    .build();
            outputStream.writeObject(res);
            outputStream.flush();

            RoomManager res2 = (RoomManager) roomManager.clone();
            outputStream.writeObject(res2);
            outputStream.flush();
        } else {
            Message res = new Message.Builder(DataType.LOGIN)
                    .success(false)
                    .build();
            outputStream.writeObject(res);
            outputStream.flush();
        }
    }

    private void handleRegisterMessage(Message message) {
        player.setUsername(message.getUsername());
        player.setPassword(message.getPassword());
    }

    private void handleRoomMessage(Message message) throws IOException {
        if (message.isJoin()) {
            player.setRoomId(message.getRoomId());
            Room room = roomManager.getRooms().get(message.getRoomId());
            room.addPlayer(player);
        } else {
            roomManager.addRoom(message);
        }

        RoomManager res = (RoomManager) roomManager.clone();
        for (ObjectOutputStream os : allOutputStreams.values()) {
            os.writeObject(res);
            os.flush();
        }
    }

    private void handleGameMessage(Message message) throws IOException {
        Room room = roomManager.getRooms().get(player.getRoomId());
        HashMap<Integer, String> cardsInGame = room.getCardsInGame();

        makeMove(message, room, cardsInGame);

        if(cardsInGame.size() == 1){
            room.setActualColor(Deck.colorFromCard(message.getCard()));
        }
        else if(cardsInGame.size() == NUMBER_OF_PLAYERS){
            int playerId = Deck.getWinnerOfLewa(room.getActualColor(), cardsInGame);

            Rule rule = Server.rulesForRounds.get(room.getRoundNumber());

            room.addPointsToWinner(rule, playerId);
            room.setTurnForWinner(playerId);
            room.setActualColor("");
            room.nextLewa();
            cardsInGame.clear();
        }
        sendToPlayersInRoom(room);
    }

    private void sendToPlayersInRoom(Room room) throws IOException {
        RoomManager res = (RoomManager) roomManager.clone();
        for(Player p : room.getPlayers()){
            allOutputStreams.get(p.getPlayerId()).writeObject(res);
            allOutputStreams.get(p.getPlayerId()).flush();
        }
    }

    private void makeMove(Message message, Room room, HashMap<Integer, String> cardsInGame) {
        player.getCards().remove(message.getCard());
        room.nextTurn();
        cardsInGame.put(player.getPlayerId(), message.getCard());
    }
}