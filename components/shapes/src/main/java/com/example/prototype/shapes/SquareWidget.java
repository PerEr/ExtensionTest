package com.example.prototype.shapes;

import com.example.prototype.api.widget.WidgetFactory;

import javax.swing.*;
import java.awt.*;
import java.util.Properties;


class SquareWidget extends JComponent {

    private ShapeConfig shapeConfig;

    public SquareWidget(ShapeConfig shapeConfig) {
        this.shapeConfig = shapeConfig;
        setOpaque(false);
    }

    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);
        final Dimension d = getSize();
        graphics.setColor(shapeConfig.color());
        graphics.fillRect(0, 0, d.width-1, d.height-1);
        graphics.setColor(Color.BLACK);
        graphics.drawRect(0, 0, d.width-1, d.height-1);
    }

    @Override
    public Dimension getPreferredSize() {
        return shapeConfig.dimension();
    }

    static class Factory implements WidgetFactory {
        @Override
        public JComponent instantiate(Properties prp) {
            final ShapeConfig shapeConfig = new ShapeConfig(prp);
            return new SquareWidget(shapeConfig);
        }
    }

    final static String NAME = "square";
}