package widget;


import api.plugin.Plugin;
import api.plugin.ServiceRegistry;
import api.util.Command;
import api.widget.WidgetFactory;
import api.widget.WidgetRegistry;

import javax.swing.*;
import java.util.LinkedList;
import java.util.List;

public class SimpleShapesPlugin implements Plugin {

    public void load(ServiceRegistry registry) {
        final WidgetRegistry widgetRegistry = (WidgetRegistry) registry.lookupService(WidgetRegistry.class);
        registerBuilder(widgetRegistry, "circle", new CircleWidget.Factory());
        registerBuilder(widgetRegistry, "square", new SquareWidget.Factory());
    }

    private void registerBuilder(final WidgetRegistry widgetRegistry, String name, final WidgetFactory factory) {
        widgetRegistry.registerWidgetBuilder(name, factory);
        unpublishCommands.add(new Command() {
            public void process() {
                widgetRegistry.unregisterWidgetBuilder(factory);
            }
        });
    }

    public void unload() {
        for (Command cmd : unpublishCommands) {
            cmd.process();
        }
    }

    private static class CircleFactory implements WidgetFactory {
        @Override
        public JPanel build() {
            return new CircleWidget();
        }
    }

    private List<Command> unpublishCommands = new LinkedList<Command>();
}
