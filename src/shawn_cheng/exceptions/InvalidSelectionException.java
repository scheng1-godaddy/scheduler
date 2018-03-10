package shawn_cheng.exceptions;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 * Exception for invalid selection
 * @author Shawn Cheng
 */
public class InvalidSelectionException extends Exception {
    public InvalidSelectionException(String msg) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Invalid Selection");
        alert.setHeaderText("Invalid Selection");
        alert.setContentText(msg);
        alert.showAndWait().filter(response -> response == ButtonType.OK);;
    }
}
