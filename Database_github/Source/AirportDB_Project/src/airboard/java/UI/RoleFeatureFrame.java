package airboard.java.UI;

import airboard.java.DAO.AirportDAO;
import airboard.java.DAO.FlightDAO;
import airboard.java.DAO.AircraftDAO;
import airboard.java.DAO.EmployeeDAO;
import airboard.java.DAO.PassengerDAO;
import airboard.java.DAO.TicketDAO;
import airboard.java.DAO.BaggageDAO;
import airboard.java.DAO.PilotDAO;
import airboard.java.DAO.AircraftStatusDAO;
import airboard.java.DAO.GroundCrewDAO;
import airboard.java.DAO.CabinCrewDAO;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class RoleFeatureFrame extends JFrame {

    private String username;
    private String role;
    private String pageName;
    private int loggedEmployeeId;

    public RoleFeatureFrame(
        String username,
        String role,
        String pageName,
        int loggedEmployeeId
) {

    this.username = username;

    this.role = role;

    this.pageName = pageName;

    this.loggedEmployeeId = loggedEmployeeId;

    initComponents();
    
    setSize(1220,720);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
}

    private void initComponents() {
       JPanel mainPanel = new BackgroundPanel("/airboard/java/images/airport_bg.jpg");

        JPanel sidebar = buildSidebar();
        sidebar.setBounds(0, 0, 220, 680);
        mainPanel.add(sidebar);

        JPanel contentPanel = new JPanel(null);
        contentPanel.setBackground(new Color(255, 255, 255, 220));
        contentPanel.setOpaque(true);
        contentPanel.setBounds(240, 25, 900, 600);
        contentPanel.setBorder(new RoundedBorder(new Color(205, 170, 80), 2, 24));
        mainPanel.add(contentPanel);

        JLabel title = new JLabel(pageName);
        title.setFont(new Font("Segoe UI", Font.BOLD, 30));
        title.setForeground(new Color(10, 25, 47));
        title.setBounds(35, 25, 400, 40);
        contentPanel.add(title);

        JLabel subtitle = new JLabel(role + " page for: " + username);
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitle.setForeground(new Color(110, 110, 110));
        subtitle.setBounds(38, 63, 400, 22);
        contentPanel.add(subtitle);

        buildPageContent(contentPanel);

        add(mainPanel);
    }

    private JPanel buildSidebar() {
        JPanel sidebar = new JPanel(null);
        sidebar.setBackground(new Color(5, 15, 3, 230));

        JLabel logo = new JLabel("✈ The Royal Journey");
        logo.setFont(new Font("Dialog", Font.BOLD, 16));
        logo.setForeground(Color.WHITE);
        logo.setBounds(20, 25, 180, 25);
        sidebar.add(logo);

        JLabel menu = new JLabel(role.toUpperCase() + " MENU");
        menu.setFont(new Font("Segoe UI", Font.BOLD, 11));
        menu.setForeground(new Color(190, 190, 190));
        menu.setBounds(25, 75, 160, 20);
        sidebar.add(menu);

        int y = 115;
        sidebar.add(menuButton("Dashboard", y));
        y += 50;

    if (role.equals("Admin")) {

    String[] items = {
            "Airports",
            "Flights",
            "Aircraft",
            "Employees"
                
    };

    for (String item : items) {
        sidebar.add(menuButton(item, y));
        y += 50;
    }

} else if (role.equals("Pilot")) {
    String[] items = {
            "My Flights",
            "Flight Schedule",
            "Aircraft Status"
             };

    for (String item : items) {
        sidebar.add(menuButton(item, y));
        y += 50;
    }

} else if (role.equals("Cabin Crew")) {
    String[] items = {
            "My Flight Passengers",
            "Flight Schedule"
    };

    for (String item : items) {
        sidebar.add(menuButton(item, y));
        y += 50;
    }

} else if (role.equals("Ground Crew")) {

    String[] items = {
            "Aircraft Operations",
            "Baggage Handling",
            "Gate Operations",
            "Boarding Pass",
            "Flight Schedule"
    };

    for (String item : items) {

        sidebar.add(menuButton(item, y));

        y += 50;
    }
} else {
    String[] items = {
            "Search Flights", "Book Ticket", "My Tickets", "Baggage", "Profile"
    };

    for (String item : items) {
        sidebar.add(menuButton(item, y));
        y += 50;
    }
}
        JButton logout = new JButton("Logout");
        logout.setBounds(35, 580, 145, 35);
        logout.setBackground(new Color(205, 170, 80));
        logout.setForeground(Color.WHITE);
        logout.setFont(new Font("Segoe UI", Font.BOLD, 12));
        logout.setFocusPainted(false);
        logout.setBorder(new RoundedBorder(Color.WHITE, 1, 14));
        logout.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            dispose();
        });
        sidebar.add(logout);

        return sidebar;
    }

    private JButton menuButton(String text, int y) {
        JButton button = new JButton(text);
        button.setBounds(25, y, 165, 35);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setBackground(new Color(16, 32, 56));
        button.setForeground(Color.WHITE);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

button.addMouseListener(new java.awt.event.MouseAdapter() {

    public void mouseEntered(java.awt.event.MouseEvent evt) {

        button.setBackground(
                new Color(30, 55, 90)
        );
    }

    public void mouseExited(java.awt.event.MouseEvent evt) {

        button.setBackground(
                new Color(16, 32, 56)
        );
    }
});
        button.setFocusPainted(false);
        button.setBorder(new RoundedBorder(new Color(205, 170, 80), 1, 14));

        button.addActionListener(e -> {
            if (text.equals("Dashboard")) {
               new MainFrame(username, role, "Dashboard", loggedEmployeeId).setVisible(true);
            } else {
               new RoleFeatureFrame(
        username,
        role,
        text,
        loggedEmployeeId
).setVisible(true);
            }
            dispose();
        });

        return button;
    }
   

   private void buildPageContent(JPanel panel) {

    // =========================
    // ADMIN
    // =========================

    if (pageName.equals("Airports")) {

        buildAirportManagement(panel);

    } else if (pageName.equals("Flights")) {

        buildFlightManagement(panel);

    } else if (pageName.equals("Aircraft")) {

        buildAircraftManagement(panel);

    } else if (pageName.equals("Employees")) {

        buildEmployeeManagement(panel);

    } else if (pageName.equals("Passengers")) {

        buildPassengerManagement(panel);

    } else if (pageName.equals("Tickets")) {

        buildTicketManagement(panel);

    } else if (pageName.equals("Reports")) {

        buildReportsPage(panel);

    } else if (pageName.equals("System Logs")) {

        buildSystemLogsPage(panel);

    } else if (pageName.equals("Backup DB")) {

        buildBackupPage(panel);

    }

    // =========================
    // PASSENGER
    // =========================

    else if (pageName.equals("Search Flights")) {

        buildSearchFlights(panel);

    } else if (pageName.equals("Book Ticket")) {

        buildBookTicket(panel);

    } else if (pageName.equals("My Tickets")) {

        buildMyTickets(panel);

    } else if (pageName.equals("Baggage")) {

        buildBaggage(panel);

    } else if (pageName.equals("Profile")) {

        buildProfile(panel);

    }

    // =========================
    // EMPLOYEE GENERAL
    // =========================

    else if (pageName.equals("Passenger Viewer")) {

        buildPassengerViewer(panel);

    } else if (pageName.equals("Ticket Check")) {

        buildTicketCheck(panel);

    } else if (pageName.equals("Boarding Pass")) {

       buildBoardingPassManagement(panel);

    } else if (pageName.equals("Tasks")) {

        buildSimplePage(
                panel,
                "Employee Tasks",
                "Assign crew, check aircraft availability, and update operations."
        );

    } else if (pageName.equals("Schedule")) {

        buildSearchFlights(panel);

    }

    // =========================
    // PILOT
    // =========================

    else if (pageName.equals("My Flights")) {

        buildPilotMyFlights(panel);

    } else if (pageName.equals("Flight Schedule")) {

        buildSearchFlights(panel);

    } else if (pageName.equals("Aircraft Status")) {

        buildAircraftStatus(panel);

    }

    // =========================
    // CABIN CREW
    // =========================

    else if (pageName.equals("My Flight Passengers")) {
    buildCabinCrewPassengers(panel);
}
      else if (pageName.equals("Cabin Tasks")) {

        buildCabinTasks(panel);

    }

    // =========================
    // GROUND CREW
    // =========================

    else if (pageName.equals("Aircraft Operations")) {

        buildCrewAircraftOperations(panel);

    } else if (pageName.equals("Baggage Handling")) {

        buildBaggageHandling(panel);

    } else if (pageName.equals("Gate Operations")) {

        buildGateOperations(panel);

    }

    // =========================
    // DEFAULT
    // =========================

    else {

        buildSimplePage(
                panel,
                pageName,
                "This page will be connected later."
        );
    }
}
    // ===================== AIRPORTS - MYSQL =====================

    private void buildAirportManagement(JPanel panel) {
        AirportDAO airportDAO = new AirportDAO();

        panel.add(label("Code:", 45, 115));
        JTextField codeField = field("", 130, 115);
        panel.add(codeField);

        panel.add(label("Name:", 330, 115));
        JTextField nameField = field("", 415, 115);
        nameField.setBounds(415, 115, 260, 32);
        panel.add(nameField);

        panel.add(label("City:", 45, 165));
        JTextField cityField = field("", 130, 165);
        panel.add(cityField);

        panel.add(label("Country:", 330, 165));
        JTextField countryField = field("", 415, 165);
        panel.add(countryField);

        JTable airportTable = createModelTable(airportDAO.getAllAirports());

        JScrollPane scrollPane = new JScrollPane(airportTable);
        scrollPane.setBounds(45, 230, 815, 250);
        scrollPane.setBorder(new RoundedBorder(new Color(205, 170, 80), 1, 18));
        panel.add(scrollPane);

        JButton addBtn = goldButton("Add");
        addBtn.setBounds(45, 505, 100, 35);
        panel.add(addBtn);

        JButton updateBtn = goldButton("Update");
        updateBtn.setBounds(165, 505, 110, 35);
        panel.add(updateBtn);

        JButton deleteBtn = goldButton("Delete");
        deleteBtn.setBounds(295, 505, 110, 35);
        panel.add(deleteBtn);

        JButton searchBtn = goldButton("Search");
        searchBtn.setBounds(425, 505, 110, 35);
        panel.add(searchBtn);

        JButton clearBtn = goldButton("Clear");
        clearBtn.setBounds(555, 505, 110, 35);
        panel.add(clearBtn);

        airportTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = airportTable.getSelectedRow();

                if (selectedRow >= 0) {
                    codeField.setText(airportTable.getValueAt(selectedRow, 1).toString());
                    nameField.setText(airportTable.getValueAt(selectedRow, 2).toString());
                    cityField.setText(airportTable.getValueAt(selectedRow, 3).toString());
                    countryField.setText(airportTable.getValueAt(selectedRow, 4).toString());
                }
            }
        });

        addBtn.addActionListener(e -> {
            String code = codeField.getText().trim();
            String name = nameField.getText().trim();
            String city = cityField.getText().trim();
            String country = countryField.getText().trim();

            if (code.isEmpty() || name.isEmpty() || city.isEmpty() || country.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all airport fields.");
                return;
            }

            boolean success = airportDAO.addAirport(code, name, city, country);

            if (success) {
                JOptionPane.showMessageDialog(this, "Airport added successfully.");
                airportTable.setModel(airportDAO.getAllAirports());
                clearAirportFields(airportTable, codeField, nameField, cityField, countryField);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add airport.");
            }
        });

        updateBtn.addActionListener(e -> {
            int selectedRow = airportTable.getSelectedRow();

            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select an airport to update.");
                return;
            }

            String code = codeField.getText().trim();
            String name = nameField.getText().trim();
            String city = cityField.getText().trim();
            String country = countryField.getText().trim();

            if (code.isEmpty() || name.isEmpty() || city.isEmpty() || country.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all airport fields.");
                return;
            }

            int airportId = Integer.parseInt(airportTable.getValueAt(selectedRow, 0).toString());

            boolean success = airportDAO.updateAirport(airportId, code, name, city, country);

            if (success) {
                JOptionPane.showMessageDialog(this, "Airport updated successfully.");
                airportTable.setModel(airportDAO.getAllAirports());
                clearAirportFields(airportTable, codeField, nameField, cityField, countryField);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update airport.");
            }
        });

        deleteBtn.addActionListener(e -> {
            int selectedRow = airportTable.getSelectedRow();

            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select an airport to delete.");
                return;
            }

            int choice = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to delete this airport?",
                    "Delete Confirmation",
                    JOptionPane.YES_NO_OPTION
            );

            if (choice == JOptionPane.YES_OPTION) {
                int airportId = Integer.parseInt(airportTable.getValueAt(selectedRow, 0).toString());

                boolean success = airportDAO.deleteAirport(airportId);

                if (success) {
                    JOptionPane.showMessageDialog(this, "Airport deleted successfully.");
                    airportTable.setModel(airportDAO.getAllAirports());
                    clearAirportFields(airportTable, codeField, nameField, cityField, countryField);
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to delete airport.");
                }
            }
        });

        searchBtn.addActionListener(e -> {
            String searchCode = codeField.getText().trim();

            if (searchCode.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Enter airport code to search.");
                return;
            }

            airportTable.setModel(airportDAO.searchAirportByCode(searchCode));

            if (airportTable.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "Airport not found.");
            }
        });

        clearBtn.addActionListener(e -> {
            airportTable.setModel(airportDAO.getAllAirports());
            clearAirportFields(airportTable, codeField, nameField, cityField, countryField);
        });
    }

    private void clearAirportFields(
            JTable airportTable,
            JTextField codeField,
            JTextField nameField,
            JTextField cityField,
            JTextField countryField
    ) {
        airportTable.clearSelection();
        codeField.setText("");
        nameField.setText("");
        cityField.setText("");
        countryField.setText("");
    }

    // ===================== FLIGHTS - MYSQL =====================

    private void buildFlightManagement(JPanel panel) {
        FlightDAO flightDAO = new FlightDAO();

        panel.add(label("Flight No:", 35, 105));
        JTextField flightNoField = field("", 130, 105);
        panel.add(flightNoField);

        panel.add(label("From ID:", 315, 105));
        JTextField fromIdField = field("", 410, 105);
        panel.add(fromIdField);

        panel.add(label("To ID:", 595, 105));
        JTextField toIdField = field("", 675, 105);
        panel.add(toIdField);

        panel.add(label("Aircraft ID:", 35, 150));
        JTextField aircraftIdField = field("", 130, 150);
        panel.add(aircraftIdField);

        panel.add(label("Airline ID:", 315, 150));
        JTextField airlineIdField = field("", 410, 150);
        panel.add(airlineIdField);

        panel.add(label("Status:", 595, 150));
        JComboBox<String> statusBox = combo(new String[]{"On Time", "Delayed", "Boarding", "Cancelled"}, 675, 150);
        panel.add(statusBox);

        panel.add(label("Departure:", 35, 195));
        JTextField departureField = field("2026-06-01 08:00:00", 130, 195);
        departureField.setBounds(130, 195, 170, 32);
        panel.add(departureField);

        panel.add(label("Arrival:", 315, 195));
        JTextField arrivalField = field("2026-06-01 16:00:00", 410, 195);
        arrivalField.setBounds(410, 195, 170, 32);
        panel.add(arrivalField);

        JTable flightTable = createModelTable(flightDAO.getAllFlights());

        if (flightTable.getColumnCount() > 10) {
            flightTable.getColumnModel().getColumn(10).setCellRenderer(new StatusRenderer());
        }

        JScrollPane scrollPane = new JScrollPane(flightTable);
        scrollPane.setBounds(35, 245, 825, 225);
        scrollPane.setBorder(new RoundedBorder(new Color(205, 170, 80), 1, 18));
        panel.add(scrollPane);

        JButton addBtn = goldButton("Add");
        addBtn.setBounds(45, 505, 100, 35);
        panel.add(addBtn);

        JButton updateBtn = goldButton("Update");
        updateBtn.setBounds(165, 505, 110, 35);
        panel.add(updateBtn);

        JButton deleteBtn = goldButton("Delete");
        deleteBtn.setBounds(295, 505, 110, 35);
        panel.add(deleteBtn);

        JButton searchBtn = goldButton("Search");
        searchBtn.setBounds(425, 505, 110, 35);
        panel.add(searchBtn);

        JButton clearBtn = goldButton("Clear");
        clearBtn.setBounds(555, 505, 110, 35);
        panel.add(clearBtn);

        if (!role.equals("Admin")) {
            addBtn.setEnabled(false);
            deleteBtn.setEnabled(false);
        }

        flightTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = flightTable.getSelectedRow();

                if (row >= 0) {
                    flightNoField.setText(flightTable.getValueAt(row, 1).toString());
                    fromIdField.setText(flightTable.getValueAt(row, 2).toString());
                    toIdField.setText(flightTable.getValueAt(row, 4).toString());
                    aircraftIdField.setText(flightTable.getValueAt(row, 6).toString());
                    airlineIdField.setText(flightTable.getValueAt(row, 7).toString());
                    departureField.setText(flightTable.getValueAt(row, 8).toString());
                    arrivalField.setText(flightTable.getValueAt(row, 9).toString());

                    if (flightTable.getValueAt(row, 10) != null) {
                        statusBox.setSelectedItem(flightTable.getValueAt(row, 10).toString());
                    }
                }
            }
        });

        addBtn.addActionListener(e -> {
            if (!role.equals("Admin")) {
                JOptionPane.showMessageDialog(this, "Access denied. Only Admin can add flights.");
                return;
            }

            try {
                String flightNumber = flightNoField.getText().trim();

                boolean success = flightDAO.addFlight(
                        flightNumber,
                        Integer.parseInt(fromIdField.getText().trim()),
                        Integer.parseInt(toIdField.getText().trim()),
                        Integer.parseInt(aircraftIdField.getText().trim()),
                        Integer.parseInt(airlineIdField.getText().trim()),
                        departureField.getText().trim(),
                        arrivalField.getText().trim(),
                        statusBox.getSelectedItem().toString()
                );

                if (success) {
                    JOptionPane.showMessageDialog(this, "Flight added successfully.");

                    NotificationCenter.addPassengerNotification(
                            "New flight added: " + flightNumber + " | Departure: " + departureField.getText().trim()
                    );

                    flightTable.setModel(flightDAO.getAllFlights());

                    if (flightTable.getColumnCount() > 10) {
                        flightTable.getColumnModel().getColumn(10).setCellRenderer(new StatusRenderer());
                    }

                    clearFlightFields(
                            flightTable,
                            flightNoField,
                            fromIdField,
                            toIdField,
                            aircraftIdField,
                            airlineIdField,
                            departureField,
                            arrivalField,
                            statusBox
                    );

                } else {
                    JOptionPane.showMessageDialog(
                            this,
                            "Failed to add flight.\nPossible reasons:\n- Flight number already exists.\n- Airport ID, Aircraft ID, or Airline ID does not exist.\n- Date format is incorrect."
                    );
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Please check IDs and date format.\nDate format: 2026-06-01 08:00:00");
            }
        });

        updateBtn.addActionListener(e -> {
            int row = flightTable.getSelectedRow();

            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Please select a flight to update.");
                return;
            }

            try {
                int flightId = Integer.parseInt(flightTable.getValueAt(row, 0).toString());
                String deletedFlightNumber = flightTable.getValueAt(row, 1).toString();

                boolean success = flightDAO.updateFlight(
                        flightId,
                        flightNoField.getText().trim(),
                        Integer.parseInt(fromIdField.getText().trim()),
                        Integer.parseInt(toIdField.getText().trim()),
                        Integer.parseInt(aircraftIdField.getText().trim()),
                        Integer.parseInt(airlineIdField.getText().trim()),
                        departureField.getText().trim(),
                        arrivalField.getText().trim(),
                        statusBox.getSelectedItem().toString()
                );

                if (success) {
                    JOptionPane.showMessageDialog(this, "Flight updated successfully.");
                    NotificationCenter.addPassengerNotification(
                    "Flight updated by Admin: " + flightNoField.getText().trim()
                    + " | Status: " + statusBox.getSelectedItem().toString()
                    + " | Departure: " + departureField.getText().trim()
);
                    NotificationCenter.addAdminNotification(
        "Flight "
                + flightNoField.getText().trim()
                + " status changed to "
                + statusBox.getSelectedItem().toString()
);

                    flightTable.setModel(flightDAO.getAllFlights());

                    if (flightTable.getColumnCount() > 10) {
                        flightTable.getColumnModel().getColumn(10).setCellRenderer(new StatusRenderer());
                    }

                    clearFlightFields(
                            flightTable,
                            flightNoField,
                            fromIdField,
                            toIdField,
                            aircraftIdField,
                            airlineIdField,
                            departureField,
                            arrivalField,
                            statusBox
                    );
                } 
                else {
                    JOptionPane.showMessageDialog(this, "Failed to update flight.");
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Please check selected flight, IDs, and date format.");
            }
        });

        deleteBtn.addActionListener(e -> {
            if (!role.equals("Admin")) {
                JOptionPane.showMessageDialog(this, "Access denied. Only Admin can delete flights.");
                return;
            }

            int row = flightTable.getSelectedRow();

            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Please select a flight to delete.");
                return;
            }

            int choice = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to delete this flight?",
                    "Delete Confirmation",
                    JOptionPane.YES_NO_OPTION
            );

            if (choice == JOptionPane.YES_OPTION) {
                int flightId = Integer.parseInt(flightTable.getValueAt(row, 0).toString());
                

               String deletedFlightNumber = flightTable.getValueAt(row, 1).toString();
                boolean success = flightDAO.deleteFlight(flightId);

               if (success) {

    JOptionPane.showMessageDialog(
            this,
            "Flight deleted successfully."
    );

    NotificationCenter.addPassengerNotification(
            "Flight cancelled by Admin: "
                    + deletedFlightNumber
    );

    NotificationCenter.addAdminNotification(
            "Flight "
                    + deletedFlightNumber
                    + " was deleted"
    );

    flightTable.setModel(flightDAO.getAllFlights());

    if (flightTable.getColumnCount() > 10) {
        flightTable.getColumnModel()
                .getColumn(10)
                .setCellRenderer(new StatusRenderer());
    }

    clearFlightFields(
            flightTable,
            flightNoField,
            fromIdField,
            toIdField,
            aircraftIdField,
            airlineIdField,
            departureField,
            arrivalField,
            statusBox
    );

} else {

    JOptionPane.showMessageDialog(
            this,
            "Failed to delete flight."
    );
}
            }
        });

        searchBtn.addActionListener(e -> {
            String flightNo = flightNoField.getText().trim();

            if (flightNo.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Enter flight number to search.");
                return;
            }

            flightTable.setModel(flightDAO.searchFlightsForPassenger(flightNo,
        "All",
        "All",
        "All",
        "All"));

            if (flightTable.getColumnCount() > 10) {
                flightTable.getColumnModel().getColumn(10).setCellRenderer(new StatusRenderer());
            }

            if (flightTable.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "Flight not found.");
            }
        });

        clearBtn.addActionListener(e -> {
            flightTable.setModel(flightDAO.getAllFlights());

            if (flightTable.getColumnCount() > 10) {
                flightTable.getColumnModel().getColumn(10).setCellRenderer(new StatusRenderer());
            }

            clearFlightFields(
                    flightTable,
                    flightNoField,
                    fromIdField,
                    toIdField,
                    aircraftIdField,
                    airlineIdField,
                    departureField,
                    arrivalField,
                    statusBox
            );
        });
    }

    private void clearFlightFields(
            JTable table,
            JTextField flightNoField,
            JTextField fromIdField,
            JTextField toIdField,
            JTextField aircraftIdField,
            JTextField airlineIdField,
            JTextField departureField,
            JTextField arrivalField,
            JComboBox<String> statusBox
    ) {
        table.clearSelection();
        flightNoField.setText("");
        fromIdField.setText("");
        toIdField.setText("");
        aircraftIdField.setText("");
        airlineIdField.setText("");
        departureField.setText("2026-06-01 08:00:00");
        arrivalField.setText("2026-06-01 16:00:00");
        statusBox.setSelectedIndex(0);
    }
    // ===================== AIRCRAFT - MYSQL =====================

    private void buildAircraftManagement(JPanel panel) {
        AircraftDAO aircraftDAO = new AircraftDAO();

        panel.add(label("Model:", 45, 120));
        JTextField modelField = field("", 140, 120);
        panel.add(modelField);

        panel.add(label("Capacity:", 330, 120));
        JTextField capacityField = field("", 425, 120);
        panel.add(capacityField);

        panel.add(label("Airline ID:", 600, 120));
        JTextField airlineIdField = field("", 700, 120);
        panel.add(airlineIdField);

        JTable aircraftTable = createModelTable(aircraftDAO.getAllAircraft());

        JScrollPane scrollPane = new JScrollPane(aircraftTable);
        scrollPane.setBounds(45, 190, 815, 285);
        scrollPane.setBorder(new RoundedBorder(new Color(205, 170, 80), 1, 18));
        panel.add(scrollPane);

        JButton addBtn = goldButton("Add");
        addBtn.setBounds(45, 505, 100, 35);
        panel.add(addBtn);

        JButton updateBtn = goldButton("Update");
        updateBtn.setBounds(165, 505, 110, 35);
        panel.add(updateBtn);

        JButton deleteBtn = goldButton("Delete");
        deleteBtn.setBounds(295, 505, 110, 35);
        panel.add(deleteBtn);

        JButton searchBtn = goldButton("Search");
        searchBtn.setBounds(425, 505, 110, 35);
        panel.add(searchBtn);

        JButton clearBtn = goldButton("Clear");
        clearBtn.setBounds(555, 505, 110, 35);
        panel.add(clearBtn);

        aircraftTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = aircraftTable.getSelectedRow();

                if (row >= 0) {
                    modelField.setText(aircraftTable.getValueAt(row, 1).toString());
                    capacityField.setText(aircraftTable.getValueAt(row, 2).toString());
                    airlineIdField.setText(aircraftTable.getValueAt(row, 3).toString());
                }
            }
        });

        addBtn.addActionListener(e -> {
            try {
                boolean success = aircraftDAO.addAircraft(
                        modelField.getText().trim(),
                        Integer.parseInt(capacityField.getText().trim()),
                        Integer.parseInt(airlineIdField.getText().trim())
                );

                if (success) {
                    JOptionPane.showMessageDialog(this, "Aircraft added successfully.");
                    aircraftTable.setModel(aircraftDAO.getAllAircraft());
                    clearAircraftFields(aircraftTable, modelField, capacityField, airlineIdField);
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to add aircraft.");
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Please check capacity and airline ID.");
            }
        });

        updateBtn.addActionListener(e -> {
            int row = aircraftTable.getSelectedRow();

            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Please select an aircraft to update.");
                return;
            }

            try {
                int aircraftId = Integer.parseInt(aircraftTable.getValueAt(row, 0).toString());

                boolean success = aircraftDAO.updateAircraft(
                        aircraftId,
                        modelField.getText().trim(),
                        Integer.parseInt(capacityField.getText().trim()),
                        Integer.parseInt(airlineIdField.getText().trim())
                );

                if (success) {
                    JOptionPane.showMessageDialog(this, "Aircraft updated successfully.");
                    aircraftTable.setModel(aircraftDAO.getAllAircraft());
                    clearAircraftFields(aircraftTable, modelField, capacityField, airlineIdField);
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to update aircraft.");
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Please check selected aircraft and values.");
            }
        });

        deleteBtn.addActionListener(e -> {
            int row = aircraftTable.getSelectedRow();

            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Please select an aircraft to delete.");
                return;
            }

            int choice = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to delete this aircraft?",
                    "Delete Confirmation",
                    JOptionPane.YES_NO_OPTION
            );

            if (choice == JOptionPane.YES_OPTION) {
                int aircraftId = Integer.parseInt(aircraftTable.getValueAt(row, 0).toString());

                boolean success = aircraftDAO.deleteAircraft(aircraftId);

                if (success) {
                    JOptionPane.showMessageDialog(this, "Aircraft deleted successfully.");
                    aircraftTable.setModel(aircraftDAO.getAllAircraft());
                    clearAircraftFields(aircraftTable, modelField, capacityField, airlineIdField);
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to delete aircraft.");
                }
            }
        });

        searchBtn.addActionListener(e -> {
            String modelText = modelField.getText().trim();

            if (modelText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Enter aircraft model to search.");
                return;
            }

            aircraftTable.setModel(aircraftDAO.searchAircraftByModel(modelText));

            if (aircraftTable.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "Aircraft not found.");
            }
        });

        clearBtn.addActionListener(e -> {
            aircraftTable.setModel(aircraftDAO.getAllAircraft());
            clearAircraftFields(aircraftTable, modelField, capacityField, airlineIdField);
        });
    }

    private void clearAircraftFields(JTable table, JTextField modelField, JTextField capacityField, JTextField airlineIdField) {
        table.clearSelection();
        modelField.setText("");
        capacityField.setText("");
        airlineIdField.setText("");
    }

    // ===================== EMPLOYEES - MYSQL =====================

    private void buildEmployeeManagement(JPanel panel) {
    EmployeeDAO employeeDAO = new EmployeeDAO();

    panel.add(label("Name:", 35, 105));
    JTextField nameField = field("", 120, 105);
    panel.add(nameField);

    panel.add(label("Role:", 305, 105));
    JComboBox<String> roleBox = combo(new String[]{"Pilot", "Crew"}, 385, 105);
    panel.add(roleBox);

    panel.add(label("Contact:", 570, 105));
    JTextField contactField = field("", 660, 105);
    panel.add(contactField);

    panel.add(label("Airport ID:", 35, 150));
    JTextField airportIdField = field("", 120, 150);
    panel.add(airportIdField);

    panel.add(label("Password:", 305, 150));
    JTextField passwordField = field("", 385, 150);
    panel.add(passwordField);

    panel.add(label("License:", 570, 150));
    JTextField licenseField = field("", 660, 150);
    panel.add(licenseField);

    panel.add(label("Position:", 35, 195));
    JTextField positionField = field("", 120, 195);
    panel.add(positionField);

    panel.add(label("Shift:", 305, 195));
    JTextField shiftField = field("", 385, 195);
    panel.add(shiftField);

    panel.add(label("Experience:", 570, 195));
    JTextField experienceField = field("", 660, 195);
    panel.add(experienceField);

    JTable employeeTable = createModelTable(employeeDAO.getAllEmployeesWithDetails());

    JScrollPane scrollPane = new JScrollPane(employeeTable);
    scrollPane.setBounds(35, 255, 825, 225);
    scrollPane.setBorder(new RoundedBorder(new Color(205, 170, 80), 1, 18));
    panel.add(scrollPane);

    JButton addBtn = goldButton("Add");
    addBtn.setBounds(45, 510, 100, 35);
    panel.add(addBtn);

    JButton updateBtn = goldButton("Update");
    updateBtn.setBounds(165, 510, 110, 35);
    panel.add(updateBtn);

    JButton deleteBtn = goldButton("Delete");
    deleteBtn.setBounds(295, 510, 110, 35);
    panel.add(deleteBtn);

    JButton searchBtn = goldButton("Search");
    searchBtn.setBounds(425, 510, 110, 35);
    panel.add(searchBtn);

    JButton clearBtn = goldButton("Clear");
    clearBtn.setBounds(555, 510, 110, 35);
    panel.add(clearBtn);

    roleBox.addActionListener(e -> {
        String role = roleBox.getSelectedItem().toString();

        if (role.equals("Pilot")) {
            licenseField.setEnabled(true);
            positionField.setEnabled(false);
            shiftField.setEnabled(false);

            positionField.setText("");
            shiftField.setText("");
        } else {
            licenseField.setEnabled(false);
            positionField.setEnabled(true);
            shiftField.setEnabled(true);

            licenseField.setText("");
        }
    });

    roleBox.setSelectedIndex(0);

    employeeTable.getSelectionModel().addListSelectionListener(e -> {
        if (!e.getValueIsAdjusting()) {
            int row = employeeTable.getSelectedRow();

            if (row >= 0) {
                nameField.setText(employeeTable.getValueAt(row, 1).toString());
                roleBox.setSelectedItem(employeeTable.getValueAt(row, 2).toString());
                contactField.setText(employeeTable.getValueAt(row, 3).toString());
                airportIdField.setText(employeeTable.getValueAt(row, 4).toString());
                passwordField.setText(employeeTable.getValueAt(row, 6).toString());
                licenseField.setText(employeeTable.getValueAt(row, 7).toString());
                positionField.setText(employeeTable.getValueAt(row, 8).toString());
                shiftField.setText(employeeTable.getValueAt(row, 9).toString());
                experienceField.setText(employeeTable.getValueAt(row, 10).toString());
            }
        }
    });

    addBtn.addActionListener(e -> {
        try {
            String name = nameField.getText().trim();
            String role = roleBox.getSelectedItem().toString();
            String contact = contactField.getText().trim();
            String password = passwordField.getText().trim();

            if (name.isEmpty() || contact.isEmpty()
                    || airportIdField.getText().trim().isEmpty()
                    || password.isEmpty()
                    || experienceField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all required fields.");
                return;
            }

            int experience = Integer.parseInt(experienceField.getText().trim());

            if (role.equals("Pilot") && licenseField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter pilot license number.");
                return;
            }

            if (role.equals("Crew")
                    && (positionField.getText().trim().isEmpty()
                    || shiftField.getText().trim().isEmpty())) {
                JOptionPane.showMessageDialog(this, "Please enter crew position and shift.");
                return;
            }

            boolean success = employeeDAO.addEmployeeWithRole(
                    name,
                    role,
                    contact,
                    Integer.parseInt(airportIdField.getText().trim()),
                    password,
                    licenseField.getText().trim(),
                    positionField.getText().trim(),
                    shiftField.getText().trim(),
                    experience
            );

            if (success) {
                JOptionPane.showMessageDialog(
                        this,
                        "Employee added successfully.\nEmployee can login using Employee ID and password."
                );

                employeeTable.setModel(employeeDAO.getAllEmployeesWithDetails());

                clearEmployeeFields(
                        employeeTable,
                        nameField,
                        roleBox,
                        contactField,
                        airportIdField,
                        passwordField,
                        licenseField,
                        positionField,
                        shiftField,
                        experienceField
                );
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add employee.");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Please check Airport ID and Experience values.");
        }
    });

    updateBtn.addActionListener(e -> {
        int row = employeeTable.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select an employee to update.");
            return;
        }

        try {
            int employeeId = Integer.parseInt(employeeTable.getValueAt(row, 0).toString());

            String name = nameField.getText().trim();
            String role = roleBox.getSelectedItem().toString();
            String contact = contactField.getText().trim();
            String password = passwordField.getText().trim();

            if (name.isEmpty() || contact.isEmpty()
                    || airportIdField.getText().trim().isEmpty()
                    || password.isEmpty()
                    || experienceField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all required fields.");
                return;
            }

            int experience = Integer.parseInt(experienceField.getText().trim());

            if (role.equals("Pilot") && licenseField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter pilot license number.");
                return;
            }

            if (role.equals("Crew")
                    && (positionField.getText().trim().isEmpty()
                    || shiftField.getText().trim().isEmpty())) {
                JOptionPane.showMessageDialog(this, "Please enter crew position and shift.");
                return;
            }

            boolean success = employeeDAO.updateEmployeeWithRole(
                    employeeId,
                    name,
                    role,
                    contact,
                    Integer.parseInt(airportIdField.getText().trim()),
                    password,
                    licenseField.getText().trim(),
                    positionField.getText().trim(),
                    shiftField.getText().trim(),
                    experience
            );

            if (success) {
                JOptionPane.showMessageDialog(this, "Employee updated successfully.");

                employeeTable.setModel(employeeDAO.getAllEmployeesWithDetails());

                clearEmployeeFields(
                        employeeTable,
                        nameField,
                        roleBox,
                        contactField,
                        airportIdField,
                        passwordField,
                        licenseField,
                        positionField,
                        shiftField,
                        experienceField
                );
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update employee.");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Please check selected employee and values.");
        }
    });

    deleteBtn.addActionListener(e -> {
        int row = employeeTable.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select an employee to delete.");
            return;
        }

        int choice = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete this employee?",
                "Delete Confirmation",
                JOptionPane.YES_NO_OPTION
        );

        if (choice == JOptionPane.YES_OPTION) {
            int employeeId = Integer.parseInt(employeeTable.getValueAt(row, 0).toString());

            boolean success = employeeDAO.deleteEmployee(employeeId);

            if (success) {
                JOptionPane.showMessageDialog(this, "Employee deleted successfully.");

                employeeTable.setModel(employeeDAO.getAllEmployeesWithDetails());

                clearEmployeeFields(
                        employeeTable,
                        nameField,
                        roleBox,
                        contactField,
                        airportIdField,
                        passwordField,
                        licenseField,
                        positionField,
                        shiftField,
                        experienceField
                );
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete employee.");
            }
        }
    });

    searchBtn.addActionListener(e -> {
        String nameText = nameField.getText().trim();

        if (nameText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter employee name to search.");
            return;
        }

        employeeTable.setModel(employeeDAO.searchEmployeeByName(nameText));

        if (employeeTable.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Employee not found.");
        }
    });

    clearBtn.addActionListener(e -> {
        employeeTable.setModel(employeeDAO.getAllEmployeesWithDetails());

        clearEmployeeFields(
                employeeTable,
                nameField,
                roleBox,
                contactField,
                airportIdField,
                passwordField,
                licenseField,
                positionField,
                shiftField,
                experienceField
        );
    });
}
private void clearEmployeeFields(
        JTable table,
        JTextField nameField,
        JComboBox<String> roleBox,
        JTextField contactField,
        JTextField airportIdField,
        JTextField passwordField,
        JTextField licenseField,
        JTextField positionField,
        JTextField shiftField,
        JTextField experienceField
) {
    table.clearSelection();

    nameField.setText("");
    roleBox.setSelectedIndex(0);
    contactField.setText("");
    airportIdField.setText("");
    passwordField.setText("");
    licenseField.setText("");
    positionField.setText("");
    shiftField.setText("");
    experienceField.setText("");

    licenseField.setEnabled(true);
    positionField.setEnabled(false);
    shiftField.setEnabled(false);
}// ===================== PASSENGERS - MYSQL =====================

    private void buildPassengerManagement(JPanel panel) {
        PassengerDAO passengerDAO = new PassengerDAO();

        panel.add(label("First Name:", 45, 120));
        JTextField firstNameField = field("", 150, 120);
        panel.add(firstNameField);

        panel.add(label("Last Name:", 330, 120));
        JTextField lastNameField = field("", 430, 120);
        panel.add(lastNameField);

        panel.add(label("Passport:", 600, 120));
        JTextField passportField = field("", 700, 120);
        panel.add(passportField);

        panel.add(label("Contact:", 45, 165));
        JTextField contactField = field("", 150, 165);
        panel.add(contactField);

        JTable passengerTable = createModelTable(passengerDAO.getAllPassengers());

        JScrollPane scrollPane = new JScrollPane(passengerTable);
        scrollPane.setBounds(45, 230, 815, 250);
        scrollPane.setBorder(new RoundedBorder(new Color(205, 170, 80), 1, 18));
        panel.add(scrollPane);

        JButton addBtn = goldButton("Add");
        addBtn.setBounds(45, 505, 100, 35);
        panel.add(addBtn);

        JButton updateBtn = goldButton("Update");
        updateBtn.setBounds(165, 505, 110, 35);
        panel.add(updateBtn);

        JButton deleteBtn = goldButton("Delete");
        deleteBtn.setBounds(295, 505, 110, 35);
        panel.add(deleteBtn);

        JButton searchBtn = goldButton("Search");
        searchBtn.setBounds(425, 505, 110, 35);
        panel.add(searchBtn);

        JButton clearBtn = goldButton("Clear");
        clearBtn.setBounds(555, 505, 110, 35);
        panel.add(clearBtn);

        passengerTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = passengerTable.getSelectedRow();

                if (row >= 0) {
                    firstNameField.setText(passengerTable.getValueAt(row, 1).toString());
                    lastNameField.setText(passengerTable.getValueAt(row, 2).toString());
                    passportField.setText(passengerTable.getValueAt(row, 3).toString());
                    contactField.setText(passengerTable.getValueAt(row, 4).toString());
                }
            }
        });

        addBtn.addActionListener(e -> {
            boolean success = passengerDAO.addPassenger(
                    firstNameField.getText().trim(),
                    lastNameField.getText().trim(),
                    passportField.getText().trim(),
                    contactField.getText().trim()
            );

            if (success) {
                JOptionPane.showMessageDialog(this, "Passenger added successfully.");
                passengerTable.setModel(passengerDAO.getAllPassengers());
                clearPassengerFields(passengerTable, firstNameField, lastNameField, passportField, contactField);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add passenger.");
            }
        });

        updateBtn.addActionListener(e -> {
            int row = passengerTable.getSelectedRow();

            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Please select a passenger to update.");
                return;
            }

            int passengerId = Integer.parseInt(passengerTable.getValueAt(row, 0).toString());

            boolean success = passengerDAO.updatePassenger(
                    passengerId,
                    firstNameField.getText().trim(),
                    lastNameField.getText().trim(),
                    passportField.getText().trim(),
                    contactField.getText().trim()
            );

            if (success) {
                JOptionPane.showMessageDialog(this, "Passenger updated successfully.");
                passengerTable.setModel(passengerDAO.getAllPassengers());
                clearPassengerFields(passengerTable, firstNameField, lastNameField, passportField, contactField);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update passenger.");
            }
        });

        deleteBtn.addActionListener(e -> {
            int row = passengerTable.getSelectedRow();

            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Please select a passenger to delete.");
                return;
            }

            int choice = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to delete this passenger?",
                    "Delete Confirmation",
                    JOptionPane.YES_NO_OPTION
            );

            if (choice == JOptionPane.YES_OPTION) {
                int passengerId = Integer.parseInt(passengerTable.getValueAt(row, 0).toString());

                boolean success = passengerDAO.deletePassenger(passengerId);

                if (success) {
                    JOptionPane.showMessageDialog(this, "Passenger deleted successfully.");
                    passengerTable.setModel(passengerDAO.getAllPassengers());
                    clearPassengerFields(passengerTable, firstNameField, lastNameField, passportField, contactField);
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to delete passenger.");
                }
            }
        });

        searchBtn.addActionListener(e -> {
            String passport = passportField.getText().trim();

            if (passport.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Enter passport number to search.");
                return;
            }

            passengerTable.setModel(passengerDAO.searchPassengerByPassport(passport));

            if (passengerTable.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "Passenger not found.");
            }
        });

        clearBtn.addActionListener(e -> {
            passengerTable.setModel(passengerDAO.getAllPassengers());
            clearPassengerFields(passengerTable, firstNameField, lastNameField, passportField, contactField);
        });
    }

    private void clearPassengerFields(JTable table, JTextField firstNameField, JTextField lastNameField, JTextField passportField, JTextField contactField) {
        table.clearSelection();
        firstNameField.setText("");
        lastNameField.setText("");
        passportField.setText("");
        contactField.setText("");
    }
    // ===================== TICKETS - MYSQL =====================

    private void buildTicketManagement(JPanel panel) {
        TicketDAO ticketDAO = new TicketDAO();

        panel.add(label("Passenger ID:", 35, 105));
        JTextField passengerIdField = field("", 140, 105);
        panel.add(passengerIdField);

        panel.add(label("Flight ID:", 330, 105));
        JTextField flightIdField = field("", 425, 105);
        panel.add(flightIdField);

        panel.add(label("Seat:", 600, 105));
        JTextField seatField = field("", 675, 105);
        panel.add(seatField);

        panel.add(label("Date:", 35, 150));
        JTextField dateField = field("2026-05-20", 140, 150);
        panel.add(dateField);

        panel.add(label("Class:", 330, 150));
        JTextField classField = field("", 425, 150);
        panel.add(classField);

        panel.add(label("Price:", 600, 150));
        JTextField priceField = field("", 675, 150);
        panel.add(priceField);

        panel.add(label("Status:", 35, 195));
        JTextField statusField = field("", 140, 195);
        panel.add(statusField);

        JTable ticketTable = createModelTable(ticketDAO.getAllTickets());

        JScrollPane scrollPane = new JScrollPane(ticketTable);
        scrollPane.setBounds(35, 245, 825, 225);
        scrollPane.setBorder(new RoundedBorder(new Color(205, 170, 80), 1, 18));
        panel.add(scrollPane);

        JButton addBtn = goldButton("Add");
        addBtn.setBounds(45, 505, 100, 35);
        panel.add(addBtn);

        JButton updateBtn = goldButton("Update");
        updateBtn.setBounds(165, 505, 110, 35);
        panel.add(updateBtn);

        JButton deleteBtn = goldButton("Delete");
        deleteBtn.setBounds(295, 505, 110, 35);
        panel.add(deleteBtn);

        JButton searchBtn = goldButton("Search");
        searchBtn.setBounds(425, 505, 110, 35);
        panel.add(searchBtn);

        JButton clearBtn = goldButton("Clear");
        clearBtn.setBounds(555, 505, 110, 35);
        panel.add(clearBtn);

        ticketTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = ticketTable.getSelectedRow();

                if (row >= 0) {
                    passengerIdField.setText(ticketTable.getValueAt(row, 1).toString());
                    flightIdField.setText(ticketTable.getValueAt(row, 3).toString());
                    seatField.setText(ticketTable.getValueAt(row, 5).toString());
                    dateField.setText(ticketTable.getValueAt(row, 6).toString());
                    classField.setText(ticketTable.getValueAt(row, 7).toString());
                    priceField.setText(ticketTable.getValueAt(row, 8).toString());
                    statusField.setText(ticketTable.getValueAt(row, 9).toString());
                }
            }
        });

        addBtn.addActionListener(e -> {
            try {
                if (Double.parseDouble(priceField.getText().trim()) <= 0) {
    JOptionPane.showMessageDialog(this, "Price must be greater than 0.");
    return;
}
                boolean success = ticketDAO.addTicket(
                        Integer.parseInt(passengerIdField.getText().trim()),
                        Integer.parseInt(flightIdField.getText().trim()),
                        seatField.getText().trim(),
                        dateField.getText().trim(),
                        classField.getText().trim(),
                        
                        Double.parseDouble(priceField.getText().trim()),
                        statusField.getText().trim()
                );

                if (success) {
                    JOptionPane.showMessageDialog(this, "Ticket added successfully.");
                    ticketTable.setModel(ticketDAO.getAllTickets());
                    clearTicketFields(ticketTable, passengerIdField, flightIdField, seatField, dateField, classField, priceField, statusField);
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to add ticket.");
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Please check values.\nDate format: 2026-05-20");
            }
        });

        updateBtn.addActionListener(e -> {
            int row = ticketTable.getSelectedRow();

            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Please select a ticket to update.");
                return;
            }

            try {
                int ticketId = Integer.parseInt(ticketTable.getValueAt(row, 0).toString());

                boolean success = ticketDAO.updateTicket(
                        ticketId,
                        Integer.parseInt(passengerIdField.getText().trim()),
                        Integer.parseInt(flightIdField.getText().trim()),
                        seatField.getText().trim(),
                        dateField.getText().trim(),
                        classField.getText().trim(),
                        Double.parseDouble(priceField.getText().trim()),
                        statusField.getText().trim()
                );

                if (success) {
                    JOptionPane.showMessageDialog(this, "Ticket updated successfully.");
                    ticketTable.setModel(ticketDAO.getAllTickets());
                    clearTicketFields(ticketTable, passengerIdField, flightIdField, seatField, dateField, classField, priceField, statusField);
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to update ticket.");
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Please check selected ticket and values.");
            }
        });

        deleteBtn.addActionListener(e -> {
            int row = ticketTable.getSelectedRow();

            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Please select a ticket to delete.");
                return;
            }

            int choice = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to delete this ticket?",
                    "Delete Confirmation",
                    JOptionPane.YES_NO_OPTION
            );

            if (choice == JOptionPane.YES_OPTION) {
                int ticketId = Integer.parseInt(ticketTable.getValueAt(row, 0).toString());

                boolean success = ticketDAO.deleteTicket(ticketId);

                if (success) {
                    JOptionPane.showMessageDialog(this, "Ticket deleted successfully.");
                    ticketTable.setModel(ticketDAO.getAllTickets());
                    clearTicketFields(ticketTable, passengerIdField, flightIdField, seatField, dateField, classField, priceField, statusField);
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to delete ticket.");
                }
            }
        });

        searchBtn.addActionListener(e -> {
            String idText = JOptionPane.showInputDialog(this, "Enter Ticket ID:");

            if (idText == null || idText.trim().isEmpty()) {
                return;
            }

            try {
                ticketTable.setModel(ticketDAO.searchTicketById(Integer.parseInt(idText.trim())));

                if (ticketTable.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(this, "Ticket not found.");
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Ticket ID must be a number.");
            }
        });

        clearBtn.addActionListener(e -> {
            ticketTable.setModel(ticketDAO.getAllTickets());
            clearTicketFields(ticketTable, passengerIdField, flightIdField, seatField, dateField, classField, priceField, statusField);
        });
    }

    private void clearTicketFields(
            JTable table,
            JTextField passengerIdField,
            JTextField flightIdField,
            JTextField seatField,
            JTextField dateField,
            JTextField classField,
            JTextField priceField,
            JTextField statusField
    ) {
        table.clearSelection();
        passengerIdField.setText("");
        flightIdField.setText("");
        seatField.setText("");
        dateField.setText("2026-05-20");
        classField.setText("");
        priceField.setText("");
        statusField.setText("");
    }
    // ===================== PASSENGER SCREENS CONNECTED TO MYSQL =====================
private void buildSearchFlights(JPanel panel) {

    FlightDAO flightDAO = new FlightDAO();
    
    JLabel searchLabel = new JLabel("Search:");
    searchLabel.setBounds(45, 170, 80, 30);
    panel.add(searchLabel);

    JTextField searchField = new JTextField();
    searchField.setBounds(130, 170, 240, 35);
    searchField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    searchField.setBorder(
            new RoundedBorder(
                    new Color(180, 180, 180),
                    1,
                    16
            )
    );
    panel.add(searchField);

    // FROM
    JLabel fromLabel = new JLabel("From:");
    fromLabel.setBounds(400, 170, 60, 30);
    panel.add(fromLabel);

    JTextField fromField = new JTextField();
    fromField.setBounds(460, 170, 120, 35);
    fromField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    fromField.setBorder(
            new RoundedBorder(
                    new Color(180, 180, 180),
                    1,
                    16
            )
    );
    panel.add(fromField);

    // TO
    JLabel toLabel = new JLabel("To:");
    toLabel.setBounds(620, 170, 40, 30);
    panel.add(toLabel);

    JTextField toField = new JTextField();
    toField.setBounds(660, 170, 120, 35);
    toField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    toField.setBorder(
            new RoundedBorder(
                    new Color(180, 180, 180),
                    1,
                    16
            )
    );
    panel.add(toField);

    // AIRLINE
    JLabel airlineLabel = new JLabel("Airline:");
    airlineLabel.setBounds(45, 225, 70, 30);
    panel.add(airlineLabel);

    JTextField airlineField = new JTextField();
    airlineField.setBounds(130, 225, 180, 35);
    airlineField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    airlineField.setBorder(
            new RoundedBorder(
                    new Color(180, 180, 180),
                    1,
                    16
            )
    );
    panel.add(airlineField);

    // STATUS
    JLabel statusLabel = new JLabel("Status:");
    statusLabel.setBounds(400, 225, 60, 30);
    panel.add(statusLabel);

    JComboBox<String> statusBox = combo(
            new String[]{
    "All",
    "On Time",
    "Delayed",
    "Boarding",
    "Cancelled"
     },
            460,
            225
    );
    panel.add(statusBox);

    // SEARCH BUTTON
    JButton searchBtn = goldButton("Search Flights");
    searchBtn.setBounds(660, 225, 170, 38);
    panel.add(searchBtn);

    JTable table = createModelTable(
            flightDAO.searchFlightsForPassenger(
                    "",
                    "All",
                    "All",
                    "All",
                    "All"
            )
    );

   int statusColumn = table.getColumnCount() - 1;

if (statusColumn >= 0) {
    table.getColumnModel()
            .getColumn(statusColumn)
            .setCellRenderer(new StatusRenderer());
}

    JScrollPane scrollPane = new JScrollPane(table);
    scrollPane.setBounds(45, 300, 815, 260);
    scrollPane.setBorder(
            new RoundedBorder(
                    new Color(205, 170, 80),
                    1,
                    18
            )
    );

    panel.add(scrollPane);

    // SEARCH ACTION
    searchBtn.addActionListener(e -> {

        table.setModel(
                flightDAO.searchFlightsForPassenger(

                        searchField.getText().trim(),

                        fromField.getText().trim().isEmpty()
                                ? "All"
                                : fromField.getText().trim(),

                        toField.getText().trim().isEmpty()
                                ? "All"
                                : toField.getText().trim(),

                        airlineField.getText().trim().isEmpty()
                                ? "All"
                                : airlineField.getText().trim(),

                        statusBox.getSelectedItem().toString()
                )
        );

       

if (statusColumn >= 0) {
    table.getColumnModel()
            .getColumn(statusColumn)
            .setCellRenderer(new StatusRenderer());
}
    });
}
   private void buildBookTicket(JPanel panel) {
    TicketDAO ticketDAO = new TicketDAO();

    panel.add(label("Passenger ID:", 60, 115));
    JTextField passengerIdField = field("", 180, 115);
    passengerIdField.setEditable(false);
    panel.add(passengerIdField);

    if (UserSession.hasPassengerProfile()) {
        passengerIdField.setText(String.valueOf(UserSession.getPassengerId()));
    }

    panel.add(label("Flight ID:", 60, 165));
    JTextField flightIdField = field("", 180, 165);
    panel.add(flightIdField);

    panel.add(label("Seat:", 60, 215));
    JTextField seatField = field("", 180, 215);
    panel.add(seatField);

    panel.add(label("Booking Date:", 60, 265));
    JTextField dateField = field("2026-05-20", 180, 265);
    panel.add(dateField);

    panel.add(label("Class:", 430, 115));
    JComboBox<String> classBox = combo(new String[]{"Economy", "Business", "First"}, 530, 115);
    panel.add(classBox);

    panel.add(label("Price:", 430, 165));
    JTextField priceField = field("500", 530, 165);
    priceField.setEditable(false);
    panel.add(priceField);

    panel.add(label("Status:", 430, 215));
    JTextField statusField = field("Confirmed", 530, 215);
    statusField.setEditable(false);
    panel.add(statusField);

    classBox.addActionListener(e -> {
        String selectedClass = classBox.getSelectedItem().toString();

        if (selectedClass.equals("Economy")) {
            priceField.setText("500");
        } else if (selectedClass.equals("Business")) {
            priceField.setText("1200");
        } else {
            priceField.setText("2000");
        }
    });

    JButton bookBtn = goldButton("Book Ticket");
    bookBtn.setBounds(180, 330, 160, 38);
    panel.add(bookBtn);

    JTextArea note = noteArea(
            "Booking Rules:\n\n"
                    + "- Create your profile first to get Passenger ID.\n"
                    + "- Passenger ID is filled automatically.\n"
                    + "- Flight ID must exist in Flight table.\n"
                    + "- Date format: 2026-05-20\n\n"
                    + "After booking, the ticket is saved in MySQL."
    );
    note.setBounds(430, 285, 360, 155);
    panel.add(note);

    bookBtn.addActionListener(e -> {
        if (!UserSession.hasPassengerProfile()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please create or select your passenger profile first from Profile page."
            );
            return;
        }

        if (flightIdField.getText().trim().isEmpty()
                || seatField.getText().trim().isEmpty()
                || dateField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all ticket fields.");
            return;
        }

        try {
            int passengerId = UserSession.getPassengerId();

            boolean success = ticketDAO.addTicket(
                    passengerId,
                    Integer.parseInt(flightIdField.getText().trim()),
                    seatField.getText().trim(),
                    dateField.getText().trim(),
                    classBox.getSelectedItem().toString(),
                    Double.parseDouble(priceField.getText().trim()),
                    statusField.getText().trim()
            );

            if (success) {
    JOptionPane.showMessageDialog(
            this,
            "Ticket booked successfully.\nPassenger ID: " + passengerId
    );

    NotificationCenter.addPassengerNotification(
            "Ticket booked successfully for Flight ID: "
                    + flightIdField.getText().trim()
    );

    NotificationCenter.addAdminNotification(
            "New ticket booked by Passenger ID: "
                    + passengerId
                    + " | Flight ID: "
                    + flightIdField.getText().trim()
                    + " | Seat: "
                    + seatField.getText().trim()
                    + " | Class: "
                    + classBox.getSelectedItem().toString()
    );

    flightIdField.setText("");
    seatField.setText("");
    dateField.setText("2026-05-20");
    classBox.setSelectedIndex(0);
    priceField.setText("500");

} else {
    JOptionPane.showMessageDialog(
            this,
            "Failed to book ticket.\nPossible reason: Flight ID does not exist."
    );
}

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please check your input.\nFlight ID must be a number.\nDate format: 2026-05-20"
            );
        }
    });
}

  private void buildMyTickets(JPanel panel) {
    TicketDAO ticketDAO = new TicketDAO();

    if (!UserSession.hasPassengerProfile()) {
        JLabel warningTitle = new JLabel("Profile Required");
        warningTitle.setFont(new Font("Segoe UI", Font.BOLD, 26));
        warningTitle.setForeground(new Color(10, 25, 47));
        warningTitle.setBounds(60, 130, 400, 40);
        panel.add(warningTitle);

        JTextArea message = noteArea(
                "You cannot view My Tickets yet.\n\n"
                        + "Please go to Profile page first and either:\n"
                        + "- Create your passenger profile, or\n"
                        + "- Search your passport and select your profile.\n\n"
                        + "After that, My Tickets will show only your own tickets."
        );
        message.setBounds(60, 190, 760, 210);
        panel.add(message);

        JButton profileBtn = goldButton("Go to Profile");
        profileBtn.setBounds(60, 430, 160, 38);
        panel.add(profileBtn);

        profileBtn.addActionListener(e -> {
            RoleFeatureFrame frame = new RoleFeatureFrame(
        username,
        role,
        pageName,
        loggedEmployeeId
);

frame.setVisible(true);
dispose();        });

        return;
    }

    JLabel noteLabel = new JLabel("Your booked tickets from MySQL database");
    noteLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    noteLabel.setForeground(new Color(90, 90, 90));
    noteLabel.setBounds(45, 100, 500, 25);
    panel.add(noteLabel);

    JTable table = createModelTable(ticketDAO.getTicketsByPassengerId(UserSession.getPassengerId()));

    JScrollPane scrollPane = new JScrollPane(table);
    scrollPane.setBounds(45, 140, 815, 320);
    scrollPane.setBorder(new RoundedBorder(new Color(205, 170, 80), 1, 18));
    panel.add(scrollPane);

    JButton refreshBtn = goldButton("Refresh");
    refreshBtn.setBounds(45, 490, 120, 38);
    panel.add(refreshBtn);

    JButton deleteBtn = goldButton("Delete Ticket");
    deleteBtn.setBounds(185, 490, 160, 38);
    panel.add(deleteBtn);

    JButton infoBtn = goldButton("My Passenger ID");
    infoBtn.setBounds(365, 490, 170, 38);
    panel.add(infoBtn);

    refreshBtn.addActionListener(e -> {
        table.setModel(ticketDAO.getTicketsByPassengerId(UserSession.getPassengerId()));
        NotificationCenter.addPassengerNotification("Tickets refreshed successfully.");
        JOptionPane.showMessageDialog(this, "Tickets refreshed successfully.");
    });

    deleteBtn.addActionListener(e -> {
        int row = table.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a ticket to delete.");
            return;
        }

        int choice = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete this ticket?",
                "Delete Confirmation",
                JOptionPane.YES_NO_OPTION
        );

        if (choice == JOptionPane.YES_OPTION) {
            int ticketId = Integer.parseInt(table.getValueAt(row, 0).toString());
            int passengerId = UserSession.getPassengerId();

            boolean success = ticketDAO.deleteTicketForPassenger(ticketId, passengerId);

            if (success) {
                JOptionPane.showMessageDialog(this, "Ticket deleted successfully.");
                NotificationCenter.addPassengerNotification("Ticket deleted successfully. Ticket ID: " + ticketId);
                table.setModel(ticketDAO.getTicketsByPassengerId(passengerId));
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Failed to delete ticket.\nThis ticket may not belong to your passenger profile."
                );
            }
        }
    });

    infoBtn.addActionListener(e -> {
        JOptionPane.showMessageDialog(
                this,
                "Your Passenger ID is: " + UserSession.getPassengerId()
        );
    });
}

  private void buildProfile(JPanel panel) {
    PassengerDAO passengerDAO = new PassengerDAO();

    panel.add(label("Passenger ID:", 60, 115));
    JTextField passengerIdField = field("", 180, 115);
    passengerIdField.setEditable(false);
    panel.add(passengerIdField);

    if (UserSession.hasPassengerProfile()) {
        passengerIdField.setText(String.valueOf(UserSession.getPassengerId()));
    }

    panel.add(label("First Name:", 60, 165));
    JTextField firstNameField = field("", 180, 165);
    panel.add(firstNameField);

    panel.add(label("Last Name:", 60, 215));
    JTextField lastNameField = field("", 180, 215);
    panel.add(lastNameField);

    panel.add(label("Passport:", 430, 115));
    JTextField passportField = field("", 530, 115);
    panel.add(passportField);

    panel.add(label("Contact:", 430, 165));
    JTextField contactField = field("", 530, 165);
    panel.add(contactField);

    JButton createBtn = goldButton("Create Profile");
    createBtn.setBounds(180, 285, 160, 38);
    panel.add(createBtn);

    JButton searchBtn = goldButton("Search Passport");
    searchBtn.setBounds(360, 285, 160, 38);
    panel.add(searchBtn);

    JButton updateBtn = goldButton("Update Profile");
    updateBtn.setBounds(540, 285, 160, 38);
    panel.add(updateBtn);

    JTextArea note = noteArea(
            "Profile Instructions:\n\n"
                    + "- If you are a new passenger, fill your data and click Create Profile.\n"
                    + "- Your Passenger ID will be generated automatically.\n"
                    + "- If you already have a profile, enter your passport number and click Search Passport.\n"
                    + "- My Tickets and Baggage pages use your Passenger ID automatically."
    );
    note.setBounds(60, 360, 760, 150);
    panel.add(note);

    createBtn.addActionListener(e -> {
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String passport = passportField.getText().trim();
        String contact = contactField.getText().trim();

        if (firstName.isEmpty() || lastName.isEmpty() || passport.isEmpty() || contact.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all profile fields.");
            return;
        }

        int newPassengerId = passengerDAO.addPassengerAndReturnId(firstName, lastName, passport, contact);

        if (newPassengerId != -1) {
            passengerIdField.setText(String.valueOf(newPassengerId));
            UserSession.setPassengerId(newPassengerId);
            UserSession.setPassengerName(
        firstNameField.getText().trim() + " " + lastNameField.getText().trim()
);

            NotificationCenter.addPassengerNotification(
                    "Profile created successfully. Passenger ID: " + newPassengerId
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Profile created successfully.\nYour Passenger ID is: " + newPassengerId
            );

        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "Failed to create profile.\nPossible reason: passport number already exists."
            );
        }
    });

    searchBtn.addActionListener(e -> {
        String passport = passportField.getText().trim();

        if (passport.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter passport number to search.");
            return;
        }

        DefaultTableModel searchModel = passengerDAO.searchPassengerByPassport(passport);

        if (searchModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Passenger not found.");
            return;
        }

        passengerIdField.setText(searchModel.getValueAt(0, 0).toString());
        firstNameField.setText(searchModel.getValueAt(0, 1).toString());
        lastNameField.setText(searchModel.getValueAt(0, 2).toString());
        passportField.setText(searchModel.getValueAt(0, 3).toString());
        contactField.setText(searchModel.getValueAt(0, 4).toString());

        int selectedPassengerId = Integer.parseInt(searchModel.getValueAt(0, 0).toString());
        UserSession.setPassengerId(selectedPassengerId);
        UserSession.setPassengerName(
        firstNameField.getText().trim() + " " + lastNameField.getText().trim()
);

        JOptionPane.showMessageDialog(
                this,
                "Passenger profile loaded successfully.\nPassenger ID: " + selectedPassengerId
        );
    });

    updateBtn.addActionListener(e -> {
        if (passengerIdField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please create or search your passenger profile first.");
            return;
        }

        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String passport = passportField.getText().trim();
        String contact = contactField.getText().trim();

        if (firstName.isEmpty() || lastName.isEmpty() || passport.isEmpty() || contact.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all profile fields.");
            return;
        }

        int passengerId = Integer.parseInt(passengerIdField.getText().trim());

        boolean success = passengerDAO.updatePassenger(
                passengerId,
                firstName,
                lastName,
                passport,
                contact
        );

        if (success) {
            UserSession.setPassengerId(passengerId);
            UserSession.setPassengerName(firstName + " " + lastName);

            NotificationCenter.addPassengerNotification("Profile updated successfully.");

            JOptionPane.showMessageDialog(this, "Profile updated successfully.");
        } else {
            JOptionPane.showMessageDialog(this, "Failed to update profile.");
        }
    });
}  

  private void buildBaggage(JPanel panel) {
    BaggageDAO baggageDAO = new BaggageDAO();

    if (!UserSession.hasPassengerProfile()) {
        JLabel warningTitle = new JLabel("Profile Required");
        warningTitle.setFont(new Font("Segoe UI", Font.BOLD, 26));
        warningTitle.setForeground(new Color(10, 25, 47));
        warningTitle.setBounds(60, 130, 400, 40);
        panel.add(warningTitle);

        JTextArea message = noteArea(
                "You cannot track baggage yet.\n\n"
                        + "Please go to Profile page first and either:\n"
                        + "- Create your passenger profile, or\n"
                        + "- Search your passport and load your profile.\n\n"
                        + "After that, Baggage Tracking will show only your baggage."
        );
        message.setBounds(60, 190, 760, 210);
        panel.add(message);

        JButton profileBtn = goldButton("Go to Profile");
        profileBtn.setBounds(60, 430, 160, 38);
        panel.add(profileBtn);

        profileBtn.addActionListener(e -> {
RoleFeatureFrame frame = new RoleFeatureFrame(
        username,
        role,
        pageName,
        loggedEmployeeId
);

frame.setVisible(true);
dispose();
        });

        return;
    }

    JLabel title = new JLabel("Smart Baggage Tracking");
    title.setFont(new Font("Segoe UI", Font.BOLD, 23));
    title.setForeground(new Color(10, 25, 47));
    title.setBounds(45, 100, 400, 30);
    panel.add(title);

    JLabel desc = new JLabel("Your baggage is linked to your booked flights and updated by airport staff.");
    desc.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    desc.setForeground(new Color(90, 90, 90));
    desc.setBounds(45, 128, 650, 25);
    panel.add(desc);

    JTable table = createModelTable(
            baggageDAO.getBaggageByPassengerId(UserSession.getPassengerId())
    );

    if (table.getColumnCount() > 6) {
        table.getColumnModel().getColumn(6).setCellRenderer(new BaggageStatusRenderer());
    }

    JScrollPane scrollPane = new JScrollPane(table);
    scrollPane.setBounds(45, 170, 815, 285);
    scrollPane.setBorder(new RoundedBorder(new Color(205, 170, 80), 1, 18));
    panel.add(scrollPane);

    JButton refreshBtn = goldButton("Refresh Tracking");
    refreshBtn.setBounds(45, 490, 170, 38);
    panel.add(refreshBtn);

    JButton reportBtn = goldButton("Report Missing");
    reportBtn.setBounds(235, 490, 160, 38);
    panel.add(reportBtn);

    JButton infoBtn = goldButton("My Passenger ID");
    infoBtn.setBounds(415, 490, 170, 38);
    panel.add(infoBtn);

    refreshBtn.addActionListener(e -> {
        table.setModel(baggageDAO.getBaggageByPassengerId(UserSession.getPassengerId()));

        if (table.getColumnCount() > 6) {
            table.getColumnModel().getColumn(6).setCellRenderer(new BaggageStatusRenderer());
        }

        NotificationCenter.addPassengerNotification("Baggage tracking refreshed successfully.");
        JOptionPane.showMessageDialog(this, "Baggage tracking refreshed successfully.");
    });

reportBtn.addActionListener(e -> {

    int passengerId = UserSession.getPassengerId();

    int selectedRow = table.getSelectedRow();

    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(
                this,
                "Please select the missing baggage first."
        );
        return;
    }

    String baggageId = table.getValueAt(selectedRow, 0).toString();
    String flightId = table.getValueAt(selectedRow, 2).toString();
    String weight = table.getValueAt(selectedRow, 3).toString();
    String status = table.getValueAt(selectedRow, 4).toString();
    String location = table.getValueAt(selectedRow, 5).toString();

    NotificationCenter.addPassengerNotification(
            "Missing baggage report submitted. Baggage ID: " + baggageId
    );

    NotificationCenter.addGroundCrewNotification(
            "Missing baggage report | Passenger ID: " + passengerId
                    + " | Baggage ID: " + baggageId
                    + " | Flight ID: " + flightId
                    + " | Weight: " + weight
                    + " kg"
                    + " | Last Location: " + location
                    + " | Current Status: " + status
    );

    JOptionPane.showMessageDialog(
            this,
            "Missing baggage report submitted successfully.\n"
                    + "Ground crew has been notified."
    );
});

    infoBtn.addActionListener(e -> {
        JOptionPane.showMessageDialog(
                this,
                "Your Passenger ID is: " + UserSession.getPassengerId()
        );
    });
}
    private void buildPassengerViewer(JPanel panel) {
        PassengerDAO passengerDAO = new PassengerDAO();

        JTable table = createModelTable(passengerDAO.getAllPassengers());

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(45, 130, 815, 310);
        scrollPane.setBorder(new RoundedBorder(new Color(205, 170, 80), 1, 18));
        panel.add(scrollPane);
    }

private void buildTicketCheck(JPanel panel) {
    GroundCrewDAO dao = new GroundCrewDAO();

    JTable table = createModelTable(dao.getTicketsForCheck());

    JScrollPane scrollPane = new JScrollPane(table);
    scrollPane.setBounds(45, 130, 815, 320);
    scrollPane.setBorder(new RoundedBorder(new Color(205, 170, 80), 1, 18));
    panel.add(scrollPane);

    JButton checkBtn = goldButton("Check Selected Ticket");
    checkBtn.setBounds(45, 490, 210, 38);
    panel.add(checkBtn);

    JButton refreshBtn = goldButton("Refresh");
    refreshBtn.setBounds(275, 490, 130, 38);
    panel.add(refreshBtn);

   checkBtn.addActionListener(e -> {

    int row = table.getSelectedRow();

    if (row == -1) {

        JOptionPane.showMessageDialog(
                this,
                "Please select a ticket first."
        );

        return;
    }

    String status = table.getValueAt(row, 5).toString();

    if (status.equalsIgnoreCase("Cancelled")) {

        JOptionPane.showMessageDialog(
                this,
                "Ticket is NOT valid.\nReason: Ticket is cancelled."
        );

    } else if (status.equalsIgnoreCase("Boarded")) {

        JOptionPane.showMessageDialog(
                this,
                "Ticket already used.\nPassenger has already boarded."
        );

    } else {

        JOptionPane.showMessageDialog(
                this,
                "Ticket is VALID for boarding.\n\n"
                        + "Ticket ID: " + table.getValueAt(row, 0) + "\n"
                        + "Passenger: " + table.getValueAt(row, 1) + "\n"
                        + "Flight: " + table.getValueAt(row, 2) + "\n"
                        + "Seat: " + table.getValueAt(row, 3) + "\n"
                        + "Status: " + status
        );
    }
});

    refreshBtn.addActionListener(e -> {
        table.setModel(dao.getTicketsForCheck());
    });
}
    // ===================== REPORTS / LOGS / BACKUP =====================

    private void buildReportsPage(JPanel panel) {
        JLabel title = new JLabel("Admin Reports");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(new Color(10, 25, 47));
        title.setBounds(45, 110, 250, 30);
        panel.add(title);

        panel.add(reportCard("Total Flights", "8", 45, 160));
        panel.add(reportCard("Confirmed Tickets", "3", 310, 160));
        panel.add(reportCard("Delayed Flights", "1", 575, 160));

        JTextArea reportArea = noteArea(
                "Report Summary:\n\n"
                        + "- Most active airport: DXB\n"
                        + "- Highest ticket class usage: Economy\n"
                        + "- One delayed flight needs operational review\n"
                        + "- Ticket confirmation rate is acceptable\n\n"
                        + "Later this page can generate PDF or database reports."
        );
        reportArea.setBounds(45, 310, 815, 190);
        panel.add(reportArea);

        JButton generateBtn = goldButton("Generate Report");
        generateBtn.setBounds(45, 525, 170, 35);
        panel.add(generateBtn);

        generateBtn.addActionListener(e -> JOptionPane.showMessageDialog(this, "Report generated successfully."));
    }

    private JPanel reportCard(String title, String value, int x, int y) {
        JPanel card = new JPanel(null);
        card.setBackground(Color.WHITE);
        card.setBounds(x, y, 220, 110);
        card.setBorder(new RoundedBorder(new Color(205, 170, 80), 1, 18));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));
        titleLabel.setForeground(new Color(70, 70, 70));
        titleLabel.setBounds(20, 18, 180, 25);
        card.add(titleLabel);

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        valueLabel.setForeground(new Color(10, 25, 47));
        valueLabel.setBounds(20, 55, 100, 35);
        card.add(valueLabel);

        return card;
    }

    private void buildSystemLogsPage(JPanel panel) {
        String[] columns = {"Log ID", "User", "Role", "Action", "Time"};

        Object[][] rows = {
                {"1", "noura herbawi", "Admin", "Logged in", "10:00"},
                {"2", "noura herbawi", "Admin", "Viewed Flights", "10:05"},
                {"3", "besan zhear", "Admin", "Updated Airport", "10:20"},
                {"4", "employee1", "Employee", "Checked Ticket", "11:00"},
                {"5", "passenger1", "Passenger", "Booked Ticket", "12:15"}
        };

        JTable table = createReadOnlyTable(rows, columns);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(45, 130, 815, 340);
        scrollPane.setBorder(new RoundedBorder(new Color(205, 170, 80), 1, 18));
        panel.add(scrollPane);

        JButton refreshBtn = goldButton("Refresh Logs");
        refreshBtn.setBounds(45, 505, 150, 35);
        panel.add(refreshBtn);

        JButton clearBtn = goldButton("Clear View");
        clearBtn.setBounds(215, 505, 130, 35);
        panel.add(clearBtn);

        refreshBtn.addActionListener(e -> JOptionPane.showMessageDialog(this, "Logs refreshed successfully."));
        clearBtn.addActionListener(e -> JOptionPane.showMessageDialog(this, "This is a display-only logs page for now."));
    }

    private void buildBackupPage(JPanel panel) {
        JLabel title = new JLabel("Database Backup");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(new Color(10, 25, 47));
        title.setBounds(45, 120, 300, 35);
        panel.add(title);

        JTextArea infoArea = noteArea(
                "Backup Function:\n\n"
                        + "This page simulates creating a backup for the airport database.\n\n"
                        + "In a real system, this button can export:\n"
                        + "- Airports data\n"
                        + "- Flights data\n"
                        + "- Passengers data\n"
                        + "- Tickets data\n"
                        + "- Employees data\n\n"
                        + "Current mode: UI simulation only."
        );
        infoArea.setBounds(45, 175, 815, 260);
        panel.add(infoArea);

        JButton backupBtn = goldButton("Create Backup");
        backupBtn.setBounds(45, 470, 160, 38);
        panel.add(backupBtn);

        JButton restoreBtn = goldButton("Restore Backup");
        restoreBtn.setBounds(225, 470, 160, 38);
        panel.add(restoreBtn);

        backupBtn.addActionListener(e -> JOptionPane.showMessageDialog(
                this,
                "Backup created successfully.\nFile name: airport_backup_2026.sql"
        ));

        restoreBtn.addActionListener(e -> JOptionPane.showMessageDialog(
                this,
                "Restore feature will be connected later."
        ));
    }

    private void buildSimplePage(JPanel panel, String title, String description) {
        JTextArea area = noteArea(
                title + "\n\n"
                        + description + "\n\n"
                        + "Later this page will be connected to MySQL."
        );
        area.setBounds(60, 130, 760, 260);
        panel.add(area);
    }
    
    
private void buildPilotMyFlights(JPanel panel) {

    PilotDAO pilotDAO = new PilotDAO();

    JLabel desc = new JLabel("Only flights assigned to this pilot are shown from Flight_Pilot table.");
    desc.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    desc.setForeground(new Color(90, 90, 90));
    desc.setBounds(45, 105, 700, 25);
    panel.add(desc);

    int pilotId = loggedEmployeeId;

    JTable table = createModelTable(
            pilotDAO.getAssignedFlightsForPilot(pilotId)
    );

    if (table.getColumnCount() > 0) {
        int statusColumn = table.getColumnCount() - 1;
        table.getColumnModel().getColumn(statusColumn).setCellRenderer(new StatusRenderer());
    }

    JScrollPane scrollPane = new JScrollPane(table);
    scrollPane.setBounds(45, 150, 815, 320);
    scrollPane.setBorder(new RoundedBorder(new Color(205, 170, 80), 1, 18));
    panel.add(scrollPane);

    JButton refreshBtn = goldButton("Refresh My Flights");
    refreshBtn.setBounds(45, 500, 180, 38);
    panel.add(refreshBtn);

    JButton detailsBtn = goldButton("Selected Flight Info");
    detailsBtn.setBounds(245, 500, 190, 38);
    panel.add(detailsBtn);

    refreshBtn.addActionListener(e -> {
        table.setModel(pilotDAO.getAssignedFlightsForPilot(pilotId));

        if (table.getColumnCount() > 0) {
            int statusColumn = table.getColumnCount() - 1;
            table.getColumnModel().getColumn(statusColumn).setCellRenderer(new StatusRenderer());
        }

        JOptionPane.showMessageDialog(this, "My flights refreshed successfully.");
    });

    detailsBtn.addActionListener(e -> {
        int row = table.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a flight first.");
            return;
        }

        JOptionPane.showMessageDialog(
                this,
                "Flight Details\n\n"
                        + "Flight ID: " + table.getValueAt(row, 0) + "\n"
                        + "Flight No: " + table.getValueAt(row, 1) + "\n"
                        + "Pilot Role: " + table.getValueAt(row, 2) + "\n"
                        + "From: " + table.getValueAt(row, 3) + "\n"
                        + "To: " + table.getValueAt(row, 4) + "\n"
                        + "Aircraft: " + table.getValueAt(row, 5) + "\n"
                        + "Airline: " + table.getValueAt(row, 6) + "\n"
                        + "Departure: " + table.getValueAt(row, 7) + "\n"
                        + "Arrival: " + table.getValueAt(row, 8) + "\n"
                        + "Status: " + table.getValueAt(row, 9)
        );
    });
}

private void buildAircraftStatus(JPanel panel) {

    AircraftStatusDAO dao = new AircraftStatusDAO();

    JTable table = createModelTable(
            dao.getPilotAircraftStatus(loggedEmployeeId)
    );

    table.setRowHeight(32);

    if (table.getColumnCount() > 3) {
        table.getColumnModel()
                .getColumn(3)
                .setCellRenderer(new StatusRenderer());
    }

    JScrollPane scroll = new JScrollPane(table);
    scroll.setBounds(40, 120, 820, 380);
    panel.add(scroll);

    JButton refreshBtn = goldButton("Refresh Aircraft Status");
    refreshBtn.setBounds(40, 525, 220, 38);
    panel.add(refreshBtn);

    refreshBtn.addActionListener(e -> {
        table.setModel(
                dao.getPilotAircraftStatus(loggedEmployeeId)
        );

        if (table.getColumnCount() > 3) {
            table.getColumnModel()
                    .getColumn(3)
                    .setCellRenderer(new StatusRenderer());
        }

        JOptionPane.showMessageDialog(
                this,
                "Aircraft status refreshed successfully."
        );
    });
}

private void buildCabinTasks(JPanel panel) {

    String[] columns = {"Task", "Status", "Notes"};

    Object[][] rows = {
            {"Passenger safety briefing", "Pending", "Before takeoff"},
            {"Seat belt check", "Pending", "Before departure"},
            {"Cabin cleanliness check", "Pending", "Before boarding"},
            {"Passenger assistance", "In Progress", "During flight"},
            {"Emergency equipment check", "Completed", "Before flight"}
    };

    JTable table = createReadOnlyTable(rows, columns);

    JScrollPane scrollPane = new JScrollPane(table);
    scrollPane.setBounds(55, 130, 780, 280);
    scrollPane.setBorder(new RoundedBorder(new Color(205, 170, 80), 1, 18));
    panel.add(scrollPane);

    JButton completeBtn = goldButton("Mark Selected Completed");
    completeBtn.setBounds(55, 450, 230, 38);
    panel.add(completeBtn);

    completeBtn.addActionListener(e -> {
        int row = table.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a task first.");
            return;
        }

        table.setValueAt("Completed", row, 1);

        JOptionPane.showMessageDialog(this, "Cabin task marked as completed.");
    });
}

private void buildBaggageHandling(JPanel panel) {
    BaggageDAO baggageDAO = new BaggageDAO();

    panel.add(label("Passenger ID:", 45, 115));
    JTextField passengerIdField = field("", 160, 115);
    panel.add(passengerIdField);

    panel.add(label("Flight ID:", 340, 115));
    JTextField flightIdField = field("", 430, 115);
    panel.add(flightIdField);

    panel.add(label("Weight:", 610, 115));
    JTextField weightField = field("", 690, 115);
    panel.add(weightField);

    panel.add(label("Status:", 45, 165));
    JComboBox<String> statusBox = combo(
            new String[]{"Checked In", "Loaded", "In Transit", "Arrived", "Lost", "Delayed"},
            160,
            165
    );
    panel.add(statusBox);

    panel.add(label("Location:", 340, 165));
    JTextField locationField = field("Airport", 430, 165);
    locationField.setBounds(430, 165, 220, 32);
    panel.add(locationField);

    JButton addBtn = goldButton("Add Baggage");
    addBtn.setBounds(45, 220, 150, 38);
    panel.add(addBtn);

    JTextArea note = noteArea(
            "Ground Crew Baggage Handling\n\n"
                    + "- Register passenger baggage\n"
                    + "- Update baggage status\n"
                    + "- Track baggage current location\n\n"
                    + "This is handled by Ground Crew only."
    );
    note.setBounds(45, 290, 815, 210);
    panel.add(note);

    addBtn.addActionListener(e -> {
        try {
            int passengerId = Integer.parseInt(passengerIdField.getText().trim());
            int flightId = Integer.parseInt(flightIdField.getText().trim());
            float weight = Float.parseFloat(weightField.getText().trim());
            if (weight <= 0) {
    JOptionPane.showMessageDialog(this, "Weight must be greater than 0.");
    return;
}
            String status = statusBox.getSelectedItem().toString();
            String location = locationField.getText().trim();

            boolean success = baggageDAO.addBaggage(
                    passengerId,
                    flightId,
                    weight,
                    status,
                    location
            );

            if (success) {
                JOptionPane.showMessageDialog(this, "Baggage registered successfully.");

                passengerIdField.setText("");
                flightIdField.setText("");
                weightField.setText("");
                statusBox.setSelectedIndex(0);
                locationField.setText("Airport");

            } else {
                JOptionPane.showMessageDialog(this, "Failed to register baggage.");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please check Passenger ID, Flight ID, and Weight."
            );
        }
    });
}

private void buildGateOperations(JPanel panel) {
    GroundCrewDAO dao = new GroundCrewDAO();

    JTable table = createModelTable(dao.getGateOperations());

    JScrollPane scrollPane = new JScrollPane(table);
    scrollPane.setBounds(45, 130, 815, 330);
    scrollPane.setBorder(new RoundedBorder(new Color(205, 170, 80), 1, 18));
    panel.add(scrollPane);

    JButton refreshBtn = goldButton("Refresh Gates");
    refreshBtn.setBounds(45, 500, 160, 38);
    panel.add(refreshBtn);

    refreshBtn.addActionListener(e -> {
        table.setModel(dao.getGateOperations());
        JOptionPane.showMessageDialog(this, "Gate operations refreshed successfully.");
    });
}
private void buildPilotFlightSchedule(JPanel panel) {
    PilotDAO pilotDAO = new PilotDAO();

    JLabel title = new JLabel("Pilot Flight Schedule");
    title.setFont(new Font("Segoe UI", Font.BOLD, 22));
    title.setForeground(new Color(10, 25, 47));
    title.setBounds(45, 100, 350, 30);
    panel.add(title);

    JLabel desc = new JLabel("Schedule is loaded based on Flight_Pilot and Flight_Schedule tables.");
    desc.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    desc.setForeground(new Color(90, 90, 90));
    desc.setBounds(45, 128, 650, 25);
    panel.add(desc);

    JTable table = createModelTable(
            pilotDAO.getAssignedFlightsForPilot(loggedEmployeeId)
    );

    if (table.getColumnCount() > 9) {
        table.getColumnModel().getColumn(9).setCellRenderer(new StatusRenderer());
    }

    JScrollPane scrollPane = new JScrollPane(table);
    scrollPane.setBounds(45, 170, 815, 300);
    scrollPane.setBorder(new RoundedBorder(new Color(205, 170, 80), 1, 18));
    panel.add(scrollPane);

    JButton refreshBtn = goldButton("Refresh Schedule");
    refreshBtn.setBounds(45, 505, 170, 38);
    panel.add(refreshBtn);

    refreshBtn.addActionListener(e -> {
        table.setModel(pilotDAO.getAssignedFlightsForPilot(loggedEmployeeId));

        if (table.getColumnCount() > 9) {
            table.getColumnModel().getColumn(9).setCellRenderer(new StatusRenderer());
        }

        JOptionPane.showMessageDialog(this, "Schedule refreshed successfully.");
    });
}
private void buildPilotAircraftStatus(JPanel panel) {
    PilotDAO pilotDAO = new PilotDAO();

    JLabel title = new JLabel("Aircraft Status");
    title.setFont(new Font("Segoe UI", Font.BOLD, 22));
    title.setForeground(new Color(10, 25, 47));
    title.setBounds(45, 100, 300, 30);
    panel.add(title);

    JLabel desc = new JLabel("Aircraft information for flights assigned to this pilot.");
    desc.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    desc.setForeground(new Color(90, 90, 90));
    desc.setBounds(45, 128, 600, 25);
    panel.add(desc);

    JTable table = createModelTable(
            pilotDAO.getPilotAircraftStatus(loggedEmployeeId)
    );

    if (table.getColumnCount() > 5) {
        table.getColumnModel().getColumn(5).setCellRenderer(new StatusRenderer());
    }

    JScrollPane scrollPane = new JScrollPane(table);
    scrollPane.setBounds(45, 170, 815, 300);
    scrollPane.setBorder(new RoundedBorder(new Color(205, 170, 80), 1, 18));
    panel.add(scrollPane);

    JButton refreshBtn = goldButton("Refresh Aircraft Status");
    refreshBtn.setBounds(45, 505, 220, 38);
    panel.add(refreshBtn);

    refreshBtn.addActionListener(e -> {
        table.setModel(pilotDAO.getPilotAircraftStatus(loggedEmployeeId));

        if (table.getColumnCount() > 5) {
            table.getColumnModel().getColumn(5).setCellRenderer(new StatusRenderer());
        }

        JOptionPane.showMessageDialog(this, "Aircraft status refreshed successfully.");
    });
}
private void buildCrewAircraftOperations(JPanel panel) {

    AircraftStatusDAO dao = new AircraftStatusDAO();

    JTable table = createModelTable(dao.getAllAircrafts());

    table.setRowHeight(30);

    if (table.getColumnCount() > 4) {
        table.getColumnModel()
                .getColumn(4)
                .setCellRenderer(new StatusRenderer());
    }

    JScrollPane scroll = new JScrollPane(table);
    scroll.setBounds(40, 120, 820, 260);
    panel.add(scroll);

    JLabel statusLabel = new JLabel("New Status:");
    statusLabel.setBounds(40, 410, 120, 30);
    panel.add(statusLabel);

    JComboBox<String> statusBox = combo(
            new String[]{
                    "Ready",
                    "Maintenance",
                    "Grounded"
            },
            150,
            410
    );
    panel.add(statusBox);

    JLabel descLabel = new JLabel("Description:");
    descLabel.setBounds(40, 470, 120, 30);
    panel.add(descLabel);

    JTextField descField = new JTextField();
    descField.setBounds(150, 470, 300, 35);
    panel.add(descField);

    JButton updateBtn = goldButton("Update Aircraft");
    updateBtn.setBounds(480, 470, 180, 38);
    panel.add(updateBtn);

    updateBtn.addActionListener(e -> {

        int row = table.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Select aircraft first."
            );
            return;
        }

        String description = descField.getText().trim();

        if (description.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please enter description."
            );
            return;
        }

        int aircraftId = Integer.parseInt(
                table.getValueAt(row, 0).toString()
        );

        String newStatus = statusBox.getSelectedItem().toString();

        boolean success = dao.updateAircraftStatus(
                aircraftId,
                newStatus,
                description
        );

     if (success) {

    JOptionPane.showMessageDialog(
            this,
            "Aircraft updated successfully."
    );

    NotificationCenter.addAdminNotification(
            "Aircraft "
                    + aircraftId
                    + " status changed to "
                    + newStatus
    );

    NotificationCenter.addAdminNotification(
            "Maintenance update added for Aircraft "
                    + aircraftId
                    + " | Description: "
                    + description
    );

    table.setModel(dao.getAllAircrafts());

    if (table.getColumnCount() > 4) {
        table.getColumnModel()
                .getColumn(4)
                .setCellRenderer(new StatusRenderer());
    }

    descField.setText("");
    statusBox.setSelectedIndex(0);

} else {

    JOptionPane.showMessageDialog(
            this,
            "Update failed."
    );
}
    });
}

    // ===================== HELPERS =====================

   private JTable createModelTable(DefaultTableModel model) {
    JTable table = new JTable(model) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    table.setRowHeight(34);
    table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
    table.getTableHeader().setBackground(new Color(10, 25, 47));
    table.getTableHeader().setForeground(Color.WHITE);
    table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
    table.setSelectionBackground(new Color(220, 230, 245));
    table.setGridColor(new Color(220, 220, 220));
    table.setShowGrid(true);

    return table;
}

    private JTable createReadOnlyTable(Object[][] rows, String[] cols) {
        JTable table = new JTable(rows, cols) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table.setRowHeight(34);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.getTableHeader().setBackground(new Color(10, 25, 47));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        table.setSelectionBackground(new Color(223, 231, 242));

        return table;
    }

    private JLabel label(String text, int x, int y) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setForeground(new Color(10, 25, 47));
        label.setBounds(x, y, 120, 30);
        return label;
    }

    private JTextField field(String text, int x, int y) {
        JTextField field = new JTextField(text);
        field.setBounds(x, y, 160, 32);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        field.setBorder(new RoundedBorder(new Color(180, 180, 180), 1, 14));
        return field;
    }

    private JComboBox<String> combo(String[] values, int x, int y) {
        JComboBox<String> combo = new JComboBox<>(values);
        combo.setBounds(x, y, 160, 32);
        combo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        combo.setBackground(Color.WHITE);
        return combo;
    }

    private JTextArea noteArea(String text) {
        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        area.setText(text);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setBorder(new RoundedBorder(new Color(205, 170, 80), 1, 18));
        return area;
    }

   private JButton goldButton(String text) {

    JButton button = new JButton(text);

    button.setBackground(new Color(214, 177, 86));
    button.setForeground(Color.WHITE);

    button.setFont(new Font("Segoe UI", Font.BOLD, 13));

    button.setFocusPainted(false);

    button.setBorder(
            new RoundedBorder(
                    new Color(10, 25, 47),
                    1,
                    18
            )
    );

    button.setCursor(new Cursor(Cursor.HAND_CURSOR));

    button.addMouseListener(new java.awt.event.MouseAdapter() {

        public void mouseEntered(java.awt.event.MouseEvent evt) {

            button.setBackground(
                    new Color(190, 150, 60)
            );
        }

        public void mouseExited(java.awt.event.MouseEvent evt) {

            button.setBackground(
                    new Color(214, 177, 86)
            );
        }
    });

    return button;
}
    private static class StatusRenderer extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(
            JTable table,
            Object value,
            boolean isSelected,
            boolean hasFocus,
            int row,
            int column
    ) {

        Component c = super.getTableCellRendererComponent(
                table, value, isSelected, hasFocus, row, column
        );

        if (isSelected) {
            return c;
        }

        c.setBackground(Color.WHITE);
        c.setForeground(Color.BLACK);

        if (value == null) {
            return c;
        }

        String status = value.toString();

        if (status.equalsIgnoreCase("On Time")) {
            c.setBackground(new Color(46, 204, 113));
            c.setForeground(Color.WHITE);

        } else if (status.equalsIgnoreCase("Delayed")) {
            c.setBackground(new Color(241, 196, 15));
            c.setForeground(Color.BLACK); 

        } else if (status.equalsIgnoreCase("Boarding")) {
            c.setBackground(new Color(52, 152, 219));
            c.setForeground(Color.WHITE);
            
        }else if (status.equalsIgnoreCase("Cancelled")) {
            c.setBackground(new Color(231, 76, 60));
            c.setForeground(Color.WHITE);}

        return c;
    }
}
    private static class BaggageStatusRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(
            JTable table,
            Object value,
            boolean isSelected,
            boolean hasFocus,
            int row,
            int column
    ) {
        Component c = super.getTableCellRendererComponent(
                table, value, isSelected, hasFocus, row, column
        );

        if (value == null) {
            c.setBackground(Color.WHITE);
            c.setForeground(Color.BLACK);
            return c;
        }

        String status = value.toString();

        if (status.equalsIgnoreCase("Checked In")) {
            c.setBackground(new Color(52, 152, 219));
            c.setForeground(Color.WHITE);
        } else if (status.equalsIgnoreCase("Loaded")) {
            c.setBackground(new Color(241, 196, 15));
            c.setForeground(Color.BLACK);
        } else if (status.equalsIgnoreCase("In Transit")) {
            c.setBackground(new Color(155, 89, 182));
            c.setForeground(Color.WHITE);
        } else if (status.equalsIgnoreCase("Arrived")) {
            c.setBackground(new Color(46, 204, 113));
            c.setForeground(Color.WHITE);
        } else if (status.equalsIgnoreCase("Lost") || status.equalsIgnoreCase("Delayed")) {
            c.setBackground(new Color(231, 76, 60));
            c.setForeground(Color.WHITE);
        } else {
            c.setBackground(Color.WHITE);
            c.setForeground(Color.BLACK);
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

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(color);
            g2.setStroke(new BasicStroke(thickness));
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(7, 10, 7, 10);
        }
    }  
    
   private void buildBoardingPassManagement(JPanel panel) {
    GroundCrewDAO dao = new GroundCrewDAO();

    panel.add(label("Ticket ID:", 45, 115));
    JTextField ticketIdField = field("", 140, 115);
    panel.add(ticketIdField);

    panel.add(label("Gate ID:", 330, 115));
    JTextField gateIdField = field("", 420, 115);
    panel.add(gateIdField);

    JButton addBtn = goldButton("Create Boarding Pass");
    addBtn.setBounds(600, 115, 210, 35);
    panel.add(addBtn);

    JTable table = createModelTable(dao.getBoardingPasses());

    JScrollPane scrollPane = new JScrollPane(table);
    scrollPane.setBounds(45, 180, 815, 300);
    scrollPane.setBorder(new RoundedBorder(new Color(205, 170, 80), 1, 18));
    panel.add(scrollPane);

    JButton refreshBtn = goldButton("Refresh");
    refreshBtn.setBounds(45, 510, 120, 38);
    panel.add(refreshBtn);

    addBtn.addActionListener(e -> {
        try {
            int ticketId = Integer.parseInt(ticketIdField.getText().trim());
            int gateId = Integer.parseInt(gateIdField.getText().trim());

            String ticketStatus = dao.getTicketStatusById(ticketId);

            if (ticketStatus == null) {
                JOptionPane.showMessageDialog(this, "Ticket does not exist.");
                return;
            }

            if (ticketStatus.equalsIgnoreCase("Cancelled")) {
                JOptionPane.showMessageDialog(
                        this,
                        "Cannot create boarding pass.\nReason: Ticket is cancelled."
                );
                return;
            }

            if (ticketStatus.equalsIgnoreCase("Boarded")) {
                JOptionPane.showMessageDialog(
                        this,
                        "Cannot create boarding pass.\nReason: Passenger already boarded."
                );
                return;
            }

            boolean success = dao.addBoardingPass(ticketId, gateId);

            if (success) {
                JOptionPane.showMessageDialog(this, "Boarding pass created successfully.");

                NotificationCenter.addAdminNotification(
                        "Boarding pass created for Ticket "
                                + ticketId
                                + " | Gate ID: "
                                + gateId
                );

                table.setModel(dao.getBoardingPasses());
                ticketIdField.setText("");
                gateIdField.setText("");

            } else {
                JOptionPane.showMessageDialog(this, "Failed to create boarding pass.");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Please check Ticket ID and Gate ID.");
        }
    });

    refreshBtn.addActionListener(e -> {
        table.setModel(dao.getBoardingPasses());
    });
}
   private void buildCabinCrewPassengers(JPanel panel) {

    CabinCrewDAO dao = new CabinCrewDAO();
    
    JLabel searchLabel = new JLabel("Flight Number:");
    searchLabel.setBounds(45, 135, 120, 30);
    panel.add(searchLabel);

JTextField flightSearchField = new JTextField();
flightSearchField.setBounds(160, 135, 180, 35);
panel.add(flightSearchField);

JButton searchBtn = goldButton("Search");
searchBtn.setBounds(360, 135, 120, 35);panel.add(searchBtn);

    JLabel desc = new JLabel("Passengers shown only for flights assigned to this cabin crew member.");
    desc.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    desc.setForeground(new Color(90, 90, 90));
    desc.setBounds(45, 105, 700, 25);
    panel.add(desc);

    JTable table = createModelTable(
            dao.getPassengersForCabinCrew(loggedEmployeeId)
    );
    searchBtn.addActionListener(e -> {

    String flightNo = flightSearchField.getText().trim();

    if (flightNo.isEmpty()) {

        JOptionPane.showMessageDialog(
                this,
                "Please enter flight number."
        );

        return;
    }

    table.setModel(
            dao.getPassengersByFlightNumber(
                    loggedEmployeeId,
                    flightNo
            )
    );
});

    JScrollPane scrollPane = new JScrollPane(table);
    scrollPane.setBounds(45, 190, 815, 280);
    scrollPane.setBorder(new RoundedBorder(new Color(205, 170, 80), 1, 18));
    panel.add(scrollPane);

    JButton refreshBtn = goldButton("Refresh Passengers");
    refreshBtn.setBounds(45, 500, 180, 38);
    panel.add(refreshBtn);

    refreshBtn.addActionListener(e -> {
        table.setModel(dao.getPassengersForCabinCrew(loggedEmployeeId));
        JOptionPane.showMessageDialog(this, "Passenger list refreshed successfully.");
    });
}
   private void buildCabinCrewSchedule(JPanel panel) {

    CabinCrewDAO dao = new CabinCrewDAO();

    JLabel desc = new JLabel("Flight schedule assigned to this cabin crew member.");
    desc.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    desc.setForeground(new Color(90, 90, 90));
    desc.setBounds(45, 105, 700, 25);
    panel.add(desc);

    JTable table = createModelTable(
            dao.getCabinCrewFlights(loggedEmployeeId)
    );

    if (table.getColumnCount() > 6) {
        table.getColumnModel().getColumn(6).setCellRenderer(new StatusRenderer());
    }

    JScrollPane scrollPane = new JScrollPane(table);
    scrollPane.setBounds(45, 150, 815, 320);
    scrollPane.setBorder(new RoundedBorder(new Color(205, 170, 80), 1, 18));
    panel.add(scrollPane);

    JButton refreshBtn = goldButton("Refresh Schedule");
    refreshBtn.setBounds(45, 500, 170, 38);
    panel.add(refreshBtn);

    refreshBtn.addActionListener(e -> {
        table.setModel(dao.getCabinCrewFlights(loggedEmployeeId));

        if (table.getColumnCount() > 6) {
            table.getColumnModel().getColumn(6).setCellRenderer(new StatusRenderer());
        }

        JOptionPane.showMessageDialog(this, "Cabin crew schedule refreshed successfully.");
    });
}
}
 
