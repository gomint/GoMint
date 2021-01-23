/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.Block;
import io.gomint.world.block.BlockBrickSlab;

@RegisterInfo(sId = "minecraft:double_stone_slab[4]")
public class ItemBrickSlab extends ItemSlab<io.gomint.inventory.item.ItemBrickSlab> implements io.gomint.inventory.item.ItemBrickSlab {

    @Override
    public ItemType itemType() {
        return ItemType.BRICK_SLAB;
    }

    @Override
    public Block block() {
        return this.blocks
            .get(BlockBrickSlab.class);
    }

}
