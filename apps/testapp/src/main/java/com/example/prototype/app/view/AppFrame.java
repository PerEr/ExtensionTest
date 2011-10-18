package com.example.prototype.app.view;

import com.example.prototype.api.services.Logger;
import com.example.prototype.api.widget.WidgetAreaRegistry;
import com.example.prototype.api.widget.WidgetRegistry;
import com.example.prototype.common.config.BasicScriptServices;
import com.example.prototype.common.config.ScriptedConfig;
import com.example.prototype.common.plugin.PluginManager;
import com.example.prototype.common.plugin.PluginManagerNotification;
import com.example.prototype.common.plugin.BasicServiceRegistry;
import com.example.prototype.common.widget.BasicWidgetArea;
import com.example.prototype.common.widget.BasicWidgetAreaRegistry;
import com.example.prototype.common.widget.BasicWidgetRegistry;

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

        final JPanel topPanel = buildHorizontalWidgetPanel();
        container.add(topPanel, BorderLayout.PAGE_START);

        final JPanel rightPanel = buildVerticalWidgetPanel();
        container.add(rightPanel, BorderLayout.LINE_END);

        final JPanel gamePanel = buildGamePanel();
        container.add(gamePanel, BorderLayout.CENTER);

        final JPanel bottomPanel = buildHorizontalWidgetPanel();
        container.add(bottomPanel, BorderLayout.PAGE_END);

        widgetAreaRegistry.publishWidgetArea("top", new BasicWidgetArea(topPanel, serviceRegistry, widgetRegistry));
        widgetAreaRegistry.publishWidgetArea("right", new BasicWidgetArea(rightPanel, serviceRegistry, widgetRegistry));
        widgetAreaRegistry.publishWidgetArea("bottom", new BasicWidgetArea(bottomPanel, serviceRegistry, widgetRegistry));

        // Load all plugins that can be auto-detected.
        pluginManager.autodetectPlugins();

        final BasicScriptServices scriptServices = new BasicScriptServices(serviceRegistry, widgetAreaRegistry);

        ScriptedConfig.load("/config.js", scriptServices);

        setSize(new Dimension(1024, 920));
        setVisible(true);
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
        final ClassLoader ld = ClassLoader.getSystemClassLoader();
        final URL url = ld.getResource(imageName);
        final Toolkit tk = Toolkit.getDefaultToolkit();
        return tk.getImage(url);
    }

    private void shutdown() {
        log("Plugins unloading...");
        pluginManager.dispose();
        log("Plugins unloaded");
        serviceRegistry.dispose();
    }

    private void log(String message) {
        final Logger logger = (Logger) serviceRegistry.lookupService(Logger.class);
        logger.logInfo(message);
    }

    private BasicServiceRegistry buildServiceRegistry() {
        final BasicServiceRegistry registry = new BasicServiceRegistry();
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
        registry.publishService(widgetAreaRegistry);
        registry.publishService(JFrame.class, this);
        return registry;
    }

    private final WidgetRegistry widgetRegistry = new BasicWidgetRegistry();
    private final WidgetAreaRegistry widgetAreaRegistry = new BasicWidgetAreaRegistry();
    private final BasicServiceRegistry serviceRegistry = buildServiceRegistry();

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
