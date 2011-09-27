package api.plugin;

public interface Plugin {
    void load(ServiceRegistry registry);
    void unload();
}
