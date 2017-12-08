package io.gomint.server.world.block;

import io.gomint.world.block.BlockType;

import io.gomint.server.entity.tileentity.SignTileEntity;
import io.gomint.server.entity.tileentity.TileEntity;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.world.block.BlockWallSign;

import java.util.ArrayList;
import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 68 )
public class WallSign extends Block implements BlockWallSign {

    @Override
    public int getBlockId() {
        return 68;
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
        return new SignTileEntity( compound, this.world );
    }

    @Override
    public List<String> getLines() {
        SignTileEntity sign = this.getTileEntity();
        return new ArrayList<>( sign.getLines() );
    }

    @Override
    public void setLine( int line, String content ) {
        // Silenty fail when line is incorrect
        if ( line > 4 || line < 1 ) {
            return;
        }

        SignTileEntity sign = this.getTileEntity();
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
            return "";
        }

        SignTileEntity sign = this.getTileEntity();
        return sign.getLines().get( line - 1 );
    }

    @Override
    public float getBlastResistance() {
        return 5.0f;
    }

    @Override
    public BlockType getType() {
        return BlockType.WALL_SIGN;
    }

}