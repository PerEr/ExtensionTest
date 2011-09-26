package usagereport;

import api.plugin.ServiceLookup;
import api.services.Logger;
import api.services.UsageReport;

public class HttpUsageReporter implements UsageReport {

    private ServiceLookup lookup;

    public HttpUsageReporter(ServiceLookup lookup) {
        this.lookup = lookup;
    }

    public void reportLogin(String user) {
        Logger logger = (Logger) lookup.lookupService(Logger.class);
        logger.logInfo("Reporting login for user " + user);
    }

    public void reportLogout(String user) {
        Logger logger = (Logger) lookup.lookupService(Logger.class);
        logger.logInfo("Reporting logout for user " + user);
    }

    public void reportGameStarted(String user, String game) {
        Logger logger = (Logger) lookup.lookupService(Logger.class);
        logger.logInfo("Reporting game start for user " + user);
    }
}
