package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.server.world.block.state.BooleanBlockState;
import io.gomint.server.world.block.state.EnumBlockState;
import io.gomint.world.block.BlockCoralBlock;
import io.gomint.world.block.BlockType;
import io.gomint.world.block.data.CoralType;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:coral_block")
public class CoralBlock extends Block implements BlockCoralBlock {

    private enum CoralTypeMagic {
        TUBE("blue"),
        BRAIN("pink"),
        BUBBLE("purple"),
        FIRE("red"),
        HORN("yellow"),
        ;

        private final String color;
        CoralTypeMagic(String color) {
            this.color = color;
        }
    }

    private static final BooleanBlockState DEAD = new BooleanBlockState(() -> new String[]{"dead_bit"});
    private static final EnumBlockState<CoralBlock.CoralTypeMagic, String> COLOR = new EnumBlockState<>(t -> new String[]{"coral_color"},
        CoralBlock.CoralTypeMagic.values(), coralTypeMagic -> coralTypeMagic.color, s -> {
        for (CoralBlock.CoralTypeMagic value : CoralBlock.CoralTypeMagic.values()) {
            if (value.color.equals(s)) {
                return value;
            }
        }

        return null;
    });

    @Override
    public String blockId() {
        return "minecraft:coral_block";
    }

    @Override
    public long breakTime() {
        return 0;
    }

    @Override
    public Class<? extends ItemStack<?>>[] toolInterfaces() {
        return ToolPresets.PICKAXE;
    }

    @Override
    public float blastResistance() {
        return 30;
    }

    @Override
    public BlockType blockType() {
        return BlockType.CORAL_BLOCK;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public BlockCoralBlock dead(boolean dead) {
        DEAD.state(this, dead);
        return this;
    }

    @Override
    public boolean dead() {
        return DEAD.state(this);
    }

    @Override
    public BlockCoralBlock type(CoralType type) {
        CoralBlock.CoralTypeMagic state = CoralBlock.CoralTypeMagic.valueOf(type.name());
        COLOR.state(this, state);
        return this;
    }

    @Override
    public CoralType type() {
        return CoralType.valueOf(COLOR.state(this).name());
    }

}
