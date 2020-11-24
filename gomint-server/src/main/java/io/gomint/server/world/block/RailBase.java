/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.math.BlockPosition;
import io.gomint.math.Location;
import io.gomint.math.Vector;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.world.block.state.EnumBlockState;
import io.gomint.world.block.BlockType;
import io.gomint.world.block.data.Facing;

public abstract class RailBase extends Block {

    private static final String[] RAIL_DIRECTION_KEY = new String[]{"rail_direction"};

    protected enum RailDirection {
        NORTH_SOUTH,
        EAST_WEST,
        ASCENDING_EAST,
        ASCENDING_WEST,
        ASCENDING_NORTH,
        ASCENDING_SOUTH,
        SOUTH_EAST,
        SOUTH_WEST,
        NORTH_WEST,
        NORTH_EAST;

        public boolean isAscending() {
            return this == ASCENDING_NORTH || this == ASCENDING_EAST || this == ASCENDING_SOUTH || this == ASCENDING_WEST;
        }
    }

    protected static final EnumBlockState<RailDirection, Integer> RAIL_DIRECTION = new EnumBlockState<>(
        v -> RAIL_DIRECTION_KEY,
        RailDirection.values(),
        Enum::ordinal,
        v -> RailDirection.values()[v]
    );

    public boolean isRailBlock(Block block) {
        return block.getBlockType() == BlockType.RAIL || block.getBlockType() == BlockType.POWERED_RAIL ||
            block.getBlockType() == BlockType.DETECTOR_RAIL || block.getBlockType() == BlockType.ACTIVATOR_RAIL;
    }

    @Override
    public boolean beforePlacement(EntityLiving entity, ItemStack item, Facing face, Location location) {
        Block block = entity.getWorld().getBlockAt(location.toBlockPosition().add(Vector.DOWN.toBlockPosition()));
        return block.isSolid();
    }

    private RailBase findRailAt(BlockPosition pos) {
        Block block = this.world.getBlockAt(pos);

        if (this.isRailBlock(block)) {
            return (RailBase) block;
        } else {
            Block otherBlock = this.world.getBlockAt(pos.clone().add(BlockPosition.UP));
            if (this.isRailBlock(otherBlock)) {
                return (RailBase) otherBlock;
            } else {
                otherBlock = this.world.getBlockAt(pos.clone().add(BlockPosition.DOWN));
                return this.isRailBlock(otherBlock) ? (RailBase) otherBlock : null;
            }
        }
    }

}
