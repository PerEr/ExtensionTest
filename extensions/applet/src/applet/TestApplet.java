package applet;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Button;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.MalformedURLException;
import java.net.URL;

public class TestApplet extends Applet implements ActionListener {

  static TestApplet myApplet;

  static HostAppletStub hostStub;

  Image im;

  TestApplet() {
      setStub(hostStub = new HostAppletStub(new String[] {"image", "http://www.dn.se/Images/Logos/dnse-medals.gif"}));
      hostStub.setActive(true);
  }

  public void init() {

    System.out.println("Code base = " + getCodeBase());
    System.out.println("Document base = " + getDocumentBase());

    System.out.println("\ninit () called");
    System.out.println("isActive () returns " + isActive());

    Button b = new Button("Visit www.java2s.com");
    b.addActionListener(this);
    add(b);

    b = new Button("Audio");
    b.addActionListener(this);
    add(b);

    String imageName = getParameter("image");

    if (imageName != null)
      im = getImage(getCodeBase(), imageName);
  }

  public void start() {
    System.out.println("start () called");
    System.out.println("isActive () returns " + isActive());
  }

  public void paint(Graphics g) {
    if (im != null)
      g.drawImage(im, 0, 0, this);
  }

  public void actionPerformed(ActionEvent e) {
    if (e.getActionCommand().equals("Audio")) {
      String soundName = getParameter("audio");

      if (soundName != null) {
        AudioClip ac = getAudioClip(getDocumentBase(), soundName);

        ac.play();
      }

      return;
    }

    try {
      URL u = new URL("http://www.java2s.com");
      getAppletContext().showDocument(u);
    } catch (MalformedURLException exc) {
      System.out.println(e);
    }
  }

  public void stop() {
    System.out.println("stop () called");
    System.out.println("isActive () returns " + isActive());
  }

  public void destroy() {
    System.out.println("destroy () called");
    System.out.println("isActive () returns " + isActive());
  }

  public static void main(String[] args) {
    Frame frame = new Frame("AppletAndApp as an Application");
    myApplet = new TestApplet();

    frame.add(new Panel().add(myApplet));

    frame.addNotify();

    myApplet.setStub(hostStub = new HostAppletStub(args));
    myApplet.init();

    frame.setSize(300, 200);
    frame.setVisible(true);

    hostStub.setActive(true);
    myApplet.start();

    frame.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent w) {
        hostStub.setActive(false);
        myApplet.stop();
        myApplet.destroy();
        System.exit(0);
      }
    });
  }
}

