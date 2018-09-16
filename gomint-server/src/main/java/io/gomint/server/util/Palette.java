/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.util;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.math.MathUtils;
import lombok.Getter;

import java.util.BitSet;

/**
 * @author geNAZt
 * @version 1.0
 */
@Getter
public class Palette {

    @Getter
    public enum PaletteVersion {
        P1( 1, 32 ),
        P2( 2, 16 ),
        P3( 3, 10, 2 ),
        P4( 4, 8 ),
        P5( 5, 6, 2 ),
        P6( 6, 5, 2 ),
        P8( 8, 4 ),
        P16( 16, 2 );

        @Getter
        private final byte versionId;
        private final byte amountOfWords;
        private final byte amountOfPadding;

        PaletteVersion( int versionId, int amountOfWords ) {
            this( versionId, amountOfWords, 0 );
        }

        PaletteVersion( int versionId, int amountOfWords, int amountOfPadding ) {
            this.versionId = (byte) versionId;
            this.amountOfWords = (byte) amountOfWords;
            this.amountOfPadding = (byte) amountOfPadding;
        }
    }

    private PacketBuffer data;
    private PaletteVersion paletteVersion = null;

    // Output indexes
    private short[] output = null;

    // Input bitset
    private int bits = 0;
    private int inputIndex = 0;
    private int wordsWritten = 0;

    /**
     * Construct a new reader for the given palette version
     *
     * @param data    which should be used to read/write
     * @param version of the palette or the amount of blocks we want to store in one word
     * @param read    do we read or write to this palette?
     */
    public Palette( PacketBuffer data, int version, boolean read ) {
        this.data = data;

        for ( PaletteVersion paletteVersionCanidate : PaletteVersion.values() ) {
            if ( ( !read && paletteVersionCanidate.getAmountOfWords() <= version ) ||
                ( read && paletteVersionCanidate.getVersionId() == version ) ) {
                this.paletteVersion = paletteVersionCanidate;
                break;
            }
        }

        if ( this.paletteVersion == null ) {
            throw new IllegalArgumentException( "Palette version " + version + " is unknown" );
        }
    }

    public void addIndexIDs( int[] indexIDs ) {
        for ( int i = 0; i < indexIDs.length; i++ ) {
            int id = indexIDs[i];

            // Check if old input is full and we need a new one
            if ( this.wordsWritten == this.paletteVersion.getAmountOfWords() ) {
                // Write to output
                this.data.writeLInt( this.bits );

                // New input
                this.bits = 0;
                this.inputIndex = 0;
                this.wordsWritten = 0;
            }

            // Write id
            while ( id != 0L ) {
                if ( id % 2L != 0 ) {
                    this.bits |= ( 1 << this.inputIndex );
                }

                ++this.inputIndex;
                id = id >>> 1;
            }

            // Increment written words
            this.wordsWritten++;

            // Set the index correct
            this.inputIndex = this.wordsWritten * this.paletteVersion.getVersionId();
        }
    }

    public void finish() {
        this.data.writeLInt( this.bits );
        this.bits = 0;
        this.inputIndex = 0;
    }

    public short[] getIndexes() {
        // Do we need to read first?
        if ( this.output == null ) {
            this.output = new short[4096];

            // We need the amount of iterations
            int iterations = MathUtils.fastCeil( 4096 / (float) this.paletteVersion.getAmountOfWords() );
            for ( int i = 0; i < iterations; i++ ) {
                int currentData = this.data.readLInt();
                int index = 0;

                for ( byte b = 0; b < this.paletteVersion.getAmountOfWords(); b++ ) {
                    short val = 0;
                    int innerShiftIndex = 0;

                    for ( byte i1 = 0; i1 < this.paletteVersion.getVersionId(); i1++ ) {
                        if ( ( currentData & ( 1 << index++ ) ) != 0 ) {
                            val ^= 1 << innerShiftIndex;
                        }

                        innerShiftIndex++;
                    }

                    int setIndex = ( i * this.paletteVersion.getAmountOfWords() ) + b;
                    if ( setIndex < 4096 ) {
                        this.output[setIndex] = val;
                    }
                }
            }
        }

        return this.output;
    }

}
