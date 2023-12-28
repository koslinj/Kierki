package koslin.jan.projekt.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.Socket;

public class LoginController {

    public TextField usernameInputLogin;
    public TextField passwordInputLogin;
    public TextField usernameInputRegister;
    public TextField passwordInputRegister;
    public Label errorLabel;
    public VBox root;

    Client client;

    public void setClient(Client client) {
        this.client = client;
    }

    @FXML
    private void handleSubmitLogin(ActionEvent event) {
        String username = usernameInputLogin.getText();
        String password = passwordInputLogin.getText();
        client.login(username, password);
    }

    @FXML
    private void handleSubmitRegister(ActionEvent event) {
        String username = usernameInputRegister.getText();
        String password = passwordInputRegister.getText();
        client.register(username, password);
    }

    public void setErrorLabel(String str) {
        errorLabel.setText(str);
    }
}

