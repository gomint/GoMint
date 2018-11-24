package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemLeaves;
import io.gomint.inventory.item.ItemShears;
import io.gomint.inventory.item.ItemStack;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.block.state.BooleanBlockState;
import io.gomint.server.world.block.state.EnumBlockState;
import io.gomint.world.block.BlockLeaves;
import io.gomint.world.block.BlockType;
import io.gomint.world.block.data.WoodType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:leaves", def = true )
@RegisterInfo( sId = "minecraft:leaves2", def = true )
public class Leaves extends Block implements BlockLeaves {

    private enum LeaveType {
        OAK,
        SPRUCE,
        BIRCH,
        JUNGLE
    }

    private enum Leave2Type {
        ACACIA,
        DARK_OAK
    }

    private EnumBlockState<LeaveType> variantLeave = new EnumBlockState<>( this, LeaveType.values(), states -> this.getBlockId().equals( "minecraft:leaves" ) );
    private EnumBlockState<Leave2Type> variantLeave2 = new EnumBlockState<>( this, Leave2Type.values(), states -> this.getBlockId().equals( "minecraft:leaves2" ) );

    private BooleanBlockState noDecay = new BooleanBlockState( this, states -> true, 2 );
    private BooleanBlockState checkDecay = new BooleanBlockState( this, states -> true, 3 );
    private BooleanBlockState noAndCheckDecay = new BooleanBlockState( this, states -> true, 4 );

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
    public List<ItemStack> getDrops( ItemStack itemInHand ) {
        return new ArrayList<ItemStack>() {{
            if ( isCorrectTool( itemInHand ) ) {
                add( ItemLeaves.create( 1 ) );
            }
        }};
    }

    @Override
    public Class<? extends ItemStack>[] getToolInterfaces() {
        return new Class[]{
            ItemShears.class
        };
    }

    @Override
    public void setWoodType( WoodType type ) {
        switch ( type ) {
            case OAK:
                this.setBlockId( "minecraft:leaves" );
                this.variantLeave.setState( LeaveType.OAK );
                break;
            case BIRCH:
                this.setBlockId( "minecraft:leaves" );
                this.variantLeave.setState( LeaveType.BIRCH );
                break;
            case JUNGLE:
                this.setBlockId( "minecraft:leaves" );
                this.variantLeave.setState( LeaveType.JUNGLE );
                break;
            case SPRUCE:
                this.setBlockId( "minecraft:leaves" );
                this.variantLeave.setState( LeaveType.SPRUCE );
                break;
            case ACACIA:
                this.setBlockId( "minecraft:leaves2" );
                this.variantLeave2.setState( Leave2Type.ACACIA );
                break;
            case DARK_OAK:
                this.setBlockId( "minecraft:leaves2" );
                this.variantLeave2.setState( Leave2Type.DARK_OAK );
                break;
        }
    }

    @Override
    public WoodType getWoodType() {
        switch ( this.getBlockId() ) {
            case "minecraft:leaves":
                switch ( this.variantLeave.getState() ) {
                    case OAK:
                        return WoodType.OAK;
                    case SPRUCE:
                        return WoodType.SPRUCE;
                    case JUNGLE:
                        return WoodType.JUNGLE;
                    case BIRCH:
                        return WoodType.BIRCH;
                }

                break;
            case "minecraft:leaves2":
                switch ( this.variantLeave2.getState() ) {
                    case ACACIA:
                        return WoodType.ACACIA;
                    case DARK_OAK:
                        return WoodType.DARK_OAK;
                }

                break;
        }

        return WoodType.OAK;
    }

}
