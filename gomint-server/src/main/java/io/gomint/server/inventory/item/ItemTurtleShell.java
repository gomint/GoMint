package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.math.Vector;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.Block;
import io.gomint.world.block.data.Facing;

@RegisterInfo(sId = "minecraft:turtle_helmet", id = 469)
public class ItemTurtleShell extends ItemArmor<io.gomint.inventory.item.ItemTurtleShell> implements io.gomint.inventory.item.ItemTurtleShell {

    @Override
    public float getReductionValue() {
        return 2;
    }

    @Override
    public boolean interact(EntityPlayer entity, Facing face, Vector clickPosition, Block clickedBlock) {
        if (clickedBlock == null) {
            if (isBetter((ItemStack<?>) entity.getArmorInventory().helmet())) {
                ItemStack<?> old = (ItemStack<?>) entity.getArmorInventory().helmet();
                entity.getArmorInventory().helmet(this);
                entity.getInventory().item(entity.getInventory().itemInHandSlot(), old);
            }
        }

        return false;
    }

    @Override
    public ItemType itemType() {
        return ItemType.TURTLE_SHELL;
    }

}
