package io.gomint.entity.monster;

import io.gomint.GoMint;
import io.gomint.entity.EntityLiving;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public interface EntitySpider extends EntityLiving<EntitySpider> {

    /**
     * Create a new entity spider with no config
     *
     * @return empty, fresh spider
     */
    static EntitySpider create() {
        return GoMint.instance().createEntity( EntitySpider.class );
    }

}
