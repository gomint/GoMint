package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.math.Vector;
import io.gomint.server.entity.Entity;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.entity.tileentity.FurnaceTileEntity;
import io.gomint.server.entity.tileentity.TileEntity;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.PlacementData;
import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.server.world.block.state.FacingBlockState;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.world.block.BlockFace;
import io.gomint.world.block.BlockType;
import lombok.EqualsAndHashCode;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 61 )
@EqualsAndHashCode( callSuper = true )
public class Furnace extends Block implements io.gomint.world.block.BlockFurnace {

    private FacingBlockState facing = new FacingBlockState();

    @Override
    public int getBlockId() {
        return 61;
    }

    @Override
    public long getBreakTime() {
        return 5250;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public float getBlastResistance() {
        return 17.5f;
    }

    @Override
    public BlockType getType() {
        return BlockType.FURNACE;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public Class<? extends ItemStack>[] getToolInterfaces() {
        return ToolPresets.PICKAXE;
    }

    @Override
    TileEntity createTileEntity( NBTTagCompound compound ) {
        super.createTileEntity( compound );
        return new FurnaceTileEntity( compound, this.world );
    }

    @Override
    public boolean needsTileEntity() {
        return true;
    }

    @Override
    public boolean interact( Entity entity, BlockFace face, Vector facePos, ItemStack item ) {
        FurnaceTileEntity tileEntity = this.getTileEntity();
        tileEntity.interact( entity, face, facePos, item );

        return true;
    }

    @Override
    public PlacementData calculatePlacementData( Entity entity, ItemStack item, Vector clickVector ) {
        this.facing.detectFromPlayer( (EntityPlayer) entity );

        this.calculateBlockData();
        return new PlacementData( this.getBlockId(), this.getBlockData(), null );
    }

    @Override
    public void generateBlockStates() {
        this.facing.fromData( (byte) ( this.getBlockData() & 0x04 ) );
    }

    @Override
    public void calculateBlockData() {
        this.resetBlockData();
        this.addToBlockData( (byte) ( this.facing.toData() << 1 ) );
    }

}
