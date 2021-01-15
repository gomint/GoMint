package io.gomint.entity.passive;

import io.gomint.GoMint;
import io.gomint.entity.EntityCreature;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public interface EntityArmorStand extends EntityCreature<EntityArmorStand> {

    /**
     * Create a new entity armor stand with no config
     *
     * @return empty, fresh armor stand
     */
    static EntityArmorStand create() {
        return GoMint.instance().createEntity( EntityArmorStand.class );
    }

}
