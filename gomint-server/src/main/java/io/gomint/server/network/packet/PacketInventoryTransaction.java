package io.gomint.server.network.packet;

import io.gomint.inventory.item.ItemStack;
import io.gomint.jraknet.PacketBuffer;
import io.gomint.math.BlockPosition;
import io.gomint.math.Vector;
import io.gomint.server.network.Protocol;
import io.gomint.world.block.data.Facing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * @author geNAZt
 * @version 1.0
 */
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
    private ItemStack<?> itemInHand;

    // Type USE_ITEM / RELEASE_ITEM
    private BlockPosition blockPosition;
    private Facing face;
    private Vector playerPosition;
    private Vector clickPosition;
    private int blockRuntimeID;

    // Type USE_ITEM_ON_ENTITY
    private long entityId;
    private Vector vector1;
    private Vector vector2;

    // New request id and changes slot (1.16)
    private int requestId;
    private ChangeSlot[] changeSlot;
    private boolean hasItemstackIDs;

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
        this.requestId = buffer.readSignedVarInt();
        if (this.requestId != 0) {
            int length = buffer.readUnsignedVarInt();
            this.changeSlot = new ChangeSlot[length];
            for (int i = 0; i < length; i++) {
                this.changeSlot[i] = new ChangeSlot();
                this.changeSlot[i].deserialize(buffer);
            }
        }

        this.type = buffer.readUnsignedVarInt();
        this.hasItemstackIDs = buffer.readBoolean();

        // Read transaction action(s)
        int actionCount = buffer.readUnsignedVarInt();
        this.actions = new NetworkTransaction[actionCount];
        for ( int i = 0; i < actionCount; i++ ) {
            NetworkTransaction networkTransaction = new NetworkTransaction();
            networkTransaction.deserialize( buffer, this.hasItemstackIDs );
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
                this.blockRuntimeID = buffer.readUnsignedVarInt();
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
                LOGGER.warn( "Unknown transaction type: {}", this.type );
        }
    }

    public static class ChangeSlot {
        private byte containerId;
        private byte[] changedSlots;

        /**
         * Deserialize a transaction action
         *
         * @param buffer Data from the packet
         */
        public void deserialize( PacketBuffer buffer ) {
            this.containerId = buffer.readByte();

            int count = buffer.readUnsignedVarInt();
            this.changedSlots = new byte[count];
            buffer.readBytes(this.changedSlots);
        }

        public byte getContainerId() {
            return containerId;
        }

        public void setContainerId(byte containerId) {
            this.containerId = containerId;
        }

        public byte[] getChangedSlots() {
            return changedSlots;
        }

        public void setChangedSlots(byte[] changedSlots) {
            this.changedSlots = changedSlots;
        }
    }

    public static class NetworkTransaction {

        private static final int SOURCE_CONTAINER = 0;
        private static final int SOURCE_WORLD = 2;
        private static final int SOURCE_CREATIVE = 3;
        private static final int SOURCE_CRAFTING_GRID = 100;
        private static final int SOURCE_WTF_IS_DIS = 99999;

        private int sourceType;
        private int windowId;
        private int unknown; // Maybe entity id?
        private int slot;
        private ItemStack<?> oldItem;
        private ItemStack<?> newItem;

        // Itemstack id for the new item (1.16)
        private int newItemStackID;

        /**
         * Deserialize a transaction action
         *
         * @param buffer Data from the packet
         */
        public void deserialize( PacketBuffer buffer, boolean hasItemstackID ) {
            this.sourceType = buffer.readUnsignedVarInt();

            switch ( this.sourceType ) {
                case SOURCE_CONTAINER:
                case SOURCE_WTF_IS_DIS:
                case SOURCE_CRAFTING_GRID:
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

            if (hasItemstackID) {
                this.newItemStackID = buffer.readSignedVarInt();
            }
        }

        public int getSourceType() {
            return sourceType;
        }

        public void setSourceType(int sourceType) {
            this.sourceType = sourceType;
        }

        public int getWindowId() {
            return windowId;
        }

        public void setWindowId(int windowId) {
            this.windowId = windowId;
        }

        public int getUnknown() {
            return unknown;
        }

        public void setUnknown(int unknown) {
            this.unknown = unknown;
        }

        public int getSlot() {
            return slot;
        }

        public void setSlot(int slot) {
            this.slot = slot;
        }

        public ItemStack<?> getOldItem() {
            return oldItem;
        }

        public void setOldItem(ItemStack<?> oldItem) {
            this.oldItem = oldItem;
        }

        public ItemStack<?> getNewItem() {
            return newItem;
        }

        public void setNewItem(ItemStack<?> newItem) {
            this.newItem = newItem;
        }

        public int getNewItemStackID() {
            return newItemStackID;
        }

        public void setNewItemStackID(int newItemStackID) {
            this.newItemStackID = newItemStackID;
        }

        @Override
        public String toString() {
            return "NetworkTransaction{" +
                "sourceType=" + sourceType +
                ", windowId=" + windowId +
                ", unknown=" + unknown +
                ", slot=" + slot +
                ", oldItem=" + oldItem +
                ", newItem=" + newItem +
                ", newItemStackID=" + newItemStackID +
                '}';
        }
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public NetworkTransaction[] getActions() {
        return actions;
    }

    public void setActions(NetworkTransaction[] actions) {
        this.actions = actions;
    }

    public int getActionType() {
        return actionType;
    }

    public void setActionType(int actionType) {
        this.actionType = actionType;
    }

    public int getHotbarSlot() {
        return hotbarSlot;
    }

    public void setHotbarSlot(int hotbarSlot) {
        this.hotbarSlot = hotbarSlot;
    }

    public ItemStack<?> getItemInHand() {
        return itemInHand;
    }

    public void setItemInHand(ItemStack<?> itemInHand) {
        this.itemInHand = itemInHand;
    }

    public BlockPosition getBlockPosition() {
        return blockPosition;
    }

    public void setBlockPosition(BlockPosition blockPosition) {
        this.blockPosition = blockPosition;
    }

    public Facing getFace() {
        return face;
    }

    public void setFace(Facing face) {
        this.face = face;
    }

    public Vector getPlayerPosition() {
        return playerPosition;
    }

    public void setPlayerPosition(Vector playerPosition) {
        this.playerPosition = playerPosition;
    }

    public Vector getClickPosition() {
        return clickPosition;
    }

    public void setClickPosition(Vector clickPosition) {
        this.clickPosition = clickPosition;
    }

    public int getBlockRuntimeID() {
        return blockRuntimeID;
    }

    public void setBlockRuntimeID(int blockRuntimeID) {
        this.blockRuntimeID = blockRuntimeID;
    }

    public long getEntityId() {
        return entityId;
    }

    public void setEntityId(long entityId) {
        this.entityId = entityId;
    }

    public Vector getVector1() {
        return vector1;
    }

    public void setVector1(Vector vector1) {
        this.vector1 = vector1;
    }

    public Vector getVector2() {
        return vector2;
    }

    public void setVector2(Vector vector2) {
        this.vector2 = vector2;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public ChangeSlot[] getChangeSlot() {
        return changeSlot;
    }

    public void setChangeSlot(ChangeSlot[] changeSlot) {
        this.changeSlot = changeSlot;
    }

    public boolean isHasItemstackIDs() {
        return hasItemstackIDs;
    }

    public void setHasItemstackIDs(boolean hasItemstackIDs) {
        this.hasItemstackIDs = hasItemstackIDs;
    }

    @Override
    public String toString() {
        return "PacketInventoryTransaction{" +
            "type=" + type +
            ", actions=" + Arrays.toString(actions) +
            ", actionType=" + actionType +
            ", hotbarSlot=" + hotbarSlot +
            ", itemInHand=" + itemInHand +
            ", blockPosition=" + blockPosition +
            ", face=" + face +
            ", playerPosition=" + playerPosition +
            ", clickPosition=" + clickPosition +
            ", blockRuntimeID=" + blockRuntimeID +
            ", entityId=" + entityId +
            ", vector1=" + vector1 +
            ", vector2=" + vector2 +
            ", requestId=" + requestId +
            ", changeSlot=" + Arrays.toString(changeSlot) +
            ", hasItemstackIDs=" + hasItemstackIDs +
            '}';
    }
}
