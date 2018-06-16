package io.gomint.entity.monster;

import io.gomint.GoMint;
import io.gomint.entity.EntityLiving;

public interface EntitySpider extends EntityLiving {

    /**
     * Create a new entity bat with no config
     *
     * @return empty, fresh spider
     */
    static EntitySpider create() {
        return GoMint.instance().createEntity( EntitySpider.class );
    }
}
