package app.view;

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

        m_layers = setupLayers();
        add(m_layers);

        addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent we) {
                shutdown();
            }
        });

        loadPlugins();

        setVisible(true);

        m_timer.start();

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
        m_pluginManager.load(new String[] {"test.TestPlugin"});
    }

    private Image loadImage(String imageName) {
        ClassLoader ld = ClassLoader.getSystemClassLoader();
        URL url = ld.getResource(imageName);
        Toolkit tk = Toolkit.getDefaultToolkit();
        return tk.getImage(url);
    }

    private void shutdown() {
        m_pluginManager.dispose();
        m_registry.dispose();
    }

    private ServiceRegistry buildServiceRegistry() {
        ServiceRegistry registry = new ServiceRegistry();
        return registry;
    }

    private void onTimer() {
    }

    private ServiceRegistry m_registry = buildServiceRegistry();

    private Timer m_timer = new Timer(4000, new ActionListener() {
        public void actionPerformed(ActionEvent actionEvent) {
            onTimer();
        }
    });

    private PluginManager m_pluginManager = new PluginManager(m_registry, new PluginManagerNotification() {
        public void onLoadFailure(String classname, Exception e) {
            System.out.println("Failed to load " + classname);
        }

        public void onLoaded(String className) {
            System.out.println("Loaded " + className);
        }
    });

    private JLayeredPane m_layers;
}
