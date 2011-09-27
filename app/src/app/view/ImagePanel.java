package app.view;

import javax.swing.*;
import java.awt.*;

public class ImagePanel extends JPanel {

    public ImagePanel(Image image) {
        m_image = image;
    }

    @Override
    public void paintComponent(Graphics graphics) {
//        super.paint(graphics);
        final Insets insets = getInsets();
        graphics.drawImage(m_image, insets.left, insets.top, this);
    }

    private Image m_image;
}
