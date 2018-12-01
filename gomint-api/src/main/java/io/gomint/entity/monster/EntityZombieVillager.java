package io.gomint.entity.monster;

import io.gomint.GoMint;
import io.gomint.entity.EntityAgeable;
import io.gomint.entity.EntityLiving;

public interface EntityZombieVillager extends EntityAgeable {

    /**
     * Create a new entity zombie villager with no config
     *
     * @return empty, fresh zombie villager
     */
    static EntityZombieVillager create() {
        return GoMint.instance().createEntity( EntityZombieVillager.class );
    }

}
