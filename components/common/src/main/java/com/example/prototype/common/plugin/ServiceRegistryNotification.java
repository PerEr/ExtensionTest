package com.example.prototype.common.plugin;

public interface ServiceRegistryNotification {
    void onServicePublished(Class serviceType, Object service);
    void onServiceUnpublished(Object service);
    void onRegistryDispose();
}
