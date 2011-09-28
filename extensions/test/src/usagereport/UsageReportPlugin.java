package usagereport;


import api.plugin.Plugin;
import api.plugin.ServiceRegistry;

public class UsageReportPlugin implements Plugin {

    public void load(final ServiceRegistry registry) {

        assert unpublisher == null;

        final HttpUsageReporter reporter = new HttpUsageReporter(registry);
        registry.publishService(reporter);

        unpublisher = new Runnable() {
            public void run() {
                int count = registry.unpublishService(reporter);
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
