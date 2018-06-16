package io.gomint.entity.monster;

import io.gomint.GoMint;
import io.gomint.entity.EntityLiving;

public interface EntityZombieVillager extends EntityLiving {

    /**
     * Create a new entity bat with no config
     *
     * @return empty, fresh zombie villager
     */
    static EntityZombieVillager create() {
        return GoMint.instance().createEntity( EntityZombieVillager.class );
    }
}
