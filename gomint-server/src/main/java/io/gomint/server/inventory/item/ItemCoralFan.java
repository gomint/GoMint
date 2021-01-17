/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.Block;
import io.gomint.world.block.BlockCoralFan;
import io.gomint.world.block.data.CoralType;
import io.gomint.world.block.data.RotationDirection;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:coral_fan", def = true )
@RegisterInfo(sId = "minecraft:coral_fan_dead")
public class ItemCoralFan extends ItemStack< io.gomint.inventory.item.ItemCoralFan> implements io.gomint.inventory.item.ItemCoralFan {

    @Override
    public ItemType itemType() {
        return ItemType.CORAL_FAN;
    }

    @Override
    public RotationDirection direction() {
        return this.data() >= 8 ? RotationDirection.NORTH_SOUTH : RotationDirection.EAST_WEST;
    }

    @Override
    public ItemCoralFan direction(RotationDirection direction) {
        if (direction == RotationDirection.NORTH_SOUTH) {
            if (this.data() < 8) {
                this.data((short) (this.data() + 8));
            }
        } else {
            if (this.data() >= 8) {
                this.data((short) (this.data() - 8));
            }
        }

        return this;
    }

    @Override
    public ItemCoralFan coralType(CoralType type) {
        short data = (short) type.ordinal();
        if (this.direction() == RotationDirection.NORTH_SOUTH) {
            data += 8;
        }

        this.data(data);
        return this;
    }

    @Override
    public CoralType coralType() {
        short type = (short) (this.data() % 8);
        return CoralType.values()[type];
    }

    @Override
    public boolean dead() {
        return this.material().equals("minecraft:coral_fan_dead");
    }

    @Override
    public ItemCoralFan dead(boolean dead) {
        if (dead) {
            this.material("minecraft:coral_fan_dead");
        } else {
            this.material("minecraft:coral_fan");
        }

        return this;
    }

    @Override
    public Block block() {
        BlockCoralFan block = (BlockCoralFan) super.block();
        block.coralType(this.coralType());
        block.rotation(this.direction());
        block.dead(this.dead());
        return block;
    }

}
