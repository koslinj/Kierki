package koslin.jan.projekt.server;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import koslin.jan.projekt.Room;
import koslin.jan.projekt.RoomManager;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

/**
 * The {@code Website} class represents the embedded HTTP server for the card game application.
 * It provides a simple web interface for monitoring the application status and interacting with the game rooms.
 */
public class Website {
    private RoomManager roomManager;
    private int websitePort;
    private HttpServer server;

    /**
     * Initializes a new instance of the {@code Website} class with the specified parameters.
     *
     * @param websitePort  The port number for the embedded website.
     * @param roomManager  The room manager managing game rooms.
     */
    public Website(int websitePort, RoomManager roomManager) {
        this.websitePort = websitePort;
        this.roomManager = roomManager;
    }

    /**
     * Starts the embedded HTTP server for the website.
     *
     * @throws IOException  If an I/O error occurs while starting the server.
     */
    public void startWebsite() throws IOException {
        server = HttpServer.create(new InetSocketAddress(websitePort), 0);

        // Create a context for the root endpoint ("/")
        server.createContext("/", new RootHandler());
        // Create a context for the "/api" endpoint
        server.createContext("/api", new AppStatusHandler());
        // Define the endpoint to skip a round in a specific room
        server.createContext("/skipRound", new SkipRoundHandler());

        // Start the server
        server.setExecutor(null);
        server.start();
        System.out.println("Server started on port 8080");
    }

    /**
     * Stops the embedded HTTP server for the website.
     */
    public void stopWebsite() {
        if (server != null) {
            server.stop(0); // Parameter 0 ensures immediate shutdown
            System.out.println("Website stopped");
        }
    }

    /**
     * The {@code RootHandler} class represents the HTTP handler for the root ("/") endpoint.
     * It serves the HTML content to the clients accessing the root endpoint.
     */
    class RootHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            // Read the HTML content from a file
            String htmlResponse = readHtmlResource();

            // Send the HTML response
            exchange.sendResponseHeaders(200, htmlResponse.length());
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(htmlResponse.getBytes(StandardCharsets.UTF_8));
            }
        }

        private String readHtmlResource() throws IOException {
            // Read the HTML content from the resource
            try (InputStream inputStream = getClass().getResourceAsStream("index.html")) {
                if (inputStream != null) {
                    return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
                } else {
                    throw new IOException("Resource not found: " + "index.html");
                }
            }
        }
    }

    /**
     * The {@code AppStatusHandler} class represents the HTTP handler for the "/api" endpoint.
     * It serves the application status in JSON format to clients accessing the "/api" endpoint.
     */
    class AppStatusHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            // Convert the roomManager object to JSON (you need to implement this conversion)
            String response = convertRoomManagerToJson(roomManager);

            // Send the JSON response
            exchange.sendResponseHeaders(200, response.length());
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        }

        // Convert RoomManager object to JSON (you need to implement this method)
        private String convertRoomManagerToJson(RoomManager roomManager) {
            return JsonConverter.convertRoomManagerToJson(roomManager);
        }
    }

    class SkipRoundHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            // Extract room ID from the request URI
            String path = exchange.getRequestURI().getPath();
            String[] pathSegments = path.split("/");
            int roomId = Integer.parseInt(pathSegments[pathSegments.length - 1]);

            // Call the method to skip the round in the specified room
            skipRound(roomId);

            // Respond with a success message
            String response = "Round skipped successfully";
            exchange.sendResponseHeaders(200, response.length());
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        }

        private void skipRound(int roomId) throws IOException {
            // Implement the logic to skip the round in the specified room
            // This method should handle the logic to skip the round
            Room room = roomManager.getRooms().get(roomId);
            if (room != null) {
                room.skipRound();

                System.out.println("Round skipped in room: " + roomId);
            }
        }
    }

}
