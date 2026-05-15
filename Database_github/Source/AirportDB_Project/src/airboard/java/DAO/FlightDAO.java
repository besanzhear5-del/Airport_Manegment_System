package airboard.java.DAO;

import airboard.java.DB.DBConnection;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class FlightDAO {

    public DefaultTableModel getAllFlights() {
        String[] columns = {
                "ID", "Flight No", "From ID", "From", "To ID", "To",
                "Aircraft ID", "Airline ID", "Departure", "Arrival", "Status"
        };

        DefaultTableModel model = new DefaultTableModel(columns, 0);

        String sql = """
                SELECT f.flight_id, f.flight_number,
                       f.departure_airport_id, dep.code AS from_code,
                       f.arrival_airport_id, arr.code AS to_code,
                       f.aircraft_id, f.airline_id,
                       fs.departure_time, fs.arrival_time, fs.status
                FROM Flight f
                LEFT JOIN Airport dep ON f.departure_airport_id = dep.airport_id
                LEFT JOIN Airport arr ON f.arrival_airport_id = arr.airport_id
                LEFT JOIN Flight_Schedule fs ON f.flight_id = fs.flight_id
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("flight_id"),
                        rs.getString("flight_number"),
                        rs.getInt("departure_airport_id"),
                        rs.getString("from_code"),
                        rs.getInt("arrival_airport_id"),
                        rs.getString("to_code"),
                        rs.getInt("aircraft_id"),
                        rs.getInt("airline_id"),
                        rs.getTimestamp("departure_time"),
                        rs.getTimestamp("arrival_time"),
                        rs.getString("status")
                });
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return model;
    }

    public boolean addFlight(
            String flightNumber,
            int departureAirportId,
            int arrivalAirportId,
            int aircraftId,
            int airlineId,
            String departureTime,
            String arrivalTime,
            String status
    ) {
        String flightSql = """
                INSERT INTO Flight
                (flight_number, departure_airport_id, arrival_airport_id, aircraft_id, airline_id)
                VALUES (?, ?, ?, ?, ?)
                """;

        String scheduleSql = """
                INSERT INTO Flight_Schedule
                (flight_id, departure_time, arrival_time, status)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement flightPs = conn.prepareStatement(flightSql, Statement.RETURN_GENERATED_KEYS)) {
                flightPs.setString(1, flightNumber);
                flightPs.setInt(2, departureAirportId);
                flightPs.setInt(3, arrivalAirportId);
                flightPs.setInt(4, aircraftId);
                flightPs.setInt(5, airlineId);

                int rows = flightPs.executeUpdate();

                if (rows == 0) {
                    conn.rollback();
                    return false;
                }

                ResultSet keys = flightPs.getGeneratedKeys();

                if (!keys.next()) {
                    conn.rollback();
                    return false;
                }

                int newFlightId = keys.getInt(1);
                keys.close();

                try (PreparedStatement schedulePs = conn.prepareStatement(scheduleSql)) {
                    schedulePs.setInt(1, newFlightId);
                    schedulePs.setTimestamp(2, Timestamp.valueOf(departureTime));
                    schedulePs.setTimestamp(3, Timestamp.valueOf(arrivalTime));
                    schedulePs.setString(4, status);

                    schedulePs.executeUpdate();
                }

                conn.commit();
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateFlight(
            int flightId,
            String flightNumber,
            int departureAirportId,
            int arrivalAirportId,
            int aircraftId,
            int airlineId,
            String departureTime,
            String arrivalTime,
            String status
    ) {
        String flightSql = """
                UPDATE Flight
                SET flight_number = ?, departure_airport_id = ?, arrival_airport_id = ?, aircraft_id = ?, airline_id = ?
                WHERE flight_id = ?
                """;

        String scheduleSql = """
                UPDATE Flight_Schedule
                SET departure_time = ?, arrival_time = ?, status = ?
                WHERE flight_id = ?
                """;

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement flightPs = conn.prepareStatement(flightSql)) {
                flightPs.setString(1, flightNumber);
                flightPs.setInt(2, departureAirportId);
                flightPs.setInt(3, arrivalAirportId);
                flightPs.setInt(4, aircraftId);
                flightPs.setInt(5, airlineId);
                flightPs.setInt(6, flightId);

                flightPs.executeUpdate();
            }

            try (PreparedStatement schedulePs = conn.prepareStatement(scheduleSql)) {
                schedulePs.setTimestamp(1, Timestamp.valueOf(departureTime));
                schedulePs.setTimestamp(2, Timestamp.valueOf(arrivalTime));
                schedulePs.setString(3, status);
                schedulePs.setInt(4, flightId);

                int scheduleRows = schedulePs.executeUpdate();

                if (scheduleRows == 0) {
                    String insertSchedule = """
                            INSERT INTO Flight_Schedule
                            (flight_id, departure_time, arrival_time, status)
                            VALUES (?, ?, ?, ?)
                            """;

                    try (PreparedStatement insertPs = conn.prepareStatement(insertSchedule)) {
                        insertPs.setInt(1, flightId);
                        insertPs.setTimestamp(2, Timestamp.valueOf(departureTime));
                        insertPs.setTimestamp(3, Timestamp.valueOf(arrivalTime));
                        insertPs.setString(4, status);
                        insertPs.executeUpdate();
                    }
                }
            }

            conn.commit();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteFlight(int flightId) {
        String sql = "DELETE FROM Flight WHERE flight_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, flightId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public DefaultTableModel searchFlightsForPassenger(
        String keyword,
        String from,
        String to,
        String airline,
        String status
) {

    String[] columns = {
        "Flight No",
        "From",
        "To",
        "Airline",
        "Departure",
        "Arrival",
        "Status"
    };

    DefaultTableModel model = new DefaultTableModel(columns, 0);

    String sql = """
        SELECT
            f.flight_number,

            dep.code AS from_airport,
            arr.code AS to_airport,

            al.name AS airline_name,

            fs.departure_time,
            fs.arrival_time,
            fs.status

        FROM Flight f

        LEFT JOIN Airport dep
            ON f.departure_airport_id = dep.airport_id

        LEFT JOIN Airport arr
            ON f.arrival_airport_id = arr.airport_id

        LEFT JOIN Airline al
            ON f.airline_id = al.airline_id

        LEFT JOIN Flight_Schedule fs
            ON f.flight_id = fs.flight_id

        WHERE
        (
            f.flight_number LIKE ?
            OR dep.code LIKE ?
            OR arr.code LIKE ?
            OR dep.city LIKE ?
            OR arr.city LIKE ?
        )

        AND (? = 'All' OR dep.code LIKE ?)
AND (? = 'All' OR arr.code LIKE ?)
AND (? = 'All' OR al.name LIKE ?)
AND (? = 'All' OR fs.status = ?)
        ORDER BY fs.departure_time
    """;

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        String search = "%" + keyword + "%";

        ps.setString(1, search);
        ps.setString(2, search);
        ps.setString(3, search);
        ps.setString(4, search);
        ps.setString(5, search);

        ps.setString(6, from);
       ps.setString(7, "%" + from + "%");


        ps.setString(8, to);
        ps.setString(9, "%" + to + "%");

        ps.setString(10, airline);
       ps.setString(11, "%" + airline + "%");

        ps.setString(12, status);
        ps.setString(13, status);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {

            model.addRow(new Object[]{

                rs.getString("flight_number"),
                rs.getString("from_airport"),
                rs.getString("to_airport"),
                rs.getString("airline_name"),
                rs.getTimestamp("departure_time"),
                rs.getTimestamp("arrival_time"),
                rs.getString("status")
            });
        }

        rs.close();

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return model;
}
   public DefaultTableModel getAvailableFlightsForDashboard() {

    String[] columns = {
            "Flight",
            "From",
            "To",
            "Departure",
            "Arrival",
            "Status"
    };

    DefaultTableModel model = new DefaultTableModel(columns, 0);

    String sql = """
            SELECT f.flight_number,
                   dep.code AS from_code,
                   arr.code AS to_code,
                   fs.departure_time,
                   fs.arrival_time,
                   fs.status
            FROM Flight f

            LEFT JOIN Airport dep
                   ON f.departure_airport_id = dep.airport_id

            LEFT JOIN Airport arr
                   ON f.arrival_airport_id = arr.airport_id

            LEFT JOIN Flight_Schedule fs
                   ON f.flight_id = fs.flight_id

            ORDER BY fs.departure_time
            """;

    try (
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
    ) {

        while (rs.next()) {

            model.addRow(new Object[]{

                    rs.getString("flight_number"),

                    rs.getString("from_code"),

                    rs.getString("to_code"),

                    rs.getTimestamp("departure_time"),

                    rs.getTimestamp("arrival_time"),

                    rs.getString("status")
            });
        }

    } catch (SQLException e) {

        e.printStackTrace();
    }

    return model;
}
}