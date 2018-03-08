package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.server.inventory.item.Items;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockLog;
import io.gomint.world.block.BlockType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 17 )
public class Log extends Block implements BlockLog {

    @Override
    public byte getBlockId() {
        return 17;
    }

    @Override
    public long getBreakTime() {
        return 3000;
    }

    @Override
    public float getBlastResistance() {
        return 10.0f;
    }

    @Override
    public BlockType getType() {
        return BlockType.LOG;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    private short getDirectionData() {
        return (short) ( this.getBlockData() & 12 );
    }

    private short getTypeData() {
        return (short) ( this.getBlockData() - this.getDirectionData() );
    }

    @Override
    public void setLogType( Type type ) {
        // Get old direction data
        short direction = this.getDirectionData();

        short newData = 0;
        switch ( type ) {
            case SPRUCE:
                newData |= 1;
                break;
            case BIRCH:
                newData |= 2;
                break;
            case JUNGLE:
                newData |= 3;
                break;
            case ACACIA:
                newData |= 4;
            case DARK_OAK:
                newData |= 5;
            default:
                break;
        }

        newData |= direction;
        this.setBlockData( (byte) newData );
        this.updateBlock();
    }

    @Override
    public Type getLogType() {
        switch ( this.getTypeData() ) {
            case 0:
                return Type.OAK;
            case 1:
                return Type.SPRUCE;
            case 2:
                return Type.BIRCH;
            case 3:
                return Type.JUNGLE;
            case 4:
                return Type.ACACIA;
            case 5:
                return Type.DARK_OAK;
            default:
                return Type.OAK;
        }
    }

    @Override
    public void setLogDirection( Direction direction ) {
        short type = this.getTypeData();

        switch ( direction ) {
            case EAST_WEST:
                type |= 4;
                break;
            case NORTH_SOUTH:
                type |= 8;
                break;
            case BARK:
                type |= 12;
                break;
            default:
                break;
        }

        this.setBlockData( (byte) type );
        this.updateBlock();
    }

    @Override
    public Direction getLogDirection() {
        short direction = this.getDirectionData();

        switch ( direction ) {
            case 0:
                return Direction.UP_DOWN;
            case 4:
                return Direction.EAST_WEST;
            case 8:
                return Direction.NORTH_SOUTH;
            case 12:
                return Direction.BARK;
            default:
                return Direction.UP_DOWN;
        }
    }

    @Override
    public List<ItemStack> getDrops( ItemStack itemInHand ) {
        // Strip away direction meta
        return new ArrayList<ItemStack>() {{
            add( Items.create( getBlockId() & 0xFF, getTypeData(), (byte) 1, null ) );
        }};
    }

}
