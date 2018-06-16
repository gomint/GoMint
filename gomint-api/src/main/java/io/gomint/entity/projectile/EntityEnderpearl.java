package io.gomint.entity.projectile;

import io.gomint.GoMint;

/**
 * @author geNAZt
 * @version 1.0
 */
public interface EntityEnderpearl extends EntityProjectile {

    /**
     * Create a new thrown enderpearl entity
     *
     * @return fresh thrown enderpearl
     */
    static EntityEnderpearl create() {
        return GoMint.instance().createEntity( EntityEnderpearl.class );
    }

}
