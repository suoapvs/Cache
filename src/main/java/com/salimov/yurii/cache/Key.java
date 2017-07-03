package com.salimov.yurii.cache;

import static com.salimov.yurii.cache.CacheConstants.KEY_TIMEOUT;

/**
 * The class implements a set of methods
 * for working with Key object in the cache.
 *
 * @author Yurii Salimov (yuriy.alex.salimov@gmail.com)
 */
final class Key implements Comparable {

    /**
     * Value of the Key.
     */
    private final Object value;

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
    Key(final Object value, final long milliseconds) {
        this.value = value;
        this.timeout = System.currentTimeMillis() +
                (milliseconds > 0 ? milliseconds : KEY_TIMEOUT);
    }

    /**
     * Constructor.
     *
     * @param value the object key in the cache.
     */
    Key(final Object value) {
        this(value, 0);
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
        if (Validator.isNotNull(object)) {
            if (super.equals(object)) {
                result = true;
            } else if (this.getClass() == object.getClass()) {
                final Key other = (Key) object;
                if (Validator.isNotNull(this.value)) {
                    result = this.value.equals(other.value);
                } else {
                    result = Validator.isNull(other.value);
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
        if (Validator.isNull(object)) {
            result = -1;
        } else {
            final Key other = (Key) object;
            result = (int) (other.timeout - this.timeout);
        }
        return result;
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
    Object getValue() {
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
}
