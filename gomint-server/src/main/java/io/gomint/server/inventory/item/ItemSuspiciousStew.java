package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author KingAli
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:suspicious_stew", id = 734 )
public class ItemSuspiciousStew extends ItemFood<io.gomint.inventory.item.ItemSuspiciousStew> implements io.gomint.inventory.item.ItemSuspiciousStew {

    @Override
    public byte maximumAmount() {
        return 1;
    }

    @Override
    public float getSaturation() {
        return (float) 7.2;
    }

    @Override
    public float getHunger() {
        return 6;
    }

    @Override
    public ItemType itemType() {
        return ItemType.SUSPICIOUSSTEW;
    }
}
