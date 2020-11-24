/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.entity;

import io.gomint.entity.BossBar;
import io.gomint.event.entity.EntityDamageEvent;
import io.gomint.event.entity.EntityTeleportEvent;
import io.gomint.event.entity.EntityVelocityEvent;
import io.gomint.math.AxisAlignedBB;
import io.gomint.math.Location;
import io.gomint.math.MathUtils;
import io.gomint.math.Vector;
import io.gomint.math.Vector2;
import io.gomint.server.entity.component.TransformComponent;
import io.gomint.server.entity.metadata.MetadataContainer;
import io.gomint.server.network.PlayerConnection;
import io.gomint.server.network.packet.Packet;
import io.gomint.server.network.packet.PacketEntityMetadata;
import io.gomint.server.network.packet.PacketEntityMotion;
import io.gomint.server.network.packet.PacketEntityMovement;
import io.gomint.server.network.packet.PacketSpawnEntity;
import io.gomint.server.util.Values;
import io.gomint.server.world.CoordinateUtils;
import io.gomint.server.world.WorldAdapter;
import io.gomint.server.world.block.Block;
import io.gomint.server.world.block.FlowingWater;
import io.gomint.server.world.block.Ladder;
import io.gomint.server.world.block.Liquid;
import io.gomint.server.world.block.StationaryWater;
import io.gomint.server.world.block.Vines;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.world.Chunk;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Base class for all entities. Defines accessors to attributes and components that are
 * common to all entities such as ID, type and transformation.
 *
 * @author BlackyPaw
 * @version 1.1
 */
public abstract class Entity implements io.gomint.entity.Entity {

    private static final Logger LOGGER = LoggerFactory.getLogger( Entity.class );
    private static final AtomicLong ENTITY_ID = new AtomicLong( 0 );
    /**
     * The id of this entity
     */
    protected final long id;

    /**
     * Type of the entity
     */
    protected final EntityType type;

    /**
     * Metadata
     */
    protected final MetadataContainer metadataContainer;
    // Useful stuff for movement. Those are values for per client tick
    protected float GRAVITY = 0.08f;
    protected float DRAG = 0.02f;

    /**
     * Bounding Box
     */
    protected AxisAlignedBB boundingBox;
    protected float height;

    /**
     * How high can this entity "climb" in one movement?
     */
    protected float stepHeight = 0;
    protected boolean onGround;

    /**
     * Collision states
     */
    // CHECKSTYLE:OFF
    protected boolean isCollidedVertically;
    protected boolean isCollidedHorizontally;
    protected boolean isCollided;
    protected Set<Block> collidedWith = new HashSet<>();

    /**
     * Fall distance tracking
     */
    protected float fallDistance = 0;

    /**
     * Since MC:PE movements are eye instead of foot based we need to offset by this amount
     */
    protected float eyeHeight;
    // CHECKSTYLE:ON
    protected float offsetY;
    protected int age;
    protected WorldAdapter world;
    protected boolean ticking = true;
    private float width;
    private boolean stuckInBlock = false;

    /**
     * Dead status
     */
    private boolean dead;
    private TransformComponent transform;
    private float lastUpdateDT;
    private List<EntityLink> links;
    // Some tracker for "smooth" movement
    private int stuckInBlockTicks = 0;

    private io.gomint.server.entity.BossBar bossBar;

    /**
     * Hidden status
     */
    private boolean hideByDefault;
    private Set<EntityPlayer> shownFor;

    /**
     * Movement status
     */
    private int nextFullMovement = 20;
    private Location oldPosition;

    /**
     * Construct a new Entity
     *
     * @param type  The type of the Entity
     * @param world The world in which this entity is in
     */
    protected Entity( EntityType type, WorldAdapter world ) {
        this.id = ENTITY_ID.incrementAndGet();
        this.type = type;
        this.world = world;
        this.metadataContainer = new MetadataContainer();
        this.metadataContainer.putLong( MetadataContainer.DATA_INDEX, 0 );
        this.metadataContainer.putInt( MetadataContainer.DATA_VARIANT, 0 );
        this.metadataContainer.putInt( MetadataContainer.DATA_POTION_COLOR, 0 );
        this.setScale( 1.0f );
        this.transform = new TransformComponent();
        this.boundingBox = new AxisAlignedBB( 0, 0, 0, 0, 0, 0 );

        // Set some default stuff
        this.setHasCollision( true );
        this.setAffectedByGravity( true );
        this.setNameTagVisible( true );

        // Apply custom properties
        this.applyCustomProperties();
    }

    /**
     * Hook which gets called when all default properties are set
     */
    protected void applyCustomProperties() {
    }

    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }

    public void setFallDistance(float fallDistance) {
        this.fallDistance = fallDistance;
    }

    @Override
    public float getFallDistance() {
        return fallDistance;
    }

    @Override
    public float getEyeHeight() {
        return eyeHeight;
    }

    public float getOffsetY() {
        return offsetY;
    }

    @Override
    public boolean isTicking() {
        return ticking;
    }

    @Override
    public void setTicking(boolean ticking) {
        this.ticking = ticking;
    }

    public float getWidth() {
        return width;
    }

    @Override
    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public List<EntityLink> getLinks() {
        return links;
    }

    public Location getOldPosition() {
        return oldPosition;
    }

    public float getHeight() {
        return height;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entity entity = (Entity) o;
        return id == entity.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Entity{" +
            "id=" + id +
            '}';
    }

    // ==================================== ACCESSORS ==================================== //

    @Override
    public long getEntityId() {
        return this.id;
    }

    /**
     * Gets the type of this entity.
     *
     * @return The type of this entity
     */
    public EntityType getType() {
        return this.type;
    }

    @Override
    public WorldAdapter getWorld() {
        return this.world;
    }

    public void setWorld( WorldAdapter world ) {
        this.world = world;
    }

    /**
     * Gets a metadata container containing all metadata values of this entity.
     *
     * @return This entity's metadata
     */
    public MetadataContainer getMetadata() {
        return this.metadataContainer;
    }

    // ==================================== UPDATING ==================================== //

    /**
     * Despawns this entity if it is currently spawned into any world.
     */
    public void despawn() {
        this.dead = true;
    }

    /**
     * Updates the entity and all components attached to it.
     *
     * @param currentTimeMS The current system time in milliseconds
     * @param dT            The time that has passed since the last tick in 1/s
     */
    public void update( long currentTimeMS, float dT ) {
        if ( this.dead ) {
            return;
        }

        this.transform.update( currentTimeMS, dT );

        this.lastUpdateDT += dT;
        if ( Values.CLIENT_TICK_RATE - this.lastUpdateDT < MathUtils.EPSILON ) {
            this.age++;

            if ( !isImmobile() ) {
                float movX = this.getMotionX();
                float movY = this.getMotionY();
                float movZ = this.getMotionZ();

                Vector moved = null;
                if ( this.shouldMove() ) {
                    moved = this.safeMove( movX, movY, movZ );
                }

                if ( this.isAffectedByGravity() ) {
                    // Calc motion
                    this.transform.manipulateMotion( 0, -this.GRAVITY, 0 );

                    // Calculate friction
                    float friction = 1 - DRAG;
                    if ( this.onGround && ( Math.abs( this.getMotionX() ) > 0.00001 || Math.abs( this.getMotionZ() ) > 0.00001 ) ) {
                        friction = this.world.getBlockAt( (int) this.getPositionX(),
                            (int) ( this.getPositionY() - 1 ),
                            (int) this.getPositionZ() ).getFrictionFactor() * 0.91f;
                    }

                    // Calculate new motion
                    float newMovX = this.transform.getMotionX() * friction;
                    float newMovY = this.transform.getMotionY() * ( 1 - DRAG );
                    float newMovZ = this.transform.getMotionZ() * friction;

                    this.transform.setMotion( newMovX, newMovY, newMovZ );
                    this.checkAfterGravity();
                }

                if ( moved != null ) {
                    // We did not move so we collided, set motion to 0 to escape hell
                    if ( movX != moved.getX() ) {
                        this.transform.setMotionX( 0 );
                    }

                    if ( movY != moved.getY() ) {
                        this.transform.setMotionY( 0 );
                    }

                    if ( movZ != moved.getZ() ) {
                        this.transform.setMotionZ( 0 );
                    }
                }
            }

            // Check for block collision
            this.checkBlockCollisions();

            // Check for void damage
            if ( this.getPositionY() < -16.0f ) {
                this.dealVoidDamage();
            }

            this.lastUpdateDT = 0;
        }

        // Check if we need to update the bounding box
        if ( this.transform.isDirty() ) {
            this.boundingBox.setBounds(
                this.getPositionX() - ( this.width / 2 ),
                this.getPositionY(),
                this.getPositionZ() - ( this.width / 2 ),
                this.getPositionX() + ( this.width / 2 ),
                this.getPositionY() + this.height,
                this.getPositionZ() + ( this.width / 2 )
            );

            this.transform.move( 0, 0, 0 );
        }
    }

    protected void checkBlockCollisions() {
        List<io.gomint.world.block.Block> blockList = this.world.getCollisionBlocks( this, true );
        if ( blockList != null ) {
            Vector pushedByBlocks = new Vector( 0, 0, 0 );

            for ( io.gomint.world.block.Block block : blockList ) {
                io.gomint.server.world.block.Block implBlock = (io.gomint.server.world.block.Block) block;
                implBlock.onEntityCollision( this );
                pushedByBlocks = implBlock.addVelocity( this, pushedByBlocks );
            }

            if ( pushedByBlocks.length() > 0 ) {
                pushedByBlocks = pushedByBlocks.normalize().multiply( 0.014f );
                Vector newMotion = this.transform.getMotion().add( pushedByBlocks );
                this.transform.setMotion( newMotion.getX(), newMotion.getY(), newMotion.getZ() );
                this.broadCastMotion();
            }
        }
    }

    /**
     * Allow for custom capping of gravity
     */
    protected void checkAfterGravity() {

    }

    protected boolean shouldMove() {
        return true;
    }

    /**
     * Move by given motion. This check for block collisions and pushes entity out of blocks when needed
     *
     * @param movX x axis movement
     * @param movY y axis movement
     * @param movZ z axis movement
     * @return vector with the actual done movement
     */
    public Vector safeMove( float movX, float movY, float movZ ) {
        // Security check so we don't move and collect bounding boxes like crazy
        if ( Math.abs( movX ) > 20 || Math.abs( movZ ) > 20 || Math.abs( movY ) > 20 ) {
            return Vector.ZERO;
        }

        float dX = movX;
        float dY = movY;
        float dZ = movZ;

        AxisAlignedBB oldBoundingBox = this.boundingBox.clone();

        // Check if we collide with some blocks when we would move that fast
        List<AxisAlignedBB> collisionList = this.world.getCollisionCubes( this, this.boundingBox.getOffsetBoundingBox( dX, dY, dZ ), false );
        if ( collisionList != null && !this.stuckInBlock ) {
            // Check if we would hit a y border block
            for ( AxisAlignedBB axisAlignedBB : collisionList ) {
                dY = axisAlignedBB.calculateYOffset( this.boundingBox, dY );
                if ( dY != movY ) {
                    Block block = this.world.getBlockAt( (int) axisAlignedBB.getMinX(), (int) axisAlignedBB.getMinY(), (int) axisAlignedBB.getMinZ() );
                    this.collidedWith.add( block );
                }
            }

            this.boundingBox.offset( 0, dY, 0 );

            // Check if we would hit a x border block
            for ( AxisAlignedBB axisAlignedBB : collisionList ) {
                dX = axisAlignedBB.calculateXOffset( this.boundingBox, dX );
                if ( dX != movX ) {
                    Block block = this.world.getBlockAt( (int) axisAlignedBB.getMinX(), (int) axisAlignedBB.getMinY(), (int) axisAlignedBB.getMinZ() );
                    LOGGER.debug( "Entity {} collided with {}", this, block );

                    this.collidedWith.add( block );
                }
            }

            this.boundingBox.offset( dX, 0, 0 );

            // Check if we would hit a z border block
            for ( AxisAlignedBB axisAlignedBB : collisionList ) {
                dZ = axisAlignedBB.calculateZOffset( this.boundingBox, dZ );
                if ( dZ != movZ ) {
                    Block block = this.world.getBlockAt( (int) axisAlignedBB.getMinX(), (int) axisAlignedBB.getMinY(), (int) axisAlignedBB.getMinZ() );
                    LOGGER.debug( "Entity {} collided with {}", this, block );

                    this.collidedWith.add( block );
                }
            }

            this.boundingBox.offset( 0, 0, dZ );
        } else {
            this.boundingBox.offset( dX, dY, dZ );
        }

        // Check if we can jump
        boolean notFallingFlag = ( this.onGround || ( dY != movY && movY < 0 ) );
        if ( this.stepHeight > 0 && notFallingFlag && ( movX != dX || movZ != dZ ) ) {
            float oldDX = dX;
            float oldDY = dY;
            float oldDZ = dZ;

            dX = movX;
            dY = this.stepHeight;
            dZ = movZ;

            // Save and restore old bounding box
            AxisAlignedBB oldBoundingBox1 = this.boundingBox.clone();
            this.boundingBox.setBounds( oldBoundingBox );

            // Check for collision
            collisionList = this.world.getCollisionCubes( this, this.boundingBox.addCoordinates( dX, dY, dZ ), false );
            if ( collisionList != null ) {
                // Check if we would hit a y border block
                for ( AxisAlignedBB axisAlignedBB : collisionList ) {
                    dY = axisAlignedBB.calculateYOffset( this.boundingBox, dY );
                }

                this.boundingBox.offset( 0, dY, 0 );

                // Check if we would hit a x border block
                for ( AxisAlignedBB axisAlignedBB : collisionList ) {
                    dX = axisAlignedBB.calculateXOffset( this.boundingBox, dX );
                }

                this.boundingBox.offset( dX, 0, 0 );

                // Check if we would hit a z border block
                for ( AxisAlignedBB axisAlignedBB : collisionList ) {
                    dZ = axisAlignedBB.calculateZOffset( this.boundingBox, dZ );
                }

                this.boundingBox.offset( 0, 0, dZ );
            }

            // Check if we moved left or right
            if ( MathUtils.square( oldDX ) + MathUtils.square( oldDZ ) >= MathUtils.square( dX ) + MathUtils.square( dZ ) ) {
                // Revert this decision of moving the bounding box up
                dX = oldDX;
                dY = oldDY;
                dZ = oldDZ;
                this.boundingBox.setBounds( oldBoundingBox1 );
            }
        }

        if ( dX != 0 || dY != 0 || dZ != 0 ) {
            // Move by new bounding box
            this.transform.setPosition(
                ( this.boundingBox.getMinX() + this.boundingBox.getMaxX() ) / 2,
                this.boundingBox.getMinY(),
                ( this.boundingBox.getMinZ() + this.boundingBox.getMaxZ() ) / 2
            );
        }

        // Check for grounding states
        this.checkIfCollided( movX, movY, movZ, dX, dY, dZ );
        this.updateFallState( dY );

        // Check if we are stuck in a block
        if ( this.needsToBePushedOutOfBlocks() ) {
            this.checkInsideBlock();
        }

        return new Vector( dX, dY, dZ );
    }

    /**
     * Does this entity needs to be pushed out of blocks when stuck?
     *
     * @return true or false, depending of it needing to be pushed
     */
    protected boolean needsToBePushedOutOfBlocks() {
        return true;
    }

    private void updateFallState( float dY ) {
        // When we are onground again we need to deal damage
        if ( this.onGround ) {
            if ( this.fallDistance > 0 ) {
                this.fall();
            }

            this.fallDistance = 0;
        } else if ( dY < 0 ) {
            this.fallDistance -= dY;
        }
    }

    /**
     * Handle falling of entities
     */
    protected abstract void fall();

    /**
     * Check if this entity is collided
     *
     * @param movX the amount of x which the entity has moved
     * @param movY the amount of y which the entity has moved
     * @param movZ the amount of z which the entity has moved
     * @param dX   the amount of x which the entity should have moved
     * @param dY   the amount of y which the entity should have moved
     * @param dZ   the amount of z which the entity should have moved
     */
    protected void checkIfCollided( float movX, float movY, float movZ, float dX, float dY, float dZ ) {
        // Check if we collided with something
        this.isCollidedVertically = movY != dY;
        this.isCollidedHorizontally = ( movX != dX || movZ != dZ );
        this.isCollided = ( this.isCollidedHorizontally || this.isCollidedVertically );
        this.onGround = ( movY != dY && movY < 0 );
    }

    // ==================================== TRANSFORMATION ==================================== //

    private void checkInsideBlock() {
        // Check in which block we are
        int fullBlockX = MathUtils.fastFloor( this.transform.getPositionX() );
        int fullBlockY = MathUtils.fastFloor( this.transform.getPositionY() );
        int fullBlockZ = MathUtils.fastFloor( this.transform.getPositionZ() );

        // Are we stuck inside a block?
        Block block = this.world.getBlockAt( fullBlockX, fullBlockY, fullBlockZ );
        if ( block.isSolid() && block.intersectsWith( this.boundingBox ) ) {
            // We need to check for "smooth" movement when its a player (it climbs .5 steps in .3 -> .420 -> .468 .487 .495 .498 .499 steps
            if ( this instanceof EntityPlayer && ( this.stuckInBlockTicks++ <= 20 || ( (EntityPlayer) this ).getAdventureSettings().isNoClip() ) ) { // Yes we can "smooth" for up to 20 ticks, thanks mojang :D
                return;
            }

            LOGGER.debug( "Entity {}({}) [{}] @ {} is stuck in a block {} @ {} -> {}",
                this.getClass().getSimpleName(), this.getEntityId(), this.stuckInBlockTicks, this.getLocation(), block.getClass().getSimpleName(), block.getPosition(), block.getBoundingBox() );

            // Calc with how much force we can get out of here, this depends on how far we are in
            float diffX = this.transform.getPositionX() - fullBlockX;
            float diffY = this.transform.getPositionY() - fullBlockY;
            float diffZ = this.transform.getPositionZ() - fullBlockZ;

            // Random out the force
            double force = Math.random() * 0.2 + 0.1;

            // Check for free blocks
            boolean freeMinusX = !this.world.getBlockAt( fullBlockX - 1, fullBlockY, fullBlockZ ).isSolid();
            boolean freePlusX = !this.world.getBlockAt( fullBlockX + 1, fullBlockY, fullBlockZ ).isSolid();
            boolean freeMinusY = !this.world.getBlockAt( fullBlockX, fullBlockY - 1, fullBlockZ ).isSolid();
            boolean freePlusY = !this.world.getBlockAt( fullBlockX, fullBlockY + 1, fullBlockZ ).isSolid();
            boolean freeMinusZ = !this.world.getBlockAt( fullBlockX, fullBlockY, fullBlockZ - 1 ).isSolid();
            boolean freePlusZ = !this.world.getBlockAt( fullBlockX, fullBlockY, fullBlockZ + 1 ).isSolid();

            // Since we want the lowest amount of push we have to select the smallest side
            byte direction = -1;
            float lowest = 9999;

            // The -X side is free, use it for now
            if ( freeMinusX ) {
                direction = 0;
                lowest = diffX;
            }

            // Choose +X side only when free and we need to move less
            if ( freePlusX && 1 - diffX < lowest ) {
                direction = 1;
                lowest = 1 - diffX;
            }

            // Choose -Y side only when free and we need to move less
            if ( freeMinusY && diffY < lowest ) {
                direction = 2;
                lowest = diffY;
            }

            // Choose +Y side only when free and we need to move less
            if ( freePlusY && 1 - diffY < lowest ) {
                direction = 3;
                lowest = 1 - diffY;
            }

            // Choose -Z side only when free and we need to move less
            if ( freeMinusZ && diffZ < lowest ) {
                direction = 4;
                lowest = diffZ;
            }

            // Choose +Z side only when free and we need to move less
            if ( freePlusZ && 1 - diffZ < lowest ) {
                direction = 5;
            }

            // Push to the side we selected
            switch ( direction ) {
                case 0:
                    this.transform.setMotion( (float) -force, 0, 0 );
                    break;
                case 1:
                    this.transform.setMotion( (float) force, 0, 0 );
                    break;
                case 2:
                    this.transform.setMotion( 0, (float) -force, 0 );
                    break;
                case 3:
                    this.transform.setMotion( 0, (float) force, 0 );
                    break;
                case 4:
                    this.transform.setMotion( 0, 0, (float) -force );
                    break;
                case 5:
                    this.transform.setMotion( 0, 0, (float) force );
                    break;
            }

            this.stuckInBlock = true;
            this.broadCastMotion();
        } else {
            this.stuckInBlock = false;
            this.stuckInBlockTicks = 0;
        }
    }

    /**
     * Gets the entity's transform as a Transformable.
     *
     * @return The entity's transform
     */
    public Transformable getTransform() {
        return this.transform;
    }

    @Override
    public Location getLocation() {
        return this.transform.toLocation( this.world );
    }

    /**
     * Set the given velocity
     *
     * @param velocity which should be applied to the entity
     * @param send     true when the entity should get the velocity
     */
    public void setVelocity( Vector velocity, boolean send ) {
        EntityVelocityEvent event = new EntityVelocityEvent( this, velocity );
        this.world.getServer().getPluginManager().callEvent( event );
        if ( event.isCancelled() ) {
            return;
        }

        LOGGER.debug( "New motion for {}: {}", this, event.getVelocity() );
        this.transform.setMotion( event.getVelocity().getX(), event.getVelocity().getY(), event.getVelocity().getZ() );
        this.fallDistance = 0;

        if ( send ) {
            this.broadCastMotion();
        }
    }

    /**
     * Broadcast the current motion to all surrounding (visible) entities
     */
    void broadCastMotion() {
        PacketEntityMotion motion = new PacketEntityMotion();
        motion.setEntityId( this.getEntityId() );
        motion.setVelocity( this.transform.getMotion() );

        this.world.sendToVisible( this.transform.getPosition().toBlockPosition(), motion,
            entity -> entity instanceof EntityPlayer && ( (EntityPlayer) entity ).getEntityVisibilityManager().isVisible( Entity.this ) );
    }

    /**
     * Gets the motion of the entity on the x axis.
     *
     * @return The motion of the entity on the x axis
     */
    public float getMotionX() {
        return this.transform.getMotionX();
    }

    /**
     * Gets the motion of the entity on the y axis.
     *
     * @return The motion of the entity on the y axis
     */
    public float getMotionY() {
        return this.transform.getMotionY();
    }

    /**
     * Gets the motion of the entity on the z axis.
     *
     * @return The motion of the entity on the z axis
     */
    public float getMotionZ() {
        return this.transform.getMotionZ();
    }

    /**
     * Gets the position of the entity on the x axis.
     *
     * @return The position of the entity on the x axis
     */
    public float getPositionX() {
        return this.transform.getPositionX();
    }

    /**
     * Gets the position of the entity on the y axis.
     *
     * @return The position of the entity on the y axis
     */
    public float getPositionY() {
        return this.transform.getPositionY();
    }

    /**
     * Gets the position of the entity on the z axis.
     *
     * @return The position of the entity on the z axis
     */
    public float getPositionZ() {
        return this.transform.getPositionZ();
    }

    /**
     * Gets the position of the entity as a vector.
     *
     * @return The position of the entity as a vector
     */
    public Vector getPosition() {
        return this.transform.getPosition();
    }

    /**
     * Sets the entity's position given a vector.
     *
     * @param position The position to set
     */
    public void setPosition( Vector position ) {
        this.transform.setPosition( position );
        this.recalcBoundingBox();
    }

    /**
     * Gets the yaw angle of the entity's body.
     *
     * @return The yaw angle of the entity's body
     */
    public float getYaw() {
        return this.transform.getYaw();
    }

    /**
     * Sets the yaw angle of the entity's body.
     *
     * @param yaw The yaw angle to set
     */
    public void setYaw( float yaw ) {
        this.transform.setYaw( yaw );
    }

    /**
     * Gets the yaw angle of the entity's head.
     *
     * @return The yaw angle of the entity's head
     */
    public float getHeadYaw() {
        return this.transform.getHeadYaw();
    }

    /**
     * Sets the yaw angle of the entity's head.
     *
     * @param headYaw The yaw angle to set
     */
    public void setHeadYaw( float headYaw ) {
        this.transform.setHeadYaw( headYaw );
    }

    /**
     * Gets the pitch angle of the entity's head.
     *
     * @return The pitch angle of the entity's head
     */
    public float getPitch() {
        return this.transform.getPitch();
    }

    /**
     * Sets the pitch angle of the entity's head.
     *
     * @param pitch The pitch angle to set.
     */
    public void setPitch( float pitch ) {
        this.transform.setPitch( pitch );
    }

    @Override
    public Vector getDirection() {
        return this.transform.getDirection();
    }

    @Override
    public Vector2 getDirectionPlane() {
        return ( new Vector2( (float) -Math.cos( Math.toRadians( this.transform.getYaw() ) - ( Math.PI / 2 ) ),
            (float) -Math.sin( Math.toRadians( this.transform.getYaw() ) - ( Math.PI / 2 ) ) ) ).normalize();
    }

    /**
     * Gets the direction the entity's head is facing as a normalized vector.
     *
     * @return The direction vector the entity's head is facing
     */
    public Vector getHeadDirection() {
        return this.transform.getHeadDirection();
    }

    /**
     * Sets the entity's position given the respective coordinates on the 3 axes.
     *
     * @param positionX The x coordinate of the position
     * @param positionY The y coordinate of the position
     * @param positionZ The z coordinate of the position
     */
    public void setPosition( float positionX, float positionY, float positionZ ) {
        this.transform.setPosition( positionX, positionY, positionZ );
        this.recalcBoundingBox();
    }

    /**
     * Moves the entity by the given offset vector. Produces the same result as
     * <pre>
     * {@code
     * Entity.setPosition( Entity.getPosition().add( offsetX, offsetY, offsetZ ) );
     * }
     * </pre>
     *
     * @param offsetX The x component of the offset
     * @param offsetY The y component of the offset
     * @param offsetZ The z component of the offset
     */
    public void move( float offsetX, float offsetY, float offsetZ ) {
        this.transform.move( offsetX, offsetY, offsetZ );
    }

    /**
     * Moves the entity by the given offset vector. Produces the same result as
     * <pre>
     * {@code
     * Entity.setPosition( Entity.getPosition().add( offsetX, offsetY, offsetZ ) );
     * }
     * </pre>
     *
     * @param offset The offset vector to apply to the entity
     */
    public void move( Vector offset ) {
        this.transform.move( offset );
    }

    /**
     * Rotates the entity's body around the yaw axis (vertical axis).
     *
     * @param yaw The yaw value by which to rotate the entity
     */
    public void rotateYaw( float yaw ) {
        this.transform.rotateYaw( yaw );
    }

    /**
     * Rotates the entity's head around the yaw axis (vertical axis).
     *
     * @param headYaw The yaw value by which to rotate the entity's head
     */
    public void rotateHeadYaw( float headYaw ) {
        this.transform.rotateHeadYaw( headYaw );
    }

    /**
     * Rotates the entity's head around the pitch axis (transverse axis).
     *
     * @param pitch The pitch value by which to rotate the entity's head
     */
    public void rotatePitch( float pitch ) {
        this.transform.rotatePitch( pitch );
    }

    @Override
    public Chunk getChunk() {
        int chunkX = CoordinateUtils.fromBlockToChunk( (int) this.getPositionX() );
        int chunkZ = CoordinateUtils.fromBlockToChunk( (int) this.getPositionZ() );

        return this.world.loadChunk( chunkX, chunkZ, true );
    }

    /**
     * Get the bounding box of the entity
     *
     * @return the current bounding box of this entity
     */
    public AxisAlignedBB getBoundingBox() {
        return this.boundingBox;
    }

    /**
     * Change the size of the entity
     *
     * @param width  the new width of the entity
     * @param height the new height of the entity
     */
    protected void setSize( float width, float height ) {
        this.width = width;
        this.height = height;
        this.eyeHeight = (float) ( height / 2 + 0.1 );

        this.metadataContainer.putFloat( MetadataContainer.DATA_BOUNDINGBOX_WIDTH, width );
        this.metadataContainer.putFloat( MetadataContainer.DATA_BOUNDINGBOX_HEIGHT, height );

        this.recalcBoundingBox();
    }

    /**
     * Does this entity has a active collision box?
     *
     * @param value true when collision is active, false when not
     */
    public void setHasCollision( boolean value ) {
        this.metadataContainer.setDataFlag( MetadataContainer.DATA_INDEX, EntityFlag.HAS_COLLISION, value );
    }

    /**
     * Does this entity has a active collision box?
     *
     * @return true when collisions are active, false when not
     */
    public boolean hasCollision() {
        return this.metadataContainer.getDataFlag( MetadataContainer.DATA_INDEX, EntityFlag.HAS_COLLISION );
    }

    @Override
    public boolean isImmobile() {
        return this.metadataContainer.getDataFlag( MetadataContainer.DATA_INDEX, EntityFlag.IMMOBILE );
    }

    @Override
    public void setImmobile( boolean value ) {
        this.metadataContainer.setDataFlag( MetadataContainer.DATA_INDEX, EntityFlag.IMMOBILE, value );
    }

    public boolean isAffectedByGravity() {
        return this.metadataContainer.getDataFlag( MetadataContainer.DATA_INDEX, EntityFlag.AFFECTED_BY_GRAVITY );
    }

    public void setAffectedByGravity( boolean value ) {
        this.metadataContainer.setDataFlag( MetadataContainer.DATA_INDEX, EntityFlag.AFFECTED_BY_GRAVITY, value );
    }

    @Override
    public String getNameTag() {
        return this.metadataContainer.getString( MetadataContainer.DATA_NAMETAG );
    }

    @Override
    public void setNameTag( String nameTag ) {
        this.metadataContainer.putString( MetadataContainer.DATA_NAMETAG, nameTag );
    }

    @Override
    public boolean isNameTagAlwaysVisible() {
        return this.metadataContainer.getDataFlag( MetadataContainer.DATA_INDEX, EntityFlag.ALWAYS_SHOW_NAMETAG );
    }

    @Override
    public void setNameTagAlwaysVisible( boolean value ) {
        this.metadataContainer.setDataFlag( MetadataContainer.DATA_INDEX, EntityFlag.ALWAYS_SHOW_NAMETAG, value );
    }

    @Override
    public boolean isNameTagVisible() {
        return this.metadataContainer.getDataFlag( MetadataContainer.DATA_INDEX, EntityFlag.CAN_SHOW_NAMETAG );
    }

    @Override
    public void setNameTagVisible( boolean value ) {
        this.metadataContainer.setDataFlag( MetadataContainer.DATA_INDEX, EntityFlag.CAN_SHOW_NAMETAG, value );
    }

    @Override
    public boolean isOnFire() {
        return this.metadataContainer.getDataFlag( MetadataContainer.DATA_INDEX, EntityFlag.ONFIRE );
    }

    @Override
    public void setOnFire( boolean value ) {
        this.metadataContainer.setDataFlag( MetadataContainer.DATA_INDEX, EntityFlag.ONFIRE, value );
    }

    @Override
    public boolean isInvisible() {
        return this.metadataContainer.getDataFlag( MetadataContainer.DATA_INDEX, EntityFlag.INVISIBLE );
    }

    @Override
    public void setInvisible( boolean value ) {
        this.metadataContainer.setDataFlag( MetadataContainer.DATA_INDEX, EntityFlag.INVISIBLE, value );
    }

    /**
     * Create the packet for display in the client
     *
     * @param receiver which should get this entity
     * @return packet for spawning this entity
     */
    public Packet createSpawnPacket( EntityPlayer receiver ) {
        PacketSpawnEntity spawnEntity = new PacketSpawnEntity();
        spawnEntity.setEntityId( this.getEntityId() );
        spawnEntity.setMetadata( this.metadataContainer );
        spawnEntity.setX( this.transform.getPositionX() );
        spawnEntity.setY( this.transform.getPositionY() + this.eyeHeight );
        spawnEntity.setZ( this.transform.getPositionZ() );
        spawnEntity.setEntityType( this.getType() );
        spawnEntity.setVelocityX( this.transform.getMotionX() );
        spawnEntity.setVelocityY( this.transform.getMotionY() );
        spawnEntity.setVelocityZ( this.transform.getMotionZ() );
        spawnEntity.setYaw( this.transform.getYaw() );
        spawnEntity.setHeadYaw( this.transform.getHeadYaw() );
        spawnEntity.setPitch( this.transform.getPitch() );
        return spawnEntity;
    }

    public void sendData( EntityPlayer player ) {
        PacketEntityMetadata metadataPacket = new PacketEntityMetadata();
        metadataPacket.setEntityId( this.getEntityId() );
        metadataPacket.setMetadata( this.metadataContainer );
        metadataPacket.setTick( this.world.getServer().getCurrentTickTime() / 50 );
        player.getConnection().addToSendQueue( metadataPacket );
    }

    @Override
    public boolean isOnGround() {
        return this.onGround;
    }

    public void setCanClimb( boolean value ) {
        this.metadataContainer.setDataFlag( MetadataContainer.DATA_INDEX, EntityFlag.CAN_CLIMB, value );
    }

    public Vector getVelocity() {
        return this.transform.getMotion();
    }

    @Override
    public void setVelocity( Vector velocity ) {
        this.setVelocity( velocity, true );
    }

    protected boolean isOnLadder() {
        Location location = this.getLocation();
        Block block = location.getWorld().getBlockAt( location.toBlockPosition() );
        return block instanceof Ladder || block instanceof Vines;
    }

    public boolean isInsideLiquid() {
        Location eyeLocation = this.getLocation().add( 0, this.eyeHeight, 0 );
        Block block = eyeLocation.getWorld().getBlockAt( eyeLocation.toBlockPosition() );
        if ( block instanceof StationaryWater || block instanceof FlowingWater ) {
            float yLiquid = (float) ( block.getPosition().getY() + 1 + ( ( (Liquid) block ).getFillHeight() - 0.12 ) );
            return eyeLocation.getY() < yLiquid;
        }

        return false;
    }

    /**
     * Decides if an entity can be attacked with an item (normal player attack)
     *
     * @return true if this entity can be attacked with an item, false if not
     */
    boolean canBeAttackedWithAnItem() {
        return true;
    }

    /**
     * Decides if this entity can get attacked by the given entity.
     *
     * @param entity which wants to attack
     * @return true when it can't be attacked from the entity given, false if it can
     */
    boolean isInvulnerableFrom( Entity entity ) {
        return false;
    }

    /**
     * Deal damage to this entity
     *
     * @param damageEvent which holds all data needed for damaging this entity
     * @return true if the entity took damage, false if not
     */
    public boolean damage( EntityDamageEvent damageEvent ) {
        // Don't damage dead entities
        if ( this.dead ) {
            return false;
        }

        // First of all we call the event
        this.world.getServer().getPluginManager().callEvent( damageEvent );
        return !damageEvent.isCancelled();
    }

    public void interact( io.gomint.entity.EntityPlayer player, Vector clickVector ) {

    }

    public void attach( EntityPlayer player ) {

    }

    public void detach( EntityPlayer player ) {

    }

    private void recalcBoundingBox() {
        // Only recalc on spawned entities
        if ( this.world == null ) {
            return;
        }

        // Update bounding box
        Location location = this.getLocation();
        getBoundingBox().setBounds(
            location.getX() - ( this.getWidth() / 2 ),
            location.getY(),
            location.getZ() - ( this.getWidth() / 2 ),
            location.getX() + ( this.getWidth() / 2 ),
            location.getY() + this.getHeight(),
            location.getZ() + ( this.getWidth() / 2 )
        );
    }

    public void setAndRecalcPosition( Location to ) {
        setPosition( to );
        setPitch( to.getPitch() );
        setYaw( to.getYaw() );
        setHeadYaw( to.getHeadYaw() );
        this.recalcBoundingBox();
    }

    void dealVoidDamage() {
        despawn();
    }

    public void onCollideWithPlayer( EntityPlayer player ) {

    }

    public void resetFallDistance() {
        this.fallDistance = 0;
    }

    public void multiplyFallDistance( float v ) {
        this.fallDistance *= v;
    }

    @Override
    public void spawn( Location location ) {
        // Check if already spawned
        if ( this.world != null ) {
            throw new IllegalStateException( "Entity already spawned" );
        }

        this.world = (WorldAdapter) location.getWorld();
        this.world.spawnEntityAt( this, location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch() );
        this.setupAI();
    }

    protected void setupAI() {

    }

    public void teleport( Location to, EntityTeleportEvent.Cause cause ) {
        EntityTeleportEvent entityTeleportEvent = new EntityTeleportEvent( this, this.getLocation(), to, cause );
        this.world.getServer().getPluginManager().callEvent( entityTeleportEvent );
        if ( entityTeleportEvent.isCancelled() ) {
            return;
        }

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
    public void teleport( Location to ) {
        this.teleport( to, EntityTeleportEvent.Cause.CUSTOM );
    }

    @Override
    public void setAge( long duration, TimeUnit unit ) {
        this.age = MathUtils.fastFloor( unit.toMillis( duration ) / 50f );
    }

    @Override
    public BossBar getBossBar() {
        return this.bossBar != null ? this.bossBar : ( this.bossBar = new io.gomint.server.entity.BossBar( this ) );
    }

    /**
     * Pre spawn packets when needed
     *
     * @param connection which is used to spawn this entity to
     */
    public void preSpawn( PlayerConnection connection ) {

    }

    /**
     * Post spawn packets when needed
     *
     * @param connection which is used to spawn this entity to
     */
    public void postSpawn( PlayerConnection connection ) {

    }

    @Override
    public float getScale() {
        return this.metadataContainer.getFloat( MetadataContainer.DATA_SCALE );
    }

    @Override
    public void setScale( float scale ) {
        this.metadataContainer.putFloat( MetadataContainer.DATA_SCALE, scale );
    }

    @Override
    public void setHiddenByDefault( boolean value ) {
        if ( this instanceof EntityPlayer ) {
            return;
        }

        if ( !this.hideByDefault && value && this.world != null ) {
            // Despawn
            for ( io.gomint.entity.EntityPlayer entityPlayer : this.world.getPlayers() ) {
                EntityPlayer entityPlayer1 = (EntityPlayer) entityPlayer;
                entityPlayer1.getEntityVisibilityManager().removeEntity( this );
            }
        }

        this.shownFor = new HashSet<>();
        this.hideByDefault = value;
    }

    @Override
    public void showFor( io.gomint.entity.EntityPlayer player ) {
        ( (EntityPlayer) player ).getEntityVisibilityManager().addEntity( this );

        if ( this.shownFor != null ) {
            this.shownFor.add( (EntityPlayer) player );
        }
    }

    @Override
    public void hideFor( io.gomint.entity.EntityPlayer player ) {
        ( (EntityPlayer) player ).getEntityVisibilityManager().removeEntity( this );

        if ( this.shownFor != null ) {
            this.shownFor.remove( player );
        }
    }

    /**
     * Check if a player can see this entity
     *
     * @param player for which we check
     * @return true when visible, false when not
     */
    public boolean canSee( EntityPlayer player ) {
        return !this.hideByDefault || player.getEntityVisibilityManager().isVisible( this );
    }

    /**
     * Check if a player should be able to see this entity
     *
     * @param player for which we check
     * @return true when it should be visible, false when not
     */
    public boolean shouldBeSeen( EntityPlayer player ) {
        return !this.hideByDefault || this.shownFor.contains( player );
    }

    /**
     * Take over configuration from the given compound
     *
     * @param compound which holds entity data
     */
    public void initFromNBT( NBTTagCompound compound ) {
        // Read position
        List<Object> pos = compound.getList( "Pos", false );
        if ( pos != null ) {
            // Data in the array are three floats (x, y, z)
            float x = MathUtils.ensureFloat( pos.get( 0 ) );
            float y = MathUtils.ensureFloat( pos.get( 1 ) );
            float z = MathUtils.ensureFloat( pos.get( 2 ) );

            this.setPosition( x, y, z );
        }

        // Now we read motion
        List<Object> motion = compound.getList( "Motion", false );
        if ( motion != null ) {
            // Data in the array are three floats (x, y, z)
            float x = MathUtils.ensureFloat( motion.get( 0 ) );
            float y = MathUtils.ensureFloat( motion.get( 1 ) );
            float z = MathUtils.ensureFloat( motion.get( 2 ) );

            this.transform.setMotion( x, y, z );
        }

        // Read rotation
        List<Object> rotation = compound.getList( "Rotation", false );
        if ( rotation != null ) {
            float yaw = (float) rotation.get( 0 );
            float pitch = (float) rotation.get( 1 );

            this.setYaw( yaw );
            this.setPitch( pitch );
        }

        // Fall distance
        this.fallDistance = compound.getFloat( "FallDistance", 0f );
        this.setAffectedByGravity( compound.getByte( "NoGravity", (byte) 0 ) == 0 );
        this.onGround = compound.getByte( "OnGround", (byte) 1 ) == 1;

        // Age is different for baby entities (those have negative age stored as integer)
        boolean isBaby = compound.getByte( "IsBaby", (byte) 0 ) == 1;
        if ( isBaby ) {
            this.age = compound.getInteger( "Age", -1500 );
        } else {
            this.age = compound.getShort( "Age", (short) 0 );
        }
    }

    public NBTTagCompound persistToNBT() {
        NBTTagCompound compound = new NBTTagCompound( "" );

        // Store ID
        compound.addValue( "identifier", this.type.getPersistantId() );

        // Store position
        List<Float> pos = new ArrayList<>();
        pos.add( this.getPositionX() );
        pos.add( this.getPositionY() );
        pos.add( this.getPositionZ() );
        compound.addValue( "Pos", pos );

        // Store motion
        List<Float> motion = new ArrayList<>();
        motion.add( this.getMotionX() );
        motion.add( this.getMotionY() );
        motion.add( this.getMotionZ() );
        compound.addValue( "Motion", motion );

        // Store rotation
        List<Float> rotation = new ArrayList<>();
        rotation.add( this.getYaw() );
        rotation.add( this.getPitch() );
        compound.addValue( "Rotation", rotation );

        // Fall distance
        compound.addValue( "FallDistance", this.fallDistance );
        compound.addValue( "NoGravity", (byte) ( this.isAffectedByGravity() ? 0 : 1 ) );
        compound.addValue( "OnGround", (byte) ( this.isOnGround() ? 1 : 0 ) );

        // Age
        if ( this.age < 0 ) {
            compound.addValue( "Age", this.age );
            compound.addValue( "IsBaby", (byte) 1 );
        } else {
            compound.addValue( "Age", (short) this.age );
            compound.addValue( "IsBaby", (byte) 0 );
        }

        return compound;
    }

    public boolean isMotionSendingEnabled() {
        return false;
    }

    public boolean needsFullMovement() {
        if ( this.nextFullMovement-- == 0 || this.oldPosition == null ||
            this.oldPosition.subtract( this.getPosition() ).length() > 20 ) {
            this.nextFullMovement = 20;
            return true;
        }

        return false;
    }

    public void updateOldPosition() {
        this.oldPosition = this.getLocation();
    }

    public Packet getMovementPacket() {
        PacketEntityMovement packetEntityMovement = new PacketEntityMovement();
        packetEntityMovement.setEntityId( this.getEntityId() );

        packetEntityMovement.setX( this.getPositionX() );
        packetEntityMovement.setY( this.getPositionY() + this.getOffsetY() );
        packetEntityMovement.setZ( this.getPositionZ() );

        packetEntityMovement.setYaw( this.getYaw() );
        packetEntityMovement.setHeadYaw( this.getHeadYaw() );
        packetEntityMovement.setPitch( this.getPitch() );

        packetEntityMovement.setOnGround( this.isOnGround() );

        return packetEntityMovement;
    }

}
