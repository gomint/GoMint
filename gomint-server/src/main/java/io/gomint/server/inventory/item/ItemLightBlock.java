/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.Block;
import io.gomint.world.block.BlockLightBlock;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:light_block")
public class ItemLightBlock extends ItemStack< io.gomint.inventory.item.ItemLightBlock> implements io.gomint.inventory.item.ItemLightBlock {

    private static final float DIVIDER = 1f / 15;

    @Override
    public ItemType itemType() {
        return ItemType.LIGHT_BLOCK;
    }

    @Override
    public float intensity() {
        return this.data() * DIVIDER;
    }

    @Override
    public ItemLightBlock intensity(float intensity) {
        this.data((short) (intensity * 15));
        return this;
    }

    @Override
    public Block block() {
        BlockLightBlock block = (BlockLightBlock) super.block();
        block.intensity(this.intensity());
        return block;
    }

}
