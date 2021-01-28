/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.network.packet;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.math.Vector;
import io.gomint.server.network.Protocol;

/**
 * @author geNAZt
 * @version 1.0
 */
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

    public Vector getPosition() {
        return this.position;
    }

    public void setPosition(Vector position) {
        this.position = position;
    }

    public RespawnState getState() {
        return this.state;
    }

    public void setState(RespawnState state) {
        this.state = state;
    }

    public long getEntityId() {
        return this.entityId;
    }

    public void setEntityId(long entityId) {
        this.entityId = entityId;
    }

    public enum RespawnState {
        SEARCHING_FOR_SPAWN( (byte) 0 ),
        READY_TO_SPAWN( (byte) 1 ),
        CLIENT_READY_TO_SPAWN( (byte) 2 );

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

        public byte getId() {
            return this.id;
        }
    }
}
