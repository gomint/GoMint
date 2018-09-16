package io.gomint.server.world.converter.v1_13;

import io.gomint.server.util.BlockIdentifier;

interface Converter {
    BlockIdentifier convert(String blockData);
}
