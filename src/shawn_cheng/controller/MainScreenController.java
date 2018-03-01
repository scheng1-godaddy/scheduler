package shawn_cheng.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import shawn_cheng.MainApp;

public class MainScreenController implements Initializable {

    MainApp mainApp;
    public Stage primaryStage;

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
        ScreenDisplays.displayAppointmentsScreen();
    }

    @FXML
    void customerHandler(ActionEvent event) {
        ScreenDisplays.displayCustomerScreen();
    }

    @FXML
    void exitHandler(ActionEvent event) {
        ScreenDisplays.displayExitConfirmation();
    }

    @FXML
    void reportsHandler(ActionEvent event) {

    }

    public void setMainApp(MainApp mainApp) {this.mainApp = mainApp;}

    @Override
    public void initialize(URL url, ResourceBundle rb) { }

}
