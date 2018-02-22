package shawn_cheng;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import shawn_cheng.controller.*;

/**
 * This is the main class for the inventory app. 
 * 
 * @author Shawn Cheng
 */

public class MainApp extends Application {
    
    /**
     * Instance Variables 
     */
    private Stage primaryStage;
    private AnchorPane mainLayout;
    
    /**
     * Constructor
     */
    public MainApp() { }
    
    /**
     * Start method to set title and create initial layout
     * @param primaryStage 
     */
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Maddy's Scheduling App");
        displayLogin();
    }

    /**
     * Load login screen
     */
    public void displayLogin() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("views/LoginScreen.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            LoginScreenController controller = loader.getController();
            controller.setMainApp(this);
            primaryStage.show();
            System.out.println("Displaying Login screen");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Load Main Menu screen
     */
    public void displayMain() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("views/MainScreen.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            // LoginScreenController controller = loader.getController();
            // Might want to give a reference back to this object from controller.
            primaryStage.show();
            System.out.println("Displaying Main screen");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Main method, used to launch the application
     * @param args 
     */
    public static void main(String[] args) {
        launch(args);
    }
}


