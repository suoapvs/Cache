package com.salimov.yurii.cache;

import java.util.Comparator;

import static com.salimov.yurii.cache.Validator.isNull;

/**
 * The class implements a set of methods for working
 * with comparators for {@link Key}.
 *
 * @author Yurii Salimov (yuriy.alex.salimov@gmail.com)
 */
final class KeyComparator implements Comparator<Key> {

    /**
     * Compares two keys.
     * <pre>
     *     compare(null, null) = 0
     *     compare(null, new Key()) = -1
     *     compare(new Key(), null) = 1
     *     compare(new Key(), new Key()) = compares it
     * </pre>
     *
     * @param first  the first key to be compared.
     * @param second the second key to be compared.
     * @return A negative integer, zero, or a positive integer as the
     * first key is less than, equal to, or greater than the
     * second key.
     */
    @Override
    public int compare(final Key first, final Key second) {
        int result;
        if (isNull(first) && isNull(second)) {
            result = 0;
        } else if (isNull(first)) {
            result = -1;
        } else if (isNull(second)) {
            result = 1;
        } else {
            result = first.compareTo(second);
        }
        return result;
    }
}
