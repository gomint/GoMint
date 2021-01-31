/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.network.packet;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.server.network.Protocol;

/**
 * @author BlackyPaw
 * @version 1.0
 */
public class PacketWorldTime extends Packet {

    private int ticks;

    public PacketWorldTime() {
        super( Protocol.PACKET_WORLD_TIME );
    }

    @Override
    public void serialize( PacketBuffer buffer, int protocolID ) {
        buffer.writeSignedVarInt( this.ticks );
    }

    @Override
    public void deserialize( PacketBuffer buffer, int protocolID ) {
        this.ticks = buffer.readSignedVarInt();
    }

    public int getTicks() {
        return this.ticks;
    }

    public void setTicks(int ticks) {
        this.ticks = ticks;
    }
}
