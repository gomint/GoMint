package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemSkull;
import io.gomint.inventory.item.ItemStack;
import io.gomint.math.AxisAlignedBB;
import io.gomint.math.Location;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.entity.tileentity.SkullTileEntity;
import io.gomint.server.entity.tileentity.TileEntity;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.block.state.BlockfaceFromPlayerBlockState;
import io.gomint.server.world.block.state.EnumBlockState;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.world.block.BlockSkull;
import io.gomint.world.block.BlockType;
import io.gomint.world.block.data.Direction;
import io.gomint.world.block.data.Facing;
import io.gomint.world.block.data.SkullType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:skull")
public class Skull extends Block implements BlockSkull {

    private static final BlockfaceFromPlayerBlockState DIRECTION = new BlockfaceFromPlayerBlockState(() -> new String[]{"facing_direction"}, true);

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
        return Collections.singletonList(new AxisAlignedBB(
            this.location.getX() + 0.25f,
            this.location.getY(),
            this.location.getZ() + 0.25f,
            this.location.getX() + 0.75f,
            this.location.getY() + 0.5f,
            this.location.getZ() + 0.75f
        ));
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
    public boolean beforePlacement(EntityLiving entity, ItemStack item, Facing face, Location location) {
        DIRECTION.detectFromPlacement(this, entity, item, face);

        // We skip downwards facing
        if (face == Facing.DOWN || face == Facing.UP) {
            DIRECTION.setState(this, Facing.UP);
        }

        SkullTileEntity tileEntity = this.getTileEntity();
        tileEntity.setRotation(entity.getYaw());
        return super.beforePlacement(entity, item, face, location);
    }

    @Override
    TileEntity createTileEntity(NBTTagCompound compound) {
        super.createTileEntity(compound);
        return this.tileEntities.construct(SkullTileEntity.class, compound, this, this.items);
    }

    @Override
    public List<ItemStack> getDrops(ItemStack itemInHand) {
        ItemSkull item = ItemSkull.create(1);
        item.setSkullType(this.getSkullType());
        return Collections.singletonList(item);
    }

    @Override
    public float getBlastResistance() {
        return 5.0f;
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.SKULL;
    }

    @Override
    public SkullType getSkullType() {
        SkullTileEntity entity = this.getTileEntity();
        return entity.getSkullType();
    }

    @Override
    public void setSkullType(SkullType type) {
        SkullTileEntity entity = this.getTileEntity();
        entity.setSkullType(type);
    }

    @Override
    public void setDirection(Direction direction) {
        if (direction == null) {
            DIRECTION.setState(this, Facing.UP);
        } else {
            DIRECTION.setState(this, direction.toFacing());
        }
    }

    @Override
    public Direction getDirection() {
        Facing facing = DIRECTION.getState(this);
        if ( facing == Facing.UP ) {
            return null;
        }

        return facing.toDirection();
    }

}
