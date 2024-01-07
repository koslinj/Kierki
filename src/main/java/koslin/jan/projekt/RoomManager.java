package koslin.jan.projekt;

import java.io.*;
import java.util.HashMap;

public class RoomManager implements Serializable {

    private final HashMap<Integer, Room> rooms;
    private int roomCounter;

    public RoomManager() {
        this.rooms = new HashMap<>();
        this.roomCounter = 1;
    }

    public synchronized HashMap<Integer, Room> getRooms() {
        return rooms;
    }

    /**
     * This method handles creating Room and putting it in HashMap
     * @param message Message received from the client that contains roomName
     */
    public synchronized void addRoom(Message message) {
        Room room = new Room(message.getRoomName(), roomCounter);
        rooms.put(roomCounter, room);
        roomCounter++;
    }

    @Override
    public Object clone() {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(this);

            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);

            return ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace(); // Handle exceptions appropriately
            return null;
        }
    }
}
