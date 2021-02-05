/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.network.packet.types;

import io.gomint.jraknet.PacketBuffer;

public class InventoryDropAction implements InventoryAction {

    private byte amount;
    private ItemStackRequestSlotInfo source;
    private boolean random;

    @Override
    public void deserialize(PacketBuffer buffer, int protocolID) throws Exception {
        this.amount = buffer.readByte();
        this.source = new ItemStackRequestSlotInfo();
        this.source.deserialize(buffer, protocolID);
        this.random = buffer.readBoolean();
    }

    @Override
    public int weight() {
        return 5;
    }

    public byte getAmount() {
        return this.amount;
    }

    public ItemStackRequestSlotInfo getSource() {
        return this.source;
    }

    public boolean isRandom() {
        return this.random;
    }

    @Override
    public String toString() {
        return "InventoryDropAction{" +
            "amount=" + this.amount +
            ", source=" + this.source +
            ", random=" + this.random +
            '}';
    }

}
