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

        List<Plugin> detectedPlugins = new LinkedList<Plugin>();

        // Detect and instantiate all "Plugin" classes.
        ServiceLoader<Plugin> loader = ServiceLoader.load(Plugin.class);
        Iterator<Plugin> ii = loader.iterator();
        while (ii.hasNext()) {
            Plugin plugin = ii.next();
            detectedPlugins.add(plugin);
        }


        for (Plugin plugin : detectedPlugins) {
            plugin.load(registry);
        }

        for (Plugin plugin : detectedPlugins) {
            plugin.resolve(registry);
        }

        return detectedPlugins.size();
    }

    public void load(String[] classNames) {

        final List<Plugin> newPlugins = new LinkedList<Plugin>();

        // Publish services
        for (final String className : classNames) {
            try {
                final Class cl = Class.forName(className);
                Plugin plugin = (Plugin) cl.newInstance();
                plugin.load(registry);
                newPlugins.add(plugin);
                notification.onLoaded(className);
            } catch (Exception e) {
                notification.onLoadFailure(className, e);
            }
        }

        // Resolve intra-plugin dependencies
        for (final Plugin plugin : newPlugins) {
            plugin.resolve(registry);
            plugins.add(plugin);
        }

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
