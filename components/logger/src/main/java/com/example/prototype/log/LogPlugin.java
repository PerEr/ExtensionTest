package com.example.prototype.log;

import com.example.prototype.api.plugin.Plugin;
import com.example.prototype.api.plugin.ServiceRegistry;
import com.example.prototype.api.services.Logger;

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
