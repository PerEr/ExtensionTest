package api;

public interface Plugin {
    void load(ServiceLookup lookup);
    void unload();
}
