package api.widget;

import javax.swing.*;
import java.util.Properties;

public interface WidgetFactory {
    JComponent instantiate(Properties prp);
}
