package com.salimov.yurii.cache;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * The class implements a set of methods for creating and starting
 * a new ScheduledExecutorService for working with the incoming command.
 *
 * @author Yurii Salimov (yuriy.alex.salimov@gmail.com)
 */
final class CacheScheduledExecutor {

    /**
     * The time to delay first execution (5 hours).
     */
    private final static long SCHEDULER_INITIAL_DELAY = 5L;

    /**
     * The period between successive executions (3 hour).
     */
    private final static long SCHEDULER_PERIOD = 3L;

    /**
     * Time unit representing one hour.
     */
    private final static TimeUnit TIME_UNIT = TimeUnit.HOURS;

    /**
     * The task to execute.
     */
    private final Runnable command;

    /**
     * Constructor.
     *
     * @param command the task to execute
     */
    CacheScheduledExecutor(final Runnable command) {
        this.command = command;
    }

    /**
     * Creates and starts a new ScheduledExecutorService.
     */
    public void go() {
        final ScheduledExecutorService service = createScheduledExecutorService();
        scheduleAtFixedRate(service);
    }

    /**
     * Configures the incoming ScheduledExecutorService
     * with the default parameters.
     *
     * @param service the instance of the ScheduledExecutorService class.
     */
    private void scheduleAtFixedRate(final ScheduledExecutorService service) {
        service.scheduleAtFixedRate(
                this.command,
                SCHEDULER_INITIAL_DELAY,
                SCHEDULER_PERIOD,
                TIME_UNIT
        );
    }

    /**
     * Creates a new ScheduledExecutorService.
     *
     * @return the instance of the ScheduledExecutorService class (newer null).
     */
    private ScheduledExecutorService createScheduledExecutorService() {
        final ThreadFactory threadFactory = createThreadFactory();
        return Executors.newSingleThreadScheduledExecutor(threadFactory);
    }

    /**
     * Creates a new threads factory for constructing a new thread-demand.
     *
     * @return The new threads factory (newer null).
     */
    private ThreadFactory createThreadFactory() {
        return runnable -> {
            final Thread thread = new Thread(runnable);
            thread.setDaemon(true);
            return thread;
        };
    }
}
