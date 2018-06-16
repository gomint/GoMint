/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.entity.passive;

import io.gomint.entity.potion.PotionEffect;
import io.gomint.event.entity.EntityDamageEvent;
import io.gomint.event.entity.EntityHealEvent;
import io.gomint.event.player.PlayerExhaustEvent;
import io.gomint.event.player.PlayerFoodLevelChangeEvent;
import io.gomint.math.MathUtils;
import io.gomint.server.entity.*;
import io.gomint.server.entity.metadata.MetadataContainer;
import io.gomint.server.inventory.ArmorInventory;
import io.gomint.server.inventory.PlayerInventory;
import io.gomint.server.network.PlayerConnection;
import io.gomint.server.network.packet.Packet;
import io.gomint.server.network.packet.PacketEntityMetadata;
import io.gomint.server.network.packet.PacketPlayerlist;
import io.gomint.server.network.packet.PacketSpawnPlayer;
import io.gomint.server.player.PlayerSkin;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.util.Values;
import io.gomint.server.world.WorldAdapter;
import io.gomint.world.Difficulty;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.UUID;

/**
 * @author geNAZt
 * @version 1.0
 */
@EqualsAndHashCode( callSuper = false, of = { "uuid" } )
@ToString( of = { "uuid", "username" } )
@RegisterInfo( id = 63 )
public class EntityHuman extends EntityCreature implements io.gomint.entity.passive.EntityHuman {

    private static final int DATA_PLAYER_BED_POSITION = 29;

    private int foodTicks;
    private float lastUpdateDT;

    // Basic information
    private String username;
    private String displayName;
    private UUID uuid;
    private String xboxId = "";

    private PlayerSkin skin;

    /**
     * Player inventory which needs to be inited
     */
    protected PlayerInventory inventory;

    /**
     * Constructs a new EntityLiving
     *
     * @param type  The type of the Entity
     * @param world The world in which this entity is in
     */
    protected EntityHuman( EntityType type, WorldAdapter world ) {
        super( type, world );
        this.initEntity();
    }

    /**
     * Create new entity human for API
     */
    public EntityHuman() {
        super( EntityType.PLAYER, null );
        this.initEntity();

        // Init inventories
        this.inventory = new PlayerInventory( this );
        this.armorInventory = new ArmorInventory( this );

        // Some default values
        this.uuid = UUID.randomUUID();
        this.username = "NPC: " + this.uuid.toString();
        this.displayName = this.username;
        this.metadataContainer.putString( MetadataContainer.DATA_NAMETAG, this.username );
    }

    private void initEntity() {
        this.setSize( 0.6f, 1.8f );
        this.eyeHeight = 1.62f;
        this.offsetY = this.eyeHeight + 0.0001f;
        this.stepHeight = 0.6f;

        this.metadataContainer.putByte( MetadataContainer.DATA_PLAYER_INDEX, (byte) 0 );

        this.metadataContainer.putShort( MetadataContainer.DATA_AIR, (short) 400 );
        this.metadataContainer.putShort( MetadataContainer.DATA_MAX_AIRDATA_MAX_AIR, (short) 400 );
        this.metadataContainer.setDataFlag( MetadataContainer.DATA_INDEX, EntityFlag.BREATHING, true );

        // Sleeping stuff
        this.setPlayerFlag( EntityFlag.PLAYER_SLEEP, false );
        this.metadataContainer.putPosition( DATA_PLAYER_BED_POSITION, 0, 0, 0 );

        // Exhaustion, saturation and food
        addAttribute( Attribute.HUNGER );
        addAttribute( Attribute.SATURATION );
        addAttribute( Attribute.EXHAUSTION );
        addAttribute( Attribute.EXPERIENCE_LEVEL );
        addAttribute( Attribute.EXPERIENCE );

        this.setNameTagAlwaysVisible( true );
        this.setCanClimb( true );
    }

    @Override
    public String getName() {
        return this.username;
    }

    @Override
    public UUID getUUID() {
        return this.uuid;
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
            if ( !this.isDead() && this.shouldTickHunger() ) {
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

            // Breathing
            // Check for block stuff
            boolean breathing = !this.isInsideLiquid() || this.hasEffect( PotionEffect.WATER_BREATHING );
            this.metadataContainer.setDataFlag( MetadataContainer.DATA_INDEX, EntityFlag.BREATHING, breathing );

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

        // Check for sprint
        if ( this.getHunger() <= 6 && this.isSprinting() ) {
            this.setSprinting( false );
        }
    }

    protected boolean shouldTickHunger() {
        return false;
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

    @Override
    public float getSaturation() {
        return this.getAttribute( Attribute.SATURATION );
    }

    /**
     * Add to the current saturation level
     *
     * @param amount which should be added to the saturation
     */
    public void addSaturation( float amount ) {
        AttributeInstance instance = this.getAttributeInstance( Attribute.SATURATION );
        this.setSaturation( instance.getValue() + amount );
    }


    public void setSaturation( float amount ) {
        AttributeInstance instance = this.getAttributeInstance( Attribute.SATURATION );
        float maxVal = instance.getMaxValue();
        float minVal = instance.getMinValue();
        this.setAttribute( Attribute.SATURATION, MathUtils.clamp( amount, minVal, maxVal ) );
    }

    @Override
    public float getHunger() {
        return this.getAttribute( Attribute.HUNGER );
    }

    /**
     * Add to the current hunger level
     *
     * @param amount which should be added to the hunger
     */
    public void addHunger( float amount ) {
        this.setHunger( this.getHunger() + amount );
    }

    @Override
    public void setHunger( float amount ) {
        AttributeInstance instance = this.getAttributeInstance( Attribute.HUNGER );
        float old = instance.getValue();
        this.setAttribute( Attribute.HUNGER, MathUtils.clamp( amount, instance.getMinValue(), instance.getMaxValue() ) );

        if ( ( old < 17 && amount >= 17 ) ||
            ( old < 6 && amount >= 6 ) ||
            ( old > 0 && amount == 0 ) ) {
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

    @Override
    public void setSprinting( boolean value ) {
        // Alter movement speed if needed
        if ( value != isSprinting() ) {
            this.metadataContainer.setDataFlag( MetadataContainer.DATA_INDEX, EntityFlag.SPRINTING, value );
            AttributeInstance movementSpeed = this.getAttributeInstance( Attribute.MOVEMENT_SPEED );
            if ( value ) {
                movementSpeed.setMultiplyModifier( AttributeModifier.SPRINT_MULTIPLY, 1.3f );
            } else {
                movementSpeed.removeMultiplyModifier( AttributeModifier.SPRINT_MULTIPLY );
            }
        }
    }

    @Override
    public boolean isSprinting() {
        return this.metadataContainer.getDataFlag( MetadataContainer.DATA_INDEX, EntityFlag.SPRINTING );
    }

    @Override
    public void setSneaking( boolean value ) {
        if ( value != isSneaking() ) {
            this.metadataContainer.setDataFlag( MetadataContainer.DATA_INDEX, EntityFlag.SNEAKING, value );
            if ( value ) {
                this.height = 1.62f;
            } else {
                this.height = 1.8f;
            }
        }
    }

    @Override
    public boolean isSneaking() {
        return this.metadataContainer.getDataFlag( MetadataContainer.DATA_INDEX, EntityFlag.SNEAKING );
    }

    @Override
    protected void kill() {
        super.kill();
    }

    @Override
    public io.gomint.player.PlayerSkin getSkin() {
        return this.skin;
    }

    @Override
    public String getXboxID() {
        return this.xboxId;
    }

    @Override
    public String getDisplayName() {
        return this.displayName;
    }

    @Override
    public void setDisplayName( String displayName ) {
        this.displayName = displayName;

        if ( this.world != null ) {
            this.updatePlayerList();
        }
    }

    @Override
    public void setSkin( io.gomint.player.PlayerSkin skin ) {
        if ( this.skin != null ) {
            this.skin = (PlayerSkin) skin;
            this.updatePlayerList();
        } else {
            this.skin = (PlayerSkin) skin;
        }
    }

    private void updatePlayerList() {
        PacketPlayerlist packetPlayerlist = new PacketPlayerlist();
        packetPlayerlist.setMode( (byte) 0 );
        packetPlayerlist.setEntries( new ArrayList<PacketPlayerlist.Entry>() {{
            add( new PacketPlayerlist.Entry( EntityHuman.this ) );
        }} );

        for ( io.gomint.entity.EntityPlayer player : this.world.getServer().getPlayers() ) {
            ( (EntityPlayer) player ).getConnection().addToSendQueue( packetPlayerlist );
        }
    }

    @Override
    public PlayerInventory getInventory() {
        return inventory;
    }

    /**
     * Init data from players
     *
     * @param username    of the player
     * @param displayName which should be the same as the username
     * @param xboxId      of the account, empty string if in offline mode
     * @param uuid        of this player
     */
    public void setPlayerData( String username, String displayName, String xboxId, UUID uuid ) {
        this.username = username;
        this.displayName = displayName;
        this.xboxId = xboxId;
        this.uuid = uuid;

        this.metadataContainer.putString( MetadataContainer.DATA_NAMETAG, this.username );
    }

    @Override
    public Packet createSpawnPacket() {
        PacketSpawnPlayer packetSpawnPlayer = new PacketSpawnPlayer();
        packetSpawnPlayer.setUuid( this.getUUID() );
        packetSpawnPlayer.setName( this.getName() );
        packetSpawnPlayer.setEntityId( this.getEntityId() );
        packetSpawnPlayer.setRuntimeEntityId( this.getEntityId() );

        packetSpawnPlayer.setX( this.getPositionX() );
        packetSpawnPlayer.setY( this.getPositionY() );
        packetSpawnPlayer.setZ( this.getPositionZ() );

        packetSpawnPlayer.setVelocityX( this.getMotionX() );
        packetSpawnPlayer.setVelocityY( this.getMotionY() );
        packetSpawnPlayer.setVelocityZ( this.getMotionZ() );

        packetSpawnPlayer.setPitch( this.getPitch() );
        packetSpawnPlayer.setYaw( this.getYaw() );
        packetSpawnPlayer.setHeadYaw( this.getHeadYaw() );

        packetSpawnPlayer.setItemInHand( this.getInventory().getItemInHand() );
        packetSpawnPlayer.setMetadataContainer( this.getMetadata() );
        return packetSpawnPlayer;
    }

    @Override
    public void preSpawn( PlayerConnection connection ) {
        PacketPlayerlist packetPlayerlist = new PacketPlayerlist();
        packetPlayerlist.setMode( (byte) 0 );
        packetPlayerlist.setEntries( new ArrayList<PacketPlayerlist.Entry>() {{
            add( new PacketPlayerlist.Entry( EntityHuman.this ) );
        }} );

        connection.addToSendQueue( packetPlayerlist );
    }

    @Override
    public void postSpawn( PlayerConnection connection ) {
        // TODO: Remove this, its a client bug in 1.2.13
        PacketEntityMetadata metadata = new PacketEntityMetadata();
        metadata.setEntityId( this.getEntityId() );
        metadata.setMetadata( this.metadataContainer );
        connection.addToSendQueue( metadata );

        PacketPlayerlist packetPlayerlist = new PacketPlayerlist();
        packetPlayerlist.setMode( (byte) 1 );
        packetPlayerlist.setEntries( new ArrayList<PacketPlayerlist.Entry>() {{
            add( new PacketPlayerlist.Entry( EntityHuman.this ) );
        }} );

        connection.addToSendQueue( packetPlayerlist );
    }

}
