package widget;

import api.widget.WidgetBuilder;

import javax.swing.*;
import java.awt.*;


public class SquareWidget extends JPanel {

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

    static class Builder implements WidgetBuilder {
        @Override
        public JPanel build() {
            return new SquareWidget();
        }
    }
}