package io.gomint.server.world.block;

import io.gomint.world.block.BlockType;

import io.gomint.inventory.item.*;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockWood;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 5 )
public class Wood extends Block implements io.gomint.world.block.BlockWood {

    @Override
    public byte getBlockId() {
        return 5;
    }

    @Override
    public long getBreakTime() {
        return 3000;
    }

    @Override
    public Class<? extends ItemStack>[] getToolInterfaces() {
        return new Class[]{
                ItemWoodenAxe.class,
                ItemIronAxe.class,
                ItemDiamondAxe.class,
                ItemGoldenAxe.class,
                ItemStoneAxe.class
        };
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
    public void setWoodType( Type type ) {
        switch ( type ) {
            case OAK:
                this.setBlockData( (byte) 0 );
                break;
            case SPRUCE:
                this.setBlockData( (byte) 1 );
                break;
            case BIRCH:
                this.setBlockData( (byte) 2 );
                break;
            case JUNGLE:
                this.setBlockData( (byte) 3 );
                break;
            case ACACIA:
                this.setBlockData( (byte) 4 );
                break;
            case DARK_OAK:
                this.setBlockData( (byte) 5 );
                break;
                default:
                    this.setBlockData( (byte) 0 );


        }
    }

    @Override
    public Type getWoodType() {
        switch ( this.getBlockData() ) {
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
}
