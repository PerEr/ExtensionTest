package com.example.prototype.common.plugin;

public interface PluginManagerNotification {

    void onLoadFailure(String classname, Exception e);
    void onLoaded(String className);
}
