package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.math.AxisAlignedBB;
import io.gomint.math.BlockPosition;
import io.gomint.math.Vector;
import io.gomint.server.entity.Entity;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.PlacementData;
import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.server.world.block.state.BooleanBlockState;
import io.gomint.server.world.block.state.FacingBlockState;
import io.gomint.world.block.BlockFace;
import io.gomint.world.block.BlockType;

import java.util.Collections;
import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:trapdoor" )
public class Trapdoor extends Block implements io.gomint.world.block.BlockTrapdoor {

    private FacingBlockState facing = new FacingBlockState();
    private BooleanBlockState opened = new BooleanBlockState();
    private BooleanBlockState top = new BooleanBlockState();

    @Override
    public boolean isOpen() {
        return ( getBlockData() & 0x04 ) == 0x04;
    }

    @Override
    public void toggle() {
        setBlockData( (byte) ( getBlockData() ^ 0x04 ) );
        updateBlock();
    }

    @Override
    public boolean interact( Entity entity, BlockFace face, Vector facePos, ItemStack item ) {
        toggle();
        return true;
    }

    @Override
    public void generateBlockStates() {
        // Facing
        this.facing.fromData( (byte) ( this.getBlockData() & 0x03 ) );

        // Top
        this.top.fromData( (byte) ( ( this.getBlockData() >> 2 ) & 0x01 ) );

        // Open
        this.opened.fromData( (byte) ( ( this.getBlockData() >> 3 ) & 0x01 ) );
    }

    @Override
    public PlacementData calculatePlacementData( Entity entity, ItemStack item, Vector clickVector ) {
        return super.calculatePlacementData( entity, item, clickVector );
    }

    @Override
    public String getBlockId() {
        return "minecraft:trapdoor";
    }

    @Override
    public long getBreakTime() {
        return 4500;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public float getBlastResistance() {
        return 15.0f;
    }

    @Override
    public BlockType getType() {
        return BlockType.TRAPDOOR;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public List<AxisAlignedBB> getBoundingBox() {
        float defaultHeight = 0.1875f;

        // Basis box
        AxisAlignedBB bb;
        if ( this.top.getState() ) {
            // Top closed box
            bb = new AxisAlignedBB(
                this.location.getX(),
                this.location.getY() + 1 - defaultHeight,
                this.location.getZ(),
                this.location.getX() + 1,
                this.location.getY() + 1,
                this.location.getZ() + 1
            );
        } else {
            // Bottom closed box
            bb = new AxisAlignedBB(
                this.location.getX(),
                this.location.getY(),
                this.location.getZ(),
                this.location.getX() + 1,
                this.location.getY() + defaultHeight,
                this.location.getZ() + 1
            );
        }

        // Check for open state
        if ( this.opened.getState() ) {
            switch ( this.facing.getState() ) {
                case NORTH:
                    bb.setBounds(
                        this.location.getX(),
                        this.location.getY(),
                        this.location.getZ() + 1 - defaultHeight,
                        this.location.getX() + 1,
                        this.location.getY() + 1,
                        this.location.getZ() + 1
                    );

                    break;

                case SOUTH:
                    bb.setBounds(
                        this.location.getX(),
                        this.location.getY(),
                        this.location.getZ(),
                        this.location.getX() + 1,
                        this.location.getY() + 1,
                        this.location.getZ() + defaultHeight
                    );

                    break;

                case WEST:
                    bb.setBounds(
                        this.location.getX() + 1 - defaultHeight,
                        this.location.getY(),
                        this.location.getZ(),
                        this.location.getX() + 1,
                        this.location.getY() + 1,
                        this.location.getZ() + 1
                    );

                    break;

                case EAST:
                    bb.setBounds(
                        this.location.getX(),
                        this.location.getY(),
                        this.location.getZ(),
                        this.location.getX() + defaultHeight,
                        this.location.getY() + 1,
                        this.location.getZ() + 1
                    );

                    break;
            }
        }

        return Collections.singletonList( bb );
    }

    @Override
    public Class<? extends ItemStack>[] getToolInterfaces() {
        return ToolPresets.AXE;
    }
}
