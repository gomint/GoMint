package io.gomint.server.world.block;


import io.gomint.inventory.item.ItemHoe;
import io.gomint.inventory.item.ItemStack;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockShroomLight;
import io.gomint.world.block.BlockType;

/**
 * @author KingAli
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:shroomlight")
public class ShroomLight extends Block implements BlockShroomLight {

    @Override
    public long breakTime() {
        return 1500;
    }

    @Override
    public Class<? extends ItemStack<?>>[] toolInterfaces() {
        return new Class[]{
            ItemHoe.class
        };
    }

    @Override
    public float blastResistance() {
        return 5.0f;
    }

    @Override
    public BlockType blockType() {
        return BlockType.SHROOMLIGHT;
    }
}
