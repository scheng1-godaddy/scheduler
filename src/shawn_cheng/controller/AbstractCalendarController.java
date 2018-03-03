package shawn_cheng.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import shawn_cheng.MainApp;
import shawn_cheng.model.Appointment;
import shawn_cheng.model.Customer;

import java.net.URL;
import java.util.ResourceBundle;

public class AbstractCalendarController extends AbstractMainController implements Initializable {

    MainApp mainApp;
    public static Appointment selectedAppointment;

    public boolean viewingWeeklyCalendar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    /**
     * Handler to add an appointment.
     * @param event
     */
    @FXML
    void addHandler(ActionEvent event) {
        ScreenDisplays.displayAppointmentScreen(false);
    }

    @FXML
    void modifyHandler(ActionEvent event) {
        ScreenDisplays.displayAppointmentScreen(true);
    }

    @FXML
    void deleteHandler(ActionEvent event) {
        // Delete the appointment.
    }
}
