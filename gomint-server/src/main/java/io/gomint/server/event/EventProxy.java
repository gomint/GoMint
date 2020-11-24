/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.event;

import io.gomint.event.Event;

/**
 * @author geNAZt
 * @version 1.0
 */
public interface EventProxy {

    /**
     * Call this method body for the given event
     * @param event which should be handled by this proxy
     */
    void call( Event event );

}
