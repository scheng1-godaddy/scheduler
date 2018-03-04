package shawn_cheng.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

    public static boolean viewingWeeklyCalendar;

    public ObservableList<Appointment> appointmentsList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    /**
     * Handler to add an appointment.
     * @param event
     */
    @FXML
    void addHandler(ActionEvent event) {
        selectedAppointment = null;
        ScreenDisplays.displayAppointmentScreen(false);
    }

    /**
     * Handler to modify selected appointment.
     * @param event
     */
    @FXML
    void modifyHandler(ActionEvent event) {
        System.out.println("The selected customer is: " + selectedAppointment);
        if (selectedAppointment == null) {
            System.out.println("Selected appointment is null!");
            // Throw exception here
        } else {
            ScreenDisplays.displayAppointmentScreen(true);
        }
    }

    @FXML
    void deleteHandler(ActionEvent event) {
        // Delete the appointment.
    }
}
