package airboard.java.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;

public class AnimatedFlightPathPanel extends JPanel {

    private double t = 0;
    private Timer timer;

    public AnimatedFlightPathPanel() {
        setOpaque(false);
        setBounds(0, 0, 1220, 720);

        timer = new Timer(25, e -> {
            t += 0.0035;

            if (t > 1) {
                t = 0;
            }

            repaint();
        });

        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        Path2D path = buildPath();

        float[] dash = {10f, 12f};

        g2.setStroke(new BasicStroke(
                2.4f,
                BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_ROUND,
                0,
                dash,
                0
        ));

        g2.setColor(new Color(214, 177, 86, 210));
        g2.draw(path);

        Point current = getPointOnPath(t);
        Point next = getPointOnPath(Math.min(t + 0.01, 1));

        double angle = Math.atan2(
                next.y - current.y,
                next.x - current.x
        );

        drawPlane(g2, current.x, current.y, angle);

        g2.dispose();
    }

    private Path2D buildPath() {
        Path2D path = new Path2D.Double();

        path.moveTo(120, 610);

        // big left curve going up
        path.curveTo(20, 420, 55, 190, 210, 105);

        // top loop
        path.curveTo(410, -5, 650, 55, 600, 205);
        path.curveTo(560, 335, 325, 305, 330, 190);
        path.curveTo(335, 80, 500, 95, 515, 210);

        // middle crossing curve
        path.curveTo(540, 380, 280, 285, 260, 455);

        // small bottom loop
        path.curveTo(245, 575, 430, 565, 410, 455);
        path.curveTo(395, 375, 285, 415, 315, 510);

        // long bottom curve ending behind login card
        path.curveTo(380, 680, 720, 690, 900, 575);
        path.curveTo(960, 540, 1010, 500, 1060, 450);

        return path;
    }

    private Point getPointOnPath(double t) {
        double x;
        double y;

        if (t < 0.18) {
            double u = t / 0.18;
            x = bezier(120, 20, 55, 210, u);
            y = bezier(610, 420, 190, 105, u);

        } else if (t < 0.36) {
            double u = (t - 0.18) / 0.18;
            x = bezier(210, 410, 650, 600, u);
            y = bezier(105, -5, 55, 205, u);

        } else if (t < 0.50) {
            double u = (t - 0.36) / 0.14;
            x = bezier(600, 560, 325, 330, u);
            y = bezier(205, 335, 305, 190, u);

        } else if (t < 0.61) {
            double u = (t - 0.50) / 0.11;
            x = bezier(330, 335, 500, 515, u);
            y = bezier(190, 80, 95, 210, u);

        } else if (t < 0.73) {
            double u = (t - 0.61) / 0.12;
            x = bezier(515, 540, 280, 260, u);
            y = bezier(210, 380, 285, 455, u);

        } else if (t < 0.82) {
            double u = (t - 0.73) / 0.09;
            x = bezier(260, 245, 430, 410, u);
            y = bezier(455, 575, 565, 455, u);

        } else if (t < 0.89) {
            double u = (t - 0.82) / 0.07;
            x = bezier(410, 395, 285, 315, u);
            y = bezier(455, 375, 415, 510, u);

        } else if (t < 0.97) {
            double u = (t - 0.89) / 0.08;
            x = bezier(315, 380, 720, 900, u);
            y = bezier(510, 680, 690, 575, u);

        } else {
            double u = (t - 0.97) / 0.03;
            x = bezier(900, 960, 1010, 1060, u);
            y = bezier(575, 540, 500, 450, u);
        }

        return new Point((int) x, (int) y);
    }

    private double bezier(double p0, double p1, double p2, double p3, double t) {
        return Math.pow(1 - t, 3) * p0
                + 3 * Math.pow(1 - t, 2) * t * p1
                + 3 * (1 - t) * Math.pow(t, 2) * p2
                + Math.pow(t, 3) * p3;
    }

    private void drawPlane(Graphics2D g2, int x, int y, double angle) {
    Graphics2D p = (Graphics2D) g2.create();

    p.translate(x, y);
    p.rotate(angle);

    p.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    p.setFont(new Font("Dialog", Font.BOLD, 48));
    p.setColor(new Color(214, 177, 86));

    p.drawString("✈", -24, 18);

    p.dispose();
}
}