module koslin.jan.projekt {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml;


    opens koslin.jan.projekt.client to javafx.fxml;
    exports koslin.jan.projekt.client;
}
