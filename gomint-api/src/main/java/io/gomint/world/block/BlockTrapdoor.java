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
 * @stability 3
 */
public interface BlockTrapdoor<B> extends BlockDirection<B> {

    /**
     * Is the trapdoor open or closed?
     *
     * @return true when the trapdoor is open, false when not
     */
    boolean open();

    /**
     * Open or close a trapdoor. The target state depends on the {@link #open()} state
     *
     * @return block for chaining
     */
    B toggle();

}
