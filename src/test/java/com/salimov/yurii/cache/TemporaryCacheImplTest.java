package com.salimov.yurii.cache;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public final class TemporaryCacheImplTest {

    private static long TIME;
    private static String KEY;
    private static Object OBJECT;
    private static Map<Object, Object> OBJECTS;
    private static TemporaryCache cache;
    
    @BeforeClass
    public static void before() {
        TIME = 1000L;
        KEY = "key";
        OBJECT = new Object();
        OBJECTS = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            OBJECTS.put(KEY + i, OBJECT);
        }
        cache = Cache.getTemporaryCache();
    }

    @Test
    public void whenPutWithMilliseconds() {
        cache.put(KEY, OBJECT, TIME);
        assertNotNull(cache.get(KEY));
        cache.remove(KEY);
        assertNull(cache.get(KEY));
    }

    @Test
    public void whenPutWithSecondsAndMilliseconds() {
        cache.put(KEY, OBJECT, TIME, TIME);
        assertNotNull(cache.get(KEY));
        cache.remove(KEY);
        assertNull(cache.get(KEY));
    }

    @Test
    public void whenPutWithMinutesAndSecondsAndMilliseconds() {
        cache.put(KEY, OBJECT, TIME, TIME, TIME);
        assertNotNull(cache.get(KEY));
        cache.remove(KEY);
        assertNull(cache.get(KEY));
    }

    @Test
    public void whenPutWithHoursAndMinutesAndSecondsAndMilliseconds() {
        cache.put(KEY, OBJECT, TIME, TIME, TIME, TIME);
        assertNotNull(cache.get(KEY));
        cache.remove(KEY);
        assertNull(cache.get(KEY));
    }

    @Test
    public void putTest() {
        cache.put(KEY, OBJECT);
        assertNotNull(cache.get(KEY));
        cache.clear();
        assertNull(cache.get(KEY));
    }

    @Test
    public void whenPutAllWithMilliseconds() {
        cache.putAll(OBJECTS, TIME);
        for (int i = 0; i < 10; i++) {
            assertNotNull(cache.get(KEY + i));
        }
        cache.clear();
    }

    @Test
    public void whenPutAllWithSecondsAndMilliseconds() {
        cache.putAll(OBJECTS, TIME, TIME);
        for (int i = 0; i < 10; i++) {
            assertNotNull(cache.get(KEY + i));
        }
        cache.clear();
    }

    @Test
    public void whenPutAllWithMinutesAndSecondsAndMilliseconds() {
        cache.putAll(OBJECTS, TIME, TIME, TIME);
        for (int i = 0; i < 10; i++) {
            assertNotNull(cache.get(KEY + i));
        }
        cache.clear();
    }

    @Test
    public void whenPutAllWithHoursAndMinutesAndSecondsAndMilliseconds() {
        cache.putAll(OBJECTS, TIME, TIME, TIME, TIME);
        for (int i = 0; i < 10; i++) {
            assertNotNull(cache.get(KEY + i));
        }
        cache.clear();
    }

    @Test
    public void putAllTest() {
        cache.putAll(OBJECTS);
        for (int i = 0; i < 10; i++) {
            assertNotNull(cache.get(KEY + i));
        }
        cache.clear();
    }

    @Test
    public void setAllTest() {
        cache.setAll(OBJECTS);
        for (int i = 0; i < 10; i++) {
            assertNotNull(cache.get(KEY + i));
        }
        cache.clear();
    }

    @Test
    public void whenGetEntriesToString() {
        cache.clear();
        cache.put(KEY + 1, OBJECT);
        assertNotNull(cache.getEntriesToString());
        cache.put(KEY + 2, OBJECT);
        assertNotNull(cache.getEntriesToString());
    }

    @Test
    public void getEntriesToString() {
        cache.clear();
        assertNotNull(cache.getEntriesToString());
        assertEquals(cache.getEntriesToString().size(), 1);

        final Map<Object, Object> map = new HashMap<>();

        cache.putAll(map);
        assertNotNull(cache.getEntriesToString());
        assertEquals(cache.getEntriesToString().size(), 1);

        final String key = "key";
        for (int i = 0; i < 10; i++) {
            map.put(key + i, new Object());
            assertNull(cache.get(key + i));
        }

        cache.putAll(map);
        assertNotNull(cache.getEntriesToString());
        assertTrue(!cache.getEntriesToString().isEmpty());

        for (int i = 0; i < 10; i++) {
            assertNotNull(cache.get(key + i));
        }
    }

    @Test
    public void whenGetByNullKeyThenReturnNull() {
        assertNull(cache.get(null));
    }

    @Test
    public void whenGetByBlankKeyThenReturnNull() {
        assertNull(cache.get(""));
        assertNull(cache.get("   "));
    }

    @Test
    public void whenRemoveByNullKeyThenDoNothing() {
        cache.remove((Key) null);
    }

    @Test
    public void whenRemoveByBlankKeyThenDoNothing() {
        cache.remove("");
        cache.remove(" ");
        cache.remove("   ");
    }

    @Test
    public void whenRemoveAllByNullKeyThenDoNothing() {
        cache.removeAll(null);
    }

    @Test
    public void whenSetAllNullMap() {
        cache.setAll(null);
    }

    @Test
    public void whenSetAllEmptyMap() {
        cache.setAll(new HashMap<>());
    }

    @Test
    public void whenClearByNullClassThenDoNothing() {
        cache.remove((Class) null);
    }

    @Test
    public void whenClearByNotNullClass() {
        cache.remove(Object.class);
    }

    @Test
    public void whenExistByNullKeyThenReturnFalse() {
        assertFalse(cache.exist(null));
    }

    @Test
    public void whenGetSizeThenReturnCacheSize() {
        assertNotNull(cache.getSize());
    }
}
