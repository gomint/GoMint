package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemTallGrass;
import io.gomint.inventory.item.ItemShears;
import io.gomint.inventory.item.ItemStack;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 31 )
public class TallGrass extends Block implements io.gomint.world.block.BlockTallGrass {

    @Override
    public int getBlockId() {
        return 31;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public boolean canPassThrough() {
        return true;
    }

    @Override
    public long getBreakTime() {
        return 0;
    }

    @Override
    public float getBlastResistance() {
        return 0.0f;
    }

    @Override
    public BlockType getType() {
        return BlockType.TALL_GRASS;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public List<ItemStack> getDrops( ItemStack itemInHand ) {
        if( isCorrectTool( itemInHand ) ) {
            return new ArrayList<ItemStack>() {{
                add( ItemTallGrass.create( 1 ) );
            }};
        }

        return new ArrayList<>();
    }

    @Override
    public Class<? extends ItemStack>[] getToolInterfaces() {
        return new Class[]{
            ItemShears.class
        };
    }

    @Override
    public void setGrassType( Type type ) {
        switch ( type ) {
            case DEAD_BUSH:
                this.setBlockData( (byte) 0 );
                break;
            case GRASS:
                this.setBlockData( (byte) 1 );
                break;
            case FERN:
                this.setBlockData( (byte) 2 );
                break;
        }

        this.updateBlock();
    }

    @Override
    public Type getGrassType() {
        switch ( this.getBlockData() ) {
            case 0:
                return Type.DEAD_BUSH;
            case 1:
                return Type.GRASS;
            case 2:
                return Type.FERN;
        }

        return null;
    }

}
