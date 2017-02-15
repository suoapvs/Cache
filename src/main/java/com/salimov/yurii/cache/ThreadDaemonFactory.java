package com.salimov.yurii.cache;

import java.util.concurrent.ThreadFactory;

/**
 * An object that creates new threads on demand.
 *
 * @author Yurii Salimov (yuriy.alex.salimov@gmail.com)
 * @version 1.0
 * @see Cache
 */
final class ThreadDaemonFactory implements ThreadFactory {

    /**
     * Constructs a new Thread.
     *
     * @param runnable a runnable to be executed by new thread instance.
     * @return constructed thread, or null if the request
     * to create a thread is rejected.
     */
    @Override
    public Thread newThread(final Runnable runnable) {
        final Thread thread = new Thread(runnable);
        thread.setDaemon(true);
        return thread;
    }
}
