package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockBeetroot;
import io.gomint.world.block.BlockType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:beetroot")
public class Beetroot extends Growable implements BlockBeetroot {

    @Override
    public String getBlockId() {
        return "minecraft:beetroot";
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
        if (GROWTH.maxed(this)) {
            List<ItemStack> drops = new ArrayList<>() {{
                add(world.getServer().items().create(457, (short) 0, (byte) 1, null)); // Beetroot
            }};

            // Randomize seeds
            int amountOfSeeds = SEED_RANDOMIZER.next();
            if (amountOfSeeds > 0) {
                drops.add(world.getServer().items().create(458, (short) 0, (byte) amountOfSeeds, null)); // Seeds
            }

            return drops;
        } else {
            return new ArrayList<>() {{
                add(world.getServer().items().create(458, (short) 0, (byte) 1, null)); // Seeds
            }};
        }
    }

    @Override
    public float getBlastResistance() {
        return 0.0f;
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.BEETROOT;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

}
