package io.gomint.server.network.packet;

import com.google.common.io.ByteStreams;
import io.gomint.jraknet.PacketBuffer;
import io.gomint.server.assets.AssetsLibrary;
import io.gomint.server.network.Protocol;
import io.gomint.taglib.NBTReader;
import io.gomint.taglib.NBTTagCompound;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.UnpooledByteBufAllocator;

import java.io.InputStream;
import java.nio.ByteOrder;
import java.util.List;

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

            ByteBuf d = UnpooledByteBufAllocator.DEFAULT.heapBuffer();
            d.writeBytes(this.nbt);
            NBTReader reader = new NBTReader(d, ByteOrder.LITTLE_ENDIAN);
            reader.setUseVarint(true);
            NBTTagCompound output = reader.parse();
            System.out.println(output);
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
        return nbt;
    }

}
