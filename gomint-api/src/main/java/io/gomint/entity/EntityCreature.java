package io.gomint.entity;

import io.gomint.inventory.ArmorInventory;

/**
 * @author geNAZt
 * @version 1.0
 */
public interface EntityCreature extends EntityLiving {

    /**
     * Get the entities armor inventory
     *
     * @return the armor inventory
     */
    ArmorInventory getArmorInventory();

}
