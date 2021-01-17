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
@RegisterInfo( sId = "minecraft:skull" )
public class ItemSkull extends ItemStack< io.gomint.inventory.item.ItemSkull> implements io.gomint.inventory.item.ItemSkull {

    @Override
    public ItemType itemType() {
        return ItemType.SKULL;
    }

    @Override
    public SkullType type() {
        return SkullType.values()[this.data()];
    }

    @Override
    public ItemSkull type(SkullType type) {
        this.data((short) type.ordinal());
        return this;
    }

    @Override
    public Block block() {
        BlockSkull block = (BlockSkull) super.block();
        block.type(this.type());
        return block;
    }

}
