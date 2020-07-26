package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

@RegisterInfo( id = 467 )
public class ItemHeartOfTheSea extends ItemStack implements io.gomint.inventory.item.ItemHeartOfTheSea {



    @Override
    public ItemType getItemType() {
        return ItemType.HEART_OF_THE_SEA;
    }
}
