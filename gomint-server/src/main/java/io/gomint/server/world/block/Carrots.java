package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemCarrot;
import io.gomint.inventory.item.ItemStack;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockCarrots;
import io.gomint.world.block.BlockType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:carrots")
public class Carrots extends Growable implements BlockCarrots {

    @Override
    public String blockId() {
        return "minecraft:carrots";
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
            return new ArrayList<>() {{
                add(ItemCarrot.create(1 + SEED_RANDOMIZER.next().byteValue())); // Carrot
            }};
        } else {
            return new ArrayList<>() {{
                add(ItemCarrot.create(1)); // Carrot
            }};
        }
    }

    @Override
    public float blastResistance() {
        return 0.0f;
    }

    @Override
    public BlockType blockType() {
        return BlockType.CARROTS;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

}
