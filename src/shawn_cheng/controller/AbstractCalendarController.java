package shawn_cheng.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import shawn_cheng.access.AppointmentAccess;
import shawn_cheng.access.ReminderAccess;
import shawn_cheng.exceptions.InvalidSelectionException;
import shawn_cheng.model.Appointment;
import shawn_cheng.model.Reminder;

/**
 * Abstract class for calendar controllers. Used for shared functions of navigation and certain members
 * @author  Shawn Cheng
 */
public abstract class AbstractCalendarController extends AbstractMainController implements Initializable {
    // Currently selected appointment
    public static Appointment selectedAppointment;
    // To track if user is last viewing monthly or weekly calendar
    public static boolean viewingWeeklyCalendar;
    // List of calendar appointments
    protected ObservableList<Appointment> calendarAppointments = FXCollections.observableArrayList();
    // Calendar GridPane
    @FXML protected GridPane calendarGrid;

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
    void modifyHandler(ActionEvent event) throws InvalidSelectionException {
        if (selectedAppointment == null) {
            throw new InvalidSelectionException("No appointment selected to modify");
        } else {
            ScreenDisplays.displayAppointmentScreen(true);
        }
    }

    /**
     * Handler for the delete button
     * @param event
     * @throws InvalidSelectionException
     */
    @FXML
    void deleteHandler(ActionEvent event)  throws InvalidSelectionException{
        if (selectedAppointment == null) {

            throw new InvalidSelectionException("No appointment selected to delete");

        } else {

            // Remove appointment
            AppointmentAccess appointmentAccess = new AppointmentAccess();
            appointmentAccess.removeAppointment(selectedAppointment);

            //Remove reminder
            ReminderAccess reminderAccess = new ReminderAccess();
            reminderAccess.removeReminder(new Reminder(selectedAppointment));
            displayCalendar();
        }
    }
    /**
     * Abstract for display calendar
     */
    public abstract void displayCalendar();

}
