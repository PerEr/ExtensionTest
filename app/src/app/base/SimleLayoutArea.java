package app.base;

import api.widget.WidgetRegistry;
import app.config.LayoutArea;

import javax.swing.*;
import java.io.*;
import java.util.Properties;

public class SimleLayoutArea implements LayoutArea {

    public SimleLayoutArea(JPanel panel, WidgetRegistry registry) {
        this.panel = panel;
        this.registry = registry;
    }

    @Override
    public void add(String widgetName)
    {
        add(widgetName, "");
    }

    @Override
    public void add(String widgetName, String param) {
        Properties prp = propertiesFromString(param);
        panel.add(registry.instantiate(widgetName, prp));
    }

    private Properties propertiesFromString(String param)
    {
        String[] lines = param.split(",");
        StringBuffer sb = new StringBuffer();
        for (String line : lines) {
            sb.append(line).append("\n");
        }
        Properties props = new Properties();
        try {
            InputStream is = new ByteArrayInputStream(sb.toString().getBytes());
            props.load(is);
        } catch (IOException e) {
            // This should never happen!
            assert false;
        }
        return props;
    }

    private final JPanel panel;
    private WidgetRegistry registry;
}
