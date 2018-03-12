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
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Controller for the Customer management screen
 * @author Shawn Cheng
 */
public class ManageCustomerScreenController extends AbstractMainController implements Initializable {

    // TableView and Table Columns
    @FXML private TableView<Customer> customerTableView;
    @FXML private TableColumn<Customer, String> nameColumn;
    @FXML private TableColumn<Customer, String> address1Column;
    @FXML private TableColumn<Customer, String> address2Column;
    @FXML private TableColumn<Customer, String> cityColumn;
    @FXML private TableColumn<Customer, String> zipColumn;
    @FXML private TableColumn<Customer, String> countryColumn;
    @FXML private TableColumn<Customer, String> phoneColumn;

    // Text input fields
    @FXML public TextField nameField;
    @FXML public TextField address1Field;
    @FXML public  TextField address2Field;
    @FXML public  TextField cityField;
    @FXML public  TextField countryField;
    @FXML public  TextField postalField;
    @FXML public  TextField phoneField;

    // Keep track of selected customer
    private Customer selectedCustomer;

    /**
     * Override of initialize
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // Set cell value factory for columns
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCustomerName()));
        address1Column.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress().getAddress()));
        address2Column.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress().getAddress2()));
        zipColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress().getPostalCode()));
        phoneColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress().getPhone()));
        cityColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress().getCity().getCity()));
        countryColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress().getCity().getCountry().getCountry()));

        // Load the table with customer data
        loadCustomerTable();

        // Keep track of customer that user selects
        customerTableView.setOnMouseClicked(event -> {
            selectedCustomer = this.customerTableView.getSelectionModel().getSelectedItem();
        });

        // Let user double click to get to modify screen (or just to view details).
        customerTableView.setRowFactory( tv -> {
            TableRow<Customer> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    this.selectedCustomer = row.getItem();
                    loadSelectedCustomer(selectedCustomer);
                }
            });
            return row;
        });

    }

    /**
     * Handler for the save button
     * @param event
     * @throws InvalidInputException
     * @throws SQLException
     */
    @FXML
    void saveButtonHandler(ActionEvent event) throws InvalidInputException, SQLException {

        // Validate the input
        String errorMsg = Customer.validateInput(this);

        // If errorMsg string has content, then there was issue with input
        if (errorMsg.length() > 0) {

            // Throw exception because of invalid input
            throw new InvalidInputException(errorMsg);

        } else {

            // Input is valid, check if we're adding or modifying
            if (this.selectedCustomer == null) {

                // Its an add, send data to add functions
                Country country = addCountry(this.countryField.getText());
                City city = addCity(this.cityField.getText(), country);
                Address address = addAddress(this.address1Field.getText(),
                        this.address2Field.getText(),
                        this.postalField.getText(),
                        this.phoneField.getText(),
                        city);
                addCustomer(this.nameField.getText(), address);

                // Go back to the customer display screen
                ScreenDisplays.displayCustomerScreen();

            } else {

                // Modifying customer, send data to the modify cuntions
                modifyCountry(selectedCustomer.getAddress().getCity().getCountry());
                modifyCity(selectedCustomer.getAddress().getCity());
                modifyAddress(selectedCustomer.getAddress());
                modifyCustomer(selectedCustomer);

                // Display customer screen
                ScreenDisplays.displayCustomerScreen();
            }
        }
    }

    /**
     * Handler for clear button
     * @param event
     */
    @FXML
    void clearButtonHandler(ActionEvent event) {

        // Clears currently selected customer
        selectedCustomer = null;

        // Clears input fields
        this.nameField.clear();
        this.address1Field.clear();
        this.address2Field.clear();
        this.phoneField.clear();
        this.postalField.clear();
        this.cityField.clear();
        this.countryField.clear();
    }

    /**
     * Handler for the delete button
     * @param event
     * @throws InvalidSelectionException
     */
    @FXML
    void deleteButtonHandler(ActionEvent event) throws InvalidSelectionException {

        // Get the currently selected customer
        Customer selectedCustomer = this.customerTableView.getSelectionModel().getSelectedItem();

        // Check if selected customer was null
        if (selectedCustomer == null) {

            // Throw exception if no selected customer
            throw new InvalidSelectionException("Need a valid selection to delete");

        } else {

            // Send alert to confirm deletion
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

    /**
     * Handler for the modify button
     * @param event
     * @throws InvalidSelectionException
     */
    @FXML
    void modifyButtonHandler(ActionEvent event) throws InvalidSelectionException {

        // Find currently selected customer
        Customer selectedCustomer = this.customerTableView.getSelectionModel().getSelectedItem();

        // Check if selected customer was null
        if (selectedCustomer == null) {

            // Throw exception if no customer selected
            throw new InvalidSelectionException("Need a valid selection to modify");

        } else {

            // Load selected customer into input fields
            loadSelectedCustomer(selectedCustomer);
        }
    }

    /**
     * Load the customer table
     */
    public void loadCustomerTable() {

        // Create the db access object
        CustomerAccess customerAccess = new CustomerAccess();

        // Populate table with customer data
        customerTableView.setItems(customerAccess.getCustomers());
    }

    /**
     * Load selected customer information into the input fields (for modificiation)
     * @param selectedCustomer
     */
    public void loadSelectedCustomer(Customer selectedCustomer) {

        // Find the selected customer and load into input fields
        this.selectedCustomer = selectedCustomer;
        this.nameField.setText(selectedCustomer.getCustomerName());
        this.address1Field.setText(selectedCustomer.getAddress().getAddress());
        this.address2Field.setText(selectedCustomer.getAddress().getAddress2());
        this.phoneField.setText(selectedCustomer.getAddress().getPhone());
        this.postalField.setText(selectedCustomer.getAddress().getPostalCode());
        this.cityField.setText(selectedCustomer.getAddress().getCity().getCity());
        this.countryField.setText(selectedCustomer.getAddress().getCity().getCountry().getCountry());
    }

    /**
     * Add country to database
     * @param countryName
     * @return
     * @throws SQLException
     */
    public Country addCountry (String countryName) throws SQLException {

        // Create country object
        Country country = new Country();
        country.setCountry(countryName);

        // Create access object and add country to the database
        CountryAccess countryAccess = new CountryAccess();
        country.setCountryID(countryAccess.addCountry(country));
        return country;
    }

    /**
     * Modify country method
     * @param country
     */
    public void modifyCountry(Country country) {

        // Check if country was modified before we do anything
        if (!(this.countryField.getText().equals(country.getCountry()))) {

            // Create access object and update country table
            CountryAccess countryAccess = new CountryAccess();
            countryAccess.updateCountry(country, this.countryField.getText());
        }
    }

    /**
     * Add city method
     * @param cityName
     * @param country
     * @return
     * @throws SQLException
     */
    public City addCity (String cityName, Country country) throws SQLException {

        // Create city object
        City city = new City();
        city.setCity(cityName);
        city.setCountry(country);

        // Create access object and add city to the database
        CityAccess cityAccess = new CityAccess();
        int cityID = cityAccess.addCity(city);
        city.setCityID(cityID);
        return city;
    }

    /**
     * Modify city method
     * @param city
     */
    public void modifyCity(City city) {

        // Check if city was modified
        if (!(this.cityField.getText().equals(city.getCity()))) {

            // Save to database
            CityAccess cityAccess = new CityAccess();
            cityAccess.updateCity(city, this.cityField.getText());
        }
    }

    /**
     * Add address method
     * @param address1
     * @param address2
     * @param postalCode
     * @param phone
     * @param city
     * @return
     * @throws SQLException
     */
    public Address addAddress (String address1, String address2, String postalCode, String phone, City city) throws SQLException{

        // Create address object
        Address address = new Address();
        address.setAddress(address1);
        address.setAddress2(address2);
        address.setPostalCode(postalCode);
        address.setPhone(phone);
        address.setCity(city);

        // Create access object and add to the database
        AddressAccess addressAccess = new AddressAccess();
        int addressID = addressAccess.addAddress(address);
        address.setAddressID(addressID);
        return address;
    }

    /**
     * Modify address method
     * @param address
     */
    public void modifyAddress(Address address) {
        // Check if address fields were modified
        if (!(this.address1Field.getText().equals(address.getAddress())) ||
                !(this.address2Field.getText().equals(address.getAddress2())) ||
                !(this.postalField.getText().equals(address.getPostalCode())) ||
                !(this.phoneField.getText().equals(address.getPhone()))) {

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

    /**
     * Add customer method
     * @param customerName
     * @param address
     * @return
     * @throws SQLException
     */
    public Customer addCustomer (String customerName, Address address) throws SQLException {

        // Create customer object
        Customer customer = new Customer();
        customer.setCustomerName(customerName);
        customer.setAddress(address);

        // Create access object and add customer to the database
        CustomerAccess customerAccess = new CustomerAccess();
        int customerID = customerAccess.addCustomer(customer);
        customer.setCustomerID(customerID);
        return customer;
    }

    /**
     * Modify customer method
     * @param customer
     */
    public void modifyCustomer(Customer customer) {

        // Check if customer information was changed
        if (!(this.nameField.getText().equals(customer.getCustomerName()))) {

            // Save to database
            CustomerAccess customerAccess = new CustomerAccess();
            customerAccess.updateCustomer(customer, this.nameField.getText());
        }
    }

}
