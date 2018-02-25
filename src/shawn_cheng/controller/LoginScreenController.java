package shawn_cheng.controller;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.event.ActionEvent;
import shawn_cheng.MainApp;
import shawn_cheng.access.UserAccess;
import shawn_cheng.model.User;

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

    @FXML
    private Label userNameLabel;

    @FXML
    private Label passwordLabel;

    @FXML
    private Label mainTitleLabel;

    private MainApp mainApp;

    Locale locale;
    ResourceBundle rb;

    public void initialize(URL url, ResourceBundle rb) {
        // Set text for labels
        //this.userNameLabel.setText(this.rb.getString("username"));

    }

    public void setMainApp(MainApp mainapp) { this.mainApp = mainapp; }

    public void setText() {
        System.out.println("Resource Bundle is " + mainApp.rb);
        this.userNameLabel.setText(mainApp.rb.getString("username"));
        this.passwordLabel.setText(mainApp.rb.getString("password"));
        this.mainTitleLabel.setText(mainApp.rb.getString("title"));
    }

    @FXML
    void exitButtonHandler(ActionEvent event) {
        mainApp.displayExitConfirmation();
    }


    @FXML
    void submitButtonHandler(ActionEvent event) {
        User newUser = new User();
        String userName = this.userNameInput.getText();
        String password = this.passWordInput.getText();
        newUser.setUserName(userName);
        newUser.setPassWord(password);
        if (newUser.validateUser()) {
            System.out.println("Input validated, checking against database");
            UserAccess userAccess = new UserAccess();
            if (userAccess.login(userName, password) != null) {
                System.out.println("Logged in");
                this.mainApp.displayMain();
            } else {
                displayLoginError();
            }
        } else {
            displayLoginError();
        }
    }

    private void displayLoginError() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(this.rb.getString("invalid_login_title"));
        alert.setHeaderText(this.rb.getString("invalid_login_title"));
        alert.setContentText(this.rb.getString("invalid_login_text"));
        alert.showAndWait();
    }

}

