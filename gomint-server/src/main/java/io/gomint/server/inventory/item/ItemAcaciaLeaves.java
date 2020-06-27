package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:leaves2", id = 161)
public class ItemAcaciaLeaves extends ItemStack implements io.gomint.inventory.item.ItemAcaciaLeaves {

    @Override
    public ItemType getType() {
        return ItemType.ACACIA_LEAVES;
    }

}
