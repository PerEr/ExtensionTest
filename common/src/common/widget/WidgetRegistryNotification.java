package common.widget;

public interface WidgetRegistryNotification {

    void onWidgetPublished(String type);

    void onWidgetUnpublished(String type);

    void onRegistryDispose();
}
