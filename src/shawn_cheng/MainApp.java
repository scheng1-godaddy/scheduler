package shawn_cheng;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
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
    public Locale locale;
    public ResourceBundle rb;
    public static Connection conn;
    /**
     * Constructor
     */
    public MainApp() {

    }
    
    /**
     * Start method to set title and create initial layout
     * @param primaryStage The Primary Stage
     */
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Maddy's Scheduling App");
        localeResolver();
        displayLogin();
    }

    private static void dbConnection() {
        String driver = "com.mysql.jdbc.Driver";
        String db = "U047Jn";
        String url = "jdbc:mysql://52.206.157.109/" + db;
        String user = "U047Jn";
        String pass = "53688170277";

        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, pass);
            System.out.println("Connected to Database");
        } catch(ClassNotFoundException | SQLException ex){
            System.out.println(ex.getMessage());
        }
    }


    private void localeResolver () {
        // Get the default locale
        this.locale = Locale.getDefault();
        // To test locale of another country uncomment line below
        // this.locale = new Locale("es", "MX");
        System.out.println("Locale is: " +locale);

        // Resource bundle
        this.rb = ResourceBundle.getBundle("loginBundle", this.locale);
    }

    /**
     * Load login screen
     */
    private void displayLogin() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("views/LoginScreen.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            LoginScreenController controller = loader.getController();
            controller.setMainApp(this);
            controller.setText();
            primaryStage.setResizable(false);
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
            MainScreenController controller = loader.getController();
            controller.setMainApp(this);
            primaryStage.setResizable(false);
            primaryStage.show();
            System.out.println("Displaying Main screen");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void displayCustomer() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("views/CustomerScreen.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            CustomerScreenController controller = loader.getController();
            controller.setMainApp(this);
            primaryStage.setResizable(false);
            primaryStage.show();
            System.out.println("Displaying Customer screen");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void displayExitConfirmation() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(this.rb.getString("confirm_title"));
        alert.setHeaderText(this.rb.getString("confirm_title"));
        alert.setContentText(this.rb.getString("confirm_text"));
        alert.showAndWait()
                .filter(userResponse -> userResponse == ButtonType.OK)
                .ifPresent(userResponse -> System.exit(0));
    }


    /**
     * Main method, used to launch the application
     * @param args args
     */
    public static void main(String[] args) {
        dbConnection();
        launch(args);

    }
}


