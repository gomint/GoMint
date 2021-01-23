package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.Block;
import io.gomint.world.block.BlockBrickWall;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:cobblestone_wall[6]")
public class ItemBrickWall extends ItemStack<io.gomint.inventory.item.ItemBrickWall> implements io.gomint.inventory.item.ItemBrickWall {

    @Override
    public ItemType itemType() {
        return ItemType.BRICK_WALL;
    }

    @Override
    public Block block() {
        return this.blocks
            .get(BlockBrickWall.class);
    }

}
