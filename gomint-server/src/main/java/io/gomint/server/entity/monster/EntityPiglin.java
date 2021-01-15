package io.gomint.server.entity.monster;

import io.gomint.server.entity.Attribute;
import io.gomint.server.entity.EntityAgeable;
import io.gomint.server.entity.EntityTags;
import io.gomint.server.entity.EntityType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.WorldAdapter;

import java.util.Set;

/**
 * @author KingAli
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:piglin" )
public class EntityPiglin extends EntityAgeable<io.gomint.entity.monster.EntityPiglin> implements io.gomint.entity.monster.EntityPiglin {

    /**
     * Constructs a new EntityLiving
     *
     * @param world The world in which this entity is in
     */
    public EntityPiglin( WorldAdapter world ) {
        super( EntityType.PIGLIN, world );
        this.initEntity();
    }


    public EntityPiglin() {
        super( EntityType.PIGLIN, null );
        this.initEntity();
    }

    private void initEntity() {
        this.attribute(Attribute.HEALTH);
        this.health(16);
        this.maxHealth(16);
        if(this.baby()) {
            this.size(0.3f, 0.975f);
        }else{
            this.size(0.6f, 1.95f);
        }
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
