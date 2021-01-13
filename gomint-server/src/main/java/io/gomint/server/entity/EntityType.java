/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
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
     * Entity type value for a chicken.
     */
    CHICKEN( "minecraft:chicken", 10 ),

    /**
     * Entity type value for a cow.
     */
    COW( "minecraft:cow", 11 ),

    /**
     * Entity type value for a pig.
     */
    PIG( "minecraft:pig", 12 ),

    /**
     * Entity type value for a sheep.
     */
    SHEEP( "minecraft:sheep", 13 ),

    /**
     * Entity type value for a wolf.
     */
    WOLF( "minecraft:wolf", 14 ),

    /**
     * Entity type value for a villager.
     */
    VILLAGER( "minecraft:villager", 15 ),

    /**
     * Entity type value for a mushroom cow.
     */
    MUSHROOM_COW( "minecraft:mooshroom", 16 ),

    /**
     * Entity type value for a squid.
     */
    SQUID( "minecraft:squid", 17 ),

    /**
     * Entity type value for a rabbit.
     */
    RABBIT( "minecraft:rabbit", 18 ),

    /**
     * Entity type value for a bat.
     */
    BAT( "minecraft:bat", 19 ),

    /**
     * Entity type value for an iron golem.
     */
    IRON_GOLEM( "minecraft:iron_golem", 20 ),

    /**
     * Entity type value for a snow golem.
     */
    SNOW_GOLEM( "minecraft:snow_golem", 21 ),

    /**
     * Entity type value for a ocelot.
     */
    OCELOT( "minecraft:ocelot", 22 ),

    /**
     * Entity type value for a horse.
     */
    HORSE( "minecraft:horse", 23 ),

    /**
     * Entity type value for a donkey.
     */
    DONKEY( "minecraft:donkey", 24 ),

    /**
     * Entity type value for a mule.
     */
    MULE( "minecraft:mule", 25 ),

    /**
     * Entity type value for a skeleton horse.
     */
    SKELETON_HORSE( "minecraft:skeleton_horse", 26 ),

    /**
     * Entity type value for a zombie horse.
     */
    ZOMBIE_HORSE( "minecraft:zombie_horse", 27 ),

    /**
     * Entity type value for a polar bear.
     */
    POLAR_BEAR( "minecraft:polar_bear", 28 ),

    /**
     * Entity type value for a llama.
     */
    LLAMA( "minecraft:llama", 29 ),

    /**
     * Entity type value for a parrot
     */
    PARROT( "minecraft:parrot", 30 ),

    /**
     * Entity type value for a dolphin
     */
    DOLPHIN( "minecraft:dolphin", 31 ),

    /**
     * Entity type value for a zombie.
     */
    ZOMBIE( "minecraft:zombie", 32 ),

    /**
     * Entity type value for a creeper.
     */
    CREEPER( "minecraft:creeper", 33 ),

    /**
     * Entity type value for a skeleton.
     */
    SKELETON( "minecraft:skeleton", 34 ),

    /**
     * Entity type value for a spider.
     */
    SPIDER( "minecraft:spider", 35 ),

    /**
     * Entity type value for a zombified piglin.
     */
    ZOMBIE_PIGLIN("minecraft:zombie_pigman", 36),

    /**
     * Entity type value for a slime.
     */
    SLIME( "minecraft:slime", 37 ),

    /**
     * Entity type value for an enderman.
     */
    ENDERMAN( "minecraft:enderman", 38 ),

    /**
     * Entity type value for a silverfish.
     */
    SILVERFISH( "minecraft:silverfish", 39 ),

    /**
     * Entity type value for a cave spider.
     */
    CAVE_SPIDER( "minecraft:cave_spider", 40 ),

    /**
     * Entity type value for a ghast.
     */
    GHAST( "minecraft:ghast", 41 ),

    /**
     * Entity type value for a magma cube.
     */
    MAGMA_CUBE( "minecraft:magma_cube", 42 ),

    /**
     * Entity type value for a blaze.
     */
    BLAZE( "minecraft:blaze", 43 ),

    /**
     * Entity type value for a zombified villager.
     */
    ZOMBIE_VILLAGER( "minecraft:zombie_villager", 44 ),

    /**
     * Entity type value for a witch.
     */
    WITCH( "minecraft:witch", 45 ),

    /**
     * Entity type value for a stray.
     */
    STRAY( "minecraft:stray", 46 ),

    /**
     * Entity type value for a husk.
     */
    HUSK( "minecraft:husk", 47 ),

    /**
     * Entity type value for a wither skeleton.
     */
    WITHER_SKELETON( "minecraft:wither_skeleton", 48 ),

    /**
     * Entity type value for a guardian.
     */
    GUARDIAN( "minecraft:guardian", 49 ),

    /**
     * Entity type value for a elder guardian.
     */
    ELDER_GUARDIAN( "minecraft:elder_guardian", 50 ),

    /**
     * Entity type value for a wither.
     */
    WITHER( "minecraft:wither", 52 ),

    /**
     * Entity type value for a ender dragon.
     */
    ENDER_DRAGON( "minecraft:ender_dragon", 53 ),

    /**
     * Entity type value for a shulker.
     */
    SHULKER( "minecraft:shulker", 54 ),

    /**
     * Entity type value for a endermite.
     */
    ENDERMITE( "minecraft:endermite", 55 ),

    /**
     * Entity type value for a vindicator.
     */
    VINDICATOR( "minecraft:vindicator", 57 ),

    /**
     * Entity type value for a phantom.
     */
    PHANTOM( "minecraft:phantom", 58 ),

    /**
     * Entity type value for a Ravager.
     */
    RAVAGER("minecraft:ravager",59),

    /**
     * Entity type value for a armor stand.
     */
    ARMOR_STAND( "minecraft:armor_stand", 61 ),

    /**
     * Entity type value for a player.
     */
    PLAYER( "minecraft:player", 63 ),

    /**
     * Entity type value for a item drop.
     */
    ITEM_DROP( "minecraft:item", 64 ),

    /**
     * Entity type value for a primed tnt.
     */
    PRIMED_TNT( "minecraft:tnt", 65 ),

    /**
     * Entity type value for a falling block.
     */
    FALLING_BLOCK( "minecraft:falling_block", 66 ),

    /**
     * Entity type value for a exp potion projectile.
     */
    EXP_BOTTLE_PROJECTILE( "minecraft:xp_bottle", 68 ),

    /**
     * Entity type value for a xp orb.
     */
    XP_ORB( "minecraft:xp_orb", 69 ),

    /**
     * Entity type value for a eye of ender signal.
     */
    EYE_OF_ENDER( "minecraft:eye_of_ender_signal", 70 ),

    /**
     * Entity type value for a ender crystal.
     */
    ENDER_CRYSTAL( "minecraft:ender_crystal", 71 ),

    /**
     * Entity type value for a firework.
     */
    FIREWORK( "minecraft:fireworks_rocket", 72 ),

    /**
     * Entity type value for a thrown trident.
     */
    THROWN_TRIDENT( "minecraft:thrown_trident", 73 ),

    /**
     * Entity type value for a turtle.
     */
    TURTLE( "minecraft:turtle", 74 ),

    /**
     * Entity type value for a cat.
     */
    CAT("minecraft:cat", 75),

    /**
     * Entity type value for a shulker bullet.
     */
    SHULKER_BULLET( "minecraft:shulker_bullet", 76 ),

    /**
     * Entity type value for a fishing hook floating on water.
     */
    FISHING_HOOK( "minecraft:fishing_hook", 77 ),

    /**
     * Entity type value for a dragon fireball.
     */
    DRAGON_FIREBALL( "minecraft:dragon_fireball", 79 ),

    /**
     * Entity type value for an arrow.
     */
    ARROW( "minecraft:arrow", 80 ),

    /**
     * Entity type value for a snowball which has been thrown.
     */
    SNOWBALL( "minecraft:snowball", 81 ),

    /**
     * Entity type value for an egg which has been thrown.
     */
    THROWN_EGG( "minecraft:egg", 82 ),

    /**
     * Entity type value for a painting hanging on a wall.
     */
    PAINTING( "minecraft:painting", 83 ),

    /**
     * Entity type value for a rideable minecart.
     */
    MINECART( "minecraft:minecart", 84 ),

    /**
     * Entity type value for a large fireball as thrown by ghasts.
     */
    LARGE_FIREBALL( "minecraft:large_fireball", 85 ),

    /**
     * Entity type value for a potion which has been thrown.
     */
    THROWN_POTION( "minecraft:splash_potion", 86 ),

    /**
     * Entity type value for a thrown enderpearl
     */
    THROWN_ENDERPEARL( "minecraft:ender_pearl", 87 ),

    /**
     * Entity type value for a leash knot.
     */
    LEASH_KNOT( "minecraft:leash_knot", 88 ),

    /**
     * Entity type value for a wither skull.
     */
    WITHER_SKULL( "minecraft:wither_skull", 89 ),

    /**
     * Entity type value for a rideable boat.
     */
    BOAT( "minecraft:boat", 90 ),

    /**
     * Entity type value for a blue wither skull.
     */
    BLUE_WITHER_SKULL( "minecraft:wither_skull_dangerous", 91 ),

    /**
     * Entity type value for a lightning strike.
     */
    LIGHTNING( "minecraft:lightning_bolt", 93 ),

    /**
     * Entity type value for a small fireball as thrown by blazes.
     */
    SMALL_FIREBALL( "minecraft:small_fireball", 94 ),

    /**
     * Entity type value for a area effect cloud.
     */
    AREA_EFFECT_CLOUD( "minecraft:area_effect_cloud", 95 ),

    /**
     * Entity type value for a hopper minecraft.
     */
    HOPPER_MINECART( "minecraft:hopper_minecart", 96 ),

    /**
     * Entity type value for a tnt minecart.
     */
    TNT_MINECART( "minecraft:tnt_minecart", 97 ),

    /**
     * Entity type value for a chest minecart.
     */
    CHEST_MINECART( "minecraft:chest_minecart", 98 ),

    /**
     * Entity type value for a command block minecart.
     */
    COMMAND_BLOCK_MINECART( "minecraft:command_block_minecart", 100 ),

    /**
     * Entity type value for a lingering potion.
     */
    LINGERING_POTION( "minecraft:lingering_potion", 101 ),

    /**
     * Entity type value for a llama spit.
     */
    LLAMA_SPIT( "minecraft:llama_spit", 102 ),

    /**
     * Entity type value for a evoker fang.
     */
    EVOKATION_FANG( "minecraft:evocation_fang", 103 ),

    /**
     * Entity type value for a evoker.
     */
    EVOKER( "minecraft:evocation_illager", 104 ),

    /**
     * Entity type value for a vex.
     */
    VEX( "minecraft:vex", 105 ),

    /**
     * Entity type value for a pufferfish.
     */
    PUFFERFISH( "minecraft:pufferfish", 108 ),

    /**
     * Entity type value for a salmon.
     */
    SALMON( "minecraft:salmon", 109 ),

    /**
     * Entity type value for a drowned.
     */
    DROWNED( "minecraft:drowned", 110 ),

    /**
     * Entity type value for a tropcial fish.
     */
    TROPICALFISH( "minecraft:tropicalfish", 111 ),

    /**
     * Entity type value for a cod.
     */
    COD( "minecraft:cod", 112 ),

    /**
     * Entity type value for a panda.
     */
    PANDA( "minecraft:panda", 113 ),

    /**
     * Entity type value for a Pillager.
     */
    PILLAGER("minecarft:pillager",114),

    /**
     * Entity type value for a Fox.
     */
    FOX("minecraft:fox",121),

    /**
     * Entity type value for a Bee.
     */
    BEE("minecraft:bee",122),

    /**
     * Entity type value for a Piglin.
     */
    PIGLIN("minecraft:piglin", 123),

    /**
     * Entity type value for a Hoglin.
     */
    HOGLIN("minecraft:hoglin", 124),

    /**
     * Entity type value for a Strider.
     */
    STRIDER("minecraft:strider", 125),

    /**
     * Entity type value for a Zoglin.
     */
    ZOGLIN("minecraft:zoglin", 126),

    /**
     * Entity type value for a Zoglin.
     */
    PIGLIN_BRUTE("minecraft:piglin_brute", 127);


    private final String persistantId;
    private final int networkId;

    EntityType(String persistantId, int networkId) {
        this.persistantId = persistantId;
        this.networkId = networkId;
    }

    public String getPersistantId() {
        return persistantId;
    }

    public int getNetworkId() {
        return networkId;
    }

}
