/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.network.packet;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.server.network.Protocol;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/**
 * @author geNAZt
 * @version 1.0
 */
@Data
public class PacketBossBar extends Packet {

    private long entityId;
    private Type type;
    private long playerId;
    private float healthPercent;
    private String title = "";
    private int color;
    private int overlay;

    /**
     * Construct a new packet
     */
    public PacketBossBar() {
        super( Protocol.PACKET_BOSS_BAR );
    }

    @Override
    public void serialize( PacketBuffer buffer, int protocolID ) {
        buffer.writeSignedVarLong( this.entityId );
        buffer.writeUnsignedVarInt( this.type.getId() );
        switch ( this.type ) {
            case SHOW:
                buffer.writeString( this.title );
                buffer.writeLFloat( this.healthPercent );
            case UNKNOWN:
                buffer.writeShort( (short) 0 );
            case TEXTURE:
                buffer.writeUnsignedVarInt( this.color );
                buffer.writeUnsignedVarInt( this.overlay );
                break;
            case HEALTH_UPDATE:
                buffer.writeLFloat( this.healthPercent );
                break;
            case TITLE:
                buffer.writeString( this.title );
                break;
        }
    }

    @Override
    public void deserialize( PacketBuffer buffer, int protocolID ) {

    }

    @AllArgsConstructor
    @Getter
    public enum Type {
        SHOW( 0 ),
        REGISTER_PLAYER( 1 ),
        HIDE( 2 ),
        UNREGISTER_PLAYER( 3 ),
        HEALTH_UPDATE( 4 ),
        TITLE( 5 ),
        UNKNOWN( 6 ),
        TEXTURE( 7 );

        private final int id;

        /* S2C: Shows the bossbar to the player. */
        public static final int TYPE_SHOW = 0;
        /* C2S: Registers a player to a boss fight. */
        public static final int TYPE_REGISTER_PLAYER = 1;
        public static final int TYPE_UPDATE = 1;
        /* S2C: Removes the bossbar from the client. */
        public static final int TYPE_HIDE = 2;
        /* C2S: Unregisters a player from a boss fight. */
        public static final int TYPE_UNREGISTER_PLAYER = 3;
        /* S2C: Appears not to be implemented. Currently bar percentage only appears to change in response to the target entity's health. */
        public static final int TYPE_HEALTH_PERCENT = 4;
        /* S2C: Also appears to not be implemented. Title clientside sticks as the target entity's nametag, or their entity type name if not set. */
        public static final int TYPE_TITLE = 5;
        /* S2C: Not sure on this. Includes color and overlay fields, plus an unknown short. TODO: check this */
        public static final int TYPE_UNKNOWN_6 = 6;
        /* S2C: Not implemented :( Intended to alter bar appearance, but these currently produce no effect on clientside whatsoever. */
        public static final int TYPE_TEXTURE = 7;
    }

}
