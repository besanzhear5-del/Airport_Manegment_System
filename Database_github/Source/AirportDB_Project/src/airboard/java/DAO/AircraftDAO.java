package airboard.java.DAO;

import airboard.java.DB.DBConnection;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class AircraftDAO {

    public DefaultTableModel getAllAircraft() {
        String[] columns = {"ID", "Model", "Capacity", "Airline ID", "Airline"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        String sql = """
                SELECT ac.aircraft_id, ac.model, ac.capacity, ac.airline_id, al.name AS airline_name
                FROM Aircraft ac
                LEFT JOIN Airline al ON ac.airline_id = al.airline_id
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("aircraft_id"),
                        rs.getString("model"),
                        rs.getInt("capacity"),
                        rs.getInt("airline_id"),
                        rs.getString("airline_name")
                });
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return model;
    }

    public boolean addAircraft(String model, int capacity, int airlineId) {
        String sql = "INSERT INTO Aircraft (model, capacity, airline_id) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, model);
            ps.setInt(2, capacity);
            ps.setInt(3, airlineId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateAircraft(int aircraftId, String model, int capacity, int airlineId) {
        String sql = "UPDATE Aircraft SET model = ?, capacity = ?, airline_id = ? WHERE aircraft_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, model);
            ps.setInt(2, capacity);
            ps.setInt(3, airlineId);
            ps.setInt(4, aircraftId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteAircraft(int aircraftId) {
        String sql = "DELETE FROM Aircraft WHERE aircraft_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, aircraftId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public DefaultTableModel searchAircraftByModel(String modelText) {
        String[] columns = {"ID", "Model", "Capacity", "Airline ID", "Airline"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        String sql = """
                SELECT ac.aircraft_id, ac.model, ac.capacity, ac.airline_id, al.name AS airline_name
                FROM Aircraft ac
                LEFT JOIN Airline al ON ac.airline_id = al.airline_id
                WHERE ac.model LIKE ?
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + modelText + "%");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("aircraft_id"),
                        rs.getString("model"),
                        rs.getInt("capacity"),
                        rs.getInt("airline_id"),
                        rs.getString("airline_name")
                });
            }

            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return model;
    }
}