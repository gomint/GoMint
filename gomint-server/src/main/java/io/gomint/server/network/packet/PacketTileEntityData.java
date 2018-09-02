package io.gomint.server.network.packet;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.math.BlockPosition;
import io.gomint.server.entity.tileentity.SerializationReason;
import io.gomint.server.entity.tileentity.TileEntity;
import io.gomint.server.network.Protocol;
import io.gomint.server.util.DumpUtil;
import io.gomint.taglib.NBTReaderNoBuffer;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.taglib.NBTWriter;
import lombok.Data;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteOrder;

/**
 * @author geNAZt
 * @version 1.0
 */
@Data
public class PacketTileEntityData extends Packet {

    private BlockPosition position;
    private TileEntity tileEntity;

    public PacketTileEntityData() {
        super( Protocol.PACKET_TILE_ENTITY_DATA );
    }

    @Override
    public void serialize( PacketBuffer buffer, int protocolID ) {
        // Block position
        writeBlockPosition( this.position, buffer );

        // NBT Tag
        NBTTagCompound compound = new NBTTagCompound( "" );
        this.tileEntity.toCompound( compound, SerializationReason.NETWORK );

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        NBTWriter nbtWriter = new NBTWriter( baos, ByteOrder.LITTLE_ENDIAN );
        nbtWriter.setUseVarint( true );
        try {
            nbtWriter.write( compound );
        } catch ( IOException e ) {
            e.printStackTrace();
        }

        buffer.writeBytes( baos.toByteArray() );
    }

    @Override
    public void deserialize( PacketBuffer buffer, int protocolID ) {
        this.position = readBlockPosition( buffer );

        byte[] data = new byte[buffer.getRemaining()];
        buffer.readBytes( data );

        NBTReaderNoBuffer reader = new NBTReaderNoBuffer( new ByteArrayInputStream( data ), ByteOrder.LITTLE_ENDIAN );
        reader.setUseVarint( true );
        try {
            DumpUtil.dumpNBTCompund( reader.parse() );
        } catch ( IOException e ) {
            e.printStackTrace();
        }
    }

}
