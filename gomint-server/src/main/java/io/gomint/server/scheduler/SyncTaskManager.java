/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.scheduler;

import io.gomint.server.GoMintServer;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;

/**
 * @author geNAZt
 * @version 1.0
 */
@RequiredArgsConstructor
public class SyncTaskManager {

    private static final Logger LOGGER = LoggerFactory.getLogger( SyncTaskManager.class );

    @Getter
    private final GoMintServer goMintServer;
    @Getter
    private final long tickLength;
    private final PriorityQueue<SyncScheduledTaskHolder> taskList = new PriorityQueue<>( Comparator.comparingLong( o -> o.execution ) );

    /**
     * Add a new pre configured Task to this scheduler
     *
     * @param task which should be executed
     */
    public void addTask( SyncScheduledTask task ) {
        if ( task.getNextExecution() == -1 ) return;

        synchronized ( this.taskList ) {
            this.taskList.add( new SyncScheduledTaskHolder( task.getNextExecution(), task ) );
        }
    }

    /**
     * Remove a specific task
     *
     * @param task The task which should be removed
     */
    public void removeTask( SyncScheduledTask task ) {
        synchronized ( this.taskList ) {
            this.taskList.remove( new SyncScheduledTaskHolder( -1, task ) );
        }
    }

    /**
     * Update and run all tasks which should be run
     *
     * @param currentMillis The amount of millis when the update started
     * @param dt            The delta time from a full second which has already been calculated
     */
    public void update( long currentMillis, float dt ) {
        synchronized ( this.taskList ) {
            // Iterate over all Tasks until we find some for later ticks
            while ( this.taskList.peek() != null && this.taskList.peek().execution < currentMillis ) {
                SyncScheduledTask task = this.taskList.poll().task;
                if ( task == null ) {
                    return;
                }

                // Check for abort value ( -1 )
                if ( task.getNextExecution() == -1 ) {
                    continue;
                }

                task.run();

                // Reschedule if needed
                if ( task.getNextExecution() > currentMillis ) {
                    this.taskList.add( new SyncScheduledTaskHolder( task.getNextExecution(), task ) );
                }
            }
        }
    }

    @AllArgsConstructor
    @EqualsAndHashCode( of = { "task" } )
    public static class SyncScheduledTaskHolder {
        @Getter private long execution;
        private SyncScheduledTask task;
    }

}
