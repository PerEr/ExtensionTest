package com.example.prototype.connection.widget;

import com.example.prototype.api.plugin.Plugin;
import com.example.prototype.api.plugin.ServiceRegistry;
import com.example.prototype.api.widget.WidgetFactory;
import com.example.prototype.api.widget.WidgetRegistry;
import com.example.prototype.connection.api.ConnectionMonitor;

public class ConnectionWidgetPlugin implements Plugin {
    @Override
    public void load(final ServiceRegistry registry) {
        assert unpublish == null;
    }

    @Override
    public void resolve(final ServiceRegistry registry) {

        assert unpublish == null;

        final ConnectionMonitor connectionMonitor = (ConnectionMonitor) registry.lookupService(ConnectionMonitor.class);
        final WidgetRegistry widgetRegistry = (WidgetRegistry) registry.lookupService(WidgetRegistry.class);

        final WidgetFactory factory = new ConnectionWidget.Factory(connectionMonitor);
        widgetRegistry.registerWidgetFactory(ConnectionWidget.NAME, factory);

        unpublish = new Runnable() {
            @Override
            public void run() {
                registry.unpublishService(factory);
            }
        };

    }

    @Override
    public void unload() {
        unpublish.run();
        unpublish = null;
    }

    private Runnable unpublish;
}
