package airboard.java.DAO;

import airboard.java.DB.DBConnection;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class PilotDAO {

    public DefaultTableModel getAssignedFlightsForPilot(int pilotId) {

    String[] columns = {
            "Flight ID",
            "Flight No",
            "Pilot Role",
            "From",
            "To",
            "Aircraft",
            "Airline",
            "Departure",
            "Arrival",
            "Status"
    };

    DefaultTableModel model = new DefaultTableModel(columns, 0);

    String sql = """
            SELECT f.flight_id,
                   f.flight_number,
                   fp.role AS pilot_role,
                   dep.code AS from_code,
                   arr.code AS to_code,
                   ac.model AS aircraft_model,
                   al.name AS airline_name,
                   fs.departure_time,
                   fs.arrival_time,
                   fs.status
            FROM Flight_Pilot fp
            JOIN Flight f ON fp.flight_id = f.flight_id
            LEFT JOIN Airport dep ON f.departure_airport_id = dep.airport_id
            LEFT JOIN Airport arr ON f.arrival_airport_id = arr.airport_id
            LEFT JOIN Aircraft ac ON f.aircraft_id = ac.aircraft_id
            LEFT JOIN Airline al ON f.airline_id = al.airline_id
            LEFT JOIN Flight_Schedule fs ON f.flight_id = fs.flight_id
            WHERE fp.pilot_id = ?
            ORDER BY fs.departure_time
            """;

    try (
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
    ) {
        ps.setInt(1, pilotId);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            model.addRow(new Object[]{
                    rs.getInt("flight_id"),
                    rs.getString("flight_number"),
                    rs.getString("pilot_role"),
                    rs.getString("from_code"),
                    rs.getString("to_code"),
                    rs.getString("aircraft_model"),
                    rs.getString("airline_name"),
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

    public DefaultTableModel getPilotAircraftStatus(int pilotId) {
        String[] columns = {
            "Flight No", "Aircraft ID", "Aircraft Model",
            "Capacity", "Airline", "Flight Status"
        };

        DefaultTableModel model = new DefaultTableModel(columns, 0);

        String sql = """
            SELECT f.flight_number,
                   ac.aircraft_id,
                   ac.model AS aircraft_model,
                   ac.capacity,
                   al.name AS airline_name,
                   fs.status
            FROM Flight_Pilot fp
            JOIN Flight f ON fp.flight_id = f.flight_id
            LEFT JOIN Aircraft ac ON f.aircraft_id = ac.aircraft_id
            LEFT JOIN Airline al ON f.airline_id = al.airline_id
            LEFT JOIN Flight_Schedule fs ON f.flight_id = fs.flight_id
            WHERE fp.pilot_id = ?
            ORDER BY f.flight_id
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, pilotId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("flight_number"),
                    rs.getInt("aircraft_id"),
                    rs.getString("aircraft_model"),
                    rs.getInt("capacity"),
                    rs.getString("airline_name"),
                    rs.getString("status")
                });
            }

            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return model;
    }
}