package com.salimov.yurii.cache;

import java.util.Map;

/**
 * @author Yurii Salimov (yuriy.alex.salimov@gmail.com)
 */
public interface TemporaryCache extends Cache {

    /**
     * Saves object in the cache.
     *
     * @param key          the object key in the cache.
     * @param object       the object to save.
     * @param milliseconds the lifetime of objects (milliseconds).
     * @return The saving object.
     */
    Object put(Object key, Object object, long milliseconds);

    /**
     * Saves object in the cache.
     *
     * @param key          the object key in the cache.
     * @param object       the object to save.
     * @param seconds      the lifetime of objects (seconds).
     * @param milliseconds the lifetime of objects (milliseconds).
     * @return The saving object.
     */
    Object put(
            Object key, Object object,
            long seconds, long milliseconds
    );

    /**
     * Saves object in the cache.
     *
     * @param key          the object key in the cache.
     * @param object       the object to save.
     * @param minutes      the lifetime of objects (minutes).
     * @param seconds      the lifetime of objects (seconds).
     * @param milliseconds the lifetime of objects (milliseconds).
     * @return The saving object.
     */
    Object put(
            Object key, Object object,
            long minutes, long seconds,
            long milliseconds
    );

    /**
     * Saves object in the cache.
     *
     * @param key          the object key in the cache.
     * @param object       the object to save.
     * @param hours        the lifetime of objects (hours).
     * @param minutes      the lifetime of objects (minutes).
     * @param seconds      the lifetime of objects (seconds).
     * @param milliseconds the lifetime of objects (milliseconds).
     * @return The saving object.
     */
    Object put(
            Object key, Object object,
            long hours, long minutes,
            long seconds, long milliseconds
    );

    /**
     * Saves objects in the cache.
     * Saves objects if map is not empty.
     *
     * @param map          the map with objects to save.
     * @param milliseconds the lifetime of objects (milliseconds).
     */
    void putAll(Map<Object, Object> map, long milliseconds);

    /**
     * Saves objects in the cache.
     *
     * @param map          the map with objects to save.
     * @param seconds      the lifetime of objects (seconds).
     * @param milliseconds the lifetime of objects (milliseconds).
     */
    void putAll(
            Map<Object, Object> map,
            long seconds, long milliseconds
    );

    /**
     * Saves objects in the cache.
     *
     * @param map          the map with objects to save.
     * @param minutes      the lifetime of objects (minutes).
     * @param seconds      the lifetime of objects (seconds).
     * @param milliseconds the lifetime of objects (milliseconds).
     */
    void putAll(
            Map<Object, Object> map,
            long minutes, long seconds,
            long milliseconds
    );

    /**
     * Saves objects in the cache.
     *
     * @param map          the map with objects to save.
     * @param hours        the lifetime of objects (hours).
     * @param minutes      the lifetime of objects (minutes).
     * @param seconds      the lifetime of objects (seconds).
     * @param milliseconds the lifetime of objects (milliseconds).
     */
    void putAll(
            Map<Object, Object> map,
            long hours, long minutes,
            long seconds, long milliseconds
    );

    /**
     *
     * @return
     */
    static TemporaryCache getTemporaryCache() {
        return TemporaryCacheImpl.getInstance();
    }
}
