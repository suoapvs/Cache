package com.salimov.yurii.cache;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public final class CacheTest {

    private final static long TIME;
    private final static String KEY;
    private final static Object OBJECT;
    private final static Map<String, Object> OBJECTS;

    static {
        TIME = 1000L;
        KEY = "key";
        OBJECT = new Object();
        OBJECTS = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            OBJECTS.put(KEY + i, OBJECT);
        }
    }

    @Test
    public void whenPutWithMilliseconds() {
        Cache.put(KEY, OBJECT, TIME);
        assertNotNull(Cache.get(KEY));
        Cache.remove(KEY);
        assertNull(Cache.get(KEY));
    }

    @Test
    public void whenPutWithSecondsAndMilliseconds() {
        Cache.put(KEY, OBJECT, TIME, TIME);
        assertNotNull(Cache.get(KEY));
        Cache.remove(KEY);
        assertNull(Cache.get(KEY));
    }

    @Test
    public void whenPutWithMinutesAndSecondsAndMilliseconds() {
        Cache.put(KEY, OBJECT, TIME, TIME, TIME);
        assertNotNull(Cache.get(KEY));
        Cache.remove(KEY);
        assertNull(Cache.get(KEY));
    }

    @Test
    public void whenPutWithHoursAndMinutesAndSecondsAndMilliseconds() {
        Cache.put(KEY, OBJECT, TIME, TIME, TIME, TIME);
        assertNotNull(Cache.get(KEY));
        Cache.remove(KEY);
        assertNull(Cache.get(KEY));
    }

    @Test
    public void putTest() {
        Cache.put(KEY, OBJECT);
        assertNotNull(Cache.get(KEY));
        Cache.clear();
        assertNull(Cache.get(KEY));
    }

    @Test
    public void whenPutAllWithMilliseconds() {
        Cache.putAll(OBJECTS, TIME);
        for (int i = 0; i < 10; i++) {
            assertNotNull(Cache.get(KEY + i));
        }
        Cache.clear();
    }

    @Test
    public void whenPutAllWithSecondsAndMilliseconds() {
        Cache.putAll(OBJECTS, TIME, TIME);
        for (int i = 0; i < 10; i++) {
            assertNotNull(Cache.get(KEY + i));
        }
        Cache.clear();
    }

    @Test
    public void whenPutAllWithMinutesAndSecondsAndMilliseconds() {
        Cache.putAll(OBJECTS, TIME, TIME, TIME);
        for (int i = 0; i < 10; i++) {
            assertNotNull(Cache.get(KEY + i));
        }
        Cache.clear();
    }

    @Test
    public void whenPutAllWithHoursAndMinutesAndSecondsAndMilliseconds() {
        Cache.putAll(OBJECTS, TIME, TIME, TIME, TIME);
        for (int i = 0; i < 10; i++) {
            assertNotNull(Cache.get(KEY + i));
        }
        Cache.clear();
    }

    @Test
    public void putAllTest() {
        Cache.putAll(OBJECTS);
        for (int i = 0; i < 10; i++) {
            assertNotNull(Cache.get(KEY + i));
        }
        Cache.clear();
    }

    @Test
    public void setAllTest() {
        Cache.setAll(OBJECTS);
        for (int i = 0; i < 10; i++) {
            assertNotNull(Cache.get(KEY + i));
        }
        Cache.clear();
    }

    @Test
    public void whenGetEntriesToString() {
        Cache.clear();
        Cache.put(KEY + 1, OBJECT);
        assertNotNull(Cache.getEntriesToString());
        Cache.put(KEY + 2, OBJECT);
        assertNotNull(Cache.getEntriesToString());
    }

    @Test
    public void getEntriesToString() {
        Cache.clear();
        assertNotNull(Cache.getEntriesToString());
        assertEquals(Cache.getEntriesToString().size(), 1);

        final Map<String, Object> map = new HashMap<>();

        Cache.putAll(map);
        assertNotNull(Cache.getEntriesToString());
        assertEquals(Cache.getEntriesToString().size(), 1);

        final String key = "key";
        for (int i = 0; i < 10; i++) {
            map.put(key + i, new Object());
            assertNull(Cache.get(key + i));
        }

        Cache.putAll(map);
        assertNotNull(Cache.getEntriesToString());
        assertTrue(!Cache.getEntriesToString().isEmpty());

        for (int i = 0; i < 10; i++) {
            assertNotNull(Cache.get(key + i));
        }
    }

    @Test
    public void whenGetByNullKeyThenReturnNull() {
        assertNull(Cache.get(null));
    }

    @Test
    public void whenGetByBlankKeyThenReturnNull() {
        assertNull(Cache.get(""));
        assertNull(Cache.get("   "));
    }

    @Test
    public void whenGetAllByNullKeyThenReturnEmptyCollection() {
        assertTrue(Cache.getAll(null).isEmpty());
    }

    @Test
    public void whenGetAllByBlankKeyThenReturnEmptyCollection() {
        assertTrue(Cache.getAll("").isEmpty());
        assertTrue(Cache.getAll("").isEmpty());
    }

    @Test
    public void whenRemoveByNullKeyThenDoNothing() {
        Cache.remove(null);
    }

    @Test
    public void whenRemoveByBlankKeyThenDoNothing() {
        Cache.remove("");
        Cache.remove(" ");
        Cache.remove("   ");
    }

    @Test
    public void whenRemoveAllByNullKeyThenDoNothing() {
        Cache.removeAll((String) null);
    }

    @Test
    public void whenRemoveAllByBlankKeyThenDoNothing() {
        Cache.removeAll("", "");
        Cache.removeAll(" ", " ");
        Cache.removeAll("   ", "   ");
    }

    @Test
    public void whenSetAllNullMap() {
        Cache.setAll(null);
    }

    @Test
    public void whenSetAllEmptyMap() {
        Cache.setAll(new HashMap<>());
    }

    @Test
    public void whenClearByNullClassThenDoNothing() {
        Cache.clear(null);
    }

    @Test
    public void whenClearByNullClass() {
        Cache.clear(Object.class);
    }

    @Test
    public void whenExistByNullKeyThenReturnFalse() {
        assertFalse(Cache.exist(null));
    }

    @Test
    public void whenGetSizeThenReturnCacheSize() {
        assertNotNull(Cache.getSize());
    }
}
