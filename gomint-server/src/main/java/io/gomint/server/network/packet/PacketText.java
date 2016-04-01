package io.gomint.server.network.packet;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.server.network.Protocol;
import lombok.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Digot
 * @version 1.0
 */
@Data
@EqualsAndHashCode ( callSuper = true )
public class PacketText extends Packet {

    private String message;
    private String source;
    private TextType type;

    /**
     * Constructs a new {@link TextType#CHAT} packet.
     * @param source The username of the entity that wrote the message
     * @param message The message
     */
    public PacketText ( String source, String message ) {
        super( Protocol.PACKET_TEXT );
        this.type = TextType.CHAT;
        this.source = source;
        this.message = message;
    }

    /**
     * Constructs a new {@link TextType#SYSTEM} packet.
     * @param message The message
     */
    public PacketText ( String message ) {
        super( Protocol.PACKET_TEXT );
        this.type = TextType.SYSTEM;
        this.message = message;
    }

    /**
     * Default constructor for networking
     */
    public PacketText () {
        super( Protocol.PACKET_TEXT );
    }

    @Override
    public void serialize ( PacketBuffer buffer ) {
        buffer.writeByte( type.getId() );

        switch ( type ) {
            case RAW:
                break;
            case CHAT:
                buffer.writeString( source );
                buffer.writeString( message );
                break;
            case TRANSLATION:
                break;
            case POPUP:
                break;
            case TIP:
                break;
            case SYSTEM:
                buffer.writeString( message );
                break;
        }
    }

    @Override
    public void deserialize ( PacketBuffer buffer ) {
        this.type = TextType.getById( buffer.readByte() );

        switch ( type ) {
            case RAW:
                break;
            case CHAT:
                this.source = buffer.readString();
                this.message = buffer.readString();
                break;
            case TRANSLATION:
                break;
            case POPUP:
                break;
            case TIP:
                break;
            case SYSTEM:
                this.message = buffer.readString();
                break;
        }
    }

    @AllArgsConstructor ( access = AccessLevel.PRIVATE )
    @Getter
    private enum TextType {
        RAW ( (byte) 0 ),
        CHAT ( (byte) 1 ),
        TRANSLATION ( (byte) 2 ),
        POPUP ( (byte) 3 ),
        TIP ( (byte) 4 ),
        SYSTEM ( (byte) 5 );

        private byte id;

        private static Map<Byte, TextType> byId = new HashMap<>();

        static{
            for ( TextType textType : values() ) {
                byId.put( textType.getId(), textType );
            }
        }

        protected static TextType getById( byte id ) {
            return byId.get( id );
        }
    }
}
