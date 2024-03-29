package com.example.prototype.common.plugin;

import com.example.prototype.api.plugin.ServiceRegistry;

import java.util.*;

public class BasicServiceRegistry implements ServiceRegistry {

    public void publishService(Class cl, Object service) {
        assert cl.isInstance(service);
        addService(cl, service);
    }

    @Override
    public int publishService(Object service) {
        final Class interfaces[] = service.getClass().getInterfaces();
        assert interfaces.length > 0;
        for (Class ii : interfaces) {
            addService(ii, service);
        }
        return interfaces.length;
    }

    public int unpublishService(Object service) {

        int count = 0;
        Iterator<Map.Entry<Class, Deque<Object>>> ii = servicesMap.entrySet().iterator();
        while (ii.hasNext()) {
            Map.Entry<Class, Deque<Object>> entry = ii.next();
            Deque<Object> services = entry.getValue();
            int sz = services.size();
            services.remove(service);
            count += sz - services.size();
            if (services.size() == 0)
                ii.remove();
        }

        for (ServiceRegistryNotification listener : listeners) {
            listener.onServiceUnpublished(service);
        }
        return count;
    }

    public Object lookupService(Class cl) {
        Deque<Object> entry = servicesMap.get(cl);
        return entry != null ? entry.getFirst() : null;
    }

    public void dispose() {
        for (ServiceRegistryNotification listener : listeners) {
            listener.onRegistryDispose();
        }
        listeners.clear();
        servicesMap.clear();
    }

    public void addListener(ServiceRegistryNotification listener) {
        listeners.add(listener);
    }

    public void removeListener(ServiceRegistryNotification listener) {
        boolean removedListener = listeners.remove(listener);
        assert removedListener;
    }

    private void addService(Class serviceType, Object service) {
        Deque<Object> entry = servicesMap.get(serviceType);
        if (entry == null) {
            entry = new LinkedList<Object>();
            servicesMap.put(serviceType, entry);
        }
        entry.addFirst(service);

        // Notify all listeners
        for (ServiceRegistryNotification listener : listeners) {
            listener.onServicePublished(serviceType, service);
        }
    }

    int services() {
        return servicesMap.size();
    }

    int listeners() {
        return listeners.size();
    }

    private final Map<Class, Deque<Object>> servicesMap = new HashMap<Class, Deque<Object>>();
    private final List<ServiceRegistryNotification> listeners = new LinkedList<ServiceRegistryNotification>();
}
