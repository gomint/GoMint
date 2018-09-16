package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.math.Vector;
import io.gomint.server.entity.Entity;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.UpdateReason;
import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.world.block.BlockFace;
import io.gomint.world.block.BlockType;

import java.util.concurrent.TimeUnit;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:wooden_button" )
public class WoodenButton extends Block implements io.gomint.world.block.BlockWoodenButton {

    @Override
    public String getBlockId() {
        return "minecraft:wooden_button";
    }

    @Override
    public long getBreakTime() {
        return 750;
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
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public boolean interact( Entity entity, BlockFace face, Vector facePos, ItemStack item ) {
        // Press the button
        this.press();

        return true;
    }

    @Override
    public long update( UpdateReason updateReason, long currentTimeMS, float dT ) {
        if ( updateReason == UpdateReason.SCHEDULED && isPressed() ) {
            this.setBlockData( (byte) ( this.getBlockData() ^ 0x08 ) );
            this.updateBlock();
        }

        return -1;
    }

    public boolean isPressed() {
        return ( this.getBlockData() & 0x08 ) == 0x08;
    }

    public void press() {
        // Check if we need to update
        if ( !isPressed() ) {
            this.setBlockData( (byte) ( this.getBlockData() ^ 0x08 ) );
            this.updateBlock();
        }

        // Schedule release in 1 second
        this.world.scheduleBlockUpdate( this.location, 1, TimeUnit.SECONDS );
    }

    @Override
    public float getBlastResistance() {
        return 2.5f;
    }

    @Override
    public BlockType getType() {
        return BlockType.WOODEN_BUTTON;
    }

    @Override
    public Class<? extends ItemStack>[] getToolInterfaces() {
        return ToolPresets.AXE;
    }

}
