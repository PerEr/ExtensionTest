package app;

import api.EventSource;
import api.Plugin;
import api.ServiceRegistry;
import app.base.EventManager;
import app.base.ServiceRegistryImpl;
import app.view.AppFrame;

public class Main {

    private static ServiceRegistryImpl buildServiceRegistry() {
        ServiceRegistryImpl registry = new ServiceRegistryImpl();
        registry.register(new EventManager());
        return registry;
    }

    public static void main(String[] args) {
        AppFrame frame = new AppFrame();
        ServiceRegistryImpl registry = buildServiceRegistry();

        try {
            loadPlugin(registry, "test.TestPlugin");
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        //registry.dispose();
    }

    private static Plugin loadPlugin(ServiceRegistryImpl registry, String className) throws Exception {
        Class cl = Class.forName(className);
        Plugin plugin = (Plugin) cl.newInstance();
        plugin.load(registry);
        return plugin;
    }
}
