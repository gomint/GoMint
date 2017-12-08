/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.entity;

/**
 * An enumeration of all entity types known. Those values are needed for the AddEntityPackets
 *
 * @author BlackyPaw
 * @version 1.0
 */
public enum EntityType {

    /**
     * Entity type value for a xp orb.
     */
    XP_ORB( 69 ),

    /**
     * Entity type value for a exp potion projectile.
     */
    EXP_BOTTLE_PROJECTILE( 68 ),

    /**
     * Entity type value for a primed tnt.
     */
    PRIMED_TNT( 65 ),

    /**
     * Entity Type value for a item drop.
     */
    ITEM_DROP( 64 ),

    /**
     * Entity Type value for a chicken.
     */
    CHICKEN( 10 ),

    /**
     * Entity Type value for a cow.
     */
    COW( 11 ),

    /**
     * Entity Type value for a pig.
     */
    PIG( 12 ),

    /**
     * Entity Type value for a sheep.
     */
    SHEEP( 13 ),

    /**
     * Entity Type value for a wolf.
     */
    WOLF( 14 ),

    /**
     * Entity Type value for a villager.
     */
    VILLAGER( 15 ),

    /**
     * Entity Type value for a mushroom cow.
     */
    MUSHROOM_COW( 16 ),

    /**
     * Entity Type value for a squid.
     */
    SQUID( 17 ),

    /**
     * Entity Type value for a rabbit.
     */
    RABBIT( 18 ),

    /**
     * Entity Type value for a bat.
     */
    BAT( 19 ),

    /**
     * Entity Type value for an iron golem.
     */
    IRON_GOLEM( 20 ),

    /**
     * Entity Type value for a snow golem.
     */
    SNOW_GOLEM( 21 ),

    /**
     * Entity Type value for a zombie.
     */
    ZOMBIE( 0x30B20 ),

    /**
     * Entity Type value for a creeper.
     */
    CREEPER( 0x000B21 ),

    /**
     * Entity Type value for a skeleton.
     */
    SKELETON( 0x010B22 ),

    /**
     * Entity Type value for a spider.
     */
    SPIDER( 0x040B23 ),

    /**
     * Entity Type value for a pig zombie.
     */
    PIG_ZOMBIE( 0x010B24 ),

    /**
     * Entity Type value for a slime.
     */
    SLIME( 0x000B25 ),

    /**
     * Entity Type value for an enderman.
     */
    ENDERMAN( 0x000B26 ),

    /**
     * Entity Type value for a silverfish.
     */
    SILVERFISH( 0x040B27 ),

    /**
     * Entity Type value for a cavespider.
     */
    CAVESPIDER( 0x040B28 ),

    /**
     * Entity Type value for a ghast.
     */
    GHAST( 0x000B29 ),

    /**
     * Entity Type value for a lava slime.
     */
    LAVA_SLIME( 0x000B2A ),

    /**
     * Entity Type value for a blaze.
     */
    BLAZE( 0x000B2B ),

    /**
     * Entity Type value for a zombified villager.
     */
    ZOMBIE_VILLAGER( 0x30B2C ),

    /**
     * Entity Type value for a witch.
     */
    WITCH( 0x000B2D ),

    /**
     * Entity Type value for a player.
     */
    PLAYER( 0x00013F ),

    /**
     * Entity Type value for a fishing hook floating on water.
     */
    FISHING_HOOK( 77 ),

    /**
     * Entity Type value for an arrow.
     */
    ARROW( 80 ),

    /**
     * Entity Type value for a snowball which has been thrown.
     */
    SNOWBALL( 0x000051 ),

    /**
     * Entity Type value for an egg which has been thrown.
     */
    THROWN_EGG( 0x000052 ),

    /**
     * Entity Type value for a painting hanging on a wall.
     */
    PAINTING( 0x000053 ),

    /**
     * Entity Type value for a rideable minecart.
     */
    MINECART_RIDEABLE( 0x080054 ),

    /**
     * Entity Type value for a large fireball as thrown by ghasts.
     */
    LARGE_FIREBALL( 0x000055 ),

    /**
     * Entity Type value for a potion which has been thrown.
     */
    THROWN_POTION( 0x000056 ),

    /**
     * Entity Type value for a rideable boat.
     */
    BOAT_RIDEABLE( 0x00005A ),

    /**
     * Entity Type value for a lightning strike.
     */
    LIGHTNING( 0x00005D ),

    /**
     * Entity Type value for a small fireball as thrown by blazes.
     */
    SMALL_FIREBALL( 0x00005E );

    private final int id;

    /**
     * New enum value for EntityType
     *
     * @param id The MC:PE id of this EntityType
     */
    EntityType( int id ) {
        this.id = id;
    }

    /**
     * Gets an entity type given its ID. If found the correct entity type is returned,
     * otherwise null is returned.
     *
     * @param id The ID of the entity type to get
     * @return The entity type that corresponds to the given ID or null if not found
     */
    public static EntityType getByID( int id ) {
        switch ( id ) {
            case 64:
                return ITEM_DROP;
            case 0x0A:
                return CHICKEN;
            case 0x0B:
                return COW;
            case 0x0C:
                return PIG;
            case 0x0D:
                return SHEEP;
            case 0x0E:
                return WOLF;
            case 0x0F:
                return VILLAGER;
            case 0x10:
                return MUSHROOM_COW;
            case 0x11:
                return SQUID;
            case 0x12:
                return RABBIT;
            case 0x13:
                return BAT;
            case 0x14:
                return IRON_GOLEM;
            case 0x15:
                return SNOW_GOLEM;
            case 0x20:
                return ZOMBIE;
            case 0x21:
                return CREEPER;
            case 0x22:
                return SKELETON;
            case 0x23:
                return SPIDER;
            case 0x24:
                return PIG_ZOMBIE;
            case 0x25:
                return SLIME;
            case 0x26:
                return ENDERMAN;
            case 0x27:
                return SILVERFISH;
            case 0x28:
                return CAVESPIDER;
            case 0x29:
                return GHAST;
            case 0x2A:
                return LAVA_SLIME;
            case 0x2B:
                return BLAZE;
            case 0x2C:
                return ZOMBIE_VILLAGER;
            case 0x2D:
                return WITCH;
            case 0x3F:
                return PLAYER;
            case 0x4D:
                return FISHING_HOOK;
            case 0x50:
                return ARROW;
            case 0x51:
                return SNOWBALL;
            case 0x52:
                return THROWN_EGG;
            case 0x53:
                return PAINTING;
            case 0x54:
                return MINECART_RIDEABLE;
            case 0x55:
                return LARGE_FIREBALL;
            case 0x56:
                return THROWN_POTION;
            case 0x5A:
                return BOAT_RIDEABLE;
            case 0x5D:
                return LIGHTNING;
            case 0x5E:
                return SMALL_FIREBALL;
            default:
                return null;
        }
    }

    /**
     * Gets the complete ID of this entity type.
     *
     * @return The complete ID of this entity type
     */
    public int getId() {
        return this.id;
    }

}
