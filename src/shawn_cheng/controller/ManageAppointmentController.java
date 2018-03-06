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
import shawn_cheng.exceptions.InvalidInputException;
import shawn_cheng.model.Appointment;
import shawn_cheng.model.Customer;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ResourceBundle;

public class ManageAppointmentController extends AbstractMainController implements Initializable {

    @FXML
    public ComboBox<Customer> customerField;

    @FXML
    public TextField contactField;

    @FXML
    public TextField titleField;

    @FXML
    public TextField locationField;

    @FXML
    public ComboBox<String> descriptionField;

    @FXML
    public TextField urlField;

    @FXML
    public ComboBox<String> startTimeField;

    @FXML
    public ComboBox<String> endTimeField;

    @FXML
    public DatePicker dateField;

    private ObservableList<String> appointmentTimes;

    private ObservableList<String> appointmentTypes;

    private ObservableList<Customer> customerList;

    private DateTimeFormatter apptTimeFormat = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);

    private Appointment selectedAppointment;

    private boolean modify;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.selectedAppointment = AbstractCalendarController.selectedAppointment;

        System.out.println("The selected appointment was: " + selectedAppointment);

        // Initialize list items for drop down menus
        createDropDownList();

        // Populate the drop down menus
        this.startTimeField.setItems(this.appointmentTimes);
        this.endTimeField.setItems(this.appointmentTimes);
        this.descriptionField.setItems(this.appointmentTypes);
        this.customerField.setItems(this.customerList);


        // Set current date to the date field.
        this.dateField.setValue(LocalDate.now());
    }

    @FXML
    void cancelButtonHandler(ActionEvent event) {
        //Bring them back to the appointments calendar
        if (!AbstractCalendarController.viewingWeeklyCalendar) {
            ScreenDisplays.displayMonthlyCalendarScreen();
        } else {
            ScreenDisplays.displayWeeklyCalendarScreen();
        }

    }


    @FXML
    void saveButtonHandler(ActionEvent event) throws InvalidInputException {
        String errorMsg = Appointment.validateInput(this);
        if (errorMsg.length() > 0) {
            // Errors detected, throw exception
            throw new InvalidInputException(errorMsg);
        }
        errorMsg = Appointment.validateTimes(this);

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

            // If modifying an appointment, then update. If not, then add.
            if (modify) {
                newAppt.setAppointmentId(selectedAppointment.getAppointmentId());
                appointmentAccess.updateAppointment(newAppt);
            } else {
                int apptId = appointmentAccess.addAppointment(newAppt);
                newAppt.setAppointmentId(apptId);
            }


            if (AbstractCalendarController.viewingWeeklyCalendar) {
                ScreenDisplays.displayWeeklyCalendarScreen();
            } else {
                ScreenDisplays.displayMonthlyCalendarScreen();
            }
        }
    }

    public void setModify(boolean modify) {
        this.modify = modify;
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

    // Create list of appointment times for the drop down
    private void createDropDownList(){

        // Create the appointment times
        this.appointmentTimes = FXCollections.observableArrayList();
        LocalTime apptHours = LocalTime.MIDNIGHT.plusHours(9);
        for (int x = 0; x < 16; x++) {
            appointmentTimes.add(apptHours.format(apptTimeFormat));
            apptHours = apptHours.plusMinutes(30);
        }

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
