package io.gomint.server.world.block;

import io.gomint.math.BlockPosition;
import io.gomint.server.entity.Entity;
import io.gomint.inventory.item.ItemStack;
import io.gomint.math.Location;
import io.gomint.math.Vector;
import io.gomint.math.Vector2;

/**
 * @author geNAZt
 * @version 1.0
 */
public abstract class Door extends Block implements io.gomint.world.block.Door {

    @Override
    public boolean isTop() {
        return ( getBlockData() & 0x08 ) == 0x08;
    }

    @Override
    public boolean isOpen() {
        if ( isTop() ) {
            Door otherPart = getLocation().getWorld().getBlockAt( getLocation().toBlockPosition().add( BlockPosition.DOWN ) );
            return otherPart.isOpen();
        }

        return ( getBlockData() & 0x04 ) == 0x04;
    }

    @Override
    public void toggle() {
        if ( isTop() ) {
            Door otherPart = getLocation().getWorld().getBlockAt( getLocation().toBlockPosition().add( BlockPosition.DOWN ) );
            otherPart.toggle();
            return;
        }

        setBlockData( (byte) ( getBlockData() ^ 0x04 ) );
        updateBlock();
    }

    @Override
    public boolean interact( Entity entity, int face, Vector facePos, ItemStack item ) {
        // Open / Close the door
        // TODO: Door events
        toggle();

        return true;
    }

    @Override
    public boolean beforePlacement( ItemStack item, Location location ) {
        Block above = location.getWorld().getBlockAt( location.toBlockPosition().add( BlockPosition.UP ) );
        return above.canBeReplaced( item );
    }

    @Override
    public byte calculatePlacementData( Entity entity, ItemStack item, Vector clickVector ) {
        if ( entity == null ) {
            return super.calculatePlacementData( null, item, clickVector );
        }

        Vector2 directionPlane = entity.getDirectionPlane();
        double xAbs = Math.abs( directionPlane.getX() );
        double zAbs = Math.abs( directionPlane.getZ() );

        if ( zAbs > xAbs ) {
            if ( directionPlane.getZ() > 0 ) {
                return (byte) 1;
            } else {
                return (byte) 3;
            }
        } else {
            if ( directionPlane.getX() > 0 ) {
                return (byte) 2;
            } else {
                return (byte) 4;
            }
        }
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public long getBreakTime() {
        return 4500;
    }

}
