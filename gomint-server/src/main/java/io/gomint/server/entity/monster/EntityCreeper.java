package io.gomint.server.entity.monster;

import io.gomint.server.entity.Attribute;
import io.gomint.server.entity.EntityFlag;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.entity.EntityTags;
import io.gomint.server.entity.EntityType;
import io.gomint.server.entity.metadata.MetadataContainer;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.WorldAdapter;

import java.util.Set;

@RegisterInfo( sId = "minecraft:creeper" )
public class EntityCreeper extends EntityLiving<io.gomint.entity.monster.EntityCreeper> implements io.gomint.entity.monster.EntityCreeper {

    /**
     * Constructs a new EntityLiving
     *
     * @param world The world in which this entity is in
     */
    public EntityCreeper( WorldAdapter world ) {
        super( EntityType.CREEPER, world );
        this.initEntity();
    }

    /**
     * Create new entity creeper for API
     */
    public EntityCreeper() {
        super( EntityType.CREEPER, null );
        this.initEntity();
    }

    private void initEntity() {
        this.size( 0.6f, 1.7f );
        this.attribute( Attribute.HEALTH );
        this.maxHealth( 20 );
        this.health( 20 );
    }

    @Override
    public boolean charged() {
        return this.metadataContainer.getDataFlag( MetadataContainer.DATA_INDEX, EntityFlag.CHARGED );
    }

    @Override
    public EntityCreeper charged(boolean value ) {
        this.metadataContainer.setDataFlag( MetadataContainer.DATA_INDEX, EntityFlag.CHARGED, value );
        return this;
    }

    @Override
    public void update( long currentTimeMS, float dT ) {
        super.update( currentTimeMS, dT );
    }

    @Override
    public Set<String> tags() {
        return EntityTags.HOSTILE_MOB;
    }

}
