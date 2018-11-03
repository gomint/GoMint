package io.gomint.server.entity.monster;

import io.gomint.entity.Entity;
import io.gomint.event.entity.EntityDamageEvent;
import io.gomint.server.entity.Attribute;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.entity.EntityType;
import io.gomint.server.entity.metadata.MetadataContainer;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.WorldAdapter;

@RegisterInfo( sId = "minecraft:elder_guardian" )
public class EntityElderGuardian extends EntityLiving implements io.gomint.entity.monster.EntityElderGuardian {

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
        this.setSize( 1.9975f, 1.9975f );
        this.addAttribute( Attribute.HEALTH );
        this.setMaxHealth( 80 );
        this.setHealth( 80 );
    }

    @Override
    public void update( long currentTimeMS, float dT ) {
        super.update( currentTimeMS, dT );
    }

    @Override
    public void onCollideWithPlayer( EntityPlayer player ) {
        super.onCollideWithPlayer( player );

        switch( this.getWorld().getDifficulty() ) {
            case EASY:
                player.attack( 5, EntityDamageEvent.DamageSource.ENTITY_ATTACK );
                break;
            case NORMAL:
                player.attack( 8, EntityDamageEvent.DamageSource.ENTITY_ATTACK );
                break;
            case HARD:
                player.attack( 12, EntityDamageEvent.DamageSource.ENTITY_ATTACK );
                break;
        }
    }

    @Override
    public void setTarget( Entity entity ) {
        this.metadataContainer.putLong( MetadataContainer.DATA_TARGET_EID, entity.getEntityId() );
    }

}
