package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.world.block.BlockType;

import io.gomint.server.entity.tileentity.SignTileEntity;
import io.gomint.server.entity.tileentity.TileEntity;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.world.block.BlockSign;

import java.util.ArrayList;
import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:standing_sign" )
public class Sign extends Block implements BlockSign {

    @Override
    public String getBlockId() {
        return "minecraft:standing_sign";
    }

    @Override
    public long getBreakTime() {
        return 1500;
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
    public boolean needsTileEntity() {
        return true;
    }

    @Override
    TileEntity createTileEntity( NBTTagCompound compound ) {
        super.createTileEntity( compound );
        return new SignTileEntity( compound, this.world, this.world.getServer().getItems() );
    }

    @Override
    public List<String> getLines() {
        SignTileEntity sign = this.getTileEntity();
        if ( sign != null ) {
            return new ArrayList<>( sign.getLines() );
        }

        return null;
    }

    @Override
    public void setLine( int line, String content ) {
        // Silenty fail when line is incorrect
        if ( line > 4 || line < 1 ) {
            return;
        }

        SignTileEntity sign = this.getTileEntity();
        if ( sign == null ) {
            return;
        }

        if ( sign.getLines().size() < line ) {
            for ( int i = 0; i < line - sign.getLines().size(); i++ ) {
                sign.getLines().add( "" );
            }
        }

        sign.getLines().set( line - 1, content );
        this.updateBlock();
    }

    @Override
    public String getLine( int line ) {
        // Silenty fail when line is incorrect
        if ( line > 4 || line < 1 ) {
            return null;
        }

        SignTileEntity sign = this.getTileEntity();
        if ( sign == null ) {
            return null;
        }

        if ( sign.getLines().size() < line ) {
            return null;
        }

        return sign.getLines().get( line - 1 );
    }

    @Override
    public float getBlastResistance() {
        return 5.0f;
    }

    @Override
    public BlockType getType() {
        return BlockType.SIGN;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public Class<? extends ItemStack>[] getToolInterfaces() {
        return ToolPresets.AXE;
    }

}
