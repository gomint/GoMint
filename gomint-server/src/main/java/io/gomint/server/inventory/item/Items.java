package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemStack;
import io.gomint.server.inventory.item.generator.ItemGenerator;
import io.gomint.server.registry.GeneratorCallback;
import io.gomint.server.registry.Registry;
import io.gomint.taglib.NBTTagCompound;
import javassist.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;

/**
 * @author geNAZt
 * @version 1.0
 */
public class Items {

    private static final Logger LOGGER = LoggerFactory.getLogger( Items.class );
    private static final Registry<ItemGenerator> GENERATORS = new Registry<>( ( id, clazz ) -> {
        try {
            return (ItemGenerator) Class.forName( "io.gomint.server.inventory.item.generator." + clazz.getSimpleName() + "Generator" ).newInstance();
        } catch ( ClassNotFoundException | IllegalAccessException | InstantiationException e1 ) {
            LOGGER.error( "Could not use pre generated generator: ", e1 );
        }

        return null;
    } );

    static {
        GENERATORS.register( "io.gomint.server.inventory.item" );
    }

    /**
     * Create a new item stack based on a id
     *
     * @param id     of the type for this item stack
     * @param data   for this item stack
     * @param amount in this item stack
     * @param nbt    additional data for this item stack
     * @param <T>    type of item stack
     * @return generated item stack
     */
    public static <T extends ItemStack> T create( int id, short data, byte amount, NBTTagCompound nbt ) {
        ItemGenerator itemGenerator = GENERATORS.getGenerator( id );
        if ( itemGenerator == null ) {
            LOGGER.warn( "Unknown item {}", id );
            return null;
        }

        // Cleanup NBT tag, root must be empty string
        if ( nbt != null && !nbt.getName().isEmpty() ) {
            nbt = nbt.deepClone( "" );
        }

        return itemGenerator.generate( data, amount, nbt );
    }

    /**
     * Create a new item stack based on a api interface
     *
     * @param itemClass which defines what item to use
     * @param amount    in this item stack
     * @param <T>       type of item stack
     * @return generated item stack
     */
    public static <T extends ItemStack> T create( Class<T> itemClass, byte amount ) {
        ItemGenerator itemGenerator = GENERATORS.getGenerator( itemClass );
        if ( itemGenerator == null ) {
            return null;
        }

        return itemGenerator.generate( (short) 0, amount );
    }

    /**
     * Get all known items
     *
     * @return
     */
    public static Collection<Integer> getAll() {
        return GENERATORS.getAll();
    }

}
