package app.base;

import api.widget.WidgetFactory;
import api.widget.WidgetRegistry;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class SimpleWidgetRegistry implements WidgetRegistry {

    public void registerWidgetFactory(String type, WidgetFactory factory) {
        assert type != null;
        assert factory != null;
        assert builders.get(type) == null;

        builders.put(type, factory);
    }

    public void unregisterWidgetFactory(WidgetFactory factory) {
        assert factory != null;

        final Object removed = builders.remove(factory);

        assert removed != null;
    }

    public JComponent instantiate(String type) {
        assert type != null;

        WidgetFactory factory = builders.get(type);

        assert factory != null;

        return factory.instantiate();
    }

    private final Map<String, WidgetFactory> builders = new HashMap<String, WidgetFactory>();
}
