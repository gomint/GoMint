/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.network.packet;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.server.network.Protocol;

public class PacketItemComponent extends Packet {

    public PacketItemComponent() {
        super(Protocol.PACKET_ITEM_COMPONENT);
    }

    @Override
    public void serialize(PacketBuffer buffer, int protocolID) throws Exception {
        buffer.writeUnsignedVarInt(0);
        // string -> compound tag
    }

    @Override
    public void deserialize(PacketBuffer buffer, int protocolID) throws Exception {

    }

}
