package shapes;


import api.widget.WidgetFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;

class CircleWidget extends JComponent {

    private Color color;
    private int colorValue;
    private int deltaColorValue;

    CircleWidget(Color color) {
        this.color = color;
        setOpaque(false);
        colorValue = 0;
        deltaColorValue = 8;
        timer.start();
    }

    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);
        Dimension d = getSize();
        graphics.setColor(color);
        graphics.fillOval(0, 0, d.width, d.height);
        Color borderColor = new Color(colorValue, colorValue, colorValue);
        graphics.setColor(borderColor);
        graphics.drawOval(0, 0, d.width, d.height);
        graphics.drawOval(1, 1, d.width-2, d.height-2);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(100,100);
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
            int colorAsInt = Integer.parseInt(prp.getProperty("color", "ff0000"), 16);
            return new CircleWidget(new Color(colorAsInt));
        }
    }

    private Timer timer = new Timer(100, new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            onTimer();
        }
    });

    final static String NAME = "circle";
}
