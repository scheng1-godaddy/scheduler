package shawn_cheng.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import shawn_cheng.access.AppointmentAccess;
import shawn_cheng.access.UserAccess;
import shawn_cheng.model.Appointment;
import shawn_cheng.model.User;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ResourceBundle;

/**
 * Controller for the Consultant Schedule Reports screen.
 *
 * @author Shawn Cheng
 */
public class ReportsConsultantScheduleController extends AbstractReportController {
    @FXML
    private TableColumn<Appointment, String> columnStartTime;

    @FXML
    private TableColumn<Appointment, String> columnEndTime;

    @FXML
    private TableColumn<Appointment, String> columnCustomer;

    @FXML
    private TableColumn<Appointment, String> columnTitle;

    @FXML
    protected TableView<Appointment> tableView;

    @FXML
    protected ComboBox<User> dropDown;

    private User selectedUser;

    private final DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);

    /**
     * Initial items to run when the page screen first loads
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // Populate drop down menu with Consultant names
        populateDropDowns();

        // Set cell value factory for columns
        columnStartTime.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStartDateTime().format(dateTimeFormat)));
        columnEndTime.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEndDateTime().format(dateTimeFormat)));
        columnCustomer.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCustomer().getCustomerName()));
        columnTitle.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
    }

    /**
     * Handler for the Run Report button
     * @param event
     */
    @Override @FXML
    public void HandlerRunReport(ActionEvent event) {

        // Get what the currently selected consultant/user is
        selectedUser = this.dropDown.getValue();

        // Want to get appointments from today to one year from now
        LocalDateTime startDateTime = LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT);
        LocalDateTime endDateTime = LocalDateTime.of(LocalDate.now().plusYears(1), LocalTime.MIDNIGHT);

        // Retrieve appointments
        AppointmentAccess appointmentAccess = new AppointmentAccess();
        ObservableList<Appointment> reportResultList = appointmentAccess.getAppointmentsSubsetByUser(startDateTime, endDateTime, selectedUser.getUserName());

        //Set appointments to the table
        tableView.setItems(reportResultList);
    }

    /**
     * Method to generate list that will populate the drop down menu
     */
    public void populateDropDowns() {

        // Get a list of all users
        UserAccess userAccess = new UserAccess();
        ObservableList<User> allUsers = userAccess.getAllUsers();
        this.dropDown.setItems(allUsers);
    }

}
