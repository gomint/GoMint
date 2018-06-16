package io.gomint.entity.monster;

import io.gomint.GoMint;
import io.gomint.entity.EntityLiving;

/**
 * @author LucGames
 * @version 1.0
 */
public interface EntityMagmaCube extends EntityLiving {

    /**
     * Create a new entity bat with no config
     *
     * @return empty, fresh magma cube
     */
    static EntityMagmaCube create() {
        return GoMint.instance().createEntity( EntityMagmaCube.class );
    }

    /**
     * Set a new size for this entity. This changes the hitbox to factor * 0.51f (for both width and height).
     * Health is 2^factor. When the size changes the entity will be healed to its new maximum health.
     *
     * @param factor of this magma cube
     */
    void setSizeFactor( int factor );

}
