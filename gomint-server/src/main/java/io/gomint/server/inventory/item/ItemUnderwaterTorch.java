package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:underwater_torch", id = 239)
public class ItemUnderwaterTorch extends ItemStack< io.gomint.inventory.item.ItemUnderwaterTorch> implements io.gomint.inventory.item.ItemUnderwaterTorch {

    @Override
    public ItemType itemType() {
        return ItemType.UNDERWATER_TORCH;
    }
}
