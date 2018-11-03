package io.gomint.server.entity.monster;

import io.gomint.event.entity.EntityDamageEvent;
import io.gomint.server.entity.Attribute;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.entity.EntityType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.WorldAdapter;

@RegisterInfo( sId = "minecraft:husk" )
public class EntityHusk extends EntityLiving implements io.gomint.entity.monster.EntityHusk{

    /**
     * Constructs a new EntityLiving
     *
     * @param world The world in which this entity is in
     */
    public EntityHusk( WorldAdapter world ) {
        super( EntityType.HUSK, world );
        this.initEntity();
    }

    /**
     * Create new entity husk for API
     */
    public EntityHusk() {
        super( EntityType.HUSK, null );
        this.initEntity();
    }

    private void initEntity() {
        this.setSize( 0.6f, 1.95f );
        this.addAttribute( Attribute.HEALTH );
        this.setMaxHealth( 20 );
        this.setHealth( 20 );
    }

    @Override
    public void onCollideWithPlayer( EntityPlayer player ) {
        super.onCollideWithPlayer( player );

        switch( this.getWorld().getDifficulty() ) {
            case EASY:
                player.attack( 2, EntityDamageEvent.DamageSource.ENTITY_ATTACK );
                break;
            case NORMAL:
                player.attack( 3, EntityDamageEvent.DamageSource.ENTITY_ATTACK );
                break;
            case HARD:
                player.attack( 4, EntityDamageEvent.DamageSource.ENTITY_ATTACK );
                break;
        }
    }

    @Override
    public void update( long currentTimeMS, float dT ) {
        super.update( currentTimeMS, dT );
    }
}
