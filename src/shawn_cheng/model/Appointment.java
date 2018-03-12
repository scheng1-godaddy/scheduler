package shawn_cheng.model;

import javafx.collections.ObservableList;
import shawn_cheng.access.AppointmentAccess;
import shawn_cheng.controller.ManageAppointmentController;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

/**
 * Object to house appointment information
 *
 * @author Shawn Cheng
 */
public class Appointment {
    // Variables for appointment information
    private int appointmentId;
    private Customer customer;
    public String title;
    private String description;
    private String location;
    private String contact;
    private String url;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    // Time Format
    private static final DateTimeFormatter timeFormat = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);

    /**
     * Sets appointment ID
     * @param appointmentId
     */
    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    /**
     * Gets the appointment ID
     * @return
     */
    public int getAppointmentId() {
        return appointmentId;
    }

    /**
     * Sets the customer object
     * @param customer
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * Gets the customer object
     * @return
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Sets the title of the appointment
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the title of the appointment
     * @return
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the appointment description
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the appointment description
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the location of the appointment
     * @param location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Gets the location of the appointment
     * @return
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the contact information
     * @param contact
     */
    public void setContact(String contact) {
        this.contact = contact;
    }

    /**
     * Gets the contact information
     * @return
     */
    public String getContact() {
        return contact;
    }

    /**
     * Sets the URL
     * @param url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Gets the URL
     * @return
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets the start time of appointment
     * @param startDateTime
     */
    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    /**
     * Gets the start time of the appointment
     * @return
     */
    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    /**
     * Sets the end time of the appointment
     * @param endDateTime
     */
    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    /**
     * Gets the end time of the appointment
     * @return
     */
    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    /**
     * Return the title of the appointment
     * @return
     */
    @Override
    public String toString() {
        return this.title;
    }

    /**
     * Validates the input for appointment information
     * @param controller
     * @return
     */
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

    /**
     * Validates the time of the appointment. Must be within business hours. End time cannot be earlier or equal the
     * start time. Appointment times cannot overlap with existing appointments for the same user/consultant. 
     * @param controller
     * @return
     */
    public static String validateTimes(ManageAppointmentController controller, Appointment selectedAppointment) {
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

        // Check if during business hours. I have to assume that business hours is based on the users local time zone (since the user is an employee!)
        if (selectedStartTime.isBefore(LocalTime.MIDNIGHT.plusHours(9)) || selectedEndTime.isAfter(LocalTime.MIDNIGHT.plusHours(17))) {
            errorMsg += "Appointment must be during business hours\n";
        }

        // Check for overlapping appointments
        AppointmentAccess appointmentAccess = new AppointmentAccess();
        ObservableList<Appointment> appointments = appointmentAccess.getAppointmentOverlaps(selectedStartDateTime, selectedEndDateTime);
        if (!appointments.isEmpty()) {
            System.out.println("Overlap check: there was a return from db query");
            // Not empty, lets check if the overlapping appointment is actually the same appointment (from a modify)
            if (selectedAppointment != null) {
                System.out.println("Overlap check: selected appointment was not null");
                // This will keep track existing appointment was found in the returned results
                boolean existingAppt = false;

                // Cycle through the results
                System.out.println("Overlap check: query returned " + appointments.size() + " results");
                for (Appointment fetchedAppt : appointments) {

                    // Check if the currently selected appointment is the same as one of the overlaps
                    if (fetchedAppt.getAppointmentId() == selectedAppointment.getAppointmentId() && appointments.size() == 1) {
                        System.out.println("Overlap check: found an existing appointment");
                        // It is the same
                        existingAppt = true;
                    }
                }

                // If the overlaps didn't covntain the existing appointment, then there was a genuine overlap issue
                if (!existingAppt) {
                    System.out.println("Overlap check: ");
                    errorMsg += "There is overlapping appointments, choose another time";
                }

            } else {
                // Selected appointment was null, so there was a genuine overlap issue
                errorMsg += "There is overlapping appointments, choose another time";
            }
        }
        return errorMsg;
    }
}