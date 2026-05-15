package airboard.java.UI;

public class EmployeeSession {

    private static int employeeId = -1;
    private static String employeeName = "";
    private static String employeeRole = "";
    private static String crewPosition = "";

    public static void setEmployee(int id, String name, String role, String position) {
        employeeId = id;
        employeeName = name;
        employeeRole = role;
        crewPosition = position;
    }

    public static int getEmployeeId() {
        return employeeId;
    }

    public static String getEmployeeName() {
        return employeeName;
    }

    public static String getEmployeeRole() {
        return employeeRole;
    }

    public static String getCrewPosition() {
        return crewPosition;
    }

    public static boolean isPilot() {
        return employeeRole.equalsIgnoreCase("Pilot");
    }

    public static boolean isCabinCrew() {
        return employeeRole.equalsIgnoreCase("Crew")
                && crewPosition.equalsIgnoreCase("Cabin");
    }

    public static boolean isGroundCrew() {
        return employeeRole.equalsIgnoreCase("Crew")
                && crewPosition.equalsIgnoreCase("Ground");
    }

    public static void clear() {
        employeeId = -1;
        employeeName = "";
        employeeRole = "";
        crewPosition = "";
    }
}