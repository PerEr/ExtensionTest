package widget;


import api.plugin.Plugin;
import api.plugin.ServiceRegistry;
import api.widget.WidgetFactory;
import api.widget.WidgetRegistry;

import javax.swing.*;
import java.util.LinkedList;
import java.util.List;

public class SimpleShapesPlugin implements Plugin {

    public void load(ServiceRegistry registry) {
        final WidgetRegistry widgetRegistry = (WidgetRegistry) registry.lookupService(WidgetRegistry.class);
        registerBuilder(widgetRegistry, "circle", new CircleWidget.Factory());
        registerBuilder(widgetRegistry, "square", new SquareWidget.Factory());
    }

    private void registerBuilder(final WidgetRegistry widgetRegistry, String name, final WidgetFactory factory) {
        widgetRegistry.registerWidgetFactory(name, factory);
        unpublishers.add(new Runnable() {
            public void run() {
                widgetRegistry.unregisterWidgetFactory(factory);
            }
        });
    }

    public void unload() {
        for (Runnable cmd : unpublishers) {
            cmd.run();
        }
    }

    private static class CircleFactory implements WidgetFactory {
        @Override
        public JPanel instantiate() {
            return new CircleWidget();
        }
    }

    private List<Runnable> unpublishers = new LinkedList<Runnable>();
}
