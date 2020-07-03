package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author KingAli
 * @version 1.0
 */
@RegisterInfo(id = 542, sId = "minecraft:twisting_vines")
public class ItemTwistingVines extends ItemStack implements io.gomint.inventory.item.ItemTwistingVines {

    @Override
    public String getBlockId() {
        return "minecraft:twisting_vines";
    }

    @Override
    public ItemType getType() {
        return ItemType.TWISTING_VINES;
    }
}
