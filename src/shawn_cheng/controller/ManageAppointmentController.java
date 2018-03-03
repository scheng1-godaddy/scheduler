package shawn_cheng.controller;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import shawn_cheng.MainApp;
import shawn_cheng.access.CustomerAccess;
import shawn_cheng.model.Appointment;
import shawn_cheng.model.Customer;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ResourceBundle;

public class ManageAppointmentController extends AbstractMainController implements Initializable {

    @FXML
    private ComboBox<Customer> customerField;

    @FXML
    private TextField contactField;

    @FXML
    private TextField titleField;

    @FXML
    private TextField locationField;

    @FXML
    private ComboBox<String> descriptionField;

    @FXML
    private TextField urlField;

    @FXML
    private ComboBox<String> startTimeField;

    @FXML
    private ComboBox<String> endTimeField;

    @FXML
    private DatePicker dateField;

    MainApp mainApp;

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

        if (modify) {
            // Its a modify, populate fields with selected appointment
        } else {
            // Not a modify, populate with default values
            // Set current date to the date field.
            this.dateField.setValue(LocalDate.now());
        }
    }

    @FXML
    void cancelButtonHandler(ActionEvent event) {
        //Bring them back to the appointments calendar
        ScreenDisplays.displayMonthlyCalendarScreen();
    }


    @FXML
    void saveButtonHandler(ActionEvent event) {

    }

    public void setMainApp(MainApp mainApp) {this.mainApp = mainApp;}

    public void setModify(boolean modify) {this.modify = modify;}

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
