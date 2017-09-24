package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.math.Location;
import io.gomint.math.Vector;
import io.gomint.server.entity.Entity;
import io.gomint.server.entity.tileentity.TileEntity;
import io.gomint.server.registry.GeneratorCallback;
import io.gomint.server.registry.Registry;
import io.gomint.server.util.AllPermission;
import io.gomint.server.world.block.generator.BlockGenerator;
import javassist.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.CodeSigner;
import java.security.CodeSource;
import java.security.ProtectionDomain;

/**
 * @author geNAZt
 * @version 1.0
 */
public class Blocks {

    private static final Logger LOGGER = LoggerFactory.getLogger( Blocks.class );
    private static final Registry<BlockGenerator> GENERATORS = new Registry<>( new GeneratorCallback<BlockGenerator>() {
        @Override
        public BlockGenerator generate( int id, Class<?> clazz ) {
            // Create generated Generator for this block
            ClassPool pool = ClassPool.getDefault();
            CtClass generatorCT = pool.makeClass( "io.gomint.server.world.block.generator." + clazz.getSimpleName() );

            try {
                generatorCT.setInterfaces( new CtClass[]{ pool.get( "io.gomint.server.world.block.generator.BlockGenerator" ) } );
            } catch ( NotFoundException e ) {
                e.printStackTrace();
                return null;
            }

            try {
                generatorCT.addMethod( CtNewMethod.make( "public Object generate( byte blockData, byte skyLightLevel, byte blockLightLevel, io.gomint.server.entity.tileentity.TileEntity tileEntity, io.gomint.math.Location location ) {" +
                        "io.gomint.server.world.block.Block block = new " + clazz.getName() + "();" +
                        "block.setBlockData( blockData );\n" +
                        "block.setTileEntity( tileEntity );\n" +
                        "block.setWorld( (io.gomint.server.world.WorldAdapter) location.getWorld() );\n" +
                        "block.setLocation( location );\n" +
                        "block.setSkyLightLevel( skyLightLevel );\n" +
                        "block.setBlockLightLevel( blockLightLevel );" +
                        "return block;" +
                        "}", generatorCT ) );

                generatorCT.addMethod( CtNewMethod.make( "public Object generate() { return new " + clazz.getName() + "(); }", generatorCT ) );
            } catch ( CannotCompileException e ) {
                e.printStackTrace();
                return null;
            }

            try {
                // Use the same code source as the Gomint JAR
                CodeSource codeSource = new CodeSource( new URL( "file:///generated/blocks/" ), (CodeSigner[]) null );
                return (BlockGenerator) generatorCT.toClass( ClassLoader.getSystemClassLoader(), new ProtectionDomain( codeSource, new AllPermission() ) ).newInstance();
            } catch ( InstantiationException | IllegalAccessException | CannotCompileException | MalformedURLException e ) {
                e.printStackTrace();
            }

            return null;
        }
    } );

    static {
        GENERATORS.register( "io.gomint.server.world.block" );
    }

    public static <T extends Block> T get( int blockId, byte blockData, byte skyLightLevel, byte blockLightLevel,
                                           TileEntity tileEntity, Location location ) {
        BlockGenerator instance = GENERATORS.getGenerator( blockId );
        if ( instance != null ) {
            return instance.generate( blockData, skyLightLevel, blockLightLevel, tileEntity, location );
        }

        LOGGER.warn( "Unknown block " + blockId );
        return null;
    }

    public static <T extends Block> T get( Class<?> apiInterface ) {
        BlockGenerator instance = GENERATORS.getGenerator( apiInterface );
        if ( instance != null ) {
            return instance.generate();
        }

        return null;
    }

    public static boolean replaceWithItem( Entity entity, Block block, ItemStack item, Vector clickVector ) {
        // We need to change the block id first
        int id = ( (io.gomint.server.inventory.item.ItemStack) item ).getBlockId();
        BlockGenerator blockGenerator = GENERATORS.getGenerator( id );
        Block newBlock = blockGenerator.generate();
        if ( !newBlock.beforePlacement( item, block.location ) ) {
            return false;
        }

        byte data = newBlock.calculatePlacementData( entity, item, clickVector );
        block = block.setType( newBlock.getClass(), data );
        block.afterPlacement();
        return true;
    }

}
