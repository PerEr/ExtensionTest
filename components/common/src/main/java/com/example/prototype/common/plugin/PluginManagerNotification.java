package com.example.prototype.common.plugin;

public interface PluginManagerNotification {

    void onLoad(String className);
    void onLoadFailure(String classname, Exception e);

    void onResolve(String className);
    void onResolveFailure(String classname, Exception e);
}
