package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.math.BlockPosition;
import io.gomint.math.Location;
import io.gomint.math.Vector;
import io.gomint.server.entity.Entity;
import io.gomint.server.util.BlockIdentifier;
import io.gomint.server.world.PlacementData;
import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.server.world.block.state.BlockState;
import io.gomint.server.world.block.state.BooleanBlockState;
import io.gomint.server.world.block.state.FacingBlockState;
import io.gomint.world.block.BlockAir;
import io.gomint.world.block.BlockFace;
import io.gomint.world.block.data.Facing;

import java.util.List;
import java.util.function.Predicate;

/**
 * @author geNAZt
 * @version 1.0
 */
public abstract class Door extends Block implements io.gomint.world.block.BlockDoor {

    private static final Predicate<List<BlockState>> ONLY_NOT_TOP = states -> {
        BooleanBlockState state = (BooleanBlockState) states.get( 0 );
        return !state.getState();
    };

    private static final Predicate<List<BlockState>> ONLY_TOP = states -> {
        BooleanBlockState state = (BooleanBlockState) states.get( 0 );
        return state.getState();
    };

    private BooleanBlockState top = new BooleanBlockState( this, states -> true, 3 );
    private BooleanBlockState open = new BooleanBlockState( this, ONLY_NOT_TOP, 2 );
    private FacingBlockState facing = new FacingBlockState( this, ONLY_NOT_TOP, (short) 1 ); // Rotation is always clockwise

    @Override
    public boolean isTop() {
        return this.top.getState();
    }

    @Override
    public boolean isOpen() {
        if ( isTop() ) {
            Door otherPart = getLocation().getWorld().getBlockAt( getLocation().toBlockPosition().add( BlockPosition.DOWN ) );
            return otherPart.isOpen();
        }

        return this.open.getState();
    }

    @Override
    public void toggle() {
        if ( isTop() ) {
            Door otherPart = getLocation().getWorld().getBlockAt( getLocation().toBlockPosition().add( BlockPosition.DOWN ) );
            otherPart.toggle();
            return;
        }

        this.open.setState( !this.isOpen() );
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

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public void afterPlacement( PlacementData data ) {
        // TODO: Calculate proper state map
        data.setBlockIdentifier( new BlockIdentifier( data.getBlockIdentifier().getBlockId(), null, (short) 8 ) );

        Block above = this.location.getWorld().getBlockAt( this.location.toBlockPosition().add( BlockPosition.UP ) );
        above.setBlockFromPlacementData( data );
    }

    @Override
    public Class<? extends ItemStack>[] getToolInterfaces() {
        return ToolPresets.AXE;
    }

    @Override
    public void setFacing( Facing facing ) {
        this.facing.setState( facing );
    }

    @Override
    public Facing getFacing() {
        return this.facing.getState();
    }

}
