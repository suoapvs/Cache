package com.salimov.yurii.cache;

import java.util.Collection;
import java.util.Map;

/**
 * The class implements a set of methods for validating an incoming object.
 *
 * @author Yurii Salimov (yuriy.alex.salimov@gmail.com)
 */
final class Validator {

    /**
     * Private Constructor.
     */
    private Validator() {
    }

    /**
     * Checks if a Object is null.
     * <pre>
     *     isNull(null) = true
     *     isNull(new Object()) = false
     * </pre>
     *
     * @param object the Object to check, may be null
     * @return true if the Object is null, false otherwise.
     */
    static boolean isNull(final Object object) {
        return (object == null);
    }

    /**
     * Checks if a Object is not null.
     * <pre>
     *     isNotNull(null) = false
     *     isNotNull(new Object()) = true
     * </pre>
     *
     * @param object the Object to check, may be null
     * @return true if the Object is not null, false otherwise.
     */
    static boolean isNotNull(final Object object) {
        return !isNull(object);
    }

    /**
     * Checks if a Collection is not null and not empty.
     * <pre>
     *     isNotEmpty(null) = false
     *     isNotEmpty(new ArrayList()) = false
     *
     *     Collection collection = new ArrayList();
     *     collection.add(new Object);
     *     isNotEmpty(collection) = true
     * </pre>
     *
     * @param collection the Collection to check, may be null
     * @return true if the Collection is not null and not empty,
     * false otherwise.
     */
    static boolean isNotEmpty(final Collection collection) {
        return isNotNull(collection) && !collection.isEmpty();
    }

    /**
     * Checks if a Map is not empty or not null.
     * <pre>
     *     isNotEmpty(null) = false
     *     isNotEmpty(new HashMap()) = false
     *
     *     Map map = new HashMap();
     *     map.put(new Object, new Object);
     *     isNotEmpty(map) = true
     * </pre>
     *
     * @param map the Map to check, may be null
     * @return true if the Map is not empty or not null,
     * false otherwise.
     */
    static boolean isNotEmpty(final Map map) {
        return isNotNull(map) && !map.isEmpty();
    }

    /**
     * Checks if a CharSequence is not empty (""), not null and not whitespace only.
     * <pre>
     *     isNotEmpty(null) = false
     *     isNotEmpty("") = false
     *     isNotEmpty(" ") = false
     *     isNotEmpty("bob") = true
     *     isNotEmpty("  bob  ") = true
     * </pre>
     *
     * @param string the CharSequence to check, may be null
     * @return true if the CharSequence is not empty and not null
     * and not whitespace, false otherwise.
     */
    static boolean isNotEmpty(final String string) {
        return isNotNull(string) && !string.equals("");
    }

    /**
     * Checks if a array is not null and not empty.
     * <pre>
     *     isNotEmpty(null) = false
     *     isNotEmpty(new Object[]{}) = false
     *     isNotEmpty(new Object[]{new Object()}) = true
     *     isNotEmpty(new Object[]{new Object(), new Object()}) = true
     * </pre>
     *
     * @param array the array to check, may be null
     * @param <T>   extends the Object class.
     * @return true if the array is not null and not empty,
     * false otherwise.
     */
    static <T> boolean isNotEmpty(final T[] array) {
        return isNotNull(array) && (array.length > 0);
    }
}
