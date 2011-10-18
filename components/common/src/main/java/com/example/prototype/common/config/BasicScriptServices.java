package com.example.prototype.common.config;

import com.example.prototype.api.plugin.ServiceRegistry;
import com.example.prototype.api.services.Logger;
import com.example.prototype.api.widget.WidgetArea;
import com.example.prototype.api.widget.WidgetAreaRegistry;

public class BasicScriptServices implements ScriptServices {

    public BasicScriptServices(ServiceRegistry serviceRegistry, WidgetAreaRegistry widgetAreaRegistry) {
        this.serviceRegistry = serviceRegistry;
        this.widgetAreaRegistry = widgetAreaRegistry;
    }

    @Override
    public WidgetArea getLayout(String name) {
        Logger logger = (Logger) serviceRegistry.lookupService(Logger.class);
        logger.logDebug("Get layout \"" + name + "\"");
        return widgetAreaRegistry.lookup(name);
    }

    private ServiceRegistry serviceRegistry;
    private WidgetAreaRegistry widgetAreaRegistry;
}
