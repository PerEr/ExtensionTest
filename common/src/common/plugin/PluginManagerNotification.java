package common.plugin;

public interface PluginManagerNotification {

    void onLoadFailure(String classname, Exception e);
    void onLoaded(String className);
}
