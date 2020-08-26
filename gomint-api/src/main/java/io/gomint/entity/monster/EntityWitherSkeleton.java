package io.gomint.entity.monster;

import io.gomint.GoMint;
import io.gomint.entity.EntityLiving;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public interface EntityWitherSkeleton extends EntityLiving {

    /**
     * Create a new entity wither skeleton with no config
     *
     * @return empty, fresh wither skeleton
     */
    static EntityWitherSkeleton create() {
        return GoMint.instance().createEntity( EntityWitherSkeleton.class );
    }

}
