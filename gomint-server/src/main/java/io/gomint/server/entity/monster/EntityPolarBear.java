package io.gomint.server.entity.monster;

import io.gomint.event.entity.EntityDamageEvent;
import io.gomint.server.entity.Attribute;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.entity.EntityType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.WorldAdapter;

@RegisterInfo( sId = "minecraft:polar_bear" )
public class EntityPolarBear extends EntityLiving implements io.gomint.entity.monster.EntityPolarBear {

    /**
     * Constructs a new EntityLiving
     *
     * @param world The world in which this entity is in
     */
    public EntityPolarBear( WorldAdapter world ) {
        super( EntityType.POLAR_BEAR, world );
        this.initEntity();
    }

    /**
     * Create new entity polar bear for API
     */
    public EntityPolarBear() {
        super( EntityType.POLAR_BEAR, null );
        this.initEntity();
    }

    private void initEntity() {
        this.setSize( 1.3f, 1.4f );
        this.addAttribute( Attribute.HEALTH );
        this.setMaxHealth( 30 );
        this.setHealth( 30 );
    }

    @Override
    public void onCollideWithPlayer( EntityPlayer player ) {
        super.onCollideWithPlayer( player );

        switch( this.getWorld().getDifficulty() ) {
            case EASY:
                player.attack( 4, EntityDamageEvent.DamageSource.ENTITY_ATTACK );
                break;
            case NORMAL:
                player.attack( 6, EntityDamageEvent.DamageSource.ENTITY_ATTACK );
                break;
            case HARD:
                player.attack( 9, EntityDamageEvent.DamageSource.ENTITY_ATTACK );
                break;
        }
    }

    @Override
    public void update( long currentTimeMS, float dT ) {
        super.update( currentTimeMS, dT );
    }
}
