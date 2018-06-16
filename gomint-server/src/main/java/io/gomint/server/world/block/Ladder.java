package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.math.Vector;
import io.gomint.math.Vector2;
import io.gomint.server.entity.Entity;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.PlacementData;
import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.world.block.BlockType;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 65 )
public class Ladder extends Block implements io.gomint.world.block.BlockLadder {

    @Override
    public int getBlockId() {
        return 65;
    }

    @Override
    public long getBreakTime() {
        return 600;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public boolean canPassThrough() {
        return true;
    }

    @Override
    public void stepOn( Entity entity ) {
        // Reset fall distance
        entity.resetFallDistance();
    }

    @Override
    public float getBlastResistance() {
        return 2.0f;
    }

    @Override
    public BlockType getType() {
        return BlockType.LADDER;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public Class<? extends ItemStack>[] getToolInterfaces() {
        return ToolPresets.AXE;
    }

    @Override
    public void setAttachSide( AttachSide attachSide ) {
        switch ( attachSide ) {
            case EAST:
                this.setBlockData( (byte) 5 );
                break;
            case WEST:
                this.setBlockData( (byte) 4 );
                break;
            case SOUTH:
                this.setBlockData( (byte) 3 );
                break;
            case NORTH:
            default:
                this.setBlockData( (byte) 2 );
                break;
        }

        this.updateBlock();
    }

    @Override
    public AttachSide getAttachSide() {
        switch ( this.getBlockData() ) {
            case 5:
                return AttachSide.EAST;
            case 4:
                return AttachSide.WEST;
            case 3:
                return AttachSide.SOUTH;
            case 2:
            default:
                return AttachSide.NORTH;
        }
    }

    @Override
    public PlacementData calculatePlacementData( Entity entity, ItemStack item, Vector clickVector ) {
        PlacementData data = super.calculatePlacementData( entity, item, clickVector );

        Vector2 directionPlane = entity.getDirectionPlane();
        double xAbs = Math.abs( directionPlane.getX() );
        double zAbs = Math.abs( directionPlane.getZ() );

        if ( zAbs > xAbs ) {
            if ( directionPlane.getZ() > 0 ) {
                return data.setMetaData( (byte) 2 );
            } else {
                return data.setMetaData( (byte) 3 );
            }
        } else {
            if ( directionPlane.getX() > 0 ) {
                return data.setMetaData( (byte) 4 );
            } else {
                return data.setMetaData( (byte) 5 );
            }
        }
    }

}
