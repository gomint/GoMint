package io.gomint.server.entity.monster;

import io.gomint.server.entity.Attribute;
import io.gomint.server.entity.EntityAgeable;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.entity.EntityType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.WorldAdapter;

@RegisterInfo( sId = "minecraft:zombie_pigman" )
public class EntityZombiePigman extends EntityAgeable implements io.gomint.entity.monster.EntityZombiePigman {

    /**
     * Constructs a new EntityLiving
     *
     * @param world The world in which this entity is in
     */
    public EntityZombiePigman( WorldAdapter world ) {
        super( EntityType.ZOMBIE_PIGMAN, world );
        this.initEntity();
    }

    /**
     * Create new entity zombie pigman for API
     */
    public EntityZombiePigman() {
        super( EntityType.ZOMBIE_PIGMAN, null );
        this.initEntity();
    }

    private void initEntity() {
        this.addAttribute(Attribute.HEALTH);
        this.setHealth(20);
        this.setMaxHealth(20);
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
}
