package com.salimov.yurii.cache;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * The class implements a set of methods for working with cache.
 *
 * @author Yurii Salimov (yuriy.alex.salimov@gmail.com)
 * @version 1.0
 */
public final class Cache {

    /**
     * The time to delay first execution (5 hours).
     */
    private final static long SCHEDULER_INITIAL_DELAY = 5L;

    /**
     * The period between successive executions (3 hour).
     */
    private final static long SCHEDULER_PERIOD = 3L;

    /**
     * Time unit representing one hour.
     */
    private final static TimeUnit TIME_UNIT = TimeUnit.HOURS;

    /**
     * The map where can be stored some objects.
     */
    private static volatile Map<Key, Object> cache;

    /**
     * Сache is modified.
     */
    private static volatile boolean isNewCache;

    /**
     * Static constructor.
     * Creates and starts ScheduledExecutorService.
     */
    static {
        cache = new ConcurrentHashMap<>();
        setNewCache();
        createScheduledExecutorService();
    }

    /**
     * Private constructor.
     */
    private Cache() {
    }

    /**
     * Saves object in the cache.
     *
     * @param <T>          a type of key.
     * @param key          a object key in the cache.
     * @param object       a object to save.
     * @param milliseconds a lifetime of objects (milliseconds).
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
     * Saves object in the cache.
     *
     * @param <T>    a type of key.
     * @param key    a object key in the cache.
     * @param object a object to save.
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
     * @param <T>          a type of key.
     * @param key          a object key in the cache.
     * @param object       a object to save.
     * @param seconds      a lifetime of objects (seconds).
     * @param milliseconds a lifetime of objects (milliseconds).
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
     * @param <T>          a type of key.
     * @param key          a object key in the cache.
     * @param object       a object to save.
     * @param minutes      a lifetime of objects (minutes).
     * @param seconds      a lifetime of objects (seconds).
     * @param milliseconds a lifetime of objects (milliseconds).
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
     * @param <T>          a type of key.
     * @param key          a object key in the cache.
     * @param object       a object to save.
     * @param hours        a lifetime of objects (hours).
     * @param minutes      a lifetime of objects (minutes).
     * @param seconds      a lifetime of objects (seconds).
     * @param milliseconds a lifetime of objects (milliseconds).
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
     * Saves object in the cache with default lifetime.
     *
     * @param <T>    a type of key.
     * @param key    a object key in the cache.
     * @param object a object to save.
     * @return The saving object.
     */
    public static <T> Object put(
            final T key,
            final Object object
    ) {
        return put(key, object, -1L);
    }

    /**
     * Saves objects in the cache.
     * Saves objects if map is not empty.
     *
     * @param <T>          a type of key.
     * @param map          a map with objects to save.
     * @param milliseconds a lifetime of objects (milliseconds).
     */
    public static <T> void putAll(
            final Map<T, Object> map,
            final long milliseconds
    ) {
        if (isNotEmptyMap(map)) {
            for (Map.Entry<T, Object> entry : map.entrySet()) {
                put(entry.getKey(), entry.getValue(), milliseconds);
            }
        }
    }

    /**
     * Saves objects in the cache.
     *
     * @param <T>          a type of key.
     * @param map          a map with objects to save.
     * @param seconds      a lifetime of objects (seconds).
     * @param milliseconds a lifetime of objects (milliseconds).
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
     * @param <T>          a type of key.
     * @param map          a map with objects to save.
     * @param minutes      a lifetime of objects (minutes).
     * @param seconds      a lifetime of objects (seconds).
     * @param milliseconds a lifetime of objects (milliseconds).
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
     * @param <T>          a type of key.
     * @param map          a map with objects to save.
     * @param hours        a lifetime of objects (hours).
     * @param minutes      a lifetime of objects (minutes).
     * @param seconds      a lifetime of objects (seconds).
     * @param milliseconds a lifetime of objects (milliseconds).
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
     * Saves objects in the cache with default lifetime..
     *
     * @param <T> a type of key.
     * @param map a map with objects to save.
     */
    public static <T> void putAll(final Map<T, Object> map) {
        final long milliseconds = -1;
        putAll(map, milliseconds);
    }

    /**
     * Returns object from cache with key.
     * Returns null if key is null.
     *
     * @param <T> a type of key.
     * @param key a object key in the cache.
     * @return The object with key or null.
     */
    public static <T> Object get(final T key) {
        Object object = null;
        if (isNotNull(key)) {
            object = get(new Key<>(key));
        }
        return object;
    }

    /**
     * Returns all objects from cache with subKey.
     *
     * @param subKey a object key in the cache.
     * @return The objects with key or empty list.
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
     * @param <T> a type of key.
     * @param key a object key in the cache.
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
     * @param keys a key string.
     */
    public static void removeAll(final String... keys) {
        if (isNotNull(keys) && keys.length > 0) {
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
     * @param <T> a type of key.
     * @param map a map with objects.
     */
    public static <T> void setAll(final Map<T, Object> map) {
        cache = new ConcurrentHashMap<>();
        if (isNotEmptyMap(map)) {
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
     * @param object a objects class to remove.
     */
    public static void clear(final Class object) {
        if (object != null) {
            getCache().entrySet()
                    .stream()
                    .filter(entry -> filterByClass(entry, object))
                    .forEach(entry -> remove(entry.getKey()));
        }
    }

    /**
     * Checks if exist object with the key in the cache.
     *
     * @param <T> a type of key.
     * @param key a object key in the cache.
     * @return true if object is exist, false otherwise.
     */
    public static <T> boolean exist(final T key) {
        return isNotNull(key) && exist(new Key<>(key));
    }

    /**
     * Returns information about objects in cache.
     * Information about cache saved in cache too.
     *
     * @return The maps with entries.
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
     * @param <T> a type of key.
     * @param key a object key in the cache.
     * @return true if object is exist, false otherwise.
     */
    private static <T> boolean exist(final Key<T> key) {
        return isNotNull(key) && cache.containsKey(key);
    }

    /**
     * Returns object from cache with key.
     * Returns null if key is null.
     *
     * @param <T> a type of key.
     * @param key a object key in the cache.
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
     * @param <T> a type of key.
     * @param key a object key in the cache.
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
     * @param key a object key in the cache.
     * @return The maps with entries.
     */
    private static Map<String, String> getNewEntriesToString(final String key) {
        Map<String, String> result = new HashMap<>();
        for (Map.Entry<Key, Object> entry : getCache().entrySet()) {
            result.put(
                    entry.getKey().getKey().toString(),
                    entry.getValue().getClass().getName()
            );
        }
        result.put(key, Map.class.getName());
        return result;
    }

    /**
     * Checks if key contains subKey.
     *
     * @param key    a object key in the cache.
     * @param subKey a object key in the cache.
     * @return true if key contains subKey, false otherwise.
     */
    private static boolean containsKey(final Key key, final String subKey) {
        return key.getKey().toString().contains(subKey);
    }

    /**
     * Filters entry object class with input class.
     *
     * @param entry  a entry to filter.
     * @param object a class to equals.
     * @param <T>    a type of key.
     * @return true if entry class equals to object class, false otherwise.
     */
    private static <T> boolean filterByClass(
            final Map.Entry<T, Object> entry,
            final Class object
    ) {
        return entry.getValue().getClass().equals(object);
    }

    /**
     * Checks if input object is not null.
     *
     * @param object a object to check.
     * @return true if object is not null, false otherwise.
     */
    private static boolean isNotNull(final Object object) {
        return (object != null);
    }

    /**
     * Checks if input map is not empty.
     *
     * @param map a map to check.
     * @return true if map is not empty, false otherwise.
     */
    private static boolean isNotEmptyMap(final Map map) {
        return isNotNull(map) && !map.isEmpty();
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

    /**
     * Creates and configures a new ScheduledExecutorService.
     */
    private static void createScheduledExecutorService() {
        Executors.newSingleThreadScheduledExecutor(
                createThreadFactory()
        ).scheduleAtFixedRate(
                new CacheCleaner(getCache()),
                SCHEDULER_INITIAL_DELAY,
                SCHEDULER_PERIOD,
                TIME_UNIT
        );
    }

    /**
     * Creates a new threads factory for constructing a new thread-demand.
     *
     * @return The new threads factory.
     */
    private static ThreadFactory createThreadFactory() {
        return runnable -> {
            final Thread thread = new Thread(runnable);
            thread.setDaemon(true);
            return thread;
        };
    }
}
