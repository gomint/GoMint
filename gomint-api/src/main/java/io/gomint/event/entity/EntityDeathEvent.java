package io.gomint.event.entity;

import io.gomint.entity.Entity;
import io.gomint.inventory.item.ItemStack;

import java.util.List;

public class EntityDeathEvent extends CancellableEntityEvent {

    private List<ItemStack> drops;

    public EntityDeathEvent( Entity entity, List<ItemStack> drops ) {
        super( entity );
        this.drops = drops;
    }

    /**
     * A list of items which will be dropped
     *
     * @return list of items
     */

    public List<ItemStack> getDrops() {
        return drops;
    }
}
