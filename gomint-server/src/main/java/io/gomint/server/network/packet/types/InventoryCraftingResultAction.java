/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.network.packet.types;

import io.gomint.inventory.item.ItemStack;
import io.gomint.jraknet.PacketBuffer;
import io.gomint.server.network.packet.Packet;

import java.util.Arrays;

/**
 * @author geNAZt
 */
public class InventoryCraftingResultAction implements InventoryAction {

    private ItemStack[] resultItems;
    private byte amount;

    @Override
    public void deserialize(PacketBuffer buffer, int protocolID) throws Exception {
        this.resultItems = Packet.readItemStacks(buffer);
        this.amount = buffer.readByte();
    }

    @Override
    public int weight() {
        return 9;
    }

    @Override
    public String toString() {
        return "{\"_class\":\"InventoryCraftingResultAction\", " +
            "\"resultItems\":" + Arrays.toString(resultItems) + ", " +
            "\"amount\":\"" + amount + "\"" +
            "}";
    }

    public byte getAmount() {
        return amount;
    }

}
