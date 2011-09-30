package app.config;

/**
 * The interface exposed to javascript to add widgets to a layout
 */
public interface LayoutArea {
    /**
     * Add a widget by name
     * @param widgetName The name of the widget
     */
    void add(String widgetName);
}
