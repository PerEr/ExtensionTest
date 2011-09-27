package api.widget;

import javax.swing.*;

public interface WidgetRegistry {
    void registerWidgetBuilder(String type, WidgetFactory factory);
    void unregisterWidgetBuilder(WidgetFactory factory);
    JPanel instantiate(String type);
}
