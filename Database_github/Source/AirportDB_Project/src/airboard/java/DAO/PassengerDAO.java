package airboard.java.DAO;

import airboard.java.DB.DBConnection;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class PassengerDAO {

    public DefaultTableModel getAllPassengers() {
        String[] columns = {"ID", "First Name", "Last Name", "Passport", "Contact"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        String sql = "SELECT passenger_id, first_name, last_name, passport_number, contact_info FROM Passenger";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("passenger_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("passport_number"),
                        rs.getString("contact_info")
                });
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return model;
    }

    public boolean addPassenger(String firstName, String lastName, String passportNumber, String contactInfo) {
        String sql = "INSERT INTO Passenger (first_name, last_name, passport_number, contact_info) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ps.setString(3, passportNumber);
            ps.setString(4, contactInfo);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updatePassenger(int passengerId, String firstName, String lastName, String passportNumber, String contactInfo) {
        String sql = "UPDATE Passenger SET first_name = ?, last_name = ?, passport_number = ?, contact_info = ? WHERE passenger_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ps.setString(3, passportNumber);
            ps.setString(4, contactInfo);
            ps.setInt(5, passengerId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deletePassenger(int passengerId) {
        String sql = "DELETE FROM Passenger WHERE passenger_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, passengerId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public DefaultTableModel searchPassengerByPassport(String passport) {
        String[] columns = {"ID", "First Name", "Last Name", "Passport", "Contact"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        String sql = """
                SELECT passenger_id, first_name, last_name, passport_number, contact_info
                FROM Passenger
                WHERE passport_number LIKE ?
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + passport + "%");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("passenger_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("passport_number"),
                        rs.getString("contact_info")
                });
            }

            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return model;
    }
    public int addPassengerAndReturnId(String firstName, String lastName, String passportNumber, String contactInfo) {
    String sql = "INSERT INTO Passenger (first_name, last_name, passport_number, contact_info) VALUES (?, ?, ?, ?)";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

        ps.setString(1, firstName);
        ps.setString(2, lastName);
        ps.setString(3, passportNumber);
        ps.setString(4, contactInfo);

        int rows = ps.executeUpdate();

        if (rows > 0) {
            ResultSet keys = ps.getGeneratedKeys();

            if (keys.next()) {
                int newId = keys.getInt(1);
                keys.close();
                return newId;
            }

            keys.close();
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return -1;
}
}