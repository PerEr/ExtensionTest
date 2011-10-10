package applet;

import api.plugin.Plugin;
import api.plugin.ServiceRegistry;
import api.widget.WidgetFactory;
import api.widget.WidgetRegistry;

import javax.swing.*;
import java.applet.Applet;
import java.awt.*;
import java.util.Properties;

public class AppletPlugin implements Plugin {

    @Override
    public void load(final ServiceRegistry registry) {
        assert unpublish == null;
    }

    @Override
    public void resolve(final ServiceRegistry registry) {

        assert unpublish == null;

        final WidgetRegistry widgetRegistry = (WidgetRegistry) registry.lookupService(WidgetRegistry.class);

        final WidgetFactory factory = new WidgetFactory() {
            @Override
            public JComponent instantiate(Properties prp) {
                JPanel panel = new JPanel(new BorderLayout());
                panel.setPreferredSize(new Dimension(500,500));
                Applet applet = new TestApplet();

                applet.init();
                applet.start();
                panel.add(applet, BorderLayout.CENTER);
                //panel.setVisible(true);
                return panel;
            }
        };

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

