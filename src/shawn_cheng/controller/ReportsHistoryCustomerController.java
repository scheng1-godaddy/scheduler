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
import shawn_cheng.model.Appointment;
import shawn_cheng.model.Customer;
import shawn_cheng.model.User;

import java.net.URL;
import java.util.ResourceBundle;

public class ReportsHistoryCustomerController extends AbstractReportController {
    @FXML
    private TableColumn<Appointment, String> columnStartTime;

    @FXML
    private TableColumn<Appointment, String> columnEndTime;

    @FXML
    private TableColumn<Appointment, String> columnTitle;

    @FXML
    private TableColumn<Appointment, String> columnType;

    @FXML
    private ComboBox<Customer> dropDown;

    @FXML
    private TableView<Appointment> tableView;

    private Customer selectedCustomer;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Populate the drop down
        populateDropDown();
        columnStartTime.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStartDateTime().format(dateTimeFormat)));
        columnEndTime.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEndDateTime().format(dateTimeFormat)));
        columnType.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));
        columnTitle.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
    }

    @Override @FXML
    public void HandlerRunReport(ActionEvent event) {

        // Find the selected customer
        this.selectedCustomer = this.dropDown.getValue();

        // Retrieve all appointments based on selected customer
        AppointmentAccess appointmentAccess = new AppointmentAccess();
        ObservableList<Appointment> reportResultList = appointmentAccess.getAppointmentCustomerHistory(selectedCustomer);
        tableView.setItems(reportResultList);
    }

    public void populateDropDown() {
        CustomerAccess customerAccess = new CustomerAccess();
        ObservableList<Customer> customerList = customerAccess.getCustomers();
        this.dropDown.setItems(customerList);
    }

}
