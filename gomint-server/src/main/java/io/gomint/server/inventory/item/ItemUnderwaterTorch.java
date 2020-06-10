package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:underwater_torch", id = 239)
public class ItemUnderwaterTorch extends ItemStack implements io.gomint.inventory.item.ItemUnderwaterTorch {

    @Override
    public ItemType getType() {
        return ItemType.UNDERWATER_TORCH;
    }
}
