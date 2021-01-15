package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.server.world.WorldAdapter;
import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.world.block.BlockMobSpawner;
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
public class MobSpawner extends Block implements BlockMobSpawner {

    @Override
    public String getBlockId() {
        return "minecraft:mob_spawner";
    }

    @Override
    public long breakTime() {
        return 7500;
    }

    @Override
    public boolean transparent() {
        return true;
    }

    @Override
    public float getBlastResistance() {
        return 25.0f;
    }

    @Override
    public BlockType blockType() {
        return BlockType.MOB_SPAWNER;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public List<ItemStack<?>> drops(ItemStack<?> itemInHand ) {
        ((WorldAdapter) this.location.world()).createExpOrb( this.location, ThreadLocalRandom.current().nextInt( 15 ) + ThreadLocalRandom.current().nextInt( 15 ) + 15 );

        return new ArrayList<>();
    }

    @Override
    public Class<? extends ItemStack<?>>[] getToolInterfaces() {
        return ToolPresets.PICKAXE;
    }

}
