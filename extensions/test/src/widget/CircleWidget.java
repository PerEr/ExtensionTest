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
        Dimension d = getSize();
        graphics.setColor(++counter % 2 == 0 ? Color.ORANGE : Color.GREEN);
        graphics.drawOval(0, 0, d.width, d.height);
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

    int counter = 0;

    final static String NAME = new String("circle");
}
