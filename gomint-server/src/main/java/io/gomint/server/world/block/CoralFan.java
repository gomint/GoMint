/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.block.state.EnumBlockState;
import io.gomint.server.world.block.state.RotationDirectionBlockState;
import io.gomint.world.block.BlockCoralFan;
import io.gomint.world.block.BlockType;
import io.gomint.world.block.data.CoralType;
import io.gomint.world.block.data.RotationDirection;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:coral_fan", def = true )
@RegisterInfo( sId = "minecraft:coral_fan_dead" )
public class CoralFan extends Block implements BlockCoralFan {

    private enum CoralTypeMagic {
        TUBE("blue"),
        BRAIN("pink"),
        BUBBLE("purple"),
        FIRE("red"),
        HORN("yellow"),
        ;

        private final String color;
        CoralTypeMagic(String color) {
            this.color = color;
        }
    }

    private static final RotationDirectionBlockState DIRECTION = new RotationDirectionBlockState(() -> new String[]{"coral_fan_direction"});
    private static final EnumBlockState<CoralTypeMagic, String> COLOR = new EnumBlockState<>(t -> new String[]{"coral_color"},
        CoralTypeMagic.values(), coralTypeMagic -> coralTypeMagic.color, s -> {
        for (CoralTypeMagic value : CoralTypeMagic.values()) {
            if (value.color.equals(s)) {
                return value;
            }
        }

        return null;
    });

    @Override
    public float getBlastResistance() {
        return 0.1f;
    }

    @Override
    public BlockType blockType() {
        return BlockType.CORAL_FAN;
    }

    @Override
    public RotationDirection getDirection() {
        return DIRECTION.getState(this);
    }

    @Override
    public void setDirection(RotationDirection direction) {
        DIRECTION.setState(this, direction);
    }

    @Override
    public void setCoralType(CoralType type) {
        CoralTypeMagic state = CoralTypeMagic.valueOf(type.name());
        COLOR.setState(this, state);
    }

    @Override
    public CoralType getCoralType() {
        return CoralType.valueOf(COLOR.getState(this).name());
    }

    @Override
    public boolean isDead() {
        return this.getBlockId().equals("minecraft:coral_fan_dead");
    }

    @Override
    public void setDead(boolean dead) {
        if (dead) {
            this.setBlockId("minecraft:coral_fan_dead");
        } else {
            this.setBlockId("minecraft:coral_fan");
        }
    }

}
