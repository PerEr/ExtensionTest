package com.example.prototype.shapes;


import com.example.prototype.api.plugin.Plugin;
import com.example.prototype.api.plugin.ServiceRegistry;
import com.example.prototype.api.widget.WidgetFactory;
import com.example.prototype.api.widget.WidgetRegistry;

import java.util.LinkedList;
import java.util.List;

public class SimpleShapesPlugin implements Plugin {

    public void load(ServiceRegistry registry) {
    }

    @Override
    public void resolve(ServiceRegistry registry) {
        final WidgetRegistry widgetRegistry = (WidgetRegistry) registry.lookupService(WidgetRegistry.class);
        registerBuilder(widgetRegistry, CircleWidget.NAME, new CircleWidget.Factory());
        registerBuilder(widgetRegistry, SquareWidget.NAME, new SquareWidget.Factory());
        registerBuilder(widgetRegistry, GridWidget.NAME, new GridWidget.Factory(widgetRegistry));
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
