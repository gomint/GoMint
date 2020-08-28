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

/**
 * @author geNAZt
 * @version 1.0
 */
public class PacketCreativeContent extends Packet {

    private ItemStack[] items;
    private PacketBuffer cache = null;

    public PacketCreativeContent() {
        super(Protocol.PACKET_CREATIVE_CONTENT);
    }

    @Override
    public void serialize(PacketBuffer buffer, int protocolID) {
        if (this.cache == null) {
            this.cache();
        }

        buffer.writeBytes(this.cache.getBuffer().asReadOnly());
    }

    private void cache() {
        this.cache = new PacketBuffer(1024);
        writeItemStacksWithIDs(this.items, this.cache);
    }

    @Override
    public void deserialize(PacketBuffer buffer, int protocolID) throws Exception {

    }

    public ItemStack[] getItems() {
        return items;
    }

    public void setItems(ItemStack[] items) {
        this.items = items;

        if (this.cache != null) {
            this.cache.release();
            this.cache = null;
        }
    }

}
