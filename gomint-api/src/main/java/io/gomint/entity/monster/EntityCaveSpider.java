package io.gomint.entity.monster;

import io.gomint.GoMint;
import io.gomint.entity.EntityLiving;

public interface EntityCaveSpider extends EntityLiving {

    /**
     * Create a new entity bat with no config
     *
     * @return empty, fresh cave spider
     */
    static EntityCaveSpider create() {
        return GoMint.instance().createEntity( EntityCaveSpider.class );
    }
}
