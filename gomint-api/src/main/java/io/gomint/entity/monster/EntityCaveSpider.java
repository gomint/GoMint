package io.gomint.entity.monster;

import io.gomint.GoMint;
import io.gomint.entity.EntityLiving;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public interface EntityCaveSpider extends EntityLiving {

    /**
     * Create a new entity cave spider with no config
     *
     * @return empty, fresh cave spider
     */
    static EntityCaveSpider create() {
        return GoMint.instance().createEntity( EntityCaveSpider.class );
    }

}
