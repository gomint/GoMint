package io.gomint.server.world.converter.anvil;

import io.gomint.server.util.BlockIdentifier;

interface Converter {
    BlockIdentifier convert( int blockId, byte metaData );
}
