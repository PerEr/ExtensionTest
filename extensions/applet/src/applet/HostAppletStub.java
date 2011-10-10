package applet;

import java.applet.AppletContext;
import java.applet.AppletStub;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Hashtable;

class HostAppletStub implements AppletStub {

    HostAppletStub(String[] args) {
        context = new HostAppletContext();
        if ((args.length & 1) != 0)
            return;

        for (int i = 0; i < args.length; i += 2)
            ht.put(args[i], args[i + 1]);
    }

    public boolean isActive() {
        return active;
    }

    public URL getDocumentBase() {
        URL u = null;

        try {
            u = new URL("file:/C:./x.html");
        } catch (MalformedURLException e) {
        }

        return u;
    }

    public URL getCodeBase() {
        URL u = null;
        try {
            u = new URL("file:/C:./");
        } catch (MalformedURLException e) {
        }

        return u;
    }

    public String getParameter(String name) {
        return (String) ht.get(name);
    }

    public AppletContext getAppletContext() {
        return context;
    }

    public void appletResize(int width, int height) {
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    private boolean active = false;

    private Hashtable ht = new Hashtable();

    private HostAppletContext context;
}
