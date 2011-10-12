package com.example.prototype.widgetloader.view;

import com.example.prototype.api.plugin.ServiceRegistry;
import com.example.prototype.api.services.Logger;
import com.example.prototype.api.widget.WidgetRegistry;
import com.example.prototype.common.plugin.BasicServiceRegistry;
import com.example.prototype.common.plugin.PluginManager;
import com.example.prototype.common.plugin.PluginManagerNotification;
import com.example.prototype.common.plugin.ServiceRegistryNotification;
import com.example.prototype.common.util.PropertyBuilder;
import com.example.prototype.common.widget.BasiceWidgetRegistry;
import com.example.prototype.common.widget.WidgetRegistryNotification;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

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

        {
            final JList serviceList = buildJList(serviceModel, "Discovered services");
            serviceList.setPreferredSize(new Dimension(400, 400));
            container.add(serviceList, BorderLayout.CENTER);
        }

        {
            final JList widgetList = buildJList(widgetModel, "Discovered widgets types");
            widgetList.setPreferredSize(new Dimension(400, 400));
            widgetList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            container.add(widgetList, BorderLayout.LINE_END);
            widgetList.addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent listSelectionEvent) {
                    log(listSelectionEvent.toString());
                    Object selectedValues[] = widgetList.getSelectedValues();
                    if (selectedValues.length > 0) {
                        String widgetName = (String) selectedValues[0];
                        log(widgetName);
                        inputField.setText(widgetName);
                        inputField.requestFocus();
                    }
                }
            });
        }

        final PluginManager pluginManager = new PluginManager(serviceRegistry, new PluginManagerNotification() {
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

        container.add(buildControlPanel(pluginManager), BorderLayout.PAGE_END);

        pack();
        setVisible(true);
    }

    private JPanel buildControlPanel(final PluginManager pluginManager) {

        final JPanel panel = new JPanel(new FlowLayout());

        final JButton detectPlugins = new JButton("Discover plugins");
        detectPlugins.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                pluginManager.autodetectPlugins();
            }
        });
        panel.add(detectPlugins);

        inputField.setPreferredSize(new Dimension(400, 40));
        panel.add(inputField);
        inputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                onWidgetSelected(inputField.getText().trim());
            }
        });

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
            widgetName = text.substring(0, ix);
            parameters = text.substring(ix + 1);
        }

        JComponent widget = widgetRegistry.instantiate(widgetName, PropertyBuilder.fromString(parameters));
        widget.setVisible(true);

        JFrame widgetFrame = new JFrame();
        widgetFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
        BasiceWidgetRegistry registry = new BasiceWidgetRegistry();
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

    private final WidgetRegistry widgetRegistry = buildWidgetRegistry();

    // Services
    private final DefaultListModel serviceModel = new DefaultListModel();
    private final ServiceRegistry serviceRegistry = buildServiceRegistry();

    private final JTextField inputField = new JTextField("");

    private JLabel statusLine = new JLabel();
}
