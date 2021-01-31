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
 * @author geNAZt
 * @version 1.0
 */
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
    public void serialize( PacketBuffer buffer, int protocolID ) {
        buffer.writeInt( this.protocol );
        buffer.writeUnsignedVarInt( this.payload.length );
        buffer.writeBytes( this.payload );
    }

    @Override
    public void deserialize( PacketBuffer buffer, int protocolID ) {
        this.protocol = buffer.readInt();
        this.payload = new byte[buffer.readUnsignedVarInt()];
        buffer.readBytes( this.payload );
    }

    public int getProtocol() {
        return this.protocol;
    }

    public void setProtocol(int protocol) {
        this.protocol = protocol;
    }

    public byte[] getPayload() {
        return this.payload;
    }

    public void setPayload(byte[] payload) {
        this.payload = payload;
    }
}
