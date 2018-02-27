package shawn_cheng.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import shawn_cheng.MainApp;
import shawn_cheng.access.AddressAccess;
import shawn_cheng.access.CityAccess;
import shawn_cheng.access.CountryAccess;
import shawn_cheng.access.CustomerAccess;
import shawn_cheng.exceptions.InvalidInputException;
import shawn_cheng.model.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class AddModifyCustomerController implements Initializable {

    private MainApp mainApp;

    private Customer selectedCustomer;

    public Stage primaryStage;

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

    }

    @FXML
    void cancelButtonHandler(ActionEvent event) {
        displayMainCustomerScreen();
    }

    @FXML
    void saveButtonHandler(ActionEvent event) throws InvalidInputException {

        String errorMsg = Customer.validateInput(this);
        System.out.println("ErrorMessage is: " + errorMsg);

        if (errorMsg.length() > 0) {
            throw new InvalidInputException(errorMsg);
        } else {
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
            displayMainCustomerScreen();
        }
    }

    public void setMainApp(MainApp mainApp) {this.mainApp = mainApp;}

    //public void setSelectedCustomer(Customer selectedCustomer) {this.selectedCustomer = selectedCustomer;}

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

    public City addCity (String cityName, Country country) {
        System.out.println("addCity called");
        City city = new City();
        city.setCity(this.cityField.getText());
        city.setCountry(country);
        CityAccess cityAccess = new CityAccess();
        int cityID = cityAccess.addCity(city);
        city.setCityID(cityID);
        return city;
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

    public void displayMainCustomerScreen() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("views/CustomerScreen.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage = mainApp.primaryStage;
            primaryStage.setScene(scene);
            CustomerScreenController controller = loader.getController();
            controller.setMainApp(mainApp);
            primaryStage.setResizable(false);
            primaryStage.show();
            System.out.println("Displaying Customer screen");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
