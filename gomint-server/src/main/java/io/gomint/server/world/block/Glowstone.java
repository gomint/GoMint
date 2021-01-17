package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemGlowstoneDust;
import io.gomint.inventory.item.ItemStack;
import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockGlowstone;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:glowstone" )
public class Glowstone extends Block implements BlockGlowstone {

    @Override
    public String blockId() {
        return "minecraft:glowstone";
    }

    @Override
    public long breakTime() {
        return 450;
    }

    @Override
    public boolean transparent() {
        return true;
    }

    @Override
    public float blastResistance() {
        return 1.5f;
    }

    @Override
    public BlockType blockType() {
        return BlockType.GLOWSTONE;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public List<ItemStack<?>> drops(ItemStack<?> itemInHand ) {
        return new ArrayList<>() {{
            add( ItemGlowstoneDust.create( ThreadLocalRandom.current().nextBoolean() ? 2 : 4 ) );
        }};
    }
}
