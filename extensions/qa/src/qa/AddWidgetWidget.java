package qa;

import com.example.prototype.api.plugin.ServiceRegistry;
import com.example.prototype.api.widget.WidgetArea;
import com.example.prototype.api.widget.WidgetAreaRegistry;
import com.example.prototype.api.widget.WidgetFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;

class AddWidgetWidget extends JButton implements WidgetChoiceListener {

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
        final JFrame frame = (JFrame) serviceRegistry.lookupService(JFrame.class);
        new WidgetNameDialog(frame, this);
    }

    private ServiceRegistry serviceRegistry;

    @Override
    public void onChoice(String choice) {
        final WidgetAreaRegistry areaRegistry = (WidgetAreaRegistry) serviceRegistry.lookupService(WidgetAreaRegistry.class);
        WidgetArea widgetArea = areaRegistry.lookup("bottom");  // TODO
        widgetArea.add(choice);
    }

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
