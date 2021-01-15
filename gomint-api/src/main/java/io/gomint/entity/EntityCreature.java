package io.gomint.entity;

import io.gomint.inventory.ArmorInventory;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public interface EntityCreature<E> extends EntityLiving<E> {

    /**
     * Get the entities armor inventory
     *
     * @return the armor inventory
     */
    ArmorInventory armorInventory();

}
