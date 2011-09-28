package connection.monitor;

import api.plugin.Plugin;
import api.plugin.ServiceRegistry;
import connection.api.ConnectionMonitor;

public class ConnectionMonitorPlugin implements Plugin {

    @Override
    public void load(final ServiceRegistry registry) {

        final ConnectionMonitor monitor = new ConnectionMonitorImpl();

        registry.publishService(monitor);

        unpublish = new Runnable() {
            @Override
            public void run() {
                registry.unpublishService(monitor);
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
