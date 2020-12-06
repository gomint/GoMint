/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.network.packet;

import io.gomint.inventory.item.ItemStack;
import io.gomint.jraknet.PacketBuffer;
import io.gomint.server.network.Protocol;

/**
 * @author geNAZt
 * @version 1.0
 */
public class PacketCreativeContent extends Packet {

    private ItemStack[] items;

    public PacketCreativeContent() {
        super(Protocol.PACKET_CREATIVE_CONTENT);
    }

    @Override
    public void serialize(PacketBuffer buffer, int protocolID) {
        buffer.writeUnsignedVarInt(this.items.length);
        for (ItemStack item : this.items) {
            buffer.writeUnsignedVarInt(((io.gomint.server.inventory.item.ItemStack) item).getStackId());
            writeItemStack(item, buffer);
        }
    }

    @Override
    public void deserialize(PacketBuffer buffer, int protocolID) throws Exception {

    }

    public ItemStack[] getItems() {
        return items;
    }

    public void setItems(ItemStack[] items) {
        this.items = items;
    }

}
