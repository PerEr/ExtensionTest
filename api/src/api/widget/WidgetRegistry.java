package api.widget;

import javax.swing.*;
import java.util.Properties;

/**
 * A registry for widget factories.
 *
 * @author Per Ersson
 */
public interface WidgetRegistry {
    /**
     * Register a factory that can create widgets from a type.
     * @param type The type (or name) of the widget to creata
     * @param factory The factory class that creates widget of the named type
     */
    void registerWidgetFactory(String type, WidgetFactory factory);

    /**
     * Un-register a widget factory
     * @param factory The widget factory
     */
    void unregisterWidgetFactory(WidgetFactory factory);

    /**
     * Instantiate a widget for a given type
     * @param type The type of widget
     * @param prp Properties to configure the widget
     * @return The widget
     */
    JComponent instantiate(String type, Properties prp);
}
