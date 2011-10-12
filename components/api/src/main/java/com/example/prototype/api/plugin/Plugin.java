package com.example.prototype.api.plugin;

/**
 * The interface all plugins must realize. Publish services provided by the plugin in the load methods, resolve
 * dependencies to other plugins in the resolve method. When resolve is called a plugin can rely on all other
 * plugins having already been given the opportunity to publish its services.
 *
 * @author Per Ersson
 */
public interface Plugin {
    /**
     * Register services with the service registry
     * @param registry The service registry
     */
    void load(ServiceRegistry registry);

    /**
     * Resolve dependencies to services used.
     * @param registry The service registry
     */
    void resolve(ServiceRegistry registry);

    /**
     * De-register all published services and cleanup all resources in use.
     */
    void unload();
}
