package com.example.prototype.api.plugin;

/**
 * A registry for services within the application.
 *
 * @author Per Ersson
 */
public interface ServiceRegistry {

    /**
     * Publish an object as a specific service type
     * @param cl The service type
     * @param service The service implementation
     */
    void publishService(Class cl, Object service);

    /**
     * Publish one or more services. The published object is inspected for interfaces.
     * @param service The service implementation
     * @return The number of published services (often 1)
     */
    int publishService(Object service);

    /**
     * Unpublish an implementation, one or more services may be removed.
     * @param service The service implementation
     * @return The number of unpublished services (1 or more)
     */
    int unpublishService(Object service);

    /**
     * Lookup the implementation of a service
     * @param cl The service type
     * @return The implementation
     */
    Object lookupService(Class cl);
}
