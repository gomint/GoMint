package io.gomint.server.network.packet;

import io.gomint.inventory.item.ItemStack;
import io.gomint.jraknet.PacketBuffer;
import io.gomint.math.BlockPosition;
import io.gomint.math.Vector;
import io.gomint.server.network.Protocol;
import io.gomint.world.block.BlockFace;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author geNAZt
 * @version 1.0
 */
@Data
public class PacketInventoryTransaction extends Packet {

    private static final Logger LOGGER = LoggerFactory.getLogger( PacketInventoryTransaction.class );

    public static final int TYPE_NORMAL = 0;
    public static final int TYPE_MISMATCH = 1;
    public static final int TYPE_USE_ITEM = 2;
    public static final int TYPE_USE_ITEM_ON_ENTITY = 3;
    public static final int TYPE_RELEASE_ITEM = 4;

    private int type;
    private NetworkTransaction[] actions;

    // Generic
    private int actionType;
    private int hotbarSlot;
    private ItemStack itemInHand;

    // Type USE_ITEM / RELEASE_ITEM
    private BlockPosition blockPosition;
    private BlockFace face;
    private Vector playerPosition;
    private Vector clickPosition;

    // Type USE_ITEM_ON_ENTITY
    private long entityId;
    private Vector vector1;
    private Vector vector2;

    /**
     * Construct a new packet
     */
    public PacketInventoryTransaction() {
        super( Protocol.PACKET_INVENTORY_TRANSACTION );
    }

    @Override
    public void serialize( PacketBuffer buffer, int protocolID ) {

    }

    @Override
    public void deserialize( PacketBuffer buffer, int protocolID ) {
        this.type = buffer.readUnsignedVarInt();

        // Read transaction action(s)
        int actionCount = buffer.readUnsignedVarInt();
        this.actions = new NetworkTransaction[actionCount];
        for ( int i = 0; i < actionCount; i++ ) {
            NetworkTransaction networkTransaction = new NetworkTransaction();
            networkTransaction.deserialize( buffer );
            this.actions[i] = networkTransaction;
        }

        // Read transaction data
        switch ( this.type ) {
            case TYPE_NORMAL:
            case TYPE_MISMATCH:
                break;
            case TYPE_USE_ITEM:
                this.actionType = buffer.readUnsignedVarInt();
                this.blockPosition = readBlockPosition( buffer );
                this.face = readBlockFace( buffer );
                this.hotbarSlot = buffer.readSignedVarInt();
                this.itemInHand = readItemStack( buffer );
                this.playerPosition = new Vector( buffer.readLFloat(), buffer.readLFloat(), buffer.readLFloat() );
                this.clickPosition = new Vector( buffer.readLFloat(), buffer.readLFloat(), buffer.readLFloat() );
                break;
            case TYPE_USE_ITEM_ON_ENTITY:
                this.entityId = buffer.readUnsignedVarLong();
                this.actionType = buffer.readUnsignedVarInt();
                this.hotbarSlot = buffer.readSignedVarInt();
                this.itemInHand = readItemStack( buffer );
                this.vector1 = new Vector( buffer.readLFloat(), buffer.readLFloat(), buffer.readLFloat() );
                this.vector2 = new Vector( buffer.readLFloat(), buffer.readLFloat(), buffer.readLFloat() );
                break;
            case TYPE_RELEASE_ITEM:
                this.actionType = buffer.readUnsignedVarInt();
                this.hotbarSlot = buffer.readSignedVarInt();
                this.itemInHand = readItemStack( buffer );
                this.playerPosition = new Vector( buffer.readLFloat(), buffer.readLFloat(), buffer.readLFloat() );
                break;
            default:
                LOGGER.warn( "Unknown transaction type: " + this.type );
        }
    }

    @Data
    public class NetworkTransaction {

        private static final int SOURCE_CONTAINER = 0;
        private static final int SOURCE_WORLD = 2;
        private static final int SOURCE_CREATIVE = 3;
        private static final int SOURCE_WTF_IS_DIS = 99999;

        private int sourceType;
        private int windowId;
        private int unknown; // Maybe entity id?
        private int slot;
        private ItemStack oldItem;
        private ItemStack newItem;

        /**
         * Deserialize a transaction action
         *
         * @param buffer Data from the packet
         */
        public void deserialize( PacketBuffer buffer ) {
            this.sourceType = buffer.readUnsignedVarInt();

            switch ( this.sourceType ) {
                case SOURCE_CONTAINER:
                case SOURCE_WTF_IS_DIS:
                    this.windowId = buffer.readSignedVarInt();
                    break;
                case SOURCE_WORLD:
                    this.unknown = buffer.readUnsignedVarInt();
                    break;
                case SOURCE_CREATIVE:
                    break;
                default:
                    LOGGER.warn( "Unknown source type: " + this.sourceType );
            }

            this.slot = buffer.readUnsignedVarInt();
            this.oldItem = readItemStack( buffer );
            this.newItem = readItemStack( buffer );
        }

    }

}
