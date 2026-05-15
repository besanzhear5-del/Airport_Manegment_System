package airboard.java.UI;

import javax.swing.*;
import java.awt.*;

public class BackgroundPanel extends JPanel {

    private Image backgroundImage;

    public BackgroundPanel(String imagePath) {
        backgroundImage = new ImageIcon(
                getClass().getResource(imagePath)
        ).getImage();

        setLayout(null);
    }

    @Override
protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    Graphics2D g2 = (Graphics2D) g.create();

    if (backgroundImage != null) {

        g2.drawImage(
                backgroundImage,
                0,
                0,
                getWidth(),
                getHeight(),
                this
        );
    }

    // dark overlay
    g2.setColor(new Color(3, 10, 22, 170));

    g2.fillRect(
            0,
            0,
            getWidth(),
            getHeight()
    );

    g2.dispose();
}
}