package io.gomint.entity.monster;

import io.gomint.GoMint;
import io.gomint.entity.EntityLiving;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public interface EntityVex extends EntityLiving {

    /**
     * Create a new entity vex with no config
     *
     * @return empty, fresh vex
     */
    static EntityVex create() {
        return GoMint.instance().createEntity( EntityVex.class );
    }

}
