package io.gomint.entity.monster;

import io.gomint.GoMint;
import io.gomint.entity.EntityLiving;

public interface EntitySkeleton extends EntityLiving {

    /**
     * Create a new entity bat with no config
     *
     * @return empty, fresh skeleton
     */
    static EntitySkeleton create() {
        return GoMint.instance().createEntity( EntitySkeleton.class );
    }
}
