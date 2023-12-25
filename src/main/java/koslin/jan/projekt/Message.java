package koslin.jan.projekt;

import java.io.Serializable;

public class Message implements Serializable {
    private DataType type;
    private String roomName;
    private int roomId;
    private int amountOfPlayers;
    private int playerId;
    private String playerName;
    private boolean join;

    // private constructor to enforce the use of the builder
    private Message(Builder builder) {
        this.type = builder.type;
        this.roomName = builder.roomName;
        this.roomId = builder.roomId;
        this.amountOfPlayers = builder.amountOfPlayers;
        this.playerId = builder.playerId;
        this.playerName = builder.playerName;
        this.join = builder.join;
    }

    // getters
    public DataType getType() {
        return type;
    }

    public String getRoomName() {
        return roomName;
    }

    public int getRoomId() {
        return roomId;
    }

    public int getAmountOfPlayers() {
        return amountOfPlayers;
    }

    public int getPlayerId() {
        return playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public boolean isJoin() {
        return join;
    }

    public void setAmountOfPlayers(int amountOfPlayers) {
        this.amountOfPlayers = amountOfPlayers;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    // Builder class
    public static class Builder {
        // Necessary property
        private final DataType type;

        // Optional properties with default values
        private String roomName = "";
        private int roomId = 0;
        private int amountOfPlayers = 0;
        private int playerId = 0;
        private String playerName = "";
        private boolean join = false;

        // Constructor with necessary property
        public Builder(DataType type) {
            this.type = type;
        }

        // methods for setting optional attributes
        public Builder roomName(String roomName) {
            this.roomName = roomName;
            return this;
        }

        public Builder roomId(int roomId) {
            this.roomId = roomId;
            return this;
        }

        public Builder amountOfPlayers(int amountOfPlayers) {
            this.amountOfPlayers = amountOfPlayers;
            return this;
        }

        public Builder playerId(int playerId) {
            this.playerId = playerId;
            return this;
        }

        public Builder playerName(String playerName) {
            this.playerName = playerName;
            return this;
        }

        public Builder join(boolean join) {
            this.join = join;
            return this;
        }

        // method to build the final object
        public Message build() {
            // Validation can be added if needed
            return new Message(this);
        }
    }
}

