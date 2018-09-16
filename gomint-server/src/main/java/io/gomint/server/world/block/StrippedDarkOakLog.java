package io.gomint.server.world.block;

import io.gomint.GoMint;
import io.gomint.inventory.item.ItemStack;
import io.gomint.inventory.item.ItemStrippedDarkOakLog;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.world.block.BlockStrippedDarkOakLog;
import io.gomint.world.block.BlockType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author KCodeYT
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:stripped_dark_oak_log" )
public class StrippedDarkOakLog extends Block implements BlockStrippedDarkOakLog {

    @Override
    public String getBlockId() {
        return "minecraft:stripped_dark_oak_log";
    }

    @Override
    public long getBreakTime() {
        return 3000;
    }

    @Override
    public Class<? extends ItemStack>[] getToolInterfaces() {
        return ToolPresets.AXE;
    }

    @Override
    public float getBlastResistance() {
        return 10.0f;
    }

    @Override
    public BlockType getType() {
        return BlockType.STRIPPED_DARK_OAK_LOG;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public void setLogDirection( Direction direction ) {
        short type = this.getBlockData();

        switch ( direction ) {
            case UP_DOWN:
                type = 0;
                break;
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

        this.setBlockData( type );
        this.updateBlock();
    }

    @Override
    public Direction getLogDirection() {
        short direction = this.getBlockData();

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

    @Override
    public List<ItemStack> getDrops( ItemStack itemInHand ) {
        return new ArrayList<ItemStack>() {{
            add( GoMint.instance().createItemStack( ItemStrippedDarkOakLog.class, 1 ) );
        }};
    }

}
