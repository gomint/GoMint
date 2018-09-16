package io.gomint.server.world.converter;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.leveldb.DB;
import io.gomint.leveldb.NativeLoader;
import io.gomint.leveldb.WriteBatch;
import io.gomint.math.MathUtils;
import io.gomint.server.entity.tileentity.SerializationReason;
import io.gomint.server.entity.tileentity.TileEntity;
import io.gomint.server.util.Allocator;
import io.gomint.server.util.BlockIdentifier;
import io.gomint.server.util.Palette;
import io.gomint.server.world.leveldb.LevelDBWorldAdapter;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.taglib.NBTWriter;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
public class BaseConverter {

    private static final Logger LOGGER = LoggerFactory.getLogger( BaseConverter.class );

    static {
        NativeLoader.load();
    }

    protected File worldFolder;

    private DB db;
    private ThreadLocal<WriteBatch> writeBatch = new ThreadLocal<>();
    private ThreadLocal<PacketBuffer> buffer = new ThreadLocal<>();

    public BaseConverter( File worldFolder ) {
        this.worldFolder = worldFolder;

        // Create new leveldb
        this.db = new DB( new File( worldFolder, "db" ) );
        this.db.open();
    }

    private ByteBuf getKey( int chunkX, int chunkZ, byte dataType ) {
        ByteBuf buf = PooledByteBufAllocator.DEFAULT.directBuffer( 9 );
        return buf.writeIntLE( chunkX ).writeIntLE( chunkZ ).writeByte( dataType );
    }

    private ByteBuf getKeySubChunk( int chunkX, int chunkZ, byte dataType, byte subChunk ) {
        ByteBuf buf = PooledByteBufAllocator.DEFAULT.directBuffer( 10 );
        return buf.writeIntLE( chunkX ).writeIntLE( chunkZ ).writeByte( dataType ).writeByte( subChunk );
    }

    private PacketBuffer getBuffer() {
        PacketBuffer buffer = this.buffer.get();
        if ( buffer == null ) {
            buffer = new PacketBuffer( 2048 );
            this.buffer.set( buffer );
        }

        return buffer;
    }

    private WriteBatch getWriteBatch() {
        WriteBatch batch = this.writeBatch.get();
        if ( batch == null ) {
            batch = new WriteBatch();
            this.writeBatch.set( batch );
        }

        return batch;
    }

    protected void startChunk( int chunkX, int chunkZ ) {
        WriteBatch batch = this.getWriteBatch();

        ByteBuf keyA = this.getKey( chunkX, chunkZ, (byte) 0x76 );
        ByteBuf keyB = this.getKey( chunkX, chunkZ, (byte) 0x36 );

        ByteBuf valA = Allocator.allocate( new byte[]{ 7 } );
        ByteBuf valB = Allocator.allocate( new byte[]{ 2, 0, 0, 0 } );

        batch.put( keyA, valA );
        batch.put( keyB, valB );
    }

    protected void storeSubChunkBlocks( int sectionY, int chunkX, int chunkZ, BlockIdentifier[] blocks ) {
        // Count how many unique blocks we have in this chunk
        int[] indexIDs = new int[4096];

        LongList indexList = new LongArrayList();
        IntList runtimeIndex = new IntArrayList();
        Int2ObjectMap<BlockIdentifier> block = new Int2ObjectOpenHashMap<>();

        int foundIndex = 0;

        String lastBlockId = "";
        short lastDataId = -1;

        int runtimeIdCounter = 0;

        for ( short blockIndex = 0; blockIndex < indexIDs.length; blockIndex++ ) {
            String blockId = blocks[blockIndex].getBlockId();
            short blockData = blocks[blockIndex].getData();

            if ( !blockId.equals( lastBlockId ) || blockData != lastDataId ) {
                long hashId = ( (long) blockId.hashCode() ) << 32 | ( blockData & 0xFF );

                foundIndex = indexList.indexOf( hashId );
                if ( foundIndex == -1 ) {
                    int runtimeId = runtimeIdCounter++;
                    block.put( runtimeId, blocks[blockIndex] );
                    runtimeIndex.add( runtimeId );
                    indexList.add( hashId );
                    foundIndex = indexList.size() - 1;
                }

                lastBlockId = blockId;
                lastDataId = blockData;
            }

            indexIDs[blockIndex] = foundIndex;
        }

        // Get correct wordsize
        int value = indexList.size();
        int numberOfBits = MathUtils.fastFloor( MathUtils.log2( value ) ) + 1;

        // Prepare palette
        int amountOfBlocks = MathUtils.fastFloor( 32f / (float) numberOfBits );

        PacketBuffer buffer = this.getBuffer();
        buffer.writeByte( (byte) 8 );
        buffer.writeByte( (byte) 1 );

        Palette palette = new Palette( buffer, amountOfBlocks, false );

        byte paletteWord = (byte) ( (byte) ( palette.getPaletteVersion().getVersionId() << 1 ) | 1 );
        buffer.writeByte( paletteWord );
        palette.addIndexIDs( indexIDs );
        palette.finish();

        // Write persistant ids
        buffer.writeLInt( indexList.size() );
        for ( int value1 : runtimeIndex.toArray( new int[0] ) ) {
            BlockIdentifier blockIdentifier = block.get( value1 );

            NBTTagCompound compound = new NBTTagCompound( "" );
            compound.addValue( "name", blockIdentifier.getBlockId() );
            compound.addValue( "val", blockIdentifier.getData() );

            try {
                compound.writeTo( new OutputStream() {
                    @Override
                    public void write( byte[] b, int off, int len ) throws IOException {
                        byte[] finalBytes = new byte[len];
                        System.arraycopy( b, off, finalBytes, 0, len );
                        buffer.writeBytes( finalBytes );
                    }

                    @Override
                    public void write( int b ) throws IOException {
                        buffer.writeByte( (byte) b );
                    }
                }, false, ByteOrder.LITTLE_ENDIAN );
            } catch ( IOException e ) {
                LOGGER.warn( "Could not write nbt runtime", e );
            }
        }

        ByteBuf key = this.getKeySubChunk( chunkX, chunkZ, (byte) 0x2f, (byte) sectionY );
        ByteBuf val = Allocator.allocate( Arrays.copyOf( buffer.getBuffer(), buffer.getPosition() ) );

        this.getWriteBatch().put( key, val );

        buffer.setPosition( 0 );
    }

    protected void storeTileEntities( int chunkX, int chunkZ, List<TileEntity> newTileEntities ) {
        WriteBatch batch = this.getWriteBatch();

        // Safe tiles
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        NBTWriter nbtWriter = new NBTWriter( baos, ByteOrder.LITTLE_ENDIAN );
        for ( TileEntity tileEntity : newTileEntities ) {
            NBTTagCompound compound = new NBTTagCompound( "" );
            tileEntity.toCompound( compound, SerializationReason.PERSIST );

            try {
                nbtWriter.write( compound );
            } catch ( IOException e ) {
                LOGGER.warn( "Could not write tile to leveldb", e );
            }
        }

        if ( baos.size() > 0 ) {
            ByteBuf key = this.getKey( chunkX, chunkZ, (byte) 0x31 );
            ByteBuf val = Allocator.allocate( baos.toByteArray() );

            batch.put( key, val );
        }
    }

    public void done() {
        this.db.close();
    }

    protected void finish() {
        WriteBatch batch = this.getWriteBatch();
        if ( batch.size() > 0 ) {
            this.db.write( batch );
            batch.clear();
        }

        batch.close();
        this.writeBatch.remove();
    }

    protected void persistChunk() {
        // Check for size
        WriteBatch batch = this.getWriteBatch();
        if ( batch.size() > 4 * 1024 * 1024 ) {    // We persist every 4 MB
            this.db.write( batch );
            batch.clear();
        }
    }

}
