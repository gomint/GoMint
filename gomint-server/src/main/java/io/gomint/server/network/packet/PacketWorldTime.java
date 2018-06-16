/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.network.packet;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.server.network.Protocol;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author BlackyPaw
 * @version 1.0
 */
@Data
@EqualsAndHashCode( callSuper = false )
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

}
