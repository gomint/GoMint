package io.gomint.server.inventory;

import io.gomint.math.BlockPosition;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.entity.tileentity.TileEntity;
import io.gomint.server.network.packet.PacketContainerOpen;
import io.gomint.server.network.type.WindowType;
import io.gomint.server.world.WorldAdapter;

/**
 * @author geNAZt
 * @version 1.0
 */
public abstract class ContainerInventory extends Inventory implements io.gomint.inventory.ContainerInventory {

    /**
     * Construct a new container inventory
     *
     * @param owner of the container (mostly a tile or normal entity)
     * @param size  of the entity
     */
    ContainerInventory( InventoryHolder owner, int size ) {
        super( owner, size );
    }

    /**
     * Get the type of inventory
     *
     * @return inventory type
     */
    public abstract WindowType getType();

    @Override
    public BlockPosition getContainerPosition() {
        TileEntity tileEntity = (TileEntity) this.owner;
        return tileEntity.getLocation().toBlockPosition();
    }

    public WorldAdapter getWorld() {
        TileEntity tileEntity = (TileEntity) this.owner;
        return (WorldAdapter) tileEntity.getLocation().getWorld();
    }

    /**
     * Called when a container has been opened
     *
     * @param player for which the container should be opened
     */
    public abstract void onOpen( EntityPlayer player );

    /**
     * Called when a container has been closed
     *
     * @param player for which the container closed
     */
    public abstract void onClose( EntityPlayer player );

    /**
     * Add a player to this container
     *
     * @param player   to add
     * @param windowId to use for this player
     */
    public void addViewer( EntityPlayer player, byte windowId ) {
        // Sent ContainerOpen first
        PacketContainerOpen containerOpen = new PacketContainerOpen();
        containerOpen.setWindowId( windowId );
        containerOpen.setType( this.getType().getId() );
        containerOpen.setLocation( this.getContainerPosition() );
        player.getConnection().addToSendQueue( containerOpen );

        // Add viewer and send contents
        super.addViewer( player );

        // Trigger additional actions for the container
        this.onOpen( player );
    }

    @Override
    public void removeViewer( EntityPlayer player ) {
        // Call special close event
        this.onClose( player );

        // Remove from view
        super.removeViewer( player );
    }

}
