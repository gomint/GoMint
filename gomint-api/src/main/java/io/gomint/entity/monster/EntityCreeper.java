package io.gomint.entity.monster;

import io.gomint.GoMint;
import io.gomint.entity.EntityLiving;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public interface EntityCreeper extends EntityLiving {

    /**
     * Create a new entity creeper with no config
     *
     * @return empty, fresh creeper
     */
    static EntityCreeper create() {
        return GoMint.instance().createEntity( EntityCreeper.class );
    }

    /**
     * Set this entity charged
     *
     * @param value true if this creeper should be charged, false if not
     */
    void setCharged( boolean value );

    /**
     * Is the creeper charged?
     *
     * @return true if this creeper is charged, false if not
     */
    boolean isCharged();

}
