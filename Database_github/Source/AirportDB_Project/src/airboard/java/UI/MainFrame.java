package airboard.java.UI;

import airboard.java.DAO.FlightDAO;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainFrame extends JFrame {

    private String username;
    private String role;
    private String pageName;
    private int loggedEmployeeId;

    private JLabel clockLabel;
    private JTextArea notificationArea;

    // ✅ Constructor الأساسي
    public MainFrame(String username, String role) {
        this(username, role, "Dashboard", 0);
    }

    // ✅ Constructor كامل
    public MainFrame(String username, String role, String pageName, int loggedEmployeeId) {
        this.username = username;
        this.role = role;
        this.pageName = pageName;
        this.loggedEmployeeId = loggedEmployeeId;

        setTitle("The Royal Journey - Main Dashboard");
        setSize(1220, 720);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        initComponents();
        startClock();
    }

    private void initComponents() {
        JPanel mainPanel = new BackgroundPanel("/airboard/java/images/airport_bg.jpg");

        JPanel sidebar = buildSidebar();
        sidebar.setBounds(0, 0, 220, 720);
        mainPanel.add(sidebar);

        JPanel contentPanel = new JPanel(null);
       contentPanel.setBackground(new Color(255, 255, 255, 220));
       contentPanel.setOpaque(true);
        contentPanel.setBounds(240, 25, 940, 640);
        contentPanel.setBorder(new RoundedBorder(new Color(205, 170, 80), 2, 24));
        mainPanel.add(contentPanel);

        JLabel title = new JLabel("Dashboard Overview");
        title.setFont(new Font("Segoe UI", Font.BOLD, 30));
        title.setForeground(new Color(10, 25, 47));
        title.setBounds(35, 25, 420, 40);
        contentPanel.add(title);

        JLabel subtitle = new JLabel("Live airport system snapshot");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitle.setForeground(new Color(110, 110, 110));
        subtitle.setBounds(38, 63, 400, 22);
        contentPanel.add(subtitle);

        JLabel tableTitle = new JLabel(getTableTitle());
        tableTitle.setFont(new Font("Segoe UI", Font.BOLD, 23));
        tableTitle.setForeground(new Color(10, 25, 47));
        tableTitle.setBounds(35, 110, 350, 35);
        contentPanel.add(tableTitle);

        JTable table = buildMainTable();

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(35, 150, 610, 330);
        scrollPane.setBorder(new RoundedBorder(new Color(205, 170, 80), 1, 18));
        contentPanel.add(scrollPane);

        JPanel liveInfo = buildLiveInfoPanel();
        liveInfo.setBounds(670, 115, 240, 365);
        contentPanel.add(liveInfo);

        add(mainPanel);
    }

    private JPanel buildSidebar() {
        JPanel sidebar = new JPanel(null);
        sidebar.setBackground(new Color(6, 16, 32));

        JLabel logo = new JLabel("✈️ The Royal Journey");
        logo.setFont(new Font("Dialog", Font.BOLD, 16));
        logo.setForeground(Color.WHITE);
        logo.setBounds(20, 25, 180, 25);
        sidebar.add(logo);

        JLabel menu = new JLabel("MENU");
        menu.setFont(new Font("Dialog", Font.BOLD, 12));
        menu.setForeground(new Color(190, 190, 190));
        menu.setBounds(25, 80, 120, 20);
        sidebar.add(menu);

        String[] menuItems = getMenuItems();

        int y = 120;

        for (String item : menuItems) {
            JButton btn = createSidebarButton(item);
            btn.setBounds(25, y, 165, 35);
            sidebar.add(btn);
            y += 50;
        }

        JButton logout = new JButton("Logout");
        logout.setBounds(35, 635, 145, 35);
        logout.setBackground(new Color(205, 170, 80));
        logout.setForeground(Color.WHITE);
        logout.setFont(new Font("Segoe UI", Font.BOLD, 12));
        logout.setFocusPainted(false);
        logout.setBorder(new RoundedBorder(Color.WHITE, 1, 14));

        logout.addActionListener(e -> {
            dispose();
            new LoginFrame().setVisible(true);
        });

        sidebar.add(logout);

        return sidebar;
    }

    private String[] getMenuItems() {
        if ("Admin".equals(role)) {
    return new String[]{
            "Dashboard",
            "Airports",
            "Flights",
            "Aircraft",
            "Employees"       
    };

        } else if ("Pilot".equals(role)) {
            return new String[]{"Dashboard", "My Flights", "Flight Schedule", "Aircraft Status"};

        } else if ("Cabin Crew".equals(role)) {
            return new String[]{"Dashboard", "My Flight Passengers",
                     "Flight Schedule"};

        } else if ("Ground Crew".equals(role)) {

    return new String[]{
            "Dashboard",
            "Aircraft Operations",
            "Baggage Handling",
            "Gate Operations",
            "Boarding Pass",
            "Flight Schedule"
    }; 
        }else {
            return new String[]{"Dashboard", "Search Flights", "Book Ticket",
                    "My Tickets", "Baggage", "Profile"};
        }
    }

    private JButton createSidebarButton(String text) {
        JButton button = new JButton(text);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setBackground(new Color(16, 32, 56));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(new RoundedBorder(new Color(205, 170, 80), 1, 14));

        button.addActionListener(e -> {
            if (text.equals("Dashboard")) {
                // ✅ بدل إعادة فتح نفس الصفحة بشكل مزعج → فقط إعادة فتح بشكل نظيف
                new MainFrame(username, role, pageName, loggedEmployeeId).setVisible(true);
                dispose();
            } else {
                new RoleFeatureFrame(username, role, text, loggedEmployeeId).setVisible(true);
                dispose();
            }
        });

        return button;
    }

    private String getTableTitle() {
        if ("Passenger".equals(role)) return "Available Flights";
        if ("Pilot".equals(role)) return "Pilot Flight Overview";
        if ("Cabin Crew".equals(role)) return "Cabin Crew Overview";
        if ("Ground Crew".equals(role)) return "Ground Crew Overview";
        return "Upcoming Flights";
    }

    private JTable buildMainTable() {
    FlightDAO flightDAO = new FlightDAO();
    DefaultTableModel model = flightDAO.getAvailableFlightsForDashboard();

    JTable table = new JTable(model) {
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    table.setRowHeight(34);
    table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
    table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
    table.getTableHeader().setBackground(new Color(10, 25, 47));
    table.getTableHeader().setForeground(Color.WHITE);

    int statusColumn = table.getColumnCount() - 1;
    if (statusColumn >= 0) {
        table.getColumnModel().getColumn(statusColumn).setCellRenderer(new StatusRenderer());
    }

    return table;
}

    private JPanel buildLiveInfoPanel() {
        JPanel rightPanel = new JPanel(null);
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setBorder(new RoundedBorder(new Color(205, 170, 80), 1, 20));

        JLabel infoTitle = new JLabel("Live Info");
        JLabel notifLabel = new JLabel("Notifications");
        notifLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        notifLabel.setForeground(new Color(10, 25, 47));
        notifLabel.setBounds(20, 195, 150, 20);

rightPanel.add(notifLabel);
        infoTitle.setFont(new Font("Segoe UI", Font.BOLD, 21));
        infoTitle.setBounds(20, 15, 150, 28);
        rightPanel.add(infoTitle);

        clockLabel = new JLabel();
        clockLabel.setBounds(20, 68, 230, 28);
        clockLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));
        rightPanel.add(clockLabel);
       String displayUser = username;

if (role.equals("Passenger") && UserSession.hasPassengerProfile()) {
    displayUser = UserSession.getPassengerName();
}

JLabel userLabel = new JLabel("User: " + displayUser);
userLabel.setFont(new Font("Arial", Font.BOLD, 15));
userLabel.setForeground(new Color(10, 25, 47));
userLabel.setBounds(20, 118, 220, 26);
rightPanel.add(userLabel);

JLabel roleLabel = new JLabel("Role: " + role);
roleLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));
roleLabel.setForeground(new Color(90, 90, 90));
roleLabel.setBounds(20, 150, 220, 26);
rightPanel.add(roleLabel);
        NotificationCenter.addPassengerNotification(
        "Flight AI203 status changed to Boarding."
);
        
if (role.equalsIgnoreCase("Admin")) {

    notificationArea = new JTextArea(
            NotificationCenter.getAdminNotificationsText()
    );

} else {

    notificationArea = new JTextArea(
            NotificationCenter.getPassengerNotificationsText()
    );
}        notificationArea.setEditable(false);

          notificationArea.setLineWrap(true);

        notificationArea.setWrapStyleWord(true);    
        JScrollPane scroll = new JScrollPane(notificationArea);
        scroll.setBounds(20, 225, 200, 75);
        rightPanel.add(scroll);

        JButton refreshButton = new JButton("Refresh");
        refreshButton.setBounds(55, 315, 130, 35);

     refreshButton.addActionListener(e -> {

    if (role.equalsIgnoreCase("Admin")) {

    notificationArea.setText(
            NotificationCenter.getAdminNotificationsText()
    );

} else {

    notificationArea.setText(
            NotificationCenter.getPassengerNotificationsText()
    );
}

});
     rightPanel.add(refreshButton);

     return rightPanel;
    }
    
private String getNotificationsText() {

    if (role.equals("Passenger")) {

        return NotificationCenter.getPassengerNotificationsText();

    } else if (role.equals("Admin")) {

        return
                "• Airport system running normally.\n"
              + "• Flights database connected.\n"
              + "• No critical alerts.";

    } else {

        return
                "• Flight operations active.\n"
              + "• No urgent notifications.";
    }
}
    private void startClock() {
        javax.swing.Timer timer = new javax.swing.Timer(1000, e -> {
            SimpleDateFormat sdf = new SimpleDateFormat(
                    "EEE, dd MMM yyyy HH:mm:ss",
                    Locale.ENGLISH
            );
            clockLabel.setText(sdf.format(new Date()));
        });
        timer.setRepeats(true);
        timer.start();
    }

    // ===== Renderer =====
   private static class StatusRenderer extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(
            JTable table, Object value, boolean isSelected,
            boolean hasFocus, int row, int column) {

        Component c = super.getTableCellRendererComponent(
                table, value, isSelected, hasFocus, row, column);

        if (isSelected) return c;

        c.setForeground(Color.BLACK);
        c.setBackground(Color.WHITE);

        if (value == null) return c;

        String status = value.toString();

        if (status.equalsIgnoreCase("On Time")) {
            c.setBackground(new Color(46, 204, 113));
            c.setForeground(Color.WHITE);

        } else if (status.equalsIgnoreCase("Delayed")) {
            c.setBackground(new Color(241, 196, 15));
            c.setForeground(Color.BLACK);

        } else if (status.equalsIgnoreCase("Cancelled")) {
            c.setBackground(new Color(231, 76, 60));
            c.setForeground(Color.WHITE);

        } else if (status.equalsIgnoreCase("Boarding")) {
            c.setBackground(new Color(52, 152, 219));
            c.setForeground(Color.WHITE);
        }

        return c;
    }
}
    private static class RoundedBorder extends AbstractBorder {
        private Color color;
        private int thickness;
        private int radius;

        public RoundedBorder(Color color, int thickness, int radius) {
            this.color = color;
            this.thickness = thickness;
            this.radius = radius;
        }

        public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(color);
            g2.setStroke(new BasicStroke(thickness));
            g2.drawRoundRect(x, y, w - 1, h - 1, radius, radius);
        }

        public Insets getBorderInsets(Component c) {
            return new Insets(7, 10, 7, 10);
        }
    }
}