package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:wheat")
public class Crops extends Growable {

    @Override
    public String getBlockId() {
        return "minecraft:wheat";
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
    public List<ItemStack> getDrops(ItemStack itemInHand) {
        if (this.growth.maxed()) {
            List<ItemStack> drops = new ArrayList<>() {{
                add(world.getServer().getItems().create(296, (short) 0, (byte) 1, null)); // Beetroot
            }};

            // Randomize seeds
            int amountOfSeeds = SEED_RANDOMIZER.next();
            if (amountOfSeeds > 0) {
                drops.add(world.getServer().getItems().create(295, (short) 0, (byte) amountOfSeeds, null)); // Seeds
            }

            return drops;
        } else {
            return new ArrayList<>() {{
                add(world.getServer().getItems().create(295, (short) 0, (byte) 1, null)); // Seeds
            }};
        }
    }

    @Override
    public float getBlastResistance() {
        return 0.0f;
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.CROPS;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

}
