/*
 * Copyright (c) 2020 Gomint Team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.server.world.block.state.BooleanBlockState;
import io.gomint.server.world.block.state.DirectionBlockState;
import io.gomint.world.block.BlockFenceGate;
import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.data.Direction;
import io.gomint.world.block.data.LogType;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:fence_gate", def = true)
@RegisterInfo(sId = "minecraft:spruce_fence_gate")
@RegisterInfo(sId = "minecraft:birch_fence_gate")
@RegisterInfo(sId = "minecraft:jungle_fence_gate")
@RegisterInfo(sId = "minecraft:dark_oak_fence_gate")
@RegisterInfo(sId = "minecraft:acacia_fence_gate")
@RegisterInfo(sId = "minecraft:warped_fence_gate")
@RegisterInfo(sId = "minecraft:crimson_fence_gate")
public class FenceGate extends Block implements BlockFenceGate {

    private final DirectionBlockState DIRECTION = new DirectionBlockState( () -> new String[]{"direction"});
    private final BooleanBlockState OPEN = new BooleanBlockState( () -> new String[]{"open_bit"});
    private final BooleanBlockState IN_WALL = new BooleanBlockState( () -> new String[]{"in_wall_bit"});

    @Override
    public long breakTime() {
        return 3000;
    }

    @Override
    public boolean transparent() {
        return true;
    }

    @Override
    public float getBlastResistance() {
        return 15.0f;
    }

    @Override
    public BlockType blockType() {
        return BlockType.FENCE_GATE;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public Class<? extends ItemStack<?>>[] getToolInterfaces() {
        return ToolPresets.AXE;
    }

    @Override
    public BlockFenceGate toggle() {
        OPEN.setState(this, !this.open());
        return this;
    }

    @Override
    public boolean open() {
        return OPEN.getState(this);
    }

    @Override
    public LogType type() {
        switch (this.getBlockId()) {
            case "minecraft:fence_gate":
                return LogType.OAK;
            case "minecraft:spruce_fence_gate":
                return LogType.SPRUCE;
            case "minecraft:birch_fence_gate":
                return LogType.BIRCH;
            case "minecraft:jungle_fence_gate":
                return LogType.DARK_OAK;
            case "minecraft:dark_oak_fence_gate":
                return LogType.JUNGLE;
            case "minecraft:acacia_fence_gate":
                return LogType.ACACIA;
            case "minecraft:warped_fence_gate":
                return LogType.WARPED;
            case "minecraft:crimson_fence_gate":
                return LogType.CRIMSON;
        }

        return LogType.OAK;
    }

    @Override
    public BlockFenceGate type(LogType logType) {
        switch (logType) {
            case OAK:
                this.setBlockId("minecraft:fence_gate");
                break;
            case SPRUCE:
                this.setBlockId("minecraft:spruce_fence_gate");
                break;
            case BIRCH:
                this.setBlockId("minecraft:birch_fence_gate");
                break;
            case DARK_OAK:
                this.setBlockId("minecraft:dark_oak_fence_gate");
                break;
            case JUNGLE:
                this.setBlockId("minecraft:jungle_fence_gate");
                break;
            case ACACIA:
                this.setBlockId("minecraft:acacia_fence_gate");
                break;
            case WARPED:
                this.setBlockId("minecraft:warped_fence_gate");
                break;
            case CRIMSON:
                this.setBlockId("minecraft:crimson_fence_gate");
                break;
        }

        return this;
    }

    @Override
    public BlockFenceGate direction(Direction direction) {
        DIRECTION.setState(this, direction);
        return this;
    }

    @Override
    public Direction direction() {
        return DIRECTION.getState(this);
    }

}
