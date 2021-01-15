package io.gomint.server.entity.monster;

import io.gomint.entity.Entity;
import io.gomint.server.entity.Attribute;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.entity.EntityTags;
import io.gomint.server.entity.EntityType;
import io.gomint.server.entity.metadata.MetadataContainer;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.WorldAdapter;

import java.util.Set;

@RegisterInfo( sId = "minecraft:elder_guardian" )
public class EntityElderGuardian extends EntityLiving<io.gomint.entity.monster.EntityElderGuardian> implements io.gomint.entity.monster.EntityElderGuardian {

    /**
     * Constructs a new EntityLiving
     *
     * @param world The world in which this entity is in
     */
    public EntityElderGuardian( WorldAdapter world ) {
        super( EntityType.ELDER_GUARDIAN, world );
        this.initEntity();
    }

    /**
     * Create new entity elder guardian for API
     */
    public EntityElderGuardian() {
        super( EntityType.ELDER_GUARDIAN, null );
        this.initEntity();
    }

    private void initEntity() {
        this.size( 1.9975f, 1.9975f );
        this.attribute( Attribute.HEALTH );
        this.maxHealth( 80 );
        this.health( 80 );
    }

    @Override
    public void update( long currentTimeMS, float dT ) {
        super.update( currentTimeMS, dT );
    }

    @Override
    public EntityElderGuardian target(Entity<?> entity ) {
        this.metadataContainer.putLong( MetadataContainer.DATA_TARGET_EID, entity.id() );
        return this;
    }

    @Override
    public Set<String> tags() {
        return EntityTags.RANGED_HOSTILE_MOB;
    }

}
