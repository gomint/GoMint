package io.gomint.server.world.block;

import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.world.block.BlockAnvil;
import io.gomint.world.block.BlockType;

import io.gomint.inventory.item.*;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:anvil" )
public class Anvil extends Block implements BlockAnvil {

    @Override
    public String blockId() {
        return "minecraft:anvil";
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
    public Class<? extends ItemStack<?>>[] toolInterfaces() {
        return ToolPresets.PICKAXE;
    }

    @Override
    public float blastResistance() {
        return 6000.0f;
    }

    @Override
    public BlockType blockType() {
        return BlockType.ANVIL;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

}
