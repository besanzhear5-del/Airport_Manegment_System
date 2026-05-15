package airboard.java. ui;

import javax.swing.*;
import java.awt.*;

public class FlightPanel extends JPanel {

    public FlightPanel() {

        setLayout(new BorderLayout());

        JTextField search = new JTextField();
        JButton add = new JButton("Add Flight");

        JTable table = new JTable();

        add(search, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(add, BorderLayout.SOUTH);
    }
}