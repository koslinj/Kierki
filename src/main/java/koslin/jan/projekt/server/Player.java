package koslin.jan.projekt.server;

import koslin.jan.projekt.client.Client;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Player {
    private final Socket clientSocket;
    private final ObjectOutputStream outputStream;
    private final int playerId;
    private String playerName;
    private int roomId;

    public Player(Socket clientSocket, int id) throws IOException {
        this.clientSocket = clientSocket;
        this.playerId = id;
        this.outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
        this.roomId = -1;
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public ObjectOutputStream getOutputStream() {
        return outputStream;
    }

    public int getPlayerId() {
        return playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
}
