package shawn_cheng.exceptions;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import shawn_cheng.MainApp;

public class InvalidLoginException extends Exception {
    public InvalidLoginException(String msg) {
        super(msg);
    }
}