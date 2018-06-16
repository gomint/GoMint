/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.entity;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;

/**
 * @author geNAZt
 * @version 1.0
 */
public class EntityMagicNumbers {

    private static final Object2IntMap<String> NEW_ID_MAPPING = new Object2IntOpenHashMap<>();
    private static final Int2ObjectMap<String> OLD_ID_MAPPING = new Int2ObjectOpenHashMap<>();

    static {
        // CHECKSTYLE:OFF
        register( 64, "item" );
        register( 69, "xp_orb" );

        register( 65, "tnt" );
        register( 66, "falling_block" );

        register( 61, "armor_stand" );
        register( 68, "xp_bottle" );
        register( 70, "eye_of_ender_signal" );
        register( 71, "ender_crystal" );
        register( 72, "fireworks_rocket" );
        register( 76, "shulker_bullet" );
        register( 77, "fishing_hook" );
        register( 79, "dragon_fireball" );
        register( 80, "arrow" );
        register( 81, "snowball" );
        register( 82, "egg" );
        register( 83, "painting" );
        register( 84, "minecart" );
        register( 85, "large_fireball" );
        register( 86, "splash_potion" );
        register( 87, "ender_pearl" );
        register( 88, "leash_knot" );
        register( 89, "wither_skull" );
        register( 90, "boat" );
        register( 91, "wither_skull_dangerous" );
        register( 93, "lightning_bolt" );
        register( 94, "small_fireball" );
        register( 95, "area_effect_cloud" );
        register( 96, "hopper_minecart" );
        register( 97, "tnt_minecart" );
        register( 98, "chest_minecart" );
        register( 100, "command_block_minecart" );
        register( 101, "lingering_potion" );
        register( 102, "llama_spit" );
        register( 103, "evocation_fang" );

        register( 32, "zombie" );
        register( 33, "creeper" );
        register( 34, "skeleton" );
        register( 35, "spider" );
        register( 36, "zombie_pigman" );
        register( 37, "slime" );
        register( 38, "enderman" );
        register( 39, "silverfish" );
        register( 40, "cave_spider" );
        register( 41, "ghast" );
        register( 42, "magma_cube" );
        register( 43, "blaze" );
        register( 44, "zombie_villager" );
        register( 45, "witch" );
        register( 46, "stray" );
        register( 47, "husk" );
        register( 48, "wither_skeleton" );
        register( 49, "guardian" );
        register( 50, "elder_guardian" );
        register( 52, "wither" );
        register( 53, "ender_dragon" );
        register( 54, "shulker" );
        register( 55, "endermite" );
        register( 57, "vindicator" );
        register( 104, "evocation_illager" );
        register( 105, "vex" );

        register( 10, "chicken" );
        register( 11, "cow" );
        register( 12, "pig" );
        register( 13, "sheep" );
        register( 14, "wolf" );
        register( 15, "villager" );
        register( 16, "mooshroom" );
        register( 17, "squid" );
        register( 18, "rabbit" );
        register( 19, "bat" );
        register( 20, "iron_golem" );
        register( 21, "snow_golem" );
        register( 22, "ocelot" );
        register( 23, "horse" );
        register( 24, "donkey" );
        register( 25, "mule" );
        register( 26, "skeleton_horse" );
        register( 27, "zombie_horse" );
        register( 28, "polar_bear" );
        register( 29, "llama" );
        register( 30, "parrot" );
        // CHECKSTYLE:ON
    }

    public static void register( int id, String newId ) {
        NEW_ID_MAPPING.put( newId, id );
        OLD_ID_MAPPING.put( id, newId );
    }

    public static int valueOfWithId( String newId ) {
        return NEW_ID_MAPPING.getOrDefault( newId, 0 );
    }

    public static String newIdFromValue( int id ) {
        return OLD_ID_MAPPING.getOrDefault( id, "minecraft:air" );
    }

}
