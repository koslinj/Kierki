package koslin.jan.projekt.client;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 * Controller class for the login view, handling user interactions connected with register and login.
 */
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

    /**
     * Handles the login form submission event.
     *
     * @param event The ActionEvent triggered by the login form submission.
     */
    @FXML
    private void handleSubmitLogin(ActionEvent event) {
        String username = usernameInputLogin.getText();
        String password = passwordInputLogin.getText();
        client.login(username, password);
    }

    /**
     * Handles the register form submission event.
     *
     * @param event The ActionEvent triggered by the register form submission.
     */
    @FXML
    private void handleSubmitRegister(ActionEvent event) {
        String username = usernameInputRegister.getText();
        String password = passwordInputRegister.getText();
        client.register(username, password);
    }

    /**
     * Sets an error message to be displayed in the error label.
     *
     * @param errorMessage The error message to be displayed.
     */
    public void setErrorLabel(String errorMessage) {
        Platform.runLater(() -> {
            errorLabel.setText(errorMessage);
        });
    }
}

