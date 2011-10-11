package common.widget;

import com.example.prototype.api.widget.WidgetArea;
import com.example.prototype.api.widget.WidgetRegistry;
import common.util.PropertyBuilder;

import javax.swing.*;

public class BasicWidgetArea implements WidgetArea {

    private final PropertyBuilder propertyBuilder = new PropertyBuilder();

    public BasicWidgetArea(JPanel panel, WidgetRegistry registry) {
        this.panel = panel;
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
        JComponent widget = registry.instantiate(widgetName, PropertyBuilder.fromString(param));
        widget.setVisible(true);
        panel.add(widget);
        panel.revalidate();
        panel.repaint();
    }

    private final JPanel panel;
    private final WidgetRegistry registry;
}
