package shawn_cheng.exceptions;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 * Exception for invalid input
 *
 * @author Shawn Cheng
 */
public class InvalidInputException extends Exception {
    public InvalidInputException(String msg) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Invalid Input");
        alert.setHeaderText("Invalid Input");
        alert.setContentText(msg);
        alert.showAndWait().filter(response -> response == ButtonType.OK);;
    }
}
