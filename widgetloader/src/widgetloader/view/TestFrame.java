package widgetloader.view;

import api.plugin.ServiceRegistry;
import api.services.Logger;
import api.widget.WidgetRegistry;
import common.plugin.BasicServiceRegistry;
import common.plugin.PluginManager;
import common.plugin.PluginManagerNotification;
import common.widget.SimpleWidgetRegistry;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Properties;

public class TestFrame extends JFrame {

    public TestFrame() throws IOException {

        final Container container = getContentPane();
        container.setLayout(new BorderLayout());

        addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent we) {
                shutdown();
            }
        });

        logPanel = new JList(logModel);
        logPanel.setPreferredSize(new Dimension(250, 300));

        container.add(logPanel, BorderLayout.LINE_START);

        Panel controlPanel = new Panel(new FlowLayout());

        inputField = new JTextField("");
        inputField.setPreferredSize(new Dimension(400, 40));
        controlPanel.add(inputField);
        inputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                pluginManager.load(new String[]{inputField.getText()});
            }
        });

        JButton loadButton = new JButton("Load plugin");
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                pluginManager.load(new String[]{inputField.getText()});
            }
        });
        controlPanel.add(loadButton);

        JButton instantiateButton = new JButton("Instantiate");
        instantiateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JComponent widget = widgetRegistry.instantiate(inputField.getText(), new Properties());
                widget.setVisible(true);
                container.add(widget, BorderLayout.CENTER);
                pack();
            }
        });
        controlPanel.add(instantiateButton);

        container.add(controlPanel, BorderLayout.PAGE_END);


        pack();
        setVisible(true);
    }

    private void shutdown() {
    }

    private void log(String message) {
        final Logger logger = (Logger) registry.lookupService(Logger.class);
        logger.logInfo(message);
        logModel.addElement(message);
    }

    private ServiceRegistry buildServiceRegistry() {
        final ServiceRegistry registry = new BasicServiceRegistry();
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
            inputField.setText("");
        }
    });

    private final JTextField inputField;
    private JList logPanel;

    private DefaultListModel logModel = new DefaultListModel();
}
