package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.math.AxisAlignedBB;
import io.gomint.math.MojangRotation;
import io.gomint.math.Vector;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.entity.tileentity.SkullTileEntity;
import io.gomint.server.entity.tileentity.TileEntity;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.PlacementData;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.world.block.data.Facing;
import io.gomint.world.block.BlockType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:skull" )
public class Skull extends Block implements io.gomint.world.block.BlockSkull {

    @Override
    public long getBreakTime() {
        return 1500;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public List<AxisAlignedBB> getBoundingBox() {
        return Collections.singletonList( new AxisAlignedBB(
            this.location.getX() + 0.25f,
            this.location.getY(),
            this.location.getZ() + 0.25f,
            this.location.getX() + 0.75f,
            this.location.getY() + 0.5f,
            this.location.getZ() + 0.75f
        ) );
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public boolean needsTileEntity() {
        return true;
    }

    @Override
    public PlacementData calculatePlacementData(EntityPlayer entity, ItemStack item, Facing face, Block block, Block clickedBlock, Vector clickVector ) {
        PlacementData data = super.calculatePlacementData( entity, item, face, block, clickedBlock, clickVector );

        NBTTagCompound compound = new NBTTagCompound( "" );
        compound.addValue( "Rot", MojangRotation.fromEntityForBlock( entity ).getRotationValue() );
        return data.setCompound( compound );
    }

    @Override
    TileEntity createTileEntity( NBTTagCompound compound ) {
        super.createTileEntity( compound );

        SkullTileEntity tileEntity = new SkullTileEntity( this );
        tileEntity.setSkullType( (byte) this.getBlockData() );
        return tileEntity;
    }

    @Override
    public List<ItemStack> getDrops( ItemStack itemInHand ) {
        return new ArrayList<ItemStack>() {{
            add( world.getServer().getItems().create( 397, getBlockData(), (byte) 1, null ) );
        }};
    }

    @Override
    public float getBlastResistance() {
        return 5.0f;
    }

    @Override
    public BlockType getType() {
        return BlockType.SKULL;
    }

}
