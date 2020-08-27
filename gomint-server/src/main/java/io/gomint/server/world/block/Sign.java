package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.server.entity.tileentity.SignTileEntity;
import io.gomint.server.entity.tileentity.TileEntity;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.server.world.block.state.SignDirectionBlockState;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.world.block.BlockSign;
import io.gomint.world.block.BlockType;
import io.gomint.world.block.data.LogType;
import io.gomint.world.block.data.SignDirection;

import java.util.ArrayList;
import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:standing_sign", def = true)
@RegisterInfo(sId = "minecraft:jungle_standing_sign")
@RegisterInfo(sId = "minecraft:acacia_standing_sign")
@RegisterInfo(sId = "minecraft:birch_standing_sign")
@RegisterInfo(sId = "minecraft:spruce_standing_sign")
@RegisterInfo(sId = "minecraft:darkoak_standing_sign")
@RegisterInfo(sId = "minecraft:crimson_standing_sign")
@RegisterInfo(sId = "minecraft:warped_standing_sign")
public class Sign extends Block implements BlockSign {

    private enum LogTypeMagic {
        OAK("minecraft:standing_sign"),
        SPRUCE("minecraft:spruce_standing_sign"),
        BIRCH("minecraft:birch_standing_sign"),
        JUNGLE("minecraft:jungle_standing_sign"),
        ACACIA("minecraft:acacia_standing_sign"),
        DARK_OAK("minecraft:darkoak_standing_sign"),
        CRIMSON("minecraft:crimson_standing_sign"),
        WARPED("minecraft:warped_standing_sign");

        private final String blockId;
        LogTypeMagic(String blockId) {
            this.blockId = blockId;
        }
    }

    private static final SignDirectionBlockState DIRECTION = new SignDirectionBlockState(() -> new String[]{"ground_sign_direction"});
    
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
        return this.world.getServer().getTileEntities().construct(SignTileEntity.class, compound, this, this.world.getServer().getItems());
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
    public LogType getWoodType() {
        for (LogTypeMagic value : LogTypeMagic.values()) {
            if (value.blockId.equals(this.getBlockId())) {
                return LogType.valueOf(value.name());
            }
        }

        return null;
    }

    @Override
    public void setWoodType(LogType logType) {
        LogTypeMagic newState = LogTypeMagic.valueOf(logType.name());
        this.setBlockId(newState.blockId);
    }

    @Override
    public SignDirection getSignDirection() {
        return DIRECTION.getState(this);
    }

    @Override
    public void setSignDirection(SignDirection direction) {
        DIRECTION.setState(this, direction);
    }

    @Override
    public float getBlastResistance() {
        return 5.0f;
    }

    @Override
    public BlockType getBlockType() {
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
