/*
 * Copyright (c) 2018 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.world;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @param <T> Type of the Gamerule. Can be Boolean, Integer or Float
 * @author BlackyPaw
 * @version 1.0
 */
public final class Gamerule<T> {

    private static final Map<String, Gamerule> BY_NBT = new HashMap<>();

    public static final Gamerule<Boolean> COMMANDBLOCK_OUTPUT = new Gamerule<>( "commandblockoutput", Boolean.class );
    public static final Gamerule<Boolean> DO_DAYLIGHT_CYCLE = new Gamerule<>( "dodaylightcycle", Boolean.class );
    public static final Gamerule<Boolean> DO_FIRE_TICK = new Gamerule<>( "dofiretick", Boolean.class );
    public static final Gamerule<Boolean> DO_MOB_LOOT = new Gamerule<>( "domobloot", Boolean.class );
    public static final Gamerule<Boolean> DO_MOB_SPAWNING = new Gamerule<>( "domobspawning", Boolean.class );
    public static final Gamerule<Boolean> DO_TILE_DROPS = new Gamerule<>( "dotiledrops", Boolean.class );
    public static final Gamerule<Boolean> KEEP_INVENTORY = new Gamerule<>( "keepinventory", Boolean.class );
    public static final Gamerule<Boolean> LOG_ADMIN_COMMANDS = new Gamerule<>( "logAdminCommands", Boolean.class );
    public static final Gamerule<Boolean> MOB_GRIEFING = new Gamerule<>( "mobgriefing", Boolean.class );
    public static final Gamerule<Boolean> NATURAL_REGENERATION = new Gamerule<>( "naturalRegeneration", Boolean.class );
    public static final Gamerule<Integer> RANDOM_TICK_SPEED = new Gamerule<>( "randomTickSpeed", Integer.class );
    public static final Gamerule<Boolean> SEND_COMMAND_FEEDBACK = new Gamerule<>( "sendcommandfeedback", Boolean.class );
    public static final Gamerule<Boolean> SHOW_DEATH_MESSAGES = new Gamerule<>( "showDeathMessages", Boolean.class );
    public static final Gamerule<Boolean> FIRE_DAMAGE = new Gamerule<>( "firedamage", Boolean.class );
    public static final Gamerule<Boolean> PVP = new Gamerule<>( "pvp", Boolean.class );
    public static final Gamerule<Boolean> DO_ENTITY_DROPS = new Gamerule<>( "doentitydrops", Boolean.class );
    public static final Gamerule<Boolean> DROWNING_DAMAGE = new Gamerule<>( "drowningdamage", Boolean.class );
    public static final Gamerule<Boolean> DO_WEATHER_CYCLE = new Gamerule<>( "doweathercycle", Boolean.class );

    private final String nbtName;
    private final Class<T> valueType;
    private Method instantiator;

    private Gamerule( final String nbtName, final Class<T> valueType ) {
        this.nbtName = nbtName;
        this.valueType = valueType;

        try {
            this.instantiator = this.valueType.getDeclaredMethod( "valueOf", new Class<?>[]{ String.class } );
            this.instantiator.setAccessible( true );
        } catch ( NoSuchMethodException e ) {
            e.printStackTrace();
            this.instantiator = null;
        }

        BY_NBT.put( nbtName, this );
    }

    /**
     * Tries to find a gamerule given its NBT name.
     *
     * @param nbtName The NBT name of the gamerule
     * @return The gamerule on success or null if no gamerule according to the NBT name was found
     */
    public static Gamerule getByNbtName( String nbtName ) {
        return BY_NBT.get( nbtName );
    }

    /**
     * Gets the name of the gamerule as it appears inside NBT files such as level.dat
     *
     * @return The name of the gamerule as it appears inside NBT files
     */
    public String getNbtName() {
        return this.nbtName;
    }

    /**
     * Gets the type of value this gamerule expects.
     *
     * @return The type of value this gamerule expects
     */
    public Class<?> getValueType() {
        return this.valueType;
    }

    /**
     * Creates a value of this gamerule's value type given the string representation of the value.
     *
     * @param value The value as a string
     * @return The value of the gamerule in its appropriate type
     */
    public T createValueFromString( String value ) {
        if ( this.instantiator == null ) {
            return null;
        }

        try {
            return this.valueType.cast( this.instantiator.invoke( null, new Object[]{ value } ) );
        } catch ( IllegalAccessException | InvocationTargetException e ) {
            e.printStackTrace();
        }

        return null;
    }

}
