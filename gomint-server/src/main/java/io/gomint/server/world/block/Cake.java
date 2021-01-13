package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.math.Location;
import io.gomint.math.Vector;
import io.gomint.server.entity.Entity;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.block.state.ProgressBlockState;
import io.gomint.world.block.BlockCake;
import io.gomint.world.block.BlockType;
import io.gomint.world.block.data.Facing;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:cake")
public class Cake extends Block implements BlockCake {

    private static final ProgressBlockState CAKE_EATEN = new ProgressBlockState(() -> new String[]{"bite_counter"}, 6, b -> b.blockType(Air.class));

    @Override
    public String getBlockId() {
        return "minecraft:cake";
    }

    @Override
    public long getBreakTime() {
        return 750;
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
    public boolean beforePlacement(EntityLiving entity, ItemStack item, Facing face, Location location) {
        CAKE_EATEN.detectFromPlacement(this, entity, item, face);
        return super.beforePlacement(entity, item, face, location);
    }

    @Override
    public float getBlastResistance() {
        return 2.5f;
    }

    @Override
    public BlockType blockType() {
        return BlockType.CAKE;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

}
