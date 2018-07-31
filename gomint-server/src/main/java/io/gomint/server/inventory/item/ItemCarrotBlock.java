package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 141 )
public class ItemCarrotBlock extends ItemStack implements io.gomint.inventory.item.ItemCarrotBlock {



    @Override
    public ItemType getType() {
        return ItemType.CARROTS;
    }

}
