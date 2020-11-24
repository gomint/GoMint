/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.util.tick;

/**
 * @author geNAZt
 * @version 1.0
 */
public interface Tickable {

    /**
     * Ticks the wanted implementation. This gets called multiple times a second depending on TPS settings
     *
     * @param currentTimeMS The current time in milliseconds. Used to reduce the number of calls to System#currentTimeMillis()
     * @param dT            The delta from the full second which has been calculated in the last tick
     */
    void update(long currentTimeMS, float dT);

}
