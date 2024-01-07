/**
 * This module contains packages related to the multiplayer card game.
 * <h2>
 * Author: Jan Koślin <br>
 * Version: 1.0
 * </h2>
 */
module koslin.jan.projekt {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml;
    requires jdk.httpserver;
    requires org.json;


    opens koslin.jan.projekt.client to javafx.fxml;
    exports koslin.jan.projekt.client;
}
