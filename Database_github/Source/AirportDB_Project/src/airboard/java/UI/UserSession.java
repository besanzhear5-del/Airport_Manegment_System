package airboard.java.UI;

public class UserSession {

    private static int passengerId = 0;
    private static String passengerName = "Passenger";

    public static void setPassengerId(int id) {
        passengerId = id;
    }

    public static int getPassengerId() {
        return passengerId;
    }

    public static boolean hasPassengerProfile() {
        return passengerId != 0;
    }

    public static void setPassengerName(String name) {
        passengerName = name;
    }

    public static String getPassengerName() {
        return passengerName;
    }

    public static void clearPassenger() {
        passengerId = 0;
        passengerName = "Passenger";
    }
}