package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemHoe;
import io.gomint.inventory.item.ItemStack;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockTarget;
import io.gomint.world.block.BlockType;

/**
 * @author KingAli
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:target" )
public class Target extends Block implements BlockTarget {

    @Override
    public String getBlockId() {
        return "minecraft:target";
    }

    @Override
    public long getBreakTime() {
        return 800;
    }

    @Override
    public float getBlastResistance() {
        return 2.5f;
    }

    @Override
    public Class<? extends ItemStack>[] getToolInterfaces() {
        return new Class[]{
            ItemHoe.class
        };
    }

    @Override
    public BlockType blockType() {
        return BlockType.TARGET;
    }
}
