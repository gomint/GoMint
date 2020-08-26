package io.gomint.entity.monster;

import io.gomint.GoMint;
import io.gomint.entity.EntityLiving;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public interface EntitySalmon extends EntityLiving {

    /**
     * Create a new entity salmon with no config
     *
     * @return empty, fresh salmon
     */
    static EntitySalmon create() {
        return GoMint.instance().createEntity( EntitySalmon.class );
    }

}
