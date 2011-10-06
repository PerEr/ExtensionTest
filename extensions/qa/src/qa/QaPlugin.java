package qa;

import api.plugin.Plugin;
import api.plugin.ServiceRegistry;
import api.widget.WidgetFactory;
import api.widget.WidgetRegistry;

import java.util.LinkedList;
import java.util.List;

public class QaPlugin implements Plugin {

    public void load(ServiceRegistry registry) {
    }

    @Override
    public void resolve(ServiceRegistry serviceRegistry) {
        final WidgetRegistry widgetRegistry = (WidgetRegistry) serviceRegistry.lookupService(WidgetRegistry.class);
        registerBuilder(widgetRegistry, AddWidgetWidget.NAME, new AddWidgetWidget.Factory(serviceRegistry));
    }

    private void registerBuilder(final WidgetRegistry widgetRegistry, String name, final WidgetFactory factory) {
        widgetRegistry.registerWidgetFactory(name, factory);
        unpublishers.add(new Runnable() {
            public void run() {
                widgetRegistry.unregisterWidgetFactory(factory);
            }
        });
    }

    public void unload() {
        for (Runnable cmd : unpublishers) {
            cmd.run();
        }
    }

    private List<Runnable> unpublishers = new LinkedList<Runnable>();
}
