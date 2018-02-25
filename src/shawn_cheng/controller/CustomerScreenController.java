package shawn_cheng.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.event.ActionEvent;
import shawn_cheng.MainApp;
import shawn_cheng.access.CustomerAccess;
import shawn_cheng.model.*;

import java.net.URL;
import java.util.ResourceBundle;

public class CustomerScreenController implements Initializable {

    MainApp mainApp;

    @FXML
    private TableView<Customer> customerTableView;

    @FXML
    private TableColumn<Customer, String> nameColumn;

    @FXML
    private TableColumn<Customer, String> address1Column;

    @FXML
    private TableColumn<Customer, String> address2Column;

    @FXML
    private TableColumn<Customer, String> cityColumn;

    @FXML
    private TableColumn<Customer, String> zipColumn;

    @FXML
    private TableColumn<Customer, String> countryColumn;

    @FXML
    private TableColumn<Customer, String> phoneColumn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("Initializing Customer Table");
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCustomerName()));
        address1Column.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress().getAddress()));
        address2Column.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress().getAddress2()));
        zipColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress().getPostalCode()));
        phoneColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress().getPhone()));
        cityColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress().getCity().getCity()));
        countryColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress().getCity().getCountry().getCountry()));
        loadCustomerTable();
    }

    @FXML
    void addButtonHandler(ActionEvent event) {

    }

    @FXML
    void deleteButtonHandler(ActionEvent event) {

    }

    @FXML
    void backButtonHandler(ActionEvent event) {
        mainApp.displayMain();
    }

    @FXML
    void modifyButtonHandler(ActionEvent event) {

    }

    public void setMainApp(MainApp mainApp) {this.mainApp = mainApp;}

    public void loadCustomerTable() {
        System.out.println("Starting load customer data into tableview");
        CustomerAccess customerAccess = new CustomerAccess();
        customerTableView.setItems(customerAccess.getCustomers());
        System.out.println("Customers retrieved from database");
    }

}
