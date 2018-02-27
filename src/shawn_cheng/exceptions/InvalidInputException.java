package shawn_cheng.exceptions;

import javafx.scene.control.Alert;

public class InvalidInputException extends Exception {
    public InvalidInputException(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid Input");
        alert.setHeaderText("Invalid Input");
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
