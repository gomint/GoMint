package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemDiamondPickaxe;
import io.gomint.inventory.item.ItemStack;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockAncientDebris;
import io.gomint.world.block.BlockType;

/**
 * @author KingAli
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:ancient_debris" )
public class AncientDebris extends Block implements BlockAncientDebris {

    @Override
    public String getBlockId() {
        return "minecraft:ancient_debris";
    }

    @Override
    public long breakTime() {
        return 45200;
    }

    @Override
    public float getBlastResistance() {
        return 6000.0f;
    }

    @Override
    public BlockType blockType() {
        return BlockType.ANCIENT_DEBRIS;
    }

    @Override
    public Class<? extends ItemStack<?>>[] getToolInterfaces() {
        return new Class[]{
            ItemDiamondPickaxe.class
        };
    }
}
