/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.anvil.entity.mcpe;

import io.gomint.server.entity.Entity;
import io.gomint.server.entity.EntityMagicNumbers;
import io.gomint.server.util.DumpUtil;
import io.gomint.server.world.WorldAdapter;
import io.gomint.server.world.anvil.entity.EntityConverter;
import io.gomint.server.world.anvil.entity.EntityConverters;
import io.gomint.taglib.NBTTagCompound;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author geNAZt
 * @version 1.0
 */
public class Entities implements EntityConverters {

    private final Int2ObjectMap<EntityConverter> converters;
    private final WorldAdapter world;

    public Entities( WorldAdapter world ) {
        this.world = world;
        this.converters = new Int2ObjectOpenHashMap<>();

        // Fill in converters
        this.converters.put( 15, new EntityVillagerConverter( this.world ) );
    }

    @Override
    public Entity read( NBTTagCompound compound ) {
        String id = compound.getString( "id", null );
        int entityId = -1;

        if ( id != null ) {
            // Try to directly load the entity by its name (normal mc:pe ids used)
            entityId = EntityMagicNumbers.valueOfWithId( id );
            if ( entityId == 0 ) {
                // Try again with all lower case id
                String idLower = id.toLowerCase();
                entityId = EntityMagicNumbers.valueOfWithId( idLower );
                if ( entityId == 0 ) {
                    // Ok we don't get it. Try again with converting additional uppercase chars converted to _ like ZombieHorse to zombie_horse
                    String underScoreLowercaseID = convertToUnderScoreLower( id );
                    entityId = EntityMagicNumbers.valueOfWithId( underScoreLowercaseID );
                }
            }

            // Check for converter
            EntityConverter converter = this.converters.get( entityId );
            if ( converter != null ) {
                return (Entity) converter.readFrom( compound );
            }

            Entity entity = this.world.getServer().getEntities().create( entityId );
            if ( entity != null ) {
                entity.initFromNBT( compound );
                return entity;
            }
        }

        return null;
    }

    private String convertToUnderScoreLower( String id ) {
        // New value
        StringBuilder builder = new StringBuilder();

        // Get the original char array
        char[] original = id.toCharArray();

        // The first letter has to be ignored / convert to lowercase if needed
        if ( original[0] <= 90 && original[0] >= 65 ) {
            builder.append( (char) ( original[0] + 32 ) );
        } else {
            builder.append( original[0] );
        }

        // Iterate over the string, but we skip the first char since we already converted it
        for ( int i = 1; i < original.length; i++ ) {
            // When we hit a uppercase now we insert a _ and the lowercase value
            if ( original[i] <= 90 && original[i] >= 65 ) {
                builder.append( "_" ).append( (char) ( original[i] + 32 ) );
            } else {
                builder.append( original[i] );
            }
        }

        return builder.toString();
    }

}
