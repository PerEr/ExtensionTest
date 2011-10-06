package api.widget;

/**
 * The interface exposed to javascript to add widgets to a layout
 */
public interface WidgetArea {
    /**
     * Add a widget by name
     *
     * @param widgetConf The name of the widget and optional paramdters
     */
    void add(String widgetConf);

    /**
     * Add a widget by name with parameters
     *
     * @param widgetName The widget name
     * @param param      A comma separated string with parameters, ex: "a=1,b=2"
     */
    void add(String widgetName, String param);

}
