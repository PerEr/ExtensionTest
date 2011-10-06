package widgetloader.view;

import api.plugin.ServiceRegistry;
import api.services.Logger;
import api.widget.WidgetRegistry;
import common.plugin.BasicServiceRegistry;
import common.plugin.PluginManager;
import common.plugin.PluginManagerNotification;
import common.plugin.ServiceRegistryNotification;
import common.util.PropertyBuilder;
import common.widget.SimpleWidgetRegistry;
import common.widget.WidgetRegistryNotification;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Properties;

public class TestFrame extends JFrame {

    public TestFrame() throws IOException {

        addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent we) {
                shutdown();
            }
        });

        final Container container = getContentPane();
        container.setLayout(new BorderLayout());

        statusLine.setPreferredSize(new Dimension(400, 20));
        container.add(statusLine, BorderLayout.PAGE_START);

        serviceList.setPreferredSize(new Dimension(400, 400));
        container.add(serviceList, BorderLayout.CENTER);

        widgetList.setPreferredSize(new Dimension(400, 400));
        container.add(widgetList, BorderLayout.LINE_END);

        container.add(buildControlPanel(), BorderLayout.PAGE_END);

        final Timer timer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (inputField.hasFocus() == false) {
                    inputField.requestFocus();
                }
            }
        });
        timer.start();

        pack();
        setVisible(true);
    }

    private JPanel buildControlPanel() {

        final JPanel panel = new JPanel(new FlowLayout());

        inputField.setPreferredSize(new Dimension(400, 40));
        panel.add(inputField);
        inputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                pluginManager.load(new String[]{inputField.getText().trim()});
            }
        });

        final JButton loadButton = new JButton("Load plugin");
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                pluginManager.load(new String[]{inputField.getText().trim()});
            }
        });
        panel.add(loadButton);

        final JButton instantiateButton = new JButton("Instantiate");
        instantiateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                onWidgetSelected(inputField.getText().trim());
            }
        });
        panel.add(instantiateButton);

        return panel;
    }

    private void onWidgetSelected(String text) {
        String widgetName = text;
        String parameters = "";

        int ix = text.indexOf(",");
        if (ix > 0) {
            widgetName = text.substring(0,ix);
            parameters = text.substring(ix+1);
        }
        JComponent widget = widgetRegistry.instantiate(widgetName, PropertyBuilder.fromString(parameters));
        widget.setVisible(true);
        JFrame widgetFrame = new JFrame();
        widgetFrame.getContentPane().add(widget, BorderLayout.CENTER);
        widgetFrame.pack();
        widgetFrame.setVisible(true);
    }

    private void shutdown() {
    }

    private void log(String message) {
        final Logger logger = (Logger) serviceRegistry.lookupService(Logger.class);
        logger.logInfo(message);
        statusLine.setText(message);
    }

    private ServiceRegistry buildServiceRegistry() {
        final BasicServiceRegistry registry = new BasicServiceRegistry();
        registry.addListener(new ServiceRegistryNotification() {
            @Override
            public void onServicePublished(Class serviceType, Object service) {
                serviceModel.addElement(serviceType.getCanonicalName());
            }

            @Override
            public void onServiceUnpublished(Object service) {
            }

            @Override
            public void onRegistryDispose() {
            }
        });
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
        registry.publishService(JFrame.class, this);
        return registry;
    }

    private WidgetRegistry buildWidgetRegistry() {
        SimpleWidgetRegistry registry = new SimpleWidgetRegistry();
        registry.addListener(new WidgetRegistryNotification() {
            @Override
            public void onWidgetPublished(String type) {
                widgetModel.addElement(type);
            }

            @Override
            public void onWidgetUnpublished(String type) {
                widgetModel.removeElement(type);
            }

            @Override
            public void onRegistryDispose() {
            }
        });
        return registry;
    }

    private JList buildJList(ListModel model, String title) {
        JList list = new JList(model);
        TitledBorder border = BorderFactory.createTitledBorder(title);
        list.setBorder(border);
        return list;
    }

    // Widgets
    private final DefaultListModel widgetModel = new DefaultListModel();
    private final JList widgetList = buildJList(widgetModel, "Widgets");

    private final WidgetRegistry widgetRegistry = buildWidgetRegistry();

    // Services
    private final DefaultListModel serviceModel = new DefaultListModel();
    private final JList serviceList = buildJList(serviceModel, "Services");
    private final ServiceRegistry serviceRegistry = buildServiceRegistry();

    private final PluginManager pluginManager = new PluginManager(serviceRegistry, new PluginManagerNotification() {
        public void onLoadFailure(String classname, Exception e) {
            log("Failed to load " + classname);
            inputField.requestFocus();
        }

        public void onLoaded(String className) {
            log("Loaded " + className);
            inputField.setText("");
            inputField.requestFocus();
        }
    });

    private final JTextField inputField = new JTextField("");

    private JLabel statusLine = new JLabel();
}
