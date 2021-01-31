/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.network.packet;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.server.network.Protocol;

public class PacketNetworkSettings extends Packet {

    private short compressionThreshold;

    public PacketNetworkSettings() {
        super(Protocol.PACKET_NETWORK_SETTINGS);
    }

    @Override
    public void serialize(PacketBuffer buffer, int protocolID) throws Exception {
        buffer.writeLShort(this.compressionThreshold);
    }

    @Override
    public void deserialize(PacketBuffer buffer, int protocolID) throws Exception {
        this.compressionThreshold = buffer.readLShort();
    }

    public short getCompressionThreshold() {
        return this.compressionThreshold;
    }

    public void setCompressionThreshold(short compressionThreshold) {
        this.compressionThreshold = compressionThreshold;
    }
}
