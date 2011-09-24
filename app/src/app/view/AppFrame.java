package app.view;

import api.ConnectionEventListener;
import api.ConnectionEventSource;
import app.base.ConnectionEventManager;
import app.base.PluginManager;
import app.base.ServiceRegistry;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

public class AppFrame extends Frame {

    public AppFrame() {

        m_pluginManager.load("test.TestPlugin");

        setSize(1024, 768);

        show();

        addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent we) {
                shutdown();
            }
        });

        onTimer();
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
        registry.register(new ConnectionEventManager());
        return registry;
    }

    private Image loadImage(String imageName) {
        ClassLoader ld = ClassLoader.getSystemClassLoader();
        URL url = ld.getResource(imageName);
        Toolkit tk = Toolkit.getDefaultToolkit();
        return tk.getImage(url);
    }

    private void onTimer() {
        ConnectionEventListener lst = (ConnectionEventListener) m_registry.lookupService(ConnectionEventListener.class);
        lst.onConnectionEstablished();
        m_timer.schedule(new TimerTask() {
            public void run() {
                onTimer();
            }
        }, 4000);
    }

    private Image m_img = loadImage("background.png");
    private ServiceRegistry m_registry = buildServiceRegistry();
    private PluginManager m_pluginManager = new PluginManager(m_registry);
    private Timer m_timer = new Timer();
}
