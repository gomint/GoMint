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
public class ItemCoralFanHang extends ItemStack<io.gomint.inventory.item.ItemCoralFanHang> implements io.gomint.inventory.item.ItemCoralFanHang {

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
    public ItemType itemType() {
        return ItemType.CORAL_FAN_HANG;
    }

    @Override
    public ItemCoralFanHang coralType(CoralType type) {
        this.calculate(this.direction(), type, this.dead());
        return this;
    }

    @Override
    public CoralType coralType() {
        short directionValue = DirectionMagic.valueOf(this.direction().name()).value;
        short type = (short) (directionValue - (this.dead() ? 2 : 0));

        for (CoralTypeMagic value : CoralTypeMagic.values()) {
            if (this.material().equals(value.id) && type == value.value) {
                return CoralType.valueOf(value.name());
            }
        }

        return CoralType.TUBE;
    }

    @Override
    public ItemCoralFanHang direction(Direction direction) {
        this.calculate(direction, this.coralType(), this.dead());
        return this;
    }

    @Override
    public Direction direction() {
        for (DirectionMagic value : DirectionMagic.values()) {
            if (this.data() >= value.value) {
                return Direction.valueOf(value.name());
            }
        }

        return Direction.WEST;
    }

    @Override
    public boolean dead() {
        short directionValue = DirectionMagic.valueOf(this.direction().name()).value;
        return this.data() - directionValue >= 2;
    }

    @Override
    public ItemCoralFanHang dead(boolean dead) {
        this.calculate(this.direction(), this.coralType(), dead);
        return this;
    }

    private void calculate(Direction direction, CoralType type, boolean isDead) {
        CoralTypeMagic coralTypeMagic = CoralTypeMagic.valueOf(type.name());

        short val = DirectionMagic.valueOf(direction.name()).value;
        val += coralTypeMagic.value;
        val += isDead ? 2 : 0;

        this.material(coralTypeMagic.id);
        this.data(val);
    }

    @Override
    public Block block() {
        BlockCoralFanHang block = (BlockCoralFanHang) super.block();
        block.coralType(this.coralType());
        block.direction(this.direction());
        block.dead(this.dead());
        return block;
    }

}
