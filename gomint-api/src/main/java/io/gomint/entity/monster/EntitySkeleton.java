package io.gomint.entity.monster;

import io.gomint.GoMint;
import io.gomint.entity.EntityLiving;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public interface EntitySkeleton extends EntityLiving<EntitySkeleton> {

    /**
     * Create a new entity skeleton with no config
     *
     * @return empty, fresh skeleton
     */
    static EntitySkeleton create() {
        return GoMint.instance().createEntity( EntitySkeleton.class );
    }

}
