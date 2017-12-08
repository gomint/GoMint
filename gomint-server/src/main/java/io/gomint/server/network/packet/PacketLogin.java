/*
 * Copyright (c) 2016, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.network.packet;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.server.network.Protocol;
import lombok.Getter;

/**
 * @author geNAZt
 * @version 1.0
 */
@Getter
public class PacketLogin extends Packet {

    private int protocol;
    private byte[] payload;

    /**
     * Construct a new login packet which contains all data to login into a MC:PE server
     */
    public PacketLogin() {
        super( Protocol.PACKET_LOGIN );
    }

    @Override
    public void serialize( PacketBuffer buffer ) {

    }

    @Override
    public void deserialize( PacketBuffer buffer ) {
        this.protocol = buffer.readInt();
        this.payload = new byte[buffer.readUnsignedVarInt()];
        buffer.readBytes( this.payload );
    }

}
