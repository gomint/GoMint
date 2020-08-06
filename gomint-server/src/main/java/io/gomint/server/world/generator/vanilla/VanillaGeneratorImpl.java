/*
 * Copyright (c) 2018 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.generator.vanilla;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import io.gomint.GoMint;
import io.gomint.math.BlockPosition;
import io.gomint.server.GoMintServer;
import io.gomint.server.network.NetworkManager;
import io.gomint.server.network.PostProcessExecutor;
import io.gomint.server.world.WorldAdapter;
import io.gomint.server.world.generator.vanilla.chunk.ChunkSquare;
import io.gomint.server.world.generator.vanilla.chunk.ChunkSquareCache;
import io.gomint.server.world.generator.vanilla.client.Client;
import io.gomint.world.Chunk;
import io.gomint.world.World;
import io.gomint.world.generator.GeneratorContext;
import io.gomint.world.generator.integrated.VanillaGenerator;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import oshi.PlatformEnum;
import oshi.SystemInfo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * @author geNAZt
 * @version 1.0
 */
public class VanillaGeneratorImpl extends VanillaGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger( VanillaGenerator.class );
    private static final String WINDOWS_DIST = "https://minecraft.azureedge.net/bin-win/bedrock-server-1.7.0.13.zip";
    private static final String LINUX_DIST = "https://minecraft.azureedge.net/bin-linux/bedrock-server-1.7.0.13.zip";

    private int port;
    private long processId;
    private long seed;

    @Autowired
    private NetworkManager networkManager;

    @Autowired
    private ApplicationContext applicationContext;

    private List<Client> client;

    private SettableFuture<BlockPosition> spawnPointFuture = SettableFuture.create();

    // Chunks from clients which haven't been consumed by the cache yet
    private final ChunkSquareCache chunkCache = new ChunkSquareCache();

    private AtomicBoolean manualClose = new AtomicBoolean( false );

    /**
     * Create a new chunk generator
     *
     * @param world   for which this generator should generate chunks
     * @param context with which this generator should generate chunks
     */
    public VanillaGeneratorImpl( World world, GeneratorContext context ) {
        super( world, context );

        // Eula check
        if ( !Objects.equals( System.getProperty( "eula.accepted" ), "true" ) ) {
            LOGGER.warn( "============================================================================" );
            LOGGER.warn( " You decided to use the vanilla generator. This needs to download the " );
            LOGGER.warn( " vanilla server binaries. Doing that means you automatically accept following " );
            LOGGER.warn( " terms and conditions: " );
            LOGGER.warn( " " );
            LOGGER.error( " Minecraft Terms: https://account.mojang.com/terms" );
            LOGGER.error( " Microsoft Privacy: https://go.microsoft.com/fwlink/?LinkId=521839" );
            LOGGER.warn( " " );
            LOGGER.error( " IF YOU ACCEPT PASS -Deula.accepted=true IN YOUR START SCRIPT" );
            LOGGER.warn( " " );
            LOGGER.warn( "============================================================================" );

            try {
                System.in.read();
            } catch ( IOException e ) {
                // Ignored
            }

            System.exit( -1 );
        }

        // Check if we have a seed
        if ( context.contains( "seed" ) ) {
            this.seed = context.get( "seed" );
        } else {
            this.seed = ThreadLocalRandom.current().nextLong();
            context.put( "seed", this.seed );
        }

        // Generate temp in current dir
        File temp = new File( "temp" );
        if ( !temp.exists() ) {
            temp.mkdirs();
        }

        // Check if we have the dist temp
        File distFolder = new File( temp, "dist" );
        if ( !distFolder.exists() ) {
            distFolder.mkdirs();
        }

        String urlToCheck;

        // Check if the vanilla server is there
        if ( SystemInfo.getCurrentPlatformEnum() == PlatformEnum.WINDOWS ) {
            urlToCheck = WINDOWS_DIST;
        } else {
            urlToCheck = LINUX_DIST;
        }

        // Split the url by its path and check if file is there
        String[] urlSplit = urlToCheck.split( "/" );
        String file = urlSplit[urlSplit.length - 1];

        File extracted = new File( distFolder, "extracted" );
        if ( !extracted.exists() ) {
            extracted.mkdirs();
        }

        File localServerCopy = new File( distFolder, file );
        if ( !localServerCopy.exists() ) {
            try {
                FileUtils.copyURLToFile( new URL( urlToCheck ), localServerCopy, 30000, 5000 );
                this.unzip( localServerCopy, extracted );
            } catch ( IOException e ) {
                LOGGER.error( "Could not download server binaries", e );
            }
        }

        // Ok we have a unpacked server now, we need to copy it over
        File tempServer = new File( temp, "server-" + world.getWorldName() );
        if ( !tempServer.exists() ) {
            tempServer.mkdirs();
        } else {
            try {
                FileUtils.deleteDirectory( tempServer );
                tempServer.mkdirs();
            } catch ( IOException e ) {
                LOGGER.error( "Could not delete old world server", e );
            }
        }

        // Copy over template
        try {
            FileUtils.copyDirectory( extracted, tempServer );
        } catch ( IOException e ) {
            LOGGER.error( "Could not copy from server template to world server directory", e );
        }

        // Edit the world servers server.properties
        File serverProperties = new File( tempServer, "server.properties" );
        try ( FileInputStream inputStream = new FileInputStream( serverProperties ) ) {
            String data = IOUtils.toString( inputStream, StandardCharsets.UTF_8 );

            // Replace the port
            data = data.replace( "server-port=19132", "server-port=0" );
            data = data.replace( "server-portv6=19133", "server-portv6=0" );

            // Disable online mode
            data = data.replace( "online-mode=true", "online-mode=false" );

            // Set default to creative
            data = data.replace( "gamemode=survival", "gamemode=creative" );

            // Set difficulty to peaceful
            data = data.replace( "difficulty=easy", "difficulty=peaceful" );

            // Set world seed
            data = data.replace( "level-seed=", "level-seed=" + this.seed );

            // Set world seed
            data = data.replace( "allow-cheats=false", "allow-cheats=true" );

            // Set world seed
            data = data.replace( "default-player-permission-level=member", "default-player-permission-level=operator" );

            // Delete old version and write new one
            serverProperties.delete();
            try ( FileOutputStream outputStream = new FileOutputStream( serverProperties ) ) {
                outputStream.write( data.getBytes() );
            }
        } catch ( IOException e ) {
            LOGGER.error( "Could not edit world servers server.properties", e );
        }

        GoMintServer server = (GoMintServer) GoMint.instance();

        // Prepare a starter if needed
        if ( SystemInfo.getCurrentPlatformEnum() == PlatformEnum.LINUX ) {
            String data = "#!/bin/bash\nLD_LIBRARY_PATH=. ./bedrock_server";
            File startSH = new File( tempServer, "start.sh" );

            try ( FileOutputStream outputStream = new FileOutputStream( startSH ) ) {
                outputStream.write( data.getBytes() );
            } catch ( IOException e ) {
                e.printStackTrace();
            }

            try {
                Files.setPosixFilePermissions( startSH.toPath(), PosixFilePermissions.fromString( "rwxr--r--" ) );
            } catch ( IOException e ) {
                e.printStackTrace();
            }
        }

        // Start the server
        SafeExec safeExec = server.getContext().getBean( SafeExec.class );
        this.processId = safeExec.exec( SystemInfo.getCurrentPlatformEnum() == PlatformEnum.WINDOWS ? tempServer.getAbsolutePath() + File.separator + "bedrock_server.exe" : tempServer.getAbsolutePath() + File.separator + "start.sh", tempServer.getAbsolutePath(), line -> {
            if ( line.contains( "IPv4 supported, port:" ) ) {
                String[] split = line.split( " " );
                port = Integer.parseInt( split[split.length - 1] );
                LOGGER.info( "Server {} bound to {}", tempServer, port );
                connect();
            }

            LOGGER.info( "{}> {}", tempServer, line );
        } );
    }

    private void connect() {
        WorldAdapter worldAdapter = (WorldAdapter) this.world;

        this.client = new CopyOnWriteArrayList<>();
        for ( int i = 0; i < 9; i++ ) {
            this.connectClient( worldAdapter );

            try {
                Thread.sleep( 250 );
            } catch ( InterruptedException e ) {
                // Ignored
            }
        }
    }

    private Client connectClient( WorldAdapter worldAdapter ) {
        PostProcessExecutor executor = this.networkManager.getPostProcessService().getExecutor();
        Client newClient = new Client( worldAdapter, this.chunkCache, executor );
        this.applicationContext.getAutowireCapableBeanFactory().autowireBean( newClient );

        // Accept the spawn point
        newClient.setSpawnPointConsumer( blockPosition -> this.spawnPointFuture.set( blockPosition ) );
        newClient.connect( "127.0.0.1", this.port );

        newClient.onDisconnect( aVoid -> {
            this.networkManager.getPostProcessService().releaseExecutor( executor );
            if ( this.manualClose.get() ) {
                return;
            }

            LOGGER.info( "Bot disconnected" );

            ChunkSquare chunkSquare = newClient.getChunkSquare();
            if ( chunkSquare != null ) {
                this.client.remove( newClient );
                this.connectClient( worldAdapter ).moveToSquareAsync( chunkSquare );
            }
        } );

        this.client.add( newClient );
        return newClient;
    }

    private void unzip( File fileZip, File destDir ) throws IOException {
        byte[] buffer = new byte[1024];
        try ( ZipInputStream zis = new ZipInputStream( new FileInputStream( fileZip ) ) ) {
            ZipEntry zipEntry = zis.getNextEntry();
            while ( zipEntry != null ) {
                File newFile = newFile( destDir, zipEntry );

                if ( zipEntry.isDirectory() ) {
                    newFile.mkdirs();
                } else {
                    try ( FileOutputStream fos = new FileOutputStream( newFile ) ) {
                        int len;
                        while ( ( len = zis.read( buffer ) ) > 0 ) {
                            fos.write( buffer, 0, len );
                        }
                    }
                }

                zipEntry = zis.getNextEntry();
            }

            zis.closeEntry();
        }
    }

    private File newFile( File destinationDir, ZipEntry zipEntry ) throws IOException {
        File destFile = new File( destinationDir, zipEntry.getName() );

        String destDirPath = destinationDir.getCanonicalPath();
        String destFilePath = destFile.getCanonicalPath();

        if ( !destFilePath.startsWith( destDirPath + File.separator ) ) {
            throw new IOException( "Entry is outside of the target dir: " + zipEntry.getName() );
        }

        return destFile;
    }

    @Override
    public Chunk generate( int x, int z ) {
        // Get the current chunk request
        while ( this.client == null ) {
            try {
                Thread.sleep( 10 );
            } catch ( InterruptedException e ) {
                // Ignored
            }
        }

        ChunkSquare chunkSquare = this.chunkCache.getChunkSquare( x, z );
        if ( chunkSquare.isComplete() ) {
            return chunkSquare.getChunk( x, z );
        } else {
            // Let all others load
            List<ListenableFuture<Boolean>> readAheadFutures = new ArrayList<>();

            for ( Client useClient : this.client ) {
                ListenableFuture<Boolean> future = this.loadChunkSquare( useClient, x, z );
                if ( future != null ) {
                    readAheadFutures.add( future );
                }
            }

            // Move the client into the square and let it download the chunks
            try {
                Futures.allAsList( readAheadFutures ).get();
            } catch ( InterruptedException | ExecutionException e ) {
                LOGGER.error( "Could not load chunks from server", e );
            }

            return chunkSquare.getChunk( x, z );
        }
    }

    private ListenableFuture<Boolean> loadChunkSquare( Client useClient, int x, int z ) {
        ChunkSquare square = this.chunkCache.getChunkSquare( x, z );
        if ( !square.isLoading() ) {
            return useClient.moveToSquareAsync( square );
        }

        // Check for surroundings (max 4 * 4)
        for ( int addX = -2; addX < 2; addX++ ) {
            for ( int addZ = -2; addZ < 2; addZ++ ) {
                square = this.chunkCache.getChunkSquare( x + ( addX * 16 ), z + ( addZ * 16 ) );
                if ( !square.isLoading() ) {
                    return useClient.moveToSquareAsync( square );
                }
            }
        }

        return null;
    }

    @Override
    public BlockPosition getSpawnPoint() {
        try {
            return this.spawnPointFuture.get();
        } catch ( InterruptedException | ExecutionException e ) {
            LOGGER.error( "Could not get spawn point", e );
        }

        return null;
    }

    @Override
    public void populate( Chunk chunk ) {

    }

    @Override
    public void close() {
        // Close all connections
        this.manualClose.set( true );

        for ( Client client1 : this.client ) {
            client1.disconnect( "Closing chunk generator down" );
        }

        SafeExec safeExec = this.applicationContext.getBean( SafeExec.class );
        safeExec.stop( this.processId );
    }

}
