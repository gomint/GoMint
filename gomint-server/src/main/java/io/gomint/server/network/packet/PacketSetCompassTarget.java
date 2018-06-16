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
 * @author geNAZt
 * @version 1.0
 */
@Data
@EqualsAndHashCode( callSuper = false )
public class PacketSetCompassTarget extends Packet {

    private int x;
    private int y;
    private int z;

    public PacketSetCompassTarget() {
        super( Protocol.PACKET_SET_COMPASS_TARGET );
    }

    @Override
    public void serialize( PacketBuffer buffer, int protocolID ) {
        buffer.writeInt( this.x );
        buffer.writeInt( this.y );
        buffer.writeInt( this.z );
    }

    @Override
    public void deserialize( PacketBuffer buffer, int protocolID ) {
        this.x = buffer.readInt();
        this.y = buffer.readInt();
        this.z = buffer.readInt();
    }

}
