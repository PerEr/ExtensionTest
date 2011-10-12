package com.example.prototype.shapes;

import java.awt.*;
import java.util.Properties;

class ShapeConfig {
    private final Properties prp;

    ShapeConfig(Properties prp) {
        this.prp = prp;
    }

    Dimension dimension() {
        final int height = Integer.parseInt(prp.getProperty("height", "100"));
        final int width = Integer.parseInt(prp.getProperty("width", "100"));
        return new Dimension(width, height);
    }

    Color color() {
        return new Color(Integer.parseInt(prp.getProperty("color", "ff0000"), 16));
    }
}
