package usagereport;


import api.plugin.Plugin;
import api.plugin.ServiceRegistry;
import api.util.Command;
import api.services.UsageReport;

public class UsageReportPlugin implements Plugin {

    public void load(final ServiceRegistry registry) {

        assert unpublishCommand == null;

        final HttpUsageReporter reporter = new HttpUsageReporter(registry);
        registry.publishService(UsageReport.class, reporter);

        unpublishCommand = new Command() {
            public void process() {
                int count = registry.unpublishService(reporter);
                assert count == 1;
            }
        };
    }

    public void unload() {
        unpublishCommand.process();
        unpublishCommand = null;
    }

    private Command unpublishCommand = null;

}
