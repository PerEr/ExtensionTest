package api;

public interface ServiceLookup {
    void publishService(Class cl, Object service);
    void unpublishService(Object service);
    Object lookupService(Class cl);
}
