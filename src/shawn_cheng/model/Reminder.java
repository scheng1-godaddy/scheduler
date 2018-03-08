package shawn_cheng.model;

import java.time.LocalDateTime;

public class Reminder {

    // Members
    public int reminderId;
    public Appointment appointment;
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

    public int getReminderId() {
        return reminderId;
    }

    public void setReminderId(int reminderId) {
        this.reminderId = reminderId;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public LocalDateTime getReminderDateTime() {
        return reminderDateTime;
    }

    public void setReminderDateTime(LocalDateTime reminderDateTime) {
        this.reminderDateTime = reminderDateTime;
    }

    @Override
    public String toString() {
        return this.appointment.getTitle();
    }


}

