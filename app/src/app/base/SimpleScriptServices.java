package app.base;

import app.config.ScriptServices;
import common.plugin.PluginManager;

import java.util.LinkedList;
import java.util.List;

public class SimpleScriptServices implements ScriptServices {

    public SimpleScriptServices(PluginManager pluginManager) {
        this.pluginManager = pluginManager;
    }

    @Override
    public void register(String pluginName) {
        pluginNames.add(pluginName);
    }

    @Override
    public void loadAll() {
        pluginManager.load(pluginNames.toArray(new String[pluginNames.size()]));
    }


    private final PluginManager pluginManager;

    private final List<String> pluginNames = new LinkedList<String>();
}
