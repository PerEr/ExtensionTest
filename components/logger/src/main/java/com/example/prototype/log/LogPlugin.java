package com.example.prototype.log;

import com.example.prototype.api.plugin.Plugin;
import com.example.prototype.api.plugin.ServiceRegistry;
import com.example.prototype.api.widget.WidgetRegistry;

import java.util.LinkedList;
import java.util.List;

public class LogPlugin implements Plugin {

    public void load(final ServiceRegistry registry) {

        compositeLogger.addLogger(new BasicLogger());
        compositeLogger.addLogger(factory);
        registry.publishService(compositeLogger);

        unpublishers.add(new Runnable() {
            public void run() {
                compositeLogger.clearAll();
                int count = registry.unpublishService(compositeLogger);
                assert count == 1;
            }
        });
    }

    @Override
    public void resolve(ServiceRegistry registry) {
        final WidgetRegistry widgetRegistry = (WidgetRegistry) registry.lookupService(WidgetRegistry.class);
        widgetRegistry.registerWidgetFactory("log", factory);
        unpublishers.add(new Runnable() {
            @Override
            public void run() {
                widgetRegistry.unregisterWidgetFactory(factory);
            }
        });
    }

    public void unload() {
        for (Runnable unpublisher : unpublishers) {
            unpublisher.run();
        }
        unpublishers.clear();
    }

    private CompositeLogger compositeLogger = new CompositeLogger();
    private LogWidgetFactory factory = new LogWidgetFactory();

    private List<Runnable> unpublishers = new LinkedList<Runnable>();
}
