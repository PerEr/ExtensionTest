package common.widget;

import api.widget.WidgetFactory;
import api.widget.WidgetRegistry;

import javax.swing.*;
import java.util.*;

public class SimpleWidgetRegistry implements WidgetRegistry {

    public void registerWidgetFactory(String type, WidgetFactory factory) {
        assert type != null;
        assert factory != null;
        assert builders.get(type) == null;

        builders.put(type, factory);
    }

    public void unregisterWidgetFactory(WidgetFactory factory) {
        assert factory != null;

        int sz = builders.size();

        List<String> keys = new LinkedList<String>();

        for (Map.Entry<String, WidgetFactory> entry : builders.entrySet()) {
            if (factory == entry.getValue()) {
                keys.add(entry.getKey());
            }
        }

        for (String key : keys) {
            builders.remove(key);
        }

        assert builders.size() < sz;
    }

    public JComponent instantiate(String type, Properties prp) {
        assert type != null;

        WidgetFactory factory = builders.get(type);

        assert factory != null;

        return factory.instantiate(prp);
    }

    private final Map<String, WidgetFactory> builders = new HashMap<String, WidgetFactory>();
}
