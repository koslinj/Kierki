package koslin.jan.projekt.client;

import koslin.jan.projekt.DataType;
import koslin.jan.projekt.Message;

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
                Message message = (Message) in.readObject();
                if (message.getType() == DataType.QUIT) {
                    break;
                } else if (message.getType() == DataType.ROOM) {
                    client.roomsController.updateUI(message);
                } else {
                    client.gameController.updateUI(message);
                }
            }
            System.out.println("RECEIVER ENDED");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
