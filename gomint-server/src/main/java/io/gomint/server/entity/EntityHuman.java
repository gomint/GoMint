package io.gomint.server.entity;

import io.gomint.event.entity.EntityDamageEvent;
import io.gomint.event.entity.EntityHealEvent;
import io.gomint.event.player.PlayerExhaustEvent;
import io.gomint.event.player.PlayerFoodLevelChangeEvent;
import io.gomint.server.entity.metadata.MetadataContainer;
import io.gomint.server.util.Values;
import io.gomint.server.world.WorldAdapter;
import io.gomint.world.Difficulty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author geNAZt
 * @version 1.0
 */
public class EntityHuman extends EntityLiving {

    private static final Logger LOGGER = LoggerFactory.getLogger( EntityHuman.class );
    private static final int DATA_PLAYER_BED_POSITION = 29;

    private int foodTicks;
    private float lastUpdateDT;

    /**
     * Constructs a new EntityLiving
     *
     * @param type  The type of the Entity
     * @param world The world in which this entity is in
     */
    protected EntityHuman( EntityType type, WorldAdapter world ) {
        super( type, world );
        this.metadataContainer.putByte( MetadataContainer.DATA_PLAYER_INDEX, (byte) 0 );

        // Sleeping stuff
        this.setPlayerFlag( EntityFlag.PLAYER_SLEEP, false );
        this.metadataContainer.putPosition( DATA_PLAYER_BED_POSITION, 0, 0, 0 );

        // Exhaustion, saturation and food
        addAttribute( Attribute.EXHAUSTION );
        addAttribute( Attribute.SATURATION );
    }

    @Override
    public void update( long currentTimeMS, float dT ) {
        super.update( currentTimeMS, dT );

        if ( this.isDead() || this.getHealth() <= 0 ) {
            return;
        }

        // Food tick
        this.lastUpdateDT += dT;
        if ( this.lastUpdateDT >= Values.CLIENT_TICK_RATE ) {
            if ( !this.isDead() ) {
                AttributeInstance hungerInstance = this.getAttributeInstance( Attribute.HUNGER );
                float hunger = hungerInstance.getValue();
                float health = -1;

                Difficulty difficulty = this.world.getDifficulty();
                if ( difficulty == Difficulty.PEACEFUL && this.foodTicks % 10 == 0 ) {
                    if ( hunger < hungerInstance.getMaxValue() ) {
                        this.addHunger( 1.0f );
                    }

                    if ( this.foodTicks % 20 == 0 ) {
                        health = this.getHealth();
                        if ( health < this.getMaxHealth() ) {
                            this.heal( 1, EntityHealEvent.Cause.SATURATION );
                        }
                    }
                }

                if ( this.foodTicks == 0 ) {
                    // Check for regeneration
                    if ( hunger >= 18 ) {
                        if ( health == -1 ) {
                            health = this.getHealth();
                        }

                        if ( health < this.getMaxHealth() ) {
                            this.heal( 1, EntityHealEvent.Cause.SATURATION );
                            this.exhaust( 3f, PlayerExhaustEvent.Cause.REGENERATION );
                        }
                    } else if ( hunger <= 0 ) {
                        if ( health == -1 ) {
                            health = this.getHealth();
                        }

                        if ( ( health > 10 && difficulty == Difficulty.NORMAL ) ||
                            ( difficulty == Difficulty.HARD && health > 1 ) ) {
                            EntityDamageEvent damageEvent = new EntityDamageEvent( this, EntityDamageEvent.DamageSource.STARVE, 1f );
                            this.damage( damageEvent );
                        }
                    }
                }

                this.foodTicks++;

                if ( this.foodTicks >= 80 ) {
                    this.foodTicks = 0;
                }
            }

            this.lastUpdateDT = 0;
        }

        // Check for sprint
        if ( this.getHunger() <= 6 && this.isSprinting() ) {
            this.setSprinting( false );
        }
    }

    /**
     * Set a player flag
     *
     * @param flag  which should be set
     * @param value to what it should be set, true or false
     */
    public void setPlayerFlag( EntityFlag flag, boolean value ) {
        this.metadataContainer.setDataFlag( MetadataContainer.DATA_PLAYER_INDEX, flag, value );
    }

    /**
     * Get the exhaustion level
     *
     * @return exhaustion level
     */
    public float getExhaustion() {
        return this.getAttribute( Attribute.EXHAUSTION );
    }

    /**
     * Set exhaustion level
     *
     * @param amount of exhaustion
     */
    public void setExhaustion( float amount ) {
        this.setAttribute( Attribute.EXHAUSTION, amount );
    }

    /**
     * Get the saturation level
     *
     * @return saturation level
     */
    public float getSaturation() {
        return this.getAttribute( Attribute.SATURATION );
    }

    /**
     * Add to the current saturation level
     *
     * @param amount which should be added to the saturation
     */
    public void addSaturation( float amount ) {
        if ( amount < 0 ) {
            return;
        }

        AttributeInstance instance = this.getAttributeInstance( Attribute.SATURATION );
        float newAmount = Math.max( Math.min( this.getSaturation() + amount, instance.getMaxValue() ), instance.getMinValue() );
        this.setSaturation( newAmount );
    }

    /**
     * Set saturation level
     *
     * @param amount of saturation
     */
    public void setSaturation( float amount ) {
        this.setAttribute( Attribute.SATURATION, amount );
    }

    /**
     * Get the hunger level
     *
     * @return hunger level
     */
    public float getHunger() {
        return this.getAttribute( Attribute.HUNGER );
    }

    /**
     * Add to the current hunger level
     *
     * @param amount which should be added to the hunger
     */
    public void addHunger( float amount ) {
        AttributeInstance instance = this.getAttributeInstance( Attribute.HUNGER );
        float newAmount = Math.max( Math.min( this.getHunger() + amount, instance.getMaxValue() ), instance.getMinValue() );
        this.setHunger( newAmount );
    }

    /**
     * Set hunger level
     *
     * @param amount of hunger
     */
    public void setHunger( float amount ) {
        float old = this.getAttribute( Attribute.HUNGER );
        this.setAttribute( Attribute.HUNGER, amount );

        if ( old < 17 && amount >= 17 ) {
            this.foodTicks = 0;
        } else if ( old < 6 && amount >= 6 ) {
            this.foodTicks = 0;
        } else if ( old > 0 && amount == 0 ) {
            this.foodTicks = 0;
        }
    }

    /**
     * Override for the EntityPlayer implementation
     *
     * @param amount of exhaustion
     * @param cause  of the exhaustion
     */
    public void exhaust( float amount, PlayerExhaustEvent.Cause cause ) {
        this.exhaust( amount );
    }

    /**
     * Exhaust for a specific amount
     *
     * @param amount of exhaust
     */
    public void exhaust( float amount ) {
        float exhaustion = this.getExhaustion() + amount;

        // When exhaustion is over 4 we decrease saturation
        while ( exhaustion >= 4 ) {
            exhaustion -= 4;

            float saturation = this.getSaturation();
            if ( saturation > 0 ) {
                saturation = Math.max( 0, saturation - 1 );
                this.setSaturation( saturation );
            } else {
                float hunger = this.getHunger();
                if ( hunger > 0 ) {
                    if ( this instanceof EntityPlayer ) {
                        PlayerFoodLevelChangeEvent foodLevelChangeEvent = new PlayerFoodLevelChangeEvent(
                            (io.gomint.entity.EntityPlayer) this, -1
                        );

                        this.world.getServer().getPluginManager().callEvent( foodLevelChangeEvent );
                        if ( !foodLevelChangeEvent.isCancelled() ) {
                            hunger = Math.max( 0, hunger - 1 );
                            this.setHunger( hunger );
                        }
                    } else {
                        hunger = Math.max( 0, hunger - 1 );
                        this.setHunger( hunger );
                    }
                }
            }
        }

        this.setExhaustion( exhaustion );
    }

    /**
     * Set player sprinting or not
     *
     * @param value true for sprinting, false for not sprinting
     */
    public void setSprinting( boolean value ) {
        // Alter movement speed if needed
        if ( value != isSprinting() ) {
            this.metadataContainer.setDataFlag( MetadataContainer.DATA_INDEX, EntityFlag.SPRINTING, value );
            float attr = this.getAttribute( Attribute.MOVEMENT_SPEED );
            this.setAttribute( Attribute.MOVEMENT_SPEED, (float) ( ( value ) ? attr * 1.3 : attr / 1.3 ) );
        }
    }

    /**
     * Check if entity is sprinting
     *
     * @return true when sprinting, false when not
     */
    public boolean isSprinting() {
        return this.metadataContainer.getDataFlag( MetadataContainer.DATA_INDEX, EntityFlag.SPRINTING );
    }

    /**
     * Set player sneaking or not
     *
     * @param value true for sneaking, false for not sneaking
     */
    public void setSneaking( boolean value ) {
        if ( value != isSneaking() ) {
            this.metadataContainer.setDataFlag( MetadataContainer.DATA_INDEX, EntityFlag.SNEAKING, value );
        }
    }

    /**
     * Is this player sneaking?
     *
     * @return true when sneaking, false when not
     */
    public boolean isSneaking() {
        return this.metadataContainer.getDataFlag( MetadataContainer.DATA_INDEX, EntityFlag.SNEAKING );
    }

    @Override
    protected void kill() {
        super.kill();
    }

}
