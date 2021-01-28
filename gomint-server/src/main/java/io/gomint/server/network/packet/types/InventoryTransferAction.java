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
        return this.amount;
    }

    public boolean hasAmount() {
        return this.hasAmount;
    }

    public ItemStackRequestSlotInfo getSource() {
        return this.source;
    }

    public ItemStackRequestSlotInfo getDestination() {
        return this.destination;
    }

    public boolean hasDestination() {
        return this.hasDestination;
    }

    @Override
    public int weight() {
        return 5;
    }

    @Override
    public String toString() {
        return "{\"InventoryTransferAction\":{"
            + "\"hasAmount\":\"" + this.hasAmount + "\""
            + ", \"hasDestination\":\"" + this.hasDestination + "\""
            + ", \"amount\":\"" + this.amount + "\""
            + ", \"source\":" + this.source
            + ", \"destination\":" + this.destination
            + "}}";
    }

}
