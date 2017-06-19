package com.salimov.yurii.cache;

import static com.salimov.yurii.cache.Validator.isNotNull;
import static com.salimov.yurii.cache.Validator.isNull;

/**
 * The class implements a set of methods
 * for working with Key object in the cache.
 *
 * @param <T> a type of key.
 * @author Yurii Salimov (yuriy.alex.salimov@gmail.com)
 */
final class Key<T> implements Comparable {

    /**
     * The default lifetime of objects (milliseconds).
     * 864000000 milliseconds = 10 days
     */
    private static final long DEFAULT_TIMEOUT = 864000000L;

    /**
     * Value of the Key.
     */
    private final T value;

    /**
     * The lifetime of object.
     */
    private final long timeout;

    /**
     * Constructor.
     *
     * @param value        the object key in the cache.
     * @param milliseconds the lifetime of objects (milliseconds).
     */
    Key(final T value, final long milliseconds) {
        this.value = value;
        this.timeout = System.currentTimeMillis() +
                (milliseconds > 0 ? milliseconds : DEFAULT_TIMEOUT);
    }

    /**
     * Constructor.
     *
     * @param value the object key in the cache.
     */
    Key(final T value) {
        this(value, DEFAULT_TIMEOUT);
    }

    /**
     * Checks whether the object is dead.
     *
     * @return Returns true if object is dead, otherwise returns false.
     */
    boolean isDead() {
        return (System.currentTimeMillis() > this.timeout);
    }

    /**
     * Checks whether the object is alive.
     *
     * @return Returns true if object is alive, otherwise returns false.
     */
    boolean isLive() {
        return !isDead();
    }

    /**
     * Gets key value.
     *
     * @return The value.
     */
    T getValue() {
        return this.value;
    }

    /**
     * Returns a object lifetime.
     *
     * @return The lifetime of object.
     */
    long getTimeout() {
        return this.timeout;
    }

    /**
     * Returns a string representation of the object.
     *
     * @return A string representation of the object.
     */
    @Override
    public String toString() {
        return "Key{" +
                "value=" + this.value +
                ", timeout=" + this.timeout +
                '}';
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * @param object the reference object with which to compare.
     * @return Returns true if this object is the same
     * as the object argument, otherwise returns false.
     */
    @Override
    public boolean equals(final Object object) {
        boolean result = false;
        if (isNotNull(object)) {
            if (super.equals(object)) {
                result = true;
            } else if (this.getClass() == object.getClass()) {
                final Key other = (Key) object;
                if (isNotNull(this.value)) {
                    result = this.value.equals(other.value);
                } else {
                    result = isNull(other.value);
                }
            }
        }
        return result;
    }

    /**
     * Returns a hash code value for the object.
     *
     * @return The hash code value for this object.
     */
    @Override
    public int hashCode() {
        return this.value.hashCode();
    }

    /**
     * Compares this object with the specified object for order.
     *
     * @param object a object to be compared.
     * @return A negative integer, zero, or a positive
     * integer as this object is less than, equal to,
     * or greater than the specified object.
     */
    @Override
    public int compareTo(final Object object) {
        int result;
        if (isNull(object)) {
            result = -1;
        } else {
            final Key other = (Key) object;
            result = (int) (other.timeout - this.timeout);
        }
        return result;
    }
}
