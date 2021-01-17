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
    public float blastResistance() {
        return 0.1f;
    }

    @Override
    public BlockType blockType() {
        return BlockType.CORAL_FAN;
    }

    @Override
    public RotationDirection rotation() {
        return DIRECTION.state(this);
    }

    @Override
    public BlockCoralFan rotation(RotationDirection direction) {
        DIRECTION.state(this, direction);
        return this;
    }

    @Override
    public BlockCoralFan coralType(CoralType type) {
        CoralTypeMagic state = CoralTypeMagic.valueOf(type.name());
        COLOR.state(this, state);
        return this;
    }

    @Override
    public CoralType coralType() {
        return CoralType.valueOf(COLOR.state(this).name());
    }

    @Override
    public boolean dead() {
        return this.blockId().equals("minecraft:coral_fan_dead");
    }

    @Override
    public BlockCoralFan dead(boolean dead) {
        if (dead) {
            this.blockId("minecraft:coral_fan_dead");
        } else {
            this.blockId("minecraft:coral_fan");
        }

        return this;
    }

}
