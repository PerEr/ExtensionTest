package com.example.prototype.common.plugin;

import junit.framework.TestCase;
import static org.mockito.Mockito.*;

public class BasicServiceRegistryTest extends TestCase {

    private BasicServiceRegistry serviceRegistry;
    private TestServiceA testServiceA;
    private TestServiceB testServiceB;

    public void setUp() throws Exception {
        serviceRegistry = new BasicServiceRegistry();

        testServiceA = mock(TestServiceA.class);
        testServiceB = mock(TestServiceB.class);
    }

    public void tearDown() throws Exception {
        serviceRegistry.dispose();
    }

    public void testPublishUnpublishService() throws Exception {

        assert serviceRegistry.services() == 0;

        int addCount= serviceRegistry.publishService(testServiceA);

        assert addCount > 0;
        assert serviceRegistry.services() > 0;

        int removeCount = serviceRegistry.unpublishService(testServiceA);

        assert removeCount > 0;
        assert serviceRegistry.services() == 0;
    }

    public void testLookupService() throws Exception {

        {
            int counter = serviceRegistry.publishService(testServiceA);
            assert counter > 0;
        }
        {
            int counter = serviceRegistry.publishService(testServiceB);
            assert counter > 0;
        }

        {
            TestServiceA service = (TestServiceA) serviceRegistry.lookupService(TestServiceA.class);
            assert service == testServiceA;
        }

        {
            TestServiceB service = (TestServiceB) serviceRegistry.lookupService(TestServiceB.class);
            assert service == testServiceB;
        }

        {
            Runnable service = (Runnable) serviceRegistry.lookupService(Runnable.class);
            assert service == null;
        }

        serviceRegistry.unpublishService(testServiceA);
        serviceRegistry.unpublishService(testServiceB);

        assert serviceRegistry.services() == 0;
    }

    public void testDispose() throws Exception {

        ServiceRegistryNotification listener = mock(ServiceRegistryNotification.class);
        serviceRegistry.addListener(listener);

        serviceRegistry.publishService(testServiceA);
        serviceRegistry.publishService(testServiceB);

        assert serviceRegistry.services() > 0;

        serviceRegistry.dispose();

        verify(listener).onRegistryDispose();

        assert serviceRegistry.services() == 0;
        assert serviceRegistry.listeners() == 0;
    }

    public void testAddRemoveListener() throws Exception {

        ServiceRegistryNotification listener1 = mock(ServiceRegistryNotification.class);
        ServiceRegistryNotification listener2 = mock(ServiceRegistryNotification.class);

        assert serviceRegistry.listeners() == 0;

        serviceRegistry.addListener(listener1);
        serviceRegistry.addListener(listener2);

        serviceRegistry.publishService(testServiceA);

        verify(listener1).onServicePublished(TestServiceA.class, testServiceA);
        verify(listener2).onServicePublished(TestServiceA.class, testServiceA);


        assert serviceRegistry.listeners() == 2;

        serviceRegistry.unpublishService(testServiceA);

        verify(listener1).onServiceUnpublished(testServiceA);
        verify(listener2).onServiceUnpublished(testServiceA);

        assert serviceRegistry.services() == 0;

        serviceRegistry.removeListener(listener2);
        assert serviceRegistry.listeners() == 1;

        serviceRegistry.removeListener(listener1);
        assert serviceRegistry.listeners() == 0;
    }

}
