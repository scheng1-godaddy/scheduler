package shawn_cheng.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import shawn_cheng.access.AppointmentAccess;
import shawn_cheng.access.CustomerAccess;
import shawn_cheng.access.UserAccess;
import shawn_cheng.model.Appointment;
import shawn_cheng.model.Customer;
import shawn_cheng.model.User;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ResourceBundle;

/**
 * Appointment history for each customer
 */
public class ReportsHistoryCustomerController extends AbstractReportController {

    // Table columns
    @FXML private TableColumn<Appointment, String> columnStart;
    @FXML private TableColumn<Appointment, String> columnEnd;
    @FXML private TableColumn<Appointment, String> columnTitle;
    @FXML private TableColumn<Appointment, String> columnType;

    // TableView
    @FXML protected TableView<Appointment> tableView;

    // Drop down
    @FXML protected ComboBox<Customer> dropDown;

    // Keep track of selected user
    private Customer selectedCustomer;

    // Format of date time
    private final DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);

    /**
     * Override for initialize
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // Populate drop down menu with Consultant names
        populateDropDowns();

        // Set cell value factory for columns
        columnStart.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStartDateTime().format(dateTimeFormat)));
        columnEnd.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEndDateTime().format(dateTimeFormat)));
        columnTitle.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
        columnType.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));
    }


    /**
     * Method to generate list that will populate the drop down menu
     */
    public void populateDropDowns() {

        // Get a list of all users
        CustomerAccess customerAccess = new CustomerAccess();
        ObservableList<Customer> allCustomers = customerAccess.getCustomers();
        this.dropDown.setItems(allCustomers);
    }
    /**
     * Handler for run report button
     * @param event
     */
    @Override @FXML
    public void HandlerRunReport(ActionEvent event) {
        // Get what the currently selected consultant/user is
        selectedCustomer = this.dropDown.getValue();

        // Retrieve appointments
        AppointmentAccess appointmentAccess = new AppointmentAccess();
        ObservableList<Appointment> reportResultList = appointmentAccess.getAllAppointmentsByCustomer(selectedCustomer.getCustomerID());

        //Set appointments to the table
        tableView.setItems(reportResultList);
    }

}
