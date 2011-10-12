package com.example.prototype.connection.monitor;

import com.example.prototype.api.plugin.Plugin;
import com.example.prototype.api.plugin.ServiceRegistry;

public class ConnectionMonitorPlugin implements Plugin {

    @Override
    public void load(final ServiceRegistry registry) {

        final ConnectionMonitorImpl monitor = new ConnectionMonitorImpl();

        registry.publishService(monitor);

        unpublish = new Runnable() {
            @Override
            public void run() {
                registry.unpublishService(monitor);
                monitor.dispose();
            }
        };

    }

    @Override
    public void resolve(ServiceRegistry registry) {
    }

    @Override
    public void unload() {
        unpublish.run();
    }

    private Runnable unpublish;
}
