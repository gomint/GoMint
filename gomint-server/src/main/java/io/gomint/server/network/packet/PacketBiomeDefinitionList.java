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
public class PacketBiomeDefinitionList extends Packet {

    private byte[] nbt;

    public PacketBiomeDefinitionList() {
        super( Protocol.PACKET_BIOME_DEFINITION_LIST );

        // TODO: move to AssetsLibrary
        try {
            InputStream inputStream = AssetsLibrary.class.getResourceAsStream( "/biome_definitions.dat" );
            if ( inputStream == null ) {
                throw new AssertionError( "Could not find biome_definitions.dat" );
            }

            // noinspection UnstableApiUsage
            this.nbt = ByteStreams.toByteArray( inputStream );
        } catch ( Exception e ) {
            throw new AssertionError( "Error whilst loading biome_definitions.dat", e );
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
        return this.nbt;
    }

}
