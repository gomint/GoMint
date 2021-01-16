package io.gomint.entity.monster;

import io.gomint.GoMint;
import io.gomint.entity.EntityLiving;

/**
 * @author LucGames
 * @version 1.0
 * @stability 3
 */
public interface EntitySlime extends EntityLiving<EntitySlime> {

    /**
     * Create a new entity slime with no config
     *
     * @return empty, fresh slime
     */
    static EntitySlime create() {
        return GoMint.instance().createEntity( EntitySlime.class );
    }

    /**
     * Set a new size for this entity. This changes the hitbox to factor * 0.51f (for both width and height).
     * Health is 2^factor. When the size changes the entity will be healed to its new maximum health.
     *
     * @param factor of this slime
     */
    EntitySlime sizeFactor(int factor );

}
