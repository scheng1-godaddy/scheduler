package shawn_cheng.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import shawn_cheng.MainApp;
import shawn_cheng.model.Customer;

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

    public static void displayMainMenu(MainApp mainApp) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("views/MainScreen.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            mainApp.primaryStage.setScene(scene);
            MainScreenController controller = loader.getController();
            controller.setMainApp(mainApp);
            mainApp.primaryStage.setResizable(false);
            mainApp.primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Load login screen
     * @param mainApp
     */
    public static void displayLogin(MainApp mainApp) {
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
            loader.setLocation(MainApp.class.getResource("views/CustomerScreen.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
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

    public static void displayAddModifyCustomerScreen(Customer selectedCustomer) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("views/AddModifyCustomer.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            AddModifyCustomerController controller = loader.getController();
            if (selectedCustomer != null) {
                controller.loadSelectedCustomer(selectedCustomer);
                System.out.println("Customer selected to modify is: " + selectedCustomer);
            }
            controller.setMainApp(mainApp);
            primaryStage.setResizable(false);
            primaryStage.show();
            System.out.println("Displaying Customer Add/Modify Screen");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
