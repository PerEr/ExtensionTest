package usagereport;


import api.plugin.Plugin;
import api.plugin.ServiceRegistry;
import api.services.UsageReport;

public class UsageReportPlugin implements Plugin {

    public void load(final ServiceRegistry registry) {

        assert unpublisher == null;

        final HttpUsageReporter reporter = new HttpUsageReporter(registry);
        registry.publishService(UsageReport.class, reporter);

        unpublisher = new Runnable() {
            public void run() {
                int count = registry.unpublishService(reporter);
                assert count == 1;
            }
        };
    }

    public void unload() {
        unpublisher.run();
        unpublisher = null;
    }

    private Runnable unpublisher = null;

}
