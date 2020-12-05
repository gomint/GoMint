/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.network.packet.types;

import io.gomint.jraknet.PacketBuffer;

public class InventoryTransferAction implements InventoryAction {

    private final boolean hasAmount;
    private final boolean hasDestination;

    private byte amount;
    private ItemStackRequestSlotInfo source;
    private ItemStackRequestSlotInfo destination;

    public InventoryTransferAction(boolean hasAmount, boolean hasDestination) {
        this.hasAmount = hasAmount;
        this.hasDestination = hasDestination;
    }

    @Override
    public void deserialize(PacketBuffer buffer, int protocolID) throws Exception {
        if (this.hasAmount) {
            this.amount = buffer.readByte();
        }

        this.source = new ItemStackRequestSlotInfo();
        this.source.deserialize(buffer, protocolID);

        if (this.hasDestination) {
            this.destination = new ItemStackRequestSlotInfo();
            this.destination.deserialize(buffer, protocolID);
        }
    }

    public byte getAmount() {
        return amount;
    }

    public boolean hasAmount() {
        return hasAmount;
    }

    public ItemStackRequestSlotInfo getSource() {
        return source;
    }

    public ItemStackRequestSlotInfo getDestination() {
        return destination;
    }

    public boolean hasDestination() {
        return hasDestination;
    }

    @Override
    public String toString() {
        return "{\"InventoryTransferAction\":{"
            + "\"hasAmount\":\"" + hasAmount + "\""
            + ", \"hasDestination\":\"" + hasDestination + "\""
            + ", \"amount\":\"" + amount + "\""
            + ", \"source\":" + source
            + ", \"destination\":" + destination
            + "}}";
    }

}
