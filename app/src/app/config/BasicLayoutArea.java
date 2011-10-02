package app.config;

import api.widget.WidgetRegistry;

import javax.swing.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Properties;

public class BasicLayoutArea implements LayoutArea {

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
        panel.add(registry.instantiate(widgetName, propertiesFromString(param)));
    }

    private Properties propertiesFromString(String param) {
        assert param != null;

        final Properties props = new Properties();
        // Replace commas with linefeeds
        final String config = param.replace(',', '\n');
        try {
            props.load(new ByteArrayInputStream(config.getBytes()));
        } catch (IOException e) {
            // This should never happen!
            assert false;
        }
        return props;
    }

    private final JPanel panel;
    private final WidgetRegistry registry;
}
