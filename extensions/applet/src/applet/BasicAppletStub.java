package applet;

import java.applet.AppletContext;
import java.applet.AppletStub;
import java.net.URL;
import java.util.Properties;

public class BasicAppletStub implements AppletStub {

    BasicAppletStub(AppletContext appletContext, Properties properties) {

        this.appletContext = appletContext;
        this.properties = properties;
    }

    @Override
    public boolean isActive() {
        return true;
    }

    @Override
    public URL getDocumentBase() {
        return null;
    }

    @Override
    public URL getCodeBase() {
        return null;
    }

    @Override
    public String getParameter(String s) {
        return (String) properties.get(s);
    }

    @Override
    public AppletContext getAppletContext() {
        return appletContext;
    }

    @Override
    public void appletResize(int i, int i1) {
    }

    private AppletContext appletContext;
    private Properties properties;
}
