package shawn_cheng.exceptions;

import javafx.scene.control.Alert;
import shawn_cheng.MainApp;

public class InvalidLoginException extends Exception {
    public InvalidLoginException(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(MainApp.rb.getString("invalid_login_title"));
        alert.setHeaderText(MainApp.rb.getString("invalid_login_title"));
        alert.setContentText(MainApp.rb.getString("invalid_login_text"));
        alert.showAndWait();
    }
}