package koslin.jan.projekt.server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Player {
    private final Socket clientSocket;
    private final ObjectOutputStream outputStream;
    private final int playerId;
    private String username;
    private String password;
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

    public String getUsername() {
        return username;
    }

    public int getRoomId() {
        return roomId;
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
