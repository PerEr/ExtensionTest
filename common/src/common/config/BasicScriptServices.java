package common.config;

import com.example.prototype.api.widget.WidgetArea;
import com.example.prototype.api.widget.WidgetAreaRegistry;
import common.plugin.PluginManager;

import java.util.LinkedList;
import java.util.List;

public class BasicScriptServices implements ScriptServices {

    public BasicScriptServices(PluginManager pluginManager, WidgetAreaRegistry widgetAreaRegistry) {
        this.pluginManager = pluginManager;
        this.widgetAreaRegistry = widgetAreaRegistry;
    }

    @Override
    public void register(String pluginName) {
        pluginNames.add(pluginName);
    }

    @Override
    public void loadAll() {
        pluginManager.load(pluginNames.toArray(new String[pluginNames.size()]));
    }

    @Override
    public WidgetArea getLayout(String name) {
        return widgetAreaRegistry.lookup(name);
    }


    private final PluginManager pluginManager;
    private WidgetAreaRegistry widgetAreaRegistry;

    private final List<String> pluginNames = new LinkedList<String>();
}
