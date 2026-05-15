package airboard.java. ui;

import javax.swing.*;
import java.awt.*;

public class PassengerPanel extends JPanel {

    public PassengerPanel() {

        setLayout(new BorderLayout());

        JTextField search = new JTextField();
        JTable table = new JTable();

        add(search, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }
}


