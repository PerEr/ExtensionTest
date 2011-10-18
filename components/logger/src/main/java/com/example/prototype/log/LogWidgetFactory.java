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
        return logWidget;
    }

    @Override
    public void logError(String message) {
        addWithColor(message, Color.RED);
    }

    @Override
    public void logInfo(String message) {
        addWithColor(message, Color.BLACK);
    }

    @Override
    public void logDebug(String message) {
        addWithColor(message, Color.GRAY);
    }

    private void addWithColor(String message, Color color) {
        JLabel label = new JLabel(message);
        label.setForeground(color);
        listModel.addElement(message);
    }

    private static DefaultListModel listModel = new DefaultListModel();
}
