package app.view;

import api.services.Logger;
import app.base.PluginManager;
import app.base.PluginManagerNotification;
import app.base.ServiceRegistry;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;

public class AppFrame extends JFrame {

    public AppFrame() {

        layers = setupLayers();

        addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent we) {
                shutdown();
            }
        });

        log("Loading plugins...");
        loadPlugins();
        log("Loaded plugins...");

        JPanel contentLayer = new JPanel(new GridLayout(10,10));
        contentLayer.setSize(new Dimension(1024,768));
        contentLayer.setOpaque(false);
        contentLayer.add(widgetFactory.instantiate("one"));
        contentLayer.add(widgetFactory.instantiate("one"));

        layers.add(contentLayer, JLayeredPane.PALETTE_LAYER);

        setContentPane(layers);

        setVisible(true);

        timer.start();

    }

    private JLayeredPane setupLayers() {
        JLayeredPane layers = new JLayeredPane();

        ImagePanel imagePanel = new ImagePanel(loadImage("background.png"));
        imagePanel.setSize(new Dimension(1024, 768));

        layers.add(imagePanel, JLayeredPane.DEFAULT_LAYER);

        setSize(imagePanel.getSize());

        return layers;
    }

    private void loadPlugins() {
        pluginManager.load(new String[] {
                "log.LogPlugin",
                "usagereport.UsageReportPlugin"
        });
    }

    private Image loadImage(String imageName) {
        ClassLoader ld = ClassLoader.getSystemClassLoader();
        URL url = ld.getResource(imageName);
        Toolkit tk = Toolkit.getDefaultToolkit();
        return tk.getImage(url);
    }

    private void shutdown() {
        log("Plugins unloading...");
        pluginManager.dispose();
        log("Plugins unloaded");
        registry.dispose();
    }

    private void log(String message) {
        Logger logger = (Logger) registry.lookupService(Logger.class);
        logger.logInfo(message);
    }

    private ServiceRegistry buildServiceRegistry() {
        ServiceRegistry registry = new ServiceRegistry();
        registry.publishService(Logger.class, new Logger() {
            public void logError(String message) {
                logInfo(message);
            }

            public void logInfo(String message) {
                System.out.println("Ful-logger; " + message);
            }

            public void logDebug(String message) {
                logInfo(message);
            }
        });
        return registry;
    }

    private void onTimer() {
    }

    private ServiceRegistry registry = buildServiceRegistry();

    private Timer timer = new Timer(4000, new ActionListener() {
        public void actionPerformed(ActionEvent actionEvent) {
            onTimer();
        }
    });

    private PluginManager pluginManager = new PluginManager(registry, new PluginManagerNotification() {
        public void onLoadFailure(String classname, Exception e) {
            log("Failed to load " + classname);
        }

        public void onLoaded(String className) {
            log("Loaded " + className);
        }
    });

    private WidgetFactory widgetFactory = new WidgetFactory();

    private JLayeredPane layers;
}
