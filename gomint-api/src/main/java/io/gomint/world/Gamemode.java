/*
 * Copyright (c) 2018 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.world;

/**
 * @author geNAZt
 * @version 1.0
 */
public enum Gamemode {

    /**
     * Survival mode can destroy blocks, attack mobs and other players like normal. They also can't fly or clip through
     * blocks
     */
    SURVIVAL,

    /**
     * Creative mode allows flying and instant breaking blocks
     */
    CREATIVE,

    /**
     * Adventure removes hitboxes so you can't hit mobs / players or interact with certain blocks. They also can't fly
     * or clip through blocks
     */
    ADVENTURE,

    /**
     * Spectator removed the ability to break blocks or hit mobs / players. This also allows flying and clipping through
     * blocks
     */
    SPECTATOR

}
