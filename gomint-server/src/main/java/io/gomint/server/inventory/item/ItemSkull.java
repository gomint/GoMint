package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.Block;
import io.gomint.world.block.BlockSkull;
import io.gomint.world.block.data.SkullType;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:skull", id = 397 )
public class ItemSkull extends ItemStack implements io.gomint.inventory.item.ItemSkull {

    @Override
    public ItemType getItemType() {
        return ItemType.SKULL;
    }

    @Override
    public SkullType getSkullType() {
        return SkullType.values()[this.getData()];
    }

    @Override
    public void setSkullType(SkullType type) {
        this.setData((short) type.ordinal());
    }

    @Override
    public Block getBlock() {
        BlockSkull block = (BlockSkull) super.getBlock();
        block.type(this.getSkullType());
        return block;
    }

}
