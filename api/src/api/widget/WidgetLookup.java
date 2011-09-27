package api.widget;

import javax.swing.*;

public interface WidgetLookup {
    void registerWidgetBuilder(String type, WidgetBuilder builder);
    void unregisterWidgetBuilder(WidgetBuilder builder);
    JPanel instantiate(String type);
}
