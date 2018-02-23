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

    private MainApp mainapp;

    Locale locale;
    ResourceBundle rb;

    public void setMainApp(MainApp mainapp) { this.mainapp = mainapp; }

    public void initialize(URL url, ResourceBundle rb) {
        // Get the default locale
        this.locale = Locale.getDefault();
        // To test locale of another country uncomment line below
        // this.locale = new Locale("es", "MX");
        System.out.println("Locale is: " +locale);

        // Resource bundle
        this.rb = ResourceBundle.getBundle("loginBundle", this.locale);

        // Set text for labels
        this.userNameLabel.setText(this.rb.getString("username"));
        this.passwordLabel.setText(this.rb.getString("password"));
        this.mainTitleLabel.setText(this.rb.getString("title"));
    }

    @FXML
    void exitButtonHandler(ActionEvent event) {
        // Display popup window to confirm exit
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(this.rb.getString("confirm_title"));
        alert.setHeaderText(this.rb.getString("confirm_title"));
        alert.setContentText(this.rb.getString("confirm_text"));
        alert.showAndWait()
                .filter(userResponse -> userResponse == ButtonType.OK)
                .ifPresent(userResponse -> System.exit(0));
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
                this.mainapp.displayMain();
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

