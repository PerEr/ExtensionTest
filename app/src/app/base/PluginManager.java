package app.base;

import api.Plugin;

import java.util.LinkedList;
import java.util.List;

public class PluginManager {

    public PluginManager(ServiceRegistry registry, PluginManagerNotification notification) {
        this.registry = registry;
        this.notification = notification;
    }

    public void load(String[] classNames) {
        List<Plugin> newPlugins = new LinkedList<Plugin>();

        // Publish services
        for (String className : classNames) {
            try {
                Class cl = Class.forName(className);
                Plugin plugin = (Plugin) cl.newInstance();
                plugin.publishServices(registry);
                newPlugins.add(plugin);
                notification.onLoaded(className);
            } catch (Exception e) {
                notification.onLoadFailure(className, e);
            }
        }

        // Resolve dependencies, this will allow plugins to use each other.
        for (Plugin plugin : newPlugins) {
            plugin.resolveDependencies(registry);
            plugins.add(plugin);
        }
    }

    public void dispose() {
        for (Plugin plugin : plugins) {
            plugin.unload();
        }

    }

    private ServiceRegistry registry;
    private PluginManagerNotification notification;
    private List<Plugin> plugins = new LinkedList<Plugin>();
}
