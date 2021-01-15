package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:colored_torch_bp", id = 204)
public class ItemBlueTorch extends ItemStack< io.gomint.inventory.item.ItemBlueTorch> implements io.gomint.inventory.item.ItemBlueTorch {

    @Override
    public ItemType itemType() {
        return ItemType.BLUE_TORCH;
    }
}
