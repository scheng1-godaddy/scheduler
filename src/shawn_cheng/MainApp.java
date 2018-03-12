package shawn_cheng;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import shawn_cheng.access.ReminderAccess;
import shawn_cheng.controller.*;
import shawn_cheng.model.Reminder;

/**
 * This is the main class scheduling application for WGU course C195.
 * 
 * @author Shawn Cheng
 */
public class MainApp extends Application {
    
    // Primary stage
    public Stage primaryStage;
    // Locale.. uncomment lines in localResolver() to change
    private Locale locale;
    // Resource bundle (English and Spanish)
    public static ResourceBundle rb;
    // Connection to the DB
    private static Connection conn;
    // Keep track of user that's logged in
    public static String userName;
    // Need a reminder access object to retrieve reminders
    public static ReminderAccess reminderAccess;
    // Logger object
    public static Logger logger;
    // File to hold logging information
    private static FileHandler schedulerLogFile;

    /**
     * Resolves locale
     */
    private void localeResolver () {
        // Get the default locale
        this.locale = Locale.getDefault();
        // To test locale of another country uncomment line below
        //this.locale = new Locale("es", "MX");
        System.out.println("Locale is: " +locale);

        // Resource bundle
        this.rb = ResourceBundle.getBundle("loginBundle", this.locale);
    }

    /**
     * Get DB connection
     */
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
        } catch(SQLException e) {
            System.out.println("SQLException: "+e.getMessage());
            System.out.println("SQLState: "+e.getSQLState());
            System.out.println("VendorError: "+e.getErrorCode());
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL Driver not found: " + e.getMessage());
        }
    }

    /**
     * Returns DB connection
     * @return
     */
    public static Connection getDBConnection() {
        return conn;
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


    /**
     * Open or create a logger.
     */
    private static void setupLogging() {
        try {
            logger = Logger.getLogger("SchedulerLog");
            logger.setLevel(Level.INFO);
            schedulerLogFile = new FileHandler("scheduler-log.txt", true);
            SimpleFormatter simpleFormatter = new SimpleFormatter();
            schedulerLogFile.setFormatter(simpleFormatter);
            logger.addHandler(schedulerLogFile);
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

        // Setup logging
        setupLogging();

        // Need to run reminder in this try block because the finally block will stop the thread after Application object ends
        try {

            // Run factory to create single thread executor
            reminderCheckService = Executors.newSingleThreadScheduledExecutor();

            // Lambda instead of anonymous class for the runnable
            reminderCheckService.scheduleWithFixedDelay(() -> {

                    // If user isn't logged in, get out.
                    if (userName == null){
                        return;
                    }

                    // Note that the end time is 15 minutes out.
                    ObservableList<Reminder> reminderList = reminderAccess.getReminders(LocalDateTime.now(), LocalDateTime.now().plusMinutes(15), userName);

                    // Everything retrieved should be within 15 minutes, so show display prompts for each
                    for (Reminder reminder : reminderList) {

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


