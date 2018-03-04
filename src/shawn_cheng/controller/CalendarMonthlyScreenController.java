package shawn_cheng.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import shawn_cheng.MainApp;
import shawn_cheng.access.AppointmentAccess;
import shawn_cheng.model.Appointment;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class CalendarMonthlyScreenController extends AbstractCalendarController implements Initializable {

    @FXML
    private Label monthLabel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Get current month
        this.selectedMonth = YearMonth.now();
        // Display the calendar
        displayMonthLabel();
        displayCalendar();
    }

    /**
     * Reference to mainApp object
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * Switches to next month
     * @param event
     */
    @FXML
    void nextMonthHandler(ActionEvent event) {
        selectedMonth = selectedMonth.plusMonths(1);
        displayMonthLabel();
        displayCalendar();
    }

    /**
     * Switches to the previous month
     * @param event
     */
    @FXML
    void previousMonthHandler(ActionEvent event) {
        selectedMonth = selectedMonth.minusMonths(1);
        displayMonthLabel();
        displayCalendar();
    }

    /**
     * Switches to the weekly view of the calendar.
     * @param event
     */
    @FXML
    void weeklyViewHandler(ActionEvent event) {

    }

    /**
     * Displays the month based on the currently selected month.
     */
    public void displayMonthLabel() {
        this.monthLabel.setText(this.selectedMonth.getMonth().name() + " " + selectedMonth.getYear());
    }

    /**
     * This method displays the calendar information. It will use nested loops to go through the
     * GridPane that represents the calendar. At each cell it will call the getDailyPane method
     * to retrieve a Border Pane containing the appointment information that will be added to the grid.
     */
    @Override
    public void displayCalendar() {

        viewingWeeklyCalendar = false;

        selectedAppointment = null;

        // Clear what's currently on the grid
        calendarGrid.getChildren().clear();

        // Get appointments from db
        LocalDateTime startDatetime = LocalDateTime.of(selectedMonth.atDay(1), LocalTime.MIDNIGHT);
        LocalDateTime endDatetime = LocalDateTime.of(selectedMonth.atEndOfMonth(), LocalTime.MIDNIGHT);
        AppointmentAccess appointmentAccess = new AppointmentAccess();
        this.calendarAppointments = appointmentAccess.getAppointmentsSubset(startDatetime, endDatetime);

        // Set variables
        LocalDate firstDayofMonth = selectedMonth.atDay(1);
        int lengthOfMonth = selectedMonth.lengthOfMonth();
        int dayOfWeek = firstDayofMonth.getDayOfWeek().getValue();

        System.out.println("Length of current month is: " + lengthOfMonth);
        System.out.println("Day of week for first day is: " + dayOfWeek);

        // Start with the first day of the month
        LocalDate currDate = firstDayofMonth;

        // Find the last day of the month
        LocalDate lastDayOfMonth = firstDayofMonth.plusDays(lengthOfMonth-1);
        System.out.println("Last day of the month is: " + lastDayOfMonth.toString());

        // Nested loops to populate the grid/calendar
        OUTER: for (int rowIndex = 0; rowIndex <= 4; rowIndex++) {
            for (int colIndex = 0; colIndex<=6; colIndex++) {

                // If first day of the month, put it on the correct day of week
                if (currDate.getDayOfMonth() == 1) {
                    colIndex = dayOfWeek;

                    // Since day of week goes from 1 to 7 and grid goes from 0 to 6, place 7 to 0
                    if (dayOfWeek == 7) {
                        colIndex = 0;
                    }

                }

                //Get daily BorderPane, which contains the appointments and the date label.
                BorderPane bp = getDailyPane(currDate);

                //Adding to the grid
                calendarGrid.add(bp, colIndex, rowIndex);

                // If last day, break out of the loop
                if (currDate.equals(lastDayOfMonth)) {
                    System.out.println("Current date is equal to the last date of the month, breaking loop");
                    break OUTER;
                }

                // Increment the current day
                currDate = currDate.plusDays(1);
            }
        }
    }



    /**
     * This method generates the BorderPane node that contains both the day of month label
     * and the ListView that houses the appointment information.
     * @param currentDate
     * @return dailyBorderPane
     */


    @Override
    public BorderPane getDailyPane (LocalDate currentDate) {
        // Make the Border Pane that will contain the day of month and appointment info
        BorderPane dailyBorderPane = new BorderPane();

        // Make the label for day of month
        Label dayOfMonthLabel = new Label();
        dayOfMonthLabel.setText(Integer.toString(currentDate.getDayOfMonth()));

        // Get appointment information.
        ObservableList<Appointment> dailyAppointments = this.calendarAppointments.stream()
                .filter(e -> e.getStartDateTime().toLocalDate().equals(currentDate))
                .collect(Collectors.toCollection(FXCollections::observableArrayList));

        // Create list view from daily appointments.
        ListView<Appointment> dailyApptListView = new ListView<>(dailyAppointments);

        // Add listener to detect when user selects appointment in the list view
        dailyApptListView.getSelectionModel().selectedItemProperty()
                .addListener((obs, oldVal, newVal) -> selectedAppointment = newVal);

        // Attach day of month to the daily border pane
        dailyBorderPane.setTop(dayOfMonthLabel);
        BorderPane.setAlignment(dayOfMonthLabel, Pos.TOP_RIGHT);

        // Attach appointment information to the border pane
        dailyBorderPane.setCenter(dailyApptListView);

        return dailyBorderPane;
    }

}
