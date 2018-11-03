package io.gomint.server.entity.monster;

import io.gomint.entity.potion.PotionEffect;
import io.gomint.event.entity.EntityDamageEvent;
import io.gomint.server.entity.Attribute;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.entity.EntityType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.WorldAdapter;

import java.util.concurrent.TimeUnit;

@RegisterInfo( sId = "minecraft:cave_spider" )
public class EntityCaveSpider extends EntityLiving implements io.gomint.entity.monster.EntityCaveSpider {

    /**
     * Constructs a new EntityLiving
     *
     * @param world The world in which this entity is in
     */
    public EntityCaveSpider( WorldAdapter world ) {
        super( EntityType.CAVE_SPIDER, world );
        this.initEntity();
    }

    /**
     * Create new entity cave spider for API
     */
    public EntityCaveSpider() {
        super( EntityType.CAVE_SPIDER, null );
        this.initEntity();
    }

    private void initEntity() {
        this.setSize( 0.7f, 0.5f );
        this.addAttribute( Attribute.HEALTH );
        this.setMaxHealth( 12 );
        this.setHealth( 12 );
    }

    @Override
    public void onCollideWithPlayer( EntityPlayer player ) {
        super.onCollideWithPlayer( player );

        switch( this.getWorld().getDifficulty() ) {
            case EASY:
                player.attack( 2, EntityDamageEvent.DamageSource.ENTITY_ATTACK );
                break;
            case NORMAL:
                player.addEffect( PotionEffect.POISON, 0, 7, TimeUnit.SECONDS );
                break;
            case HARD:
                player.addEffect( PotionEffect.POISON, 0, 15, TimeUnit.SECONDS );
                break;
        }
    }

    @Override
    public void update( long currentTimeMS, float dT ) {
        super.update( currentTimeMS, dT );
    }
}
