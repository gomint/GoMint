package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.server.entity.Entity;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.server.world.block.state.BlockfaceBlockState;
import io.gomint.world.block.BlockLadder;
import io.gomint.world.block.data.Facing;
import io.gomint.world.block.BlockType;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:ladder" )
public class Ladder extends Block implements BlockLadder {

    private static final BlockfaceBlockState ATTACHED = new BlockfaceBlockState( () -> new String[]{"facing_direction"} );

    @Override
    public String getBlockId() {
        return "minecraft:ladder";
    }

    @Override
    public long getBreakTime() {
        return 600;
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
    public void stepOn( Entity entity ) {
        // Reset fall distance
        entity.resetFallDistance();
    }

    @Override
    public float getBlastResistance() {
        return 2.0f;
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.LADDER;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public Class<? extends ItemStack>[] getToolInterfaces() {
        return ToolPresets.AXE;
    }

    @Override
    public void setAttachSide( Facing attachSide ) {
        ATTACHED.setState( this, attachSide );
    }

    @Override
    public Facing getAttachSide() {
        return ATTACHED.getState(this);
    }

}
