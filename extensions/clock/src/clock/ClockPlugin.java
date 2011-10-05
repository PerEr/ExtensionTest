package clock;

import api.plugin.Plugin;
import api.plugin.ServiceRegistry;
import api.widget.WidgetFactory;
import api.widget.WidgetRegistry;

public class ClockPlugin implements Plugin {
    @Override
    public void load(final ServiceRegistry registry) {
    }

    @Override
    public void resolve(final ServiceRegistry registry) {
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
    public void unload() {
        unpublish.run();
        unpublish = null;
    }

    private Runnable unpublish;
}
