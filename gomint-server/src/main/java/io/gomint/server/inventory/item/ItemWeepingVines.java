package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author KingAli
 * @version 1.0
 */
@RegisterInfo(id = 486, sId = "minecraft:weeping_vines")
public class ItemWeepingVines extends ItemStack implements io.gomint.inventory.item.ItemWeepingVines {

    @Override
    public String getBlockId() {
        return "minecraft:weeping_vines";
    }

    @Override
    public ItemType getType() {
        return ItemType.WEEPING_VINES;
    }
}
