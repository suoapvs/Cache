package com.salimov.yurii.cache;

/**
 * The class implements a set of methods
 * for working with Key object in the cache.
 *
 * @param <T> a type of key.
 * @author Yurii Salimov (yuriy.alex.salimov@gmail.com)
 * @version 1.0
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
    private final T key;

    /**
     * The lifetime of object.
     */
    private final long timeout;

    /**
     * Constructor.
     *
     * @param key          a object key in the cache.
     * @param milliseconds a lifetime of objects (milliseconds).
     */
    Key(final T key, final long milliseconds) {
        this.key = key;
        this.timeout = System.currentTimeMillis() +
                (milliseconds > 0 ? milliseconds : DEFAULT_TIMEOUT);
    }

    /**
     * Constructor.
     *
     * @param key a object key in the cache.
     */
    Key(final T key) {
        this(key, DEFAULT_TIMEOUT);
    }

    /**
     * Gets key value.
     *
     * @return The key.
     */
    T getKey() {
        return this.key;
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
     * Returns a object lifetime.
     *
     * @return The lifetime of object.
     */
    long getTimeout() {
        return this.timeout;
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * @param object a reference object with which to compare.
     * @return Returns true if this object is the same
     * as the object argument, otherwise returns false.
     */
    @Override
    public boolean equals(final Object object) {
        boolean result = false;
        if (object != null) {
            if (super.equals(object)) {
                result = true;
            } else if (this.getClass() == object.getClass()) {
                final Key other = (Key) object;
                if (this.key != null) {
                    result = this.key.equals(other.key);
                } else {
                    result = other.key == null;
                }
            }
        }
        return result;
    }

    /**
     * Returns a string representation of the object.
     *
     * @return A string representation of the object.
     */
    @Override
    public String toString() {
        return "Key{" +
                "key=" + this.key +
                ", timeout=" + this.timeout +
                '}';
    }

    /**
     * Returns a hash code value for the object.
     *
     * @return The hash code value for this object.
     */
    @Override
    public int hashCode() {
        return this.key.hashCode();
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
        int result = 0;
        if (object == null) {
            result = -1;
        } else {
            final Key other = (Key) object;
            if (this.timeout < other.timeout) {
                result = 1;
            } else if (this.timeout > other.timeout) {
                result = -1;
            }
        }
        return result;
    }
}
