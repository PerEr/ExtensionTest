package api;

public interface Plugin {
    void load(EventSource eventSource);
    void unload();
}
