/*
 * Copyright (c) 2018, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.Block;
import io.gomint.world.block.BlockCoralFanHang;
import io.gomint.world.block.data.CoralType;
import io.gomint.world.block.data.Direction;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:coral_fan_hang", id = -135, def = true)
@RegisterInfo(sId = "minecraft:coral_fan_hang2", id = -136)
@RegisterInfo(sId = "minecraft:coral_fan_hang3", id = -137)
public class ItemCoralFanHang extends ItemStack implements io.gomint.inventory.item.ItemCoralFanHang {

    private enum DirectionMagic {
        SOUTH((short) 12),
        NORTH((short) 8),
        EAST((short) 4),
        WEST((short) 0);

        private final short value;

        DirectionMagic(short value) {
            this.value = value;
        }
    }

    private enum CoralTypeMagic {
        TUBE("minecraft:coral_fan_hang", (short) 0),
        BRAIN("minecraft:coral_fan_hang", (short) 1),
        BUBBLE("minecraft:coral_fan_hang2", (short) 0),
        FIRE("minecraft:coral_fan_hang2", (short) 1),
        HORN("minecraft:coral_fan_hang3", (short) 0);

        private final String id;
        private final short value;

        CoralTypeMagic(String id, short value) {
            this.id = id;
            this.value = value;
        }
    }

    @Override
    public ItemType getItemType() {
        return ItemType.CORAL_FAN_HANG;
    }

    @Override
    public void setCoralType(CoralType type) {
        this.calculate(this.getDirection(), type, this.isDead());
    }

    @Override
    public CoralType getCoralType() {
        short directionValue = DirectionMagic.valueOf(this.getDirection().name()).value;
        short type = (short) (directionValue - (this.isDead() ? 2 : 0));

        for (CoralTypeMagic value : CoralTypeMagic.values()) {
            if (this.getMaterial().equals(value.id) && type == value.value) {
                return CoralType.valueOf(value.name());
            }
        }

        return CoralType.TUBE;
    }

    @Override
    public void setDirection(Direction direction) {
        this.calculate(direction, this.getCoralType(), this.isDead());
    }

    @Override
    public Direction getDirection() {
        for (DirectionMagic value : DirectionMagic.values()) {
            if (this.getData() >= value.value) {
                return Direction.valueOf(value.name());
            }
        }

        return Direction.WEST;
    }

    @Override
    public boolean isDead() {
        short directionValue = DirectionMagic.valueOf(this.getDirection().name()).value;
        return this.getData() - directionValue >= 2;
    }

    @Override
    public void setDead(boolean dead) {
        this.calculate(this.getDirection(), this.getCoralType(), dead);
    }

    private void calculate(Direction direction, CoralType type, boolean isDead) {
        CoralTypeMagic coralTypeMagic = CoralTypeMagic.valueOf(type.name());

        short val = DirectionMagic.valueOf(direction.name()).value;
        val += coralTypeMagic.value;
        val += isDead ? 2 : 0;

        this.setMaterial(coralTypeMagic.id);
        this.setData(val);
    }

    @Override
    public Block getBlock() {
        BlockCoralFanHang block = (BlockCoralFanHang) super.getBlock();
        block.setCoralType(this.getCoralType());
        block.setDirection(this.getDirection());
        block.setDead(this.isDead());
        return block;
    }

}
