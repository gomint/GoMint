package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.math.BlockPosition;
import io.gomint.math.Location;
import io.gomint.math.Vector;
import io.gomint.math.Vector2;
import io.gomint.server.entity.Entity;
import io.gomint.server.registry.SkipRegister;
import io.gomint.server.util.BlockIdentifier;
import io.gomint.server.world.PlacementData;
import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.world.block.BlockAir;
import io.gomint.world.block.BlockFace;

/**
 * @author geNAZt
 * @version 1.0
 */
@SkipRegister
public abstract class Door extends Block implements io.gomint.world.block.BlockDoor {

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
    public boolean interact( Entity entity, BlockFace face, Vector facePos, ItemStack item ) {
        // Open / Close the door
        // TODO: Door events
        toggle();

        return true;
    }

    @Override
    public boolean beforePlacement( Entity entity, ItemStack item, Location location ) {
        Block above = location.getWorld().getBlockAt( location.toBlockPosition().add( BlockPosition.UP ) );
        return above.canBeReplaced( item );
    }

    @Override
    public PlacementData calculatePlacementData( Entity entity, ItemStack item, Vector clickVector ) {
        PlacementData data = super.calculatePlacementData( entity, item, clickVector );

        Vector2 directionPlane = entity.getDirectionPlane();
        double xAbs = Math.abs( directionPlane.getX() );
        double zAbs = Math.abs( directionPlane.getZ() );

        if ( zAbs > xAbs ) {
            if ( directionPlane.getZ() > 0 ) {
                return data.setBlockIdentifier( new BlockIdentifier( data.getBlockIdentifier().getBlockId(), (short) 3 ) );
            } else {
                return data.setBlockIdentifier( new BlockIdentifier( data.getBlockIdentifier().getBlockId(), (short) 2 ) );
            }
        } else {
            if ( directionPlane.getX() > 0 ) {
                return data.setBlockIdentifier( new BlockIdentifier( data.getBlockIdentifier().getBlockId(), (short) 0 ) );
            } else {
                return data.setBlockIdentifier( new BlockIdentifier( data.getBlockIdentifier().getBlockId(), (short) 1 ) );
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

    @Override
    public boolean onBreak( boolean creative ) {
        if ( isTop() ) {
            Block otherPart = getLocation().getWorld().getBlockAt( getLocation().toBlockPosition().add( BlockPosition.DOWN ) );
            otherPart.setType( BlockAir.class );
        } else {
            Block otherPart = getLocation().getWorld().getBlockAt( getLocation().toBlockPosition().add( BlockPosition.UP ) );
            otherPart.setType( BlockAir.class );
        }

        return true;
    }

    public void setTopPart() {
        this.setBlockData( (byte) 0x08 );
        this.updateBlock();
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public void afterPlacement( PlacementData data ) {
        data.setBlockIdentifier( new BlockIdentifier( data.getBlockIdentifier().getBlockId(), (short) 8 ) );

        Block above = this.location.getWorld().getBlockAt( this.location.toBlockPosition().add( BlockPosition.UP ) );
        above.setBlockFromPlacementData( data );
    }

    @Override
    public Class<? extends ItemStack>[] getToolInterfaces() {
        return ToolPresets.AXE;
    }
}
