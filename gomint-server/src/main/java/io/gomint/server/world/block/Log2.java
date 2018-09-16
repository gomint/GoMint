package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.math.Vector;
import io.gomint.server.entity.Entity;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.world.block.BlockFace;
import io.gomint.world.block.BlockLog2;
import io.gomint.world.block.BlockType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author KCodeYT
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:log2" )
public class Log2 extends Block implements BlockLog2 {

    @Override
    public String getBlockId() {
        return "minecraft:log2";
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
        return BlockType.LOG2;
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
    public boolean interact( Entity entity, BlockFace face, Vector facePos, ItemStack item ) {
        if ( entity instanceof EntityPlayer && this.isCorrectTool( item ) ) {
            switch ( this.getTypeData() ) {
                case 1:
                    this.setType( StrippedDarkOakLog.class );
                    break;
                case 0:
                default:
                    this.setType( StrippedAcaciaLog.class );
                    break;
            }

            this.updateBlock();

            return true;
        }

        return false;
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
        this.setBlockData( (byte) newData );
        this.updateBlock();
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
    public Class<? extends ItemStack>[] getToolInterfaces() {
        return ToolPresets.AXE;
    }

    @Override
    public List<ItemStack> getDrops( ItemStack itemInHand ) {
        // Strip away direction meta
        return new ArrayList<ItemStack>() {{
            add( world.getServer().getItems().create( getBlockId(), getTypeData(), (byte) 1, null ) );
        }};
    }

}
