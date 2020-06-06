package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = -212, sId = "minecraft:wood" )
public class ItemWood extends ItemStack implements io.gomint.inventory.item.ItemWood {

    @Override
    public long getBurnTime() {
        return 15000;
    }

    @Override
    public String getBlockId() {
        return "minecraft:wood";
    }

    @Override
    public ItemType getType() {
        return ItemType.WOOD;
    }

    // TODO: Add types

}
