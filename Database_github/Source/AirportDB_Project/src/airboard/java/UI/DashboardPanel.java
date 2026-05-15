package airboard.java. ui;

import javax.swing.*;
import java.awt.*;

public class DashboardPanel extends JPanel {

    public DashboardPanel() {

        setLayout(new GridLayout(2,2));

        add(card("Flights"));
        add(card("Passengers"));
        add(card("Aircraft"));
        add(card("Airports"));
    }

    JPanel card(String title) {
        JPanel p = new JPanel();
        p.setBackground(new Color(40,40,60));
        JLabel l = new JLabel(title);
        l.setForeground(Color.WHITE);
        p.add(l);
        return p;
    }
}