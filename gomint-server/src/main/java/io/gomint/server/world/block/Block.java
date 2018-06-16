package io.gomint.server.world.block;

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
import io.gomint.server.entity.tileentity.TileEntities;
import io.gomint.server.entity.tileentity.TileEntity;
import io.gomint.server.network.PlayerConnection;
import io.gomint.server.network.packet.Packet;
import io.gomint.server.network.packet.PacketTileEntityData;
import io.gomint.server.network.packet.PacketUpdateBlock;
import io.gomint.server.world.BlockRuntimeIDs;
import io.gomint.server.world.PlacementData;
import io.gomint.server.world.UpdateReason;
import io.gomint.server.world.WorldAdapter;
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
import java.util.List;
import java.util.function.Function;

/**
 * @author geNAZt
 * @version 1.0
 */
@EqualsAndHashCode( of = { "location" } )
public abstract class Block implements io.gomint.world.block.Block {

    private static final Logger LOGGER = LoggerFactory.getLogger( Block.class );

    // CHECKSTYLE:OFF
    @Getter
    private int blockId;
    @Setter
    protected WorldAdapter world;
    @Setter
    @Getter
    protected Location location;
    @Getter
    @Setter
    private int layer;
    @Getter
    private byte blockData;
    @Setter
    private TileEntity tileEntity;
    @Getter
    private byte skyLightLevel;
    @Getter
    private byte blockLightLevel;

    // Set all needed data
    public void setData( int blockId, byte blockData, TileEntity tileEntity, WorldAdapter worldAdapter, Location location, int layer, byte skyLightLevel, byte blockLightLevel ) {
        this.blockId = blockId;
        this.blockData = blockData;
        this.tileEntity = tileEntity;
        this.world = worldAdapter;
        this.location = location;
        this.skyLightLevel = skyLightLevel;
        this.blockLightLevel = blockLightLevel;
        this.layer = layer;
        this.generateBlockStates();
    }
    // CHECKSTYLE:ON

    /**
     * Hook for blocks which have custom block states. This should be used to convert data from the block to the state
     * objects
     */
    public void generateBlockStates() {

    }

    /**
     * Check if a block update is scheduled for this block
     *
     * @return true when there is, false when not
     */
    public boolean isUpdateScheduled() {
        return this.world.isUpdateScheduled( this.location.toBlockPosition() );
    }

    /**
     * Called when a normal block update should be done
     *
     * @param updateReason  The reason why this block should update
     * @param currentTimeMS The timestamp when the tick has begun
     * @param dT            The difference time in full seconds since the last tick
     * @return a timestamp for the next execution
     */
    public long update( UpdateReason updateReason, long currentTimeMS, float dT ) {
        return -1;
    }

    /**
     * Method which gets called when a entity steps on a block
     *
     * @param entity which stepped on the block
     */
    public void stepOn( Entity entity ) {

    }

    /**
     * method which gets called when a entity got off a block which it {@link #stepOn(Entity)}.
     *
     * @param entity which got off the block
     */
    public void gotOff( Entity entity ) {

    }

    /**
     * Called when a entity decides to interact with the block
     *
     * @param entity  The entity which interacts with it
     * @param face    The block face the entity interacts with
     * @param facePos The position where the entity interacted with the block
     * @param item    The item with which the entity interacted, can be null
     */
    public boolean interact( Entity entity, BlockFace face, Vector facePos, ItemStack item ) {
        return false;
    }

    /**
     * Store additional temporary data to a block. Things like how many players have stepped on a pressure plate etc.
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
     * Does this block need a tile entity on placement?
     *
     * @return true when it needs a tile entity, false if it doesn't
     */
    public boolean needsTileEntity() {
        return false;
    }

    /**
     * Create a tile entity at the blocks location
     *
     * @param compound which has been prebuilt by {@link #calculatePlacementData(Entity, ItemStack, Vector)}
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
     * Update the block for the client
     */
    void updateBlock() {
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

    public void calculateBlockData() {
    }

    @Override
    public boolean isPlaced() {
        if ( this.location == null ) {
            return false;
        }

        WorldAdapter worldAdapter = (WorldAdapter) this.location.getWorld();
        return worldAdapter.getBlockId( this.location.toBlockPosition(), this.layer ) == this.getBlockId();
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
     * @param <T>  block generic type
     * @param data optional data for the block
     * @return the new placed block
     */
    public <T extends io.gomint.world.block.Block> T setBlockFromPlacementData( PlacementData data ) {
        BlockPosition pos = this.location.toBlockPosition();
        Block instance = this.world.getServer().getBlocks().get( data.getBlockId() );

        if ( instance != null ) {
            WorldAdapter worldAdapter = (WorldAdapter) this.location.getWorld();
            worldAdapter.setBlockId( pos, this.layer, data.getBlockId() );
            worldAdapter.setBlockData( pos, this.layer, data.getMetaData() );
            worldAdapter.resetTemporaryStorage( pos, this.layer );

            instance.setWorld( worldAdapter );
            instance.setLocation( this.location );

            // Check if new block needs tile entity
            if ( instance.needsTileEntity() ) {
                // Create new tile entity compound if null
                if ( data.getCompound() == null ) {
                    data.setCompound( new NBTTagCompound( "" ) );
                }

                TileEntity tileEntity = instance.createTileEntity( data.getCompound() );
                if ( tileEntity != null ) {
                    instance.setTileEntity( tileEntity );
                    worldAdapter.storeTileEntity( pos, tileEntity );
                }
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
        BlockPosition pos = this.location.toBlockPosition();
        Block instance = this.world.getServer().getBlocks().get( blockType );
        if ( instance != null ) {
            WorldAdapter worldAdapter = (WorldAdapter) this.location.getWorld();
            worldAdapter.setBlockId( pos, this.layer, instance.getBlockId() );
            worldAdapter.setBlockData( pos, this.layer, (byte) 0 );
            worldAdapter.resetTemporaryStorage( pos, this.layer );

            instance.setWorld( worldAdapter );
            instance.setLocation( this.location );

            // Check if new block needs tile entity
            if ( instance.needsTileEntity() ) {
                TileEntity tileEntity = instance.createTileEntity( new NBTTagCompound( "" ) );
                if ( tileEntity != null ) {
                    instance.setTileEntity( tileEntity );
                    worldAdapter.storeTileEntity( pos, tileEntity );
                }
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
        worldAdapter.setBlockId( pos, this.layer, instance.getBlockId() );
        worldAdapter.setBlockData( pos, this.layer, instance.getBlockData() );
        worldAdapter.resetTemporaryStorage( pos, this.layer );

        // Check if new block needs tile entity
        if ( instance.getTileEntity() != null ) {
            worldAdapter.storeTileEntity( pos, instance.getTileEntity() );
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
        worldAdapter.setBlockId( pos, this.layer, instance.getBlockId() );
        worldAdapter.setBlockData( pos, this.layer, instance.getBlockData() );
        worldAdapter.resetTemporaryStorage( pos, this.layer );

        // Check if new block needs tile entity
        if ( instance.getTileEntity() != null ) {
            // We need to copy the tile entity and change its location
            NBTTagCompound compound = new NBTTagCompound( "" );
            instance.getTileEntity().toCompound( compound );

            // Change the position
            compound.addValue( "x", pos.getX() );
            compound.addValue( "y", pos.getY() );
            compound.addValue( "z", pos.getZ() );

            // Construct new tile entity
            TileEntity tileEntity = TileEntities.construct( compound, worldAdapter );
            worldAdapter.storeTileEntity( pos, tileEntity );
        }

        long next = instance.update( UpdateReason.BLOCK_ADDED, this.world.getServer().getCurrentTickTime(), 0f );
        if ( next > this.world.getServer().getCurrentTickTime() ) {
            this.world.addTickingBlock( next, this.location.toBlockPosition() );
        }

        worldAdapter.updateBlock( pos );
        return worldAdapter.getBlockAt( pos );
    }

    /**
     * Get the break time needed to break this block without any enchantings or tools
     *
     * @return time in milliseconds it needs to break this block
     */
    public long getBreakTime() {
        return 250;
    }

    /**
     * Get the attached tile entity to this block
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
            LOGGER.warn( "Your world has been corrupted. The block {} @ {} has no stored tile entity. Please fix the world {}. The block will be repaired now, don't expect it to work like it should!",
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
        }

        return null;
    }

    /**
     * Get the side of this block
     *
     * @param face which side we want to get
     * @return block attached to the side given
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
        }

        return null;
    }

    private Block getRelative( BlockPosition position ) {
        return this.location.getWorld().getBlockAt( this.location.toBlockPosition().add( position ) );
    }

    /**
     * Check if this block can be replaced by the item in the arguments
     *
     * @param item which may replace this block
     * @return true if this block can be replaced with the item, false when not
     */
    public boolean canBeReplaced( ItemStack item ) {
        return false;
    }

    /**
     * Send all block packets needed to display this block
     *
     * @param connection  which should get the block data
     */
    public void send( PlayerConnection connection ) {
        if ( !isPlaced() ) {
            return;
        }

        BlockPosition position = this.location.toBlockPosition();

        PacketUpdateBlock updateBlock = new PacketUpdateBlock();
        updateBlock.setPosition( position );
        updateBlock.setBlockId( BlockRuntimeIDs.fromLegacy( this.getBlockId(), this.getBlockData(), connection.getProtocolID() ) );
        updateBlock.setFlags( PacketUpdateBlock.FLAG_ALL_PRIORITY );

        connection.addToSendQueue( updateBlock );

        // Also reset tile entity when needed
        if ( this.getTileEntity() != null ) {
            PacketTileEntityData tileEntityData = new PacketTileEntityData();
            tileEntityData.setPosition( position );
            tileEntityData.setTileEntity( this.getTileEntity() );

            connection.addToSendQueue( tileEntityData );
        }
    }

    public boolean beforePlacement( Entity entity, ItemStack item, Location location ) {
        return true;
    }

    public void afterPlacement( PlacementData data ) {

    }

    public PlacementData calculatePlacementData( Entity entity, ItemStack item, Vector clickVector ) {
        io.gomint.server.inventory.item.ItemStack implStack = (io.gomint.server.inventory.item.ItemStack) item;
        return new PlacementData( implStack.getBlockId(), (byte) item.getData(), null );
    }

    /**
     * Get the final break time of a block in milliseconds. This applies all sorts of enchantments and effects which
     * needs to be used.
     *
     * @param item   with which the block should be destroyed
     * @param player which should destroy the block
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
     * Get drops from the block when it broke
     *
     * @param itemInHand which was used to destroy this block
     * @return a list of drops
     */
    public List<ItemStack> getDrops( ItemStack itemInHand ) {
        return new ArrayList<ItemStack>() {{
            add( world.getServer().getItems().create( getBlockId() & 0xFF, getBlockData(), (byte) 1, null ) );
        }};
    }

    /**
     * Set new block data for this position
     *
     * @param blockData which should be set
     */
    public void setBlockData( byte blockData ) {
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

    public void setBlockId( byte blockId ) {
        if ( isPlaced() ) {
            this.blockId = blockId;
            this.world.setBlockId( this.location.toBlockPosition(), this.layer, blockId );
        }
    }

    public boolean canBeFlowedInto() {
        return false;
    }

    public void addVelocity( Entity entity, Vector pushedByBlocks ) {

    }

    public boolean intersectsWith( AxisAlignedBB boundingBox ) {
        for ( AxisAlignedBB axisAlignedBB : getBoundingBox() ) {
            if ( axisAlignedBB.intersectsWith( boundingBox ) ) {
                return true;
            }
        }

        return false;
    }

    /**
     * Only usable for other repair strats like post process runners
     *
     * @return the raw tile entity or null if there is none
     */
    public TileEntity getRawTileEntity() {
        return this.tileEntity;
    }

}
