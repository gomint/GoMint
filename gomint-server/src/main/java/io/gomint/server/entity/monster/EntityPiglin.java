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
public class EntityPiglin extends EntityAgeable implements io.gomint.entity.monster.EntityPiglin {

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
        this.addAttribute(Attribute.HEALTH);
        this.setHealth(16);
        this.setMaxHealth(16);
        if(this.isBaby()) {
            this.setSize(0.3f, 0.975f);
        }else{
            this.setSize(0.6f, 1.95f);
        }
    }

    @Override
    public void update( long currentTimeMS, float dT ) {
        super.update( currentTimeMS, dT );
    }

    @Override
    public Set<String> getTags() {
        return EntityTags.HOSTILE_MOB;
    }

}
