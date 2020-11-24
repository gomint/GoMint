/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world;

import io.gomint.taglib.NBTTagCompound;
import io.gomint.world.WeatherType;

import java.util.Random;

/**
 * @author BlackyPaw
 * @version 1.0
 */
public class Weather {

    private final Random random;

    private int clearWeatherTime;
    private boolean downfall;
    private int downfallTime;
    private boolean thundering;
    private int thunderTime;

    public Weather( Random random, NBTTagCompound compound ) {
        this.random = random;

        this.clearWeatherTime = compound.getInteger( "clearWeatherTime", 0 );
        this.downfall = ( compound.getByte( "raining", (byte) 0 ) == 1 );
        this.downfallTime = compound.getInteger( "rainTime", 0 );
        this.thundering = ( compound.getByte( "thundering", (byte) 0 ) == 1 );
        this.thunderTime = compound.getInteger( "thunderTime", 0 );
    }

    /**
     * Gets the current type of weather that the world is experiencing.
     *
     * @return The current type of weather
     */
    public WeatherType getCurrentWeather() {
        return ( this.thundering ? WeatherType.THUNDERSTORM : ( this.downfall ? WeatherType.DOWNFALL : WeatherType.CLEAR ) );
    }

    /**
     * Gets the time in ticks before the next weather change will take place.
     *
     * @return The time at which the weather will change
     */
    public int getNextWeatherTicks() {
        return Math.min( this.downfallTime, this.thunderTime );
    }

    /**
     * Updates the weather
     */
    public void tick() {
        this.clearWeatherTime--;
        this.downfallTime--;
        this.thunderTime--;

        if ( this.downfallTime <= 0 ) {
            this.downfall = !this.downfall;
            this.downfallTime = this.random.nextInt( 200000 );
        }

        if ( this.thunderTime <= 0 ) {
            this.thundering = !this.thundering;
            this.thunderTime = this.random.nextInt( 500000 );
        }

        if ( this.thundering && !this.downfall ) {
            // If it's thundering we always have downfall, too!
            this.downfall = true;
        }

        // TODO: Dispatch weather change event here!
    }

}
