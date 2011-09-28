package common.plugin;

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
        for (final String className : classNames) {
            try {
                final Class cl = Class.forName(className);
                Plugin plugin = (Plugin) cl.newInstance();
                plugin.load(registry);
                plugins.add(plugin);
                notification.onLoaded(className);
            } catch (Exception e) {
                notification.onLoadFailure(className, e);
            }
        }

        // Resolve intra-plugin dependencies
        for (final Plugin plugin : plugins) {
            plugin.resolve(registry);
        }

    }

    public void dispose() {
        for (final Plugin plugin : plugins) {
            plugin.unload();
        }

    }

    private final ServiceRegistry registry;
    private final PluginManagerNotification notification;
    private final List<Plugin> plugins = new LinkedList<Plugin>();
}
