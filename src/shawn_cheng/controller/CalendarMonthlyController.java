package shawn_cheng.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import shawn_cheng.MainApp;
import shawn_cheng.model.Appointment;
import shawn_cheng.model.Customer;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class CalendarMonthlyController extends CalendarControllerAbstract implements Initializable {

    private MainApp mainApp;

    private YearMonth selectedMonth;

    @FXML
    private Label monthLabel;

    @FXML
    private GridPane calendarGrid;

    private ObservableList<Appointment> calendarAppointments = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Get current month
        this.selectedMonth = YearMonth.now();
        // Display the calendar
        this.calendarAppointments = createAppointmentsList();
        displayMonthLabel();
        displayCalendar();

        /*
        // Below is all trial stuff
        BorderPane bp = new BorderPane();
        Label textLabel = new Label();
        textLabel.setText("Test Label");
        Label numLabel = new Label();
        numLabel.setText("1");
        //bp.setCenter(textLabel);
        bp.setTop(numLabel);
        BorderPane.setAlignment(numLabel, Pos.TOP_RIGHT);

        // Trying something with ListViews
        Appointment newAppointment = new Appointment();
        newAppointment.setTitle("New Appointment");

        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        appointments.add(newAppointment);

        ListView<Appointment> listView = new ListView<>(appointments);
        listView.getSelectionModel().selectedItemProperty()
                .addListener((obs, oldVal, newVal) -> selectedAppointment = newVal);
        bp.setCenter(listView);

        // Add another BorderPane (datePane)
        /*
        BorderPane bp2 = new BorderPane();
        Appointment newAppointment2 = new Appointment();
        newAppointment2.setTitle("2nd Appointment");
        ObservableList<Appointment> appointments2 = FXCollections.observableArrayList();
        appointments2.add(newAppointment2);
        ListView<Appointment> listView2 = new ListView<>(appointments2);
        listView2.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> selectedAppointment = newVal);
        bp2.setCenter(listView2);
        Label numLabel2 = new Label();
        numLabel2.setText("2");
        bp2.setTop(numLabel2);
        BorderPane.setAlignment(numLabel2, Pos.TOP_RIGHT);
        */


        /*//Add to Calendar
        calendarGrid.add(bp, this.selectedMonth.atDay(1).getDayOfWeek().getValue(), 0);
        //calendarGrid.add(bp2, this.selectedMonth.atDay(2).getDayOfWeek().getValue(), 0);

        System.out.println(createAppointmentsList().get(0).getTitle());
        */

    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    void addHandler(ActionEvent event) {

    }

    @FXML
    void backHandler(ActionEvent event) {
        ScreenDisplays.displayMainMenu();
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
        displayMonthLabel();
        displayCalendar();
    }

    @FXML
    void previousMonthHandler(ActionEvent event) {
        selectedMonth = selectedMonth.minusMonths(1);
        displayMonthLabel();
        displayCalendar();
    }

    @FXML
    void weeklyViewHandler(ActionEvent event) {

    }

    public void displayMonthLabel() {
        selectedAppointment = null;
        this.monthLabel.setText(this.selectedMonth.getMonth().name() + " " + selectedMonth.getYear());
    }

    public void displayCalendar() {

        // Clear what's currently on the grid
        calendarGrid.getChildren().clear();

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
                System.out.println("The current date label is: " + currDate.getDayOfMonth());
                currDate = currDate.plusDays(1);
            }
        }
    }

    public ObservableList<Appointment> createAppointmentsList() {
        // Create appt1
        Appointment appt1 = new Appointment();
        appt1.setTitle("Appt Today");
        appt1.setAppointmentId(1);
        appt1.setContact("My Contact");
        appt1.setDescription("A test description");
        appt1.setLocation("A test location");
        appt1.setUrl("http://test.com");
        appt1.setStartDateTime(LocalDateTime.now());
        appt1.setEndDateTime(LocalDateTime.now().plusHours(1));

        // Create appt2
        Appointment appt2 = new Appointment();
        appt2.setTitle("Appt Next Month");
        appt2.setAppointmentId(2);
        appt2.setContact("My Contact 2");
        appt2.setDescription("A test description 2");
        appt2.setLocation("A test location 2");
        appt2.setUrl("http://test2.com");
        appt2.setStartDateTime(LocalDateTime.now().plusMonths(1));
        appt2.setEndDateTime(LocalDateTime.now().plusMonths(1).plusHours(1));

        this.calendarAppointments.add(appt1);
        this.calendarAppointments.add(appt2);
        return calendarAppointments;
    }

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

        // Attach day of month to the daily border pane
        dailyBorderPane.setTop(dayOfMonthLabel);
        BorderPane.setAlignment(dayOfMonthLabel, Pos.TOP_RIGHT);

        // Attach appointment information to the border pane
        dailyBorderPane.setCenter(dailyApptListView);

        return dailyBorderPane;
    }

}
