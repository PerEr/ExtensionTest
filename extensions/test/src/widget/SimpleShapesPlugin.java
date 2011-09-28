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
        registerBuilder(widgetRegistry, CircleWidget.NAME, new CircleWidget.Factory());
        registerBuilder(widgetRegistry, SquareWidget.NAME, new SquareWidget.Factory());
    }

    @Override
    public void resolve(ServiceRegistry registry) {
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

    private List<Runnable> unpublishers = new LinkedList<Runnable>();
}
