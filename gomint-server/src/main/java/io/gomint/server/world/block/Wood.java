package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemFlintAndSteel;
import io.gomint.inventory.item.ItemStack;
import io.gomint.math.Vector;
import io.gomint.server.entity.Entity;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.world.block.BlockFace;
import io.gomint.world.block.BlockType;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:planks" )
public class Wood extends Block implements io.gomint.world.block.BlockWood {

    @Override
    public String getBlockId() {
        return "minecraft:planks";
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
        return 15.0f;
    }

    @Override
    public BlockType getType() {
        return BlockType.WOOD;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public WoodType getWoodType() {
        switch ( this.getBlockData() ) {
            case 5:
                return WoodType.DARK_OAK;
            case 4:
                return WoodType.ACACIA;
            case 3:
                return WoodType.JUNGLE;
            case 2:
                return WoodType.BIRCH;
            case 1:
                return WoodType.SPRUCE;
            case 0:
            default:
                return WoodType.OAK;
        }
    }

    @Override
    public void setWoodType( WoodType woodType ) {
        switch ( woodType ) {
            case DARK_OAK:
                this.setBlockData( (byte) 5 );
                break;
            case ACACIA:
                this.setBlockData( (byte) 4 );
                break;
            case JUNGLE:
                this.setBlockData( (byte) 3 );
                break;
            case BIRCH:
                this.setBlockData( (byte) 2 );
                break;
            case SPRUCE:
                this.setBlockData( (byte) 1 );
                break;
            case OAK:
            default:
                this.setBlockData( (byte) 0 );
                break;
        }

        this.updateBlock();
    }

}
