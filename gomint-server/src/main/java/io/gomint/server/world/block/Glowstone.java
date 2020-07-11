package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemGlowstoneDust;
import io.gomint.inventory.item.ItemStack;
import io.gomint.util.random.FastRandom;
import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:glowstone" )
public class Glowstone extends Block implements io.gomint.world.block.BlockGlowstone {

    @Override
    public String getBlockId() {
        return "minecraft:glowstone";
    }

    @Override
    public long getBreakTime() {
        return 450;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public float getBlastResistance() {
        return 1.5f;
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.GLOWSTONE;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public List<ItemStack> getDrops( ItemStack itemInHand ) {
        return new ArrayList<ItemStack>() {{
            add( ItemGlowstoneDust.create( FastRandom.current().nextBoolean() ? 2 : 4 ) );
        }};
    }
}
