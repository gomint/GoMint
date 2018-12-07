package io.gomint.server.world.block;

import com.google.common.collect.Lists;
import io.gomint.enchant.EnchantmentAquaAffinity;
import io.gomint.enchant.EnchantmentEfficiency;
import io.gomint.entity.potion.PotionEffect;
import io.gomint.inventory.item.ItemReduceBreaktime;
import io.gomint.inventory.item.ItemStack;
import io.gomint.inventory.item.ItemSword;
import io.gomint.math.AxisAlignedBB;
import io.gomint.math.BlockPosition;
import io.gomint.math.Location;
import io.gomint.math.Vector;
import io.gomint.server.entity.Entity;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.entity.tileentity.SerializationReason;
import io.gomint.server.entity.tileentity.TileEntities;
import io.gomint.server.entity.tileentity.TileEntity;
import io.gomint.server.network.PlayerConnection;
import io.gomint.server.network.packet.PacketTileEntityData;
import io.gomint.server.network.packet.PacketUpdateBlock;
import io.gomint.server.util.BlockIdentifier;
import io.gomint.server.world.BlockRuntimeIDs;
import io.gomint.server.world.PlacementData;
import io.gomint.server.world.UpdateReason;
import io.gomint.server.world.WorldAdapter;
import io.gomint.server.world.block.state.BlockState;
import io.gomint.server.world.storage.TemporaryStorage;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.world.Biome;
import io.gomint.world.block.BlockFace;
import io.gomint.world.block.data.Facing;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author geNAZt
 * @version 1.0
 */
@EqualsAndHashCode( of = { "location" } )
public abstract class Block implements io.gomint.world.block.Block {

    private static final Logger LOGGER = LoggerFactory.getLogger( Block.class );

    // CHECKSTYLE:OFF
    @Getter
    private String blockId;
    @Setter
    protected WorldAdapter world;
    @Setter
    @Getter
    protected Location location;
    @Getter
    @Setter
    private int layer;
    @Getter
    private short blockData;
    @Setter
    private TileEntity tileEntity;
    @Getter
    private byte skyLightLevel;
    @Getter
    private byte blockLightLevel;

    private LinkedHashMap<BlockState, Predicate<List<BlockState>>> blockStates;
    private List<BlockState> blockStateList;

    private boolean ready;

    // Set all needed data
    public void setData( String blockId, short blockData, TileEntity tileEntity, WorldAdapter worldAdapter, Location location, int layer, byte skyLightLevel, byte blockLightLevel ) {
        this.blockId = blockId;
        this.blockData = blockData;
        this.tileEntity = tileEntity;
        this.world = worldAdapter;
        this.location = location;
        this.skyLightLevel = skyLightLevel;
        this.blockLightLevel = blockLightLevel;
        this.layer = layer;
        this.generateBlockStates();

        this.ready = true;
    }
    // CHECKSTYLE:ON

    /**
     * Register a new block state wrapper
     *
     * @param blockState which should be registered
     * @param predicate  which decides if this block state gets serialized or not
     */
    public void registerState( BlockState blockState, Predicate<List<BlockState>> predicate ) {
        // Check if we have a storage
        if ( this.blockStates == null ) {
            this.blockStates = new LinkedHashMap<>();
            this.blockStateList = new ArrayList<>();
        }

        // Store this new block state
        this.blockStates.put( blockState, predicate );
        this.blockStateList.add( blockState );
    }

    /**
     * Hook for blocks which have custom blockId states. This should be used to convert data from the blockId to the state
     * objects
     */
    void generateBlockStates() {
        // Iterate over the block states
        if ( this.blockStates != null ) {
            for ( Map.Entry<BlockState, Predicate<List<BlockState>>> entry : this.blockStates.entrySet() ) {
                if ( entry.getValue().test( this.blockStateList ) ) {
                    entry.getKey().fromData( this.blockData );
                }
            }
        }
    }

    /**
     * Check if a blockId update is scheduled for this blockId
     *
     * @return true when there is, false when not
     */
    boolean isUpdateScheduled() {
        return this.world.isUpdateScheduled( this.location.toBlockPosition() );
    }

    /**
     * Called when a normal blockId update should be done
     *
     * @param updateReason  The reason why this blockId should update
     * @param currentTimeMS The timestamp when the tick has begun
     * @param dT            The difference time in full seconds since the last tick
     * @return a timestamp for the next execution
     */
    public long update( UpdateReason updateReason, long currentTimeMS, float dT ) {
        return -1;
    }

    /**
     * Method which gets called when a entity steps on a blockId
     *
     * @param entity which stepped on the blockId
     */
    public void stepOn( Entity entity ) {

    }

    /**
     * method which gets called when a entity got off a blockId which it {@link #stepOn(Entity)}.
     *
     * @param entity which got off the blockId
     */
    public void gotOff( Entity entity ) {

    }

    /**
     * Called when a entity decides to interact with the blockId
     *
     * @param entity  The entity which interacts with it
     * @param face    The blockId face the entity interacts with
     * @param facePos The position where the entity interacted with the blockId
     * @param item    The item with which the entity interacted, can be null
     * @return true when the blockId has made a action for interaction, false when not
     */
    public boolean interact( Entity entity, BlockFace face, Vector facePos, ItemStack item ) {
        return false;
    }

    /**
     * Called when an entity punches a blockId
     *
     * @param player   The player which punches it
     * @param position The position where the entity punched the blockId
     */
    public boolean punch( EntityPlayer player, BlockPosition position ) {
        return false;
    }

    /**
     * Store additional temporary data to a blockId. Things like how many players have stepped on a pressure plate etc.
     * are use cases for this system.
     *
     * @param key  of the value under which it will be stored and received
     * @param func which gets the old value (or null) passed into and returns the new value to be stored
     * @param <T>  type of old value
     * @param <R>  type of new value
     * @return new value
     */
    <T, R> R storeInTemporaryStorage( String key, Function<T, R> func ) {
        // Check world storage
        BlockPosition blockPosition = this.location.toBlockPosition();
        TemporaryStorage temporaryStorage = ( (WorldAdapter) this.location.getWorld() ).getTemporaryBlockStorage( blockPosition, this.layer );
        return temporaryStorage.store( key, func );
    }

    /**
     * Get a temporary stored value
     *
     * @param key of the value
     * @param <T> type of the value
     * @return the value or null when nothing has been stored
     */
    <T> T getFromTemporaryStorage( String key ) {
        // Check world storage
        BlockPosition blockPosition = this.location.toBlockPosition();
        TemporaryStorage temporaryStorage = ( (WorldAdapter) this.location.getWorld() ).getTemporaryBlockStorage( blockPosition, this.layer );
        return (T) temporaryStorage.get( key );
    }

    @Override
    public boolean isTransparent() {
        return false;
    }

    @Override
    public boolean isSolid() {
        return true;
    }

    /**
     * Does this blockId need a tile entity on placement?
     *
     * @return true when it needs a tile entity, false if it doesn't
     */
    public boolean needsTileEntity() {
        return false;
    }

    /**
     * Create a tile entity at the blocks location
     *
     * @param compound which has been prebuilt by {@link #calculatePlacementData(EntityPlayer, ItemStack, Block, Vector)}
     * @return new tile entity or null if there is none
     */
    TileEntity createTileEntity( NBTTagCompound compound ) {
        BlockPosition position = this.location.toBlockPosition();

        // Add generic tile entity stuff
        compound.addValue( "x", position.getX() );
        compound.addValue( "y", position.getY() );
        compound.addValue( "z", position.getZ() );

        return null;
    }

    /**
     * Update the blockId for the client
     */
    public void updateBlock() {
        this.calculateBlockData();

        if ( this.location == null ) {
            // No need to update
            return;
        }

        BlockPosition pos = this.location.toBlockPosition();
        WorldAdapter worldAdapter = (WorldAdapter) this.location.getWorld();
        worldAdapter.updateBlock( pos );
        worldAdapter.flagNeedsPersistance( pos );
    }

    /**
     * Set the new block data value from the metadata generator
     */
    short calculateBlockData() {
        if ( this.blockStates != null ) {
            short newBlockData = 0;

            for ( Map.Entry<BlockState, Predicate<List<BlockState>>> entry : this.blockStates.entrySet() ) {
                if ( entry.getValue().test( this.blockStateList ) ) {
                    newBlockData += entry.getKey().toData();
                }
            }

            this.setBlockData( newBlockData );
        }

        return this.blockData;
    }

    public PlacementData calculatePlacementData( EntityPlayer entity, ItemStack item, BlockFace face, Block block, Block clickedBlock, Vector clickVector ) {
        // Do we have some block states?
        if ( this.blockStates != null ) {
            for ( Map.Entry<BlockState, Predicate<List<BlockState>>> entry : this.blockStates.entrySet() ) {
                if ( entry.getValue().test( this.blockStateList ) ) {
                    entry.getKey().detectFromPlacement( entity, item, face, block, clickedBlock, clickVector );
                }
            }

            io.gomint.server.inventory.item.ItemStack implStack = (io.gomint.server.inventory.item.ItemStack) item;
            return new PlacementData( new BlockIdentifier( implStack.getBlockId(), this.calculateBlockData() ), null );
        }

        if ( item.getData() > 0 ) {
            LOGGER.warn( "Block implementation is missing states: {}", this.getClass().getName() );
        }

        io.gomint.server.inventory.item.ItemStack implStack = (io.gomint.server.inventory.item.ItemStack) item;
        return new PlacementData( new BlockIdentifier( implStack.getBlockId(), item.getData() ), null );
    }

    @Override
    public boolean isPlaced() {
        if ( this.location == null ) {
            return false;
        }

        WorldAdapter worldAdapter = (WorldAdapter) this.location.getWorld();
        return worldAdapter.getBlockId( this.location.toBlockPosition(), this.layer ).equals( this.getBlockId() );
    }

    @Override
    public Biome getBiome() {
        if ( this.location == null ) {
            return null;
        }

        WorldAdapter worldAdapter = (WorldAdapter) this.location.getWorld();
        return worldAdapter.getBiome( this.location.toBlockPosition() );
    }

    /**
     * Internal overload for NBT compound calculations
     *
     * @param <T>  blockId generic type
     * @param data optional data for the blockId
     * @return the new placed blockId
     */
    public <T extends io.gomint.world.block.Block> T setBlockFromPlacementData( PlacementData data ) {
        BlockPosition pos = this.location.toBlockPosition();
        Block instance = this.world.getServer().getBlocks().get( data.getBlockIdentifier().getBlockId() );

        if ( instance != null ) {
            WorldAdapter worldAdapter = (WorldAdapter) this.location.getWorld();
            worldAdapter.setBlock( pos, this.layer, data.getBlockIdentifier() );
            worldAdapter.resetTemporaryStorage( pos, this.layer );

            instance.setWorld( worldAdapter );
            instance.setLocation( this.location );

            // We need to calculate needed states
            instance.setBlockData( data.getBlockIdentifier().getData() );
            instance.generateBlockStates();

            // Check if new blockId needs tile entity
            if ( instance.needsTileEntity() ) {
                // Create new tile entity compound if null
                if ( data.getCompound() == null ) {
                    data.setCompound( new NBTTagCompound( "" ) );
                }

                TileEntity tileEntityInstance = instance.createTileEntity( data.getCompound() );
                if ( tileEntityInstance != null ) {
                    this.world.getServer().getContext().getAutowireCapableBeanFactory().autowireBean( tileEntityInstance );
                    instance.setTileEntity( tileEntityInstance );
                    worldAdapter.storeTileEntity( pos, tileEntityInstance );
                }
            } else {
                worldAdapter.removeTileEntity( pos );
            }

            instance = this.world.getBlockAt( pos );
            long next = instance.update( UpdateReason.BLOCK_ADDED, this.world.getServer().getCurrentTickTime(), 0f );
            if ( next > this.world.getServer().getCurrentTickTime() ) {
                this.world.addTickingBlock( next, pos );
            }

            worldAdapter.updateBlock( pos );
        }

        return (T) instance;
    }

    @Override
    public <T extends io.gomint.world.block.Block> T setType( Class<T> blockType ) {
        return this.setType( blockType, true, true );
    }

    public <T extends io.gomint.world.block.Block> T setType( Class<T> blockType, boolean checkTile, boolean resetData ) {
        BlockPosition pos = this.location.toBlockPosition();
        Block instance = this.world.getServer().getBlocks().get( blockType );
        if ( instance != null ) {
            WorldAdapter worldAdapter = (WorldAdapter) this.location.getWorld();

            BlockIdentifier blockIdentifier = new BlockIdentifier( instance.getBlockId(), ( resetData ) ? 0 : this.blockData );
            worldAdapter.setBlock( pos, this.layer, blockIdentifier );

            if ( resetData ) {
                worldAdapter.resetTemporaryStorage( pos, this.layer );
            }

            instance.setWorld( worldAdapter );
            instance.setLocation( this.location );

            // Check if new blockId needs tile entity
            if ( checkTile ) {
                if ( instance.needsTileEntity() ) {
                    TileEntity tileEntityInstance = instance.createTileEntity( new NBTTagCompound( "" ) );
                    if ( tileEntityInstance != null ) {
                        this.world.getServer().getContext().getAutowireCapableBeanFactory().autowireBean( tileEntityInstance );
                        instance.setTileEntity( tileEntityInstance );
                        worldAdapter.storeTileEntity( pos, tileEntityInstance );
                    }
                } else {
                    worldAdapter.removeTileEntity( pos );
                }
            } else {
                instance.setTileEntity( this.tileEntity );
            }

            long next = instance.update( UpdateReason.BLOCK_ADDED, this.world.getServer().getCurrentTickTime(), 0f );
            if ( next > this.world.getServer().getCurrentTickTime() ) {
                this.world.addTickingBlock( next, this.location.toBlockPosition() );
            }

            worldAdapter.updateBlock( pos );
        }

        return world.getBlockAt( pos );
    }

    @Override
    public <T extends io.gomint.world.block.Block> T setFromBlock( T apiInstance ) {
        // Fast fail when location doesn't match
        if ( !this.location.equals( apiInstance.getLocation() ) ) {
            return null;
        }

        Block instance = (Block) apiInstance;
        BlockPosition pos = this.location.toBlockPosition();
        WorldAdapter worldAdapter = (WorldAdapter) this.location.getWorld();
        worldAdapter.setBlock( pos, this.layer, new BlockIdentifier( instance.blockId, instance.blockData ) );
        worldAdapter.resetTemporaryStorage( pos, this.layer );

        // Check if new blockId needs tile entity
        if ( instance.getTileEntity() != null ) {
            worldAdapter.storeTileEntity( pos, instance.getTileEntity() );
        } else {
            worldAdapter.removeTileEntity( pos );
        }

        long next = instance.update( UpdateReason.BLOCK_ADDED, this.world.getServer().getCurrentTickTime(), 0f );
        if ( next > this.world.getServer().getCurrentTickTime() ) {
            this.world.addTickingBlock( next, this.location.toBlockPosition() );
        }

        worldAdapter.updateBlock( pos );
        return apiInstance;
    }

    @Override
    public <T extends io.gomint.world.block.Block> T copyFromBlock( T apiInstance ) {
        Block instance = (Block) apiInstance;
        BlockPosition pos = this.location.toBlockPosition();
        WorldAdapter worldAdapter = (WorldAdapter) this.location.getWorld();
        worldAdapter.setBlock( pos, this.layer, new BlockIdentifier( instance.blockId, instance.blockData ) );
        worldAdapter.resetTemporaryStorage( pos, this.layer );

        // Check if new blockId needs tile entity
        if ( instance.getTileEntity() != null ) {
            // We need to copy the tile entity and change its location
            NBTTagCompound compound = new NBTTagCompound( "" );
            instance.getTileEntity().toCompound( compound, SerializationReason.PERSIST );

            // Change the position
            compound.addValue( "x", pos.getX() );
            compound.addValue( "y", pos.getY() );
            compound.addValue( "z", pos.getZ() );

            // Construct new tile entity
            TileEntity tileEntityInstance = TileEntities.construct( this.world.getServer().getContext(), compound, instance );
            worldAdapter.storeTileEntity( pos, tileEntityInstance );
        } else {
            worldAdapter.removeTileEntity( pos );
        }

        long next = instance.update( UpdateReason.BLOCK_ADDED, this.world.getServer().getCurrentTickTime(), 0f );
        if ( next > this.world.getServer().getCurrentTickTime() ) {
            this.world.addTickingBlock( next, this.location.toBlockPosition() );
        }

        worldAdapter.updateBlock( pos );
        return worldAdapter.getBlockAt( pos );
    }

    /**
     * Get the break time needed to break this blockId without any enchantings or tools
     *
     * @return time in milliseconds it needs to break this blockId
     */
    public long getBreakTime() {
        return 250;
    }

    /**
     * Get the attached tile entity to this blockId
     *
     * @param <T> The type of the tile entity
     * @return null when there is not tile entity attached, otherwise the stored tile entity
     */
    public <T extends TileEntity> T getTileEntity() {
        if ( !isPlaced() ) {
            return null;
        }

        // Emergency checking
        if ( this.tileEntity == null && needsTileEntity() ) {
            LOGGER.warn( "Your world has been corrupted. The blockId {} @ {} has no stored tile entity. Please fix the world {}. The blockId will be repaired now, don't expect it to work like it should!",
                this.getClass().getSimpleName(), this.location, this.world.getWorldName() );

            this.tileEntity = createTileEntity( new NBTTagCompound( "" ) );
            if ( this.tileEntity != null ) {
                this.world.storeTileEntity( this.location.toBlockPosition(), this.tileEntity );
            }
        }

        return (T) this.tileEntity;
    }

    @Override
    public List<AxisAlignedBB> getBoundingBox() {
        return Collections.singletonList( new AxisAlignedBB(
            this.location.getX(),
            this.location.getY(),
            this.location.getZ(),
            this.location.getX() + 1,
            this.location.getY() + 1,
            this.location.getZ() + 1
        ) );
    }

    @Override
    public float getFrictionFactor() {
        return 0.6f;
    }

    @Override
    public boolean canPassThrough() {
        return false;
    }

    @Override
    public Block getSide( BlockFace face ) {
        switch ( face ) {
            case DOWN:
                return this.getRelative( BlockPosition.DOWN );
            case UP:
                return this.getRelative( BlockPosition.UP );
            case NORTH:
                return this.getRelative( BlockPosition.NORTH );
            case SOUTH:
                return this.getRelative( BlockPosition.SOUTH );
            case WEST:
                return this.getRelative( BlockPosition.WEST );
            case EAST:
                return this.getRelative( BlockPosition.EAST );
            default:
                return null;
        }
    }

    /**
     * Get the side of this blockId
     *
     * @param face which side we want to get
     * @return blockId attached to the side given
     */
    public Block getSide( Facing face ) {
        switch ( face ) {
            case SOUTH:
                return this.getRelative( BlockPosition.SOUTH );
            case NORTH:
                return this.getRelative( BlockPosition.NORTH );
            case EAST:
                return this.getRelative( BlockPosition.EAST );
            case WEST:
                return this.getRelative( BlockPosition.WEST );
            default:
                return null;
        }
    }

    private Block getRelative( BlockPosition position ) {
        return this.location.getWorld().getBlockAt( this.location.toBlockPosition().add( position ) );
    }

    /**
     * Check if this blockId can be replaced by the item in the arguments
     *
     * @param item which may replace this blockId
     * @return true if this blockId can be replaced with the item, false when not
     */
    public boolean canBeReplaced( ItemStack item ) {
        return false;
    }

    /**
     * Send all blockId packets needed to display this blockId
     *
     * @param connection which should get the blockId data
     */
    public void send( PlayerConnection connection ) {
        if ( !isPlaced() ) {
            return;
        }

        BlockPosition position = this.location.toBlockPosition();

        PacketUpdateBlock updateBlock = new PacketUpdateBlock();
        updateBlock.setPosition( position );
        updateBlock.setBlockId( BlockRuntimeIDs.from( this.getBlockId(), this.blockData ) );
        updateBlock.setFlags( PacketUpdateBlock.FLAG_ALL_PRIORITY );

        connection.addToSendQueue( updateBlock );

        // Also reset tile entity when needed
        if ( this.getTileEntity() != null ) {
            PacketTileEntityData tileEntityData = new PacketTileEntityData();
            tileEntityData.setPosition( position );

            NBTTagCompound compound = new NBTTagCompound( "" );
            this.getTileEntity().toCompound( compound, SerializationReason.NETWORK );

            tileEntityData.setCompound( compound );

            connection.addToSendQueue( tileEntityData );
        }
    }

    public boolean beforePlacement( Entity entity, ItemStack item, Location location ) {
        return true;
    }

    public void afterPlacement( PlacementData data ) {

    }

    /**
     * Get the final break time of a blockId in milliseconds. This applies all sorts of enchantments and effects which
     * needs to be used.
     *
     * @param item   with which the blockId should be destroyed
     * @param player which should destroy the blockId
     * @return break time in milliseconds
     */
    public long getFinalBreakTime( ItemStack item, EntityPlayer player ) {
        // Get basis break time ( breaking with right tool )
        float base = ( getBreakTime() / 1500F );
        float toolStrength = 1.0F;

        // Instant break
        if ( base <= 0 ) {
            return 50;
        }

        // Check if we need a tool
        boolean foundInterface = false;

        Class<? extends ItemStack>[] interfacez = getToolInterfaces();
        if ( interfacez != null ) {
            for ( Class<? extends ItemStack> aClass : interfacez ) {
                if ( aClass.isAssignableFrom( item.getClass() ) ) {
                    toolStrength = ( (ItemReduceBreaktime) item ).getDivisor();
                    foundInterface = true;
                }
            }
        }

        // Sword special case
        if ( !foundInterface && item instanceof ItemSword ) {
            toolStrength = 1.5F;
        }

        // Check for efficiency
        if ( toolStrength > 1.0F ) {
            EnchantmentEfficiency enchantment = item.getEnchantment( EnchantmentEfficiency.class );
            if ( enchantment != null && enchantment.getLevel() > 0 ) {
                toolStrength += ( enchantment.getLevel() * enchantment.getLevel() + 1 );
            }
        }

        // Haste effect
        int hasteAmplifier = player.getEffectAmplifier( PotionEffect.HASTE );
        if ( hasteAmplifier != -1 ) {
            toolStrength *= 1F + ( hasteAmplifier + 1 ) * 0.2F;
        }

        // Mining fatigue effect
        int miningFatigueAmplifier = player.getEffectAmplifier( PotionEffect.MINING_FATIGUE );
        if ( miningFatigueAmplifier != -1 ) {
            switch ( miningFatigueAmplifier ) {
                case 0:
                    toolStrength *= 0.3f;
                    break;

                case 1:
                    toolStrength *= 0.09f;
                    break;

                case 2:
                    toolStrength *= 0.00027f;
                    break;

                case 3:
                default:
                    toolStrength *= 8.1E-4F;
                    break;
            }
        }

        // When in water
        if ( player.isInsideLiquid() && player.getArmorInventory().getHelmet().getEnchantment( EnchantmentAquaAffinity.class ) == null ) {
            toolStrength /= 5.0F;
        }

        // When not onground
        if ( !player.isOnGround() ) {
            toolStrength /= 5.0F;
        }

        // Can't be broken
        float result;
        if ( !foundInterface && !canBeBrokenWithHand() ) {
            result = toolStrength / base / 100F;
        } else {
            result = toolStrength / base / 30F;
        }

        long time = (long) ( ( 1F / result ) * 50F );
        if ( time < 50 ) {
            time = 50;
        }

        return time;
    }

    public Class<? extends ItemStack>[] getToolInterfaces() {
        return null;
    }

    public boolean onBreak( boolean creative ) {
        return true;
    }

    public boolean canBeBrokenWithHand() {
        return false;
    }

    /**
     * Get drops from the blockId when it broke
     *
     * @param itemInHand which was used to destroy this blockId
     * @return a list of drops
     */
    public List<ItemStack> getDrops( ItemStack itemInHand ) {
        ItemStack drop = this.world.getServer().getItems().create( getBlockId(), this.blockData, (byte) 1, null );
        return Lists.newArrayList( drop );
    }

    /**
     * Set new blockId data for this position
     *
     * @param blockData which should be set
     */
    public void setBlockData( short blockData ) {
        // Only update when there has been a value already loaded
        if ( this.blockData > -1 && isPlaced() ) {
            this.world.setBlockData( this.location.toBlockPosition(), this.layer, blockData );
        }

        this.blockData = blockData;
    }

    public void onEntityCollision( Entity entity ) {

    }

    public void onEntityStanding( EntityLiving entityLiving ) {

    }

    boolean isCorrectTool( ItemStack itemInHand ) {
        for ( Class<? extends ItemStack> aClass : getToolInterfaces() ) {
            if ( aClass.isAssignableFrom( itemInHand.getClass() ) ) {
                return true;
            }
        }

        return false;
    }

    public abstract float getBlastResistance();

    public void setBlockId( String blockId ) {
        if ( isPlaced() ) {
            this.world.setBlockId( this.location.toBlockPosition(), this.layer, blockId );
            this.updateBlock();
        }

        this.blockId = blockId;
    }

    public boolean canBeFlowedInto() {
        return false;
    }

    public Vector addVelocity( Entity entity, Vector pushedByBlocks ) {
        return pushedByBlocks;
    }

    public boolean intersectsWith( AxisAlignedBB boundingBox ) {
        for ( AxisAlignedBB axisAlignedBB : getBoundingBox() ) {
            if ( axisAlignedBB.intersectsWith( boundingBox ) ) {
                return true;
            }
        }

        return false;
    }

    public boolean ready() {
        return this.ready;
    }

}
