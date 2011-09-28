package log;

import api.plugin.Plugin;
import api.plugin.ServiceRegistry;
import api.services.Logger;

public class LogPlugin implements Plugin {

    public void load(final ServiceRegistry registry) {

        assert unpublisher == null;

        final Logger logger = new BasicLogger();
        registry.publishService(logger);

        unpublisher = new Runnable() {
            public void run() {
                int count = registry.unpublishService(logger);
                assert count == 1;
            }
        };
    }

    @Override
    public void resolve(ServiceRegistry registry) {
    }

    public void unload() {
        unpublisher.run();
        unpublisher = null;
    }

    private Runnable unpublisher = null;
}
