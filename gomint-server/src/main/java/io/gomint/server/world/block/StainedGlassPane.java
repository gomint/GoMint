package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockType;
import io.gomint.world.block.data.BlockColor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 160 )
public class StainedGlassPane extends Block implements io.gomint.world.block.BlockStainedGlassPane {

    @Override
    public int getBlockId() {
        return 160;
    }

    @Override
    public long getBreakTime() {
        return 450;
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
    public List<ItemStack> getDrops( ItemStack itemInHand ) {
        return new ArrayList<>();
    }

    @Override
    public float getBlastResistance() {
        return 1.5f;
    }

    @Override
    public BlockType getType() {
        return BlockType.STAINED_GLASS_PANE;
    }

    @Override
    public BlockColor getColor() {
        switch ( this.getBlockData() ) {
            case 0:
                return BlockColor.WHITE;
            case 1:
                return BlockColor.ORANGE;
            case 2:
                return BlockColor.MAGENTA;
            case 3:
                return BlockColor.LIGHT_BLUE;
            case 4:
                return BlockColor.YELLOW;
            case 5:
                return BlockColor.LIME;
            case 6:
                return BlockColor.PINK;
            case 7:
                return BlockColor.GRAY;
            case 8:
                return BlockColor.LIGHT_GRAY;
            case 9:
                return BlockColor.CYAN;
            case 10:
                return BlockColor.PURPLE;
            case 11:
                return BlockColor.BLUE;
            case 12:
                return BlockColor.BROWN;
            case 13:
                return BlockColor.GREEN;
            case 14:
                return BlockColor.RED;
            default:
                return BlockColor.BLACK;

        }
    }

    @Override
    public void setColor( BlockColor color ) {
        switch ( color ) {
            case WHITE:
                this.setBlockData( (byte) 0 );
                return;
            case ORANGE:
                this.setBlockData( (byte) 1 );
                return;
            case MAGENTA:
                this.setBlockData( (byte) 2 );
                return;
            case LIGHT_BLUE:
                this.setBlockData( (byte) 3 );
                return;
            case YELLOW:
                this.setBlockData( (byte) 4 );
                return;
            case LIME:
                this.setBlockData( (byte) 5 );
                return;
            case PINK:
                this.setBlockData( (byte) 6 );
                return;
            case GRAY:
                this.setBlockData( (byte) 7 );
                return;
            case LIGHT_GRAY:
                this.setBlockData( (byte) 8 );
                return;
            case CYAN:
                this.setBlockData( (byte) 9 );
                return;
            case PURPLE:
                this.setBlockData( (byte) 10 );
                return;
            case BLUE:
                this.setBlockData( (byte) 11 );
                return;
            case BROWN:
                this.setBlockData( (byte) 12 );
                return;
            case GREEN:
                this.setBlockData( (byte) 13 );
                return;
            case RED:
                this.setBlockData( (byte) 14 );
                return;
            default:
            case BLACK:
                this.setBlockData( (byte) 15 );
        }
    }

}
