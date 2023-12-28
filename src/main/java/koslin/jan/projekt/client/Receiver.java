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

public class Receiver implements Runnable {

    Client client;
    Socket server;

    public Receiver(Client client, Socket server) {
        this.server = server;
        this.client = client;
    }

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
                    }
                } else if (obj instanceof RoomManager roomManager) {
                    client.roomsController.updateUI(roomManager);
                    if(client.getRoomId() == -1){
                        for(Room r : roomManager.getRooms().values()){
                            for(Player p : r.getPlayers()){
                                if(p.getPlayerId() == client.getPlayerId()){
                                    client.setRoomId(r.getRoomId());
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
