package shawn_cheng.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import shawn_cheng.access.AddressAccess;
import shawn_cheng.access.CityAccess;
import shawn_cheng.access.CountryAccess;
import shawn_cheng.access.CustomerAccess;
import shawn_cheng.exceptions.InvalidInputException;
import shawn_cheng.exceptions.InvalidSelectionException;
import shawn_cheng.model.*;
import java.net.URL;
import java.util.ResourceBundle;

public class ManageCustomerScreenController extends AbstractMainController implements Initializable {

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

    private Customer selectedCustomer;

    @FXML
    public TextField nameField;

    @FXML
    public TextField address1Field;

    @FXML
    public  TextField address2Field;

    @FXML
    public  TextField cityField;

    @FXML
    public  TextField countryField;

    @FXML
    public  TextField postalField;

    @FXML
    public  TextField phoneField;

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
        customerTableView.setOnMouseClicked(event -> {
            selectedCustomer = this.customerTableView.getSelectionModel().getSelectedItem();
            System.out.println("Customer clicked on was: " + selectedCustomer.getCustomerName());
        });
    }

    @FXML
    void saveButtonHandler(ActionEvent event) throws InvalidInputException {
        String errorMsg = Customer.validateInput(this);
        System.out.println("ErrorMessage is: " + errorMsg);

        if (errorMsg.length() > 0) {
            throw new InvalidInputException(errorMsg);
        } else {
            if (this.selectedCustomer == null) {
                System.out.println("Adding Customer");
                Country country = addCountry(this.countryField.getText());
                City city = addCity(this.cityField.getText(), country);
                Address address = addAddress(this.address1Field.getText(),
                        this.address2Field.getText(),
                        this.postalField.getText(),
                        this.phoneField.getText(),
                        city);
                Customer customer = addCustomer(this.nameField.getText(), address);
                System.out.println("Customer Added: " + customer);
                ScreenDisplays.displayCustomerScreen();
            } else {
                // Logic for modifying customer
                System.out.println("Modifying customer information");
                modifyCountry(selectedCustomer.getAddress().getCity().getCountry());
                modifyCity(selectedCustomer.getAddress().getCity());
                modifyAddress(selectedCustomer.getAddress());
                modifyCustomer(selectedCustomer);
                ScreenDisplays.displayCustomerScreen();
            }
        }
    }


    @FXML
    void clearButtonHandler(ActionEvent event) {
        selectedCustomer = null;
        this.nameField.clear();
        this.address1Field.clear();
        this.address2Field.clear();
        this.phoneField.clear();
        this.postalField.clear();
        this.cityField.clear();
        this.countryField.clear();
    }

    @FXML
    void deleteButtonHandler(ActionEvent event) throws InvalidSelectionException {
        Customer selectedCustomer = this.customerTableView.getSelectionModel().getSelectedItem();
        if (selectedCustomer == null) {
            throw new InvalidSelectionException("Need a valid selection to delete");
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Confirmation");
            alert.setHeaderText("Delete Confirmation");
            alert.setContentText("Are you sure you want to delete?");
            alert.showAndWait()
                    .filter(userResponse -> userResponse == ButtonType.OK)
                    .ifPresent(userResponse -> {
                        CustomerAccess customerAccess = new CustomerAccess();
                        customerAccess.deleteCustomer(selectedCustomer);
                        loadCustomerTable();
                    });
        }
    }

    @FXML
    void modifyButtonHandler(ActionEvent event) throws InvalidSelectionException {
        Customer selectedCustomer = this.customerTableView.getSelectionModel().getSelectedItem();
        if (selectedCustomer == null) {
            throw new InvalidSelectionException("Need a valid selection to modify");
        } else {
            loadSelectedCustomer(selectedCustomer);
        }
    }


    public void loadCustomerTable() {
        System.out.println("Starting load customer data into tableview");
        CustomerAccess customerAccess = new CustomerAccess();
        customerTableView.setItems(customerAccess.getCustomers());
        System.out.println("Customers retrieved from database");
    }

    public void loadSelectedCustomer(Customer selectedCustomer) {
        this.selectedCustomer = selectedCustomer;
        this.nameField.setText(selectedCustomer.getCustomerName());
        this.address1Field.setText(selectedCustomer.getAddress().getAddress());
        this.address2Field.setText(selectedCustomer.getAddress().getAddress2());
        this.phoneField.setText(selectedCustomer.getAddress().getPhone());
        this.postalField.setText(selectedCustomer.getAddress().getPostalCode());
        this.cityField.setText(selectedCustomer.getAddress().getCity().getCity());
        this.countryField.setText(selectedCustomer.getAddress().getCity().getCountry().getCountry());
    }

    public Country addCountry (String countryName) {
        System.out.println("addCountry called");
        Country country = new Country();
        country.setCountry(countryName);
        CountryAccess countryAccess = new CountryAccess();
        country.setCountryID(countryAccess.addCountry(country));
        return country;
    }

    public void modifyCountry(Country country) {
        if (!(this.countryField.getText().equals(country.getCountry()))) {
            System.out.println("Country value changed");
            // Save to database
            CountryAccess countryAccess = new CountryAccess();
            countryAccess.updateCountry(country, this.countryField.getText());
        }
    }

    public City addCity (String cityName, Country country) {
        System.out.println("addCity called");
        City city = new City();
        city.setCity(cityName);
        city.setCountry(country);
        CityAccess cityAccess = new CityAccess();
        int cityID = cityAccess.addCity(city);
        city.setCityID(cityID);
        return city;
    }

    public void modifyCity(City city) {
        if (!(this.cityField.getText().equals(city.getCity()))) {
            System.out.println("City value changed");
            // Save to database
            CityAccess cityAccess = new CityAccess();
            cityAccess.updateCity(city, this.cityField.getText());
        }
    }

    public Address addAddress (String address1, String address2, String postalCode, String phone, City city) {
        System.out.println("addAddress called");
        Address address = new Address();
        address.setAddress(address1);
        address.setAddress2(address2);
        address.setPostalCode(postalCode);
        address.setPhone(phone);
        address.setCity(city);
        AddressAccess addressAccess = new AddressAccess();
        int addressID = addressAccess.addAddress(address);
        address.setAddressID(addressID);
        return address;
    }

    public void modifyAddress(Address address) {
        if (!(this.address1Field.getText().equals(address.getAddress())) ||
                !(this.address2Field.getText().equals(address.getAddress2())) ||
                !(this.postalField.getText().equals(address.getPostalCode())) ||
                !(this.phoneField.getText().equals(address.getPhone()))) {
            System.out.println("Address value changed");
            // Save to database
            AddressAccess addressAccess = new AddressAccess();
            addressAccess.updateAddress(address,
                    this.address1Field.getText(),
                    this.address2Field.getText(),
                    this.postalField.getText(),
                    this.phoneField.getText()
            );
        }
    }

    public Customer addCustomer (String customerName, Address address) {
        System.out.println("addCustomer Called");
        Customer customer = new Customer();
        customer.setCustomerName(customerName);
        customer.setAddress(address);
        CustomerAccess customerAccess = new CustomerAccess();
        int customerID = customerAccess.addCustomer(customer);
        customer.setCustomerID(customerID);
        return customer;
    }

    public void modifyCustomer(Customer customer) {
        if (!(this.nameField.getText().equals(customer.getCustomerName()))) {
            System.out.println("Customer name value changed");
            // Save to database
            CustomerAccess customerAccess = new CustomerAccess();
            customerAccess.updateCustomer(customer, this.nameField.getText());
        }
    }

}
