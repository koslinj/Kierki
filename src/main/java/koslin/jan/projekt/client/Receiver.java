package koslin.jan.projekt.client;

import javafx.application.Platform;
import koslin.jan.projekt.DataType;
import koslin.jan.projekt.Message;
import koslin.jan.projekt.Room;
import koslin.jan.projekt.RoomManager;
import koslin.jan.projekt.server.Player;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * A Runnable class responsible for receiving messages from the server and updating the client accordingly.
 * It is connected with specific client and allow to listen for messages from server in the background.
 * After reading a message it assigns tasks to the specific controller in associated {@link Client}
 */
public class Receiver implements Runnable {

    Client client;
    Socket server;

    /**
     * Constructs a Receiver instance.
     *
     * @param client The client associated with this receiver.
     * @param server The socket connected to the server for receiving messages.
     */
    public Receiver(Client client, Socket server) {
        this.server = server;
        this.client = client;
    }

    /**
     * Continuously listens for incoming messages from the server and updates the client accordingly.
     * The thread terminates when a QUIT message is received or an exception occurs.
     */
    @Override
    public void run() {
        try {
            ObjectInputStream in = new ObjectInputStream(server.getInputStream());
            while (server.isConnected()) {
                Object obj = in.readObject();
                if (obj instanceof Message message){
                    if (message.getType() == DataType.QUIT) {
                        break;
                    } else if (message.getType() == DataType.REGISTER) {
                        client.setPlayerId(message.getPlayerId());
                    } else if (message.getType() == DataType.LOGIN) {
                        client.handleLoginResponse(message);
                    } else if (message.getType() == DataType.ROOM) {
                        client.handleRoomResponse(message);
                    }
                } else if (obj instanceof RoomManager roomManager) {
                    client.roomsController.updateUI(roomManager);
                    if(client.getRoomId() == -1){
                        for(Room r : roomManager.getRooms().values()){
                            for(Player p : r.getPlayers()){
                                if(p.getPlayerId() == client.getPlayerId()){
                                    client.setRoomId(r.getRoomId());
                                    client.roomsController.setInfoLabelText("");
                                    client.gameController.showGameBoard();
                                    client.gameController.updateUI(roomManager);
                                }
                            }
                        }
                    } else {
                        client.gameController.updateUI(roomManager);
                    }
                }

            }
            System.out.println("RECEIVER ENDED");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
