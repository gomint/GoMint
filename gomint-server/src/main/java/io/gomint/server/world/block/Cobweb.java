package io.gomint.server.world.block;

import io.gomint.server.entity.Entity;
import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.world.block.BlockType;

import io.gomint.inventory.item.*;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockCobweb;

import java.util.ArrayList;
import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:web" )
public class Cobweb extends Block implements BlockCobweb {

    @Override
    public String blockId() {
        return "minecraft:web";
    }

    @Override
    public long breakTime() {
        return 6000;
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
    public Class<? extends ItemStack<?>>[] toolInterfaces() {
        return ToolPresets.SWORD;
    }

    @Override
    public List<ItemStack<?>> drops(ItemStack<?> itemInHand ) {
        if ( isCorrectTool( itemInHand ) ) {
            return new ArrayList<>() {{
                add( ItemString.create( 1 ) );
            }};
        }

        return new ArrayList<>();
    }

    @Override
    public float blastResistance() {
        return 20.0f;
    }

    @Override
    public BlockType blockType() {
        return BlockType.COBWEB;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public void stepOn(Entity<?> entity ) {
        // Reset fall distance
        entity.resetFallDistance();
    }

}
