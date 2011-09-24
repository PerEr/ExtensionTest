package api;

public interface ServiceRegistry {
    Object lookupService(Class cl);
}
