package com.salimov.yurii.cache;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * @author Yurii Salimov (yuriy.alex.salimov@gmail.com)
 */
public class CacheCleanerTest {

    private final static int MAX_SIZE = 20;
    private static final long DEFAULT_TIMEOUT = 3000L;

    private static CacheCleaner cleaner;

    @BeforeClass
    public static void beforeClass() {
        final Map<Key, Object> cache = createCacheMap();
        cleaner = new CacheCleaner(cache, MAX_SIZE);
    }

    @Test
    public void whenAddNegativeMaxSizeThenGetPositiveDefaultSize() {
        final Map<Key, Object> cache = new HashMap<>();
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
        final Map<Key, Object> cache = createCacheMap();
        assertFalse(cache.isEmpty());
    }

    @Test
    public void whenCreateCacheMapThenReturnMapWithNotNullObjects() {
        final Map<Key, Object> map = createCacheMap();
        for (Map.Entry<Key, Object> entry : map.entrySet()) {
            assertNotNull(entry.getKey());
            assertNotNull(entry.getValue());
        }
    }

    private static Map<Key, Object> createCacheMap() {
        final Map<Key, Object> map = new HashMap<>();
        Key key;
        Object object;
        for (int i = 0; i < 2 * MAX_SIZE; i++) {
            object = new Object();
            key = new Key<>(object.toString(), DEFAULT_TIMEOUT);
            map.put(key, object);
        }
        return map;
    }
}