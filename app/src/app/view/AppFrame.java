package app.view;

import api.services.Logger;
import api.widget.WidgetRegistry;
import app.base.SimpleScriptServices;
import app.config.ScriptedConfig;
import common.plugin.PluginManager;
import common.plugin.PluginManagerNotification;
import common.plugin.ServiceRegistry;
import common.widget.SimpleWidgetRegistry;

import javax.script.ScriptException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.URL;

public class AppFrame extends JFrame {

    public AppFrame() throws IOException, ScriptException {

        JLayeredPane layers = setupLayers();
        setContentPane(layers);

        addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent we) {
                shutdown();
            }
        });

        SimpleScriptServices scriptServices = new SimpleScriptServices(pluginManager, widgetRegistry);

        JPanel topPanel = buildTopPanel();
        layers.add(topPanel, JLayeredPane.PALETTE_LAYER);
        scriptServices.addLayout("top", topPanel);

        ScriptedConfig.load("config.js", scriptServices);

        setVisible(true);
    }

    private JPanel buildTopPanel() {
        final JPanel panel = new JPanel(new FlowLayout());
        panel.setSize(new Dimension(1024,200));
        panel.setOpaque(false);
        return panel;
    }

    private JLayeredPane setupLayers() {
        final JLayeredPane layers = new JLayeredPane();

        final ImagePanel imagePanel = new ImagePanel(loadImage("background.png"));
        imagePanel.setSize(new Dimension(1024, 768));

        layers.add(imagePanel, JLayeredPane.DEFAULT_LAYER);

        setSize(imagePanel.getSize());

        return layers;
    }

    private Image loadImage(String imageName) {
        final ClassLoader ld = ClassLoader.getSystemClassLoader();
        final URL url = ld.getResource(imageName);
        final Toolkit tk = Toolkit.getDefaultToolkit();
        return tk.getImage(url);
    }

    private void shutdown() {
        log("Plugins unloading...");
        pluginManager.dispose();
        log("Plugins unloaded");
        registry.dispose();
    }

    private void log(String message) {
        final Logger logger = (Logger) registry.lookupService(Logger.class);
        logger.logInfo(message);
    }

    private ServiceRegistry buildServiceRegistry() {
        final ServiceRegistry registry = new ServiceRegistry();
        registry.publishService(new Logger() {
            public void logError(String message) {
                logInfo(message);
            }

            public void logInfo(String message) {
                System.out.println(message);
            }

            public void logDebug(String message) {
                logInfo(message);
            }
        });
        registry.publishService(widgetRegistry);
        return registry;
    }

    private final WidgetRegistry widgetRegistry = new SimpleWidgetRegistry();
    private final ServiceRegistry registry = buildServiceRegistry();

    private final PluginManager pluginManager = new PluginManager(registry, new PluginManagerNotification() {
        public void onLoadFailure(String classname, Exception e) {
            log("Failed to load " + classname);
        }

        public void onLoaded(String className) {
            log("Loaded " + className);
        }
    });

}
