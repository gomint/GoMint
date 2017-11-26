/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.scheduler;

import io.gomint.server.GoMintServer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private final TaskList<SyncScheduledTask> taskList = new TaskList<>();

    /**
     * Add a new pre configured Task to this scheduler
     *
     * @param task which should be executed
     */
    public void addTask( SyncScheduledTask task ) {
        if ( task.getNextExecution() == -1 ) return;

        synchronized ( this.taskList ) {
            this.taskList.add( task.getNextExecution(), task );
        }
    }

    /**
     * Remove a specific task
     *
     * @param task The task which should be removed
     */
    public void removeTask( SyncScheduledTask task ) {
        synchronized ( this.taskList ) {
            this.taskList.remove( task );
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
            while ( this.taskList.getNextTaskTime() < currentMillis ) {
                SyncScheduledTask task = this.taskList.getNextElement();
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
                    this.taskList.add( task.getNextExecution(), task );
                }
            }
        }
    }

}
