package io.gomint.server.world.block;

import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.server.world.block.state.EnumBlockState;
import io.gomint.world.block.BlockSandstone;
import io.gomint.world.block.BlockType;

import io.gomint.inventory.item.*;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.data.Sandcolor;
import io.gomint.world.block.data.SandstoneType;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:sandstone", def = true )
@RegisterInfo( sId = "minecraft:red_sandstone" )
public class Sandstone extends Block implements BlockSandstone {

    private enum SandstoneMagicType {
        NORMAL("default"),
        CHISELED("heiroglyphs"),
        CUT("cut"),
        SMOOTH("smooth");

        private final String type;
        SandstoneMagicType(String type) {
            this.type = type;
        }
    }

    private static final EnumBlockState<SandstoneMagicType, String> VARIANT = new EnumBlockState<>(k -> new String[]{"sand_stone_type"},
        SandstoneMagicType.values(), v -> v.type, s -> {
        for (SandstoneMagicType value : SandstoneMagicType.values()) {
            if (value.type.equals(s)) {
                return value;
            }
        }

        return null;
    });

    @Override
    public long breakTime() {
        return 1200;
    }

    @Override
    public Class<? extends ItemStack<?>>[] toolInterfaces() {
        return ToolPresets.PICKAXE;
    }

    @Override
    public float blastResistance() {
        return 4.0f;
    }

    @Override
    public BlockType blockType() {
        return BlockType.SANDSTONE;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return false;
    }

    @Override
    public BlockSandstone color(Sandcolor color) {
        this.blockId(color == Sandcolor.NORMAL ? "minecraft:sandstone" : "minecraft:red_sandstone" );
        return this;
    }

    @Override
    public Sandcolor color() {
        return this.blockId().equals("minecraft:sandstone") ? Sandcolor.NORMAL : Sandcolor.RED;
    }

    @Override
    public BlockSandstone type(SandstoneType type) {
        VARIANT.state(this, SandstoneMagicType.valueOf(type.name()));
        return this;
    }

    @Override
    public SandstoneType type() {
        return SandstoneType.valueOf(VARIANT.state(this).name());
    }

}
