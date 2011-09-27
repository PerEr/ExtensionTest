package widget;


import api.widget.WidgetFactory;

import javax.swing.*;
import java.awt.*;

public class CircleWidget extends JPanel {

    CircleWidget() {
        setOpaque(false);
    }

    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);
        graphics.setColor(Color.ORANGE);
        graphics.drawOval(10, 10, 40, 40);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(100,100);
    }

    static class Factory implements WidgetFactory {

        @Override
        public JPanel build() {
            return new CircleWidget();
        }
    }
}
