package applet;

import api.plugin.Plugin;
import api.plugin.ServiceRegistry;
import api.widget.WidgetFactory;
import api.widget.WidgetRegistry;

public class AppletHostPlugin implements Plugin {

    @Override
    public void load(final ServiceRegistry registry) {
        assert unpublish == null;
    }

    @Override
    public void resolve(final ServiceRegistry registry) {

        assert unpublish == null;

        final WidgetRegistry widgetRegistry = (WidgetRegistry) registry.lookupService(WidgetRegistry.class);

        final WidgetFactory factory = new AppletHostFactory();

        widgetRegistry.registerWidgetFactory("applet", factory);

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

