package koslin.jan.projekt;

import java.io.*;
import java.util.HashMap;

/**
 * The RoomManager class manages the creation and storage of Room objects in a multiplayer card game.
 */
public class RoomManager implements Serializable {

    private final HashMap<Integer, Room> rooms;
    private int roomCounter;

    /**
     * Constructs a RoomManager with an initial empty collection of rooms.
     */
    public RoomManager() {
        this.rooms = new HashMap<>();
        this.roomCounter = 1;
    }

    /**
     * Retrieves the collection of rooms.
     *
     * @return A HashMap containing room ID and Room objects.
     */
    public synchronized HashMap<Integer, Room> getRooms() {
        return rooms;
    }

    /**
     * Adds a new room to the collection based on the information provided in the message.
     *
     * @param message The Message object containing room information (roomName).
     */
    public synchronized void addRoom(Message message) {
        Room room = new Room(message.getRoomName(), roomCounter);
        rooms.put(roomCounter, room);
        roomCounter++;
    }
}
