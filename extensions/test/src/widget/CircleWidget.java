package widget;


import api.widget.WidgetFactory;

import javax.swing.*;
import java.awt.*;

class CircleWidget extends JComponent {

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
        public JComponent instantiate() {
            return new CircleWidget();
        }
    }

    final static String NAME = new String("circle");
}
