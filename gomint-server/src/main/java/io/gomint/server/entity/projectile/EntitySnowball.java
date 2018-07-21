package io.gomint.server.entity.projectile;

import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.entity.EntityType;
import io.gomint.server.world.WorldAdapter;

public class EntitySnowball extends EntityProjectile implements io.gomint.entity.projectile.EntitySnowball {

    /**
     * Construct a new Entity
     *
     * @param player  Which throw a snowball
     * @param world   The world in which this entity is in
     */
    protected EntitySnowball( EntityPlayer player, WorldAdapter world ) {
        super( player, EntityType.SNOWBALL, world );
    }

    @Override
    public boolean isCritical() {
        return false;
    }

    @Override
    public float getDamage() {
        return 0;
    }
}
