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
    ZOMBIE( 32 ),

    /**
     * Entity Type value for a creeper.
     */
    CREEPER( 33 ),

    /**
     * Entity Type value for a skeleton.
     */
    SKELETON( 34 ),

    /**
     * Entity Type value for a spider.
     */
    SPIDER( 35 ),

    /**
     * Entity Type value for a pig zombie.
     */
    PIG_ZOMBIE( 36 ),

    /**
     * Entity Type value for a slime.
     */
    SLIME( 37 ),

    /**
     * Entity Type value for an enderman.
     */
    ENDERMAN( 38 ),

    /**
     * Entity Type value for a silverfish.
     */
    SILVERFISH( 39 ),

    /**
     * Entity Type value for a cavespider.
     */
    CAVESPIDER( 40 ),

    /**
     * Entity Type value for a ghast.
     */
    GHAST( 41 ),

    /**
     * Entity Type value for a lava slime.
     */
    LAVA_SLIME( 0x000B2A ),

    /**
     * Entity Type value for a blaze.
     */
    BLAZE( 43 ),

    /**
     * Entity Type value for a zombified villager.
     */
    ZOMBIE_VILLAGER( 44 ),

    /**
     * Entity Type value for a witch.
     */
    WITCH( 45 ),

    /**
     * Entity Type value for a player.
     */
    PLAYER( 63 ),

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
     * Entity type value for a thrown enderpearl
     */
    THROWN_ENDERPEARL( 87 ),

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
    SMALL_FIREBALL( 0x00005E ),

    /**
     * Entity Type value for a donkey.
     */
    DONKEY( 24 ),

    /**
     * Entity Type value for a horse.
     */
    HORSE( 23 ),

    /**
     * Entity Type value for a lama.
     */
    LAMA( 29 ),

    /**
     * Entity Type value for a mule.
     */
    MULE( 25 ),

    /**
     * Entity Type value for a ocelot.
     */
    OCELOT( 22 ),

    /**
     * Entity Type value for a polar bear.
     */
    POLAR_BEAR( 28 ),

    /**
     * Entity Type value for a skeleton horse.
     */
    SKELETON_HORSE( 26 ),

    /**
     * Entity Type value for a zombie horse.
     */
    ZOMBIE_HORSE( 27 ),

    /**
     * Entity Type value for a elder guardian.
     */
    ELDER_GUARDIAN( 50 ),

    /**
     * Entity Type value for a ender dragon.
     */
    ENDER_DRAGON( 53 ),

    /**
     * Entity Type value for a endermite.
     */
    ENDERMITE( 55 ),

    /**
     * Entity Type value for a guardian.
     */
    GUARDIAN( 49 ),

    /**
     * Entity Type value for a husk.
     */
    HUSK( 47 ),

    /**
     * Entity Type value for a magma cube.
     */
    MAGMA_CUBE( 42 ),

    /**
     * Entity Type value for a shulker.
     */
    SHULKER( 54 ),

    /**
     * Entity Type value for a stray.
     */
    STRAY( 46 ),

    /**
     * Entity Type value for a wither.
     */
    WITHER( 52 ),

    /**
     * Entity Type value for a wither skeleton.
     */
    WITHER_SKELETON( 48 ),

    /**
     * Entity Type value for a vex.
     */
    VEX( 105 ),

    /**
     * Entity Type value for a armor stand.
     */
    ARMOR_STAND( 61 ),

    /**
     * Entity type value for a falling block
     */
    FALLING_BLOCK( 66 );

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
     * Gets the complete ID of this entity type.
     *
     * @return The complete ID of this entity type
     */
    public int getId() {
        return this.id;
    }

}
