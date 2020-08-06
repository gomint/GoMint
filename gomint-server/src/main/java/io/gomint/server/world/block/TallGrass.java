package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemTallGrass;
import io.gomint.inventory.item.ItemShears;
import io.gomint.inventory.item.ItemStack;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.block.state.EnumBlockState;
import io.gomint.world.block.BlockTallGrass;
import io.gomint.world.block.BlockType;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:tallgrass")
public class TallGrass extends Block implements BlockTallGrass {

    @Getter
    private enum TypeMagic {
        GRASS("tall"),
        FERN("fern"),
        SNOW("snow");

        private final String type;

        TypeMagic(String type) {
            this.type = type;
        }
    }

    private static final EnumBlockState<TypeMagic, String> VARIANT = new EnumBlockState<>(v -> new String[]{"tall_grass_type"}, TypeMagic.values(), TypeMagic::getType, v -> {
        for (TypeMagic value : TypeMagic.values()) {
            if (value.getType().equals(v)) {
                return value;
            }
        }

        return null;
    });

    @Override
    public String getBlockId() {
        return "minecraft:tallgrass";
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public boolean canPassThrough() {
        return true;
    }

    @Override
    public long getBreakTime() {
        return 0;
    }

    @Override
    public float getBlastResistance() {
        return 0.0f;
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.TALL_GRASS;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public List<ItemStack> getDrops(ItemStack itemInHand) {
        if (isCorrectTool(itemInHand)) {
            return new ArrayList<ItemStack>() {{
                add(ItemTallGrass.create(1));
            }};
        }

        return new ArrayList<>();
    }

    @Override
    public Class<? extends ItemStack>[] getToolInterfaces() {
        return new Class[]{
            ItemShears.class
        };
    }

    @Override
    public void setGrassType(Type type) {
        VARIANT.setState(this, TypeMagic.valueOf(type.name()));
    }

    @Override
    public Type getGrassType() {
        return Type.valueOf(VARIANT.getState(this).name());
    }

}
