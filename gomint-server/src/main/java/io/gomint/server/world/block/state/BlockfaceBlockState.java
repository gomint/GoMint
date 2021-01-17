/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.block.state;

import io.gomint.inventory.item.ItemStack;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.world.block.Block;
import io.gomint.world.block.data.Facing;

import java.util.function.Supplier;

/**
 * @author geNAZt
 * @version 1.0
 */
public class BlockfaceBlockState extends BlockState<Facing, Integer> {

    protected final boolean detectUpDown;

    public BlockfaceBlockState(Supplier<String[]> key) {
        this(key, false);
    }

    public BlockfaceBlockState(Supplier<String[]> key, boolean detectUpDown) {
        super(v -> key.get());
        this.detectUpDown = detectUpDown;
    }

    @Override
    protected void calculateValueFromState(Block block, Facing state) {
        switch (state) {
            case DOWN:
            default:
                this.value(block, 0);
                return;
            case UP:
                this.value(block, 1);
                return;
            case NORTH:
                this.value(block, 2);
                return;
            case SOUTH:
                this.value(block, 3);
                return;
            case WEST:
                this.value(block, 4);
                return;
            case EAST:
                this.value(block, 5);
        }
    }

    @Override
    public void detectFromPlacement(Block newBlock, EntityLiving<?> player, ItemStack<?> placedItem, Facing face) {
        if (face == null) {
            this.state(newBlock, Facing.NORTH);
            return;
        }

        if (!this.detectUpDown && (face == Facing.UP || face == Facing.DOWN)) {
            face = Facing.NORTH;
        }

        this.state(newBlock, face);
    }

    @Override
    public Facing state(Block block) {
        switch (this.value(block)) {
            case 0:
            default:
                return Facing.DOWN;
            case 1:
                return Facing.UP;
            case 2:
                return Facing.NORTH;
            case 3:
                return Facing.SOUTH;
            case 4:
                return Facing.WEST;
            case 5:
                return Facing.EAST;
        }
    }

}
