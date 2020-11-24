/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.network.packet;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.server.network.Protocol;

import java.util.Objects;

/**
 * @author BlackyPaw
 * @version 1.0
 */
public class PacketPlayState extends Packet {

    private PlayState state;

    public PacketPlayState() {
        super( Protocol.PACKET_PLAY_STATE );
    }

    @Override
    public void serialize( PacketBuffer buffer, int protocolID ) {
        buffer.writeInt( this.state.getValue() );
    }

    @Override
    public void deserialize( PacketBuffer buffer, int protocolID ) {
        this.state = PlayState.fromValue( buffer.readInt() );
    }

    public PlayState getState() {
        return state;
    }

    public void setState(PlayState state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PacketPlayState that = (PacketPlayState) o;
        return state == that.state;
    }

    @Override
    public int hashCode() {
        return Objects.hash(state);
    }

    /**
     * Enumeration of play states observed to be sent inside certain packets.
     */
    public enum PlayState {

        LOGIN_SUCCESS( 0 ),
        LOGIN_FAILED_CLIENT( 1 ),
        LOGIN_FAILED_SERVER( 2 ),
        SPAWN( 3 );

        private final int value;

        PlayState( int value ) {
            this.value = value;
        }

        public static PlayState fromValue( int value ) {
            switch ( value ) {
                case 0:
                    return LOGIN_SUCCESS;
                case 1:
                    return LOGIN_FAILED_CLIENT;
                case 2:
                    return LOGIN_FAILED_SERVER;
                case 3:
                    return SPAWN;
                default:
                    return null;
            }
        }

        public int getValue() {
            return this.value;
        }

    }
}
