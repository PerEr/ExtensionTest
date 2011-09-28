package api.widget;

import javax.swing.*;

public interface WidgetRegistry {
    void registerWidgetFactory(String type, WidgetFactory factory);
    void unregisterWidgetFactory(WidgetFactory factory);
    JComponent instantiate(String type);
}
