package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author KingAli
 * @version 1.0
 */
@RegisterInfo( id = 734 )
public class ItemSuspiciousStew extends ItemFood {

    @Override
    public byte getMaximumAmount() {
        return 1;
    }

    @Override
    public float getSaturation() {
        return 0;
    }

    @Override
    public float getHunger() {
        return 6;
    }

    @Override
    public ItemType getType() {
        return ItemType.SUSPICIOUSSTEW;
    }
}
