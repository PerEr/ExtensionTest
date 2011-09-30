package app.config;

public interface ScriptServices {
    void register(String pluginName);
    void loadAll();

    LayoutArea getLayout(String str);
}
