package widget;


import api.plugin.Plugin;
import api.plugin.ServiceLookup;
import api.util.Command;
import api.widget.WidgetBuilder;
import api.widget.WidgetLookup;

import javax.swing.*;
import java.util.LinkedList;
import java.util.List;

public class SimpleShapesPlugin implements Plugin {

    public void load(ServiceLookup lookup) {
        final WidgetLookup widgetLookup = (WidgetLookup) lookup.lookupService(WidgetLookup.class);
        registerBuilder(widgetLookup, "circle", new CircleWidget.Builder());
        registerBuilder(widgetLookup, "square", new SquareWidget.Builder());
    }

    private void registerBuilder(final WidgetLookup widgetLookup, String name, final WidgetBuilder builder) {
        widgetLookup.registerWidgetBuilder(name, builder);
        unpublishCommands.add(new Command() {
            public void process() {
                widgetLookup.unregisterWidgetBuilder(builder);
            }
        });
    }

    public void unload() {
        for (Command cmd : unpublishCommands) {
            cmd.process();
        }
    }

    private static class CircleBuilder implements WidgetBuilder {
        @Override
        public JPanel build() {
            return new CircleWidget();
        }
    }

    private List<Command> unpublishCommands = new LinkedList<Command>();
}
