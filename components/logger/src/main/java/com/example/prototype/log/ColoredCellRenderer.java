package com.example.prototype.log;

import javax.swing.*;
import java.awt.*;

class ColoredCellRenderer extends JLabel implements ListCellRenderer {


    public ColoredCellRenderer() {
        setOpaque(true);

        // Pre-load the graphics images to save time
        image = new ImageIcon[4];
        image[0] = new ImageIcon("circles.gif");
        image[1] = new ImageIcon("bubbles.gif");
        image[2] = new ImageIcon("thatch.gif");
        image[3] = new ImageIcon("pinstripe.gif");
    }

    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
                                                  boolean cellHasFocus) {

        LogEntry logEntry = (LogEntry) value;

        // Display the text for this item
        setText(logEntry.message);

        // Set the correct image
        setIcon(image[index % 4]);

        setForeground(logEntry.color);

        // Draw the correct colors and font
        if (isSelected) {
            // Set the color and font for a selected item
            setBackground(Color.yellow);
            setFont(selectedFont);
        } else {
            // Set the color and font for an unselected item
            setBackground(Color.white);
            setFont(plainFont);
        }

        return this;
    }

    private ImageIcon image[];
    private Font selectedFont = new Font("Roman", Font.BOLD, 12);
    private Font plainFont = new Font("Roman", Font.PLAIN, 12);
}