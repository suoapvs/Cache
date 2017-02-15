package com.salimov.yurii.cache;

import java.util.Comparator;

/**
 * The class implements a set of methods for working
 * with comparators for {@link Key}.
 *
 * @author Yurii Salimov (yuriy.alex.salimov@gmail.com)
 * @version 1.0
 * @see Key
 */
final class KeyComparator implements Comparator<Key> {

    /**
     * The class implements a method for working with comparator
     * for {@link Key} by timeout.
     *
     * @see Key
     */
    @Override
    public int compare(Key key1, Key key2) {
        int result;
        if (key1 == null && key2 == null) {
            result = 0;
        } else if (key1 == null) {
            result = -1;
        } else if (key2 == null) {
            result = 1;
        } else {
            result = (int) (key1.getTimeout() - key2.getTimeout());
        }
        return result;
    }
}
