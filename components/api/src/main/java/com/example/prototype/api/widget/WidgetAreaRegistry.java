package com.example.prototype.api.widget;

public interface WidgetAreaRegistry {

    WidgetArea lookup(String name);

    void publishWidgetArea(String name, WidgetArea widgetArea);
    void unpublishWidgetArea(WidgetArea widgetArea);

}
