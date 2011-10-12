package com.example.prototype.shapes;


import com.example.prototype.api.widget.WidgetFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;

class CircleWidget extends JComponent {

    private int colorValue;
    private int deltaColorValue;
    private final ShapeConfig shapeConfig;

    CircleWidget(ShapeConfig shapeConfig) {
        this.shapeConfig = shapeConfig;
        setOpaque(false);
        colorValue = 0;
        deltaColorValue = 8;
        timer.start();
    }

    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);
        final Dimension d = getSize();
        graphics.setColor(shapeConfig.color());
        graphics.fillOval(0, 0, d.width, d.height);
        final Color borderColor = new Color(colorValue, colorValue, colorValue);
        graphics.setColor(borderColor);
        graphics.drawOval(0, 0, d.width-1, d.height-1);
        graphics.drawOval(1, 1, d.width-3, d.height-3);
    }

    @Override
    public Dimension getPreferredSize() {
        return shapeConfig.dimension();
    }

    private void onTimer() {
        colorValue += deltaColorValue;
        if (colorValue == (256-deltaColorValue) || colorValue == 0)
            deltaColorValue = -deltaColorValue;
        repaint();
    }
    static class Factory implements WidgetFactory {

        @Override
        public JComponent instantiate(Properties prp) {
            final ShapeConfig shapeConfig = new ShapeConfig(prp);
            return new CircleWidget(shapeConfig);
        }
    }

    private Timer timer = new Timer(100, new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            onTimer();
        }
    });

    final static String NAME = "circle";
}
