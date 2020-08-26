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
 * @stability 3
 */
public enum WorldType {

    /**
     * Add a persistent world. This world will be saved on disk.
     */
    PERSISTENT,

    /**
     * Create a new in memory world, this is the fastest way to store and generate a world. This does NOT save chunks to disk.
     * Be sure that you have set the chunk GC to false if you don't want to loose chunks whilst running.
     */
    IN_MEMORY

}
