package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author KingAli
 * @version 1.0
 */
@RegisterInfo(id = 523, sId = "minecraft:soul_torch")
public class ItemSoulTorch extends ItemStack implements io.gomint.inventory.item.ItemSoulTorch {

    @Override
    public String getBlockId() {
        return "minecraft:soul_torch";
    }

    @Override
    public ItemType getType() {
        return ItemType.SOUL_TORCH;
    }
}
