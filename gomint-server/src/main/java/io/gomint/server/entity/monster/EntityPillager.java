package io.gomint.server.entity.monster;

import io.gomint.server.entity.Attribute;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.entity.EntityTags;
import io.gomint.server.entity.EntityType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.WorldAdapter;

import java.util.Set;

/**
 * @author KingAli
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:pillager" )
public class EntityPillager extends EntityLiving<io.gomint.entity.monster.EntityPillager> implements io.gomint.entity.monster.EntityPillager {

    /**
     * Constructs a new EntityLiving
     *
     * @param world The world in which this entity is in
     */
    public EntityPillager( WorldAdapter world ) {
        super( EntityType.PILLAGER, world );
        this.initEntity();
    }

    public EntityPillager() {
        super( EntityType.PILLAGER, null );
        this.initEntity();
    }

    private void initEntity() {
        this.size( 0.6f, 1.95f );
        this.attribute( Attribute.HEALTH );
        this.maxHealth( 24 );
        this.health( 24 );
    }

    @Override
    public void update( long currentTimeMS, float dT ) {
        super.update( currentTimeMS, dT );
    }

    @Override
    public Set<String> tags() {
        return EntityTags.RANGED_HOSTILE_MOB;
    }

}
