/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.world.block;

import io.gomint.world.block.data.ElementType;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 1
 */
public interface BlockElement extends Block {

    /**
     * Get the type of element this block holds
     *
     * @return type of element
     */
    ElementType type();

    /**
     * Set type of element for this block
     *
     * @param type of element
     * @return block for chaining
     */
    BlockElement type(ElementType type);

}
