package airboard.java.DAO;

import airboard.java.DB.DBConnection;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class EmployeeDAO {

    public DefaultTableModel getAllEmployeesWithDetails() {
        String[] columns = {
            "ID", "Name", "Role", "Contact", "Airport ID", "Airport",
            "Password", "License", "Position", "Shift", "Experience"
        };

        DefaultTableModel model = new DefaultTableModel(columns, 0);

        String sql = """
            SELECT e.employee_id, e.name, e.role, e.contact_info, e.airport_id,
                   a.code AS airport_code, e.employee_password,
                   p.license_number,
                   p.experience_years AS pilot_experience,
                   c.position,
                   c.shift,
                   c.experience_years AS crew_experience
            FROM Employee e
            LEFT JOIN Airport a ON e.airport_id = a.airport_id
            LEFT JOIN Pilot p ON e.employee_id = p.employee_id
            LEFT JOIN Crew c ON e.employee_id = c.employee_id
            ORDER BY e.employee_id
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String license = "";
                String position = "";
                String shift = "";
                int experience = 0;

                if (rs.getString("license_number") != null) {
                    license = rs.getString("license_number");
                    experience = rs.getInt("pilot_experience");
                }

                if (rs.getString("position") != null) {
                    position = rs.getString("position");
                    shift = rs.getString("shift");
                    experience = rs.getInt("crew_experience");
                }

                model.addRow(new Object[]{
                    rs.getInt("employee_id"),
                    rs.getString("name"),
                    rs.getString("role"),
                    rs.getString("contact_info"),
                    rs.getInt("airport_id"),
                    rs.getString("airport_code"),
                    rs.getString("employee_password"),
                    license,
                    position,
                    shift,
                    experience
                });
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return model;
    }

    public boolean addEmployeeWithRole(
            String name,
            String role,
            String contactInfo,
            int airportId,
            String password,
            String licenseNumber,
            String position,
            String shift,
            int experienceYears
    ) {
        String employeeSql = """
            INSERT INTO Employee (name, role, contact_info, airport_id, employee_password)
            VALUES (?, ?, ?, ?, ?)
        """;

        String pilotSql = """
            INSERT INTO Pilot (employee_id, license_number, experience_years)
            VALUES (?, ?, ?)
        """;

        String crewSql = """
            INSERT INTO Crew (employee_id, position, shift, experience_years)
            VALUES (?, ?, ?, ?)
        """;

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement empPs = conn.prepareStatement(employeeSql, Statement.RETURN_GENERATED_KEYS)) {
                empPs.setString(1, name);
                empPs.setString(2, role);
                empPs.setString(3, contactInfo);
                empPs.setInt(4, airportId);
                empPs.setString(5, password);

                int rows = empPs.executeUpdate();

                if (rows == 0) {
                    conn.rollback();
                    return false;
                }

                ResultSet keys = empPs.getGeneratedKeys();

                if (!keys.next()) {
                    conn.rollback();
                    return false;
                }

                int newEmployeeId = keys.getInt(1);
                keys.close();

                if (role.equals("Pilot")) {
                    try (PreparedStatement pilotPs = conn.prepareStatement(pilotSql)) {
                        pilotPs.setInt(1, newEmployeeId);
                        pilotPs.setString(2, licenseNumber);
                        pilotPs.setInt(3, experienceYears);
                        pilotPs.executeUpdate();
                    }
                } else if (role.equals("Crew")) {
                    try (PreparedStatement crewPs = conn.prepareStatement(crewSql)) {
                        crewPs.setInt(1, newEmployeeId);
                        crewPs.setString(2, position);
                        crewPs.setString(3, shift);
                        crewPs.setInt(4, experienceYears);
                        crewPs.executeUpdate();
                    }
                }

                conn.commit();
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateEmployeeWithRole(
            int employeeId,
            String name,
            String role,
            String contactInfo,
            int airportId,
            String password,
            String licenseNumber,
            String position,
            String shift,
            int experienceYears
    ) {
        String updateEmployeeSql = """
            UPDATE Employee
            SET name = ?, role = ?, contact_info = ?, airport_id = ?, employee_password = ?
            WHERE employee_id = ?
        """;

        String deletePilotSql = "DELETE FROM Pilot WHERE employee_id = ?";
        String deleteCrewSql = "DELETE FROM Crew WHERE employee_id = ?";

        String insertPilotSql = """
            INSERT INTO Pilot (employee_id, license_number, experience_years)
            VALUES (?, ?, ?)
        """;

        String insertCrewSql = """
            INSERT INTO Crew (employee_id, position, shift, experience_years)
            VALUES (?, ?, ?, ?)
        """;

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement ps = conn.prepareStatement(updateEmployeeSql)) {
                ps.setString(1, name);
                ps.setString(2, role);
                ps.setString(3, contactInfo);
                ps.setInt(4, airportId);
                ps.setString(5, password);
                ps.setInt(6, employeeId);
                ps.executeUpdate();
            }

            try (PreparedStatement ps = conn.prepareStatement(deletePilotSql)) {
                ps.setInt(1, employeeId);
                ps.executeUpdate();
            }

            try (PreparedStatement ps = conn.prepareStatement(deleteCrewSql)) {
                ps.setInt(1, employeeId);
                ps.executeUpdate();
            }

            if (role.equals("Pilot")) {
                try (PreparedStatement ps = conn.prepareStatement(insertPilotSql)) {
                    ps.setInt(1, employeeId);
                    ps.setString(2, licenseNumber);
                    ps.setInt(3, experienceYears);
                    ps.executeUpdate();
                }
            } else if (role.equals("Crew")) {
                try (PreparedStatement ps = conn.prepareStatement(insertCrewSql)) {
                    ps.setInt(1, employeeId);
                    ps.setString(2, position);
                    ps.setString(3, shift);
                    ps.setInt(4, experienceYears);
                    ps.executeUpdate();
                }
            }

            conn.commit();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteEmployee(int employeeId) {
        String sql = "DELETE FROM Employee WHERE employee_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, employeeId);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public DefaultTableModel searchEmployeeByName(String nameText) {
        String[] columns = {
            "ID", "Name", "Role", "Contact", "Airport ID", "Airport",
            "Password", "License", "Position", "Shift", "Experience"
        };

        DefaultTableModel model = new DefaultTableModel(columns, 0);

        String sql = """
            SELECT e.employee_id, e.name, e.role, e.contact_info, e.airport_id,
                   a.code AS airport_code, e.employee_password,
                   p.license_number,
                   p.experience_years AS pilot_experience,
                   c.position,
                   c.shift,
                   c.experience_years AS crew_experience
            FROM Employee e
            LEFT JOIN Airport a ON e.airport_id = a.airport_id
            LEFT JOIN Pilot p ON e.employee_id = p.employee_id
            LEFT JOIN Crew c ON e.employee_id = c.employee_id
            WHERE e.name LIKE ?
            ORDER BY e.employee_id
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + nameText + "%");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String license = "";
                String position = "";
                String shift = "";
                int experience = 0;

                if (rs.getString("license_number") != null) {
                    license = rs.getString("license_number");
                    experience = rs.getInt("pilot_experience");
                }

                if (rs.getString("position") != null) {
                    position = rs.getString("position");
                    shift = rs.getString("shift");
                    experience = rs.getInt("crew_experience");
                }

                model.addRow(new Object[]{
                    rs.getInt("employee_id"),
                    rs.getString("name"),
                    rs.getString("role"),
                    rs.getString("contact_info"),
                    rs.getInt("airport_id"),
                    rs.getString("airport_code"),
                    rs.getString("employee_password"),
                    license,
                    position,
                    shift,
                    experience
                });
            }

            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return model;
    }

    public boolean validateEmployeeLogin(int employeeId, String password) {
        String sql = """
            SELECT employee_id
            FROM Employee
            WHERE employee_id = ? AND employee_password = ?
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, employeeId);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            boolean exists = rs.next();
            rs.close();

            return exists;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getEmployeeNameById(int employeeId) {
        String sql = "SELECT name FROM Employee WHERE employee_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, employeeId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String name = rs.getString("name");
                rs.close();
                return name;
            }

            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "Employee";
    }
   public String getEmployeeRoleById(int employeeId) {
    String sql = "SELECT role FROM Employee WHERE employee_id = ?";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, employeeId);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            String role = rs.getString("role");
            rs.close();
            return role;
        }

        rs.close();

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return "";
}

public String getCrewPositionById(int employeeId) {
    String sql = "SELECT position FROM Crew WHERE employee_id = ?";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, employeeId);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            String position = rs.getString("position");
            rs.close();
            return position;
        }

        rs.close();

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return "";
}
}