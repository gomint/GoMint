/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.block.state;

import io.gomint.inventory.item.ItemStack;
import io.gomint.math.Vector;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.util.Bearing;
import io.gomint.server.world.block.Block;
import io.gomint.world.block.data.Facing;

import java.util.function.Supplier;

/**
 * @author geNAZt
 * @version 1.0
 */
public class BlockfaceBlockState extends BlockState<Facing, Integer> {

    protected final boolean detectUpDown;

    public BlockfaceBlockState(Block block, Supplier<String> key) {
        this(block, key, false);
    }

    public BlockfaceBlockState(Block block, Supplier<String> key, boolean detectUpDown) {
        super(block, key);
        this.detectUpDown = detectUpDown;
    }

    @Override
    protected void calculateValueFromState(Facing state) {
        switch (this.getState()) {
            case DOWN:
            default:
                this.setValue(0);
                return;
            case UP:
                this.setValue(1);
                return;
            case NORTH:
                this.setValue(2);
                return;
            case SOUTH:
                this.setValue(3);
                return;
            case WEST:
                this.setValue(4);
                return;
            case EAST:
                this.setValue(5);
        }
    }

    @Override
    public void detectFromPlacement(EntityPlayer player, ItemStack placedItem, Facing face, Block block, Block clickedBlock, Vector clickPosition) {
        if (this.detectUpDown) {
            if (player.getPitch() < -60) {
                this.setState(Facing.DOWN);
                return;
            } else if (player.getPitch() > 60) {
                this.setState(Facing.UP);
                return;
            }
        }

        Bearing bearing = Bearing.fromAngle(player.getYaw());
        bearing = bearing.opposite();
        this.setState(bearing.toBlockFace());
    }

    @Override
    public Facing getState() {
        switch (this.getValue()) {
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
