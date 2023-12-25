package koslin.jan.projekt;

import java.util.HashMap;

public class RoomManager {

    private final HashMap<Integer, Room> rooms;
    private int roomCounter;

    public RoomManager() {
        this.rooms = new HashMap<>();
        this.roomCounter = 1;
    }

    public synchronized HashMap<Integer, Room> getRooms() {
        return rooms;
    }

    public synchronized Room addRoom(Message message) {
        Room room = new Room(message.getRoomName(), roomCounter, 0);
        rooms.put(roomCounter, room);
        //message.setRoomId(roomCounter);
        roomCounter++;
        return room;
    }
}
