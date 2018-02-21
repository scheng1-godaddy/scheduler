package shawn_cheng.view_controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Button;

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

    public void initialize(URL url, ResourceBundle rb) {

    }
}

