package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 202 )
public class ItemRedTorch extends ItemStack implements io.gomint.inventory.item.ItemRedTorch {

    @Override
    public String getBlockId() {
        return "minecraft:colored_torch_rg";
    }

    @Override
    public ItemType getType() {
        return ItemType.RED_TORCH;
    }
}
