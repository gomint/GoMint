package io.gomint.entity.projectile;

import io.gomint.GoMint;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public interface EntityEnderpearl extends EntityProjectile<EntityEnderpearl> {

    /**
     * Create a new thrown enderpearl entity
     *
     * @return fresh thrown enderpearl
     */
    static EntityEnderpearl create() {
        return GoMint.instance().createEntity( EntityEnderpearl.class );
    }

}
