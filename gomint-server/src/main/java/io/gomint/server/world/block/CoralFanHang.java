/*
 * Copyright (c) 2018 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.block.state.BooleanBlockState;
import io.gomint.server.world.block.state.DirectionBlockState;
import io.gomint.world.block.BlockCoralFanHang;
import io.gomint.world.block.BlockType;
import io.gomint.world.block.data.CoralType;
import io.gomint.world.block.data.Direction;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:coral_fan_hang", def = true) // tube, brain
@RegisterInfo(sId = "minecraft:coral_fan_hang2") // bubble, fire
@RegisterInfo(sId = "minecraft:coral_fan_hang3") // horn
public class CoralFanHang extends Block implements BlockCoralFanHang {

    private static final BooleanBlockState TYPE = new BooleanBlockState(() -> new String[]{"coral_hang_type_bit"});
    private static final DirectionBlockState DIRECTION = new DirectionBlockState(() -> new String[]{"coral_direction"});
    private static final BooleanBlockState DEAD = new BooleanBlockState(() -> new String[]{"dead_bit"});

    private enum CoralTypeMagic {
        TUBE("minecraft:coral_fan_hang", false),
        BRAIN("minecraft:coral_fan_hang", true),
        BUBBLE("minecraft:coral_fan_hang2", false),
        FIRE("minecraft:coral_fan_hang2", true),
        HORN("minecraft:coral_fan_hang3", false);

        private final String id;
        private final boolean type;

        CoralTypeMagic(String id, boolean type) {
            this.id = id;
            this.type = type;
        }
    }

    @Override
    public float getBlastResistance() {
        return 0.1f;
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.CORAL_FAN_HANG;
    }

    @Override
    public Direction getDirection() {
        return DIRECTION.getState(this);
    }

    @Override
    public void setDirection(Direction direction) {
        DIRECTION.setState(this, direction);
    }

    @Override
    public void setCoralType(CoralType type) {
        CoralTypeMagic m = CoralTypeMagic.valueOf(type.name());
        this.setBlockId(m.id);
        TYPE.setState(this, m.type);
    }

    @Override
    public CoralType getCoralType() {
        boolean type = TYPE.getState(this);
        for (CoralTypeMagic value : CoralTypeMagic.values()) {
            if (value.type == type && value.id.equals(this.getBlockId())) {
                return CoralType.valueOf(value.name());
            }
        }

        return CoralType.TUBE;
    }

    @Override
    public boolean isDead() {
        return DEAD.getState(this);
    }

    @Override
    public void setDead(boolean dead) {
        DEAD.setState(this, dead);
    }

}
