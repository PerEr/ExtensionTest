package app.base;

import api.widget.WidgetRegistry;
import app.config.LayoutArea;

import javax.swing.*;

public class SimleLayoutArea implements LayoutArea {

    public SimleLayoutArea(JPanel panel, WidgetRegistry registry) {
        this.panel = panel;
        this.registry = registry;
    }

    @Override
    public void add(String widgetName) {
        panel.add(registry.instantiate(widgetName));
    }

    private final JPanel panel;
    private WidgetRegistry registry;
}
