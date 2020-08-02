package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.math.Vector;
import io.gomint.server.entity.Entity;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.PlacementData;
import io.gomint.server.world.block.state.ProgressBlockState;
import io.gomint.world.block.data.Facing;
import io.gomint.world.block.BlockType;
import lombok.EqualsAndHashCode;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:cake")
@EqualsAndHashCode(callSuper = true)
public class Cake extends Block implements io.gomint.world.block.BlockCake {

    private static final ProgressBlockState CAKE_EATEN = new ProgressBlockState(() -> new String[]{"bite_counter"}, 6, b -> b.setBlockType(Air.class));

    @Override
    public String getBlockId() {
        return "minecraft:cake";
    }

    @Override
    public long getBreakTime() {
        return 750;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public boolean interact(Entity entity, Facing face, Vector facePos, ItemStack item) {
        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;
            if (player.getHunger() < 20) {
                player.addHunger(2);

                float saturation = Math.min(player.getSaturation() + (2 * 0.1f * 2.0f), player.getHunger());
                player.setSaturation(saturation);

                CAKE_EATEN.progress(this);
            }
        }

        return false;
    }

    @Override
    public PlacementData calculatePlacementData(EntityPlayer entity, ItemStack item, Facing face, Block block, Block clickedBlock, Vector clickVector) {
        CAKE_EATEN.detectFromPlacement(this, entity, item, face, block, clickedBlock, clickVector);
        return new PlacementData(this.identifier, null);
    }

    @Override
    public float getBlastResistance() {
        return 2.5f;
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.CAKE;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

}
