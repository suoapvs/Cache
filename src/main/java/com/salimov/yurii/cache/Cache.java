package com.salimov.yurii.cache;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * @author Yurii Salimov (yuriy.alex.salimov@gmail.com)
 */
public interface Cache {

    /**
     * Saves object in the cache with default lifetime.
     *
     * @param key    the object key in the cache.
     * @param object the object to save.
     * @return The saving object.
     */
    Object put(Object key, Object object);

    /**
     * Saves objects in the cache with default lifetime.
     *

     * @param map the map with objects to save.
     */
    void putAll(Map<Object, Object> map);

    /**
     * Returns object from cache with key.
     * Returns null if key is null.
     *

     * @param key the object key in the cache.
     * @return The object with key or null.
     */
    Object get(Object key);

    /**
     * Returns all objects from cache with subKey.
     *
     * @return The objects with key or empty list (newer null).
     */
    Collection<Object> getAll();

    /**
     * Removes object from cache with key.
     * Removes object if key is not null.
     *
     * @param key the object key in the cache.
     */
    void remove(Object key);

    /**
     * Removes all objects from the cache
     * if they key contains the string subKey.
     * Removes objects if subKey is not blank.
     * The objects are removed in a parallel thread.
     *
     * @param keys the key strings.
     */
    void removeAll(final Collection<Object> keys);

    /**
     * Clears objects by class.
     *
     * @param object the objects class to remove.
     */
    void remove(Class object);

    /**
     * Clears the cache.
     */
    void clear();

    /**
     * Sets map with objects.
     *
     * @param map the map with objects.
     */
    void setAll(Map<Object, Object> map);

    /**
     * Checks if exist object with the key in the cache.
     *
     * @param key the object key in the cache.
     * @return true if object is exist, false otherwise.
     */
    boolean exist(Object key);

    /**
     * Returns information about objects in cache.
     * Information about cache saved in cache too.
     *
     * @return The maps with entries (newer null).
     */
    Map<String, String> getEntriesToString();

    /**
     *
     * @return
     */
    Set<Key> keySet();

    /**
     *
     * @return
     */
    boolean isEmpty();

    /**
     *
     * @return
     */
    int getSize();

    /**
     *
     * @return
     */
    static TemporaryCache getTemporaryCache() {
        return TemporaryCache.getTemporaryCache();
    }
}
