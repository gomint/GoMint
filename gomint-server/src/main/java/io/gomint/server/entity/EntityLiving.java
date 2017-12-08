package io.gomint.server.entity;

import com.koloboke.collect.ObjCursor;
import io.gomint.entity.potion.PotionEffect;
import io.gomint.event.entity.EntityDamageByEntityEvent;
import io.gomint.event.entity.EntityDamageEvent;
import io.gomint.event.entity.EntityHealEvent;
import io.gomint.math.Location;
import io.gomint.math.MathUtils;
import io.gomint.math.Vector;
import io.gomint.server.entity.component.AIBehaviourComponent;
import io.gomint.server.entity.metadata.MetadataContainer;
import io.gomint.server.entity.pathfinding.PathfindingEngine;
import io.gomint.server.entity.potion.Effects;
import io.gomint.server.entity.potion.effect.Effect;
import io.gomint.server.inventory.InventoryHolder;
import io.gomint.server.network.packet.Packet;
import io.gomint.server.network.packet.PacketEntityEvent;
import io.gomint.server.network.packet.PacketSpawnEntity;
import io.gomint.server.player.EffectManager;
import io.gomint.server.util.EnumConnectors;
import io.gomint.server.util.Values;
import io.gomint.server.util.collection.EntityHashSet;
import io.gomint.server.world.WorldAdapter;
import io.gomint.world.block.Block;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Common base class for all entities that live. All living entities possess
 * an AI which is the significant characteristic that marks an entity as being
 * alive in GoMint's definition.
 *
 * @author BlackyPaw
 * @version 1.0
 */
public abstract class EntityLiving extends Entity implements InventoryHolder, io.gomint.entity.EntityLiving {

    private static final Logger LOGGER = LoggerFactory.getLogger( EntityLiving.class );

    // AI of the entity:
    protected AIBehaviourComponent behaviour;
    // Pathfinding engine of the entity:
    protected PathfindingEngine pathfinding;

    protected Map<String, AttributeInstance> attributes = new HashMap<>();

    private float lastUpdateDT = 0;
    @Getter
    private EntityHashSet attachedEntities = EntityHashSet.withExpectedSize( 10 );

    private byte attackCoolDown = 0;

    protected int deadTimer = 0;
    private int fireTicks = 0;

    // Damage stats
    protected float lastDamage = 0;
    @Getter
    protected EntityDamageEvent.DamageSource lastDamageSource;
    @Getter
    protected io.gomint.entity.Entity lastDamageEntity;

    // Effects
    private final EffectManager effectManager = new EffectManager( this );

    /**
     * Constructs a new EntityLiving
     *
     * @param type  The type of the Entity
     * @param world The world in which this entity is in
     */
    protected EntityLiving( EntityType type, WorldAdapter world ) {
        super( type, world );
        this.behaviour = new AIBehaviourComponent();
        this.pathfinding = new PathfindingEngine( this.getTransform() );
        this.initAttributes();

        this.metadataContainer.putShort( MetadataContainer.DATA_AIR, (short) 400 );
        this.metadataContainer.putShort( MetadataContainer.DATA_MAX_AIRDATA_MAX_AIR, (short) 400 );
        this.metadataContainer.putFloat( MetadataContainer.DATA_SCALE, 1.0f );
        this.metadataContainer.setDataFlag( MetadataContainer.DATA_INDEX, EntityFlag.BREATHING, true );
    }

    private void initAttributes() {
        addAttribute( Attribute.ABSORPTION );
        addAttribute( Attribute.ATTACK_DAMAGE );
        addAttribute( Attribute.FOLLOW_RANGE );
        addAttribute( Attribute.HEALTH );
        addAttribute( Attribute.MOVEMENT_SPEED );
        addAttribute( Attribute.KNOCKBACK_RESISTANCE );
    }

    protected float addAttribute( Attribute attribute ) {
        AttributeInstance instance = attribute.create();
        this.attributes.put( instance.getKey(), instance );
        return instance.getValue();
    }

    public float getAttribute( Attribute attribute ) {
        AttributeInstance instance = this.attributes.get( attribute.getKey() );
        if ( instance != null ) {
            return instance.getValue();
        }

        return addAttribute( attribute );
    }

    public AttributeInstance getAttributeInstance( Attribute attribute ) {
        return this.attributes.get( attribute.getKey() );
    }

    public void setAttribute( Attribute attribute, float value ) {
        AttributeInstance instance = this.attributes.get( attribute.getKey() );
        if ( instance != null ) {
            instance.setValue( value );
        }
    }

    @Override
    protected void fall() {
        // Check for jump potion
        float distanceReduce = 0.0f;
        int jumpAmplifier = getEffectAmplifier( PotionEffect.JUMP );
        if ( jumpAmplifier != -1 ) {
            distanceReduce = jumpAmplifier + 1;
        }

        float damage = MathUtils.fastFloor( this.fallDistance - 3f - distanceReduce );
        if ( damage > 0 ) {
            EntityDamageEvent damageEvent = new EntityDamageEvent( this,
                EntityDamageEvent.DamageSource.FALL, damage );
            this.damage( damageEvent );
        }
    }

    // ==================================== UPDATING ==================================== //

    @Override
    public void update( long currentTimeMS, float dT ) {
        if ( !( this.isDead() || this.getHealth() <= 0 ) ) {
            super.update( currentTimeMS, dT );
            this.behaviour.update( currentTimeMS, dT );
        }

        // Check if last hit entity is still alive
        if ( this.lastDamageEntity != null ) {
            if ( this.lastDamageEntity.isDead() ) {
                this.lastDamageEntity = null;
            }
        }

        // Only update when alive
        if ( this.getHealth() >= 0 && !this.isDead() ) {
            // Update effects
            this.effectManager.update( currentTimeMS, dT );
        }

        // Check for client tick stuff
        this.lastUpdateDT += dT;
        if ( this.lastUpdateDT >= Values.CLIENT_TICK_RATE ) {
            // Calc death stuff
            if ( this.getHealth() <= 0 ) {
                if ( this.deadTimer != -1 && this.deadTimer++ >= 20 ) {
                    despawn();
                    this.deadTimer = -1;
                }
            } else {
                this.deadTimer = 0;
            }

            if ( this.isDead() || this.getHealth() <= 0 ) {
                this.lastUpdateDT = 0;
                return;
            }

            // Reset attack cooldown
            if ( this.attackCoolDown > 0 ) {
                this.attackCoolDown--;
            }

            // Fire?
            if ( this.fireTicks > 0 ) {
                if ( this.fireTicks % 20 == 0 ) {
                    EntityDamageEvent damageEvent = new EntityDamageEvent( this, EntityDamageEvent.DamageSource.ON_FIRE, 1.0f );
                    damage( damageEvent );
                }

                this.fireTicks--;
                if ( this.fireTicks == 0 ) {
                    setOnFire( false );
                }
            }

            // Check for block stuff
            boolean breathing = !this.isInsideLiquid() || this.hasEffect( PotionEffect.WATER_BREATHING );
            this.metadataContainer.setDataFlag( MetadataContainer.DATA_INDEX, EntityFlag.BREATHING, breathing );

            // Check for damage due to blocks (cactus, fire, lava)
            List<Block> blockList = this.world.getCollisionBlocks( this );
            if ( blockList != null ) {
                for ( Block block : blockList ) {
                    io.gomint.server.world.block.Block implBlock = (io.gomint.server.world.block.Block) block;
                    implBlock.onEntityCollision( this );
                }
            }

            io.gomint.server.world.block.Block standingIn = this.world.getBlockAt( this.getPosition().toBlockPosition() );
            standingIn.onEntityStanding( this );

            // Breathing
            short air = this.metadataContainer.getShort( MetadataContainer.DATA_AIR );
            short maxAir = this.metadataContainer.getShort( MetadataContainer.DATA_MAX_AIRDATA_MAX_AIR );

            if ( !breathing ) {
                if ( --air < 0 ) {
                    EntityDamageEvent damageEvent = new EntityDamageEvent( this, EntityDamageEvent.DamageSource.DROWNING, 2.0f );
                    damage( damageEvent );
                } else {
                    this.metadataContainer.putShort( MetadataContainer.DATA_AIR, air );
                }
            } else {
                if ( air != maxAir ) {
                    this.metadataContainer.putShort( MetadataContainer.DATA_AIR, maxAir );
                }
            }

            this.lastUpdateDT = 0;
        }
    }

    @Override
    public void setHealth( float amount ) {
        AttributeInstance attributeInstance = this.attributes.get( Attribute.HEALTH.getKey() );
        attributeInstance.setValue( Math.min( attributeInstance.getMaxValue(), Math.max( attributeInstance.getMinValue(), amount ) ) );
    }

    @Override
    public float getHealth() {
        return this.getAttribute( Attribute.HEALTH );
    }

    /**
     * Construct a spawn packet for this entity
     *
     * @return the spawn packet of this entity, ready to be sent to the client
     */
    @Override
    public Packet createSpawnPacket() {
        // Broadcast spawn entity packet:
        PacketSpawnEntity packet = new PacketSpawnEntity();
        packet.setEntityId( this.id );
        packet.setEntityType( this.type );
        packet.setX( this.getPositionX() );
        packet.setY( this.getPositionY() );
        packet.setZ( this.getPositionZ() );
        packet.setVelocityX( this.getMotionX() );
        packet.setVelocityY( this.getMotionY() );
        packet.setVelocityZ( this.getMotionZ() );
        packet.setPitch( this.getPitch() );
        packet.setYaw( this.getYaw() );
        packet.setAttributes( this.attributes.values() );
        packet.setMetadata( this.getMetadata() );
        return packet;
    }

    @Override
    public void setAbsorptionHearts( float amount ) {
        AttributeInstance attributeInstance = this.attributes.get( Attribute.ABSORPTION.getKey() );
        attributeInstance.setValue( MathUtils.clamp( amount, attributeInstance.getMinValue(), attributeInstance.getMaxValue() ) );
    }

    @Override
    public float getAbsorptionHearts() {
        return this.getAttribute( Attribute.ABSORPTION );
    }

    @Override
    public float getMaxHealth() {
        return this.getAttributeInstance( Attribute.HEALTH ).getMaxValue();
    }

    /**
     * Heal this entity by given amount and cause
     *
     * @param amount of heal
     * @param cause  of this heal
     */
    public void heal( float amount, EntityHealEvent.Cause cause ) {
        EntityHealEvent event = new EntityHealEvent( this, amount, cause );
        this.world.getServer().getPluginManager().callEvent( event );

        if ( !event.isCancelled() ) {
            this.setHealth( this.getHealth() + amount );
        }
    }

    @Override
    public boolean damage( EntityDamageEvent damageEvent ) {
        // Don't damage dead entities
        if ( this.getHealth() <= 0 ) {
            return false;
        }

        // Check for effect blocking
        if ( hasEffect( PotionEffect.FIRE_RESISTANCE ) && (
                damageEvent.getDamageSource() == EntityDamageEvent.DamageSource.FIRE ||
                    damageEvent.getDamageSource() == EntityDamageEvent.DamageSource.LAVA ||
                    damageEvent.getDamageSource() == EntityDamageEvent.DamageSource.ON_FIRE
            ) ) {
            return false;
        }

        // Armor calculations
        float damage = applyArmorReduction( damageEvent );
        damage = applyEffectReduction( damageEvent, damage );

        // Absorption
        float absorptionHearts = this.getAbsorptionHearts();
        if ( absorptionHearts > 0 ) {
            float oldDamage = damage;
            damage = Math.max( damage - absorptionHearts, 0f );
            this.setAbsorptionHearts( absorptionHearts - ( oldDamage - damage ) );
        }

        // Check for attack timer
        if ( this.attackCoolDown > 0 && damage <= this.lastDamage ) {
            return false;
        }

        // Call event
        if ( !super.damage( damageEvent ) ) {
            return false;
        }

        float health = MathUtils.fastCeil( this.getHealth() - damage );

        // Set health
        this.setHealth( health <= 0 ? 0 : health );

        // Send animation
        PacketEntityEvent entityEvent = new PacketEntityEvent();
        entityEvent.setEntityId( this.id );
        entityEvent.setEventId( ( health <= 0 ) ? EntityEvent.DEATH.getId() : EntityEvent.HURT.getId() );

        ObjCursor<io.gomint.entity.Entity> attachedEntitiesCursor = this.attachedEntities.cursor();
        while ( attachedEntitiesCursor.moveNext() ) {
            EntityPlayer entityPlayer = (EntityPlayer) attachedEntitiesCursor.elem();
            entityPlayer.getConnection().addToSendQueue( entityEvent );
        }

        if ( damageEvent instanceof EntityDamageByEntityEvent ) {
            // Knockback
            Entity entity = (Entity) ( (EntityDamageByEntityEvent) damageEvent ).getAttacker();
            float diffX = this.getPositionX() - entity.getPositionX();
            float diffZ = this.getPositionZ() - entity.getPositionZ();

            float distance = (float) Math.sqrt( diffX * diffX + diffZ * diffZ );
            if ( distance > 0.0 ) {
                float baseModifier = 0.4F;

                distance = 1 / distance;

                Vector motion = this.getVelocity();
                motion.divide( 2f, 2f, 2f );
                motion.add(
                    ( diffX * distance * baseModifier ),
                    baseModifier,
                    ( diffZ * distance * baseModifier )
                );

                if ( motion.getY() > baseModifier ) {
                    motion.setY( baseModifier );
                }

                this.setVelocity( motion, true );
            }
        }

        this.lastDamage = damage;
        this.lastDamageSource = damageEvent.getDamageSource();
        this.lastDamageEntity = ( damageEvent instanceof EntityDamageByEntityEvent ) ? ( (EntityDamageByEntityEvent) damageEvent ).getAttacker() : null;

        if ( this instanceof EntityPlayer ) {
            ( (EntityPlayer) this ).getConnection().addToSendQueue( entityEvent );
        }

        if ( health <= 0 ) {
            this.kill();
        }

        this.attackCoolDown = 10;
        return true;
    }

    float applyEffectReduction( EntityDamageEvent damageEvent, float damage ) {
        // Starve is absolute damage
        if ( damageEvent.getDamageSource() == EntityDamageEvent.DamageSource.STARVE ) {
            return damage;
        }

        int damageResistanceAmplifier = getEffectAmplifier( PotionEffect.DAMAGE_RESISTANCE );
        if ( damageResistanceAmplifier != -1 && damageEvent.getDamageSource() != EntityDamageEvent.DamageSource.VOID ) {
            float maxReductionDiff = 25 -  ( ( damageResistanceAmplifier + 1 ) * 5);
            float amplifiedDamage = damage * maxReductionDiff;
            damage = amplifiedDamage / 25.0F;
        }

        if ( damage <= 0.0f ) {
            return 0.0f;
        }

        return damage;
    }

    /**
     * Reset fire status on kill
     */
    protected void kill() {
        this.fireTicks = 0;
        setOnFire( false );

        this.effectManager.removeAll();
    }

    /**
     * Apply reduction based on the armor value of a entity
     *
     * @param damageEvent which wants to deal damage
     * @return damage left over after removing armor reductions
     */
    float applyArmorReduction( EntityDamageEvent damageEvent ) {
        return damageEvent.getDamage();
    }

    @Override
    public void attach( EntityPlayer player ) {
        this.attachedEntities.add( player );
        this.effectManager.sendForPlayer( player );
    }

    @Override
    public void detach( EntityPlayer player ) {
        this.attachedEntities.remove( player );
    }

    void resetAttributes() {
        for ( AttributeInstance instance : this.attributes.values() ) {
            instance.reset();
        }
    }

    @Override
    void dealVoidDamage() {
        EntityDamageEvent damageEvent = new EntityDamageEvent( this,
            EntityDamageEvent.DamageSource.VOID, 4.0F );
        this.damage( damageEvent );
    }

    /**
     * Set entity on fire for given amount of seconds
     *
     * @param seconds for how long this entity should be on fire
     */
    public void setFire( int seconds ) {
        int newFireTicks = seconds * 20;
        if ( newFireTicks > this.fireTicks ) {
            this.fireTicks = newFireTicks;
            setOnFire( true );
        } else if ( newFireTicks == 0 ) {
            this.fireTicks = 0;
            setOnFire( false );
        }
    }


    @Override
    public void addEffect( PotionEffect effect, int amplifier, long duration, TimeUnit timeUnit ) {
        byte effectId = (byte) EnumConnectors.POTION_EFFECT_CONNECTOR.convert( effect ).getId();
        Effect effectInstance = Effects.generate( effectId, amplifier, this.world.getServer().getCurrentTickTime() + timeUnit.toMillis( duration ) );

        if ( effectInstance != null ) {
            this.effectManager.addEffect( effectId, effectInstance );
        }
    }

    @Override
    public boolean hasEffect( PotionEffect effect ) {
        byte effectId = (byte) EnumConnectors.POTION_EFFECT_CONNECTOR.convert( effect ).getId();
        return this.effectManager.hasEffect( effectId );
    }

    @Override
    public int getEffectAmplifier( PotionEffect effect ) {
        byte effectId = (byte) EnumConnectors.POTION_EFFECT_CONNECTOR.convert( effect ).getId();
        return this.effectManager.getEffectAmplifier( effectId );
    }

    @Override
    public void removeEffect( PotionEffect effect ) {
        byte effectId = (byte) EnumConnectors.POTION_EFFECT_CONNECTOR.convert( effect ).getId();
        this.effectManager.removeEffect( effectId );
    }

    @Override
    public void removeAllEffects() {
        this.effectManager.removeAll();
    }

    @Override
    public void teleport( Location to ) {
        WorldAdapter actualWorld = this.getWorld();

        this.setAndRecalcPosition( to );

        if ( !to.getWorld().equals( actualWorld ) ) {
            actualWorld.removeEntity( this );
            this.setWorld( (WorldAdapter) to.getWorld() );
            ( (WorldAdapter) to.getWorld() ).spawnEntityAt( this, to.getX(), to.getY(), to.getZ(), to.getYaw(), to.getPitch() );
        }

        this.fallDistance = 0;
    }

    @Override
    public float getMovementSpeed() {
        return this.getAttribute( Attribute.MOVEMENT_SPEED );
    }

    @Override
    public void setMovementSpeed( float value ) {
        this.setAttribute( Attribute.MOVEMENT_SPEED, value );
    }

    @Override
    public String getNameTag() {
        return getMetadata().getString( 4 );
    }

    @Override
    public void setNameTag( String nameTag ) {
        getMetadata().putString( 4, nameTag );
    }

}
