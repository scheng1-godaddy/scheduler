package shawn_cheng.exceptions;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class InvalidSelectionException extends Exception {
    public InvalidSelectionException(String msg) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Invalid Selection");
        alert.setHeaderText("Invalid Selection");
        alert.setContentText(msg);
        alert.showAndWait().filter(response -> response == ButtonType.OK);;
    }
}
