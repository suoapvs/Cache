package com.salimov.yurii.cache;

import com.salimov.yurii.cache.temporary.CacheCleaner;
import com.salimov.yurii.cache.temporary.Key;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Yurii Salimov (yuriy.alex.salimov@gmail.com)
 */
public class CacheCleanerTest {

    private final static int MAX_SIZE = 20;
    private static final long DEFAULT_TIMEOUT = 3000L;

    private static CacheCleaner cleaner;

    @BeforeClass
    public static void beforeClass() {
        final TemporaryCache cache = createCache();
        cleaner = new CacheCleaner(cache, MAX_SIZE);
    }

    @Test
    public void whenAddNegativeMaxSizeThenGetPositiveDefaultSize() {
        final TemporaryCache cache = createCache();
        final CacheCleaner cleaner = new CacheCleaner(cache, -MAX_SIZE);
        final int size = cleaner.getMaxSize();
        assertTrue(size > 0);
    }

    @Test
    public void whenRunThenDoIt() {
        cleaner.run();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        cleaner.run();
    }

    @Test
    public void whenGetMaxSizeThenReturnValidNumber() {
        final int size = cleaner.getMaxSize();
        assertTrue(size == MAX_SIZE);
    }

    @Test
    public void whenCreateCacheMapThenReturnNotEmptyMap() {
        final TemporaryCache cache = createCache();
        assertFalse(cache.isEmpty());
    }

    private static TemporaryCache createCache() {
        final TemporaryCache cache = Cache.getTemporaryCache();
        Key key;
        Object object;
        for (int i = 0; i < 2 * MAX_SIZE; i++) {
            object = new Object();
            key = new Key(object.toString(), DEFAULT_TIMEOUT);
            cache.put(key, object);
        }
        return cache;
    }
}