/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world;

import io.gomint.event.entity.EntityDamageByEntityEvent;
import io.gomint.event.entity.EntityDamageEvent;
import io.gomint.event.entity.EntityExplodeEvent;
import io.gomint.inventory.item.ItemAir;
import io.gomint.inventory.item.ItemStack;
import io.gomint.math.*;
import io.gomint.math.Vector;
import io.gomint.server.entity.Entity;
import io.gomint.server.world.block.Air;
import io.gomint.world.Particle;
import io.gomint.world.block.Block;
import io.gomint.world.block.data.Facing;
import io.gomint.world.block.BlockTNT;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author geNAZt
 * @version 1.0
 */
public class Explosion {

    private static final float STEP_LENGTH = 0.3f;
    private final float size;
    private final Entity<?> source;

    // Temporary values
    private final Vector tempRay = new Vector( 0, 0, 0 );
    private final BlockPosition tempBlock = new BlockPosition( 0, 0, 0 );
    private final Set<Block> affectedBlocks = new HashSet<>();
    private Block currentBlock;

    public Explosion(float size, Entity<?> source) {
        this.size = size;
        this.source = source;
    }

    private void checkAffectedWithZGiven( int z ) {
        for ( int y = 0; y < 16; ++y ) {
            for ( int x = 0; x < 16; ++x ) {
                checkAffected( x, y, z );
            }
        }
    }

    private void checkAffectedWithXGiven( int x ) {
        for ( int y = 0; y < 16; ++y ) {
            for ( int z = 0; z < 16; ++z ) {
                checkAffected( x, y, z );
            }
        }
    }

    private void checkAffectedWithYGiven( int y ) {
        for ( int x = 0; x < 16; ++x ) {
            for ( int z = 0; z < 16; ++z ) {
                checkAffected( x, y, z );
            }
        }
    }

    private void checkAffected( int x, int y, int z ) {
        // Did we hit any border?
        if ( x == 0 || x == 15 ||
            y == 0 || y == 15 ||
            z == 0 || z == 15 ) {

            this.tempRay.x( x / (float) 15 * 2f - 1 );
            this.tempRay.y( y / (float) 15 * 2f - 1 );
            this.tempRay.z( z / (float) 15 * 2f - 1 );

            float distanceToBorder = this.tempRay.length();

            this.tempRay.x( ( this.tempRay.x() / distanceToBorder ) * STEP_LENGTH );
            this.tempRay.y( ( this.tempRay.y() / distanceToBorder ) * STEP_LENGTH );
            this.tempRay.z( ( this.tempRay.z() / distanceToBorder ) * STEP_LENGTH );

            float directionX = this.source.positionX();
            float directionY = this.source.positionY();
            float directionZ = this.source.positionZ();

            for (double blastForce = this.size * ( 0.7f + ThreadLocalRandom.current().nextFloat() * 0.6f ); blastForce > 0; blastForce -= 0.225f ) {
                int newX = MathUtils.fastFloor( directionX );
                int newY = MathUtils.fastFloor( directionY );
                int newZ = MathUtils.fastFloor( directionZ );

                if ( this.currentBlock == null ||
                    newX != this.tempBlock.x() ||
                    newY != this.tempBlock.y() ||
                    newZ != this.tempBlock.z() ) {

                    this.tempBlock.x( newX );
                    this.tempBlock.y( newY );
                    this.tempBlock.z( newZ );

                    // Break if collision checks go out of world borders
                    if ( this.tempBlock.y() < 0 || this.tempBlock.y() > 255 ) {
                        break;
                    }

                    this.currentBlock = this.source.world().blockAt( this.tempBlock );
                }

                if ( !( this.currentBlock instanceof Air ) ) {
                    blastForce -= ( ( ( (io.gomint.server.world.block.Block) this.currentBlock ).getBlastResistance() / 5 ) + 0.3f ) * STEP_LENGTH;
                    if ( blastForce > 0 ) {
                        this.affectedBlocks.add( this.currentBlock );
                    }
                }

                directionX += this.tempRay.x();
                directionY += this.tempRay.y();
                directionZ += this.tempRay.z();
            }
        }
    }

    /**
     * Calculate which blocks should be blown up by this explosion and after that deal damage to all entities around
     *
     * @param currentTimeMS The timestamp when the tick has begun
     * @param dT            The difference time in full seconds since the last tick
     */
    public void explode( long currentTimeMS, float dT ) {
        // Is explosion too small?
        if ( this.size < 0.1f ) {
            return;
        }

        // Check all directions for affected blocks
        checkAffectedWithYGiven( 0 );
        checkAffectedWithYGiven( 15 );

        checkAffectedWithXGiven( 0 );
        checkAffectedWithXGiven( 15 );

        checkAffectedWithZGiven( 0 );
        checkAffectedWithZGiven( 15 );

        // Call explode event
        EntityExplodeEvent event = new EntityExplodeEvent( this.source, this.affectedBlocks, ( 1f / this.size ) * 100f );
        this.source.world().getServer().pluginManager().callEvent( event );
        if ( event.cancelled() ) {
            return;
        }

        Location sourceLocation = this.source.location();
        float explosionDiameter = this.size * 2f;

        float minX = MathUtils.fastFloor( this.source.positionX() - explosionDiameter - 1 );
        float maxX = MathUtils.fastCeil( this.source.positionX() + explosionDiameter + 1 );
        float minY = MathUtils.fastFloor( this.source.positionY() - explosionDiameter - 1 );
        float maxY = MathUtils.fastCeil( this.source.positionY() + explosionDiameter + 1 );
        float minZ = MathUtils.fastFloor( this.source.positionZ() - explosionDiameter - 1 );
        float maxZ = MathUtils.fastCeil( this.source.positionZ() + explosionDiameter + 1 );

        AxisAlignedBB explosionBox = new AxisAlignedBB( minX, minY, minZ, maxX, maxY, maxZ );
        Collection<io.gomint.entity.Entity<?>> entities = this.source.world().getNearbyEntities( explosionBox, this.source );
        if ( entities != null ) {
            for ( io.gomint.entity.Entity<?> entity : entities ) {
                Location entityLocation = entity.location();
                float distance = ( entityLocation.distance( sourceLocation ) / explosionDiameter );
                if ( distance <= 1 ) {
                    Vector motion = entityLocation.subtract( sourceLocation ).normalize();
                    float impact = ( 1 - distance );
                    int damage = (int) ( ( ( impact * impact + impact ) / 2 ) * 8 * explosionDiameter + 1 );

                    EntityDamageByEntityEvent damageEvent = new EntityDamageByEntityEvent( entity, this.source, EntityDamageEvent.DamageSource.ENTITY_EXPLODE, damage );
                    ( (Entity<?>) entity ).damage( damageEvent );

                    entity.velocity( motion.multiply( impact ) );
                }
            }
        }

        Set<Block> alreadyUpdated = new HashSet<>();
        for ( Block block : event.affectedBlocks() ) {
            if ( block instanceof BlockTNT ) {
                ( (BlockTNT) block ).prime( 0.5f + ThreadLocalRandom.current().nextFloat() );
            } else if ( ThreadLocalRandom.current().nextFloat() * 100 < event.randomDropChance() ) {
                for ( ItemStack<?> drop : block.drops( ItemAir.create( 0 ) ) ) {
                    this.source.world().dropItem( new Vector(block.position()).add( 0.5f, 0.5f, 0.5f ), drop );
                }
            }

            block.blockType( Air.class );

            for ( Facing blockFace : Facing.values() ) {
                Block attached = block.side( blockFace );
                if ( !event.affectedBlocks().contains( attached ) && !alreadyUpdated.contains( attached ) ) {
                    io.gomint.server.world.block.Block implBlock = (io.gomint.server.world.block.Block) attached;
                    implBlock.update( UpdateReason.EXPLOSION, currentTimeMS, dT );
                    alreadyUpdated.add( attached );
                }
            }
        }

        // TODO: PacketExplode has been removed

        this.source.world().sendParticle( sourceLocation, Particle.HUGE_EXPLODE_SEED );
        this.source.world().sendLevelEvent( sourceLocation, LevelEvent.CAULDRON_EXPLODE, 0 );
    }

}
