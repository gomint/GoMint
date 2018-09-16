package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.world.block.BlockCoralBlock;
import io.gomint.world.block.BlockType;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:coral_block" )
public class CoralBlock extends Block implements BlockCoralBlock {

    @Override
    public String getBlockId() {
        return "minecraft:coral_block";
    }

    @Override
    public long getBreakTime() {
        return 50;
    }

    @Override
    public Class<? extends ItemStack>[] getToolInterfaces() {
        return ToolPresets.PICKAXE;
    }

    @Override
    public float getBlastResistance() {
        return 30;
    }

    @Override
    public BlockType getType() {
        return BlockType.CORAL_BLOCK;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public CoralType getCoralType() {
        switch( this.getBlockData() ) {
            case 9:
                return CoralType.DEAD_HORN;
            case 8:
                return CoralType.DEAD_FIRE;
            case 7:
                return CoralType.DEAD_BUBBLE;
            case 6:
                return CoralType.DEAD_BRIAN;
            case 5:
                return CoralType.DEAD_TUBE;
            case 4:
                return CoralType.HORN;
            case 3:
                return CoralType.FIRE;
            case 2:
                return CoralType.BUBBLE;
            case 1:
                return CoralType.BRAIN;
            case 0:
            default:
                return CoralType.TUBE;
        }
    }

    @Override
    public void setCoralType( CoralType coralType ) {
        switch( coralType ) {
            case DEAD_HORN:
                this.setBlockData( (byte) 9 );
                break;
            case DEAD_FIRE:
                this.setBlockData( (byte) 8 );
                break;
            case DEAD_BUBBLE:
                this.setBlockData( (byte) 7 );
                break;
            case DEAD_BRIAN:
                this.setBlockData( (byte) 6 );
                break;
            case DEAD_TUBE:
                this.setBlockData( (byte) 5 );
                break;
            case HORN:
                this.setBlockData( (byte) 4 );
                break;
            case FIRE:
                this.setBlockData( (byte) 3 );
                break;
            case BUBBLE:
                this.setBlockData( (byte) 2 );
                break;
            case BRAIN:
                this.setBlockData( (byte) 1 );
                break;
            case TUBE:
            default:
                this.setBlockData( (byte) 0 );
                break;
        }
        this.updateBlock();
    }
}
