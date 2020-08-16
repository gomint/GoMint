package io.gomint.server.network.packet;

import com.google.common.io.ByteStreams;
import io.gomint.jraknet.PacketBuffer;
import io.gomint.server.assets.AssetsLibrary;
import io.gomint.server.network.Protocol;

import java.io.InputStream;

/**
 * @author HerryYT
 * @version 1.0
 */
public class PacketAvailableEntityIdentifiers extends Packet {

    private byte[] nbt;

    public PacketAvailableEntityIdentifiers() {
        super( Protocol.PACKET_AVAILABLE_ENTITY_IDENTIFIERS );

        // TODO: move to AssetsLibrary
        try {
            InputStream inputStream = AssetsLibrary.class.getResourceAsStream( "/entity_identifiers.dat" );
            if ( inputStream == null ) {
                throw new AssertionError( "Could not find entity_identifiers.dat" );
            }
            // noinspection UnstableApiUsage
            nbt = ByteStreams.toByteArray( inputStream );
        } catch ( Exception e ) {
            throw new AssertionError( "Error whilst loading entity_identifiers.dat", e );
        }

    }

    @Override
    public void serialize(PacketBuffer buffer, int protocolID) {
        buffer.writeBytes( this.nbt );
    }

    @Override
    public void deserialize(PacketBuffer buffer, int protocolID) {

    }

    public byte[] getNbt() {
        return nbt;
    }

    public void setNbt(byte[] nbt) {
        this.nbt = nbt;
    }
}
