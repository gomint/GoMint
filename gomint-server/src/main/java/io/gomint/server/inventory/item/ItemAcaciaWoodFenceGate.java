package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 187 )
public class ItemAcaciaWoodFenceGate extends ItemStack implements io.gomint.inventory.item.ItemAcaciaWoodFenceGate {



    @Override
    public ItemType getItemType() {
        return ItemType.ACACIA_FENCE_GATE;
    }

}
