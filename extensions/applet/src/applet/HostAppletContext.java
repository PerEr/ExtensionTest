package applet;

import java.applet.Applet;
import java.applet.AppletContext;
import java.applet.AudioClip;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Iterator;

class HostAppletContext implements AppletContext {

    public AudioClip getAudioClip(URL url) {
        return Applet.newAudioClip(url);
    }

    public Image getImage(URL url) {
        Toolkit tk = Toolkit.getDefaultToolkit();
        return tk.getImage(url);
    }

    public Applet getApplet(String name) {
        assert false;
        return null;
    }

    public Enumeration getApplets() {
        assert false;
        return null;
    }

    public void showDocument(URL url) {
        System.out.println("Showing document " + url);
    }

    public void showDocument(URL url, String frame) {
        try {
            showDocument(new URL(url.toString() + frame));
        } catch (MalformedURLException e) {
        }
    }

    public void showStatus(String message) {
        System.out.println(message);
    }

    public void setStream(String key, InputStream stream) throws IOException {
    }

    public InputStream getStream(String key) {
        assert false;
        return null;
    }

    public Iterator<String> getStreamKeys() {
        assert false;
        return null;
    }
}
