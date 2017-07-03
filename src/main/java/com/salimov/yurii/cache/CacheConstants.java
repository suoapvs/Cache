package com.salimov.yurii.cache;

import java.util.concurrent.TimeUnit;

/**
 * TemporaryCacheImpl constants.
 *
 * @author Yurii Salimov (yuriy.alex.salimov@gmail.com)
 */
interface CacheConstants {

    /**
     * The default lifetime of an objects in the cache (milliseconds).
     * 10 days = (10 d * 24 h * 60 m * 60 s * 1000 ms) ms
     */
    long KEY_TIMEOUT = 10L * 24L * 60L * 60L * 1000L;

    /**
     * The default maximum size of objects which can be stored in the cache.
     */
    int CACHE_MAX_SIZE = 150;

    /**
     * The default cache load factor.
     */
    double CACHE_LOAD_FACTOR = 0.75;

    /**
     * The default cache normal size.
     * <pre>
     *     normal size = max size * default load factor.
     * </pre>
     */
    int CACHE_NORMAL_SIZE = (int) (CACHE_MAX_SIZE * CACHE_LOAD_FACTOR);

    /**
     * The time to delay first execution (5 hours).
     */
    long SCHEDULER_INITIAL_DELAY = 5L;

    /**
     * The period between successive executions (3 hour).
     */
    long SCHEDULER_PERIOD = 3L;

    /**
     * Time unit representing one hour.
     */
    TimeUnit SCHEDULER_TIME_UNIT = TimeUnit.HOURS;
}
