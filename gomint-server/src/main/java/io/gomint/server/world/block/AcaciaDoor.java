package io.gomint.server.world.block;

import io.gomint.world.block.BlockType;

import io.gomint.inventory.item.*;
import io.gomint.math.BlockPosition;
import io.gomint.server.inventory.item.Items;
import io.gomint.server.registry.RegisterInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 196 )
public class AcaciaDoor extends Door {

    @Override
    public int getBlockId() {
        return 196;
    }

    @Override
    public Class<? extends ItemStack>[] getToolInterfaces() {
        return new Class[]{
                ItemWoodenAxe.class,
                ItemIronAxe.class,
                ItemDiamondAxe.class,
                ItemGoldenAxe.class,
                ItemStoneAxe.class
        };
    }

    @Override
    public List<ItemStack> getDrops( ItemStack itemInHand ) {
        return new ArrayList<ItemStack>(){{
            add( Items.create( 430, (short) 0, (byte) 1, null ) );
        }};
    }

    @Override
    public void afterPlacement() {
        // Set the top part
        Block above = location.getWorld().getBlockAt( location.toBlockPosition().add( BlockPosition.UP ) );
        AcaciaDoor door = above.setType( AcaciaDoor.class );
        door.setTopPart();
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public float getBlastResistance() {
        return 15.0f;
    }

    @Override
    public BlockType getType() {
        return BlockType.ACACIA_DOOR;
    }

}