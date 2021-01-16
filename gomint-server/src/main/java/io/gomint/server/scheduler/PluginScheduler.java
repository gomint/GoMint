/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.scheduler;

import io.gomint.plugin.Plugin;
import io.gomint.scheduler.Scheduler;
import io.gomint.scheduler.Task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author geNAZt
 * @version 1.0
 */
public class PluginScheduler implements Scheduler {

    private Plugin plugin;
    private CoreScheduler coreScheduler;

    private Set<Task> runningTasks = Collections.synchronizedSet( new HashSet<>() );

    /**
     * Create a new scheduler for the given plugin
     *
     * @param plugin        for which this scheduler is
     * @param coreScheduler which schedules tasks inside gomint
     */
    public PluginScheduler( Plugin plugin, CoreScheduler coreScheduler ) {
        this.plugin = plugin;
        this.coreScheduler = coreScheduler;
    }

    @Override
    public Task executeAsync( Runnable runnable ) {
        if ( this.coreScheduler == null ) {
            throw new IllegalStateException( "This PluginScheduler has been cleaned and closed. No new Tasks can be scheduled" );
        }

        Task task = coreScheduler.executeAsync( runnable );
        task.onException( e -> {
            plugin.logger().warn( "A task thrown a Exception", e );
            return true;
        } );

        task.onComplete( () -> runningTasks.remove( task ) );

        this.runningTasks.add( task );
        return task;
    }

    @Override
    public Task scheduleAsync( Runnable runnable, long delay, TimeUnit timeUnit ) {
        if ( this.coreScheduler == null ) {
            throw new IllegalStateException( "This PluginScheduler has been cleaned and closed. No new Tasks can be scheduled" );
        }

        Task task = coreScheduler.scheduleAsync( runnable, delay, timeUnit );
        task.onException( e -> {
            plugin.logger().warn( "A task thrown a Exception", e );
            return true;
        } );

        task.onComplete( () -> runningTasks.remove( task ) );

        this.runningTasks.add( task );
        return task;
    }

    @Override
    public Task scheduleAsync( Runnable runnable, long delay, long period, TimeUnit timeUnit ) {
        if ( this.coreScheduler == null ) {
            throw new IllegalStateException( "This PluginScheduler has been cleaned and closed. No new Tasks can be scheduled" );
        }

        Task task = coreScheduler.scheduleAsync( runnable, delay, period, timeUnit );
        task.onException( e -> {
            plugin.logger().warn( "A task thrown a Exception", e );
            return true;
        } );

        task.onComplete( () -> runningTasks.remove( task ) );

        this.runningTasks.add( task );
        return task;
    }

    @Override
    public Task execute( Runnable runnable ) {
        if ( this.coreScheduler == null ) {
            throw new IllegalStateException( "This PluginScheduler has been cleaned and closed. No new Tasks can be scheduled" );
        }

        Task task = coreScheduler.execute( runnable );
        task.onException( e -> {
            plugin.logger().warn( "A task thrown a Exception", e );
            return true;
        } );

        task.onComplete( () -> runningTasks.remove( task ) );

        this.runningTasks.add( task );
        return task;
    }

    @Override
    public Task schedule( Runnable runnable, long delay, TimeUnit timeUnit ) {
        if ( this.coreScheduler == null ) {
            throw new IllegalStateException( "This PluginScheduler has been cleaned and closed. No new Tasks can be scheduled" );
        }

        Task task = coreScheduler.schedule( runnable, delay, timeUnit );
        task.onException( e -> {
            plugin.logger().warn( "A task thrown a Exception", e );
            return true;
        } );

        task.onComplete( () -> runningTasks.remove( task ) );

        this.runningTasks.add( task );
        return task;
    }

    @Override
    public Task schedule( Runnable runnable, long delay, long period, TimeUnit timeUnit ) {
        if ( this.coreScheduler == null ) {
            throw new IllegalStateException( "This PluginScheduler has been cleaned and closed. No new Tasks can be scheduled" );
        }

        Task task = coreScheduler.schedule( runnable, delay, period, timeUnit );
        task.onException( e -> {
            plugin.logger().warn( "A task thrown a Exception", e );
            return true;
        } );

        task.onComplete( () -> runningTasks.remove( task ) );

        this.runningTasks.add( task );
        return task;
    }

    /**
     * Internal Method for cleaning up all Tasks
     */
    public void cleanup() {
        for ( Task runningTask : new ArrayList<>( this.runningTasks ) ) {
            runningTask.cancel();
        }

        this.runningTasks.clear();
        this.plugin = null;
        this.coreScheduler = null;
    }

}
