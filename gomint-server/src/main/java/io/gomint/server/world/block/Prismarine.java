package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.server.world.block.state.EnumBlockState;
import io.gomint.world.block.BlockPrismarine;
import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.data.PrismarineType;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:prismarine" )
public class Prismarine extends Block implements BlockPrismarine {

    public enum PrismarineTypeMagic {

        NORMAL("default"),
        DARK("dark"),
        BRICK("bricks");

        private final String type;
        PrismarineTypeMagic(String type) {
            this.type = type;
        }

    }

    private static final EnumBlockState<PrismarineTypeMagic, String> VARIANT = new EnumBlockState<>(v -> new String[]{"prismarine_block_type"},
        PrismarineTypeMagic.values(), v -> v.type, s -> {
        for (PrismarineTypeMagic value : PrismarineTypeMagic.values()) {
            if (value.type.equals(s)) {
                return value;
            }
        }

        return null;
    });

    @Override
    public long breakTime() {
        return 2250;
    }

    @Override
    public float blastResistance() {
        return 30.0f;
    }

    @Override
    public BlockType blockType() {
        return BlockType.PRISMARINE;
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
    public BlockPrismarine type(PrismarineType type) {
        VARIANT.state(this, PrismarineTypeMagic.valueOf(type.name()));
        return this;
    }

    @Override
    public PrismarineType type() {
        return PrismarineType.valueOf(VARIANT.state(this).name());
    }

}
