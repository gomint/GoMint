/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemBrickSlab;
import io.gomint.inventory.item.ItemStack;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockBrickSlab;
import io.gomint.world.block.BlockType;

import java.util.Collections;
import java.util.List;

@RegisterInfo(sId = "minecraft:stone_slab[stone_slab_type=brick]")
public class BrickSlab extends Slab<BlockBrickSlab> implements BlockBrickSlab {

    @Override
    public long breakTime() {
        return 10000;
    }

    @Override
    public BlockType blockType() {
        return BlockType.BRICK_SLAB;
    }

    @Override
    public float blastResistance() {
        return 30.0f;
    }

    @Override
    public List<ItemStack<?>> drops(ItemStack<?> itemInHand) {
        return Collections.singletonList(ItemBrickSlab.create(1));
    }

}
