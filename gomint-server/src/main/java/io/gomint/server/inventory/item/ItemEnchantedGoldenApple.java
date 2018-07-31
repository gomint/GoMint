package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 466 )
public class ItemEnchantedGoldenApple extends ItemFood implements io.gomint.inventory.item.ItemEnchantedGoldenApple {



    @Override
    public float getSaturation() {
        return 1.2f;
    }

    @Override
    public float getHunger() {
        return 4;
    }

    @Override
    public ItemType getType() {
        return ItemType.ENCHANTED_GOLDEN_APPLE;
    }

}
