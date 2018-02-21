package shawn_cheng;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import shawn_cheng.view_controller.*;
import static javafx.application.Application.launch;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
     * Create inventory and add sample data to it
     */
    public MainApp() {

        
    }
    
    /**
     * Start method to set title and create initial layout
     * @param primaryStage 
     */

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Maddy's Scheduling App");
        initMainLayout();
    }

    /**
     * Load main screen 
     */
    public void initMainLayout() {
        try {
            // Load Main layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view_controller/LoginScreen.fxml"));
            mainLayout = (AnchorPane) loader.load();
            LoginScreenController controller = loader.getController();
            // Show the scene containing the main layout.
            Scene scene = new Scene(mainLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
            System.out.println("Displaying main screen");
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
        System.out.println("Testing Git Hub");
    }
}


