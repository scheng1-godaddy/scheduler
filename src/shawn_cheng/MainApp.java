package shawn_cheng;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import shawn_cheng.access.ReminderAccess;
import shawn_cheng.controller.*;
import shawn_cheng.model.Reminder;

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
    public Locale locale;
    public static ResourceBundle rb;
    public static Connection conn;
    public static String userName;
    public static ReminderAccess reminderAccess;
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
        ScreenDisplays.displayLogin();
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
        // Get DB Connection
        dbConnection();

        // Create reminder table access object
        reminderAccess = new ReminderAccess();

        // Create reference to executor service
        ScheduledExecutorService reminderCheckService = null;

        // Need to run reminder in this try block because the finally block will stop the thread after Application object ends
        try {

            // Run factory to create single thread executor
            reminderCheckService = Executors.newSingleThreadScheduledExecutor();

            // Lambda instead of anonymous class for the runnable
            reminderCheckService.scheduleWithFixedDelay(() -> {
                    System.out.println("Running reminder check");

                    // If user isn't logged in, get out.
                    if (userName == null){
                        return;
                    }

                    // Note that the end time is 15 minutes out.
                    ObservableList<Reminder> reminderList = reminderAccess.getReminders(LocalDateTime.now(), LocalDateTime.now().plusMinutes(15), userName);

                    // Everything retrieved should be within 15 minutes, so show display prompts for each
                    for (Reminder reminder : reminderList) {
                        System.out.println("Reminders retrieved are: " + reminder.getAppointment().getTitle());
                        // We need to use runLater because we want this to run on the JavaFX Application thread
                        Platform.runLater(() -> ScreenDisplays.displayReminder(reminder));
                    }
            }, 0, 15, TimeUnit.SECONDS);

            // Now we call the Application launch method
            launch(args);

        } finally {

            // Shutdown the task when application thread closes
            if(reminderCheckService != null) {
                reminderCheckService.shutdown();
            }
        }

    }
}


