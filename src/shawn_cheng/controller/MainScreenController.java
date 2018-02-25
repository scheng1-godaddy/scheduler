package shawn_cheng.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import shawn_cheng.MainApp;

public class MainScreenController {

    MainApp mainApp;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button appointmentsButton;

    @FXML
    private Button customerButton;

    @FXML
    private Button reportsButton;

    @FXML
    private Button exitButton;

    @FXML
    void appointmentsHandler(ActionEvent event) {

    }

    @FXML
    void customerHandler(ActionEvent event) {
        mainApp.displayCustomer();
    }

    @FXML
    void exitHandler(ActionEvent event) {
        mainApp.displayExitConfirmation();
    }

    @FXML
    void reportsHandler(ActionEvent event) {

    }

    public void setMainApp(MainApp mainApp) {this.mainApp = mainApp;}

    @FXML
    void initialize() {
    }
}
