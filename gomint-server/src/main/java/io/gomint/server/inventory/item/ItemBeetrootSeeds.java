package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 458 )
public class ItemBeetrootSeeds extends ItemStack implements io.gomint.inventory.item.ItemBeetrootSeeds {



    @Override
    public int getBlockId() {
        return 244;
    }

    @Override
    public ItemType getType() {
        return ItemType.BEETROOT_SEEDS;
    }

}
