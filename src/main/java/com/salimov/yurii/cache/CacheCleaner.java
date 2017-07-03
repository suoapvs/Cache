package com.salimov.yurii.cache;

import java.util.*;

/**
 * The class implements a set of methods for checking cache at old objects.
 *
 * @author Yurii Salimov (yuriy.alex.salimov@gmail.com)
 */
final class CacheCleaner implements Runnable {

    /**
     * The maximum size of objects
     * which can be stored in the cache.
     */
    private final int maxSize;

    /**
     * The map where can be stored some objects.
     */
    private final TemporaryCache cache;

    /**
     * Constructor.
     *
     * @param cache   the map where can be stored some objects.
     * @param maxSize the maximum size of objects
     *                which can be stored in the cache.
     */
    CacheCleaner(final TemporaryCache cache, final int maxSize) {
        this.cache = cache;
        this.maxSize = (maxSize > 0) ? maxSize : CacheConstants.CACHE_MAX_SIZE;
    }

    /**
     * Constructor.
     *
     * @param cache the map where can be stored some objects.
     */
    CacheCleaner(final TemporaryCacheImpl cache) {
        this(cache, 0);
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
    int getMaxSize() {
        return this.maxSize;
    }

    /**
     * Removes dead objects from cache.
     */
    private void removeDeadObject() {
        final Set<Key> keySet = this.cache.keySet();
        final Iterator<Key> iterator = keySet.iterator();
        Key key;
        while (iterator.hasNext()) {
            key = iterator.next();
            if (key.isDead()) {
                iterator.remove();
            }
        }
    }

    /**
     * Cleans cache when cache.size() great maxSize.
     */
    private void cleanCache() {
        if (isGreatMaxSize()) {
            final List<Key> keys = getSortedKeys();
            cleanToNormalSize(keys);
        }
    }

    /**
     * Sorted and return the cache keys.
     *
     * @return The lit of the sorted keys.
     */
    private List<Key> getSortedKeys() {
        final List<Key> keys = new ArrayList<>(this.cache.keySet());
        final Comparator<Key> comparator = new KeyComparator();
        Collections.sort(keys, comparator);
        return keys;
    }

    /**
     * Cleans cache.
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
        return (this.cache.getSize() > this.maxSize);
    }

    /**
     * Checks if cache.size() great then normal size.
     * normal size = max size * default load factor.
     *
     * @return true if cache.size() great normalSize, false otherwise.
     */
    private boolean isNormalSize() {
        return (this.cache.getSize() <= CacheConstants.CACHE_NORMAL_SIZE);
    }
}
