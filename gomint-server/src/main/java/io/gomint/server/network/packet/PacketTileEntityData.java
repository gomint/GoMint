package io.gomint.server.network.packet;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.math.BlockPosition;
import io.gomint.server.network.Protocol;
import io.gomint.taglib.NBTReader;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.taglib.NBTWriter;

import java.nio.ByteOrder;

/**
 * @author geNAZt
 * @version 1.0
 */
public class PacketTileEntityData extends Packet {

    private static final int MAX_ALLOC = 1024 * 1024;

    private BlockPosition position;
    private NBTTagCompound compound;

    public PacketTileEntityData() {
        super( Protocol.PACKET_TILE_ENTITY_DATA );
    }

    @Override
    public void serialize( PacketBuffer buffer, int protocolID ) throws Exception {
        // Block position
        writeBlockPosition( this.position, buffer );

        // NBT Tag
        NBTWriter nbtWriter = new NBTWriter( buffer.getBuffer(), ByteOrder.LITTLE_ENDIAN );
        nbtWriter.setUseVarint( true );
        nbtWriter.write( this.compound );
    }

    @Override
    public void deserialize( PacketBuffer buffer, int protocolID ) throws Exception {
        this.position = readBlockPosition( buffer );

        NBTReader reader = new NBTReader( buffer.getBuffer(), ByteOrder.LITTLE_ENDIAN );
        reader.setUseVarint( true );
        reader.setAllocateLimit( MAX_ALLOC );

        this.compound = reader.parse();
    }

    public BlockPosition getPosition() {
        return position;
    }

    public void setPosition(BlockPosition position) {
        this.position = position;
    }

    public NBTTagCompound getCompound() {
        return compound;
    }

    public void setCompound(NBTTagCompound compound) {
        this.compound = compound;
    }
}
