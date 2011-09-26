package usagereport;


import api.Command;
import api.Plugin;
import api.ServiceLookup;
import api.service.UsageReport;

public class UsageReportPlugin implements Plugin {

    public void load(final ServiceLookup lookup) {

        assert unpublishCommand == null;

        final HttpUsageReporter reporter = new HttpUsageReporter(lookup);
        lookup.publishService(UsageReport.class, reporter);

        unpublishCommand = new Command() {
            public void process() {
                lookup.unpublishService(reporter);
            }
        };
    }

    public void unload() {
        unpublishCommand.process();
        unpublishCommand = null;
    }

    private Command unpublishCommand = null;

}
