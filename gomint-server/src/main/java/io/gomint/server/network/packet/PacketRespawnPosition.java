/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.network.packet;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.math.Vector;
import io.gomint.server.network.Protocol;
import lombok.Data;
import lombok.Getter;

/**
 * @author geNAZt
 * @version 1.0
 */
@Data
public class PacketRespawnPosition extends Packet {

    private Vector position;
    private RespawnState state;
    private long entityId;

    public PacketRespawnPosition() {
        super( Protocol.PACKET_RESPAWN_POSITION );
    }

    @Override
    public void serialize( PacketBuffer buffer, int protocolID ) {
        writeVector( this.position, buffer );
        buffer.writeByte( this.state.getId() );
        buffer.writeUnsignedVarLong( this.entityId );
    }

    @Override
    public void deserialize( PacketBuffer buffer, int protocolID ) {
        this.position = this.readVector( buffer );
        this.state = RespawnState.valueOf( buffer.readByte() );
        this.entityId = buffer.readUnsignedVarLong();
    }

    public enum RespawnState {
        SEARCHING_FOR_SPAWN( (byte) 0 ),
        READY_TO_SPAWN( (byte) 1 ),
        CLIENT_READY_TO_SPAWN( (byte) 2 );

        @Getter
        private final byte id;

        RespawnState(byte id) {
            this.id = id;
        }

        public static RespawnState valueOf( byte state ) {
            switch ( state ) {
                case 0:
                    return SEARCHING_FOR_SPAWN;
                case 1:
                    return READY_TO_SPAWN;
                case 2:
                    return CLIENT_READY_TO_SPAWN;
                default:
                    return CLIENT_READY_TO_SPAWN;
            }
        }
    }
}
