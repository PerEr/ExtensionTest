package common.config;

import api.widget.WidgetRegistry;
import common.util.PropertyBuilder;

import javax.swing.*;

public class BasicLayoutArea implements LayoutArea {

    private final PropertyBuilder propertyBuilder = new PropertyBuilder();

    public BasicLayoutArea(JPanel panel, WidgetRegistry registry) {
        this.panel = panel;
        this.registry = registry;
    }

    @Override
    public void add(String widgetName) {
        add(widgetName, "");
    }

    @Override
    public void add(String widgetName, String param) {
        panel.add(registry.instantiate(widgetName, PropertyBuilder.fromString(param)));
    }

    private final JPanel panel;
    private final WidgetRegistry registry;
}
