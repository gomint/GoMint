/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.world.block;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 2
 */
public interface BlockEnderChest extends Block {

    /**
     * Set a custom name for this container
     *
     * @param customName which should be used
     */
    void setCustomName( String customName );

    /**
     * Get the custom name of this container
     *
     * @return custom name of this container
     */
    String getCustomName();

}
