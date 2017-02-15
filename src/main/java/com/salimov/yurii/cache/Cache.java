package com.salimov.yurii.cache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * The class implements a set of methods for working with cache.
 *
 * @author Yurii Salimov (yuriy.alex.salimov@gmail.com)
 * @version 1.0
 * @see CacheCleaner
 * @see ThreadDaemonFactory
 * @see Key
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
     * Static constructor.
     * Creates and starts ScheduledExecutorService.
     * @see CacheCleaner
     * @see ThreadDaemonFactory
     */
    static {
        cache = new ConcurrentHashMap<>();
        final ScheduledExecutorService scheduler =
                Executors.newSingleThreadScheduledExecutor(
                        new ThreadDaemonFactory()
                );
        scheduler.scheduleAtFixedRate(
                new CacheCleaner(cache),
                SCHEDULER_INITIAL_DELAY,
                SCHEDULER_PERIOD,
                TIME_UNIT
        );
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
        Object savingObject = null;
        if ((key != null) && (object != null)) {
            savingObject = cache.put(
                    new Key<>(key, milliseconds),
                    object
            );
        }
        return savingObject != null ? savingObject : object;
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
        return put(
                key, object,
                minutes + 60 * hours, seconds, milliseconds
        );
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
        if (map != null && !map.isEmpty()) {
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
     * Returns {@code null} if key is {@code null}.
     *
     * @param <T> a type of key.
     * @param key a object key in the cache.
     * @return The object with key or {@code null}.
     */
    public static <T> Object get(final T key) {
        Object object = null;
        if (key != null) {
            object = cache.get(new Key<>(key));
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
        Collection<Object> objects = null;
        if (subKey != null) {
            objects = cache.keySet()
                    .stream()
                    .filter(
                            key -> key.getKey().toString()
                                    .contains(subKey)
                    ).map(key -> cache.get(key))
                    .collect(Collectors.toList());
        }
        return objects;
    }

    /**
     * Removes object from cache with key.
     * Removes object if key is not {@code null}.
     *
     * @param <T> a type of key.
     * @param key a object key in the cache.
     */
    public static <T> void remove(final T key) {
        if (key != null) {
            cache.remove(new Key<>(key));
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
        if (keys != null && keys.length > 0) {
            new Thread(() -> {
                final Collection<Key> cacheKeys = new ArrayList<>(cache.keySet());
                for (String _key : keys) {
                    if (_key != null) {
                        cacheKeys.stream()
                                .filter(
                                        key -> key.getKey()
                                                .toString()
                                                .contains(_key)
                                ).forEach(key -> cache.remove(key));
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
        if (map != null) {
            for (Map.Entry<T, Object> entry : map.entrySet()) {
                put(entry.getKey(), entry.getValue());
            }
        }
    }

    /**
     * Clears the cache.
     */
    public static void clear() {
        cache.clear();
    }

    /**
     * Clears objects by class.
     *
     * @param object a objects class to remove.
     */
    public static void clear(final Class object) {
        if (object != null) {
            cache.entrySet()
                    .stream()
                    .filter(
                            entry -> entry.getValue()
                                    .getClass()
                                    .equals(object)
                    )
                    .forEach(
                            entry -> cache.remove(
                                    entry.getKey()
                            )
                    );
        }
    }

    /**
     * Checks if exist object with the key in the cache.
     *
     * @param <T> a type of key.
     * @param key a object key in the cache.
     * @return {@code true} if object is exist,
     * {@code false} otherwise.
     */
    public static <T> boolean exist(final T key) {
        return cache.containsKey(new Key<>(key));
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
        if ((result == null) || (result.size() != cache.size())) {
            result = getNewEntriesToString(key);
            put(key, result);
        }
        return result;
    }

    /**
     * Returns information about objects in cache.
     *
     * @param key a object key in the cache.
     * @return The maps with entries.
     */
    private static Map<String, String> getNewEntriesToString(final String key) {
        Map<String, String> result = new HashMap<>();
        for (Map.Entry<Key, Object> entry : cache.entrySet()) {
            result.put(
                    entry.getKey().getKey().toString(),
                    entry.getValue().getClass().getName()
            );
        }
        result.put(key, Map.class.getName());
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
}
