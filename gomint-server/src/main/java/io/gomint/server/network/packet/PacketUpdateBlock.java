package io.gomint.server.network.packet;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.math.BlockPosition;
import io.gomint.server.network.Protocol;

/**
 * @author geNAZt
 * @version 1.0
 */
public class PacketUpdateBlock extends Packet {

    public static final int FLAG_NONE = 0b0000;
    public static final int FLAG_NEIGHBORS = 0b0001;
    public static final int FLAG_NETWORK = 0b0010;
    public static final int FLAG_NOGRAPHIC = 0b0100;
    public static final int FLAG_PRIORITY = 0b1000;
    public static final int FLAG_ALL = PacketUpdateBlock.FLAG_NEIGHBORS | PacketUpdateBlock.FLAG_NETWORK;
    public static final int FLAG_ALL_PRIORITY = PacketUpdateBlock.FLAG_ALL | PacketUpdateBlock.FLAG_PRIORITY;

    private BlockPosition position;
    private int blockId;
    private int flags;
    private int layer;

    public PacketUpdateBlock() {
        super( Protocol.PACKET_UPDATE_BLOCK );
    }

    @Override
    public void serialize( PacketBuffer buffer, int protocolID ) {
        writeBlockPosition( this.position, buffer );
        buffer.writeUnsignedVarInt( this.blockId );
        buffer.writeUnsignedVarInt( this.flags );
        buffer.writeUnsignedVarInt( this.layer );
    }

    @Override
    public void deserialize( PacketBuffer buffer, int protocolID ) {

    }

    public BlockPosition getPosition() {
        return this.position;
    }

    public void setPosition(BlockPosition position) {
        this.position = position;
    }

    public int getBlockId() {
        return this.blockId;
    }

    public void setBlockId(int blockId) {
        this.blockId = blockId;
    }

    public int getFlags() {
        return this.flags;
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }

    public int getLayer() {
        return this.layer;
    }

    public void setLayer(int layer) {
        this.layer = layer;
    }
}
