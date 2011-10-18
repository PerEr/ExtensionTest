package com.example.prototype.common.widget;

import com.example.prototype.api.widget.WidgetArea;
import junit.framework.TestCase;

import static org.mockito.Mockito.mock;

public class BasicWidgetAreaRegistryTest extends TestCase {

    private BasicWidgetAreaRegistry basicWidgetAreaRegistry;

    private final String Area1 = "one";
    private final String Area2 = "two";

    public void setUp() throws Exception {
        basicWidgetAreaRegistry = new BasicWidgetAreaRegistry();
    }

    public void tearDown() throws Exception {
        basicWidgetAreaRegistry.dispose();
        basicWidgetAreaRegistry = null;
    }

    public void testLookup() throws Exception {

    }

    public void testPublishWidgetArea() throws Exception {

        WidgetArea widgetArea1 = mock(WidgetArea.class);
        WidgetArea widgetArea2 = mock(WidgetArea.class);

        assert basicWidgetAreaRegistry.areas() == 0;

        basicWidgetAreaRegistry.publishWidgetArea(Area1, widgetArea1);
        basicWidgetAreaRegistry.publishWidgetArea(Area2, widgetArea2);

        assert basicWidgetAreaRegistry.areas() == 2;

        basicWidgetAreaRegistry.unpublishWidgetArea(widgetArea1);
        basicWidgetAreaRegistry.unpublishWidgetArea(widgetArea2);

        assert basicWidgetAreaRegistry.areas() == 0;
    }

    public void testDispose() throws Exception {

    }
}
