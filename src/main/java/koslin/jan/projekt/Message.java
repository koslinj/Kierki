package koslin.jan.projekt;

import java.io.Serializable;

/**
 * The Message class represents a serializable object used for communication between the client and server.
 * It includes various attributes such as message type, room information, player details, and success status.
 * The class follows the builder pattern to facilitate the creation of Message objects with optional attributes.
 */
public class Message implements Serializable {
    private DataType type;
    private String roomName;
    private int roomId;
    private int amountOfPlayers;
    private int playerId;
    private String username;
    private String password;
    private boolean success;
    private boolean join;
    private String card;

    // private constructor to enforce the use of the builder
    private Message(Builder builder) {
        this.type = builder.type;
        this.roomName = builder.roomName;
        this.roomId = builder.roomId;
        this.amountOfPlayers = builder.amountOfPlayers;
        this.playerId = builder.playerId;
        this.username = builder.username;
        this.password = builder.password;
        this.join = builder.join;
        this.success = builder.success;
        this.card = builder.card;
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

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isJoin() {
        return join;
    }

    public String getCard() {
        return card;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setAmountOfPlayers(int amountOfPlayers) {
        this.amountOfPlayers = amountOfPlayers;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    /**
     * Builder class for creating instances of Message with optional attributes.
     */
    public static class Builder {
        // Necessary property
        private final DataType type;

        // Optional properties with default values
        private String roomName = "";
        private int roomId = 0;
        private int amountOfPlayers = 0;
        private int playerId = 0;
        private String username = "";
        private String password = "";
        private boolean join = false;
        private boolean success = false;
        private String card = "";

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

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder join(boolean join) {
            this.join = join;
            return this;
        }

        public Builder card(String card) {
            this.card = card;
            return this;
        }

        public Builder success(boolean success) {
            this.success = success;
            return this;
        }

        // method to build the final object
        public Message build() {
            // Validation can be added if needed
            return new Message(this);
        }
    }
}

