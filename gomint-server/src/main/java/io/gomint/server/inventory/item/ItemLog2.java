package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author KCodeYT
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:log2", id = 162 )
public class ItemLog2 extends ItemStack implements io.gomint.inventory.item.ItemLog2 {

    @Override
    public long getBurnTime() {
        return 15000;
    }

    @Override
    public ItemType getType() {
        return ItemType.LOG2;
    }

    private short getDirectionData() {
        return (short) ( this.getData() & 12 );
    }

    private short getTypeData() {
        return (short) ( this.getData() - this.getDirectionData() );
    }

    @Override
    public void setLogType( Type type ) {
        // Get old direction data
        short direction = this.getDirectionData();

        short newData = 0;
        switch ( type ) {
            case DARK_OAK:
                newData |= 1;
                break;
            default:
                break;
        }

        newData |= direction;
        this.setData( newData );
    }

    @Override
    public Type getLogType() {
        switch ( this.getTypeData() ) {
            case 0:
                return Type.ACACIA;
            case 1:
                return Type.DARK_OAK;
            default:
                return Type.ACACIA;
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

        this.setData( type );
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

}
