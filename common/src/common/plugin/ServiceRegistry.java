package common.plugin;

import java.util.*;

public class ServiceRegistry implements api.plugin.ServiceRegistry {

    public void publishService(Class cl, Object service) {
        assert cl.isInstance(service);
        addService(cl, service);
    }

    @Override
    public int publishService(Object service) {
        final Class interfaces[] = service.getClass().getInterfaces();
        assert interfaces.length > 0;
        for (int ii=0 ; ii<interfaces.length ; ++ii) {
            addService(interfaces[ii], service);
        }
        return interfaces.length;
    }

    public int unpublishService(Object service) {
        int count = 0;
        for (final Deque<Object> services : servicesMap.values()) {
            int sz = services.size();
            services.remove(service);
            count += sz - services.size();
        }
        return count;
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

    private final Map<Class, Deque<Object>> servicesMap = new HashMap<Class, Deque<Object>>();
}
