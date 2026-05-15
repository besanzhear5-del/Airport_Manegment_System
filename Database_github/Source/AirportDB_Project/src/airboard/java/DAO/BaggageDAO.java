package airboard.java.DAO;

import airboard.java.DB.DBConnection;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class BaggageDAO {

    public DefaultTableModel getBaggageByPassengerId(int passengerId) {
        String[] columns = {
                "Baggage ID", "Passenger ID", "Passenger", "Flight ID",
                "Flight", "Weight", "Status", "Current Location"
        };

        DefaultTableModel model = new DefaultTableModel(columns, 0);

        String sql = """
                SELECT b.baggage_id, b.passenger_id,
                       CONCAT(p.first_name, ' ', p.last_name) AS passenger_name,
                       b.flight_id, f.flight_number,
                       b.weight, b.status, b.current_location
                FROM Baggage b
                LEFT JOIN Passenger p ON b.passenger_id = p.passenger_id
                LEFT JOIN Flight f ON b.flight_id = f.flight_id
                WHERE b.passenger_id = ?
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, passengerId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("baggage_id"),
                        rs.getInt("passenger_id"),
                        rs.getString("passenger_name"),
                        rs.getInt("flight_id"),
                        rs.getString("flight_number"),
                        rs.getFloat("weight"),
                        rs.getString("status"),
                        rs.getString("current_location")
                });
            }

            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return model;
    }
    public boolean addBaggage(int passengerId, int flightId, float weight, String status, String currentLocation) {
    String sql = """
            INSERT INTO Baggage
            (passenger_id, flight_id, weight, status, current_location)
            VALUES (?, ?, ?, ?, ?)
            """;

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, passengerId);
        ps.setInt(2, flightId);
        ps.setFloat(3, weight);
        ps.setString(4, status);
        ps.setString(5, currentLocation);

        return ps.executeUpdate() > 0;

    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}
}