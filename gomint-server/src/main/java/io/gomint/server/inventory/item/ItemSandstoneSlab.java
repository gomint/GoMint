/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.Block;
import io.gomint.world.block.BlockSandstoneDoubleSlab;
import io.gomint.world.block.BlockSandstoneSlab;
import io.gomint.world.block.data.RoughnessType;
import io.gomint.world.block.data.Sandcolor;
import io.gomint.world.block.data.SandstoneSlabType;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:double_stone_slab[1]", def = true) // sandstone
@RegisterInfo(sId = "minecraft:double_stone_slab3[1]") // smooth red sandstone
@RegisterInfo(sId = "minecraft:double_stone_slab2[6]") // smooth sandstone
@RegisterInfo(sId = "minecraft:double_stone_slab2[0]") // red sandstone
@RegisterInfo(sId = "minecraft:double_stone_slab4[3]") // cut sandstone
@RegisterInfo(sId = "minecraft:double_stone_slab4[4]") // cut red sandstone
public class ItemSandstoneSlab extends ItemSlab<io.gomint.inventory.item.ItemSandstoneSlab> implements io.gomint.inventory.item.ItemSandstoneSlab {

    @Override
    public ItemType itemType() {
        return ItemType.SANDSTONE_SLAB;
    }

    @Override
    public io.gomint.inventory.item.ItemSandstoneSlab color(Sandcolor color) {
        return this.change(color, this.type());
    }

    private io.gomint.inventory.item.ItemSandstoneSlab change(Sandcolor color, SandstoneSlabType type) {
        switch (color) {
            case NORMAL:
                switch (type) {
                    case NORMAL:
                        this.material("minecraft:double_stone_slab");
                        return this.data((short) 1);
                    case SMOOTH:
                        this.material("minecraft:double_stone_slab2");
                        return this.data((short) 6);
                    case CUT:
                        this.material("minecraft:double_stone_slab4");
                        return this.data((short) 3);
                }
            case RED:
                switch (type) {
                    case NORMAL:
                        this.material("minecraft:double_stone_slab2");
                        return this.data((short) 0);
                    case SMOOTH:
                        this.material("minecraft:double_stone_slab3");
                        return this.data((short) 1);
                    case CUT:
                        this.material("minecraft:double_stone_slab4");
                        return this.data((short) 4);
                }
        }

        return this;
    }

    @Override
    public Sandcolor color() {
        if ((this.material().equals("minecraft:double_stone_slab3") && this.data() == 1) ||
            (this.material().equals("minecraft:double_stone_slab2") && this.data() == 0) ||
            (this.material().equals("minecraft:double_stone_slab4") && this.data() == 4)) {
            return Sandcolor.RED;
        }

        return Sandcolor.NORMAL;
    }

    @Override
    public io.gomint.inventory.item.ItemSandstoneSlab type(SandstoneSlabType type) {
        return this.change(this.color(), type);
    }

    @Override
    public SandstoneSlabType type() {
        if ((this.material().equals("minecraft:double_stone_slab2") && this.data() == 6) ||
            (this.material().equals("minecraft:double_stone_slab3") && this.data() == 1)) {
            return SandstoneSlabType.SMOOTH;
        }

        if ((this.material().equals("minecraft:double_stone_slab4") && this.data() == 3) ||
            (this.material().equals("minecraft:double_stone_slab4") && this.data() == 4)) {
            return SandstoneSlabType.CUT;
        }

        return SandstoneSlabType.NORMAL;
    }

    @Override
    public Block block() {
        BlockSandstoneSlab block = this.blocks.get(BlockSandstoneSlab.class);
        block.type(this.type());
        block.color(this.color());
        return block;
    }

}
