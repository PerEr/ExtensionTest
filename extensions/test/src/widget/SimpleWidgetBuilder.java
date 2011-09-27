package widget;


import api.plugin.Plugin;
import api.plugin.ServiceLookup;
import api.util.Command;
import api.widget.WidgetBuilder;
import api.widget.WidgetLookup;

import javax.swing.*;

public class SimpleWidgetBuilder implements WidgetBuilder, Plugin {

    public JPanel build() {
        return new SimpleWidget();
    }

    public void load(ServiceLookup lookup) {
        final WidgetLookup widgetLookup = (WidgetLookup) lookup.lookupService(WidgetLookup.class);
        widgetLookup.registerWidgetBuilder("simple", this);
        final WidgetBuilder self = this;
        unpublish = new Command() {
            public void process() {
                widgetLookup.unregisterWidgetBuilder(self);
            }
        };
    }

    public void unload() {
        unpublish.process();
    }

    private Command unpublish;
}
