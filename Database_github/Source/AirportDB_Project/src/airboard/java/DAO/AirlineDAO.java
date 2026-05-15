package airboard.java.DAO;

import airboard.java.DB.DBConnection;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class AirlineDAO {

    public DefaultTableModel getAllAirlines() {
        String[] columns = {"ID", "Name", "Country"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        String sql = "SELECT airline_id, name, country FROM Airline";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("airline_id"),
                        rs.getString("name"),
                        rs.getString("country")
                });
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return model;
    }

    public boolean addAirline(String name, String country) {
        String sql = "INSERT INTO Airline (name, country) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setString(2, country);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateAirline(int airlineId, String name, String country) {
        String sql = "UPDATE Airline SET name = ?, country = ? WHERE airline_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setString(2, country);
            ps.setInt(3, airlineId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteAirline(int airlineId) {
        String sql = "DELETE FROM Airline WHERE airline_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, airlineId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public DefaultTableModel searchAirlineByName(String name) {
        String[] columns = {"ID", "Name", "Country"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        String sql = "SELECT airline_id, name, country FROM Airline WHERE name LIKE ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + name + "%");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("airline_id"),
                        rs.getString("name"),
                        rs.getString("country")
                });
            }

            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return model;
    }
}