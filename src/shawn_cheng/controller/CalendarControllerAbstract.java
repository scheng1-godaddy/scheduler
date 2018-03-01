package shawn_cheng.controller;

import javafx.fxml.Initializable;
import shawn_cheng.MainApp;
import shawn_cheng.model.Appointment;
import shawn_cheng.model.Customer;

import java.net.URL;
import java.util.ResourceBundle;

public class CalendarControllerAbstract implements Initializable {

    MainApp mainApp;
    public static Appointment selectedAppointment;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

}
