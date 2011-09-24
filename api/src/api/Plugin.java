package api;

public interface Plugin {
    void load(ServiceRegistry registry);
    void unload();
}
