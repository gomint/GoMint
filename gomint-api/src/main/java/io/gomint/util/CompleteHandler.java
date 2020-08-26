package io.gomint.util;

/**
 * @author geNAZt
 * @version 1.0
 *
 * Interface which should be used when a task has been completed.
 * @stability 3
 */
public interface CompleteHandler {

    /**
     * Called when the task has been completed.
     */
    void onComplete();

}
