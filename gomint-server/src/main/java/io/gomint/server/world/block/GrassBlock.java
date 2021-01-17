package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemDirt;
import io.gomint.inventory.item.ItemStack;
import io.gomint.world.block.BlockGrassBlock;
import io.gomint.world.block.BlockType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.server.world.UpdateReason;
import io.gomint.world.block.data.Facing;

import java.util.ArrayList;
import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:grass" )
public class GrassBlock extends Block implements BlockGrassBlock {

    @Override
    public String blockId() {
        return "minecraft:grass";
    }

    @Override
    public long update( UpdateReason updateReason, long currentTimeMS, float dT ) {
        Block block = this.side( Facing.UP );
        byte lightLevel = block.skyLightLevel();

        if ( lightLevel >= 9 ) {

        }

        return -1;
    }

    @Override
    public long breakTime() {
        return 900;
    }

    @Override
    public float blastResistance() {
        return 3.0f;
    }

    @Override
    public BlockType blockType() {
        return BlockType.GRASS_BLOCK;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public List<ItemStack<?>> drops(ItemStack<?> itemInHand ) {
        return new ArrayList<>(){{
            add( ItemDirt.create( 1 ) );
        }};
    }

    @Override
    public Class<? extends ItemStack<?>>[] toolInterfaces() {
        return ToolPresets.SHOVEL;
    }

}
