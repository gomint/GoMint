package io.gomint.server.world.block;

import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.server.world.block.state.EnumBlockState;
import io.gomint.world.block.BlockType;

import io.gomint.inventory.item.*;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockDirt;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:dirt" )
public class Dirt extends Block implements BlockDirt {

    enum TypeMagic {
        NORMAL("normal"),
        COARSE("coarse");

        private final String magic;
        TypeMagic(String magic) {
            this.magic = magic;
        }
    }

    private static final EnumBlockState<TypeMagic, String> VARIANT = new EnumBlockState<>(v -> new String[]{"dirt_type"}, TypeMagic.values(), e -> e.magic, v -> {
        if ("normal".equals(v)) {
            return TypeMagic.NORMAL;
        }

        return TypeMagic.COARSE;
    });

    @Override
    public String getBlockId() {
        return "minecraft:dirt";
    }

    @Override
    public long getBreakTime() {
        return 750;
    }

    @Override
    public Class<? extends ItemStack>[] getToolInterfaces() {
        return ToolPresets.SHOVEL;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public float getBlastResistance() {
        return 2.5f;
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.DIRT;
    }

    @Override
    public void setDirtType(Type type) {
        VARIANT.setState(this, TypeMagic.valueOf(type.name()));
    }

    @Override
    public Type getDirtType() {
        return Type.valueOf(VARIANT.getState(this).name());
    }

}
