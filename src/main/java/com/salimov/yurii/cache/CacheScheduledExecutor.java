package com.salimov.yurii.cache;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;

import static com.salimov.yurii.cache.CacheConstants.*;

/**
 * The class implements a set of methods for creating and starting
 * a new ScheduledExecutorService for working with the incoming command.
 *
 * @author Yurii Salimov (yuriy.alex.salimov@gmail.com)
 */
final class CacheScheduledExecutor {

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
    void go() {
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
                SCHEDULER_TIME_UNIT
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
