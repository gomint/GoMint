package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

@RegisterInfo( id = 468 )
public class ItemScute extends ItemStack implements io.gomint.inventory.item.ItemScute {



    @Override
    public ItemType getItemType() {
        return ItemType.SCUTE;
    }
}
