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
public class PacketText extends Packet {

    private Type type;
    private String sender;
    private String message;
    private String[] arguments;
    private String xuid;

    public PacketText() {
        super( Protocol.PACKET_TEXT );
    }

    public String getSubtitle() {
        return this.sender;
    }

    public void setSubtitle( String subtitle ) {
        this.sender = subtitle;
    }

    @Override
    public void serialize( PacketBuffer buffer ) {
        buffer.writeByte( this.type.getId() );
        buffer.writeBoolean( false );
        switch ( this.type ) {
            case CLIENT_MESSAGE:
            case TIP_MESSAGE:
            case SYSTEM_MESSAGE:
                buffer.writeString( this.message );
                break;

            case PLAYER_CHAT:
                buffer.writeString( this.sender );
                buffer.writeString( this.message );
                buffer.writeString( this.xuid );
                break;

            case LOCALIZABLE_MESSAGE:
                buffer.writeString( this.message );
                buffer.writeByte( (byte) this.arguments.length );
                for ( String argument : this.arguments ) {
                    buffer.writeString( argument );
                }

                break;

            case POPUP_NOTICE:
                buffer.writeString( this.message );
                buffer.writeString( this.sender );
                break;
        }
    }

    @Override
    public void deserialize( PacketBuffer buffer ) {
        this.type = Type.getById( buffer.readByte() );
        buffer.readBoolean();
        switch ( this.type ) {
            case CLIENT_MESSAGE:
            case TIP_MESSAGE:
            case SYSTEM_MESSAGE:
                this.message = buffer.readString();
                break;

            case PLAYER_CHAT:
                this.sender = buffer.readString();
                this.message = buffer.readString();
                this.xuid = buffer.readString();
                break;

            case LOCALIZABLE_MESSAGE:
                this.message = buffer.readString();
                byte count = buffer.readByte();
                this.arguments = new String[count];
                for ( byte i = 0; i < count; ++i ) {
                    this.arguments[i] = buffer.readString();
                }

                break;

            case POPUP_NOTICE:
                this.message = buffer.readString();
                this.sender = buffer.readString();
                break;
        }
    }

    public enum Type {

        /**
         * Type value for unformatted messages.
         */
        CLIENT_MESSAGE( (byte) 0 ),

        /**
         * Type value for usual player chat.
         */
        PLAYER_CHAT( (byte) 1 ),

        /**
         * Type value for localizable messages included in Minecraft's language files.
         */
        LOCALIZABLE_MESSAGE( (byte) 2 ),

        /**
         * Type value for displaying text right above a player's action bar.
         */
        POPUP_NOTICE( (byte) 3 ),

        /**
         * Type value for displaying text slightly below the center of the screen (similar to title
         * text of PC edition).
         */
        TIP_MESSAGE( (byte) 4 ),

        /**
         * Type value for unformatted messages. Actual use unknown, same as system, apparently.
         */
        SYSTEM_MESSAGE( (byte) 5 );

        private final byte id;

        Type( byte id ) {
            this.id = id;
        }

        public static Type getById( byte id ) {
            switch ( id ) {
                case 0:
                    return CLIENT_MESSAGE;
                case 1:
                    return PLAYER_CHAT;
                case 2:
                    return LOCALIZABLE_MESSAGE;
                case 3:
                    return POPUP_NOTICE;
                case 4:
                    return TIP_MESSAGE;
                case 5:
                    return SYSTEM_MESSAGE;
                default:
                    return null;
            }
        }

        public byte getId() {
            return this.id;
        }

    }
}
