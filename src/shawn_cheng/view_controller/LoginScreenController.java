package shawn_cheng.view_controller;

import java.net.URL;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import javax.annotation.Resource;

/**
 * FXML Controller class for the main screen
 *
 * @author Shawn Cheng
 */
public class LoginScreenController implements Initializable {
    @FXML
    private AnchorPane ap;

    @FXML
    private Button exitButton;

    @FXML
    private Label promptLabel;

    @FXML
    private TextField userNameInput;

    @FXML
    private TextField passWordInput;

    @FXML
    private Button submitButton;

    Locale locale;
    ResourceBundle resourceBundle;

    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    void exitButtonHandler(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Are you sure you want to exit?");
        alert.setContentText("Press OK to exit");
        alert.showAndWait()
                .filter(userResponse -> userResponse == ButtonType.OK)
                .ifPresent(userResponse -> System.exit(0));

    }
}

