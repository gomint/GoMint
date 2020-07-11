package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.server.world.block.state.BooleanBlockState;
import io.gomint.server.world.block.state.DirectValueBlockState;
import io.gomint.server.world.block.state.EnumBlockState;
import io.gomint.world.block.BlockCoralBlock;
import io.gomint.world.block.BlockType;
import lombok.Getter;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:coral_block")
public class CoralBlock extends Block implements BlockCoralBlock {

    @Getter
    private enum CoralTypeMagic {
        TUBE(false, "blue"),
        BRAIN(false, "pink"),
        BUBBLE(false, "purple"),
        FIRE(false, "red"),
        HORN(false, "yellow"),
        DEAD_TUBE(true, "blue"),
        DEAD_BRAIN(true, "pink"),
        DEAD_BUBBLE(true, "purple"),
        DEAD_FIRE(true, "red"),
        DEAD_HORN(true, "yellow");

        private final boolean dead;
        private final String color;

        CoralTypeMagic(boolean dead, String color) {
            this.dead = dead;
            this.color = color;
        }
    }

    private final BooleanBlockState dead = new BooleanBlockState(this, () -> new String[]{"dead_bit"});
    private final DirectValueBlockState<String> color = new DirectValueBlockState<>(this, () -> new String[]{"coral_color"}, "blue");

    @Override
    public String getBlockId() {
        return "minecraft:coral_block";
    }

    @Override
    public long getBreakTime() {
        return 50;
    }

    @Override
    public Class<? extends ItemStack>[] getToolInterfaces() {
        return ToolPresets.PICKAXE;
    }

    @Override
    public float getBlastResistance() {
        return 30;
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.CORAL_BLOCK;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public CoralType getCoralType() {
        String c = this.color.getState();
        boolean d = this.dead.getState();
        for (CoralTypeMagic value : CoralTypeMagic.values()) {
            if (value.dead == d && c.equals(value.color)) {
                return CoralType.valueOf(value.name());
            }
        }

        return CoralType.TUBE;
    }

    @Override
    public void setCoralType(CoralType coralType) {
        CoralTypeMagic newState = CoralTypeMagic.valueOf(coralType.name());
        this.dead.setState(newState.isDead());
        this.color.setState(newState.getColor());
    }

}
