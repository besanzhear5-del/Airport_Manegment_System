package airboard.java.UI;

import airboard.java.DAO.EmployeeDAO;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import java.awt.*;

public class LoginFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> roleBox;
    private JCheckBox showPasswordCheck;

    public LoginFrame() {
        setTitle("The Royal Journey - Login");
        setSize(1220, 720);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        initComponents();
    }

private void initComponents() {

    JPanel mainPanel = new BackgroundPanel(
        "/airboard/java/images/login_bg.jpg"
);

    AnimatedFlightPathPanel flightPath = new AnimatedFlightPathPanel();
    mainPanel.add(flightPath);

    JPanel loginCard = new JPanel(null);
    loginCard.setBackground(new Color(18, 29, 45, 235));
    loginCard.setBounds(720, 85, 420, 520);
    loginCard.setBorder(new RoundedBorder(new Color(214, 177, 86), 2, 32));
    loginCard.setOpaque(true);
    mainPanel.add(loginCard);

    JLabel planeIcon = new JLabel("✈ THE ROYAL JOURNEY");
    planeIcon.setFont(new Font("Dialog", Font.BOLD, 25));
    planeIcon.setForeground(new Color(214, 177, 86));
    planeIcon.setHorizontalAlignment(SwingConstants.CENTER);
    planeIcon.setBounds(40, 55, 340, 50);
    loginCard.add(planeIcon);

    JLabel subtitle = new JLabel("Airport Management System");
    subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 15));
    subtitle.setForeground(Color.WHITE);
    subtitle.setHorizontalAlignment(SwingConstants.CENTER);
    subtitle.setBounds(40, 105, 340, 25);
    loginCard.add(subtitle);

    JLabel welcome = new JLabel("Welcome Back");
    welcome.setFont(new Font("Segoe UI", Font.BOLD, 25));
    welcome.setForeground(Color.WHITE);
    welcome.setHorizontalAlignment(SwingConstants.CENTER);
    welcome.setBounds(40, 165, 340, 35);
    loginCard.add(welcome);

    usernameField = new JTextField("Username / Employee ID") {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2 = (Graphics2D) g.create();
            g2.setFont(new Font("Dialog", Font.PLAIN, 18));
            g2.setColor(new Color(220, 220, 220));
            g2.drawString("👤", 16, 30);
            g2.dispose();
        }
    };

    usernameField.setBounds(55, 240, 310, 45);
    usernameField.setFont(new Font("Segoe UI", Font.PLAIN, 15));
    usernameField.setForeground(new Color(170, 175, 185));
    usernameField.setCaretColor(Color.WHITE);
    usernameField.setBackground(new Color(20, 35, 55));
    usernameField.setBorder(
    BorderFactory.createCompoundBorder(
        new RoundedBorder(new Color(95, 110, 130), 1, 18),
        BorderFactory.createEmptyBorder(0, 55, 0, 10)
    )
);
    

    usernameField.addFocusListener(new java.awt.event.FocusAdapter() {
        public void focusGained(java.awt.event.FocusEvent evt) {
            if (usernameField.getText().equals("Username / Employee ID")) {
                usernameField.setText("");
                usernameField.setForeground(Color.WHITE);
            }
        }

        public void focusLost(java.awt.event.FocusEvent evt) {
            if (usernameField.getText().trim().isEmpty()) {
                usernameField.setText("Username / Employee ID");
                usernameField.setForeground(new Color(170, 175, 185));
            }
        }
    });

    loginCard.add(usernameField);

    passwordField = new JPasswordField("Password") {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2 = (Graphics2D) g.create();
            g2.setFont(new Font("Dialog", Font.PLAIN, 18));
            g2.setColor(new Color(220, 220, 220));
            g2.drawString("🔒", 16, 30);
            g2.dispose();
        }
    };

    passwordField.setBounds(55, 305, 310, 45);
    passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 15));
    passwordField.setForeground(new Color(170, 175, 185));
    passwordField.setCaretColor(Color.WHITE);
    passwordField.setBackground(new Color(20, 35, 55));
    passwordField.setBorder(
    BorderFactory.createCompoundBorder(
        new RoundedBorder(new Color(95, 110, 130), 1, 18),
        BorderFactory.createEmptyBorder(0, 55, 0, 10)
    )
);
    passwordField.setEchoChar((char) 0);
    

    passwordField.addFocusListener(new java.awt.event.FocusAdapter() {
        public void focusGained(java.awt.event.FocusEvent evt) {
            String pass = new String(passwordField.getPassword());

            if (pass.equals("Password")) {
                passwordField.setText("");
                passwordField.setForeground(Color.WHITE);
                passwordField.setEchoChar('•');
            }
        }

        public void focusLost(java.awt.event.FocusEvent evt) {
            String pass = new String(passwordField.getPassword());

            if (pass.trim().isEmpty()) {
                passwordField.setEchoChar((char) 0);
                passwordField.setText("Password");
                passwordField.setForeground(new Color(170, 175, 185));
            }
        }
    });

    loginCard.add(passwordField);

    showPasswordCheck = new JCheckBox("Show Password");
    showPasswordCheck.setBounds(55, 358, 160, 25);
    showPasswordCheck.setOpaque(false);
    showPasswordCheck.setFont(new Font("Segoe UI", Font.PLAIN, 12));
    showPasswordCheck.setForeground(Color.WHITE);

    showPasswordCheck.addActionListener(e -> {
        String pass = new String(passwordField.getPassword());

        if (pass.equals("Password")) {
            return;
        }

        if (showPasswordCheck.isSelected()) {
            passwordField.setEchoChar((char) 0);
        } else {
            passwordField.setEchoChar('•');
        }
    });

    loginCard.add(showPasswordCheck);

    roleBox = new JComboBox<>(new String[]{"Admin", "Employee", "Passenger"});
    roleBox.setBounds(55, 395, 310, 42);
    roleBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    roleBox.setBackground(new Color(20, 35, 55));
    roleBox.setForeground(Color.WHITE);
    roleBox.setBorder(new RoundedBorder(new Color(95, 110, 130), 1, 18));
    loginCard.add(roleBox);

    JButton loginButton = modernGoldButton("Login");
    loginButton.setBounds(55, 455, 145, 45);
    loginCard.add(loginButton);

    JButton exitButton = modernDarkButton("Exit");
    exitButton.setBounds(220, 455, 145, 48);
    loginCard.add(exitButton);

    loginButton.addActionListener(e -> loginAction());
    exitButton.addActionListener(e -> System.exit(0));

    add(mainPanel);
}
   private void loginAction() {
    String username = usernameField.getText().trim();
String password = new String(passwordField.getPassword()).trim();

if (username.equals("Username / Employee ID")) {
    username = "";
}

if (password.equals("Password")) {
    password = "";
}
    String selectedRole = roleBox.getSelectedItem().toString();

    if (selectedRole.equals("Passenger")) {
        handlePassengerLogin();
        return;
    }

    if (username.isEmpty() || password.isEmpty()) {
        JOptionPane.showMessageDialog(
                this,
                "Please enter username and password."
        );
        return;
    }

    if (selectedRole.equals("Admin")) {
        handleAdminLogin(username, password);

    } else if (selectedRole.equals("Employee")) {
        handleEmployeeLogin(username, password);
    }
}

    private void handleAdminLogin(String username, String password) {
        boolean adminOne =
                username.equalsIgnoreCase("Besan zhear")
                        && password.equals("12324177");

        boolean adminTwo =
                username.equalsIgnoreCase("Noura herbawi")
                        && password.equals("12325106");

        if (adminOne || adminTwo) {
            JOptionPane.showMessageDialog(this, "Admin login successful.");

            new MainFrame(username, "Admin").setVisible(true);
            dispose();

        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "Invalid admin login.\nOnly authorized admins can access the Admin dashboard."
            );
        }
    }

  private void handleEmployeeLogin(String username, String password) {

    EmployeeDAO employeeDAO = new EmployeeDAO();

    try {

        int employeeId = Integer.parseInt(username);

        boolean validEmployee =
                employeeDAO.validateEmployeeLogin(
                        employeeId,
                        password
                );

        if (validEmployee) {

            String employeeName =
                    employeeDAO.getEmployeeNameById(employeeId);

            String employeeRole =
                    employeeDAO.getEmployeeRoleById(employeeId);

            String crewPosition =
                    employeeDAO.getCrewPositionById(employeeId);

            EmployeeSession.setEmployee(
                    employeeId,
                    employeeName,
                    employeeRole,
                    crewPosition
            );

            String dashboardRole;

            if (employeeRole.equalsIgnoreCase("Pilot")) {

                dashboardRole = "Pilot";

            } else if (
                    employeeRole.equalsIgnoreCase("Crew")
                    && crewPosition.equalsIgnoreCase("Cabin")
            ) {

                dashboardRole = "Cabin Crew";

            } else {

                dashboardRole = "Ground Crew";
            }

            JOptionPane.showMessageDialog(
                    this,
                    "Employee login successful.\n"
                            + "Welcome, "
                            + employeeName
                            + "\nRole: "
                            + dashboardRole
            );

            new MainFrame(
                    employeeName,
                    dashboardRole,
                    "Dashboard",
                    employeeId
            ).setVisible(true);

            dispose();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid employee ID or password."
            );
        }

    } catch (Exception ex) {

        JOptionPane.showMessageDialog(
                this,
                "Employee ID must be a number."
        );
    }
}

    private void handlePassengerLogin() {
    JOptionPane.showMessageDialog(
            this,
            "Passenger login successful."
    );

    new MainFrame(
            "Passenger",
            "Passenger",
            "Dashboard",
            0
    ).setVisible(true);

    dispose();
}
    private JTextField modernTextField() {
    JTextField field = new JTextField();
    field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    field.setBorder(new RoundedBorder(new Color(180, 180, 180), 1, 18));
    field.setBackground(Color.WHITE);
    return field;
}

private JButton modernGoldButton(String text) {
    JButton button = new JButton(text);
    button.setBackground(new Color(214, 177, 86));
    button.setForeground(Color.WHITE);
    button.setFont(new Font("Segoe UI", Font.BOLD, 15));
    button.setFocusPainted(false);
    button.setContentAreaFilled(false);
    button.setOpaque(true);
    button.setBorder(new RoundedBorder(new Color(214, 177, 86), 1, 30));
    button.setCursor(new Cursor(Cursor.HAND_CURSOR));

    return button;
}

private JButton modernDarkButton(String text) {
    JButton button = new JButton(text);
    button.setBackground(new Color(10, 25, 47));
    button.setForeground(Color.WHITE);
    button.setFont(new Font("Segoe UI", Font.BOLD, 15));
    button.setFocusPainted(false);
    button.setContentAreaFilled(false);
    button.setOpaque(true);
    button.setBorder(new RoundedBorder(new Color(214, 177, 86), 1, 30));
    button.setCursor(new Cursor(Cursor.HAND_CURSOR));

    return button;
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
    
}