package airboard.java.DAO;

import airboard.java.DB.DBConnection;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class GroundCrewDAO {

    public DefaultTableModel getGateOperations() {
        String[] columns = {
                "Gate ID", "Gate", "Terminal", "Airport", "Flight No", "Status", "Departure"
        };

        DefaultTableModel model = new DefaultTableModel(columns, 0);

        String sql = """
                SELECT g.gate_id,
                       g.name AS gate_name,
                       t.name AS terminal_name,
                       a.code AS airport_code,
                       f.flight_number,
                       fs.status,
                       fs.departure_time
                FROM Gate g
                JOIN Terminal t ON g.terminal_id = t.terminal_id
                JOIN Airport a ON t.airport_id = a.airport_id
                LEFT JOIN BoardingPass bp ON g.gate_id = bp.gate_id
                LEFT JOIN Ticket tk ON bp.ticket_id = tk.ticket_id
                LEFT JOIN Flight f ON tk.flight_id = f.flight_id
                LEFT JOIN Flight_Schedule fs ON f.flight_id = fs.flight_id
                ORDER BY g.gate_id
                """;

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("gate_id"),
                        rs.getString("gate_name"),
                        rs.getString("terminal_name"),
                        rs.getString("airport_code"),
                        rs.getString("flight_number"),
                        rs.getString("status"),
                        rs.getTimestamp("departure_time")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return model;
    }

    public DefaultTableModel getBoardingPasses() {
        String[] columns = {
                "Boarding Pass ID", "Ticket ID", "Passenger", "Flight", "Seat", "Gate"
        };

        DefaultTableModel model = new DefaultTableModel(columns, 0);

        String sql = """
                SELECT bp.boarding_pass_id,
                       tk.ticket_id,
                       CONCAT(p.first_name, ' ', p.last_name) AS passenger_name,
                       f.flight_number,
                       tk.seat_number,
                       g.name AS gate_name
                FROM BoardingPass bp
                JOIN Ticket tk ON bp.ticket_id = tk.ticket_id
                JOIN Passenger p ON tk.passenger_id = p.passenger_id
                JOIN Flight f ON tk.flight_id = f.flight_id
                JOIN Gate g ON bp.gate_id = g.gate_id
                ORDER BY bp.boarding_pass_id
                """;

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("boarding_pass_id"),
                        rs.getInt("ticket_id"),
                        rs.getString("passenger_name"),
                        rs.getString("flight_number"),
                        rs.getString("seat_number"),
                        rs.getString("gate_name")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return model;
    }

    public boolean addBoardingPass(int ticketId, int gateId) {
        String sql = """
                INSERT INTO BoardingPass (ticket_id, gate_id)
                VALUES (?, ?)
                """;

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setInt(1, ticketId);
            ps.setInt(2, gateId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public DefaultTableModel getTicketsForCheck() {
        String[] columns = {
                "Ticket ID", "Passenger", "Flight", "Seat", "Class", "Status"
        };

        DefaultTableModel model = new DefaultTableModel(columns, 0);

        String sql = """
                SELECT tk.ticket_id,
                       CONCAT(p.first_name, ' ', p.last_name) AS passenger_name,
                       f.flight_number,
                       tk.seat_number,
                       tk.class,
                       tk.status
                FROM Ticket tk
                JOIN Passenger p ON tk.passenger_id = p.passenger_id
                JOIN Flight f ON tk.flight_id = f.flight_id
                ORDER BY tk.ticket_id
                """;

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("ticket_id"),
                        rs.getString("passenger_name"),
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
    public String getTicketStatusById(int ticketId) {

    String sql = "SELECT status FROM Ticket WHERE ticket_id = ?";

    try (
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
    ) {
        ps.setInt(1, ticketId);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return rs.getString("status");
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return null;
}
}