package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemStack;
import io.gomint.server.inventory.item.generator.ItemGenerator;
import io.gomint.server.registry.GeneratorCallback;
import io.gomint.server.registry.Registry;
import io.gomint.taglib.NBTTagCompound;
import javassist.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

/**
 * @author geNAZt
 * @version 1.0
 */
public class Items {

    private static final Logger LOGGER = LoggerFactory.getLogger( Items.class );
    private static final Registry<ItemGenerator> GENERATORS = new Registry<>( new GeneratorCallback<ItemGenerator>() {
        private ClassPool pool = ClassPool.getDefault();
        private CtClass inter;

        @Override
        public ItemGenerator generate( int id, Class<?> clazz ) {
            if ( id == 210 || id == 211 || id == 212 || id == 230 || id == 242 ) {
                System.out.println( "INVALID BLOCK ID" );
            }

            if ( id == 454 || id == 456 || id == 498 ) {
                System.out.println( "INVALID ITEM ID" );
            }

            if ( this.inter == null ) {
                try {
                    this.inter = pool.get( "io.gomint.server.inventory.item.generator.ItemGenerator" );
                } catch ( NotFoundException e ) {
                    e.printStackTrace();
                }
            }

            CtClass generatorClass = this.pool.makeClass( "io.gomint.server.inventory.item.generator." + clazz.getSimpleName() );
            generatorClass.addInterface( this.inter );

            // Generate the generation method
            try {
                generatorClass.addMethod( CtNewMethod.make( "public io.gomint.inventory.item.ItemStack generate( short data, byte amount, io.gomint.taglib.NBTTagCompound nbt ) {" +
                        "return new " + clazz.getName() + "(data, amount, nbt);" +
                        "}", generatorClass ) );

                generatorClass.addMethod( CtNewMethod.make( "public io.gomint.inventory.item.ItemStack generate( short data, byte amount ) {" +
                        "return new " + clazz.getName() + "(data, amount);" +
                        "}", generatorClass ) );
            } catch ( CannotCompileException e ) {
                e.printStackTrace();
                return null;
            }

            try {
                return (ItemGenerator) generatorClass.toClass().newInstance();
            } catch ( InstantiationException | IllegalAccessException | CannotCompileException e ) {
                e.printStackTrace();
            }

            return null;
        }
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
            LOGGER.warn( "Unknown item " + id );
            return null;
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
