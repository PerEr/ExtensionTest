package widget;

import api.widget.WidgetFactory;

import javax.swing.*;
import java.awt.*;


class SquareWidget extends JPanel {

    SquareWidget() {
        setOpaque(false);
    }

    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);
        graphics.setColor(Color.WHITE);
        graphics.drawRect(10, 10, 70, 70);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(100,100);
    }

    static class Factory implements WidgetFactory {
        @Override
        public JPanel instantiate() {
            return new SquareWidget();
        }
    }
}