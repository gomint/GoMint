package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemShears;
import io.gomint.inventory.item.ItemStack;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.block.state.BlockState;
import io.gomint.world.block.BlockLeaves;
import io.gomint.world.block.BlockType;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:leaves" )
public class Leaves extends Block implements BlockLeaves {

    private BlockState<BlockLeaves.Type> type = new BlockState<Type>() {
        @Override
        public short toData() {
            switch ( this.getState() ) {
                case OAK:
                    return 0;
                case SPRUCE:
                    return 1;
                case BIRCH:
                    return 2;
            }

            return 3;
        }

        @Override
        public void fromData( short data ) {
            switch ( data ) {
                case 0:
                    this.setState( Type.OAK );
                    break;
                case 1:
                    this.setState( Type.SPRUCE );
                    break;
                case 2:
                    this.setState( Type.BIRCH );
                    break;
                case 3:
                    this.setState( Type.JUNGLE );
                    break;
            }
        }
    };

    @Override
    public void generateBlockStates() {
        this.type.fromData( (byte) ( this.getBlockData() & 0x03 ) );
    }

    @Override
    public void calculateBlockData() {
        this.setBlockData( this.type.toData() );
    }

    @Override
    public String getBlockId() {
        return "minecraft:leaves";
    }

    @Override
    public long getBreakTime() {
        return 300;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public float getBlastResistance() {
        return 1.0f;
    }

    @Override
    public BlockType getType() {
        return BlockType.LEAVES;
    }

    @Override
    public Class<? extends ItemStack>[] getToolInterfaces() {
        return new Class[]{
            ItemShears.class
        };
    }

    @Override
    public void setLeaveType( Type type ) {
        this.type.setState( type );
        this.updateBlock();
    }

    @Override
    public Type getLeaveType() {
        return this.type.getState();
    }

}
