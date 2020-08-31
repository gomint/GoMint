package io.gomint.server.inventory;

import io.gomint.math.BlockPosition;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.inventory.item.Items;
import io.gomint.server.network.packet.PacketBlockEvent;
import io.gomint.server.network.type.WindowType;
import io.gomint.server.world.WorldAdapter;
import io.gomint.world.Sound;

/**
 * @author DelxHQ
 * @version 1.0
 */

public class ShulkerBoxInventory extends ContainerInventory implements io.gomint.inventory.ShulkerBoxInventory {

    public ShulkerBoxInventory(Items items, InventoryHolder owner ) {
        super( items, owner, 27 );
    }

    @Override
    public WindowType getType() {
        return WindowType.CONTAINER;
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
            world.playSound( position.toVector().add( 0.5f, 0.5f, 0.5f ), Sound.SHULKER_OPEN, (byte) 1 );
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
            world.playSound( position.toVector().add( 0.5f, 0.5f, 0.5f ), Sound.SHULKER_CLOSE, (byte) 1 );
        }
    }
}
