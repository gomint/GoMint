package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.server.world.WorldAdapter;
import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.util.random.FastRandom;
import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:mob_spawner" )
public class MobSpawner extends Block implements io.gomint.world.block.BlockMobSpawner {

    @Override
    public String getBlockId() {
        return "minecraft:mob_spawner";
    }

    @Override
    public long getBreakTime() {
        return 7500;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public float getBlastResistance() {
        return 25.0f;
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.MOB_SPAWNER;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public List<ItemStack> getDrops( ItemStack itemInHand ) {
        ((WorldAdapter) this.location.getWorld()).createExpOrb( this.location, ThreadLocalRandom.current().nextInt( 15 ) + ThreadLocalRandom.current().nextInt( 15 ) + 15 );

        return new ArrayList<>();
    }

    @Override
    public Class<? extends ItemStack>[] getToolInterfaces() {
        return ToolPresets.PICKAXE;
    }

}
