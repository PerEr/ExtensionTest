package com.example.prototype.app.view;

import javax.swing.*;
import java.awt.*;

class ImagePanel extends JPanel {

    ImagePanel(Image image) {
        m_image = image;
    }

    @Override
    public void paintComponent(Graphics graphics) {
        final Insets insets = getInsets();
        graphics.drawImage(m_image, insets.left, insets.top, this);
    }

    private Image m_image;
}
