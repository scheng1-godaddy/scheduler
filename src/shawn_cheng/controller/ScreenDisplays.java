package shawn_cheng.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import shawn_cheng.MainApp;
import shawn_cheng.model.Appointment;
import shawn_cheng.model.Reminder;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Holds static methods to display various screens.
 */
public class ScreenDisplays {

    // Pointer to primary stage
    public static Stage primaryStage;

    // Pointer to the main application
    public static MainApp mainApp;

    /**
     * Sets primary stage
     * @param stage
     */
    public static void setPrimaryStage (Stage stage) {
        primaryStage = stage;
    }

    /**
     * Sets pointer to main application
     * @param mainapp
     */
    public static void setMainApp (MainApp mainapp) {
        mainApp = mainapp;
    }

    /**
     * Display login screen
     */
    public static void displayLogin() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("views/LoginScreen.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            mainApp.primaryStage.setScene(scene);
            mainApp.primaryStage.setResizable(false);
            mainApp.primaryStage.show();
            System.out.println("Displaying Login screen");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Display exit confirmation
     */
    public static void displayExitConfirmation() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(MainApp.rb.getString("confirm_title"));
        alert.setHeaderText(MainApp.rb.getString("confirm_title"));
        alert.setContentText(MainApp.rb.getString("confirm_text"));
        alert.showAndWait()
                .filter(userResponse -> userResponse == ButtonType.OK)
                .ifPresent(userResponse -> System.exit(0));
    }

    /**
     * Display manage customer screen
     */
    public static void displayCustomerScreen() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("views/ManageCustomerScreen.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
            System.out.println("Displaying Customer screen");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Display monthly calendar screen
     */
    public static void displayMonthlyCalendarScreen() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("views/CalendarMonthlyScreen.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
            System.out.println("Displaying Monthly Calendar screen");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Display weekly calendar screen
     */
    public static void displayWeeklyCalendarScreen() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("views/CalendarWeeklyScreen.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
            System.out.println("Displaying Weekly Calendar screen");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Display manage appointment screen
     * @param modify
     */
    public static void displayAppointmentScreen(boolean modify) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("views/ManageAppointmentScreen.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            ManageAppointmentController controller = loader.getController();
            controller.setModify(modify);
            primaryStage.setResizable(false);
            primaryStage.show();
            System.out.println("Displaying Appointments screen");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Display Report screen for Appointment type count per month
     */
    public static void displayReportApptTypeScreen() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("views/ReportsApptTypeScreen.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
            System.out.println("Displaying Reports: Appt Type screen");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Display Report screen for Consultant Schedule
     */
    public static void displayReportConsultantScheduleScreen() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("views/ReportsConsultantSchedule.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
            System.out.println("Displaying Reports: Consultant Schedule screen");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Display Report screen for all past appointments for chosen customer
     */
    public static void displayReportHistoryPerCustomerScreen() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("views/ReportsHistoryPerCustomer.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
            System.out.println("Displaying Reports: History Per Customer screen");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Displays reminder prompt
     * @param reminder
     */
    public static void displayReminder(Reminder reminder) {
        Appointment appointment = reminder.getAppointment();
        String apptTitle = appointment.getTitle();
        LocalDateTime apptTime = appointment.getStartDateTime();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Appointment Reminder");
        alert.setHeaderText("Appointment Reminder");
        alert.setContentText("Appointment " + apptTitle + " is starting at  " + apptTime.toString());
        alert.showAndWait()
                .filter(response -> response == ButtonType.OK)
                .ifPresent(response -> {
                    MainApp.reminderAccess.removeReminder(reminder);
                });
    }
}
