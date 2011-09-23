package app;

import api.EventSource;
import api.Plugin;
import app.base.EventManager;
import app.view.AppFrame;

public class Main {
    public static void main(String[] args) {
        System.out.println("1");
        AppFrame frame = new AppFrame();
        EventSource eventManager = new EventManager();
        try {
            Class cl = Class.forName("test.TestPlugin");
            api.Plugin pl = (Plugin) cl.newInstance();
            pl.load(eventManager);
            pl.unload();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (InstantiationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IllegalAccessException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
