package api.plugin;

public interface ServiceLookup {
    int publishService(Class cl, Object service);
    int unpublishService(Object service);
    Object lookupService(Class cl);
}
