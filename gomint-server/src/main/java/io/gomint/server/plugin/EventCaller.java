/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.plugin;

import io.gomint.event.Event;

/**
 * @author geNAZt
 * @version 1.0
 */
public interface EventCaller {

    /**
     * Call out a event. This will give it to all handlers attached and return it once its done.
     *
     * @param event The event which should be handled
     * @param <T>   The type of event which we handle
     * @return the handled and changed event
     */
    <T extends Event> T callEvent( T event );

}
