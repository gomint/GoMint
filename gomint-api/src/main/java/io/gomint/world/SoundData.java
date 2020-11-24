/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.world;

import io.gomint.world.block.Block;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public final class SoundData {

    private Instrument instrument = null;
    private Class<? extends Block> block = null;

    private SoundData( Instrument instrument ) {
        this.instrument = instrument;
    }

    private SoundData( Class<? extends Block> blockClass ) {
        this.block = blockClass;
    }

    /**
     * Get the instrument selected. Is null when no instrument has been selected
     *
     * @return instrument selected or null
     */
    public Instrument getInstrument() {
        return this.instrument;
    }

    /**
     * Get the block selected. Is null when no block has been selected
     *
     * @return block selected or null
     */
    public Class<? extends Block> getBlock() {
        return this.block;
    }

    /**
     * Generate a new sound data instance for the given instrument
     *
     * @param instrument which should be played
     * @return sound data instance
     */
    public static SoundData instrument( Instrument instrument ) {
        return new SoundData( instrument );
    }

    /**
     * Generate a new sound data instance for the given block
     *
     * @param blockClass for which we need a sound
     * @return sound data instance
     */
    public static SoundData block( Class<? extends Block> blockClass ) {
        return new SoundData( blockClass );
    }

    public enum Instrument {
        // CHECKSTYLE:OFF
        PIANO,
        BASS_DRUM,
        CLICK,
        TABOUR,
        BASS
        // CHECKSTYLE:ON
    }

}
