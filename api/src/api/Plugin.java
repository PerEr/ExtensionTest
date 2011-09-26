package api;

public interface Plugin {
    void publishServices(ServiceLookup lookup);
    void resolveDependencies(ServiceLookup lookup);
    void unload();
}
