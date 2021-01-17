package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemBeetroot;
import io.gomint.inventory.item.ItemSeeds;
import io.gomint.inventory.item.ItemStack;
import io.gomint.inventory.item.ItemWheat;
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
    public String blockId() {
        return "minecraft:wheat";
    }

    @Override
    public boolean transparent() {
        return true;
    }

    @Override
    public boolean solid() {
        return false;
    }

    @Override
    public boolean canPassThrough() {
        return true;
    }

    @Override
    public long breakTime() {
        return 0;
    }

    @Override
    public List<ItemStack<?>> drops(ItemStack<?> itemInHand) {
        if (GROWTH.maxed(this)) {
            List<ItemStack<?>> drops = new ArrayList<>() {{
                add(ItemWheat.create(1)); // Beetroot
            }};

            // Randomize seeds
            int amountOfSeeds = SEED_RANDOMIZER.next();
            if (amountOfSeeds > 0) {
                drops.add(ItemSeeds.create(amountOfSeeds)); // Seeds
            }

            return drops;
        } else {
            return new ArrayList<>() {{
                add(ItemSeeds.create(1)); // Seeds
            }};
        }
    }

    @Override
    public float blastResistance() {
        return 0.0f;
    }

    @Override
    public BlockType blockType() {
        return BlockType.CROPS;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

}
