/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.network.packet;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.server.network.Protocol;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author BlackyPaw
 * @version 1.0
 */
@Data
@EqualsAndHashCode( callSuper = false )
public class PacketBatch extends Packet {

    private boolean compressed;

    private ByteBuf payload;

    public PacketBatch() {
        super( Protocol.PACKET_BATCH );
    }

    @Override
    public void serialize( PacketBuffer buffer, int protocolID ) {
        buffer.writeBytes( this.payload );
    }

    @Override
    public void deserialize( PacketBuffer buffer, int protocolID ) {
        this.payload = buffer.getBuffer();
    }

    @Override
    public void serializeHeader( PacketBuffer buffer ) {
        buffer.writeByte( this.getId() );
    }

}
