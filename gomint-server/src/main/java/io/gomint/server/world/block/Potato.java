package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemPoisonousPotato;
import io.gomint.inventory.item.ItemPotato;
import io.gomint.inventory.item.ItemStack;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockPotato;
import io.gomint.world.block.BlockType;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:potatoes")
public class Potato extends Growable implements BlockPotato {

    @Override
    public String blockId() {
        return "minecraft:potatoes";
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
                add(ItemPotato.create(1 + SEED_RANDOMIZER.next().byteValue())); // Potato
            }};

            if (ThreadLocalRandom.current().nextDouble() > 0.98) {
                drops.add(ItemPoisonousPotato.create(1)); // Poison potato on top!
            }

            return drops;
        } else {
            return new ArrayList<>() {{
                add(ItemPotato.create(1)); // Potato
            }};
        }
    }

    @Override
    public float blastResistance() {
        return 0.0f;
    }

    @Override
    public BlockType blockType() {
        return BlockType.POTATO;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

}
