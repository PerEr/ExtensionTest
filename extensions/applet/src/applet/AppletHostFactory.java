package applet;

import api.widget.WidgetFactory;

import javax.swing.*;
import java.applet.Applet;
import java.applet.AppletContext;
import java.applet.AppletStub;
import java.awt.*;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Properties;

public class AppletHostFactory implements WidgetFactory {
    @Override
    public JComponent instantiate(Properties prp) {
        final AppletConfig appletConfig = new AppletConfig(prp);
        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(appletConfig.dimension());

        try {
            URL urls[] = { new URL(appletConfig.url()) };
            ClassLoader classLoader = new URLClassLoader(urls, this.getClass().getClassLoader());
            Class<Applet> appletClass = (Class<Applet>) classLoader.loadClass(appletConfig.code());
            Applet applet = appletClass.newInstance();
            AppletContext appletContext = new BasicAppletContext();
            AppletStub appletStub = new BasicAppletStub(appletContext, prp);
            applet.setStub(appletStub);
            applet.init();
            applet.start();
            panel.add(applet);
        } catch (Exception ex) {
            JLabel label = new JLabel("Applet failed to load");
            panel.add(label);
        }

        panel.setPreferredSize(appletConfig.dimension());
        return panel;
    }
}
