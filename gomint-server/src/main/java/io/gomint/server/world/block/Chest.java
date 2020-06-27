package io.gomint.server.world.block;

import io.gomint.inventory.Inventory;
import io.gomint.inventory.item.ItemStack;
import io.gomint.math.Vector;
import io.gomint.server.entity.Entity;
import io.gomint.server.entity.tileentity.ChestTileEntity;
import io.gomint.server.entity.tileentity.TileEntity;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.server.world.block.state.BlockfaceBlockState;
import io.gomint.server.world.block.state.DirectionBlockState;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.world.block.BlockChest;
import io.gomint.world.block.data.Direction;
import io.gomint.world.block.data.Facing;
import io.gomint.world.block.BlockType;

import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:chest" )
public class Chest extends ContainerBlock implements BlockChest {

    private final BlockfaceBlockState direction = new BlockfaceBlockState( this, () -> new String[]{"facing_direction"} );

    @Override
    public String getBlockId() {
        return "minecraft:chest";
    }

    @Override
    public long getBreakTime() {
        return 3750;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public boolean interact(Entity entity, Facing face, Vector facePos, ItemStack item ) {
        ChestTileEntity tileEntity = this.getTileEntity();
        if ( tileEntity != null ) {
            tileEntity.interact( entity, face, facePos, item );
        }

        return true;
    }

    @Override
    public Inventory getInventory() {
        ChestTileEntity tileEntity = this.getTileEntity();
        if ( tileEntity != null ) {
            return tileEntity.getInventory();
        }

        return null;
    }

    @Override
    public boolean needsTileEntity() {
        return true;
    }

    @Override
    TileEntity createTileEntity( NBTTagCompound compound ) {
        return new ChestTileEntity( this );
    }

    @Override
    public float getBlastResistance() {
        return 12.5f;
    }

    @Override
    public BlockType getType() {
        return BlockType.CHEST;
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
    public List<ItemStack> getDrops( ItemStack itemInHand ) {
        List<ItemStack> items = super.getDrops( itemInHand );

        // We also drop the inventory
        ChestTileEntity chestTileEntity = this.getTileEntity();
        for ( ItemStack itemStack : chestTileEntity.getInventory().getContentsArray() ) {
            if ( itemStack != null ) {
                items.add( itemStack );
            }
        }

        return items;
    }

    @Override
    public void setFacing( Facing facing ) {
        this.direction.setState( facing );
    }

    @Override
    public Facing getFacing() {
        return this.direction.getState();
    }

}
