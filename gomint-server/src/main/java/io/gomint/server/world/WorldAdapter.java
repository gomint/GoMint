/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world;

import io.gomint.GoMint;
import io.gomint.entity.Entity;
import io.gomint.entity.EntityPlayer;
import io.gomint.event.player.PlayerInteractEvent;
import io.gomint.inventory.item.ItemAir;
import io.gomint.inventory.item.ItemStack;
import io.gomint.math.AxisAlignedBB;
import io.gomint.math.BlockPosition;
import io.gomint.math.Location;
import io.gomint.math.MathUtils;
import io.gomint.math.Vector;
import io.gomint.server.GoMintServer;
import io.gomint.server.async.Delegate;
import io.gomint.server.async.Delegate2;
import io.gomint.server.async.MultiOutputDelegate;
import io.gomint.server.config.WorldConfig;
import io.gomint.server.entity.passive.EntityItem;
import io.gomint.server.entity.passive.EntityXPOrb;
import io.gomint.server.entity.tileentity.TileEntity;
import io.gomint.server.network.PlayerConnection;
import io.gomint.server.network.Protocol;
import io.gomint.server.network.packet.*;
import io.gomint.server.util.EnumConnectors;
import io.gomint.server.world.block.Air;
import io.gomint.server.world.storage.TemporaryStorage;
import io.gomint.util.random.FastRandom;
import io.gomint.world.Biome;
import io.gomint.world.Chunk;
import io.gomint.world.Difficulty;
import io.gomint.world.Gamemode;
import io.gomint.world.Gamerule;
import io.gomint.world.Particle;
import io.gomint.world.ParticleData;
import io.gomint.world.Sound;
import io.gomint.world.SoundData;
import io.gomint.world.World;
import io.gomint.world.WorldLayer;
import io.gomint.world.block.Block;
import io.gomint.world.block.BlockAir;
import io.gomint.world.block.BlockFace;
import io.gomint.world.generator.ChunkGenerator;
import io.gomint.world.generator.GeneratorContext;
import io.gomint.world.generator.integrated.VoidGenerator;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author BlackyPaw
 * @version 1.0
 */
@EqualsAndHashCode( of = { "worldDir" } )
@ToString( of = { "levelName" } )
public abstract class WorldAdapter implements World {

    // Shared objects
    @Getter
    protected final GoMintServer server;
    @Getter
    protected final Logger logger;

    // World properties
    @Getter
    protected final File worldDir;
    protected String levelName;
    protected Location spawn;
    @Getter
    protected Map<Gamerule, Object> gamerules = new HashMap<>();
    @Getter
    private WorldConfig config;

    /**
     * Get the difficulty of this world
     */
    @Getter
    @Setter
    protected Difficulty difficulty = Difficulty.NORMAL;

    // Chunk Handling
    protected ChunkCache chunkCache;
    protected ChunkGenerator chunkGenerator;

    // Entity Handling
    private EntityManager entityManager;

    // Block ticking
    int randomUpdateNumber = FastRandom.current().nextInt();
    private TickList tickQueue = new TickList();

    // I/O
    private AtomicBoolean asyncWorkerRunning;
    private BlockingQueue<AsyncChunkTask> asyncChunkTasks;
    private Queue<AsyncChunkPackageTask> chunkPackageTasks;
    private Thread asyncWorkerThread;

    // EntityPlayer handling
    private Object2ObjectMap<io.gomint.server.entity.EntityPlayer, ChunkAdapter> players;

    protected WorldAdapter( GoMintServer server, File worldDir ) {
        this.chunkGenerator = new VoidGenerator( this, new GeneratorContext() );
        this.server = server;
        this.logger = LoggerFactory.getLogger( "io.gomint.World-" + worldDir.getName() );
        this.worldDir = worldDir;
        this.entityManager = new EntityManager( this );
        this.config = this.server.getWorldConfig( worldDir.getName() );
        this.players = new Object2ObjectOpenHashMap<>();
        this.asyncChunkTasks = new LinkedBlockingQueue<>();
        this.chunkPackageTasks = new ConcurrentLinkedQueue<>();
        this.startAsyncWorker( server.getExecutorService() );
        this.initGamerules();
    }

    // ==================================== GENERAL ACCESSORS ==================================== //

    /**
     * Get the current view of players on this world.
     *
     * @return The Collection View of the Players currently on this world
     */
    public Object2ObjectMap<io.gomint.server.entity.EntityPlayer, ChunkAdapter> getPlayers0() {
        return this.players;
    }

    /**
     * Get a collection (set) of all players online on this world
     *
     * @return collection of all players online on this world
     */
    public Collection<EntityPlayer> getPlayers() {
        return Collections.unmodifiableSet( this.players.keySet() );
    }
	
	@Override
	public void playSound( Vector vector, Sound sound, byte pitch ) {
		this.playSound( null, vector, sound, pitch, -1 );
	}

    /**
     * Play a sound at the location given
     *
     * @param player Which should get this sound, if null all get the sound
     * @param vector The location where the sound should be played
     * @param sound  The sound which should be played
     * @param pitch  The pitch at which the sound should be played
     * @param data   additional data for the sound
     * @throws IllegalArgumentException when the sound data given is incorrect for the sound wanted to play
     */
    @Override
    public void playSound( EntityPlayer player, Vector vector, Sound sound, byte pitch, SoundData data ) {
        int soundData = -1;

        switch ( sound ) {
            case LAND:
            case BREAK_BLOCK:
            case PLACE:
            case HIT:
                // Need a block
                if ( data.getBlock() == null ) {
                    throw new IllegalArgumentException( "Sound " + sound + " needs block sound data" );
                }

                soundData = BlockRuntimeIDs.fromLegacy( this.server.getBlocks().getID( data.getBlock() ), (byte) 0,
                    player != null ? ( (io.gomint.server.entity.EntityPlayer) player ).getConnection().getProtocolID() : Protocol.MINECRAFT_PE_PROTOCOL_VERSION );

                break;

            case NOTE:
                // Check if needed data is there
                if ( data.getInstrument() == null ) {
                    throw new IllegalArgumentException( "Sound NOTE needs instrument sound data" );
                }

                switch ( data.getInstrument() ) {
                    case PIANO:
                        soundData = 0;
                        break;
                    case BASS_DRUM:
                        soundData = 1;
                        break;
                    case CLICK:
                        soundData = 2;
                        break;
                    case TABOUR:
                        soundData = 3;
                        break;
                    case BASS:
                        soundData = 4;
                        break;
                    default:
                        soundData = -1;
                        break;
                }

                break;

            default:
                break;
        }

        this.playSound( null, vector, sound, pitch, soundData );
    }

    /**
     * Play a sound at the location given
     *
     * @param player    Which should get this sound, if null all get the sound
     * @param vector    The location where the sound should be played
     * @param sound     The sound which should be played
     * @param pitch     The pitch at which the sound should be played
     * @param extraData any data which should be send to the client to identify the sound
     */
    @Override
    public void playSound( EntityPlayer player, Vector vector, Sound sound, byte pitch, int extraData ) {
        // There are sounds which don't work but have level event counterparts so we use them for now
        switch ( sound ) {
            case IMITATE_GHAST:
                this.sendLevelEvent( player, vector, LevelEvent.SOUND_GHAST, pitch );

                break;
            default:
                PacketWorldSoundEvent soundPacket = new PacketWorldSoundEvent();
                soundPacket.setType( EnumConnectors.SOUND_CONNECTOR.convert( sound ) );
                soundPacket.setPitch( pitch );
                soundPacket.setExtraData( extraData );
                soundPacket.setPosition( vector );

                if ( player == null ) {
                    sendToVisible( vector.toBlockPosition(), soundPacket, entity -> true );
                } else {
                    io.gomint.server.entity.EntityPlayer implPlayer = (io.gomint.server.entity.EntityPlayer) player;
                    implPlayer.getConnection().addToSendQueue( soundPacket );
                }

                break;
        }
    }

    @Override
    public String getWorldName() {
        return this.worldDir.getName();
    }

    @Override
    public String getLevelName() {
        return this.levelName;
    }

    @Override
    public Location getSpawnLocation() {
        return this.spawn;
    }

    @Override
    public <T extends Block> T getBlockAt( BlockPosition pos ) {
        return this.getBlockAt( pos.getX(), pos.getY(), pos.getZ() );
    }

    private Block getOptionalBlockAt( BlockPosition position ) {
        if ( position.getY() < 0 || position.getY() > 255 ) {
            return null;
        }

        ChunkAdapter chunkAdapter = this.getChunk( position.getX() >> 4, position.getZ() >> 4 );
        if ( chunkAdapter != null ) {
            return chunkAdapter.getBlockAt( position.getX() & 0xF, position.getY(), position.getZ() & 0xF );
        }

        return null;
    }

    @Override
    public <T extends Block> T getBlockAt( int x, int y, int z ) {
        return this.getBlockAt( x, y, z, WorldLayer.NORMAL );
    }

    @Override
    public <T extends Block> T getBlockAt( int x, int y, int z, WorldLayer layer ) {
        // Secure location
        if ( y < 0 || y > 255 ) {
            return (T) this.server.getBlocks().get( 0, (byte) 0, (byte) ( y > 255 ? 15 : 0 ), (byte) 0, null, new Location( this, x, y, z ), layer.ordinal() );
        }

        ChunkAdapter chunk = this.loadChunk( x >> 4, z >> 4, false );
        if ( chunk == null ) {
            return (T) this.server.getBlocks().get( 0, (byte) 0, (byte) ( y > 255 ? 15 : 0 ), (byte) 0, null, new Location( this, x, y, z ), layer.ordinal() );
        }

        return chunk.getBlockAt( x & 0xF, y, z & 0xF, layer.ordinal() );
    }

    /**
     * Set the block id for the given location
     *
     * @param position Position of the block
     * @param layer    on which we want to set the block
     * @param blockId  The new block id
     */
    public void setBlockId( BlockPosition position, int layer, int blockId ) {
        final ChunkAdapter chunk = this.loadChunk(
            CoordinateUtils.fromBlockToChunk( position.getX() ),
            CoordinateUtils.fromBlockToChunk( position.getZ() ),
            true );

        chunk.setBlock( position.getX() & 0xF, position.getY(), position.getZ() & 0xF, layer, blockId );
    }

    /**
     * Get the current id at the given location
     *
     * @param position where we want to search
     * @param layer    where the block is
     * @return id of the block
     */
    public int getBlockId( BlockPosition position, int layer ) {
        // Sanity check
        if ( position.getY() < 0 ) {
            this.logger.warn( "Got request for block under y 0", new Exception() );
            return 0;
        }

        final ChunkAdapter chunk = this.loadChunk(
            CoordinateUtils.fromBlockToChunk( position.getX() ),
            CoordinateUtils.fromBlockToChunk( position.getZ() ),
            true );

        return chunk.getBlock( position.getX() & 0xF, position.getY(), position.getZ() & 0xF, layer );
    }

    /**
     * Set the data byte for the given block
     *
     * @param position Position of the block
     * @param layer    where this block is
     * @param data     The new data of the block
     */
    public void setBlockData( BlockPosition position, int layer, byte data ) {
        int xChunk = CoordinateUtils.fromBlockToChunk( position.getX() );
        int zChunk = CoordinateUtils.fromBlockToChunk( position.getZ() );

        final ChunkAdapter chunk = this.loadChunk( xChunk, zChunk, true );
        chunk.setData( position.getX() & 0xF, position.getY(), position.getZ() & 0xF, layer, data );
    }

    /**
     * Get the biome of a specific block
     *
     * @param position which should be searched
     * @return biome of the block
     */
    public Biome getBiome( BlockPosition position ) {
        int xChunk = CoordinateUtils.fromBlockToChunk( position.getX() );
        int zChunk = CoordinateUtils.fromBlockToChunk( position.getZ() );

        final ChunkAdapter chunk = this.loadChunk( xChunk, zChunk, true );
        return chunk.getBiome( position.getX() & 0xF, position.getZ() & 0xF );
    }

    private void initGamerules() {
        this.setGamerule( Gamerule.DO_DAYLIGHT_CYCLE, false );
    }

    @Override
    public <T> void setGamerule( Gamerule<T> gamerule, T value ) {
        this.gamerules.put( gamerule, value );
    }

    @Override
    @SuppressWarnings( "unchecked" )
    public <T> T getGamerule( Gamerule<T> gamerule ) {
        return (T) this.gamerules.get( gamerule );
    }

    // ==================================== UPDATING ==================================== //

    /**
     * Ticks the world and updates what needs to be updated.
     *
     * @param currentTimeMS The current time in milliseconds. Used to reduce the number of calls to System#currentTimeMillis()
     * @param dT            The delta from the full second which has been calculated in the last tick
     */
    public void update( long currentTimeMS, float dT ) {
        // ---------------------------------------
        // Tick the chunk cache to get rid of Chunks
        if ( !this.config.isDisableChunkGC() ) {
            this.chunkCache.tick( currentTimeMS );
        }

        // ---------------------------------------
        // Update all blocks

        // Tick chunks (random blocks and tiles)
        this.tickChunks( currentTimeMS, dT, !this.config.isDisableRandomTicking() );

        // Scheduled blocks
        while ( this.tickQueue.getNextTaskTime() < currentTimeMS ) {
            BlockPosition blockToUpdate = this.tickQueue.getNextElement();
            if ( blockToUpdate == null ) {
                break;
            }

            // Get the block
            Block block = getOptionalBlockAt( blockToUpdate );
            if ( block != null ) {
                // CHECKSTYLE:OFF
                try {
                    io.gomint.server.world.block.Block block1 = (io.gomint.server.world.block.Block) block;
                    long next = block1.update( UpdateReason.SCHEDULED, currentTimeMS, dT );

                    // Reschedule if needed
                    if ( next > currentTimeMS ) {
                        this.tickQueue.add( next, blockToUpdate );
                    }
                } catch ( Exception e ) {
                    logger.error( "Error whilst ticking block @ " + blockToUpdate, e );
                }
                // CHECKSTYLE:ON
            }
        }

        // ---------------------------------------
        // Update all entities
        this.entityManager.update( currentTimeMS, dT );

        // ---------------------------------------
        // Chunk packages are done in main thread in order to be able to
        // cache packets without possibly getting into race conditions:
        while ( !this.chunkPackageTasks.isEmpty() ) {
            // One chunk per tick at max:
            AsyncChunkPackageTask task = this.chunkPackageTasks.poll();
            ChunkAdapter chunk = this.getChunk( task.getX(), task.getZ() );
            if ( chunk == null ) {
                chunk = this.loadChunk( task.getX(), task.getZ(), false );
            }

            if ( chunk != null ) {
                packageChunk( chunk, task.getCallback() );
            }
        }

        // ---------------------------------------
        // Perform regular updates:
    }

    private void tickChunks( long currentTimeMS, float dT, boolean tickRandomBlocks ) {
        long[] tickingHashes = this.chunkCache.getTickingChunks( dT );
        for ( long chunkHash : tickingHashes ) {
            ChunkAdapter chunkAdapter = this.chunkCache.getChunkInternal( chunkHash );
            if ( tickRandomBlocks ) {
                chunkAdapter.tickRandomBlocks( currentTimeMS, dT );
            }

            chunkAdapter.tickTiles( currentTimeMS, dT );
        }
    }

    // ==================================== ENTITY MANAGEMENT ==================================== //

    /**
     * Removes a player from this world and cleans up its references
     *
     * @param player The player entity which should be removed from the world
     */
    public void removePlayer( io.gomint.server.entity.EntityPlayer player ) {
        ChunkAdapter chunkAdapter = this.players.remove( player );
        if ( chunkAdapter != null ) {
            chunkAdapter.removePlayer( player );
        }

        this.entityManager.despawnEntity( player );
    }

    /**
     * Gets an entity given its unique ID.
     *
     * @param entityId The entity's unique ID
     * @return The entity if found or null otherwise
     */
    public Entity findEntity( long entityId ) {
        return this.entityManager.findEntity( entityId );
    }

    /**
     * Spawns the given entity at the specified position.
     *
     * @param entity The entity to spawn
     * @param vector The vector which contains the position of the spawn
     */
    public void spawnEntityAt( Entity entity, Vector vector ) {
        this.spawnEntityAt( entity, vector.getX(), vector.getY(), vector.getZ() );
    }

    /**
     * Spawns the given entity at the specified position.
     *
     * @param entity    The entity to spawn
     * @param positionX The x coordinate to spawn the entity at
     * @param positionY The y coordinate to spawn the entity at
     * @param positionZ The z coordinate to spawn the entity at
     */
    public void spawnEntityAt( Entity entity, float positionX, float positionY, float positionZ ) {
        this.entityManager.spawnEntityAt( entity, positionX, positionY, positionZ );
    }

    /**
     * Spawns the given entity at the specified position with the specified rotation.
     *
     * @param entity    The entity to spawn
     * @param positionX The x coordinate to spawn the entity at
     * @param positionY The y coordinate to spawn the entity at
     * @param positionZ The z coordinate to spawn the entity at
     * @param yaw       The yaw value of the entity ; will be applied to both the entity's body and head
     * @param pitch     The pitch value of the entity
     */
    public void spawnEntityAt( Entity entity, float positionX, float positionY, float positionZ, float yaw, float pitch ) {
        this.entityManager.spawnEntityAt( entity, positionX, positionY, positionZ, yaw, pitch );
    }

    // ==================================== CHUNK MANAGEMENT ==================================== //

    /**
     * Gets the chunk at the specified coordinates. If the chunk is currently not available
     * it will be loaded or generated.
     *
     * @param x The x-coordinate of the chunk
     * @param z The z-coordinate of the chunk
     * @return The chunk if available or null otherwise
     */
    public ChunkAdapter getChunk( int x, int z ) {
        return this.chunkCache.getChunk( x, z );
    }

    /**
     * Gets a chunk asynchronously. This allows to load or generate the chunk if it is not yet available
     * and then return it once it gets available. The callback is guaranteed to be invoked: if the chunk
     * could not be loaded nor be generated it will be passed null as its argument.
     *
     * @param x        The x-coordinate of the chunk
     * @param z        The z-coordinate of the chunk
     * @param generate Whether or not to generate teh chunk if it does not yet exist
     * @param callback The callback to be invoked once the chunk is available
     */
    public void getOrLoadChunk( int x, int z, boolean generate, Delegate<ChunkAdapter> callback ) {
        // Early out:
        ChunkAdapter chunk = this.chunkCache.getChunk( x, z );
        if ( chunk != null ) {
            callback.invoke( chunk );
            return;
        }

        // Check if we already have a task
        AsyncChunkLoadTask oldTask = this.findAsyncChunkLoadTask( x, z );
        if ( oldTask != null ) {
            this.logger.debug( "Found loader for chunk {} {}", x, z );

            // Set generating if needed
            if ( !oldTask.isGenerate() && generate ) {
                oldTask.setGenerate( true );
            }

            // Check for multi callback
            MultiOutputDelegate<ChunkAdapter> multiOutputDelegate;
            if ( oldTask.getCallback() instanceof MultiOutputDelegate ) {
                multiOutputDelegate = (MultiOutputDelegate<ChunkAdapter>) oldTask.getCallback();
                multiOutputDelegate.getOutputs().offer( callback );
            } else {
                Delegate<ChunkAdapter> delegate = oldTask.getCallback();
                multiOutputDelegate = new MultiOutputDelegate<>();
                multiOutputDelegate.getOutputs().offer( delegate );
                multiOutputDelegate.getOutputs().offer( callback );
                oldTask.setCallback( multiOutputDelegate );
            }

            return;
        }

        // Schedule this chunk for asynchronous loading:
        AsyncChunkLoadTask task = new AsyncChunkLoadTask( x, z, generate, callback );
        this.asyncChunkTasks.offer( task );
    }

    private AsyncChunkLoadTask findAsyncChunkLoadTask( int x, int z ) {
        for ( AsyncChunkTask task : this.asyncChunkTasks ) {
            if ( task instanceof AsyncChunkLoadTask ) {
                AsyncChunkLoadTask loadTask = (AsyncChunkLoadTask) task;
                if ( loadTask.getX() == x && loadTask.getZ() == z ) {
                    return loadTask;
                }
            }
        }

        return null;
    }

    /**
     * Send a chunk of this world to the client
     *
     * @param x            The x-coordinate of the chunk
     * @param z            The z-coordinate of the chunk
     * @param player       The player we want to send the chunk to
     * @param sync         Force sync chunk loading
     * @param sendDelegate delegate which may add the chunk to the send queue or add all entities on it to the send queue
     */
    public void sendChunk( int x, int z, io.gomint.server.entity.EntityPlayer player, boolean sync, Delegate2<Long, ChunkAdapter> sendDelegate ) {
        if ( !sync ) {
            this.getOrLoadChunk( x, z, true, chunk -> chunk.packageChunk( player, sendDelegate ) );
        } else {
            ChunkAdapter chunkAdapter = this.loadChunk( x, z, true );
            if ( chunkAdapter.dirty || chunkAdapter.cachedPacket == null || chunkAdapter.cachedPacket.get() == null ) {
                packageChunk( chunkAdapter, sendDelegate );
            } else {
                sendDelegate.invoke( CoordinateUtils.toLong( x, z ), chunkAdapter );
            }
        }
    }

    /**
     * Move a player to a new chunk. This is done so we know which player is in which chunk so we can unload unneeded
     * Chunks better and faster.
     *
     * @param x      The x-coordinate of the chunk
     * @param z      The z-coordinate of the chunk
     * @param player The player which should be set into the chunk
     */
    public void movePlayerToChunk( int x, int z, io.gomint.server.entity.EntityPlayer player ) {
        ChunkAdapter oldChunk = this.players.get( player );
        ChunkAdapter newChunk = this.loadChunk( x, z, true );

        if ( oldChunk == null ) {
            newChunk.addPlayer( player );
            this.players.put( player, newChunk );
        }

        if ( oldChunk != null && !oldChunk.equals( newChunk ) ) {
            oldChunk.removePlayer( player );
            newChunk.addPlayer( player );
            this.players.put( player, newChunk );
        }
    }

    /**
     * Prepares the region surrounding the world's spawn point.
     */
    protected void prepareSpawnRegion() {
        long start = System.currentTimeMillis();

        final int spawnRadius = this.config.getAmountOfChunksForSpawnArea();
        if ( spawnRadius == 0 ) {
            return;
        }

        final int chunkX = CoordinateUtils.fromBlockToChunk( (int) this.spawn.getX() );
        final int chunkZ = CoordinateUtils.fromBlockToChunk( (int) this.spawn.getZ() );

        int amountOfChunksLoaded = 0;
        for ( int i = chunkX - spawnRadius; i <= chunkX + spawnRadius; i++ ) {
            for ( int j = chunkZ - spawnRadius; j <= chunkZ + spawnRadius; j++ ) {
                this.loadChunk( i, j, true );
                amountOfChunksLoaded++;
            }
        }

        this.logger.info( "Loaded {} chunks in {} ms", amountOfChunksLoaded, ( System.currentTimeMillis() - start ) );
    }

    /**
     * Load a Chunk from the underlying implementation
     *
     * @param x        The x coordinate of the chunk we want to load
     * @param z        The x coordinate of the chunk we want to load
     * @param generate A boolean which decides whether or not the chunk should be generated when not found
     * @return The loaded or generated Chunk
     */
    public abstract ChunkAdapter loadChunk( int x, int z, boolean generate );

    /**
     * Saves the given chunk to its respective region file. The respective region file
     * is created automatically if it does not yet exist.
     *
     * @param chunk The chunk to be saved
     */
    protected abstract void saveChunk( ChunkAdapter chunk );

    /**
     * Saves the given chunk to its region file asynchronously.
     *
     * @param chunk The chunk to save
     */
    void saveChunkAsynchronously( ChunkAdapter chunk ) {
        AsyncChunkSaveTask task = new AsyncChunkSaveTask( chunk );
        this.asyncChunkTasks.offer( task );
    }

    /**
     * Notifies the world that the given chunk was told to package itself. This will effectively
     * produce an asynchronous chunk task which will be completed by the asynchronous worker thread.
     *
     * @param x        The x coordinate of the chunk we want to package
     * @param z        The z coordinate of the chunk we want to package
     * @param callback The callback to be invoked once the chunk is packaged
     */
    void notifyPackageChunk( int x, int z, Delegate2<Long, ChunkAdapter> callback ) {
        AsyncChunkPackageTask task = new AsyncChunkPackageTask( x, z, callback );
        this.chunkPackageTasks.add( task );
    }

    /**
     * Package a Chunk into a ChunkData Packet for Raknet. This is done to enable caching of those packets.
     *
     * @param chunk    The chunk which should be packed
     * @param callback The callback which should be invoked when the packing has been done
     */
    private void packageChunk( ChunkAdapter chunk, Delegate2<Long, ChunkAdapter> callback ) {
        PacketWorldChunk packet = chunk.createPackagedData( Protocol.MINECRAFT_PE_PROTOCOL_VERSION );
        chunk.setCachedPacket( packet );
        callback.invoke( CoordinateUtils.toLong( chunk.getX(), chunk.getZ() ), chunk );
    }

    // ==================================== NETWORKING HELPERS ==================================== //

    /**
     * Send a packet to all players which can see the position
     *
     * @param position  where the packet will have its impact
     * @param packet    which should be sent
     * @param predicate which decides over each entity if they will get the packet sent or not
     */
    public void sendToVisible( BlockPosition position, Packet packet, Predicate<Entity> predicate ) {
        int posX = CoordinateUtils.fromBlockToChunk( position.getX() );
        int posZ = CoordinateUtils.fromBlockToChunk( position.getZ() );

        for ( EntityPlayer player : this.getPlayers() ) {
            Location location = player.getLocation();
            int currentX = CoordinateUtils.fromBlockToChunk( (int) location.getX() );
            int currentZ = CoordinateUtils.fromBlockToChunk( (int) location.getZ() );

            if ( Math.abs( posX - currentX ) <= player.getViewDistance() &&
                Math.abs( posZ - currentZ ) <= player.getViewDistance() &&
                predicate.test( player ) ) {
                ( (io.gomint.server.entity.EntityPlayer) player ).getConnection().addToSendQueue( packet );
            }
        }
    }

    // ==================================== ASYNCHRONOUS WORKER ==================================== //

    /**
     * Starts the asynchronous worker thread used by the world to perform I/O operations for chunks.
     */
    private void startAsyncWorker( ExecutorService executorService ) {
        this.asyncWorkerRunning = new AtomicBoolean( true );

        executorService.execute( () -> {
            Thread.currentThread().setName( Thread.currentThread().getName() + " [Async World I/O: " + WorldAdapter.this.getWorldName() + "]" );
            WorldAdapter.this.asyncWorkerThread = Thread.currentThread();
            WorldAdapter.this.asyncWorkerLoop();
        } );
    }

    /**
     * Main loop of the world's asynchronous worker thread.
     */
    private void asyncWorkerLoop() {
        while ( this.asyncWorkerRunning.get() ) {
            try {
                AsyncChunkTask task = this.asyncChunkTasks.take();

                ChunkAdapter chunk;
                switch ( task.getType() ) {
                    case LOAD:
                        AsyncChunkLoadTask load = (AsyncChunkLoadTask) task;
                        chunk = this.loadChunk( load.getX(), load.getZ(), load.isGenerate() );

                        load.getCallback().invoke( chunk );
                        break;

                    case SAVE:
                        AsyncChunkSaveTask save = (AsyncChunkSaveTask) task;
                        chunk = save.getChunk();
                        this.saveChunk( chunk );
                        break;

                    case POPULATE:
                        AsyncChunkPopulateTask populateTask = (AsyncChunkPopulateTask) task;

                        if ( !populateTask.getChunk().isPopulated() ) {
                            this.chunkGenerator.populate( populateTask.getChunk() );
                            populateTask.getChunk().calculateHeightmap( 240 );
                            populateTask.getChunk().setPopulated( true );
                        }

                        break;

                    default:
                        // Log some error when this happens

                        break;
                }
            } catch ( InterruptedException interrupted ) {
                return;
            } catch ( Throwable cause ) {
                // Catching throwable in order to make sure no uncaught exceptions puts
                // the asynchronous worker into nirvana:
                logger.error( "Error whilst doing async work: ", cause );
            }
        }
    }

    /**
     * Send the block given under the position to all players in the chunk of the block
     *
     * @param pos The position of the block to update
     */
    public void updateBlock( BlockPosition pos ) {
        // Players can't see unpopulated chunks
        ChunkAdapter adapter = this.getChunk( pos.getX() >> 4, pos.getZ() >> 4 );
        if ( !adapter.isPopulated() ) {
            return;
        }

        if ( !GoMint.instance().isMainThread() ) {
            this.server.addToMainThread( () -> {
                flagChunkDirty( pos );

                sendToVisible( pos, null, entity -> {
                    if ( entity instanceof io.gomint.server.entity.EntityPlayer ) {
                        ( (io.gomint.server.entity.EntityPlayer) entity ).getBlockUpdates().add( pos );
                    }

                    return false;
                } );
            } );
        } else {
            flagChunkDirty( pos );

            sendToVisible( pos, null, entity -> {
                if ( entity instanceof io.gomint.server.entity.EntityPlayer ) {
                    ( (io.gomint.server.entity.EntityPlayer) entity ).getBlockUpdates().add( pos );
                }

                return false;
            } );
        }
    }

    private void flagChunkDirty( BlockPosition position ) {
        int posX = CoordinateUtils.fromBlockToChunk( position.getX() );
        int posZ = CoordinateUtils.fromBlockToChunk( position.getZ() );

        ChunkAdapter chunkAdapter = getChunk( posX, posZ );
        if ( chunkAdapter != null ) {
            chunkAdapter.dirty = true;
        }
    }

    /**
     * Append all packages needed to update a specific block
     *
     * @param connection which should get the packets
     * @param pos of the block which should be updated
     */
    public void appendUpdatePackets( PlayerConnection connection, BlockPosition pos ) {
        io.gomint.server.world.block.Block block = getBlockAt( pos );

        // Update the block
        PacketUpdateBlock updateBlock = new PacketUpdateBlock();
        updateBlock.setPosition( pos );

        updateBlock.setBlockId( BlockRuntimeIDs.fromLegacy( block.getBlockId(), block.getBlockData(), connection.getProtocolID() ) );
        updateBlock.setFlags( PacketUpdateBlock.FLAG_ALL );

        connection.addToSendQueue( updateBlock );

        // Check for tile entity
        if ( block.getTileEntity() != null ) {
            PacketTileEntityData tileEntityData = new PacketTileEntityData();
            tileEntityData.setPosition( pos );
            tileEntityData.setTileEntity( block.getTileEntity() );
            connection.addToSendQueue( tileEntityData );
        }
    }

    /**
     * Get the amount of players online on this world
     *
     * @return amount of players online on this world
     */
    public int getAmountOfPlayers() {
        return players.size();
    }

    /**
     * Get all entities which touch or are inside this bounding box
     *
     * @param bb        the bounding box which should be used to collect entities in
     * @param exception a entity which should not be included in the list
     * @param function  which gets called for getting the correct bounding box for this check
     * @return either null if there are no entities or a collection of entities
     */
    public Collection<Entity> getNearbyEntities( AxisAlignedBB bb, Entity exception, Function<Entity, AxisAlignedBB> function ) {
        final Set[] nearby = new HashSet[1];
        final Consumer<Entity> consumer = new Consumer<Entity>() {
            @Override
            public void accept( Entity entity ) {
                if ( !entity.equals( exception ) ) {
                    AxisAlignedBB entityBB = function == null ? entity.getBoundingBox() : function.apply( entity );
                    if ( entityBB.intersectsWith( bb ) ) {
                        if ( nearby[0] == null ) {
                            nearby[0] = new HashSet<>();
                        }

                        nearby[0].add( entity );
                    }
                }
            }
        };

        int minX = MathUtils.fastFloor( ( bb.getMinX() - 2 ) / 16 );
        int maxX = MathUtils.fastCeil( ( bb.getMaxX() + 2 ) / 16 );
        int minZ = MathUtils.fastFloor( ( bb.getMinZ() - 2 ) / 16 );
        int maxZ = MathUtils.fastCeil( ( bb.getMaxZ() + 2 ) / 16 );

        for ( int x = minX; x <= maxX; ++x ) {
            for ( int z = minZ; z <= maxZ; ++z ) {
                Chunk chunk = this.getChunk( x, z );
                if ( chunk != null ) {
                    chunk.iterateEntities( Entity.class, consumer );
                }
            }
        }

        return nearby[0];
    }

    private <T> List<T> iterateBlocks( int minX, int maxX, int minY, int maxY, int minZ, int maxZ, AxisAlignedBB
        bb, boolean returnBoundingBoxes, boolean includePassThrough ) {
        List values = null;

        for ( int z = minZ; z < maxZ; ++z ) {
            for ( int x = minX; x < maxX; ++x ) {
                for ( int y = minY; y < maxY; ++y ) {
                    Block block = this.getBlockAt( x, y, z );

                    if ( ( !block.canPassThrough() || includePassThrough ) && block.intersectsWith( bb ) ) {
                        if ( values == null ) {
                            values = new ArrayList<>();
                        }

                        if ( returnBoundingBoxes ) {
                            values.addAll( block.getBoundingBox() );
                        } else {
                            values.add( block );
                        }
                    }
                }
            }
        }

        return values;
    }

    /**
     * Get blocks which collide with the given entity.
     *
     * @param entity             which is used to check for block collisions
     * @param includePassThrough if the result should also include blocks which can normally be passed through
     * @return list of blocks with which the entity collides, or null when no block has been found
     */
    public List<Block> getCollisionBlocks( io.gomint.entity.Entity entity, boolean includePassThrough ) {
        AxisAlignedBB bb = entity.getBoundingBox().grow( 0.1f, 0.01f, 0.1f );

        int minX = MathUtils.fastFloor( bb.getMinX() );
        int minY = MathUtils.fastFloor( bb.getMinY() );
        int minZ = MathUtils.fastFloor( bb.getMinZ() );
        int maxX = MathUtils.fastCeil( bb.getMaxX() );
        int maxY = MathUtils.fastCeil( bb.getMaxY() );
        int maxZ = MathUtils.fastCeil( bb.getMaxZ() );

        return iterateBlocks( minX, maxX, minY, maxY, minZ, maxZ, bb, false, includePassThrough );
    }

    @Override
    public List<AxisAlignedBB> getCollisionCubes( io.gomint.entity.Entity entity, AxisAlignedBB bb,
                                                  boolean includeEntities ) {
        int minX = MathUtils.fastFloor( bb.getMinX() );
        int minY = MathUtils.fastFloor( bb.getMinY() );
        int minZ = MathUtils.fastFloor( bb.getMinZ() );
        int maxX = MathUtils.fastCeil( bb.getMaxX() );
        int maxY = MathUtils.fastCeil( bb.getMaxY() );
        int maxZ = MathUtils.fastCeil( bb.getMaxZ() );

        List<AxisAlignedBB> collisions = iterateBlocks( minX, maxX, minY, maxY, minZ, maxZ, bb, true, false );

        if ( includeEntities ) {
            Collection<io.gomint.entity.Entity> entities = getNearbyEntities( bb.grow( 0.25f, 0.25f, 0.25f ), entity, null );
            if ( entities != null ) {
                for ( io.gomint.entity.Entity entity1 : entities ) {
                    if ( collisions == null ) {
                        collisions = new ArrayList<>();
                    }

                    collisions.add( entity1.getBoundingBox() );
                }
            }
        }

        return collisions;
    }

    /**
     * Use a item on a block to interact / place it down
     *
     * @param itemInHand    of the player which wants to interact
     * @param blockPosition on which we want to use the item
     * @param face          on which we interact
     * @param clickPosition the exact position on the block we interact with
     * @param entity        which interacts with the block
     * @return true when interaction was successful, false when not
     */
    public boolean useItemOn( ItemStack itemInHand, BlockPosition blockPosition, BlockFace face, Vector
        clickPosition, io.gomint.server.entity.EntityPlayer entity ) {
        Block blockClicked = this.getBlockAt( blockPosition );
        if ( blockClicked instanceof Air ) {
            return false;
        }

        PlayerInteractEvent interactEvent = new PlayerInteractEvent( entity, PlayerInteractEvent.ClickType.RIGHT, blockClicked );
        this.server.getPluginManager().callEvent( interactEvent );

        if ( interactEvent.isCancelled() ) {
            return false;
        }

        // TODO: Event stuff and spawn protection / Adventure gamemode

        io.gomint.server.world.block.Block clickedBlock = (io.gomint.server.world.block.Block) blockClicked;
        boolean interacted = false;
        if ( !entity.isSneaking() ) {
            interacted = clickedBlock.interact( entity, face, clickPosition, itemInHand );
        }

        // Let the item interact
        boolean itemInteracted = ( (io.gomint.server.inventory.item.ItemStack) itemInHand )
            .interact( entity, face, clickPosition, clickedBlock );

        if ( ( !interacted && !itemInteracted ) || entity.isSneaking() ) {
            boolean canBePlaced = ( (io.gomint.server.inventory.item.ItemStack) itemInHand ).getBlockId() < 256 && !( itemInHand instanceof ItemAir );
            if ( canBePlaced ) {
                Block blockReplace = blockClicked.getSide( face );
                io.gomint.server.world.block.Block replaceBlock = (io.gomint.server.world.block.Block) blockReplace;

                if ( clickedBlock.canBeReplaced( itemInHand ) ) {
                    replaceBlock = clickedBlock;
                } else if ( !replaceBlock.canBeReplaced( itemInHand ) ) {
                    return false;
                }

                // We got the block we want to replace
                // Let the item build up the block
                boolean success = this.server.getBlocks().replaceWithItem( entity, clickedBlock, replaceBlock, itemInHand, clickPosition );
                if ( success ) {
                    // Play sound
                    io.gomint.server.world.block.Block newBlock = replaceBlock.getLocation().getWorld().getBlockAt( replaceBlock.getLocation().toBlockPosition() );
                    playSound( null, newBlock.getLocation(), Sound.PLACE, (byte) 1, BlockRuntimeIDs.fromLegacy( newBlock.getBlockId(), (byte) 0, entity.getConnection().getProtocolID() ) );

                    // Schedule neighbour updates
                    scheduleNeighbourUpdates( newBlock );

                    if ( entity.getGamemode() != Gamemode.CREATIVE ) {
                        ( (io.gomint.server.inventory.item.ItemStack) itemInHand ).afterPlacement();
                    }
                }

                return success;
            }
        }

        return interacted || itemInteracted;
    }

    private void scheduleNeighbourUpdates( Block block ) {
        io.gomint.server.world.block.Block implBlock = (io.gomint.server.world.block.Block) block;
        for ( BlockFace face : BlockFace.values() ) {
            io.gomint.server.world.block.Block neighbourBlock = implBlock.getSide( face );

            // CHECKSTYLE:OFF
            try {
                long next = neighbourBlock.update( UpdateReason.NEIGHBOUR_UPDATE, this.server.getCurrentTickTime(), 0f );
                if ( next > this.server.getCurrentTickTime() ) {
                    BlockPosition position = neighbourBlock.getLocation().toBlockPosition();
                    this.tickQueue.add( next, position );
                }
            } catch ( Exception e ) {
                this.logger.error( "Exception while updating block @ {}", neighbourBlock.getLocation(), e );
            }
            // CHECKSTYLE:ON
        }
    }

    public EntityItem createItemDrop( Location location, ItemStack item ) {
        EntityItem entityItem = new EntityItem( item, this );
        spawnEntityAt( entityItem, location );
        return entityItem;
    }

    public void close() {
        // Stop async worker
        this.asyncWorkerRunning.set( false );

        // Wait until the thread is done
        this.asyncWorkerThread.interrupt();
    }

    public TemporaryStorage getTemporaryBlockStorage( BlockPosition position, int layer ) {
        // Get chunk
        ChunkAdapter chunk = this.loadChunk( position.getX() >> 4, position.getZ() >> 4, true );
        return chunk.getTemporaryStorage( position.getX() & 0xF, position.getY(), position.getZ() & 0xF, layer );
    }

    public ChunkAdapter generate( int x, int z ) {
        if ( this.chunkGenerator != null ) {
            ChunkAdapter chunk = (ChunkAdapter) this.chunkGenerator.generate( x, z );
            if ( chunk != null ) {
                chunk.calculateHeightmap( 240 );
                this.chunkCache.putChunk( chunk );
                this.addPopulateTask( chunk );
                return chunk;
            }
        }

        return null;
    }

    public void addPopulateTask( ChunkAdapter chunk ) {
        this.asyncChunkTasks.offer( new AsyncChunkPopulateTask( chunk ) );
    }

    public void sendLevelEvent( Vector position, int levelEvent, int data ) {
        this.sendLevelEvent( null, position, levelEvent, data );
    }

    public void sendLevelEvent( EntityPlayer player, Vector position, int levelEvent, int data ) {
        PacketWorldEvent worldEvent = new PacketWorldEvent();
        worldEvent.setData( data );
        worldEvent.setEventId( levelEvent );
        worldEvent.setPosition( position );

        if ( player != null ) {
            ( (io.gomint.server.entity.EntityPlayer) player ).getConnection().addToSendQueue( worldEvent );
        } else {
            sendToVisible( position.toBlockPosition(), worldEvent, entity -> true );
        }
    }

    public void storeTileEntity( BlockPosition position, TileEntity tileEntity ) {
        // Get chunk
        ChunkAdapter chunk = this.loadChunk( position.getX() >> 4, position.getZ() >> 4, true );
        chunk.setTileEntity( position.getX() & 0xF, position.getY(), position.getZ() & 0xF, tileEntity );
    }

    public boolean breakBlock( BlockPosition position, List<ItemStack> drops, boolean creative ) {
        io.gomint.server.world.block.Block block = getBlockAt( position );
        if ( block.onBreak( creative ) ) {
            if ( !drops.isEmpty() ) {
                for ( ItemStack itemStack : drops ) {
                    EntityItem item = this.createItemDrop( block.getLocation().add( 0.5f, 0.5f, 0.5f ), itemStack );
                    item.setVelocity( new Vector( FastRandom.current().nextFloat() * 0.2f - 0.1f, 0.2f, FastRandom.current().nextFloat() * 0.2f - 0.1f ) );
                }
            }

            // Break animation (this also plays the break sound in the client)
            sendLevelEvent( position.toVector().add( .5f, .5f, .5f ), LevelEvent.PARTICLE_DESTROY, BlockRuntimeIDs.fromLegacy( block.getBlockId(), block.getBlockData(), Protocol.MINECRAFT_PE_PROTOCOL_VERSION ) );

            block.setType( BlockAir.class );

            return true;
        } else {
            return false;
        }
    }

    public void resetTemporaryStorage( BlockPosition position, int layer ) {
        // Get chunk
        int x = position.getX(), y = position.getY(), z = position.getZ();
        int xChunk = CoordinateUtils.fromBlockToChunk( x );
        int zChunk = CoordinateUtils.fromBlockToChunk( z );

        ChunkAdapter chunk = this.loadChunk( xChunk, zChunk, true );
        chunk.resetTemporaryStorage( x & 0xF, y, z & 0xF, layer );
    }

    public void scheduleBlockUpdate( Location location, long delay, TimeUnit unit ) {
        BlockPosition position = location.toBlockPosition();
        long key = this.server.getCurrentTickTime() + unit.toMillis( delay );
        this.tickQueue.add( key, position );
    }

    public void dropItem( Location location, ItemStack drop ) {
        Vector motion = new Vector( FastRandom.current().nextFloat() * 0.2f - 0.1f,
            0.2f, FastRandom.current().nextFloat() * 0.2f - 0.1f );

        EntityItem item = this.createItemDrop( location, drop );
        item.setVelocity( motion );
    }

    @Override
    public void sendParticle( Vector location, Particle particle ) {
        this.sendParticle( null, location, particle, 0 );
    }

    public void sendParticle( EntityPlayer player, Vector location, Particle particle, int data ) {
        int eventId;
        switch ( particle ) {
            case PUNCH_BLOCK:
                eventId = LevelEvent.PARTICLE_PUNCH_BLOCK;
                break;
            case BREAK_BLOCK:
                eventId = LevelEvent.PARTICLE_DESTROY;
                break;
            default:
                eventId = LevelEvent.ADD_PARTICLE_MASK | EnumConnectors.PARTICLE_CONNECTOR.convert( particle ).getId();
        }

        sendLevelEvent( player, location, eventId, data );
    }

    @Override
    public void sendParticle( Vector location, Particle particle, ParticleData data ) {
        this.sendParticle( null, location, particle, data );
    }

    public void sendParticle( EntityPlayer player, Vector location, Particle particle, ParticleData data ) {
        int dataNumber = 0;

        switch ( particle ) {
            case FALLING_DUST:
                if ( data.getRed() == -1 || data.getBlue() == -1 || data.getGreen() == -1 || data.getAlpha() == -1 ) {
                    throw new IllegalArgumentException( "Particle data does not reflect color for particle " + particle );
                }

                dataNumber = ( ( data.getAlpha() & 0xff ) << 24 ) |
                    ( ( data.getRed() & 0xff ) << 16 ) |
                    ( ( data.getGreen() & 0xff ) << 8 ) |
                    ( data.getBlue() & 0xff );

                break;

            case PUNCH_BLOCK:
                if ( data.getBlock() == null || data.getFace() == -1 ) {
                    throw new IllegalArgumentException( "Particle data does not reflect block and face data for particle " + particle );
                }

                io.gomint.server.world.block.Block block = (io.gomint.server.world.block.Block) data.getBlock();
                dataNumber = BlockRuntimeIDs.fromLegacy( block.getBlockId(), block.getBlockData(),
                    player != null ? ( (io.gomint.server.entity.EntityPlayer) player ).getConnection().getProtocolID() : Protocol.MINECRAFT_PE_PROTOCOL_VERSION ) | ( data.getFace() << 24 );

                break;

            case BREAK_BLOCK:
                if ( data.getBlock() == null ) {
                    throw new IllegalArgumentException( "Particle data does not reflect block data for particle " + particle );
                }

                block = (io.gomint.server.world.block.Block) data.getBlock();
                dataNumber = BlockRuntimeIDs.fromLegacy( block.getBlockId(), block.getBlockData(),
                    player != null ? ( (io.gomint.server.entity.EntityPlayer) player ).getConnection().getProtocolID() : Protocol.MINECRAFT_PE_PROTOCOL_VERSION );

                break;
        }

        this.sendParticle( player, location, particle, dataNumber );
    }

    public void createExpOrb( Location location, int amount ) {
        EntityXPOrb xpOrb = new EntityXPOrb( (WorldAdapter) location.getWorld(), amount );
        spawnEntityAt( xpOrb, location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch() );
    }

    public void removeEntity( io.gomint.server.entity.Entity entity ) {
        // Just tell the entity manager, it will handle the rest
        this.entityManager.despawnEntity( entity );
    }

    @Override
    public void unload( Consumer<EntityPlayer> playerConsumer ) {
        if ( !GoMint.instance().isMainThread() ) {
            this.logger.warn( "Unloading worlds from an async thread. This is not safe and can lead to CME" );
        }

        // Unload all players via API
        if ( playerConsumer != null ) {
            Set<EntityPlayer> playerCopy = new HashSet<>( this.players.keySet() );
            playerCopy.forEach( playerConsumer );
        }

        // Stop this world
        this.close();

        if ( this.config.isSaveOnUnload() ) {
            // Save this world
            this.chunkCache.saveAll();
        }

        // Drop all FDs
        this.closeFDs();

        // Remove world from manager
        this.server.getWorldManager().unloadWorld( this );
    }

    protected abstract void closeFDs();

    @Override
    public <T extends Block> void iterateBlocks( Class<T> blockClass, Consumer<T> blockConsumer ) {
        // Get the id of the block which we search
        int blockId = this.server.getBlocks().getID( blockClass );

        // Iterate over all chunks
        this.chunkCache.iterateAll( chunkAdapter -> {
            int chunkZ = chunkAdapter.getZ();
            int chunkX = chunkAdapter.getX();

            for ( int i = 0; i < 2; i++ ) {
                for ( int x = 0; x < 16; x++ ) {
                    for ( int y = 0; y < 256; y++ ) {
                        for ( int z = 0; z < 16; z++ ) {
                            int currentBlockId = chunkAdapter.getBlock( x, y, z, i );
                            if ( currentBlockId == blockId ) {
                                T block = getBlockAt( ( chunkX << 4 ) + x, y, ( chunkZ << 4 ) + z );
                                blockConsumer.accept( block );
                            }
                        }
                    }
                }
            }

        } );
    }

    @Override
    public <T extends Entity> void iterateEntities( Class<T> entityClass, Consumer<T> entityConsumer ) {
        // Iterate over all chunks
        this.chunkCache.iterateAll( chunkAdapter -> chunkAdapter.iterateEntities( entityClass, entityConsumer ) );
    }

    /**
     * Adjust the spawn level to the first in air block
     */
    protected void adjustSpawn() {
        BlockPosition check = new BlockPosition( (int) this.spawn.getX(), 0, (int) this.spawn.getZ() );
        for ( int i = 255; i > 0; i-- ) {
            check.setY( i );
            if ( this.getBlockId( check, 0 ) != 0 ) {
                this.spawn.setY( 1 + i );
                break;
            }
        }
    }

    public void constructGenerator( Class<? extends ChunkGenerator> generator, GeneratorContext context ) throws
        WorldCreateException {
        try {
            this.chunkGenerator = generator.getConstructor( World.class, GeneratorContext.class ).newInstance( this, context );
        } catch ( NoSuchMethodException e ) {
            throw new WorldCreateException( "The given generator does not provide a (World, GeneratorContext) constructor" );
        } catch ( IllegalAccessException e ) {
            throw new WorldCreateException( "The given generator can't be constructed. Be sure the (World, GeneratorContext) constructor is public" );
        } catch ( InstantiationException e ) {
            throw new WorldCreateException( "The generator given is either an abstracted class or some kind of interface" );
        } catch ( InvocationTargetException e ) {
            throw new WorldCreateException( "The constructor of the generator has thrown this exception", e );
        }
    }

    public void addTickingBlock( long time, BlockPosition position ) {
        this.tickQueue.add( time, position );
    }

    public boolean isUpdateScheduled( BlockPosition position ) {
        return this.tickQueue.contains( position );
    }

    @Override
    public void save() {
        this.chunkCache.saveAll();
    }

    public void registerEntitiesFromChunk( ChunkAdapter chunk ) {
        Long2ObjectMap<Entity> entities = chunk.getEntities();
        if ( entities != null && !entities.isEmpty() ) {
            this.entityManager.addFromChunk( entities );
        }
    }

    public void flagNeedsPersistance( BlockPosition pos ) {
        int xChunk = CoordinateUtils.fromBlockToChunk( pos.getX() );
        int zChunk = CoordinateUtils.fromBlockToChunk( pos.getZ() );

        ChunkAdapter chunk = this.loadChunk( xChunk, zChunk, true );
        chunk.flagNeedsPersistance();
    }

    public ChunkCache getChunkCache() {
        return this.chunkCache;
    }

    /**
     * Gets called when a player gets switched to this world
     *
     * @param player which has been switched to this world
     */
    public void playerSwitched( io.gomint.server.entity.EntityPlayer player ) {
        // Set difficulty
        PacketSetDifficulty packetSetDifficulty = new PacketSetDifficulty();
        packetSetDifficulty.setDifficulty( this.difficulty.getDifficultyDegree() );
        player.getConnection().addToSendQueue( packetSetDifficulty );
    }

    public abstract boolean persistPlayer( io.gomint.server.entity.EntityPlayer player );

    @Override
    public Block getHighestBlockAt( int x, int z ) {
        ChunkAdapter chunk = this.loadChunk( x >> 4, z >> 4, true );
        int y = chunk.getHeight( x & 0xF, z & 0xF );
        return chunk.getBlockAt( x & 0xF, y, z & 0xF );
    }

    @Override
    public Block getHighestBlockAt( int x, int z, WorldLayer layer ) {
        ChunkAdapter chunk = this.loadChunk( x >> 4, z >> 4, true );
        int y = chunk.getHeight( x & 0xF, z & 0xF );
        return chunk.getBlockAt( x & 0xF, y, z & 0xF, layer );
    }

}
