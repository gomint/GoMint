package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.math.Location;
import io.gomint.math.Vector;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockFlower;
import io.gomint.world.block.BlockType;
import io.gomint.world.block.data.Facing;

import java.util.HashSet;
import java.util.Set;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:red_flower")
@RegisterInfo(sId = "minecraft:wither_rose")
public class Flower extends Block implements BlockFlower {

    private static final Set<BlockType> ALLOWED_PLACED_ON = new HashSet<>() {{
        add(BlockType.DISPENSER);
        add(BlockType.GRASS_BLOCK);
    }};

    @Override
    public boolean beforePlacement(EntityLiving<?> entity, ItemStack<?> item, Facing face, Location location) {
        // Check if downwards block is valid
        Block block = entity.world().blockAt(location.toBlockPosition().add(Vector.DOWN.toBlockPosition()));
        return ALLOWED_PLACED_ON.contains(block.blockType());
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
    public boolean canPassThrough() {
        return true;
    }

    @Override
    public long breakTime() {
        return 0;
    }

    @Override
    public float getBlastResistance() {
        return 0.0f;
    }

    @Override
    public BlockType blockType() {
        return BlockType.FLOWER;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

}
