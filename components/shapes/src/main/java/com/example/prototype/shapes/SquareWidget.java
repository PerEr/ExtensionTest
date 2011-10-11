package com.example.prototype.shapes;

import com.example.prototype.api.widget.WidgetFactory;

import javax.swing.*;
import java.awt.*;
import java.util.Properties;


class SquareWidget extends JComponent {

    private Color color;

    SquareWidget(Color color) {
        this.color = color;
        setOpaque(false);
    }

    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);
        Dimension d = getSize();
        graphics.setColor(color);
        graphics.fillRect(0, 0, d.width, d.height);
        graphics.setColor(Color.BLACK);
        graphics.drawRect(0, 0, d.width, d.height);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(100,100);
    }

    static class Factory implements WidgetFactory {
        @Override
        public JComponent instantiate(Properties prp) {
            int colorAsInt = Integer.parseInt(prp.getProperty("color", "ff0000"), 16);
            return new SquareWidget(new Color(colorAsInt));
        }
    }

    final static String NAME = "square";
}