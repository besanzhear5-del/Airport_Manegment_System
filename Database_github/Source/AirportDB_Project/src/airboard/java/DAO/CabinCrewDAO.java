package airboard.java.DAO;

import airboard.java.DB.DBConnection;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class CabinCrewDAO {

    public DefaultTableModel getCabinCrewFlights(int crewId) {

        String[] columns = {
                "Flight ID", "Flight No", "From", "To", "Departure", "Arrival", "Status", "Crew Role"
        };

        DefaultTableModel model = new DefaultTableModel(columns, 0);

        String sql = """
                SELECT f.flight_id,
                       f.flight_number,
                       dep.code AS from_code,
                       arr.code AS to_code,
                       fs.departure_time,
                       fs.arrival_time,
                       fs.status,
                       fc.shift_role
                FROM Flight_Crew fc
                JOIN Flight f ON fc.flight_id = f.flight_id
                LEFT JOIN Airport dep ON f.departure_airport_id = dep.airport_id
                LEFT JOIN Airport arr ON f.arrival_airport_id = arr.airport_id
                LEFT JOIN Flight_Schedule fs ON f.flight_id = fs.flight_id
                WHERE fc.crew_id = ?
                ORDER BY fs.departure_time
                """;

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setInt(1, crewId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("flight_id"),
                        rs.getString("flight_number"),
                        rs.getString("from_code"),
                        rs.getString("to_code"),
                        rs.getTimestamp("departure_time"),
                        rs.getTimestamp("arrival_time"),
                        rs.getString("status"),
                        rs.getString("shift_role")
                });
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return model;
    }

    public DefaultTableModel getPassengersForCabinCrew(int crewId) {

        String[] columns = {
                "Passenger ID", "Passenger Name", "Passport", "Flight No", "Seat", "Class", "Ticket Status"
        };

        DefaultTableModel model = new DefaultTableModel(columns, 0);

        String sql = """
                SELECT p.passenger_id,
                       CONCAT(p.first_name, ' ', p.last_name) AS passenger_name,
                       p.passport_number,
                       f.flight_number,
                       t.seat_number,
                       t.class,
                       t.status
                FROM Flight_Crew fc
                JOIN Flight f ON fc.flight_id = f.flight_id
                JOIN Ticket t ON f.flight_id = t.flight_id
                JOIN Passenger p ON t.passenger_id = p.passenger_id
                WHERE fc.crew_id = ?
                ORDER BY f.flight_number, t.seat_number
                """;

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setInt(1, crewId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("passenger_id"),
                        rs.getString("passenger_name"),
                        rs.getString("passport_number"),
                        rs.getString("flight_number"),
                        rs.getString("seat_number"),
                        rs.getString("class"),
                        rs.getString("status")
                });
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return model;
    }
    public DefaultTableModel getPassengersByFlightNumber(
        int crewId,
        String flightNumber
) {

    String[] columns = {
            "Passenger ID",
            "Passenger Name",
            "Passport",
            "Flight No",
            "Seat",
            "Class",
            "Ticket Status"
    };

    DefaultTableModel model = new DefaultTableModel(columns, 0);

    String sql = """
            SELECT p.passenger_id,
                   CONCAT(p.first_name, ' ', p.last_name) AS passenger_name,
                   p.passport_number,
                   f.flight_number,
                   t.seat_number,
                   t.class,
                   t.status
            FROM Flight_Crew fc
            JOIN Flight f ON fc.flight_id = f.flight_id
            JOIN Ticket t ON f.flight_id = t.flight_id
            JOIN Passenger p ON t.passenger_id = p.passenger_id
            WHERE fc.crew_id = ?
            AND f.flight_number = ?
            ORDER BY t.seat_number
            """;

    try (
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
    ) {

        ps.setInt(1, crewId);
        ps.setString(2, flightNumber);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {

            model.addRow(new Object[]{
                    rs.getInt("passenger_id"),
                    rs.getString("passenger_name"),
                    rs.getString("passport_number"),
                    rs.getString("flight_number"),
                    rs.getString("seat_number"),
                    rs.getString("class"),
                    rs.getString("status")
            });
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return model;
}
}