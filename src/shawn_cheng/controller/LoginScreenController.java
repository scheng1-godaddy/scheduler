package shawn_cheng.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.event.ActionEvent;
import shawn_cheng.MainApp;
import shawn_cheng.access.UserAccess;
import shawn_cheng.exceptions.InvalidLoginException;
import shawn_cheng.model.User;

/**
 * FXML Controller class for the main screen
 *
 * @author Shawn Cheng
 */
public class LoginScreenController implements Initializable {

    @FXML
    private TextField userNameInput;

    @FXML
    private TextField passWordInput;

    @FXML
    private Label userNameLabel;

    @FXML
    private Label passwordLabel;

    @FXML
    private Label mainTitleLabel;


    @Override
    public void initialize(URL url, ResourceBundle rb) { }


    public void setText() {
        System.out.println("Resource Bundle is " + MainApp.rb);
        this.userNameLabel.setText(MainApp.rb.getString("username"));
        this.passwordLabel.setText(MainApp.rb.getString("password"));
        this.mainTitleLabel.setText(MainApp.rb.getString("title"));
    }

    @FXML
    void exitButtonHandler(ActionEvent event) { ScreenDisplays.displayExitConfirmation(); }


    @FXML
    void submitButtonHandler(ActionEvent event)  {
        User newUser = new User();
        String userName = this.userNameInput.getText();
        String password = this.passWordInput.getText();
        MainApp.userName = userName;
        newUser.setUserName(userName);
        newUser.setPassWord(password);
        try {
            if (newUser.validateUser()) {
                System.out.println("Input validated, checking against database");
                UserAccess userAccess = new UserAccess();
                if (userAccess.login(userName, password) != null) {
                    System.out.println("Logged in");
                    ScreenDisplays.displayMonthlyCalendarScreen();
                } else {
                    throw new InvalidLoginException("");
                }
            } else {
                throw new InvalidLoginException("");
            }
        } catch (InvalidLoginException e) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle(MainApp.rb.getString("invalid_login_title"));
            alert.setHeaderText(MainApp.rb.getString("invalid_login_title"));
            alert.setContentText(MainApp.rb.getString("invalid_login_text"));
            alert.showAndWait().filter(response -> response == ButtonType.OK);;
        }
    }
}

