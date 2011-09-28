package clock;

import api.plugin.Plugin;
import api.plugin.ServiceRegistry;
import api.widget.WidgetFactory;
import api.widget.WidgetRegistry;

/**
 * Created by IntelliJ IDEA.
 * User: per
 * Date: 2011-09-28
 * Time: 20.44
 * To change this template use File | Settings | File Templates.
 */
public class ClockPlugin implements Plugin {
    @Override
    public void load(final ServiceRegistry registry) {
        assert unpublish == null;

        WidgetRegistry widgetRegistry = (WidgetRegistry) registry.lookupService(WidgetRegistry.class);

        final WidgetFactory factory = new ClockWidget.Factory();
        widgetRegistry.registerWidgetFactory(ClockWidget.NAME, factory);

        unpublish = new Runnable() {
            @Override
            public void run() {
                registry.unpublishService(factory);
            }
        };

    }

    @Override
    public void resolve(ServiceRegistry registry) {
    }

    @Override
    public void unload() {
        unpublish.run();
        unpublish = null;
    }

    private Runnable unpublish;
}
