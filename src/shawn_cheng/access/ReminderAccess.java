package shawn_cheng.access;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import shawn_cheng.MainApp;
import shawn_cheng.model.Appointment;
import shawn_cheng.model.Reminder;
import shawn_cheng.model.User;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

public class ReminderAccess {

    // Get DB connection
    Connection conn = MainApp.getDBConnection();

    /**
     * Add reminder
     * @param reminder
     * @return reminder ID
     */
    public int addReminder(Reminder reminder) {
        String query = "INSERT INTO reminder (reminderId, reminderDate, snoozeIncrement, " +
                "snoozeIncrementTypeId, appointmentId, createdBy, createdDate, remindercol) " +
                "VALUES (?, ?, 0, 0, ?, ?, NOW(), '')";

        int reminderId = getReminderId();
        try {
            PreparedStatement stmt = conn.prepareStatement(query);

            // Store the date in UTC time
            ZoneId zone = ZoneId.systemDefault();
            LocalDateTime reminderDate = reminder.getReminderDateTime()
                    .atZone(zone)
                    .withZoneSameInstant(ZoneOffset.UTC)
                    .toLocalDateTime();

            // Replace values in the query string
            stmt.setInt(1, reminderId);
            stmt.setTimestamp(2, Timestamp.valueOf(reminderDate));
            stmt.setInt(3, reminder.getAppointment().getAppointmentId());
            stmt.setString(4, MainApp.userName);
            stmt.executeUpdate();
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return reminderId;
    }

    /**
     * Retrieves ID for reminder
     *
     * @return maxID
     */
    private int getReminderId() {
        int reminderId = 0;

        String query = "SELECT MAX(reminderId) FROM reminder";
        try {
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery(query);

            if(result.next()) {
                reminderId = result.getInt(1);
            }
        } catch(SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return reminderId + 1;
    }

    /**
     * Removes reminder
     * @param reminder
     */
    public void removeReminder(Reminder reminder) {
        System.out.println("Executing remove reminder");
        String query = "DELETE FROM reminder WHERE reminderId = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, reminder.getReminderId());
            System.out.println("Running the following SQL statement: " + stmt);
            stmt.executeUpdate();
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Updates reminder
     * @param reminder
     */
    public void updateReminder(Reminder reminder) {
        String updateQuery = "UPDATE reminder SET reminderDate=? WHERE reminderId = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(updateQuery);

            ZoneId zone = ZoneId.systemDefault();
            LocalDateTime reminderDate = reminder.getReminderDateTime()
                    .atZone(zone)
                    .withZoneSameInstant(ZoneOffset.UTC)
                    .toLocalDateTime();

            stmt.setTimestamp(1, Timestamp.valueOf(reminderDate));
            stmt.setInt(2, reminder.getReminderId());
            stmt.executeUpdate();
        } catch(SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public ObservableList<Reminder> getReminders(LocalDateTime startTime, LocalDateTime endTime, String userName) {
        ObservableList<Reminder> reminders = FXCollections.observableArrayList();

        String query = "SELECT r.reminderId, a.* FROM reminder AS r " +
                "JOIN appointment AS a ON r.appointmentId = a.appointmentId " +
                "JOIN customer AS c ON a.customerId = c.customerId " +
                "WHERE r.reminderDate BETWEEN ? AND ? AND a.createdBy = ? AND c.active = 1";

        try{
            PreparedStatement stmt = conn.prepareStatement(query);

            // Convert time to UTC time to store
            ZoneId zone = ZoneId.systemDefault();
            LocalDateTime startDatetimeUTC = startTime.atZone(zone).withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime();
            LocalDateTime endDatetimeUTC = endTime.atZone(zone).withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime();
            stmt.setTimestamp(1, Timestamp.valueOf(startDatetimeUTC));
            stmt.setTimestamp(2, Timestamp.valueOf(endDatetimeUTC));
            stmt.setString(3, userName);
            System.out.println("Executing the following query: " + stmt);
            ResultSet rs = stmt.executeQuery();

            // Get the results and place into Appointment objects
            while (rs.next()) {
                Appointment appt = new Appointment();

                appt.setAppointmentId(rs.getInt("appointmentId"));
                appt.setTitle(rs.getString("title"));
                appt.setDescription(rs.getString("description"));
                appt.setLocation(rs.getString("location"));
                appt.setContact(rs.getString("contact"));
                appt.setUrl(rs.getString("url"));

                // Convert times back from UTC (but need to get it as local date time first
                LocalDateTime fetchedStartTimeUTC = rs.getTimestamp("start").toLocalDateTime();
                LocalDateTime fetchedEndTimeUTC = rs.getTimestamp("end").toLocalDateTime();
                LocalDateTime startDateTimeLocal = fetchedStartTimeUTC.atZone(ZoneOffset.UTC).withZoneSameInstant(zone).toLocalDateTime();
                LocalDateTime endDateTimeLocal = fetchedEndTimeUTC.atZone(ZoneOffset.UTC).withZoneSameInstant(zone).toLocalDateTime();

                appt.setStartDateTime(startDateTimeLocal);
                appt.setEndDateTime(endDateTimeLocal);

                Reminder reminder = new Reminder(appt);
                reminder.setReminderId(rs.getInt("reminderId"));

                reminders.add(reminder);
            }
        } catch(SQLException ex){
            System.out.println(ex.getMessage());
        }

        return reminders;
    }
}
