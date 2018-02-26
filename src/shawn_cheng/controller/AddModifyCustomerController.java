package shawn_cheng.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import shawn_cheng.MainApp;
import shawn_cheng.access.CustomerAccess;
import shawn_cheng.model.Customer;

import java.net.URL;
import java.util.ResourceBundle;

public class AddModifyCustomerController implements Initializable {

    MainApp mainApp;
    @FXML
    private TextField nameField;

    @FXML
    private TextField address1Field;

    @FXML
    private TextField address2Field;

    @FXML
    private TextField cityField;

    @FXML
    private TextField countryField;

    @FXML
    private TextField postalField;

    @FXML
    private TextField phoneField;


    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    void cancelButtonHandler(ActionEvent event) {

    }

    @FXML
    void saveButtonHandler(ActionEvent event) {

    }

    public void setMainApp(MainApp mainApp) {this.mainApp = mainApp;}

    public Customer gatherData() {
        Customer customer = new Customer();
        //String name = nameField.getText();
        //customer.setCustomerName();
        return customer;
    }
}
