package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:command_block", id = 137 )
public class ItemCommandBlock extends ItemStack<ItemCommandBlock> {

    @Override
    public ItemType itemType() {
        return ItemType.COMMAND_BLOCK;
    }

}
