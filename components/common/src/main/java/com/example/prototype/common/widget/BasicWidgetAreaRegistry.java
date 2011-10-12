package com.example.prototype.common.widget;

import com.example.prototype.api.widget.WidgetArea;
import com.example.prototype.api.widget.WidgetAreaRegistry;

import java.util.HashMap;
import java.util.Map;

public class BasicWidgetAreaRegistry implements WidgetAreaRegistry {

    @Override
    public WidgetArea lookup(String name) {
        return areas.get(name);
    }

    @Override
    public void publishWidgetArea(String name, WidgetArea widgetArea) {
        assert name != null;
        assert widgetArea != null;
        assert areas.get(name) == null;
        areas.put(name, widgetArea);

    }

    @Override
    public void unpublishWidgetArea(String name, WidgetArea widgetArea) {
        int sz = areas.size();
        areas.remove(widgetArea);
        assert sz + 1 == areas.size();
    }

    private final Map<String, WidgetArea> areas = new HashMap<String, WidgetArea>();
}
