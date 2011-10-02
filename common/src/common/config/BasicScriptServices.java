package common.config;

import api.widget.WidgetRegistry;
import common.plugin.PluginManager;

import javax.swing.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class BasicScriptServices implements ScriptServices {

    public BasicScriptServices(PluginManager pluginManager, WidgetRegistry registry) {
        this.pluginManager = pluginManager;
        this.registry = registry;
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
    public LayoutArea getLayout(String name) {
        return layouts.get(name);
    }


    public void addLayout(String name, JPanel panel) {
        assert layouts.get(name) == null;
        layouts.put(name, new BasicLayoutArea(panel, registry));
    }

    private final PluginManager pluginManager;
    private WidgetRegistry registry;

    private Map<String, LayoutArea> layouts = new HashMap<String, LayoutArea>();
    private final List<String> pluginNames = new LinkedList<String>();
}
