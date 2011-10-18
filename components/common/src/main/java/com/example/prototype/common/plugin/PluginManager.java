package com.example.prototype.common.plugin;

import com.example.prototype.api.plugin.Plugin;
import com.example.prototype.api.plugin.ServiceRegistry;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ServiceLoader;

public class PluginManager {

    public PluginManager(ServiceRegistry registry, PluginManagerNotification notification) {
        this.registry = registry;
        this.notification = notification;
    }

    public int autodetectPlugins() {

        final List<Plugin> detectedPlugins = new LinkedList<Plugin>();

        // Detect and instantiate all "Plugin" classes.
        final ServiceLoader<Plugin> loader = ServiceLoader.load(Plugin.class);
        for (Plugin plugin : loader) {
            detectedPlugins.add(plugin);
        }

        // Step 1) "Load" all plugins
        Iterator<Plugin> ii = detectedPlugins.iterator();
        while (ii.hasNext()) {
            Plugin plugin = ii.next();
            String name = plugin.getClass().getName();
            try {
                plugin.load(registry);
                notification.onLoad(name);
            } catch(Exception ex) {
                notification.onLoadFailure(name, ex);
                ii.remove();
            }
        }

        // Step 2) "Resolve" all loaded plugins
        for (Plugin plugin : detectedPlugins) {
            String name = plugin.getClass().getName();
            try {
                plugin.resolve(registry);
                notification.onResolve(name);
            } catch(Exception ex) {
                notification.onResolveFailure(name, ex);
                ii.remove();
            }
        }

        // Return the plugins that both loaded and resolved
        return detectedPlugins.size();
    }

    public void dispose() {
        for (final Plugin plugin : plugins) {
            plugin.unload();
        }
        plugins.clear();
    }

    private final ServiceRegistry registry;
    private final PluginManagerNotification notification;
    private final List<Plugin> plugins = new LinkedList<Plugin>();
}
