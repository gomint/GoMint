package io.gomint.server.world.block;

import com.google.common.collect.Lists;
import io.gomint.inventory.item.ItemBed;
import io.gomint.inventory.item.ItemStack;
import io.gomint.math.AxisAlignedBB;
import io.gomint.math.Location;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.entity.tileentity.BedTileEntity;
import io.gomint.server.entity.tileentity.TileEntity;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.util.Bearing;
import io.gomint.server.world.block.state.BooleanBlockState;
import io.gomint.server.world.block.state.DirectionBlockState;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.world.block.BlockBed;
import io.gomint.world.block.BlockType;
import io.gomint.world.block.data.BlockColor;
import io.gomint.world.block.data.Direction;
import io.gomint.world.block.data.Facing;

import java.util.Collections;
import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:bed")
public class Bed extends Block implements BlockBed {

    private static final DirectionBlockState DIRECTION = new DirectionBlockState(() -> new String[]{"direction"});
    private static final BooleanBlockState OCCUPIED = new BooleanBlockState(() -> new String[]{"occupied_bit"});
    private static final BooleanBlockState HEAD = new BooleanBlockState(() -> new String[]{"head_piece_bit"});

    @Override
    public String getBlockId() {
        return "minecraft:bed";
    }

    @Override
    public long getBreakTime() {
        return 300;
    }

    @Override
    public boolean transparent() {
        return true;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public boolean onBreak(boolean creative) {
        Bed otherHalf = (Bed) this.getOtherHalf();
        if (otherHalf != null) {
            otherHalf.blockType(Air.class);
        }

        return true;
    }

    @Override
    public float getBlastResistance() {
        return 1.0f;
    }

    @Override
    public BlockType blockType() {
        return BlockType.BED;
    }

    private io.gomint.world.block.Block getOtherBlock() {
        // Select which side we need to check
        Direction facingToOtherHalf = DIRECTION.getState(this);
        if (this.isHeadPart()) {
            facingToOtherHalf = facingToOtherHalf.opposite();
        }

        return this.getSide(facingToOtherHalf);
    }

    @Override
    public BlockColor getColor() {
        BedTileEntity tileEntity = this.getTileEntity();
        return tileEntity.getColor();
    }

    @Override
    public void setColor(BlockColor color) {
        BedTileEntity tileEntity = this.getTileEntity();
        tileEntity.setColor(color);

        this.updateBlock();
    }

    @Override
    public BlockBed getOtherHalf() {
        io.gomint.world.block.Block otherHalf = getOtherBlock();

        // Check if other part is a bed
        if (otherHalf != null && otherHalf.blockType() == BlockType.BED) {
            Bed otherBedHalf = (Bed) otherHalf;
            if (otherBedHalf.isHeadPart() != this.isHeadPart()) {
                return otherBedHalf;
            }
        }

        return null;
    }

    @Override
    public boolean isHeadPart() {
        return HEAD.getState(this);
    }

    @Override
    public void setHeadPart(boolean value) {
        HEAD.setState(this, value);
    }

    @Override
    public boolean beforePlacement(EntityLiving entity, ItemStack item, Facing face, Location location) {
        // We need to check if we are placed on a solid block
        Block block = (Block) location.getWorld().blockAt(location.toBlockPosition()).side(Facing.DOWN);
        if (block.solid()) {
            Bearing bearing = Bearing.fromAngle(entity.getYaw());

            // Check for other block
            Block other = block.getSide(bearing.toDirection());
            if (!other.solid()) {
                return false;
            }

            Block replacingHead = other.side(Facing.UP);
            return replacingHead.canBeReplaced(item);
        }

        return false;
    }

    @Override
    public void afterPlacement() {
        Block otherBlock = (Block) this.getOtherBlock();
        if (otherBlock != null) {
            Bed bed = otherBlock.blockType(Bed.class);
            bed.setColor(this.getColor());
            bed.setHeadPart(true);
        }

        super.afterPlacement();
    }

    @Override
    public boolean needsTileEntity() {
        return true;
    }

    @Override
    TileEntity createTileEntity(NBTTagCompound compound) {
        super.createTileEntity(compound);
        return this.tileEntities.construct(BedTileEntity.class, compound, this, this.items);
    }

    @Override
    public List<ItemStack> drops(ItemStack itemInHand) {
        ItemBed bed = ItemBed.create(1);
        bed.setColor(((BedTileEntity) this.getTileEntity()).getColor());
        return Lists.newArrayList(bed);
    }

    @Override
    public List<AxisAlignedBB> boundingBoxes() {
        return Collections.singletonList(new AxisAlignedBB(
            this.location.getX(),
            this.location.getY(),
            this.location.getZ(),
            this.location.getX() + 1,
            this.location.getY() + 0.5625f,
            this.location.getZ() + 1
        ));
    }

}
