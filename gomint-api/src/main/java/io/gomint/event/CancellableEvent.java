/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.event;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author geNAZt
 * @version 1.0
 */
@EqualsAndHashCode( callSuper = false )
@ToString()
public class CancellableEvent extends Event {

    private boolean cancelled = false;

    /**
     * Get the state of the cancelling of this event
     *
     * @return true when cancelled, false when not
     */
    public boolean isCancelled() {
        return this.cancelled;
    }

    /**
     * Set the cancelled state of this event
     *
     * @param cancelled The state of this event
     */
    public void setCancelled( boolean cancelled ) {
        this.cancelled = cancelled;
    }

}
