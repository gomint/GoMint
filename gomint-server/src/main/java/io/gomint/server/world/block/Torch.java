package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.math.AxisAlignedBB;
import io.gomint.math.Location;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.block.state.BlockfaceBlockState;
import io.gomint.server.world.block.state.EnumBlockState;
import io.gomint.world.block.BlockTorch;
import io.gomint.world.block.BlockType;
import io.gomint.world.block.data.Facing;

import java.util.Collections;
import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:torch")
public class Torch extends Block implements BlockTorch {

    private enum FacingMagic {
        DOWN("down"),
        UP("top"),
        EAST("east"),
        WEST("west"),
        NORTH("north"),
        SOUTH("south");

        private final String direction;

        FacingMagic(String direction) {
            this.direction = direction;
        }
    }

    private static final EnumBlockState<FacingMagic, String> FACING = new EnumBlockState<>(v -> new String[]{"torch_facing_direction"}, FacingMagic.values(), v -> v.direction, s -> {
        for (FacingMagic value : FacingMagic.values()) {
            if (value.direction.equals(s)) {
                return value;
            }
        }

        return null;
    });

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public float getBlastResistance() {
        return 0.0f;
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.TORCH;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public long getBreakTime() {
        return 0;
    }

    @Override
    public boolean canPassThrough() {
        return true;
    }

    @Override
    public List<AxisAlignedBB> getBoundingBox() {
        float size = 0.15f;

        switch (FACING.getState(this)) {
            case EAST:
                return Collections.singletonList(new AxisAlignedBB(
                    this.location.getX(),
                    this.location.getY() + 0.2f,
                    this.location.getZ() + 0.5f - size,
                    this.location.getX() + size * 2f,
                    this.location.getY() + 0.8f,
                    this.location.getZ() + 0.5f + size
                ));
            case WEST:
                return Collections.singletonList(new AxisAlignedBB(
                    this.location.getX() + 1.0f - size * 2f,
                    this.location.getY() + 0.2f,
                    this.location.getZ() + 0.5f - size,
                    this.location.getX() + 1f,
                    this.location.getY() + 0.8f,
                    this.location.getZ() + 0.5f + size
                ));
            case SOUTH:
                return Collections.singletonList(new AxisAlignedBB(
                    this.location.getX() + 0.5f - size,
                    this.location.getY() + 0.2f,
                    this.location.getZ(),
                    this.location.getX() + 0.5f + size,
                    this.location.getY() + 0.8f,
                    this.location.getZ() + size * 2f
                ));
            case NORTH:
                return Collections.singletonList(new AxisAlignedBB(
                    this.location.getX() + 0.5f - size,
                    this.location.getY() + 0.2f,
                    this.location.getZ() + 1f - size * 2f,
                    this.location.getX() + 0.5f + size,
                    this.location.getY() + 0.8f,
                    this.location.getZ() + 1f
                ));
        }

        size = 0.1f;
        return Collections.singletonList(new AxisAlignedBB(
            this.location.getX() + 0.5f - size,
            this.location.getY() + 0.0f,
            this.location.getZ() + 0.5f - size,
            this.location.getX() + 0.5f + size,
            this.location.getY() + 0.6f,
            this.location.getZ() + 0.5f + size
        ));
    }

    @Override
    public boolean beforePlacement(EntityLiving entity, ItemStack item, Facing face, Location location) {
        if (!this.getSide(face).isTransparent()) {
            FACING.setState(this, FacingMagic.valueOf(face.name()));
            return true;
        }

        return false;
    }

}
