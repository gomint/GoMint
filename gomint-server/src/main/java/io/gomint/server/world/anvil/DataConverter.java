package io.gomint.server.world.anvil;

import io.gomint.server.util.Pair;
import io.gomint.world.block.BlockFace;

import java.util.HashMap;
import java.util.Map;

/**
 * @author geNAZt
 * @version 1.0
 */
class DataConverter {

    private Pair<Integer, Converter>[] converter = new Pair[255];
    private Pair<Integer, Byte> convertedValue = new Pair<>( 0, (byte) 1 );

    public DataConverter() {
        addConverter( 36, 250, ( b, m ) -> m );                                                                                 // Piston extension
        addConverter( 43, 43, ( b, m ) -> m == 6 ? 7 : m == 7 ? 6 : m );                                                        // Double Slab
        addConverter( 44, 44, ( b, m ) -> m == 6 ? 7 : m == 7 ? 6 : m == 14 ? 15 : m == 15 ? 14 : m );                          // Slab
        addConverter( 84, 25, ( b, m ) -> (byte) 0 );                                                                           // Jukebox
        addConverter( 85, 85, ( b, m ) -> (byte) 0 );                                                                           // Fence
        addConverter( 95, 241, ( b, m ) -> m );                                                                                 // Stained Glass
        addConverter( 96, 96, ( b, m ) -> (byte) ( ( ( m & 0x04 ) << 1 ) | ( ( m & 0x08 ) >> 1 ) | ( 3 - ( m & 0x03 ) ) ) );    // Trapdoor
        addConverter( 167, 167, ( b, m ) -> (byte) ( ( ( m & 0x04 ) << 1 ) | ( ( m & 0x08 ) >> 1 ) | ( 3 - ( m & 0x03 ) ) ) );  // Iron trapdoor
        addConverter( 125, 157, ( b, m ) -> m );                                                                                // Double wooden slab
        addConverter( 126, 158, ( b, m ) -> m );                                                                                // Wooden slab
        addConverter( 143, 143, ( b, m ) -> {                                                                                   // Wooden button
            // Check if button has been pressed
            boolean pressed = false;
            if ( ( m & 0x08 ) == 0x08 ) {
                pressed = true;
                m ^= 0x08;
            }

            switch ( m ) {
                case 0:
                    return (byte) ( BlockFace.DOWN.getValue() + ( ( pressed ) ? 8 : 0 ) ); // 0
                case 1:
                    return (byte) ( BlockFace.SOUTH.getValue() + ( ( pressed ) ? 8 : 0 ) ); // 5
                case 2:
                    return (byte) ( BlockFace.NORTH.getValue() + ( ( pressed ) ? 8 : 0 ) ); // 4
                case 3:
                    return (byte) ( BlockFace.WEST.getValue() + ( ( pressed ) ? 8 : 0 ) ); // 3
                case 4:
                    return (byte) ( BlockFace.EAST.getValue() + ( ( pressed ) ? 8 : 0 ) ); // 2
                case 5:
                    return (byte) ( BlockFace.UP.getValue() + ( ( pressed ) ? 8 : 0 ) ); // 1
            }

            return (byte) ( BlockFace.DOWN.getValue() + ( ( pressed ) ? 8 : 0 ) );
        } );

        addConverter( 77, 77, ( b, m ) -> {                                                                                     // Stone Button
            // Check if button has been pressed
            boolean pressed = false;
            if ( ( m & 0x08 ) == 0x08 ) {
                pressed = true;
                m ^= 0x08;
            }

            switch ( m ) {
                case 0:
                    return (byte) ( BlockFace.DOWN.getValue() + ( ( pressed ) ? 8 : 0 ) ); // 0
                case 1:
                    return (byte) ( BlockFace.SOUTH.getValue() + ( ( pressed ) ? 8 : 0 ) ); // 5
                case 2:
                    return (byte) ( BlockFace.NORTH.getValue() + ( ( pressed ) ? 8 : 0 ) ); // 4
                case 3:
                    return (byte) ( BlockFace.WEST.getValue() + ( ( pressed ) ? 8 : 0 ) ); // 3
                case 4:
                    return (byte) ( BlockFace.EAST.getValue() + ( ( pressed ) ? 8 : 0 ) ); // 2
                case 5:
                    return (byte) ( BlockFace.UP.getValue() + ( ( pressed ) ? 8 : 0 ) ); // 1
            }

            return (byte) ( BlockFace.DOWN.getValue() + ( ( pressed ) ? 8 : 0 ) );
        } );

        addConverter( 157, 126, ( b, m ) -> m );                                                                                // Activator rail
        addConverter( 158, 125, ( b, m ) -> m );                                                                                // Dropper
        addConverter( 166, 95, ( b, m ) -> (byte) 0 );                                                                          // Barrier
        addConverter( 176, 0, ( b, m ) -> (byte) 0 );                                                                           // Standing banner
        addConverter( 177, 0, ( b, m ) -> (byte) 0 );                                                                           // Banner
        addConverter( 188, 85, ( b, m ) -> (byte) 1 );
        addConverter( 189, 85, ( b, m ) -> (byte) 2 );
        addConverter( 190, 85, ( b, m ) -> (byte) 3 );
        addConverter( 191, 85, ( b, m ) -> (byte) 4 );
        addConverter( 192, 85, ( b, m ) -> (byte) 5 );
        addConverter( 198, 208, ( b, m ) -> m );
        addConverter( 199, 140, ( b, m ) -> m );
        addConverter( 202, 201, ( b, m ) -> (byte) 2 );
        addConverter( 205, 182, ( b, m ) -> (byte) 1 );
        addConverter( 207, 244, ( b, m ) -> m );
        addConverter( 208, 198, ( b, m ) -> m );
        addConverter( 209, 209, ( b, m ) -> m );
        addConverter( 212, 174, ( b, m ) -> m );
        addConverter( 218, 251, ( b, m ) -> m );
    }

    private void addConverter( int sourceId, int destId, Converter converter ) {
        this.converter[sourceId] = new Pair<>( destId, converter );
    }

    Pair<Integer, Byte> convert( int blockId, byte metaData ) {
        Pair<Integer, Converter> converterPair = this.converter[blockId];
        if ( converterPair == null ) {
            return null;
        } else {
            this.convertedValue.setFirst( converterPair.getFirst() );
            this.convertedValue.setSecond( converterPair.getSecond().convert( blockId, metaData ) );
            return this.convertedValue;
        }
    }

    private interface Converter {
        byte convert( int blockId, byte metaData );
    }

}
