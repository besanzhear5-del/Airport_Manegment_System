package airboard.java.DAO;

import airboard.java.DB.DBConnection;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class AircraftStatusDAO {

    public DefaultTableModel getAllAircrafts() {

        String[] columns = {
                "Aircraft ID",
                "Model",
                "Capacity",
                "Airline ID",
                "Current Status"
        };

        DefaultTableModel model = new DefaultTableModel(columns, 0);

        String sql = """
                SELECT aircraft_id,
                       model,
                       capacity,
                       airline_id,
                       current_status
                FROM Aircraft
                ORDER BY aircraft_id
                """;

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("aircraft_id"),
                        rs.getString("model"),
                        rs.getInt("capacity"),
                        rs.getInt("airline_id"),
                        rs.getString("current_status")
                });
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return model;
    }

    public boolean updateAircraftStatus(int aircraftId, String status, String description) {

        String updateAircraftSql = """
                UPDATE Aircraft
                SET current_status = ?
                WHERE aircraft_id = ?
                """;

        String insertMaintenanceSql = """
                INSERT INTO Maintenance (aircraft_id, description, date, status)
                VALUES (?, ?, CURDATE(), ?)
                """;

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement updatePs = conn.prepareStatement(updateAircraftSql);
                PreparedStatement insertPs = conn.prepareStatement(insertMaintenanceSql)
        ) {
            updatePs.setString(1, status);
            updatePs.setInt(2, aircraftId);

            int updated = updatePs.executeUpdate();

            if (updated > 0) {
                insertPs.setInt(1, aircraftId);
                insertPs.setString(2, description);
                insertPs.setString(3, status);

                insertPs.executeUpdate();
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public DefaultTableModel getPilotAircraftStatus(int pilotId) {

        String[] columns = {
                "Aircraft ID",
                "Model",
                "Airline",
                "Current Status",
                "Last Maintenance Description",
                "Maintenance Date"
        };

        DefaultTableModel model = new DefaultTableModel(columns, 0);

        String sql = """
                SELECT DISTINCT
                       a.aircraft_id,
                       a.model,
                       al.name AS airline_name,
                       a.current_status,
                       m.description,
                       m.date
                FROM Flight_Pilot fp
                JOIN Flight f ON fp.flight_id = f.flight_id
                JOIN Aircraft a ON f.aircraft_id = a.aircraft_id
                LEFT JOIN Airline al ON a.airline_id = al.airline_id
                LEFT JOIN Maintenance m
                       ON m.maintenance_id = (
                            SELECT m2.maintenance_id
                            FROM Maintenance m2
                            WHERE m2.aircraft_id = a.aircraft_id
                            ORDER BY m2.date DESC, m2.maintenance_id DESC
                            LIMIT 1
                       )
                WHERE fp.pilot_id = ?
                ORDER BY a.aircraft_id
                """;

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setInt(1, pilotId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("aircraft_id"),
                        rs.getString("model"),
                        rs.getString("airline_name"),
                        rs.getString("current_status"),
                        rs.getString("description"),
                        rs.getDate("date")
                });
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return model;
    }
}