package app.base;

import api.plugin.Plugin;

import java.util.LinkedList;
import java.util.List;

public class PluginManager {

    public PluginManager(ServiceRegistry registry, PluginManagerNotification notification) {
        this.registry = registry;
        this.notification = notification;
    }

    public void load(String[] classNames) {
        // Publish services
        for (String className : classNames) {
            try {
                Class cl = Class.forName(className);
                Plugin plugin = (Plugin) cl.newInstance();
                plugin.load(registry);
                plugins.add(plugin);
                notification.onLoaded(className);
            } catch (Exception e) {
                notification.onLoadFailure(className, e);
            }
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
