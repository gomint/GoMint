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
 * @author lukeeey
 * @version 1.0
 */
@Data
public class PacketStopSound extends Packet {

    private String soundName;
    private boolean stopAll;

    public PacketStopSound() {
        super( Protocol.PACKET_STOP_SOUND );
    }

    @Override
    public void serialize( PacketBuffer buffer, int protocolID ) {
        buffer.writeString( this.soundName );
        buffer.writeBoolean( this.stopAll );
    }

    @Override
    public void deserialize( PacketBuffer buffer, int protocolID ) {
        this.soundName = buffer.readString();
        this.stopAll = buffer.readBoolean();
    }

}
