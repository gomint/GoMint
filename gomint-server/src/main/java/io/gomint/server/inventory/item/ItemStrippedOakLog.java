package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author KCodeYT
 * @version 1.0
 */
@RegisterInfo( id = -10 )
public class ItemStrippedOakLog extends ItemStack implements io.gomint.inventory.item.ItemStrippedOakLog {

    @Override
    public String getBlockId() {
        return "minecraft:stripped_oak_log";
    }

    @Override
    public ItemType getType() {
        return ItemType.STRIPPED_OAK_LOG;
    }

    @Override
    public void setLogDirection( Direction direction ) {
        short type = 0;

        switch ( direction ) {
            case EAST_WEST:
                type = 1;
                break;
            case NORTH_SOUTH:
                type = 2;
                break;
            case BARK:
                type = 3;
                break;
            default:
                break;
        }

        this.setData( type );
    }

    @Override
    public Direction getLogDirection() {
        short direction = this.getData();

        switch ( direction ) {
            case 0:
                return Direction.UP_DOWN;
            case 1:
                return Direction.EAST_WEST;
            case 2:
                return Direction.NORTH_SOUTH;
            case 3:
                return Direction.BARK;
            default:
                return Direction.UP_DOWN;
        }
    }

}
