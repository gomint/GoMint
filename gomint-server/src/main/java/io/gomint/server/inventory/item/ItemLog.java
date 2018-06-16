package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 17 )
public class ItemLog extends ItemStack implements io.gomint.inventory.item.ItemLog {

    // CHECKSTYLE:OFF
    public ItemLog( short data, int amount ) {
        super( 17, data, amount );
    }

    public ItemLog( short data, int amount, NBTTagCompound nbt ) {
        super( 17, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.LOG;
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
            case SPRUCE:
                newData |= 1;
                break;
            case BIRCH:
                newData |= 2;
                break;
            case JUNGLE:
                newData |= 3;
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
                return Type.OAK;
            case 1:
                return Type.SPRUCE;
            case 2:
                return Type.BIRCH;
            case 3:
                return Type.JUNGLE;
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
