package app.base;

import api.ServiceLookup;

import java.util.*;

public class ServiceRegistry implements ServiceLookup {

    public void publishService(Class cl, Object service) {
        Class interfaces[] = service.getClass().getInterfaces();
        for (int ii=0 ; ii<interfaces.length ; ++ii) {
            addService(interfaces[ii], service);
        }
    }

    public void unpublishService(Object service) {
        for (Deque<Object> services : servicesMap.values()) {
            services.remove(service);
        }
    }

    public Object lookupService(Class cl) {
        return servicesMap.get(cl).getFirst();
    }

    public void dispose() {
        servicesMap.clear();
    }


    private void addService(Class serviceType, Object service) {
        Deque<Object> entry = servicesMap.get(serviceType);
        if (entry == null) {
            entry = new LinkedList<Object>();
            servicesMap.put(serviceType, entry);
        }
        entry.addFirst(service);
    }

    private Map<Class, Deque<Object>> servicesMap = new HashMap<Class, Deque<Object>>();
}
