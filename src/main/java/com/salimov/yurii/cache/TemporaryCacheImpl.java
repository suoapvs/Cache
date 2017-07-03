package com.salimov.yurii.cache;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static com.salimov.yurii.cache.Validator.isNull;

/**
 * The class implements a set of methods for working with cache.
 *
 * @author Yurii Salimov (yuriy.alex.salimov@gmail.com)
 */
final class TemporaryCacheImpl implements TemporaryCache {

    private static TemporaryCacheImpl cache;

    /**
     * The map where can be stored some objects.
     */
    private volatile Map<Key, Object> map;

    /**
     * Сache is modified.
     */
    private volatile boolean modified;

    /**
     * Private constructor.
     */
    private TemporaryCacheImpl() {
        this.map = new ConcurrentHashMap<>();
        this.modified = true;
        final CacheCleaner cleaner = new CacheCleaner(this);
        final CacheScheduledExecutor executor = new CacheScheduledExecutor(cleaner);
        executor.go();
    }

    static TemporaryCacheImpl getInstance() {
        if (isNull(cache)) {
            cache = new TemporaryCacheImpl();
        }
        return cache;
    }

    /**
     * Saves object in the cache with default lifetime.
     *
     * @param key    the object key in the cache.
     * @param object the object to save.
     * @return The saving object.
     */
    @Override
    public Object put(final Object key, final Object object) {
        final long milliseconds = -1L;
        return put(key, object, milliseconds);
    }

    /**
     * Saves object in the cache.
     *
     * @param key          the object key in the cache.
     * @param object       the object to save.
     * @param milliseconds the lifetime of objects (milliseconds).
     * @return The saving object.
     */
    @Override
    public Object put(final Object key, final Object object, final long milliseconds) {
        final Key keyObject = new Key(key, milliseconds);
        return put(keyObject, object);
    }

    /**
     * Saves object in the cache.
     *
     * @param key          the object key in the cache.
     * @param object       the object to save.
     * @param seconds      the lifetime of objects (seconds).
     * @param milliseconds the lifetime of objects (milliseconds).
     * @return The saving object.
     */
    @Override
    public Object put(
            final Object key, final Object object,
            final long seconds, final long milliseconds
    ) {
        final long _milliseconds = milliseconds + 1000 * seconds;
        return put(key, object, _milliseconds);
    }

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
    @Override
    public Object put(
            final Object key, final Object object,
            final long minutes, final long seconds,
            final long milliseconds
    ) {
        final long _seconds = seconds + 60 * minutes;
        return put(key, object, _seconds, milliseconds);
    }

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
    @Override
    public Object put(
            final Object key, final Object object,
            final long hours, final long minutes,
            final long seconds, final long milliseconds
    ) {
        final long _minutes = minutes + 60 * hours;
        return put(key, object, _minutes, seconds, milliseconds);
    }

    /**
     * Saves objects in the cache.
     * Saves objects if map is not empty.
     *
     * @param map          the map with objects to save.
     * @param milliseconds the lifetime of objects (milliseconds).
     */
    @Override
    public void putAll(final Map<Object, Object> map, final long milliseconds) {
        if (Validator.isNotEmpty(map)) {
            for (Map.Entry<Object, Object> entry : map.entrySet()) {
                put(entry.getKey(), entry.getValue(), milliseconds);
            }
        }
    }

    /**
     * Saves objects in the cache.
     *
     * @param map          the map with objects to save.
     * @param seconds      the lifetime of objects (seconds).
     * @param milliseconds the lifetime of objects (milliseconds).
     */
    @Override
    public void putAll(
            final Map<Object, Object> map,
            final long seconds, final long milliseconds
    ) {
        final long _milliseconds = milliseconds + 1000 * seconds;
        putAll(map, _milliseconds);
    }

    /**
     * Saves objects in the cache.
     *
     * @param map          the map with objects to save.
     * @param minutes      the lifetime of objects (minutes).
     * @param seconds      the lifetime of objects (seconds).
     * @param milliseconds the lifetime of objects (milliseconds).
     */
    @Override
    public void putAll(
            final Map<Object, Object> map,
            final long minutes, final long seconds,
            final long milliseconds
    ) {
        final long _seconds = seconds + 60 * minutes;
        putAll(map, _seconds, milliseconds);
    }

    /**
     * Saves objects in the cache.
     *
     * @param map          the map with objects to save.
     * @param hours        the lifetime of objects (hours).
     * @param minutes      the lifetime of objects (minutes).
     * @param seconds      the lifetime of objects (seconds).
     * @param milliseconds the lifetime of objects (milliseconds).
     */
    @Override
    public void putAll(
            final Map<Object, Object> map,
            final long hours, final long minutes,
            final long seconds, final long milliseconds
    ) {
        final long _minutes = minutes + 60 * hours;
        putAll(map, _minutes, seconds, milliseconds);
    }

    /**
     * Saves objects in the cache with default lifetime.
     *
     * @param map the map with objects to save.
     */
    @Override
    public void putAll(final Map<Object, Object> map) {
        final long milliseconds = -1L;
        putAll(map, milliseconds);
    }

    /**
     * Returns object from cache with key.
     * Returns null if key is null.
     *
     * @param key the object key in the cache.
     * @return The object with key or null.
     */
    @Override
    public Object get(final Object key) {
        Object object = null;
        if (Validator.isNotNull(key)) {
            final Key _key = new Key(key);
            object = get(_key);
        }
        return object;
    }

    /**
     * Returns all objects from cache with subKey.
     *
     * @return The objects with key or empty list (newer null).
     */
    @Override
    public Collection<Object> getAll() {
        final Collection<Object> values = this.map.values();
        return new ArrayList<>(values);
    }

    /**
     * Removes object from cache with key.
     * Removes object if key is not null.
     *
     * @param key the object key in the cache.
     */
    @Override
    public void remove(final Object key) {
        if (Validator.isNotNull(key)) {
            final Key _key = new Key(key);
            remove(_key);
        }
    }

    /**
     * Removes all objects from the cache
     * if they key contains the string subKey.
     * Removes objects if subKey is not blank.
     * The objects are removed in a parallel thread.
     *
     * @param keys the key strings.
     */
    @Override
    public void removeAll(final Collection<Object> keys) {
        if (Validator.isNotEmpty(keys)) {
            keys.forEach(this::remove);
        }
    }

    /**
     * Sets map with objects.
     *
     * @param map the map with objects.
     */
    @Override
    public void setAll(final Map<Object, Object> map) {
        clear();
        if (Validator.isNotEmpty(map)) {
            for (Map.Entry<Object, Object> entry : map.entrySet()) {
                put(entry.getKey(), entry.getValue());
            }
        }
    }

    /**
     * Clears the cache.
     */
    @Override
    public void clear() {
        this.map.clear();
    }

    /**
     * Clears objects by class.
     *
     * @param objectsClass the objects class to remove.
     */
    @Override
    public void remove(final Class objectsClass) {
        if (Validator.isNotNull(objectsClass)) {
            for (Map.Entry<Key, Object> entry : this.map.entrySet()) {
                if (filterByClass(entry, objectsClass)) {
                    remove(entry.getKey());
                }
            }
        }
    }

    /**
     * Checks if exist object with the key in the cache.
     *
     * @param key the object key in the cache.
     * @return true if object is exist, false otherwise.
     */
    @Override
    public boolean exist(final Object key) {
        final Key _key = new Key(key);
        return Validator.isNotNull(key) && exist(_key);
    }

    /**
     * Returns information about objects in cache.
     * Information about cache saved in cache too.
     *
     * @return The maps with entries (newer null).
     */
    @Override
    @SuppressWarnings("unchecked")
    public Map<String, String> getEntriesToString() {
        final String key = "Cache information";
        Map<String, String> result = (Map) get(key);
        if (isModified()) {
            result = getNewEntriesToString(key);
            put(key, result);
            noModify();
        }
        return result;
    }

    /**
     * @return
     */
    @Override
    public Set<Key> keySet() {
        return this.map.keySet();
    }

    /**
     * @return
     */
    @Override
    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    /**
     * Gets size of objects which storing in the cache.
     *
     * @return The size of objects which storing in the cache.
     */
    public int getSize() {
        return this.map.size();
    }

    /**
     * Removes object from cache with key.
     * Removes object if key is not null.
     *
     * @param key the object key in the cache.
     */
    void remove(final Key key) {
        if (Validator.isNotNull(key)) {
            this.map.remove(key);
            modify();
        }
    }

    /**
     * Saves object in the cache.
     *
     * @param key    the object key in the cache.
     * @param object the object to save.
     * @return The saving object.
     */
    private Object put(final Key key, final Object object) {
        Object savingObject = null;
        if (Validator.isNotNull(key) && Validator.isNotNull(object)) {
            savingObject = this.map.put(key, object);
            modify();
        }
        return Validator.isNotNull(savingObject) ? savingObject : object;
    }

    /**
     * Checks if exist object with the key in the cache.
     *
     * @param key the object key in the cache.
     * @return true if object is exist, false otherwise.
     */
    private boolean exist(final Key key) {
        return Validator.isNotNull(key) && this.map.containsKey(key);
    }

    /**
     * Returns object from cache with key.
     * Returns null if key is null.
     *
     * @param key the object key in the cache.
     * @return The object with key or null.
     */
    private Object get(final Key key) {
        Object object = null;
        if (Validator.isNotNull(key)) {
            object = this.map.get(key);
        }
        return object;
    }

    /**
     * Returns information about objects in cache.
     *
     * @return The maps with entries (newer null).
     */
    private Map<String, String> getNewEntriesToString(final String key) {
        Map<String, String> result = new HashMap<>();
        String keyValueToString;
        String valueClassName;
        for (Map.Entry<Key, Object> entry : this.map.entrySet()) {
            keyValueToString = getKeyValueToString(entry);
            valueClassName = getValueClassName(entry);
            result.put(keyValueToString, valueClassName);
        }
        result.put(key, Map.class.getName());
        return result;
    }

    /**
     * @param entry
     * @return
     */
    private String getKeyValueToString(final Map.Entry<Key, Object> entry) {
        final Key key = entry.getKey();
        final Object value = key.getValue();
        return value.toString();
    }

    /**
     * @param entry
     * @return
     */
    private String getValueClassName(final Map.Entry<Key, Object> entry) {
        final Object value = entry.getValue();
        final Class<?> valueClass = value.getClass();
        return valueClass.getName();
    }

    /**
     * Checks if key contains subKey.
     *
     * @param key    the object key in the cache.
     * @param subKey the object key in the cache.
     * @return true if key contains subKey, false otherwise.
     */
    private boolean containsKey(final Key key, final String subKey) {
        final String keyValueToString = key.getValue().toString();
        return keyValueToString.contains(subKey);
    }

    /**
     * Filters entry object class with input class.
     *
     * @param entry  the entry to filter.
     * @param object the class to equals.
     * @return true if entry class equals to object class, false otherwise.
     */
    private boolean filterByClass(final Map.Entry<Key, Object> entry, final Class object) {
        final Object value = entry.getValue();
        final Class valueClass = value.getClass();
        return valueClass.equals(object);
    }

    /**
     * Сache is modified.
     *
     * @return true if cache is modified, false otherwise.
     */
    private boolean isModified() {
        return this.modified;
    }

    /**
     * Sets that the cache has been changed.
     */
    private void modify() {
        this.modified = true;
    }

    /**
     * Sets that the cache has been not changed.
     */
    private void noModify() {
        this.modified = false;
    }
}
