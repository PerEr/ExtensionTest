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

        final Container container = getContentPane();
        container.setLayout(new BorderLayout());

        addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent we) {
                shutdown();
            }
        });

        SimpleScriptServices scriptServices = new SimpleScriptServices(pluginManager, widgetRegistry);

        final JPanel topPanel = buildHorizontalWidgetPanel();
        container.add(topPanel, BorderLayout.PAGE_START);

        final JPanel rightPanel = buildVerticalWidgetPanel();
        container.add(rightPanel, BorderLayout.LINE_END);

        final JPanel gamePanel = buildGamePanel();
        container.add(gamePanel, BorderLayout.CENTER);

        final JPanel bottomPanel = buildHorizontalWidgetPanel();
        container.add(bottomPanel, BorderLayout.PAGE_END);

        scriptServices.addLayout("top", topPanel);
        scriptServices.addLayout("right", rightPanel);
        scriptServices.addLayout("bottom", bottomPanel);

        ScriptedConfig.load("config.js", scriptServices);

        setSize(new Dimension(1024, 920));
        setVisible(true);
    }

    private JPanel buildHorizontalWidgetPanel() {
        final JPanel panel = new JPanel(new FlowLayout());
        panel.setBackground(Color.BLACK);
        return panel;
    }

    private JPanel buildVerticalWidgetPanel() {
        final JPanel panel = new JPanel(new GridLayout(5,1));
        panel.setBackground(Color.BLACK);
        return panel;
    }

    private JPanel buildGamePanel() {
        return new ImagePanel(loadImage("background.png"));
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
