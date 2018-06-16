package io.gomint.server.network.packet;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.server.network.Protocol;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Data
public class PacketSetTitle extends Packet {

    private int type;
    private String text = "";
    private int fadeInTime = 0;
    private int stayTime = 0;
    private int fadeOutTime = 0;

    public PacketSetTitle() {
        super( Protocol.PACKET_SET_TITLE );
    }

    @Override
    public void serialize( PacketBuffer buffer, int protocolID ) {
        buffer.writeSignedVarInt( type );
        buffer.writeString( text );
        buffer.writeSignedVarInt( fadeInTime );
        buffer.writeSignedVarInt( stayTime);
        buffer.writeSignedVarInt( fadeOutTime );
    }

    @Override
    public void deserialize( PacketBuffer buffer, int protocolID ) {
        this.type = buffer.readSignedVarInt();
        this.text = buffer.readString();
        this.fadeInTime = buffer.readSignedVarInt();
        this.stayTime = buffer.readSignedVarInt();
        this.fadeOutTime = buffer.readSignedVarInt();
    }

    @RequiredArgsConstructor
    public enum TitleType {

        TYPE_CLEAR( 0 ),
        TYPE_RESET( 1 ),
        TYPE_TITLE( 2 ),
        TYPE_SUBTITLE( 3 ),
        TYPE_ACTION_BAR( 4 ),
        TYPE_ANIMATION_TIMES( 5 );

        @Getter
        private final int id;

    }
}
