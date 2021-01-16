package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.Block;
import io.gomint.world.block.BlockTNT;
import io.gomint.world.block.data.TNTType;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:tnt", id = 46)
public class ItemTNT extends ItemStack< io.gomint.inventory.item.ItemTNT> implements io.gomint.inventory.item.ItemTNT {

    @Override
    public ItemType itemType() {
        return ItemType.TNT;
    }

    @Override
    public Block block() {
        BlockTNT tnt = (BlockTNT) super.block();
        tnt.type(TNTType.NORMAL);
        return tnt;
    }

}
