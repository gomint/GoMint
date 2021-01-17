package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemBeetroot;
import io.gomint.inventory.item.ItemBeetrootSeeds;
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
                add(ItemBeetroot.create(1)); // Beetroot
            }};

            // Randomize seeds
            int amountOfSeeds = SEED_RANDOMIZER.next();
            if (amountOfSeeds > 0) {
                drops.add(ItemBeetrootSeeds.create(amountOfSeeds)); // Seeds
            }

            return drops;
        } else {
            return new ArrayList<>() {{
                add(ItemBeetrootSeeds.create(1)); // Seeds
            }};
        }
    }

    @Override
    public float blastResistance() {
        return 0.0f;
    }

    @Override
    public BlockType blockType() {
        return BlockType.BEETROOT;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

}
