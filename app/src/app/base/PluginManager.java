package app.base;

import api.Plugin;

import java.util.LinkedList;
import java.util.List;

public class PluginManager {

    public PluginManager(ServiceRegistry registry) {
        m_registry = registry;
    }

    public boolean load(String className) {
        boolean ok = false;
        try {
            Class cl = Class.forName(className);
            Plugin plugin = (Plugin) cl.newInstance();
            plugin.load(m_registry);
            m_plugins.add(plugin);
            ok = true;
        } catch (Exception e) {
            System.out.println("Failed to load plugin " + className);
        }
        return ok;
    }

    public void dispose() {
        for (Plugin plugin : m_plugins) {
            plugin.unload();
        }

    }

    private ServiceRegistry m_registry;
    private List<Plugin> m_plugins = new LinkedList<Plugin>();
}
