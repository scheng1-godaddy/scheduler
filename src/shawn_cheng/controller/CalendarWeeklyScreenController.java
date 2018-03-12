package shawn_cheng.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Callback;
import shawn_cheng.access.AppointmentAccess;
import shawn_cheng.model.Appointment;
import java.net.URL;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ResourceBundle;

/**
 * Controller for the Weekly Calendar Screen
 * @author Shawn Cheng
 */
public class CalendarWeeklyScreenController extends AbstractCalendarController {

    // Currently selected week
    private LocalDate selectedWeek;
    // Format LocalDate
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);
    // Day of week labels
    @FXML private Label dateLabelSunday;
    @FXML private Label dateLabelMonday;
    @FXML private Label dateLabelTuesday;
    @FXML private Label dateLabelWednesday;
    @FXML private Label dateLabelThursday;
    @FXML private Label dateLabelFriday;
    @FXML private Label dateLabelSaturday;
    // Label to display date range of week
    @FXML private Label weekLabel;

    /**
     * Override Initialize
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Get current week, start with today's date
        LocalDate today = LocalDate.now();
        int dayOfWeek = today.getDayOfWeek().getValue();
        // Selected week should be the Sunday of that week
        if (dayOfWeek == 7) {
            this.selectedWeek = today;
        } else {
            this.selectedWeek = today.minusDays(dayOfWeek);
        }
        // Display the calendar
        displayCalendar();
    }

    /**
     * Handler for next week button
     * @param event
     */
    @FXML
    void nextWeekHandler(ActionEvent event) {
        selectedWeek = selectedWeek.plusWeeks(1);
        displayCalendar();
    }

    /**
     * Handler for previous week button
     * @param event
     */
    @FXML
    void previousWeekHandler(ActionEvent event) {
        selectedWeek = selectedWeek.minusWeeks(1);
        displayCalendar();
    }

    /**
     * Switches to the monthly view of the calendar.
     * @param event
     */
    @FXML
    void monthlyViewHandler(ActionEvent event) {
        ScreenDisplays.displayMonthlyCalendarScreen();
    }

    /**
     * Displays the week based on the currently selected week.
     */
    public void displayWeekLabel() {

        // Display date range on header
        this.weekLabel.setText(this.selectedWeek.format(dateFormatter) + " - " + this.selectedWeek.plusDays(6).format(dateFormatter));

        // Display dates above columns
        this.dateLabelSunday.setText(this.selectedWeek.format(dateFormatter));
        this.dateLabelMonday.setText(this.selectedWeek.plusDays(1).format(dateFormatter));
        this.dateLabelTuesday.setText(this.selectedWeek.plusDays(2).format(dateFormatter));
        this.dateLabelWednesday.setText(this.selectedWeek.plusDays(3).format(dateFormatter));
        this.dateLabelThursday.setText(this.selectedWeek.plusDays(4).format(dateFormatter));
        this.dateLabelFriday.setText(this.selectedWeek.plusDays(5).format(dateFormatter));
        this.dateLabelSaturday.setText(this.selectedWeek.plusDays(6).format(dateFormatter));
    }

    /**
     * This method displays the calendar information. It will use nested loops to go through the
     * GridPane that represents the calendar. At each cell it will call the getDailyPane method
     * to retrieve a Border Pane containing the appointment information that will be added to the grid.
     */
    public void displayCalendar() {

        // Display labels for day of week and date range
        displayWeekLabel();

        // Keep track of which calendar user was last viewing
        viewingWeeklyCalendar = true;

        // Clear currently selected appointment
        selectedAppointment = null;

        // Clear what's currently on the grid
        calendarGrid.getChildren().clear();

        // Display the grid lines.
        insertPanes();

        // Display the time column
        populateTimeLabels();

        // Get appointments from db
        LocalDateTime startDatetime = LocalDateTime.of(selectedWeek, LocalTime.MIDNIGHT);
        LocalDateTime endDatetime = LocalDateTime.of(selectedWeek.plusDays(6), LocalTime.MIDNIGHT);
        AppointmentAccess appointmentAccess = new AppointmentAccess();
        this.calendarAppointments = appointmentAccess.getAppointmentsSubset(startDatetime, endDatetime);

        // Loop through appointments retrieved for the week.
        for (Appointment appt : calendarAppointments) {
            // Get appointment start time
            LocalDateTime curApptStartTime = appt.getStartDateTime();

            // We can get the column value based on what day of week
            int colIndex = curApptStartTime.getDayOfWeek().getValue() + 1;

            // Perform a loop to get the row value (based on start time of appt)
            int rowIndex = 0;
            LocalTime curTime = LocalTime.of(9, 0);
            LocalTime apptTime = LocalTime.of(curApptStartTime.getHour(), curApptStartTime.getMinute());
            for (int x =0; x <= 15; x++) {
                if (curTime.equals(apptTime)) {
                    break;
                } else {
                    rowIndex++;
                }
                curTime = curTime.plusMinutes(30);
            }

            // Creating a list to populate the ListView.
            ObservableList<Appointment> currentAppointment = FXCollections.observableArrayList();
            currentAppointment.add(appt);

            // Create the ListView, this will
            ListView<Appointment> currentApptListView = new ListView<>(currentAppointment);
            currentApptListView.setEditable(false);

            // Custom cell factory so that we can use text object for word wrapping
            currentApptListView.setCellFactory(new Callback<ListView<Appointment>, ListCell<Appointment>>() {

                @Override
                public ListCell<Appointment> call(ListView<Appointment> param) {
                    ListCell<Appointment> cell = new ListCell<Appointment>() {
                        private Text text;
                        @Override
                        protected void updateItem(Appointment appt, boolean empty) {
                            super.updateItem(appt, empty);
                            if (appt != null) {
                                //setText(appt.getTitle());
                                //setWrapText(true);
                                text = new Text(appt.getTitle());
                                text.setWrappingWidth(110);
                                setGraphic(text);
                            } else {
                                setText("");
                            }
                        }
                    };
                    return cell;
                }
            });



            currentApptListView.getSelectionModel().selectedItemProperty()
                    .addListener((obs, oldVal, newVal) -> selectedAppointment = newVal);

            currentApptListView.setOnMouseClicked(event -> {if (event.getClickCount() == 2 && selectedAppointment != null) {
                ScreenDisplays.displayAppointmentScreen(true);
            }});

            // Determine how many rows we'll span based on the length of the appointment.
            System.out.println("Start time is: " + appt.getStartDateTime().toLocalTime() + " End time is: " + appt.getEndDateTime().toLocalTime());
            long apptLength = Duration.between(appt.getStartDateTime().toLocalTime(), appt.getEndDateTime().toLocalTime()).toMinutes();
            System.out.println("Appointment length is: " + apptLength);
            int rowSpan = (int) (apptLength/30);

            // Add to the calendar grid.
            calendarGrid.add(currentApptListView, colIndex, rowIndex, 1, rowSpan);
        }
    }

    /**
     * This is to apply CSS styling to show grid lines in each cell for the gridpane.
     */
    private void insertPanes() {
        for (int colValue = 0; colValue <= 7; colValue++ ) {
            for (int rowValue=0; rowValue <= 15; rowValue++) {
                Pane pane = new Pane();
                pane.getStyleClass().add("grid-cell");
                calendarGrid.add(pane, colValue, rowValue);
            }
        }
    }

    /**
     * Populate the time column
     */
    private void populateTimeLabels() {
        DateTimeFormatter timeFormat = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);
        LocalTime calTimes = LocalTime.of(9, 0);
        for (int rowIndex = 0; rowIndex <= 15; rowIndex++) {
            Label timeLabel = new Label();
            timeLabel.setText(calTimes.format(timeFormat));
            calendarGrid.add(timeLabel, 0, rowIndex);
            GridPane.setHalignment(timeLabel, HPos.CENTER);
            calTimes = calTimes.plusMinutes(30);
        }
    }

}
