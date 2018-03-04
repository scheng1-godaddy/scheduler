package shawn_cheng.access;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import shawn_cheng.MainApp;
import shawn_cheng.model.Appointment;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

public class AppointmentAccess {

    private Connection conn = MainApp.getDBConnection();

    public ObservableList<Appointment> getAllAppointments() {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        String getAppointmentQuery = String.join(" ",
                "SELECT * FROM appointment AS a",
                "JOIN customer AS c",
                "ON a.customerId = c.customerId",
                "WHERE c.active = 1"
        );

        try{
            PreparedStatement stmt = conn.prepareStatement(getAppointmentQuery);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Appointment appt = new Appointment();
                appt.setAppointmentId(rs.getInt("appointmentId"));
                appt.setTitle(rs.getString("title"));
                appt.setDescription(rs.getString("description"));
                appt.setLocation(rs.getString("location"));
                appt.setContact(rs.getString("contact"));
                appt.setUrl(rs.getString("url"));

                // Adjust times from UTC
                ZoneId zone = ZoneId.systemDefault();
                LocalDateTime startUTC = rs.getTimestamp("start").toLocalDateTime();
                LocalDateTime endUTC = rs.getTimestamp("end").toLocalDateTime();
                LocalDateTime startLocal = startUTC.atZone(ZoneOffset.UTC).withZoneSameInstant(zone).toLocalDateTime();
                LocalDateTime endLocal = endUTC.atZone(ZoneOffset.UTC).withZoneSameInstant(zone).toLocalDateTime();

                appt.setStartDateTime(startLocal);
                appt.setEndDateTime(endLocal);

                CustomerAccess customerAccess = new CustomerAccess();
                appt.setCustomer(customerAccess.getCustomer(rs.getInt("customerId")));

                appointments.add(appt);
            }
        } catch(SQLException ex){
            System.out.println(ex.getMessage());
        }

        return appointments;
    }

    public ObservableList<Appointment> getAppointmentsSubset(LocalDateTime startTime, LocalDateTime endTime) {

        ObservableList<Appointment> appointments = FXCollections.observableArrayList();

        String query = "SELECT * FROM appointment AS a JOIN customer AS c " +
                "ON a.customerId = c.customerId " +
                "WHERE a.start >= ? AND a.end <= ? AND c.active = 1";

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            // Need to convert times to UTC for the query (times in DB are in UTC).
            ZoneId zone = ZoneId.systemDefault();
            LocalDateTime startDatetimeParam = startTime.atZone(zone).withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime();
            LocalDateTime endDatetimeParam = endTime.atZone(zone).withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime();
            stmt.setTimestamp(1, Timestamp.valueOf(startDatetimeParam));
            stmt.setTimestamp(2, Timestamp.valueOf(endDatetimeParam));
            System.out.println("Executing the following query: " + stmt);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Appointment apptResult = new Appointment();
                apptResult.setAppointmentId(rs.getInt("appointmentId"));
                apptResult.setTitle(rs.getString("title"));
                apptResult.setDescription(rs.getString("description"));
                apptResult.setLocation(rs.getString("location"));
                apptResult.setContact(rs.getString("contact"));
                apptResult.setUrl(rs.getString("url"));

                // Convert time from UTC to local time zone
                LocalDateTime startTimeLocalZone = rs.getTimestamp("start").toLocalDateTime()
                        .atZone(ZoneOffset.UTC).withZoneSameInstant(zone).toLocalDateTime();
                LocalDateTime endTimeLocalZone = rs.getTimestamp("end").toLocalDateTime()
                        .atZone(ZoneOffset.UTC).withZoneSameInstant(zone).toLocalDateTime();
                apptResult.setStartDateTime(startTimeLocalZone);
                apptResult.setEndDateTime(endTimeLocalZone);

                // Get customer
                CustomerAccess customer = new CustomerAccess();
                apptResult.setCustomer(customer.getCustomer(rs.getInt("customerId")));

                appointments.add(apptResult);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return appointments;
    }

    public int addAppointment(Appointment appointment) {
        System.out.println("Add appointment called in AppointmentAccess");
        // Get appointment ID
        int apptID = getNewId();
        // Get current time zone
        ZoneId zone = ZoneId.systemDefault();
        // Adjust start time to UTC
        LocalDateTime startTimeUTC = appointment.getStartDateTime()
                .atZone(zone).withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime();
        // Adjust end time to UTC
        LocalDateTime endTimeUTC = appointment.getEndDateTime()
                .atZone(zone).withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime();

        // Query to insert appointment
        String query = "INSERT INTO appointment " +
                "(appointmentId, customerId, title, description, location, contact, " +
                "url, start, end, createDate, createdBy, lastUpdate, lastUpdateBy) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), ?, NOW(), ?)";

        try {
            // Replace values in query
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, apptID);
            stmt.setInt(2, appointment.getCustomer().getCustomerID());
            stmt.setString(3, appointment.getTitle());
            stmt.setString(4, appointment.getDescription());
            stmt.setString(5, appointment.getLocation());
            stmt.setString(6, appointment.getContact());
            stmt.setString(7, appointment.getUrl());
            stmt.setTimestamp(8, Timestamp.valueOf(startTimeUTC));
            stmt.setTimestamp(9, Timestamp.valueOf(endTimeUTC));
            stmt.setString(10, MainApp.userName);
            stmt.setString(11, MainApp.userName);
            //Execute query
            System.out.println("Executing the following query: " + stmt);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return apptID;
    }

    public int getNewId() {
        int id = 0;
        String query = "SELECT MAX(appointmentId) FROM appointment";
        try {
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery(query);

            if(result.next()) {
                id = result.getInt(1);
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Appointment ID is: " + (id + 1));
        return id + 1;
    }

    public void updateAppointment(Appointment appointment) {
        String query = "UPDATE appointment " +
                "SET customerId=?, title=?, description=?, location=?, " +
                "contact=?, url=?, start=?, end=?, lastUpdate=NOW(), lastUpdateBy=? " +
                "WHERE appointmentId = ?";

        // Convert times to UTC
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime startUTC = appointment.getStartDateTime()
                .atZone(zone).withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime();
        LocalDateTime endUTC = appointment.getEndDateTime()
                .atZone(zone).withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime();

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, appointment.getCustomer().getCustomerID());
            stmt.setString(2, appointment.getTitle());
            stmt.setString(3, appointment.getDescription());
            stmt.setString(4, appointment.getLocation());
            stmt.setString(5, appointment.getContact());
            stmt.setString(6, appointment.getUrl());
            stmt.setTimestamp(7, Timestamp.valueOf(startUTC));
            stmt.setTimestamp(8, Timestamp.valueOf(endUTC));
            stmt.setString(9, MainApp.userName);
            stmt.setInt(10, appointment.getAppointmentId());
            stmt.executeUpdate();
        } catch(SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void removeAppointment(Appointment appointment) {
        String query = "DELETE FROM appointment WHERE appointmentId = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, appointment.getAppointmentId());
            stmt.executeUpdate();
        } catch(SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
