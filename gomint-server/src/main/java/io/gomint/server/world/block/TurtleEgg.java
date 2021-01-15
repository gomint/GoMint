/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.block;

import io.gomint.math.MathUtils;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.block.state.DirectValueBlockState;
import io.gomint.server.world.block.state.EnumBlockState;
import io.gomint.world.block.BlockTurtleEgg;
import io.gomint.world.block.BlockType;
import io.gomint.world.block.data.CrackStatus;

@RegisterInfo(sId = "minecraft:turtle_egg")
public class TurtleEgg extends Block implements BlockTurtleEgg {

    private static final DirectValueBlockState<String> EGG_COUNT = new DirectValueBlockState<>(() -> new String[]{"turtle_egg_count"}, "one_egg");
    private static final EnumBlockState<CrackStatus, String> CRACK_STATUS = new EnumBlockState<>(k -> new String[]{"cracked_state"}, CrackStatus.values(),
        crackStatus -> crackStatus.name().toLowerCase(), s -> CrackStatus.valueOf(s.toUpperCase()));

    @Override
    public long breakTime() {
        return 750;
    }

    @Override
    public float getBlastResistance() {
        return 0.5f;
    }

    @Override
    public BlockType blockType() {
        return BlockType.TURTLE_EGG;
    }

    @Override
    public BlockTurtleEgg amountOfEggs(int amountOfEggs) {
        int capped = MathUtils.clamp(amountOfEggs, 1, 4);
        switch (capped) {
            case 1:
                EGG_COUNT.setState(this, "one_egg");
                break;
            case 2:
                EGG_COUNT.setState(this, "two_egg");
                break;
            case 3:
                EGG_COUNT.setState(this, "three_egg");
                break;
            default:
            case 4:
                EGG_COUNT.setState(this, "four_egg");
                break;
        }

        return this;
    }

    @Override
    public int amountOfEggs() {
        switch (EGG_COUNT.getState(this)) {
            case "one_egg":
                return 1;
            case "two_egg":
                return 2;
            case "three_egg":
                return 3;
            default:
            case "four_egg":
                return 4;
        }
    }

    @Override
    public BlockTurtleEgg crackStatus(CrackStatus status) {
        CRACK_STATUS.setState(this, status);
        return this;
    }

    @Override
    public CrackStatus crackStatus() {
        return CRACK_STATUS.getState(this);
    }

}
