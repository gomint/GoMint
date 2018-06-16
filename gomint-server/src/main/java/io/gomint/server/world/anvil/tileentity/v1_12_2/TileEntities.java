/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.anvil.tileentity.v1_12_2;

import io.gomint.server.entity.tileentity.TileEntity;
import io.gomint.server.world.WorldAdapter;
import io.gomint.server.world.anvil.tileentity.TileEntityConverters;
import io.gomint.server.world.anvil.tileentity.v1_8.SignConverter;
import io.gomint.server.world.anvil.tileentity.v1_8.SkullConverter;
import io.gomint.taglib.NBTTagCompound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author geNAZt
 * @version 1.0
 */
public class TileEntities implements TileEntityConverters {

    private static final Logger LOGGER = LoggerFactory.getLogger( TileEntities.class );

    // Individual converters for the tiles
    private SignConverter signConverter;
    private SkullConverter skullConverter;

    /**
     * Construct 1.8 converter for the given world
     *
     * @param worldAdapter which should be used in all convert operations
     */
    public TileEntities( WorldAdapter worldAdapter ) {
        this.signConverter = new SignConverter( worldAdapter );
        this.skullConverter = new SkullConverter( worldAdapter );
    }

    @Override
    public TileEntity read( NBTTagCompound compound ) {
        String id = compound.getString( "id", "" );
        switch ( id ) {
            case "minecraft:structure_block":
                return null; // TODO: Need template parsing and template processors for structures first
            case "minecraft:command_block":
                return null; // TODO: Needs other configuration options for command blocks first
            case "minecraft:skull":
                return this.skullConverter.readFrom( compound );
            case "minecraft:sign":
                return this.signConverter.readFrom( compound );
        }

        LOGGER.warn( "Unknown id {}", id );
        return null;
    }

}
