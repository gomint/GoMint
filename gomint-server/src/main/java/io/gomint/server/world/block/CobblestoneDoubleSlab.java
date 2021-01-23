/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemCobblestoneDoubleSlab;
import io.gomint.inventory.item.ItemCobblestoneSlab;
import io.gomint.inventory.item.ItemStack;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.server.world.block.state.EnumBlockState;
import io.gomint.world.block.BlockCobblestoneDoubleSlab;
import io.gomint.world.block.BlockCobblestoneSlab;
import io.gomint.world.block.BlockType;
import io.gomint.world.block.data.CobblestoneType;

import java.util.Collections;
import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:double_stone_slab[stone_slab_type=cobblestone]", def = true )
@RegisterInfo( sId = "minecraft:double_stone_slab2[stone_slab_type_2=mossy_cobblestone]" )
public class CobblestoneDoubleSlab extends Block implements BlockCobblestoneDoubleSlab {

    public enum StoneTypeMagic {
        COBBLESTONE("minecraft:stone_slab", "cobblestone"),
        MOSSY_COBBLESTONE("minecraft:stone_slab2", "mossy_cobblestone");

        private final String value;
        private final String blockId;

        StoneTypeMagic(String blockId, String value) {
            this.value = value;
            this.blockId = blockId;
        }
    }

    private static final EnumBlockState<StoneTypeMagic, String> VARIANT = new EnumBlockState<>(v -> {
        return new String[]{"stone_slab_type", "stone_slab_type_2"};
    }, StoneTypeMagic.values(), v -> v.value, v -> {
        for (StoneTypeMagic value : StoneTypeMagic.values()) {
            if (value.value.equals(v)) {
                return value;
            }
        }

        return null;
    });

    @Override
    public long breakTime() {
        return 3000;
    }

    @Override
    public float blastResistance() {
        return 30.0f;
    }

    @Override
    public BlockType blockType() {
        return BlockType.COBBLESTONE_DOUBLE_SLAB;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public Class<? extends ItemStack<?>>[] toolInterfaces() {
        return ToolPresets.PICKAXE;
    }

    @Override
    public BlockCobblestoneDoubleSlab type(CobblestoneType type) {
        StoneTypeMagic newState = (type == CobblestoneType.NORMAL) ? StoneTypeMagic.COBBLESTONE : StoneTypeMagic.MOSSY_COBBLESTONE;

        this.blockId(newState.blockId);
        VARIANT.state(this, newState);

        return this;
    }

    @Override
    public CobblestoneType type() {
        return VARIANT.state(this) == StoneTypeMagic.COBBLESTONE ? CobblestoneType.NORMAL : CobblestoneType.MOSSY;
    }

    @Override
    public List<ItemStack<?>> drops(ItemStack<?> itemInHand) {
        return Collections.singletonList(ItemCobblestoneDoubleSlab.create(1).type(this.type()));
    }

}
