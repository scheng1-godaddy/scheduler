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
import shawn_cheng.model.Appointment;
import shawn_cheng.model.Customer;

import java.net.URL;
import java.time.YearMonth;
import java.util.ResourceBundle;

public class CalendarMonthlyController extends CalendarControllerAbstract implements Initializable {

    private MainApp mainApp;

    private YearMonth selectedMonth;

    @FXML
    private Label monthLabel;

    @FXML
    private GridPane calendarGrid;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        this.selectedMonth = YearMonth.now();
        displayCalendar();

        // Below is all trial stuff
        BorderPane bp = new BorderPane();
        Label textLabel = new Label();
        textLabel.setText("Test Label");
        Label numLabel = new Label();
        numLabel.setText("1");
        //bp.setCenter(textLabel);
        bp.setTop(numLabel);

        // Trying something with ListViews
        Appointment newAppointment = new Appointment();
        newAppointment.setTitle("New Appointment");
        Appointment newAppointment2 =  new Appointment();
        newAppointment2.setTitle("Another Appointment");

        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        appointments.add(newAppointment);
        appointments.add(newAppointment2);

        ListView<Appointment> listView = new ListView<>(appointments);
        listView.getSelectionModel().selectedItemProperty()
                .addListener((obs, oldVal, newVal) -> selectedAppointment = newVal);
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
        System.out.println("The selected customer is: " + selectedAppointment);

    }

    @FXML
    void nextMonthHandler(ActionEvent event) {
        selectedMonth = selectedMonth.plusMonths(1);
        displayCalendar();
    }

    @FXML
    void previousMonthHandler(ActionEvent event) {
        selectedMonth = selectedMonth.minusMonths(1);
        displayCalendar();
    }

    @FXML
    void weeklyViewHandler(ActionEvent event) {

    }

    public void displayCalendar() {
        selectedAppointment = null;
        this.monthLabel.setText(this.selectedMonth.getMonth().name() + " " + selectedMonth.getYear());
    }

}
