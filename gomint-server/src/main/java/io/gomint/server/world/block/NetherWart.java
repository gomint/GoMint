package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.math.Location;
import io.gomint.math.Vector;
import io.gomint.server.entity.EntityLiving;
import io.gomint.world.block.BlockNetherWart;
import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.data.Facing;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:nether_wart" )
public class NetherWart extends Block implements BlockNetherWart {

    @Override
    public boolean beforePlacement(EntityLiving<?> entity, ItemStack<?> item, Facing face, Location location, Vector clickVector) {
        Block down = this.side(Facing.DOWN);
        return down.solid();
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
    public float blastResistance() {
        return 0.0f;
    }

    @Override
    public BlockType blockType() {
        return BlockType.NETHER_WART;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

}
