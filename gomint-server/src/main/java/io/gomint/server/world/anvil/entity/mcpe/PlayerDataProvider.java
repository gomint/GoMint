/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.anvil.entity.mcpe;

import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.world.anvil.AnvilWorldAdapter;
import io.gomint.server.world.anvil.entity.PlayerDataConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * @author geNAZt
 * @version 1.0
 */
public class PlayerDataProvider implements PlayerDataConverter {

    private static final Logger LOGGER = LoggerFactory.getLogger( PlayerDataProvider.class );
    private final AnvilWorldAdapter worldAdapter;

    public PlayerDataProvider( AnvilWorldAdapter worldAdapter ) {
        this.worldAdapter = worldAdapter;

        // We need the playerdata folder
        File playerDataFolder = new File( this.worldAdapter.getWorldDir(), "playerdata" );
        if ( !playerDataFolder.exists() ) {
            if ( !playerDataFolder.mkdirs() ) {
                LOGGER.warn( "Could not create player data storage" );
            }
        }
    }

    @Override
    public void read( EntityPlayer player ) {

    }

    @Override
    public void write( EntityPlayer player ) {

    }

}
