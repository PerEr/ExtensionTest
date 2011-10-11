package com.example.prototype.api.widget;

import javax.swing.*;
import java.util.Properties;

/**
 * The interface to realize for all widget factories.
 * @see WidgetRegistry
 * @author Per Ersson
 */
public interface WidgetFactory {

    /**
     * Instantiate a widget and configure it using the property object
     * @param prp Properties for the widget
     * @return A ready-to-use widget
     */
    JComponent instantiate(Properties prp);
}
