package io.gomint.entity.projectile;

import io.gomint.GoMint;

public interface EntitySnowball extends EntityProjectile {

    /**
     * Create a new thrown snowball entity
     *
     * @return fresh thrown snowball
     */
    static EntitySnowball create() {
        return GoMint.instance().createEntity( EntitySnowball.class );
    }


}
