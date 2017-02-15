package com.salimov.yurii.cache;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * The class implements a set of methods for checking cache at old objects.
 *
 * @author Yurii Salimov (yuriy.alex.salimov@gmail.com)
 * @version 1.0
 * @see Cache
 * @see Key
 */
final class CacheCleaner implements Runnable {

    /**
     * The default maximum size of objects
     * which can be stored in the cache.
     */
    private final static int DEFAULT_MAX_SIZE = 100;

    /**
     * The maximum size of objects
     * which can be stored in the cache.
     */
    private int maxSize;

    /**
     * The map where can be stored some objects.
     */
    private final Map<Key, Object> cache;

    /**
     * Constructor.
     *
     * @param cache   a map where can be stored some objects.
     * @param maxSize a maximum size of objects
     *                which can be stored in the cache.
     */
    CacheCleaner(
            final Map<Key, Object> cache,
            final int maxSize
    ) {
        this.cache = cache;
        setMaxSize(maxSize);
    }

    /**
     * Constructor.
     *
     * @param cache a map where can be stored some objects.
     */
    CacheCleaner(final Map<Key, Object> cache) {
        this(cache, DEFAULT_MAX_SIZE);
    }

    /**
     * Used to create a SenderImpl thread, starting
     * the thread causes the object's run method
     * to be called in that separately executing thread.
     */
    @Override
    public void run() {
        removeDeadObject();
        cleanCache();
    }

    /**
     * Gets maximum size of objects which
     * can be stored in the cache.
     *
     * @return The maximum size of objects
     * which can be stored in the cache.
     */
    int getMaxSize() {
        return this.maxSize;
    }

    /**
     * Sets maximum size of objects which
     * can be stored in the cache.
     *
     * @param maxSize a maximum size of objects which
     *                can be stored in the cache.
     */
    void setMaxSize(final int maxSize) {
        this.maxSize = maxSize > 0 ? maxSize : DEFAULT_MAX_SIZE;
    }

    /**
     * Removes dead objects from cache.
     */
    private void removeDeadObject() {
        this.cache.keySet()
                .stream()
                .filter(Key::isDead)
                .forEach(cache::remove);
    }

    /**
     * Cleans cache when cache.size() great maxSize.
     */
    private void cleanCache() {
        if (this.cache.size() > getMaxSize()) {
            final List<Key> keys = new ArrayList<>(
                    this.cache.keySet()
            );
            Collections.sort(keys, new KeyComparator());
            cleanToNormalSize(keys);
        }
    }

    /**
     * Cleans cache. Leaves last maxSize / 2 objects.
     *
     * @param keys a keys list.
     */
    private void cleanToNormalSize(final List<Key> keys) {
        final int normalSize = getMaxSize() / 2;
        for (Key key : keys) {
            this.cache.remove(key);
            if (this.cache.size() <= normalSize) {
                break;
            }
        }
    }
}
