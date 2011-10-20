package com.example.prototype.log;

import com.example.prototype.api.services.Logger;
import com.example.prototype.api.widget.WidgetFactory;

import javax.swing.*;
import java.awt.*;
import java.util.Properties;

public class LogWidgetFactory implements WidgetFactory, Logger {

    @Override
    public JComponent instantiate(Properties prp) {
        LogWidget logWidget = new LogWidget(listModel);
        logWidget.setCellRenderer(coloredCellRenderer);
        return new JScrollPane(logWidget);
    }

    @Override
    public void logError(String message) {
        addWithColor(message, errorColor);
    }

    @Override
    public void logInfo(String message) {
        addWithColor(message, infoColor);
    }

    @Override
    public void logDebug(String message) {
        addWithColor(message, debugColor);
    }

    private void addWithColor(String message, Color color) {
        LogEntry logEntry = new LogEntry(message, color);
        listModel.addElement(logEntry);
    }

    private final Color errorColor = new Color(128,0, 0);
    private final Color infoColor = new Color(0,128,0);
    private final Color debugColor = Color.GRAY;


    private final ListCellRenderer coloredCellRenderer = new ColoredCellRenderer();
    private static DefaultListModel listModel = new DefaultListModel();
}
