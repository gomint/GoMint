package io.gomint.server.world;

/**
 * @author geNAZt
 * @version 1.0
 */
public enum UpdateReason {

    /**
     * This reason means the block has been selected by a random number
     */
    RANDOM,

    /**
     * This reason means that the block wanted to
     */
    SCHEDULED,

    /**
     * This reason mean that any of the surrounding blocks did update
     */
    NEIGHBOUR_UPDATE,

    /**
     * A neighbour block exploded
     */
    EXPLOSION,

    /**
     * A block has been placed
     */
    BLOCK_ADDED

}
