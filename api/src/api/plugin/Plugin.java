package api.plugin;

public interface Plugin {
    void load(ServiceLookup lookup);
    void unload();
}
