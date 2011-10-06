package qa;

import api.plugin.ServiceRegistry;
import api.widget.WidgetFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;

class AddWidgetWidget extends JButton {

    public AddWidgetWidget(final ServiceRegistry serviceRegistry, Properties prp) {
        this.serviceRegistry = serviceRegistry;
        setText("+");
        setPreferredSize(new Dimension(20, 20));
        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                onClick();
            }
        });
    }

    private void onClick() {
        JFrame frame = (JFrame) serviceRegistry.lookupService(JFrame.class);
        WidgetNameDialog myDialog = new WidgetNameDialog(frame, "Do you like Java?");
        //final WidgetRegistry widgetRegistry = (WidgetRegistry) serviceRegistry.lookupService(WidgetRegistry.class);
    }

    private ServiceRegistry serviceRegistry;

    static class Factory implements WidgetFactory {

        Factory(ServiceRegistry serviceRegistry) {
            this.serviceRegistry = serviceRegistry;
        }

        @Override
        public JComponent instantiate(Properties prp) {
            return new AddWidgetWidget(serviceRegistry, prp);
        }

        private final ServiceRegistry serviceRegistry;
    }

    static String NAME = "add_widget";
}
