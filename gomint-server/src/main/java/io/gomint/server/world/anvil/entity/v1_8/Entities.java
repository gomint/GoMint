/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.anvil.entity.v1_8;

import io.gomint.server.entity.Entity;
import io.gomint.server.util.DumpUtil;
import io.gomint.server.world.WorldAdapter;
import io.gomint.server.world.anvil.entity.EntityConverter;
import io.gomint.server.world.anvil.entity.EntityConverters;
import io.gomint.taglib.NBTTagCompound;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author geNAZt
 * @version 1.0
 */
public class Entities implements EntityConverters {

    private static final Logger LOGGER = LoggerFactory.getLogger( Entities.class );
    private final Object2ObjectMap<String, EntityConverter> converters;
    private final WorldAdapter world;

    public Entities( WorldAdapter world ) {
        this.world = world;
        this.converters = new Object2ObjectOpenHashMap<>();

        // Fill in converters
        this.converters.put( "Villager", new EntityVillagerConverter( this.world ) );
    }

    @Override
    public Entity read( NBTTagCompound compound ) {
        String id = compound.getString( "id", null );
        if ( id == null ) {
            return null;
        }

        EntityConverter<? extends Entity> converter = this.converters.get( id );
        if ( converter == null ) {
            LOGGER.warn( "Unknown entity {}", id );
            DumpUtil.dumpNBTCompund( compound );
            return null;
        }

        return converter.readFrom( compound );
    }

    @Override
    public NBTTagCompound write( Entity entity ) {
        return null;
    }

}
