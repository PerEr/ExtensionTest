package usagereport;

import api.plugin.ServiceRegistry;
import api.services.Logger;
import api.services.UsageReport;

public class HttpUsageReporter implements UsageReport {

    private ServiceRegistry registry;

    public HttpUsageReporter(ServiceRegistry registry) {
        this.registry = registry;
    }

    public void reportLogin(String user) {
        Logger logger = (Logger) registry.lookupService(Logger.class);
        logger.logInfo("Reporting login for user " + user);
    }

    public void reportLogout(String user) {
        Logger logger = (Logger) registry.lookupService(Logger.class);
        logger.logInfo("Reporting logout for user " + user);
    }

    public void reportGameStarted(String user, String game) {
        Logger logger = (Logger) registry.lookupService(Logger.class);
        logger.logInfo("Reporting game start for user " + user);
    }
}
