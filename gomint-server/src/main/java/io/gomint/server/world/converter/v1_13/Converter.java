package io.gomint.server.world.converter.v1_13;

import io.gomint.server.util.BlockIdentifier;

/**
 * Created by Marco Neuhaus on 16.09.2018 for the Project GoMintFork.
 */
interface Converter {
    BlockIdentifier convert(String blockData);
}
