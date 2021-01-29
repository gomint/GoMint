/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */
package io.gomint.event.world;

import io.gomint.world.World;

/**
 * Event is called when a world is loaded. Event is called in world's thread.
 * @author Janmm14
 * @version 2.0
 * @stability 2
 */
public class WorldLoadEvent extends SimpleWorldEvent {

    /**
     * Create a event for announcing world loading
     *
     * @param world for which this event is
     */
    public WorldLoadEvent(World world) {
        super(world);
    }

}
