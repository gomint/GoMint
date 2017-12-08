package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockType;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 155 )
public class BlockOfQuartz extends Block implements io.gomint.world.block.BlockBlockOfQuartz {

    @Override
    public int getBlockId() {
        return 155;
    }

    @Override
    public long getBreakTime() {
        return 1200;
    }

    @Override
    public float getBlastResistance() {
        return 4.0f;
    }

    @Override
    public BlockType getType() {
        return BlockType.BLOCK_OF_QUARTZ;
    }

    @Override
    public Variant getVariant() {
        switch ( this.getBlockData() ) {
            case 0:
                return Variant.NORMAL;
            case 1:
                return Variant.CHISELED;
            case 2:
                return Variant.VERTICAL_PILLAR;
            case 3:
                return Variant.NORTH_SOUTH_PILLAR;
            case 4:
            default:
                return Variant.EAST_WEST_PILLAR;
        }
    }

    @Override
    public void setVariant( Variant variant ) {
        switch ( variant ) {
            case CHISELED:
                this.setBlockData( (byte) 1 );
                break;
            case VERTICAL_PILLAR:
                this.setBlockData( (byte) 2 );
                break;
            case NORTH_SOUTH_PILLAR:
                this.setBlockData( (byte) 3 );
                break;
            case EAST_WEST_PILLAR:
                this.setBlockData( (byte) 4 );
                break;
            case NORMAL:
            default:
                this.setBlockData( (byte) 0 );
        }
    }

}
