package qa;

import api.widget.WidgetFactory;

import javax.swing.*;
import java.awt.*;
import java.util.Properties;

class AddWidgetWidget extends JPanel {

    AddWidgetWidget() {
        final JButton addButton = new JButton("+");
        addButton.setPreferredSize(new Dimension(20, 20));
        add(addButton);
    }

    static class Factory implements WidgetFactory {
        @Override
        public JComponent instantiate(Properties prp) {
            return new AddWidgetWidget();
        }
    }

    static String NAME = "add_widget";
}
