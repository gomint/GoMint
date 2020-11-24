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
@RegisterInfo( sId = "minecraft:coral_fan", id = -133, def = true )
@RegisterInfo(sId = "minecraft:coral_fan_dead", id = -134)
public class ItemCoralFan extends ItemStack implements io.gomint.inventory.item.ItemCoralFan {

    @Override
    public ItemType getItemType() {
        return ItemType.CORAL_FAN;
    }

    @Override
    public RotationDirection getDirection() {
        return this.getData() >= 8 ? RotationDirection.NORTH_SOUTH : RotationDirection.EAST_WEST;
    }

    @Override
    public void setDirection(RotationDirection direction) {
        if (direction == RotationDirection.NORTH_SOUTH) {
            if (this.getData() < 8) {
                this.setData((short) (this.getData() + 8));
            }
        } else {
            if (this.getData() >= 8) {
                this.setData((short) (this.getData() - 8));
            }
        }
    }

    @Override
    public void setCoralType(CoralType type) {
        short data = (short) type.ordinal();
        if (this.getDirection() == RotationDirection.NORTH_SOUTH) {
            data += 8;
        }

        this.setData(data);
    }

    @Override
    public CoralType getCoralType() {
        short type = (short) (this.getData() % 8);
        return CoralType.values()[type];
    }

    @Override
    public boolean isDead() {
        return this.getMaterial().equals("minecraft:coral_fan_dead");
    }

    @Override
    public void setDead(boolean dead) {
        if (dead) {
            this.setMaterial("minecraft:coral_fan_dead");
        } else {
            this.setMaterial("minecraft:coral_fan");
        }
    }

    @Override
    public Block getBlock() {
        BlockCoralFan block = (BlockCoralFan) super.getBlock();
        block.setCoralType(this.getCoralType());
        block.setDirection(this.getDirection());
        block.setDead(this.isDead());
        return block;
    }

}
