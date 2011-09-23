package api;

public interface EventSource {
    Command addListener(EventListener listener);
}
