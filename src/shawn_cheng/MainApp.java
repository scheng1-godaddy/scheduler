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

    public static void reminderCheck() {
        System.out.println("Running reminder check");
        if (userName == null){
            return;
        }
        ObservableList<Reminder> reminderList = reminderAccess.getReminders(LocalDateTime.now(), LocalDateTime.now().plusMinutes(20), userName);
        for (Reminder reminder : reminderList) {
            System.out.println("Reminders retrieved are: " + reminder.getAppointment().getTitle());
            Platform.runLater(() -> ScreenDisplays.displayReminder(reminder));
        }
    }

    /**
     * Main method, used to launch the application
     * @param args args
     */
    public static void main(String[] args) {


        dbConnection();
        reminderAccess = new ReminderAccess();
        ScheduledExecutorService reminderCheckService = null;

        try {
            System.out.println("Assign runnable remindersTask");
            Runnable remindersTask = () -> reminderCheck();
            System.out.println("Thread pool");
            reminderCheckService = Executors.newScheduledThreadPool(2);
            //reminderCheckService = Executors.newSingleThreadScheduledExecutor();
            System.out.println("Delay");
            reminderCheckService.scheduleWithFixedDelay(remindersTask, 0, 10, TimeUnit.SECONDS);
            System.out.println("Launch");
            launch(args);
        } finally {
            // Shutdown the task when application thread closes
            if(reminderCheckService != null) {
                reminderCheckService.shutdown();
            }
        }

    }
}


