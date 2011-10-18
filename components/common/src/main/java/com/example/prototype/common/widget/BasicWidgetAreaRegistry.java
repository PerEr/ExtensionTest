package com.example.prototype.common.widget;

import com.example.prototype.api.plugin.ServiceRegistry;
import com.example.prototype.api.widget.WidgetArea;
import com.example.prototype.api.widget.WidgetAreaRegistry;

import java.util.HashMap;
import java.util.Iterator;
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
    public void unpublishWidgetArea(WidgetArea widgetArea) {
        int removed = 0;
        Iterator<Map.Entry<String, WidgetArea>> ii = areas.entrySet().iterator();
        while (ii.hasNext()) {
            Map.Entry<String, WidgetArea> entry = ii.next();
            if (entry.getValue() == widgetArea) {
                ii.remove();
                ++removed;
            }
        }
        assert removed > 0;
    }

    public void dispose() {
        areas.clear();
    }

    int areas() {
        return areas.size();
    }

    private final Map<String, WidgetArea> areas = new HashMap<String, WidgetArea>();
}
