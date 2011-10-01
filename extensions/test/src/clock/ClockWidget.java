package clock;

// File   : GUI-lowlevel/animation/analogclock/ClockWidget.java
// Purpose: An analog clock component -- Uses Timer and Calendar.
// Note   : Uses a BufferedImage for clock face so isn't drawn each time.
// Author : Fred Swartz, 1999-2007, Placed in public domain.

import api.widget.WidgetFactory;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.*;

class ClockWidget extends JComponent {

    private static final double TWO_PI   = 2.0 * Math.PI;

    private static final int    UPDATE_INTERVAL = 100;  // Millisecs

    private Calendar calendar = Calendar.getInstance();  // Current time.

    private int diameter;                 // Height and width of clock face
    private int centerX;                  // x coord of middle of clock
    private int centerY;                  // y coord of middle of clock
    private BufferedImage clockImage;     // Saved image of the clock face.

    static final String NAME = "clock";

    ClockWidget() {
        setPreferredSize(new Dimension(120,120));
        setOpaque(false);

        javax.swing.Timer timer = new javax.swing.Timer(UPDATE_INTERVAL, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateTime();
                repaint();
            }
        });
        timer.start();
    }

    private void updateTime() {
        //... Avoid creating new objects.
        calendar.setTimeInMillis(System.currentTimeMillis());
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);

        //... The panel may have been resized, get current dimensions
        int width = getWidth();
        int height = getHeight();
        diameter = ((width < height) ? width : height);
        diameter -= 4;
        centerX = width / 2;
        centerY = height / 2;

        //... Create the clock face background image if this is the first time,
        //    or if the size of the panel has changed
        if (clockImage == null
                || clockImage.getWidth() != width
                || clockImage.getHeight() != height) {
            clockImage = (BufferedImage)(this.createImage(width, height));

            //... Get a graphics context from this image
            Graphics2D g2a = clockImage.createGraphics();
            g2a.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                 RenderingHints.VALUE_ANTIALIAS_ON);
            drawClockFace(g2a);
        }

        //... Draw the clock face from the precomputed image
        g2.drawImage(clockImage, null, 0, 0);

        //... Draw the clock hands dynamically each time.
        drawClockHands(g2);
    }

    private void drawClockHands(Graphics2D g2) {
        //... Get the various time elements from the Calendar object.
        int hours   = calendar.get(Calendar.HOUR);
        int minutes = calendar.get(Calendar.MINUTE);
        int seconds = calendar.get(Calendar.SECOND);
        int millis  = calendar.get(Calendar.MILLISECOND);

        //... second hand
        int handMax = diameter / 2;    // Second hand extends to outer rim.
        double fseconds = (seconds + (double)millis/1000) / 60.0;
        drawRadius(g2, fseconds, 0, handMax, 2);

        //... minute hand
        handMax = diameter / 3;
        double fminutes = (minutes + fseconds) / 60.0;
        drawRadius(g2, fminutes, 0, handMax, 4);

        //... hour hand
        handMax = diameter / 4;
        drawRadius(g2, (hours + fminutes) / 12.0, 0, handMax, 6);
    }

    private void drawClockFace(Graphics2D g2) {
        // ... Draw the clock face.  Probably into a buffer.
        g2.setColor(Color.YELLOW);
        g2.fillOval(centerX-diameter/2, centerY-diameter/2, diameter, diameter);
        g2.setColor(Color.BLUE);
        g2.drawOval(centerX-diameter/2, centerY-diameter/2, diameter, diameter);

        int radius = diameter / 2;

        //... Draw the tick marks around the circumference.
        for (int sec = 0; sec < 60; sec++) {
            int tickStart;
            if (sec%5 == 0) {
                tickStart = radius - 10;  // Draw long tick mark every 5.
            } else {
                tickStart = radius - 5;   // Short tick mark.
            }
            drawRadius(g2, sec / 60.0, tickStart , radius, 2);
        }
    }

    // This draw lines along a radius from the clock face center.
    // By changing the parameters, it can be used to draw tick marks,
    // as well as the hands.
    private void drawRadius(Graphics2D g2, double percent,
                            int minRadius, int maxRadius, int width) {
        //... percent parameter is the fraction (0.0 - 1.0) of the way
        //    clockwise from 12.   Because the Graphics2D methods use radians
        //    counterclockwise from 3, a little conversion is necessary.
        //    It took a little experimentation to get this right.
        double radians = (0.5 - percent) * TWO_PI;
        double sine   = Math.sin(radians);
        double cosine = Math.cos(radians);

        int dxmin = centerX + (int)(minRadius * sine);
        int dymin = centerY + (int)(minRadius * cosine);

        int dxmax = centerX + (int)(maxRadius * sine);
        int dymax = centerY + (int)(maxRadius * cosine);

        drawThickLine(g2, dxmin, dymin, dxmax, dymax, width);

        // g2.drawLine(dxmin, dymin, dxmax, dymax);
    }

    public void drawThickLine(Graphics g, int x1, int y1, int x2, int y2, int thickness) {
        // The thick line is in fact a filled polygon
        int dX = x2 - x1;
        int dY = y2 - y1;
        // line length
        double lineLength = Math.sqrt(dX * dX + dY * dY);

        double scale = (double) (thickness) / (2 * lineLength);

        // The x,y increments from an endpoint needed to create a rectangle...
        double ddx = -scale * (double) dY;
        double ddy = scale * (double) dX;
        ddx += (ddx > 0) ? 0.5 : -0.5;
        ddy += (ddy > 0) ? 0.5 : -0.5;
        int dx = (int) ddx;
        int dy = (int) ddy;

        // Now we can compute the corner points...
        int xPoints[] = new int[4];
        int yPoints[] = new int[4];

        xPoints[0] = x1 + dx;
        yPoints[0] = y1 + dy;
        xPoints[1] = x1 - dx;
        yPoints[1] = y1 - dy;
        xPoints[2] = x2 - dx;
        yPoints[2] = y2 - dy;
        xPoints[3] = x2 + dx;
        yPoints[3] = y2 + dy;

        g.fillPolygon(xPoints, yPoints, 4);
    }


    static class Factory implements WidgetFactory {
        @Override
        public JComponent instantiate(Properties prp) {
            return new ClockWidget();
        }
    }
}