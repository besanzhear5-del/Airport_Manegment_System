package airboard.java.DAO;

import airboard.java.DB.DBConnection;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class TicketDAO {

    public DefaultTableModel getAllTickets() {
        String[] columns = {
                "ID", "Passenger ID", "Passenger", "Flight ID", "Flight",
                "Seat", "Booking Date", "Class", "Price", "Status"
        };

        DefaultTableModel model = new DefaultTableModel(columns, 0);

        String sql = """
                SELECT t.ticket_id, t.passenger_id,
                       CONCAT(p.first_name, ' ', p.last_name) AS passenger_name,
                       t.flight_id, f.flight_number,
                       t.seat_number, t.booking_date, t.class, t.price, t.status
                FROM Ticket t
                LEFT JOIN Passenger p ON t.passenger_id = p.passenger_id
                LEFT JOIN Flight f ON t.flight_id = f.flight_id
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("ticket_id"),
                        rs.getInt("passenger_id"),
                        rs.getString("passenger_name"),
                        rs.getInt("flight_id"),
                        rs.getString("flight_number"),
                        rs.getString("seat_number"),
                        rs.getDate("booking_date"),
                        rs.getString("class"),
                        rs.getBigDecimal("price"),
                        rs.getString("status")
                });
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return model;
    }

    public boolean addTicket(
            int passengerId,
            int flightId,
            String seatNumber,
            String bookingDate,
            String ticketClass,
            double price,
            String status
    ) {
        String sql = """
                INSERT INTO Ticket
                (passenger_id, flight_id, seat_number, booking_date, class, price, status)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, passengerId);
            ps.setInt(2, flightId);
            ps.setString(3, seatNumber);
            ps.setDate(4, Date.valueOf(bookingDate));
            ps.setString(5, ticketClass);
            ps.setDouble(6, price);
            ps.setString(7, status);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateTicket(
            int ticketId,
            int passengerId,
            int flightId,
            String seatNumber,
            String bookingDate,
            String ticketClass,
            double price,
            String status
    ) {
        String sql = """
                UPDATE Ticket
                SET passenger_id = ?, flight_id = ?, seat_number = ?, booking_date = ?, class = ?, price = ?, status = ?
                WHERE ticket_id = ?
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, passengerId);
            ps.setInt(2, flightId);
            ps.setString(3, seatNumber);
            ps.setDate(4, Date.valueOf(bookingDate));
            ps.setString(5, ticketClass);
            ps.setDouble(6, price);
            ps.setString(7, status);
            ps.setInt(8, ticketId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteTicket(int ticketId) {
        String sql = "DELETE FROM Ticket WHERE ticket_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, ticketId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public DefaultTableModel searchTicketById(int ticketId) {
        String[] columns = {
                "ID", "Passenger ID", "Passenger", "Flight ID", "Flight",
                "Seat", "Booking Date", "Class", "Price", "Status"
        };

        DefaultTableModel model = new DefaultTableModel(columns, 0);

        String sql = """
                SELECT t.ticket_id, t.passenger_id,
                       CONCAT(p.first_name, ' ', p.last_name) AS passenger_name,
                       t.flight_id, f.flight_number,
                       t.seat_number, t.booking_date, t.class, t.price, t.status
                FROM Ticket t
                LEFT JOIN Passenger p ON t.passenger_id = p.passenger_id
                LEFT JOIN Flight f ON t.flight_id = f.flight_id
                WHERE t.ticket_id = ?
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, ticketId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("ticket_id"),
                        rs.getInt("passenger_id"),
                        rs.getString("passenger_name"),
                        rs.getInt("flight_id"),
                        rs.getString("flight_number"),
                        rs.getString("seat_number"),
                        rs.getDate("booking_date"),
                        rs.getString("class"),
                        rs.getBigDecimal("price"),
                        rs.getString("status")
                });
            }

            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return model;
    }
    public DefaultTableModel getTicketsByPassengerId(int passengerId) {
    String[] columns = {
            "ID", "Passenger ID", "Passenger", "Flight ID", "Flight",
            "Seat", "Booking Date", "Class", "Price", "Status"
    };

    DefaultTableModel model = new DefaultTableModel(columns, 0);

    String sql = """
            SELECT t.ticket_id, t.passenger_id,
                   CONCAT(p.first_name, ' ', p.last_name) AS passenger_name,
                   t.flight_id, f.flight_number,
                   t.seat_number, t.booking_date, t.class, t.price, t.status
            FROM Ticket t
            LEFT JOIN Passenger p ON t.passenger_id = p.passenger_id
            LEFT JOIN Flight f ON t.flight_id = f.flight_id
            WHERE t.passenger_id = ?
            """;

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, passengerId);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            model.addRow(new Object[]{
                    rs.getInt("ticket_id"),
                    rs.getInt("passenger_id"),
                    rs.getString("passenger_name"),
                    rs.getInt("flight_id"),
                    rs.getString("flight_number"),
                    rs.getString("seat_number"),
                    rs.getDate("booking_date"),
                    rs.getString("class"),
                    rs.getBigDecimal("price"),
                    rs.getString("status")
            });
        }

        rs.close();

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return model;
}

public boolean deleteTicketForPassenger(int ticketId, int passengerId) {
    String sql = "DELETE FROM Ticket WHERE ticket_id = ? AND passenger_id = ?";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, ticketId);
        ps.setInt(2, passengerId);

        return ps.executeUpdate() > 0;

    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}
}