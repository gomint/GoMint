/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.jraknet.PacketBuffer;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:shield", id = 513 )
public class ItemShield extends ItemStack< io.gomint.inventory.item.ItemShield> implements io.gomint.inventory.item.ItemShield {

    @Override
    public ItemType itemType() {
        return ItemType.SHIELD;
    }

    @Override
    public void readAdditionalData(PacketBuffer buffer) {
        buffer.readSignedVarLong();
    }

    @Override
    public void writeAdditionalData(PacketBuffer buffer) {
        buffer.writeSignedVarLong(0);
    }

}
