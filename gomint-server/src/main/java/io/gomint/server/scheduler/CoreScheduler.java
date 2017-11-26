/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.scheduler;

import io.gomint.scheduler.Scheduler;
import io.gomint.scheduler.Task;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author geNAZt
 * @version 1.0
 */
@RequiredArgsConstructor
public class CoreScheduler implements Scheduler {

    private final ExecutorService executorService;
    private final SyncTaskManager syncTaskManager;

    @Override
    public Task executeAsync( Runnable runnable ) {
        return this.scheduleAsync( runnable, 0, TimeUnit.MILLISECONDS );
    }

    @Override
    public Task scheduleAsync( Runnable runnable, long delay, TimeUnit timeUnit ) {
        return this.scheduleAsync( runnable, delay, -1, timeUnit );
    }

    @Override
    public Task scheduleAsync( Runnable runnable, long delay, long period, TimeUnit timeUnit ) {
        AsyncScheduledTask task = new AsyncScheduledTask( runnable, delay, period, timeUnit );
        executorService.execute( task );
        return task;
    }

    @Override
    public Task execute( Runnable runnable ) {
        return this.schedule( runnable, 0, TimeUnit.MILLISECONDS );
    }

    @Override
    public Task schedule( Runnable runnable, long delay, TimeUnit timeUnit ) {
        return this.schedule( runnable, delay, -1, timeUnit );
    }

    @Override
    public Task schedule( Runnable runnable, long delay, long period, TimeUnit timeUnit ) {
        SyncScheduledTask task = new SyncScheduledTask( this.syncTaskManager, runnable, delay, period, timeUnit );
        this.syncTaskManager.addTask( task );
        return task;
    }

}
