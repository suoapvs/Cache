package com.salimov.yurii.cache;

import java.util.*;
import java.util.stream.Collectors;

import static com.salimov.yurii.cache.Validator.isNotEmpty;

/**
 * The class implements a set of methods for checking cache at old objects.
 *
 * @author Yurii Salimov (yuriy.alex.salimov@gmail.com)
 */
final class CacheCleaner implements Runnable {

    /**
     * The default maximum size of objects
     * which can be stored in the cache.
     */
    private final static int DEFAULT_MAX_SIZE = 150;

    /**
     * The maximum size of objects
     * which can be stored in the cache.
     */
    private final int maxSize;

    /**
     * The map where can be stored some objects.
     */
    private final Map<Key, Object> cache;

    /**
     * Constructor.
     *
     * @param cache   the map where can be stored some objects.
     * @param maxSize the maximum size of objects
     *                which can be stored in the cache.
     */
    CacheCleaner(final Map<Key, Object> cache, final int maxSize) {
        this.cache = cache;
        this.maxSize = (maxSize > 0) ? maxSize : DEFAULT_MAX_SIZE;
    }

    /**
     * Constructor.
     *
     * @param cache the map where can be stored some objects.
     */
    CacheCleaner(final Map<Key, Object> cache) {
        this(cache, DEFAULT_MAX_SIZE);
    }

    /**
     * Used to create a EmailSender thread, starting
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
    public int getMaxSize() {
        return this.maxSize;
    }

    /**
     * Removes dead objects from cache.
     */
    private void removeDeadObject() {
        final List<Key> deadKeys = this.cache.keySet()
                .stream()
                .filter(Key::isDead)
                .collect(Collectors.toList());
        if (isNotEmpty(deadKeys)) {
            deadKeys.forEach(this.cache::remove);
        }
    }

    /**
     * Cleans cache when cache.size() great maxSize.
     */
    private void cleanCache() {
        if (isGreatMaxSize()) {
            final List<Key> keys = new ArrayList<>(this.cache.keySet());
            final Comparator<Key> comparator = new KeyComparator();
            Collections.sort(keys, comparator);
            cleanToNormalSize(keys);
        }
    }

    /**
     * Cleans cache. Leaves last maxSize / 2 objects.
     *
     * @param keys the keys list.
     */
    private void cleanToNormalSize(final Collection<Key> keys) {
        for (Key key : keys) {
            if (isNormalSize()) {
                break;
            }
            this.cache.remove(key);
        }
    }

    /**
     * Checks if cache.size() great maxSize.
     *
     * @return true if cache.size() great maxSize, false otherwise.
     */
    private boolean isGreatMaxSize() {
        return (this.cache.size() > this.maxSize);
    }

    /**
     * Checks if cache.size() great normalSize is maxSize / 2.
     *
     * @return true if cache.size() great normalSize, false otherwise.
     */
    private boolean isNormalSize() {
        return (this.cache.size() <= this.maxSize / 2);
    }
}
