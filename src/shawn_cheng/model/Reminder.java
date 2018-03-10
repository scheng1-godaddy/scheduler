package shawn_cheng.model;

import java.time.LocalDateTime;

/**
 * Class to hold reminder information
 *
 * @author Shawn Cheng
 */
public class Reminder {

    // Unique ID
    public int reminderId;
    // Reference to appointment
    public Appointment appointment;
    // The date time of the appointment start time.
    private LocalDateTime reminderDateTime;

    /**
     * Constructor.
     *
     * @param appointment
     */
    public Reminder(Appointment appointment) {
        this.appointment = appointment;
        this.reminderDateTime = appointment.getStartDateTime();
    }

    /**
     * Get reminder ID
     * @return
     */
    public int getReminderId() {
        return reminderId;
    }

    /**
     * Set reminder ID
     * @param reminderId
     */
    public void setReminderId(int reminderId) {
        this.reminderId = reminderId;
    }

    /**
     * Get appointment reference
     * @return
     */
    public Appointment getAppointment() {
        return appointment;
    }

    /**
     * Set appointment reference
     * @param appointment
     */
    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    /**
     * Get reminder date time (same as the appointment start time)
     * @return
     */
    public LocalDateTime getReminderDateTime() {
        return reminderDateTime;
    }

    /**
     * Set reminderDateTime
     * @param reminderDateTime
     */
    public void setReminderDateTime(LocalDateTime reminderDateTime) {
        this.reminderDateTime = reminderDateTime;
    }

    /**
     * Override toString. Returns the appointment title.
     * @return
     */
    @Override
    public String toString() {
        return this.appointment.getTitle();
    }


}

