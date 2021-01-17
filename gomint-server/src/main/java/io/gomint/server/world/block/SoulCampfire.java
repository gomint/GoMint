/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.math.Location;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.server.world.block.state.BooleanBlockState;
import io.gomint.server.world.block.state.DirectionBlockState;
import io.gomint.world.block.BlockSoulCampfire;
import io.gomint.world.block.BlockType;
import io.gomint.world.block.data.Direction;
import io.gomint.world.block.data.Facing;

/**
 * @author KingAli
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:soul_campfire" )
public class SoulCampfire extends Block implements BlockSoulCampfire {

    private static final DirectionBlockState DIRECTION = new DirectionBlockState(() -> new String[]{"direction"}); // Rotation is always clockwise
    private static final BooleanBlockState EXTINGUISHED = new BooleanBlockState(() -> new String[]{"extinguished"});

    @Override
    public boolean beforePlacement(EntityLiving<?> entity, ItemStack<?> item, Facing face, Location location) {
        super.beforePlacement(entity, item, face, location);
        DIRECTION.detectFromPlacement(this, entity, item, face);
        EXTINGUISHED.state(this, false);
        return true;
    }

    @Override
    public long breakTime() {
        return 3000;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public float blastResistance() {
        return 10.0f;
    }

    @Override
    public Class<? extends ItemStack<?>>[] toolInterfaces() {
        return ToolPresets.AXE;
    }

    @Override
    public BlockType blockType() {
        return BlockType.SOUL_CAMPFIRE;
    }

    @Override
    public BlockSoulCampfire direction(Direction direction) {
        DIRECTION.state(this, direction);
        return this;
    }

    @Override
    public Direction direction() {
        return DIRECTION.state(this);
    }

    @Override
    public BlockSoulCampfire extinguished(boolean value) {
        EXTINGUISHED.state(this, value);
        return this;
    }

    @Override
    public boolean isExtinguished() {
        return EXTINGUISHED.state(this);
    }

}
