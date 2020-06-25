package io.gomint.server.world.block;


import io.gomint.inventory.item.ItemHoe;
import io.gomint.inventory.item.ItemStack;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockType;

/**
 * @author KingAli
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:shroomligh")
public class ShroomLight extends Block implements io.gomint.world.block.BlockShroomLight {

    @Override
    public String getBlockId() {
        return "minecraft:shroomlight";
    }

    @Override
    public long getBreakTime() {
        return 1500;
    }

    @Override
    public Class<? extends ItemStack>[] getToolInterfaces() {
        return new Class[]{
            ItemHoe.class
        };
    }

    @Override
    public float getBlastResistance() {
        return 5.0f;
    }

    @Override
    public BlockType getType() {
        return BlockType.SHROOMLIGHT;
    }
}
