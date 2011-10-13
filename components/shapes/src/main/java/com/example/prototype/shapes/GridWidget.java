package com.example.prototype.shapes;

import com.example.prototype.api.widget.WidgetFactory;
import com.example.prototype.api.widget.WidgetRegistry;

import javax.swing.*;
import java.awt.*;
import java.util.Properties;

class GridWidget extends JComponent {

    private final ShapeConfig shapeConfig;

    GridWidget(ShapeConfig shapeConfig, WidgetRegistry widgetRegistry, Properties prp) {
        this.shapeConfig = shapeConfig;
        setLayout(new GridLayout(shapeConfig.columns(), shapeConfig.rows()));
        for (int rr = 0; rr < shapeConfig.rows() ; ++rr) {
            for (int cc = 0; cc < shapeConfig.columns() ; ++cc) {
                add(widgetRegistry.instantiate(shapeConfig.type(), prp));
            }
        }
        setOpaque(false);
    }

    @Override
    public Dimension getPreferredSize() {
        Dimension shapeDim = shapeConfig.dimension();
        return new Dimension(shapeDim.width * shapeConfig.columns(), shapeDim.height * shapeConfig.rows());
    }

    static class Factory implements WidgetFactory {

        private WidgetRegistry widgetRegistry;

        Factory(WidgetRegistry widgetRegistry) {

            this.widgetRegistry = widgetRegistry;
        }

        @Override
        public JComponent instantiate(Properties prp) {
            final ShapeConfig shapeConfig = new ShapeConfig(prp);
            return new GridWidget(shapeConfig, widgetRegistry, prp);
        }
    }

    final static String NAME = "grid";
}
