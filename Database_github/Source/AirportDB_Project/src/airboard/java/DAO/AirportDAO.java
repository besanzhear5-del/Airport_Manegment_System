package airboard.java.DAO;

import airboard.java.DB.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;

public class AirportDAO {

    public DefaultTableModel getAllAirports() {
        String[] columns = {"ID", "Code", "Name", "City", "Country"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        String sql = "SELECT airport_id, code, name, city, country FROM Airport";

        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Object[] row = {
                    rs.getInt("airport_id"),
                    rs.getString("code"),
                    rs.getString("name"),
                    rs.getString("city"),
                    rs.getString("country")
                };

                model.addRow(row);
            }

            rs.close();
            ps.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return model;
    }

    public boolean addAirport(String code, String name, String city, String country) {
        String sql = "INSERT INTO Airport (code, name, city, country) VALUES (?, ?, ?, ?)";

        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, code);
            ps.setString(2, name);
            ps.setString(3, city);
            ps.setString(4, country);

            int rows = ps.executeUpdate();

            ps.close();
            conn.close();

            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateAirport(int airportId, String code, String name, String city, String country) {
        String sql = "UPDATE Airport SET code = ?, name = ?, city = ?, country = ? WHERE airport_id = ?";

        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, code);
            ps.setString(2, name);
            ps.setString(3, city);
            ps.setString(4, country);
            ps.setInt(5, airportId);

            int rows = ps.executeUpdate();

            ps.close();
            conn.close();

            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteAirport(int airportId) {
        String sql = "DELETE FROM Airport WHERE airport_id = ?";

        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, airportId);

            int rows = ps.executeUpdate();

            ps.close();
            conn.close();

            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public DefaultTableModel searchAirportByCode(String code) {
        String[] columns = {"ID", "Code", "Name", "City", "Country"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        String sql = "SELECT airport_id, code, name, city, country FROM Airport WHERE code LIKE ?";

        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, "%" + code + "%");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Object[] row = {
                    rs.getInt("airport_id"),
                    rs.getString("code"),
                    rs.getString("name"),
                    rs.getString("city"),
                    rs.getString("country")
                };

                model.addRow(row);
            }

            rs.close();
            ps.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return model;
    }
}