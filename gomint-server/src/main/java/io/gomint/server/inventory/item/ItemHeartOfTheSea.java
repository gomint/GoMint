package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

@RegisterInfo( sId = "minecraft:heart_of_the_sea", id = 467 )
public class ItemHeartOfTheSea extends ItemStack< io.gomint.inventory.item.ItemHeartOfTheSea> implements io.gomint.inventory.item.ItemHeartOfTheSea {



    @Override
    public ItemType itemType() {
        return ItemType.HEART_OF_THE_SEA;
    }
}
