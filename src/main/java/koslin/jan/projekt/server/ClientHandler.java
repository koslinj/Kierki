package koslin.jan.projekt.server;

import koslin.jan.projekt.DataType;
import koslin.jan.projekt.Message;
import koslin.jan.projekt.Room;
import koslin.jan.projekt.RoomManager;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;

public class ClientHandler extends Thread {
    private Player player;
    private ObjectInputStream in;
    private RoomManager roomManager;
    private HashMap<Integer, Player> allPlayers;

    public ClientHandler(Player player, RoomManager roomManager, HashMap<Integer, Player> players) {
        this.player = player;
        this.roomManager = roomManager;
        this.allPlayers = players;
    }

    public void run() {
        try {
            in = new ObjectInputStream(player.getClientSocket().getInputStream());

            while (player.getClientSocket().isConnected()) {
                Message message = (Message) in.readObject();
                if(message.getType() == DataType.QUIT){
                    allPlayers.get(player.getPlayerId()).getOutputStream().writeObject(message);
                    allPlayers.get(player.getPlayerId()).getOutputStream().flush();
                    allPlayers.remove(player.getPlayerId());
                    break;
                }
                handleMessage(message);
            }

            in.close();
            player.getClientSocket().close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void handleMessage(Message message) throws IOException {
        if (message.isJoin()) {
            player.setPlayerName(message.getPlayerName());
            allPlayers.get(player.getPlayerId()).setRoomId(message.getRoomId());
            Room room = roomManager.getRooms().get(message.getRoomId());
            room.addPlayer(player);
            message.setAmountOfPlayers(room.getAmountOfPlayers());

            for(Player p : room.getPlayers().values()){
                Message res = new Message.Builder(DataType.GAME)
                        .playerId(player.getPlayerId())
                        .playerName(player.getPlayerName())
                        .build();

                if(p!=player){
                    p.getOutputStream().writeObject(res);
                    p.getOutputStream().flush();
                }
            }

            for(Player p : room.getPlayers().values()){
                Message res = new Message.Builder(DataType.GAME)
                        .playerId(p.getPlayerId())
                        .playerName(p.getPlayerName())
                        .build();

                player.getOutputStream().writeObject(res);
                player.getOutputStream().flush();
            }

        } else {
            Room room = roomManager.addRoom(message);
            message.setRoomId(room.getRoomId());
        }

        for(Player p : allPlayers.values()){
            p.getOutputStream().writeObject(message);
            System.out.println("ID pokoju -> " + message.getRoomId());
            System.out.println("Pokój gracza " + p.getPlayerId() + " -> " + p.getRoomId());
            p.getOutputStream().flush();
        }
    }
}