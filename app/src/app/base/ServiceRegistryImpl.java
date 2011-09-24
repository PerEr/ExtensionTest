package app.base;

import api.ServiceRegistry;

import java.util.HashMap;
import java.util.Map;

public class ServiceRegistryImpl implements ServiceRegistry{

    public void register(Object service) {
        Class cl = service.getClass();
        Class interfaces[] = cl.getInterfaces();
        for (int ii=0 ; ii<interfaces.length ; ++ii) {
            m_services.put(interfaces[ii], service);
        }
    }

    public Object lookupService(Class cl) {
        return m_services.get(cl);
    }

    public void dispose() {
        m_services.clear();
    }
    private Map<Class, Object> m_services = new HashMap<Class, Object>();
}
