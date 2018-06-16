package io.gomint.server.inventory;

import io.gomint.math.BlockPosition;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.entity.tileentity.EnderChestTileEntity;
import io.gomint.server.network.PlayerConnection;
import io.gomint.server.network.packet.PacketBlockEvent;
import io.gomint.server.network.packet.PacketInventoryContent;
import io.gomint.server.network.packet.PacketInventorySetSlot;
import io.gomint.server.network.type.WindowType;
import io.gomint.server.world.WorldAdapter;
import io.gomint.world.Sound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class EnderChestInventory extends ContainerInventory {

    /**
     * Constructs a ender chest inventory. A ender chest does NOT have a own inventory. It displays
     * a special user inventory. So regardless of what ender chest the user clicks the content is the same
     * in all and synced across all chests.
     *
     * @param owner tile entity which owns this inventory
     */
    public EnderChestInventory( InventoryHolder owner ) {
        super( owner, 27 );
    }

    @Override
    public WindowType getType() {
        return WindowType.CONTAINER;
    }

    @Override
    public BlockPosition getContainerPosition() {
        return ( (EnderChestTileEntity) this.owner ).getLocation().toBlockPosition();
    }

    @Override
    public WorldAdapter getWorld() {
        return (WorldAdapter) ( (EnderChestTileEntity) this.owner ).getLocation().getWorld();
    }

    @Override
    public void onOpen( EntityPlayer player ) {
        // Sound and open animation
        if ( this.viewer.size() == 1 ) {
            BlockPosition position = this.getContainerPosition();
            WorldAdapter world = this.getWorld();

            PacketBlockEvent blockEvent = new PacketBlockEvent();
            blockEvent.setPosition( position );
            blockEvent.setData1( 1 );
            blockEvent.setData2( 2 );

            world.sendToVisible( position, blockEvent, entity -> true );
            world.playSound( position.toVector().add( 0.5f, 0.5f, 0.5f ), Sound.CHEST_OPEN, (byte) 1 );
        }
    }

    @Override
    public void onClose( EntityPlayer player ) {
        // Sound and close animation
        if ( this.viewer.size() == 1 ) {
            BlockPosition position = this.getContainerPosition();
            WorldAdapter world = this.getWorld();

            PacketBlockEvent blockEvent = new PacketBlockEvent();
            blockEvent.setPosition( position );
            blockEvent.setData1( 1 );
            blockEvent.setData2( 0 );

            world.sendToVisible( position, blockEvent, entity -> true );
            world.playSound( position.toVector().add( 0.5f, 0.5f, 0.5f ), Sound.CHEST_CLOSED, (byte) 1 );
        }
    }

}
