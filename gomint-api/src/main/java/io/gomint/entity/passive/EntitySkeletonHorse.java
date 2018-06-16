package io.gomint.entity.passive;

import io.gomint.GoMint;
import io.gomint.entity.EntityLiving;

public interface EntitySkeletonHorse extends EntityLiving {

    /**
     * Create a new entity donkey with no config
     *
     * @return empty, fresh skeleton horse
     */
    static EntitySkeletonHorse create() {
        return GoMint.instance().createEntity( EntitySkeletonHorse.class );
    }
}
