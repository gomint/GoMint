package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.Block;
import io.gomint.world.block.BlockPrismarineWall;
import io.gomint.world.block.BlockWall;
import io.gomint.world.block.data.StoneType;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:cobblestone_wall[11]", def = true)
public class ItemPrismarineWall extends ItemStack< io.gomint.inventory.item.ItemPrismarineWall> implements io.gomint.inventory.item.ItemPrismarineWall {

    @Override
    public ItemType itemType() {
        return ItemType.PRISMARINE_WALL;
    }

    @Override
    public Block block() {
        return this.blocks.get(BlockPrismarineWall.class);
    }

}
