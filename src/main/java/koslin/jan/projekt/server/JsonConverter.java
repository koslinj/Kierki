package koslin.jan.projekt.server;

import koslin.jan.projekt.server.Player;
import koslin.jan.projekt.Room;
import koslin.jan.projekt.RoomManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

public class JsonConverter {

    public static String convertRoomManagerToJson(RoomManager roomManager) {
        JSONObject jsonRoomManager = new JSONObject();
        jsonRoomManager.put("rooms", convertRoomsToJsonArray(roomManager.getRooms()));
        // Add other fields as needed

        return jsonRoomManager.toString();
    }

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

