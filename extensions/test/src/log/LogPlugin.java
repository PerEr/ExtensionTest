package log;

import api.Command;
import api.Plugin;
import api.ServiceLookup;
import api.service.Logger;

public class LogPlugin implements Plugin {

    public void load(final ServiceLookup lookup) {

        assert unpublishCommand == null;

        final Logger logger = new BasicLogger();
        lookup.publishService(Logger.class, logger);

        unpublishCommand = new Command() {
            public void process() {
                lookup.unpublishService(logger);
            }
        };
    }

    public void unload() {
        unpublishCommand.process();
        unpublishCommand = null;
    }

    private Command unpublishCommand = null;
}
