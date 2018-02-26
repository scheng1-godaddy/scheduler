package shawn_cheng.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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

    }

    @FXML
    void customerHandler(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("views/CustomerScreen.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage = mainApp.primaryStage;
            primaryStage.setScene(scene);
            CustomerScreenController controller = loader.getController();
            controller.setMainApp(mainApp);
            primaryStage.setResizable(false);
            primaryStage.show();
            System.out.println("Displaying Customer screen");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void exitHandler(ActionEvent event) {
        mainApp.displayExitConfirmation();
    }

    @FXML
    void reportsHandler(ActionEvent event) {

    }

    public void setMainApp(MainApp mainApp) {this.mainApp = mainApp;}

    @Override
    public void initialize(URL url, ResourceBundle rb) { }
}
