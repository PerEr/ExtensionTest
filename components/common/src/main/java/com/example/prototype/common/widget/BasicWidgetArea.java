package com.example.prototype.common.widget;

import com.example.prototype.api.plugin.ServiceRegistry;
import com.example.prototype.api.services.Logger;
import com.example.prototype.api.widget.WidgetArea;
import com.example.prototype.api.widget.WidgetRegistry;
import com.example.prototype.common.util.PropertyBuilder;

import javax.swing.*;

public class BasicWidgetArea implements WidgetArea {

    public BasicWidgetArea(JPanel panel, ServiceRegistry serviceRegistry, WidgetRegistry registry) {
        this.panel = panel;
        this.serviceRegistry = serviceRegistry;
        this.registry = registry;
    }

    @Override
    public void add(String text) {
        String widgetName = text;
        String parameters = "";

        int ix = text.indexOf(",");
        if (ix > 0) {
            widgetName = text.substring(0, ix);
            parameters = text.substring(ix + 1);
        }
        add(widgetName, parameters);
    }

    @Override
    public void add(String widgetName, String param) {
        Logger logger = (Logger) serviceRegistry.lookupService(Logger.class);
        logger.logDebug("Adding: " + widgetName + "," + param);

        JComponent widget = registry.instantiate(widgetName, PropertyBuilder.fromString(param));
        widget.setVisible(true);
        panel.add(widget);
        panel.revalidate();
        panel.repaint();
    }

    private final JPanel panel;
    private ServiceRegistry serviceRegistry;
    private final WidgetRegistry registry;
}
