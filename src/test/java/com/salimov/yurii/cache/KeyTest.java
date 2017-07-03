package com.salimov.yurii.cache;

import com.salimov.yurii.cache.temporary.Key;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Yurii Salimov (yuriy.alex.salimov@gmail.com)
 */
public class KeyTest {

    private final static String ANY_STRING = "Some string...";
    private static final long DEFAULT_TIMEOUT = 1000L;

    @Test
    public void whenCallConstructorWithOneParameter() {
        final Key key = new Key(ANY_STRING);
        final Object keyValue = key.getValue();
        assertEquals(keyValue, ANY_STRING);
    }

    @Test
    public void whenCallConstructorWithTwoParameter() {
        final Key key = new Key(ANY_STRING, DEFAULT_TIMEOUT);
        final Object keyValue = key.getValue();
        assertEquals(keyValue, ANY_STRING);
    }

    @Test
    public void whenSetSmallTimeOutThenKeyIsDead() {
        final Key key = new Key(ANY_STRING, 1);
        sleepOneSecond();
        final boolean deaden = key.isDead();
        assertTrue(deaden);
        final boolean lived = key.isLive();
        assertFalse(lived);
    }

    @Test
    public void whenSetBigTimeOutThenKeyIsDead() {
        final Key key = new Key(ANY_STRING, 100 * DEFAULT_TIMEOUT);
        final boolean deaden = key.isDead();
        assertFalse(deaden);
        final boolean lived = key.isLive();
        assertTrue(lived);
    }

    @Test
    public void toStringTest() {
        final Key key = new Key(ANY_STRING);
        final String testString = "Key{" +
                "value=" + key.getValue() +
                ", timeout=" + key.getTimeout() +
                '}';
        assertEquals(key.toString(), testString);
    }

    @Test
    public void equalsWithNull() throws Exception {
        final Key key = new Key(ANY_STRING);
        assertFalse(key.equals(null));
    }

    @Test
    public void equalsWithObjectOfTheAnotherClass() throws Exception {
        final Key key = new Key(ANY_STRING);
        final String string = "Hello World!";
        assertFalse(key.equals(string));
    }

    @Test
    public void equalsTwoObjects() throws Exception {
        final Key first = new Key(ANY_STRING);
        final Key second = new Key(ANY_STRING);
        for (int i = 0; i < 5; i++) {
            assertTrue(first.equals(second));
            assertTrue(second.equals(first));
        }
    }

    @Test
    public void equalsThreeObjects() throws Exception {
        final Key first = new Key(ANY_STRING);
        final Key second = new Key(ANY_STRING);
        final Key third = new Key(ANY_STRING);
        for (int i = 0; i < 5; i++) {
            assertTrue(first.equals(second));
            assertTrue(second.equals(third));
            assertTrue(third.equals(first));
        }
    }

    @Test
    public void hashCodeTest() throws Exception {
        final Key key = new Key(ANY_STRING);
        int hashCode = key.hashCode();
        for (int i = 0; i < 5; i++) {
            int temp = key.hashCode();
            assertEquals(temp, hashCode);
            hashCode = temp;
        }
    }

    @Test
    public void whenCompareNullThenReturnNegativeOne() {
        final Key key = new Key(ANY_STRING);
        final int result = key.compareTo(null);
        assertTrue(result == -1);
    }

    @Test
    public void whenCompareThenReturnSomeValue() {
        final Key first = new Key(ANY_STRING);
        final Key second = new Key(ANY_STRING);
        final int result = first.compareTo(second);
        assertTrue(result != -1);
    }

    private void sleepOneSecond() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}