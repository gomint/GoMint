package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.math.Vector;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.Block;
import io.gomint.world.block.data.Facing;

@RegisterInfo(sId = "minecraft:turtle_helmet")
public class ItemTurtleShell extends ItemArmor<io.gomint.inventory.item.ItemTurtleShell> implements io.gomint.inventory.item.ItemTurtleShell {

    @Override
    public float getReductionValue() {
        return 2;
    }

    @Override
    public boolean interact(EntityPlayer entity, Facing face, Vector clickPosition, Block clickedBlock) {
        if (clickedBlock == null) {
            if (isBetter((ItemStack<?>) entity.armorInventory().helmet())) {
                ItemStack<?> old = (ItemStack<?>) entity.armorInventory().helmet();
                entity.armorInventory().helmet(this);
                entity.inventory().item(entity.inventory().itemInHandSlot(), old);
            }
        }

        return false;
    }

    @Override
    public ItemType itemType() {
        return ItemType.TURTLE_SHELL;
    }

}
