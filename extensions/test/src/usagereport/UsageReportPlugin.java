package usagereport;


import api.plugin.Plugin;
import api.plugin.ServiceLookup;
import api.util.Command;
import api.services.UsageReport;

public class UsageReportPlugin implements Plugin {

    public void load(final ServiceLookup lookup) {

        assert unpublishCommand == null;

        final HttpUsageReporter reporter = new HttpUsageReporter(lookup);
        lookup.publishService(UsageReport.class, reporter);

        unpublishCommand = new Command() {
            public void process() {
                int count = lookup.unpublishService(reporter);
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
