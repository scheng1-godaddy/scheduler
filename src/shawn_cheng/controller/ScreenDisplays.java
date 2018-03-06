package shawn_cheng.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import shawn_cheng.MainApp;

import java.io.IOException;

public class ScreenDisplays {

    public static Stage primaryStage;
    public static MainApp mainApp;

    /**
     * Load Main Menu screen
     * @param stage
     */

    public static void setPrimaryStage (Stage stage) {
        primaryStage = stage;
    }

    public static void setMainApp (MainApp mainapp) {
        mainApp = mainapp;
    }

    /**
     * Load login screen
     */
    public static void displayLogin() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("views/LoginScreen.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            mainApp.primaryStage.setScene(scene);
            LoginScreenController controller = loader.getController();
            controller.setMainApp(mainApp);
            controller.setText();
            mainApp.primaryStage.setResizable(false);
            mainApp.primaryStage.show();
            System.out.println("Displaying Login screen");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void displayExitConfirmation() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(MainApp.rb.getString("confirm_title"));
        alert.setHeaderText(MainApp.rb.getString("confirm_title"));
        alert.setContentText(MainApp.rb.getString("confirm_text"));
        alert.showAndWait()
                .filter(userResponse -> userResponse == ButtonType.OK)
                .ifPresent(userResponse -> System.exit(0));
    }

    public static void displayCustomerScreen() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("views/ManageCustomerScreen.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            ManageCustomerScreenController controller = loader.getController();
            controller.setMainApp(mainApp);
            primaryStage.setResizable(false);
            primaryStage.show();
            System.out.println("Displaying Customer screen");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void displayMonthlyCalendarScreen() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("views/CalendarMonthlyScreen.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            CalendarMonthlyScreenController controller = loader.getController();
            controller.setMainApp(mainApp);
            primaryStage.setResizable(false);
            primaryStage.show();
            System.out.println("Displaying Monthly Calendar screen");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void displayWeeklyCalendarScreen() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("views/CalendarWeeklyScreen.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            CalendarWeeklyScreenController controller = loader.getController();
            controller.setMainApp(mainApp);
            primaryStage.setResizable(false);
            primaryStage.show();
            System.out.println("Displaying Weekly Calendar screen");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void displayAppointmentScreen(boolean modify) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("views/ManageAppointmentScreen.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            ManageAppointmentController controller = loader.getController();
            controller.setModify(modify);
            controller.setMainApp(mainApp);
            primaryStage.setResizable(false);
            primaryStage.show();
            System.out.println("Displaying Appointments screen");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
