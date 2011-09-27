package app.view;

import api.services.Logger;
import api.widget.WidgetRegistry;
import app.base.PluginManager;
import app.base.PluginManagerNotification;
import app.base.ServiceRegistry;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.URL;

public class AppFrame extends JFrame {

    public AppFrame() throws IOException {

        layers = setupLayers();

        addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent we) {
                shutdown();
            }
        });

        Config config = new Config();

        log("Loading plugins...");
        pluginManager.load(config.plugins());
        log("Loaded plugins...");

        final JPanel contentLayer = loadWidgets(config.widgets());

        layers.add(contentLayer, JLayeredPane.PALETTE_LAYER);

        setContentPane(layers);

        setVisible(true);

        timer.start();

    }

    private JPanel loadWidgets(String[] widgets) {
        final JPanel contentLayer = new JPanel(new GridLayout(1,4));
        contentLayer.setSize(new Dimension(1024,768));
        contentLayer.setOpaque(false);
        for (String widget : widgets) {
            contentLayer.add(widgetFactory.instantiate(widget));
        }
        return contentLayer;
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
        registry.publishService(WidgetRegistry.class, widgetFactory);
        return registry;
    }

    private void onTimer() {
    }

    private final JLayeredPane layers;

    private final WidgetRegistry widgetFactory = new SimpleWidgetRegistry();
    private final ServiceRegistry registry = buildServiceRegistry();

    private final PluginManager pluginManager = new PluginManager(registry, new PluginManagerNotification() {
        public void onLoadFailure(String classname, Exception e) {
            log("Failed to load " + classname);
        }

        public void onLoaded(String className) {
            log("Loaded " + className);
        }
    });

    private final Timer timer = new Timer(4000, new ActionListener() {
        public void actionPerformed(ActionEvent actionEvent) {
            onTimer();
        }
    });

}
