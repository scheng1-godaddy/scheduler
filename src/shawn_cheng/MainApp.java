package shawn_cheng;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.application.Application;
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
    public Stage primaryStage;
    private AnchorPane mainLayout;
    public Locale locale;
    public static ResourceBundle rb;
    public static Connection conn;
    public static String userName;
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
        ScreenDisplays.setPrimaryStage(this.primaryStage);
        ScreenDisplays.setMainApp(this);
        localeResolver();
        ScreenDisplays.displayLogin(this);
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

    public static Connection getDBConnection() {
        return conn;
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
     * Main method, used to launch the application
     * @param args args
     */
    public static void main(String[] args) {
        dbConnection();
        launch(args);

    }
}


