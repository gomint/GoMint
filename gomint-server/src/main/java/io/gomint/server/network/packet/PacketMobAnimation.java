package io.gomint.server.network.packet;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.server.network.Protocol;
import lombok.Getter;

/**
 * @author geNAZt
 * @version 1.0
 */
public class PacketMobAnimation extends Packet {

    private Animation animation;
    private long entityId;

    public PacketMobAnimation() {
        super( (byte) -1 );
    }

    @Override
    public void serialize( PacketBuffer buffer ) {

    }

    @Override
    public void deserialize( PacketBuffer buffer ) {

    }

    public enum Animation {
        ARM_SWING( (byte) 1 );

        @Getter
        private final byte mcpeMagic;

        Animation( byte mcpeMagic ) {
            this.mcpeMagic = mcpeMagic;
        }

        public static Animation getAnimationById( byte id ) {
            for ( Animation animation : values() ) {
                if ( animation.mcpeMagic == id ) {
                    return animation;
                }
            }

            System.out.println( "Unknown animation id: " + id );
            return null;
        }
    }

}
