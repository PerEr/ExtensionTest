package app.view;

import app.base.EventManager;
import app.base.PluginManager;
import app.base.ServiceRegistry;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;

public class AppFrame extends Frame {

    private Image m_img = loadImage("background.png");
    private ServiceRegistry m_registry = buildServiceRegistry();
    private PluginManager m_pluginManager = new PluginManager(m_registry);

    public AppFrame() {

        m_pluginManager.load("test.TestPlugin");

        setSize(1024, 563);

        show();

        addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent we) {
                shutdown();
            }
        });
    }

    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);    //To change body of overridden methods use File | Settings | File Templates.
        Insets insets = getInsets();
        graphics.drawImage(m_img, insets.left, insets.top, this);
    }

    private void shutdown() {
        m_pluginManager.dispose();
        m_registry.dispose();
    }

    private ServiceRegistry buildServiceRegistry() {
        ServiceRegistry registry = new ServiceRegistry();
        registry.register(new EventManager());
        return registry;
    }

    private Image loadImage(String imageName) {
        ClassLoader ld = ClassLoader.getSystemClassLoader();
        URL url = ld.getResource(imageName);
        Toolkit tk = Toolkit.getDefaultToolkit();
        return tk.getImage(url);
    }

}
