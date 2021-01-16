package io.gomint.entity.monster;

import io.gomint.GoMint;
import io.gomint.entity.EntityAgeable;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public interface EntityZombieVillager extends EntityAgeable<EntityZombieVillager> {

    /**
     * Create a new entity zombie villager with no config
     *
     * @return empty, fresh zombie villager
     */
    static EntityZombieVillager create() {
        return GoMint.instance().createEntity( EntityZombieVillager.class );
    }

}
