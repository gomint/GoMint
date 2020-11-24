/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.block.state.BooleanBlockState;
import io.gomint.server.world.block.state.EnumBlockState;
import io.gomint.world.block.BlockCoral;
import io.gomint.world.block.BlockType;
import io.gomint.world.block.data.CoralType;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:coral" )
public class Coral extends Block implements BlockCoral {

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

    private static final BooleanBlockState DEAD = new BooleanBlockState(() -> new String[]{"dead_bit"});
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
    public BlockType getBlockType() {
        return BlockType.CORAL;
    }

    @Override
    public void setDead(boolean dead) {
        DEAD.setState(this, dead);
    }

    @Override
    public boolean isDead() {
        return DEAD.getState(this);
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

}
