package shawn_cheng.controller;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import shawn_cheng.access.AppointmentAccess;
import shawn_cheng.model.ReportTypeCount;

import java.net.URL;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/**
 * Controller for the Appointment Type Count Report screen
 * @author Shawn Cheng
 */
public class ReportsApptTypeController extends AbstractReportController {

    @FXML
    private TableColumn<ReportTypeCount, Integer> columnCount;

    @FXML
    private TableColumn<ReportTypeCount, String> columnReportType;

    @FXML
    protected ComboBox<String> dropDown;

    @FXML
    protected TableView<ReportTypeCount> tableView;

    private YearMonth selectedMonth;

    private DateTimeFormatter monthFormat = DateTimeFormatter.ofPattern("MMMM - yyy");

    /**
     * Initial method that runs when screen first loads
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // Populate the drop down menus with month values
        populateDropDowns();

        // Set cell value factory
        columnReportType.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));
        columnCount.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getCount()).asObject());
    }

    /**
     * Handler for the run report button
     * @param event
     */
    @Override @FXML
    public void HandlerRunReport(ActionEvent event) {

        // Get the currently selected month
        selectedMonth = YearMonth.parse(this.dropDown.getValue(), monthFormat);

        // Set the start and end times to beginning of the month and end of the month
        LocalDateTime startDateTime = LocalDateTime.of(selectedMonth.atDay(1), LocalTime.MIDNIGHT);
        LocalDateTime endDateTime = LocalDateTime.of(selectedMonth.atEndOfMonth(), LocalTime.MIDNIGHT);

        // Query the database for types along with the counts
        AppointmentAccess appointmentAccess = new AppointmentAccess();
        ObservableList<ReportTypeCount> reportResultList = appointmentAccess.getAppointmentsSubsetGroupType(startDateTime, endDateTime);

        // Populate the tableview with the results.
        tableView.setItems(reportResultList);
    }

    /**
     * Method to generate list that will populate the drop down menu
     */
    public void populateDropDowns() {
        // Create a list of all months, this will be used to populate the drop down menu
        ObservableList<String> allMonths = FXCollections.observableArrayList();
        for(int x = 1; x <= 12; x++) {
            allMonths.add(YearMonth.of(Year.now().getValue(), x).format(monthFormat));
        }
        this.dropDown.setItems(allMonths);
    }
}
