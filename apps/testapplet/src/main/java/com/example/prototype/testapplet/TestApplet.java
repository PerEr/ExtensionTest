package com.example.prototype.testapplet;

import com.example.prototype.api.services.Logger;
import com.example.prototype.common.config.BasicScriptServices;
import com.example.prototype.common.config.ScriptedConfig;
import com.example.prototype.common.plugin.BasicServiceRegistry;
import com.example.prototype.common.plugin.PluginManager;
import com.example.prototype.common.plugin.PluginManagerNotification;
import com.example.prototype.common.widget.BasicWidgetArea;
import com.example.prototype.common.widget.BasicWidgetAreaRegistry;
import com.example.prototype.common.widget.BasicWidgetRegistry;

import javax.swing.*;
import java.applet.Applet;
import java.awt.*;
import java.net.URL;

public class TestApplet extends Applet {

    public TestApplet() {
    }

    @Override
    public void init() {
        super.init();
        setLayout(new BorderLayout());

        add(topPanel, BorderLayout.PAGE_START);
        add(rightPanel, BorderLayout.LINE_END);
        add(gamePanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.PAGE_END);

        setSize(new Dimension(1024, 920));
        setVisible(true);
    }

    @Override
    public void start() {
        super.start();

        setupServiceRegistry();

        setupWidgetAreas();

        log("Start");

        // Load all plugins that can be auto-detected.
        pluginManager.autodetectPlugins();

        log("Plugins loaded");

        final BasicScriptServices scriptServices = new BasicScriptServices(serviceRegistry, widgetAreaRegistry);

        try {
            String scriptName = "/config.js";
            log("Running " + scriptName);
            ScriptedConfig.load(scriptName, scriptServices);
            log("Script ran fine");
        } catch (Exception e) {
            log("Script failed: " + e.getMessage());
        }

        log("Started");
    }

    @Override
    public void stop() {
        log("Unload plugins");
        pluginManager.dispose();

        log("Unload services");
        serviceRegistry.dispose();

        log("CLear widget area registry");
        widgetAreaRegistry.dispose();

        log("CLear widget registry");
        widgetRegistry.dispose();

        super.stop();
    }

    private JPanel buildHorizontalWidgetPanel() {
        final JPanel panel = new JPanel(new FlowLayout());
        panel.setBackground(Color.BLACK);
        return panel;
    }

    private JPanel buildVerticalWidgetPanel() {
        final JPanel panel = new JPanel(new GridLayout(5, 1));
        panel.setBackground(Color.BLACK);
        return panel;
    }

    private JPanel buildGamePanel() {
        return new ImagePanel(loadImage("background.png"));
    }

    private Image loadImage(String imageName) {
        final ClassLoader ld = this.getClass().getClassLoader();
        final URL url = ld.getResource(imageName);
        final Toolkit tk = Toolkit.getDefaultToolkit();
        return tk.getImage(url);
    }

    private void log(String message) {
        final Logger logger = (Logger) serviceRegistry.lookupService(Logger.class);
        if (logger != null)
            logger.logInfo(message);
    }

    private void setupServiceRegistry() {
        serviceRegistry.publishService(new Logger() {
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
        serviceRegistry.publishService(widgetRegistry);
        serviceRegistry.publishService(widgetAreaRegistry);
    }

    private void setupWidgetAreas() {
        widgetAreaRegistry.publishWidgetArea("top", new BasicWidgetArea(topPanel, serviceRegistry, widgetRegistry));
        widgetAreaRegistry.publishWidgetArea("right", new BasicWidgetArea(rightPanel, serviceRegistry, widgetRegistry));
        widgetAreaRegistry.publishWidgetArea("bottom", new BasicWidgetArea(bottomPanel, serviceRegistry, widgetRegistry));
    }

    // The registries
    private final BasicWidgetRegistry widgetRegistry = new BasicWidgetRegistry();
    private final BasicWidgetAreaRegistry widgetAreaRegistry = new BasicWidgetAreaRegistry();
    private final BasicServiceRegistry serviceRegistry = new BasicServiceRegistry();

    // The panels
    private JPanel topPanel = buildHorizontalWidgetPanel();
    private JPanel rightPanel = buildVerticalWidgetPanel();
    private JPanel gamePanel = buildGamePanel();
    private JPanel bottomPanel = buildHorizontalWidgetPanel();

    private final PluginManager pluginManager = new PluginManager(serviceRegistry, new PluginManagerNotification() {

        @Override
        public void onLoad(String className) {
            log("Loaded " + className);
        }

        @Override
        public void onLoadFailure(String classname, Exception e) {
            log("Failed to load " + classname);
        }

        @Override
        public void onResolve(String className) {
            log("resolved " + className);
        }

        @Override
        public void onResolveFailure(String classname, Exception e) {
            log("Failed to resolve " + classname);
        }

    });

}
