package io.gomint.server.entity.monster;

import io.gomint.event.entity.EntityDamageEvent;
import io.gomint.server.entity.Attribute;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.entity.EntityType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.WorldAdapter;

@RegisterInfo( sId = "minecraft:enderman" )
public class EntityEnderman extends EntityLiving implements io.gomint.entity.monster.EntityEnderman {

    /**
     * Constructs a new EntityLiving

     * @param world The world in which this entity is in
     */
    public EntityEnderman( WorldAdapter world ) {
        super( EntityType.ENDERMAN, world );
        this.initEntity();
    }

    /**
     * Create new entity enderman for API
     */
    public EntityEnderman() {
        super( EntityType.ENDERMAN, null );
        this.initEntity();
    }

    private void initEntity() {
        this.setSize( 0.6f, 2.9f );
        this.addAttribute( Attribute.HEALTH );
        this.setMaxHealth( 40 );
        this.setHealth( 40 );
    }

    @Override
    public void onCollideWithPlayer( EntityPlayer player ) {
        super.onCollideWithPlayer( player );

        switch( this.getWorld().getDifficulty() ) {
            case EASY:
                player.attack( 4, EntityDamageEvent.DamageSource.ENTITY_ATTACK );
                break;
            case NORMAL:
                player.attack( 7, EntityDamageEvent.DamageSource.ENTITY_ATTACK );
                break;
            case HARD:
                player.attack( 10, EntityDamageEvent.DamageSource.ENTITY_ATTACK );
                break;
        }
    }

    @Override
    public void update( long currentTimeMS, float dT ) {
        super.update( currentTimeMS, dT );
    }
}
