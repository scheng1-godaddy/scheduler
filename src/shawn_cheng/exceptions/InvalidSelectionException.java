package shawn_cheng.exceptions;

import javafx.scene.control.Alert;

public class InvalidSelectionException extends Exception {
    public InvalidSelectionException(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid Selection");
        alert.setHeaderText("Invalid Selection");
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
