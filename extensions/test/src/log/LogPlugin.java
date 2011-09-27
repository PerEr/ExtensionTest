package log;

import api.plugin.Plugin;
import api.plugin.ServiceLookup;
import api.util.Command;
import api.services.Logger;

public class LogPlugin implements Plugin {

    public void load(final ServiceLookup lookup) {

        assert unpublishCommand == null;

        final Logger logger = new BasicLogger();
        lookup.publishService(Logger.class, logger);

        unpublishCommand = new Command() {
            public void process() {
                int count = lookup.unpublishService(logger);
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