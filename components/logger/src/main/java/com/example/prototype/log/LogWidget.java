package com.example.prototype.log;

import javax.swing.*;
import java.awt.*;

class LogWidget extends JList {

    LogWidget(DefaultListModel listModel) {
        super(listModel);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(300, 400);
    }

}
