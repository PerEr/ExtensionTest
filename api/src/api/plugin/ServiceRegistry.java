package api.plugin;

public interface ServiceRegistry {
    int publishService(Class cl, Object service);
    int unpublishService(Object service);
    Object lookupService(Class cl);
}
