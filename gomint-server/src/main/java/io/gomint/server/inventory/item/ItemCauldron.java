package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.Block;
import io.gomint.world.block.BlockCauldron;
import io.gomint.world.block.data.LiquidType;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:cauldron", id = 380 )
public class ItemCauldron extends ItemStack implements io.gomint.inventory.item.ItemCauldron {

    @Override
    public ItemType getItemType() {
        return ItemType.CAULDRON;
    }

    @Override
    public Block getBlock() {
        BlockCauldron block = (BlockCauldron) super.getBlock();
        return block
            .fillHeight(0f)
            .type(LiquidType.WATER);
    }

}
