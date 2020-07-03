/*
 * Copyright (c) 2018 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.network.packet;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.inventory.item.ItemStack;
import io.gomint.server.network.Protocol;
import lombok.Data;

/**
 * @author geNAZt
 * @version 1.0
 */
@Data
public class PacketCreativeContent extends Packet {

    private ItemStack[] items;

    public PacketCreativeContent() {
        super(Protocol.PACKET_CREATIVE_CONTENT);
    }

    @Override
    public void serialize(PacketBuffer buffer, int protocolID) throws Exception {
        buffer.writeUnsignedVarInt(this.items.length);

        for (ItemStack item : this.items) {
            io.gomint.server.inventory.item.ItemStack serverItem = (io.gomint.server.inventory.item.ItemStack) item;
            buffer.writeUnsignedVarInt(serverItem.getID());
            writeItemStack(item, buffer);
        }
    }

    @Override
    public void deserialize(PacketBuffer buffer, int protocolID) throws Exception {

    }
}
