package com.salimov.yurii.cache;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static com.salimov.yurii.cache.Validator.isNotEmpty;
import static com.salimov.yurii.cache.Validator.isNotNull;

/**
 * The class implements a set of methods for working with cache.
 *
 * @author Yurii Salimov (yuriy.alex.salimov@gmail.com)
 */
public final class Cache {

    /**
     * The map where can be stored some objects.
     */
    private static volatile Map<Key, Object> cache = new ConcurrentHashMap<>();

    /**
     * Сache is modified.
     */
    private static volatile boolean isNewCache = true;

    /**
     * Static constructor.
     * Creates and starts a new ScheduledExecutorService
     * for cleaning the cache.
     */
    static {
        final CacheCleaner cleaner = new CacheCleaner(getCache());
        new CacheScheduledExecutor(cleaner).go();
    }

    /**
     * Private constructor.
     */
    private Cache() {
    }

    /**
     * Saves object in the cache.
     *
     * @param <T>    the type of a key.
     * @param key    the object key in the cache.
     * @param object the object to save.
     * @return The saving object.
     */
    private static <T> Object put(
            final Key<T> key,
            final Object object
    ) {
        Object savingObject = null;
        if (isNotNull(key) && isNotNull(object)) {
            savingObject = getCache().put(key, object);
            setNewCache();
        }
        return isNotNull(savingObject) ? savingObject : object;
    }

    /**
     * Saves object in the cache.
     *
     * @param <T>          the type of a key.
     * @param key          the object key in the cache.
     * @param object       the object to save.
     * @param milliseconds the lifetime of objects (milliseconds).
     * @return The saving object.
     */
    public static <T> Object put(
            final T key,
            final Object object,
            final long milliseconds
    ) {
        return put(new Key<>(key, milliseconds), object);
    }

    /**
     * Saves object in the cache with default lifetime.
     *
     * @param <T>    the type of a key.
     * @param key    the object key in the cache.
     * @param object the object to save.
     * @return The saving object.
     */
    public static <T> Object put(
            final T key,
            final Object object
    ) {
        return put(key, object, -1L);
    }

    /**
     * Saves object in the cache.
     *
     * @param <T>          the type of a key.
     * @param key          the object key in the cache.
     * @param object       the object to save.
     * @param seconds      the lifetime of objects (seconds).
     * @param milliseconds the lifetime of objects (milliseconds).
     * @return The saving object.
     */
    public static <T> Object put(
            final T key,
            final Object object,
            final long seconds,
            final long milliseconds
    ) {
        return put(key, object, milliseconds + 1000 * seconds);
    }

    /**
     * Saves object in the cache.
     *
     * @param <T>          the type of a key.
     * @param key          the object key in the cache.
     * @param object       the object to save.
     * @param minutes      the lifetime of objects (minutes).
     * @param seconds      the lifetime of objects (seconds).
     * @param milliseconds the lifetime of objects (milliseconds).
     * @return The saving object.
     */
    public static <T> Object put(
            final T key,
            final Object object,
            final long minutes,
            final long seconds,
            final long milliseconds
    ) {
        return put(key, object, seconds + 60 * minutes, milliseconds);
    }

    /**
     * Saves object in the cache.
     *
     * @param <T>          the type of a key.
     * @param key          the object key in the cache.
     * @param object       the object to save.
     * @param hours        the lifetime of objects (hours).
     * @param minutes      the lifetime of objects (minutes).
     * @param seconds      the lifetime of objects (seconds).
     * @param milliseconds the lifetime of objects (milliseconds).
     * @return The saving object.
     */
    public static <T> Object put(
            final T key,
            final Object object,
            final long hours,
            final long minutes,
            final long seconds,
            final long milliseconds
    ) {
        return put(key, object, minutes + 60 * hours, seconds, milliseconds);
    }

    /**
     * Saves objects in the cache.
     * Saves objects if map is not empty.
     *
     * @param <T>          the type of key.
     * @param map          the map with objects to save.
     * @param milliseconds the lifetime of objects (milliseconds).
     */
    public static <T> void putAll(
            final Map<T, Object> map,
            final long milliseconds
    ) {
        if (isNotEmpty(map)) {
            for (Map.Entry<T, Object> entry : map.entrySet()) {
                put(entry.getKey(), entry.getValue(), milliseconds);
            }
        }
    }

    /**
     * Saves objects in the cache.
     *
     * @param <T>          the type of key.
     * @param map          the map with objects to save.
     * @param seconds      the lifetime of objects (seconds).
     * @param milliseconds the lifetime of objects (milliseconds).
     */
    public static <T> void putAll(
            final Map<T, Object> map,
            final long seconds,
            final long milliseconds
    ) {
        putAll(map, milliseconds + 1000 * seconds);
    }

    /**
     * Saves objects in the cache.
     *
     * @param <T>          the type of key.
     * @param map          the map with objects to save.
     * @param minutes      the lifetime of objects (minutes).
     * @param seconds      the lifetime of objects (seconds).
     * @param milliseconds the lifetime of objects (milliseconds).
     */
    public static <T> void putAll(
            final Map<T, Object> map,
            final long minutes,
            final long seconds,
            final long milliseconds
    ) {
        putAll(map, seconds + 60 * minutes, milliseconds);
    }

    /**
     * Saves objects in the cache.
     *
     * @param <T>          the type of key.
     * @param map          the map with objects to save.
     * @param hours        the lifetime of objects (hours).
     * @param minutes      the lifetime of objects (minutes).
     * @param seconds      the lifetime of objects (seconds).
     * @param milliseconds the lifetime of objects (milliseconds).
     */
    public static <T> void putAll(
            final Map<T, Object> map,
            final long hours,
            final long minutes,
            final long seconds,
            final long milliseconds
    ) {
        putAll(map, minutes + 60 * hours, seconds, milliseconds);
    }

    /**
     * Saves objects in the cache with default lifetime.
     *
     * @param <T> the type of a key.
     * @param map the map with objects to save.
     */
    public static <T> void putAll(final Map<T, Object> map) {
        final long milliseconds = -1;
        putAll(map, milliseconds);
    }

    /**
     * Returns object from cache with key.
     * Returns null if key is null.
     *
     * @param <T> the type of a key.
     * @param key the object key in the cache.
     * @return The object with key or null.
     */
    public static <T> Object get(final T key) {
        Object object = null;
        if (isNotNull(key)) {
            final Key<T> _key = new Key<>(key);
            object = get(_key);
        }
        return object;
    }

    /**
     * Returns all objects from cache with subKey.
     *
     * @param subKey the object key in the cache.
     * @return The objects with key or empty list (newer null).
     */
    public static Collection<Object> getAll(final String subKey) {
        final List<Object> objects = new ArrayList<>();
        if (isNotNull(subKey)) {
            getCache().keySet().stream()
                    .filter(key -> containsKey(key, subKey))
                    .forEach(key -> objects.add(get(key)));
        }
        return objects;
    }

    /**
     * Removes object from cache with key.
     * Removes object if key is not null.
     *
     * @param <T> the type of a key.
     * @param key the object key in the cache.
     */
    public static <T> void remove(final T key) {
        if (isNotNull(key)) {
            remove(new Key<>(key));
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
    public static void removeAll(final String... keys) {
        if (isNotEmpty(keys)) {
            new Thread(() -> {
                final Collection<Key> cacheKeys = new ArrayList<>(cache.keySet());
                for (String sKey : keys) {
                    if (isNotNull(sKey)) {
                        cacheKeys.stream()
                                .filter(key -> containsKey(key, sKey))
                                .forEach(Cache::remove);
                    }
                }
            }).start();
        }
    }

    /**
     * Sets map with objects.
     *
     * @param <T> the type of a key.
     * @param map the map with objects.
     */
    public static <T> void setAll(final Map<T, Object> map) {
        cache = new ConcurrentHashMap<>();
        if (isNotEmpty(map)) {
            for (Map.Entry<T, Object> entry : map.entrySet()) {
                put(entry.getKey(), entry.getValue());
            }
        }
    }

    /**
     * Clears the cache.
     */
    public static void clear() {
        getCache().clear();
    }

    /**
     * Clears objects by class.
     *
     * @param object the objects class to remove.
     */
    public static void clear(final Class object) {
        if (isNotNull(object)) {
            getCache().entrySet()
                    .stream()
                    .filter(entry -> filterByClass(entry, object))
                    .forEach(entry -> remove(entry.getKey()));
        }
    }

    /**
     * Checks if exist object with the key in the cache.
     *
     * @param <T> the type of a key.
     * @param key the object key in the cache.
     * @return true if object is exist, false otherwise.
     */
    public static <T> boolean exist(final T key) {
        return isNotNull(key) && exist(new Key<>(key));
    }

    /**
     * Returns information about objects in cache.
     * Information about cache saved in cache too.
     *
     * @return The maps with entries (newer null).
     */
    @SuppressWarnings("unchecked")
    public static Map<String, String> getEntriesToString() {
        final String key = "Cache information";
        Map<String, String> result = (Map) get(key);
        if (isNewCache()) {
            result = getNewEntriesToString(key);
            put(key, result);
            setOldCache();
        }
        return result;
    }

    /**
     * Gets size of objects which storing in the cache.
     *
     * @return The size of objects which storing in the cache.
     */
    public static int getSize() {
        return cache.size();
    }

    /**
     * Checks if exist object with the key in the cache.
     *
     * @param <T> the type of a key.
     * @param key the object key in the cache.
     * @return true if object is exist, false otherwise.
     */
    private static <T> boolean exist(final Key<T> key) {
        return isNotNull(key) && cache.containsKey(key);
    }

    /**
     * Returns object from cache with key.
     * Returns null if key is null.
     *
     * @param <T> the type of a key.
     * @param key the object key in the cache.
     * @return The object with key or null.
     */
    private static <T> Object get(final Key<T> key) {
        Object object = null;
        if (isNotNull(key)) {
            object = getCache().get(key);
        }
        return object;
    }

    /**
     * Removes object from cache with key.
     * Removes object if key is not null.
     *
     * @param <T> the type of a key.
     * @param key the object key in the cache.
     */
    private static <T> void remove(final Key<T> key) {
        if (isNotNull(key)) {
            getCache().remove(key);
            setNewCache();
        }
    }

    /**
     * Returns information about objects in cache.
     *
     * @param key the object key in the cache.
     * @return The maps with entries (newer null).
     */
    private static Map<String, String> getNewEntriesToString(final String key) {
        Map<String, String> result = new HashMap<>();
        String keyValueToString;
        String valueClassName;
        for (Map.Entry<Key, Object> entry : getCache().entrySet()) {
            keyValueToString = entry.getKey().getValue().toString();
            valueClassName = entry.getValue().getClass().getName();
            result.put(keyValueToString, valueClassName);
        }
        result.put(key, Map.class.getName());
        return result;
    }

    /**
     * Checks if key contains subKey.
     *
     * @param key    the object key in the cache.
     * @param subKey the object key in the cache.
     * @return true if key contains subKey, false otherwise.
     */
    private static boolean containsKey(final Key key, final String subKey) {
        final String keyValueToString = key.getValue().toString();
        return keyValueToString.contains(subKey);
    }

    /**
     * Filters entry object class with input class.
     *
     * @param <T>    the type of a key.
     * @param entry  the entry to filter.
     * @param object the class to equals.
     * @return true if entry class equals to object class, false otherwise.
     */
    private static <T> boolean filterByClass(final Map.Entry<T, Object> entry, final Class object) {
        return entry.getValue().getClass().equals(object);
    }

    /**
     * Сache is modified.
     *
     * @return true if cache is modified, false otherwise.
     */
    private static boolean isNewCache() {
        return isNewCache;
    }

    /**
     * Sets that the cache has been changed.
     */
    private static void setNewCache() {
        isNewCache = true;
    }

    /**
     * Sets that the cache has been not changed.
     */
    private static void setOldCache() {
        isNewCache = false;
    }

    /**
     * Returns the cache map.
     *
     * @return The cache map.
     */
    private static Map<Key, Object> getCache() {
        return cache;
    }
}
