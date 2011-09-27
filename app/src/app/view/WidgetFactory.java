package app.view;

import api.widget.WidgetBuilder;
import api.widget.WidgetLookup;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class WidgetFactory implements WidgetLookup {

    public void registerWidgetBuilder(String type, WidgetBuilder builder) {
        assert type != null;
        assert builder != null;
        assert builders.get(type) == null;

        builders.put(type, builder);
    }

    public void unregisterWidgetBuilder(WidgetBuilder builder) {
        assert builder != null;

        Object removed = builders.remove(builder);

        assert removed != null;
    }

    public JPanel instantiate(String type) {
        assert type != null;

        WidgetBuilder builder = builders.get(type);

        assert builder != null;

        return builder.build();
    }

    private Map<String, WidgetBuilder> builders = new HashMap<String, WidgetBuilder>();
}
