/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.network.packet.types;

import io.gomint.jraknet.PacketBuffer;

public class ItemStackRequestSlotInfo {

    private byte windowId;
    private byte slot;
    private int itemStackId;

    public void deserialize(PacketBuffer buffer, int protocolID) throws Exception {
        this.windowId = buffer.readByte();
        this.slot = buffer.readByte();
        this.itemStackId = buffer.readSignedVarInt();
    }

    public byte getWindowId() {
        return this.windowId;
    }

    public byte getSlot() {
        return this.slot;
    }

    public int getItemStackId() {
        return this.itemStackId;
    }

    @Override
    public String toString() {
        return "{\"_class\":\"ItemStackRequestSlotInfo\", " +
            "\"windowId\":\"" + this.windowId + "\"" + ", " +
            "\"slot\":\"" + this.slot + "\"" + ", " +
            "\"itemStackId\":\"" + this.itemStackId + "\"" +
            "}";
    }

}
