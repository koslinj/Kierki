package koslin.jan.projekt.server;

import koslin.jan.projekt.server.Player;
import koslin.jan.projekt.Room;
import koslin.jan.projekt.RoomManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * The {@code JsonConverter} class provides methods to convert objects, such as {@code RoomManager},
 * to their JSON representation.
 * With this class it is possible to include only important fields in the output JSON.
 */
public class JsonConverter {

    /**
     * Converts a {@code RoomManager} object to its JSON representation.
     *
     * @param roomManager The {@code RoomManager} to be converted.
     * @return The simplified JSON representation of the {@code RoomManager}.
     */
    public static String convertRoomManagerToJson(RoomManager roomManager) {
        JSONObject jsonRoomManager = new JSONObject();
        jsonRoomManager.put("rooms", convertRoomsToJsonArray(roomManager.getRooms()));

        return jsonRoomManager.toString();
    }

    /**
     * Converts a map of rooms to a JSON array.
     *
     * @param rooms The map of rooms to be converted.
     * @return The JSON array representing the list of rooms.
     */
    private static JSONArray convertRoomsToJsonArray(Map<Integer, Room> rooms) {
        JSONArray jsonRooms = new JSONArray();
        for (Map.Entry<Integer, Room> entry : rooms.entrySet()) {
            JSONObject jsonRoom = new JSONObject();
            jsonRoom.put("roomId", entry.getKey());
            jsonRoom.put("roomName", entry.getValue().getRoomName());
            jsonRoom.put("players", convertPlayersToJsonArray(entry.getValue().getPlayers()));
            jsonRoom.put("roundNumber", entry.getValue().getRoundNumber());
            jsonRoom.put("lewaNumber", entry.getValue().getLewaNumber());

            jsonRooms.put(jsonRoom);
        }
        return jsonRooms;
    }

    /**
     * Converts a list of players to a JSON array.
     *
     * @param players The list of players to be converted.
     * @return The JSON array representing the list of players.
     */
    private static JSONArray convertPlayersToJsonArray(List<Player> players) {
        JSONArray jsonPlayers = new JSONArray();
        for (Player player : players) {
            JSONObject jsonPlayer = new JSONObject();
            jsonPlayer.put("playerId", player.getPlayerId());
            jsonPlayer.put("username", player.getUsername());
            jsonPlayer.put("points", player.getPoints());

            jsonPlayers.put(jsonPlayer);
        }
        return jsonPlayers;
    }
}

