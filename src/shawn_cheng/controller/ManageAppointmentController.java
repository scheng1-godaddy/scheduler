package shawn_cheng.controller;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import shawn_cheng.access.AppointmentAccess;
import shawn_cheng.access.CustomerAccess;
import shawn_cheng.access.ReminderAccess;
import shawn_cheng.exceptions.InvalidInputException;
import shawn_cheng.model.Appointment;
import shawn_cheng.model.Customer;
import shawn_cheng.model.Reminder;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ResourceBundle;

/**
 * Controller for manage appointment screen
 * @author Shawn Cheng
 */
public class ManageAppointmentController extends AbstractMainController implements Initializable {
    // FXML fields
    @FXML public ComboBox<Customer> customerField;
    @FXML public TextField contactField;
    @FXML public TextField titleField;
    @FXML public TextField locationField;
    @FXML public ComboBox<String> descriptionField;
    @FXML public TextField urlField;
    @FXML public ComboBox<String> startTimeField;
    @FXML public ComboBox<String> endTimeField;
    @FXML public DatePicker dateField;
    // Start times for drop down list
    private ObservableList<String> appointmentStartTimes;
    // End times for drop down list
    private ObservableList<String> appointmentEndTimes;
    // Appointment Types for drop down list
    private ObservableList<String> appointmentTypes;
    // List for customers drop down
    private ObservableList<Customer> customerList;
    // Format for appointment times drop down
    private DateTimeFormatter apptTimeFormat = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);
    // Keeps track of the appointment that was selected
    private Appointment selectedAppointment;
    // Keeps track if user is modifying appointment
    private boolean modify;

    /**
     * Override for initialize
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // Get the selected appointment
        this.selectedAppointment = AbstractCalendarController.selectedAppointment;

        // Initialize list items for drop down menus
        createDropDownList();

        // Populate the drop down menus
        this.startTimeField.setItems(this.appointmentStartTimes);
        this.endTimeField.setItems(this.appointmentEndTimes);
        this.descriptionField.setItems(this.appointmentTypes);
        this.customerField.setItems(this.customerList);

        // Set current date to the date field.
        this.dateField.setValue(LocalDate.now());
    }

    /**
     * Handler for the cancel button
     * @param event
     */
    @FXML
    void cancelButtonHandler(ActionEvent event) {

        //Bring them back to the appointments calendar
        if (!AbstractCalendarController.viewingWeeklyCalendar) {
            ScreenDisplays.displayMonthlyCalendarScreen();
        } else {
            ScreenDisplays.displayWeeklyCalendarScreen();
        }
    }

    /**
     * Handler for the save button
     * @param event
     * @throws InvalidInputException
     * @throws SQLException
     */
    @FXML
    void saveButtonHandler(ActionEvent event) throws InvalidInputException, SQLException {

        // Validate the information
        String errorMsg = Appointment.validateInput(this);

        // If error msg string has any content, there was an issue with the input
        if (errorMsg.length() > 0) {

            // Errors detected, throw exception
            throw new InvalidInputException(errorMsg);
        }

        // Validate the appointment times
        errorMsg = Appointment.validateTimes(this, selectedAppointment);

        // If any content, then there was an issue with the appointment time
        if (errorMsg.length() > 0) {

            // Errors detected, throw exception
            throw new InvalidInputException(errorMsg);

        } else {

            // No errors, proceed with creating appointment object
            System.out.println("Appointment data validated");

            // Create the proper time datetime objects from String values
            LocalDateTime startDateTime = LocalDateTime.of(this.dateField.getValue(), LocalTime.parse(this.startTimeField.getValue(), apptTimeFormat));
            LocalDateTime endDateTime = LocalDateTime.of(this.dateField.getValue(), LocalTime.parse(this.endTimeField.getValue(), apptTimeFormat));

            // Building appointment object now
            Appointment newAppt = new Appointment();
            newAppt.setTitle(this.titleField.getText());
            newAppt.setDescription(this.descriptionField.getValue());
            newAppt.setCustomer(this.customerField.getValue());
            newAppt.setContact(this.contactField.getText());
            newAppt.setLocation(this.locationField.getText());
            newAppt.setUrl(this.urlField.getText());
            newAppt.setStartDateTime(startDateTime);
            newAppt.setEndDateTime(endDateTime);

            // Create DB access object for appointment table
            AppointmentAccess appointmentAccess = new AppointmentAccess();

            // Create DB access object for reminder table
            ReminderAccess reminderAccess = new ReminderAccess();

            // If modifying an appointment, then update. If not, then add.
            if (modify) {

                // Set ID if its a modify
                newAppt.setAppointmentId(selectedAppointment.getAppointmentId());

                // Update database
                appointmentAccess.updateAppointment(newAppt);

                // Build reminder object and update reminder table
                Reminder reminder = new Reminder(newAppt);

                // We need to find out what the existing reminder ID is
                int reminderId = reminderAccess.getExistingReminderId(selectedAppointment.getAppointmentId());

                // Check if there's existing reminder
                if (reminderId > 0) {

                    // Only update if we actually found an ID
                    reminder.setReminderId(reminderId);
                    reminderAccess.updateReminder(reminder);

                } else {

                    // Couldn't find an ID
                    System.out.println("Could not find existing reminder ID");

                }

            } else {

                // Its an add, so add appointment and get the ID back
                int apptId = appointmentAccess.addAppointment(newAppt);
                newAppt.setAppointmentId(apptId);

                // Build reminder object and insert into reminder table
                Reminder reminder = new Reminder(newAppt);
                reminderAccess.addReminder(reminder);

            }

            // Check to see which view the user was looking at
            if (AbstractCalendarController.viewingWeeklyCalendar) {

                // Display weekly calendar
                ScreenDisplays.displayWeeklyCalendarScreen();

            } else {

                // Display monthly calendar
                ScreenDisplays.displayMonthlyCalendarScreen();
            }
        }
    }

    /**
     * Sets the modify tracking value and populates input fields
     * @param modify
     */
    public void setModify(boolean modify) {

        // Set modify tracking
        this.modify = modify;

        // If modifying, populate the text fields
        if (modify) {
            this.titleField.setText(selectedAppointment.getTitle());
            this.customerField.getSelectionModel().select(selectedAppointment.getCustomer());
            System.out.println("Customer is: " + selectedAppointment.getCustomer());
            this.descriptionField.setValue(selectedAppointment.getDescription());
            this.contactField.setText(selectedAppointment.getContact());
            this.locationField.setText(selectedAppointment.getLocation());
            this.urlField.setText(selectedAppointment.getUrl());
            this.dateField.setValue(selectedAppointment.getStartDateTime().toLocalDate());
            this.startTimeField.getSelectionModel().select(selectedAppointment.getStartDateTime().format(apptTimeFormat));
            this.endTimeField.getSelectionModel().select(selectedAppointment.getEndDateTime().format(apptTimeFormat));

        }
    }

    /**
     * Creates the list values that will be used to populate drop down menus
     */
    private void createDropDownList(){

        // Create the appointment times lists
        this.appointmentStartTimes = FXCollections.observableArrayList();
        this.appointmentEndTimes = FXCollections.observableArrayList();

        // Get starting time of 9 am
        LocalTime apptHours = LocalTime.MIDNIGHT.plusHours(9);

        // For loop to increment time and populate the rest of the business hours for starting time
        for (int x = 0; x < 16; x++) {
            appointmentStartTimes.add(apptHours.format(apptTimeFormat));
            apptHours = apptHours.plusMinutes(30);
        }

        // Populate end times with same list from start time but add 30 mins to the last start time (can end at 5)
        this.appointmentEndTimes.addAll(this.appointmentStartTimes);
        this.appointmentEndTimes.add(apptHours.format(apptTimeFormat));

        // Create the appointment types/descriptions
        // Since we need to run a report for the different types I'm hardcoding to a specific list
        this.appointmentTypes = FXCollections.observableArrayList();
        this.appointmentTypes.add("Introduction");
        this.appointmentTypes.add("Consultation");
        this.appointmentTypes.add("Complaint");
        this.appointmentTypes.add("Close out");
        this.appointmentTypes.add("Feedback");

        // Retrieve list of customers
        CustomerAccess customerAccess = new CustomerAccess();
        this.customerList = customerAccess.getCustomers();
    }
}
