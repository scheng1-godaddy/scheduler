package shawn_cheng.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import shawn_cheng.MainApp;
import shawn_cheng.model.Customer;

import java.net.URL;
import java.util.ResourceBundle;

public class CalendarMonthlyController implements Initializable {

    private MainApp mainApp;
    public Customer selectedCustomer;

    @FXML
    private Label monthLabel;

    @FXML
    private GridPane calendarGrid;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        BorderPane bp = new BorderPane();
        Label textLabel = new Label();
        textLabel.setText("Test Label");
        Label numLabel = new Label();
        numLabel.setText("1");
        //bp.setCenter(textLabel);
        bp.setTop(numLabel);

        // Trying something with ListViews
        ObservableList<Customer> customers = FXCollections.observableArrayList();
        Customer customer1 = new Customer();
        customer1.setCustomerName("TestCustomer");
        customers.add(customer1);
        ListView<Customer> listView = new ListView<>(customers);
        listView.getSelectionModel().selectedItemProperty()
                .addListener((obs, oldVal, newVal) -> selectedCustomer = newVal);
        bp.setCenter(listView);


        calendarGrid.add(bp, 0, 0);
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    void addHandler(ActionEvent event) {


    }

    @FXML
    void backHandler(ActionEvent event) {
        ScreenDisplays.displayMainMenu(mainApp);
    }

    @FXML
    void deleteHandler(ActionEvent event) {

    }

    @FXML
    void modifyHandler(ActionEvent event) {
        System.out.println("The selected customer is: " + selectedCustomer);

    }

    @FXML
    void nextMonthHandler(ActionEvent event) {

    }

    @FXML
    void previousMonthHandler(ActionEvent event) {

    }

    @FXML
    void weeklyViewHandler(ActionEvent event) {

    }

}
