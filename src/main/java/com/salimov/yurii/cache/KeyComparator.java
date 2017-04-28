package com.salimov.yurii.cache;

import java.util.Comparator;

/**
 * The class implements a set of methods for working
 * with comparators for {@link Key}.
 *
 * @author Yurii Salimov (yuriy.alex.salimov@gmail.com)
 * @version 1.0
 */
final class KeyComparator implements Comparator<Key> {

    /**
     * The class implements a method for working with comparator
     * for {@link Key} by timeout.
     */
    @Override
    public int compare(Key key1, Key key2) {
        int result;
        if (isNull(key1) && isNull(key2)) {
            result = 0;
        } else if (isNull(key1)) {
            result = -1;
        } else if (isNull(key2)) {
            result = 1;
        } else {
            result = (int) (key1.getTimeout() - key2.getTimeout());
        }
        return result;
    }

    /**
     * Checks if input key is null.
     *
     * @param key a key to check.
     * @return true if key is null, false otherwise.
     */
    private static boolean isNull(final Key key) {
        return (key == null);
    }
}
