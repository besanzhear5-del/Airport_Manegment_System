package airboard.java.UI;

import java.util.ArrayList;

public class NotificationCenter {

    // =========================
    // Passenger Notifications
    // =========================

    private static ArrayList<String> passengerNotifications = new ArrayList<>();

    public static void addPassengerNotification(String message) {
        passengerNotifications.add("• " + message);
    }

    public static String getPassengerNotificationsText() {

        if (passengerNotifications.isEmpty()) {
            return "• No passenger notifications.";
        }

        StringBuilder sb = new StringBuilder();

        for (String notification : passengerNotifications) {
            sb.append(notification).append("\n");
        }

        return sb.toString();
    }

    // =========================
    // Admin Notifications
    // =========================

    private static ArrayList<String> adminNotifications = new ArrayList<>();

    public static void addAdminNotification(String message) {
        adminNotifications.add("• " + message);
    }

    public static String getAdminNotificationsText() {

        if (adminNotifications.isEmpty()) {
            return "• No admin notifications.";
        }

        StringBuilder sb = new StringBuilder();

        for (String notification : adminNotifications) {
            sb.append(notification).append("\n");
        }

        return sb.toString();
    }
    private static ArrayList<String> groundCrewNotifications = new ArrayList<>();

public static void addGroundCrewNotification(String message) {
    groundCrewNotifications.add("• " + message);
}

public static String getGroundCrewNotificationsText() {

    if (groundCrewNotifications.isEmpty()) {
        return "• No ground crew notifications.";
    }

    StringBuilder sb = new StringBuilder();

    for (String notification : groundCrewNotifications) {
        sb.append(notification).append("\n");
    }

    return sb.toString();
}

}