package shawn_cheng.model;

import javafx.collections.ObservableList;
import shawn_cheng.access.AppointmentAccess;
import shawn_cheng.controller.ManageAppointmentController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class Appointment {
    private int appointmentId;
    private Customer customer;
    public String title;
    private String description;
    private String location;
    private String contact;
    private String url;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private final LocalDateTime endOfDayLocalDateTime;
    private static final DateTimeFormatter timeFormat = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);

    public Appointment () {
        LocalTime midnight = LocalTime.MIDNIGHT;
        LocalDate today = LocalDate.now();
        endOfDayLocalDateTime = LocalDateTime.of(today, midnight);
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getContact() {
        return contact;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    @Override
    public String toString() {
        return this.title;
    }

    public static String validateInput(ManageAppointmentController controller) {
        System.out.println("Checking appointment input");
        String errorMessage = "";

        if (controller.titleField.getText().equals("")) {
            errorMessage += "Invalid title\n";
        }
        if (controller.descriptionField.getValue() == null || controller.descriptionField.getValue().equals("")) {
            errorMessage += "Invalid description\n";
        }
        if (controller.customerField.getValue() == null || controller.customerField.getValue().equals("")) {
            errorMessage += "Invalid customer\n";
        }
        if (controller.startTimeField.getValue() == null) {
            errorMessage += "Invalid start time\n";
        }
        if (controller.endTimeField.getValue() == null) {
            errorMessage += "Invalid end time\n";
        }
        if (controller.dateField.getValue() == null) {
            errorMessage += "Invalid date";
        }
        return errorMessage;
    }

    public static String validateTimes(ManageAppointmentController controller) {
        System.out.println("Validating appointment times");
        String errorMsg = "";
        LocalDate selectedDate = controller.dateField.getValue();
        LocalTime selectedStartTime = LocalTime.parse(controller.startTimeField.getValue(), timeFormat);
        LocalTime selectedEndTime = LocalTime.parse(controller.endTimeField.getValue(), timeFormat);
        LocalDateTime selectedStartDateTime = LocalDateTime.of(selectedDate, selectedStartTime);
        LocalDateTime selectedEndDateTime = LocalDateTime.of(selectedDate, selectedEndTime);
        int dayOfWeek = selectedDate.getDayOfWeek().getValue();

        System.out.println("Appointment day of week is: " + dayOfWeek);
        // Check if business day (Monday - Friday)
        if (dayOfWeek == 7 || dayOfWeek == 6) {
            errorMsg += "Appointments can only be scheduled on business days\n";
        }

        // Check if end time is greater then start time
        if (selectedEndTime.isBefore(selectedStartTime) || selectedEndTime.equals(selectedStartTime)) {
            errorMsg += "Appointment end time must be later then the start time\n";
        }

        // Check for overlapping appointments
        AppointmentAccess appointmentAccess = new AppointmentAccess();
        ObservableList<Appointment> appointments = appointmentAccess.getAppointmentOverlaps(selectedStartDateTime, selectedEndDateTime);
        if (!appointments.isEmpty()) {
            errorMsg += "There is overlapping appointments, choose another time";
        }
        return errorMsg;
    }
}