package io.gomint.server.world.converter.anvil;

import io.gomint.server.GoMintServer;
import io.gomint.server.entity.tileentity.TileEntity;
import io.gomint.server.inventory.item.Items;
import io.gomint.server.util.BlockIdentifier;
import io.gomint.server.world.NibbleArray;
import io.gomint.server.world.converter.BaseChunkConverter;
import io.gomint.server.world.converter.anvil.tileentity.v1_8.TileEntities;
import io.gomint.taglib.NBTTagCompound;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;


public class ChunkConverter1_8 extends BaseChunkConverter {

    private BlockConverter blockConverter;
    private TileEntities tileEntityConverter;

    public ChunkConverter1_8( GoMintServer server ){
        super( server );
        // Setup block converter
        this.blockConverter = new BlockConverter( getAssets().getConverterData() );

        // Setup item converter
        Object2IntMap<String> itemConverter = new Object2IntOpenHashMap<>();
        for ( NBTTagCompound compound : getAssets().getConverterItemsData() ) {
            itemConverter.put( compound.getString( "s", "minecraft:air" ), compound.getInteger( "i", 0 ) );
        }

        this.tileEntityConverter = new TileEntities(new Items( server.getClassPath(), getAssets().getJeTopeItems() ), itemConverter );
    }

    @Override
    public BlockIdentifier[] convertChunkSection(int chunkX, int chunkZ, NBTTagCompound section) {
        byte[] blocks = section.getByteArray( "Blocks", new byte[0] );
        byte[] addBlocks = section.getByteArray( "Add", new byte[0] );
        int sectionY = section.getByte( "Y", (byte) 0 );

        NibbleArray add = addBlocks.length > 0 ? NibbleArray.create( addBlocks ) : null;
        NibbleArray data = NibbleArray.create( section.getByteArray( "Data", new byte[0] ) );

        if ( blocks == null ) {
            throw new IllegalArgumentException( "Corrupt chunk: Section is missing obligatory compounds" );
        }

        BlockIdentifier[] newBlocks = new BlockIdentifier[4096];

        for ( int j = 0; j < 16; ++j ) {
            for ( int i = 0; i < 16; ++i ) {
                for ( int k = 0; k < 16; ++k ) {
                    int blockIndex = ( j << 8 | k << 4 | i );

                    int blockId = ( ( ( add != null ? add.get( blockIndex ) << 8 : 0 ) | blocks[blockIndex] ) & 0xFF );
                    byte blockData = data.get( blockIndex );

                    // Block data converter
                    if ( blockId == 3 && blockData == 1 ) {
                        blockId = 198;
                        blockData = 0;
                    } else if ( blockId == 3 && blockData == 2 ) {
                        blockId = 243;
                        blockData = 0;
                    }

                    // Fix water & lava at the bottom of a chunk
                    if ( sectionY + j == 0 && ( blockId == 8 || blockId == 9 || blockId == 10 || blockId == 11 ) ) {
                        blockId = 7;
                        blockData = 0;
                    }

                    short newIndex = (short) ( ( i << 8 ) + ( k << 4 ) + j );
                    BlockIdentifier converted = this.blockConverter.convert( blockId, blockData );
                    if ( converted == null ) {
                        newBlocks[newIndex] = new BlockIdentifier( "minecraft:air", (short) 0 );
                        getLogger().warn( "Could not convert block {}:{}", blockId, blockData );
                    } else {
                        newBlocks[newIndex] = converted;
                    }
                }
            }
        }

        return newBlocks;
    }

    @Override
    public TileEntity convertTileEntity(NBTTagCompound tileCompound) {
        return this.tileEntityConverter.read( tileCompound );
    }
}
