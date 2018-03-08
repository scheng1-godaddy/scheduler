package shawn_cheng.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import shawn_cheng.MainApp;
import shawn_cheng.access.AppointmentAccess;
import shawn_cheng.access.ReminderAccess;
import shawn_cheng.exceptions.InvalidSelectionException;
import shawn_cheng.model.Appointment;
import shawn_cheng.model.Customer;
import shawn_cheng.model.Reminder;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public abstract class AbstractCalendarController extends AbstractMainController implements Initializable {

    public static Appointment selectedAppointment;

    public static boolean viewingWeeklyCalendar;

    @FXML
    protected GridPane calendarGrid;

    protected YearMonth selectedMonth;

    protected ObservableList<Appointment> calendarAppointments = FXCollections.observableArrayList();

    /**
     * Override of initialize
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.selectedMonth = YearMonth.now();
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
    void modifyHandler(ActionEvent event) throws InvalidSelectionException {
        System.out.println("The selected customer is: " + selectedAppointment);
        if (selectedAppointment == null) {
            System.out.println("Selected appointment is null!");
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

        System.out.println("The selected customer is: " + selectedAppointment);
        if (selectedAppointment == null) {

            System.out.println("Selected appointment is null!");
            throw new InvalidSelectionException("No appointment selected to delete");

        } else {

            // Remove appointment
            AppointmentAccess appointmentAccess = new AppointmentAccess();
            appointmentAccess.removeAppointment(selectedAppointment);
            System.out.println("Appointment " + selectedAppointment.getTitle() + " Deleted");
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


    /**
     * Abstract for
     * @param currentDate
     * @return
     */
    /*
    public abstract BorderPane getDailyPane (LocalDate currentDate);
    */
}
