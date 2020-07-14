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
import io.gomint.math.MathUtils;
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
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.util.BlockIdentifier;
import io.gomint.server.world.BlockRuntimeIDs;
import io.gomint.server.world.PlacementData;
import io.gomint.server.world.UpdateReason;
import io.gomint.server.world.WorldAdapter;
import io.gomint.server.world.block.state.BlockState;
import io.gomint.server.world.storage.TemporaryStorage;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.world.Biome;
import io.gomint.world.block.data.Direction;
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
@EqualsAndHashCode(of = {"location"})
public abstract class Block implements io.gomint.world.block.Block {

    private static final Logger LOGGER = LoggerFactory.getLogger(Block.class);

    // This is the source of truth
    @Setter @Getter
    protected BlockIdentifier identifier;

    // CHECKSTYLE:OFF
    @Setter
    protected WorldAdapter world;
    @Setter
    @Getter
    protected Location location;
    @Getter
    @Setter
    private int layer;
    @Setter
    private TileEntity tileEntity;
    @Getter
    private byte skyLightLevel;
    @Getter
    private byte blockLightLevel;

    // States
    private List<BlockState> blockStates;

    private boolean ready;

    // Set all needed data
    public void setData(BlockIdentifier identifier, TileEntity tileEntity, WorldAdapter worldAdapter, Location location, int layer, byte skyLightLevel, byte blockLightLevel) {
        this.identifier = identifier;
        this.tileEntity = tileEntity;
        this.world = worldAdapter;
        this.location = location;
        this.skyLightLevel = skyLightLevel;
        this.blockLightLevel = blockLightLevel;
        this.layer = layer;

        this.ready = true;
    }
    // CHECKSTYLE:ON

    /**
     * Register a new block state wrapper
     *
     * @param blockState which should be registered
     */
    public void registerState(BlockState blockState) {
        // Check if we have a storage
        if (this.blockStates == null) {
            this.blockStates = new ArrayList<>();
        }

        // Store this new block state
        this.blockStates.add(blockState);
    }

    /**
     * Check if a blockId update is scheduled for this blockId
     *
     * @return true when there is, false when not
     */
    boolean isUpdateScheduled() {
        return this.world.isUpdateScheduled(this.location.toBlockPosition());
    }

    /**
     * Called when a normal blockId update should be done
     *
     * @param updateReason  The reason why this blockId should update
     * @param currentTimeMS The timestamp when the tick has begun
     * @param dT            The difference time in full seconds since the last tick
     * @return a timestamp for the next execution
     */
    public long update(UpdateReason updateReason, long currentTimeMS, float dT) {
        return -1;
    }

    /**
     * Method which gets called when a entity steps on a blockId
     *
     * @param entity which stepped on the blockId
     */
    public void stepOn(Entity entity) {

    }

    /**
     * method which gets called when a entity got off a blockId which it {@link #stepOn(Entity)}.
     *
     * @param entity which got off the blockId
     */
    public void gotOff(Entity entity) {

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
    public boolean interact(Entity entity, Facing face, Vector facePos, ItemStack item) {
        return false;
    }

    /**
     * Called when an entity punches a blockId
     *
     * @param player   The player which punches it
     * @param position The position where the entity punched the blockId
     */
    public boolean punch(EntityPlayer player, BlockPosition position) {
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
    <T, R> R storeInTemporaryStorage(String key, Function<T, R> func) {
        // Check world storage
        BlockPosition blockPosition = this.location.toBlockPosition();
        TemporaryStorage temporaryStorage = ((WorldAdapter) this.location.getWorld()).getTemporaryBlockStorage(blockPosition, this.layer);
        return temporaryStorage.store(key, func);
    }

    /**
     * Get a temporary stored value
     *
     * @param key of the value
     * @param <T> type of the value
     * @return the value or null when nothing has been stored
     */
    <T> T getFromTemporaryStorage(String key) {
        // Check world storage
        BlockPosition blockPosition = this.location.toBlockPosition();
        TemporaryStorage temporaryStorage = ((WorldAdapter) this.location.getWorld()).getTemporaryBlockStorage(blockPosition, this.layer);
        return (T) temporaryStorage.get(key);
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
     * @param compound which has been prebuilt by {@link #calculatePlacementData(EntityPlayer, ItemStack, Facing, Block, Block, Vector)}
     * @return new tile entity or null if there is none
     */
    TileEntity createTileEntity(NBTTagCompound compound) {
        BlockPosition position = this.location.toBlockPosition();

        // Add generic tile entity stuff
        compound.addValue("x", position.getX());
        compound.addValue("y", position.getY());
        compound.addValue("z", position.getZ());

        return null;
    }

    /**
     * Update the blockId for the client
     */
    public void updateBlock() {
        if (this.location == null) {
            // No need to update
            return;
        }

        BlockPosition pos = this.location.toBlockPosition();
        WorldAdapter worldAdapter = (WorldAdapter) this.location.getWorld();
        worldAdapter.setBlock(pos, this.layer, this.identifier.getRuntimeId());
        worldAdapter.updateBlock(pos);
        worldAdapter.flagNeedsPersistance(pos);
    }

    public PlacementData calculatePlacementData(EntityPlayer entity, ItemStack item, Facing face, Block block, Block clickedBlock, Vector clickVector) {
        // Do we have some block states?
        if (this.blockStates != null) {
            for (BlockState blockState : this.blockStates) {
                blockState.detectFromPlacement(entity, item, face, block, clickedBlock, clickVector);
            }

            return new PlacementData(this.identifier, null);
        }

        if (item != null && item.getData() > 0) {
            LOGGER.warn("Block implementation is missing states: {}", this.getClass().getName());
        }

        return new PlacementData(this.identifier, null);
    }

    @Override
    public boolean isPlaced() {
        if (this.location == null) {
            return false;
        }

        WorldAdapter worldAdapter = (WorldAdapter) this.location.getWorld();
        return worldAdapter.getRuntimeID(this.location.toBlockPosition(), this.layer) == this.identifier.getRuntimeId();
    }

    @Override
    public Biome getBiome() {
        if (this.location == null) {
            return null;
        }

        WorldAdapter worldAdapter = (WorldAdapter) this.location.getWorld();
        return worldAdapter.getBiome(this.location.toBlockPosition());
    }

    /**
     * Internal overload for NBT compound calculations
     *
     * @param <T>  blockId generic type
     * @param data optional data for the blockId
     * @return the new placed blockId
     */
    public <T extends io.gomint.world.block.Block> T setBlockFromPlacementData(PlacementData data) {
        BlockPosition pos = this.location.toBlockPosition();

        // Check if this block can be replaced
        if (pos.getY() < 0 || pos.getY() > 255) {
            LOGGER.warn("Invalid block placement @ {}", pos, new Exception());
            return null;
        }

        Block instance = this.world.getServer().getBlocks().get(data.getBlockIdentifier().getBlockId());
        if (instance != null) {
            WorldAdapter worldAdapter = (WorldAdapter) this.location.getWorld();
            worldAdapter.setBlock(pos, this.layer, data.getBlockIdentifier().getRuntimeId());
            worldAdapter.resetTemporaryStorage(pos, this.layer);

            instance.setWorld(worldAdapter);
            instance.setLocation(this.location);

            // We need to calculate needed states
            instance.setIdentifier(data.getBlockIdentifier());

            // Check if new blockId needs tile entity
            if (instance.needsTileEntity()) {
                // Create new tile entity compound if null
                if (data.getCompound() == null) {
                    data.setCompound(new NBTTagCompound(""));
                }

                TileEntity tileEntityInstance = instance.createTileEntity(data.getCompound());
                if (tileEntityInstance != null) {
                    this.world.getServer().getContext().getAutowireCapableBeanFactory().autowireBean(tileEntityInstance);
                    instance.setTileEntity(tileEntityInstance);
                    worldAdapter.storeTileEntity(pos, tileEntityInstance);
                }
            } else {
                worldAdapter.removeTileEntity(pos);
            }

            instance = this.world.getBlockAt(pos);
            long next = instance.update(UpdateReason.BLOCK_ADDED, this.world.getServer().getCurrentTickTime(), 0f);
            if (next > this.world.getServer().getCurrentTickTime()) {
                this.world.addTickingBlock(next, pos);
            }

            worldAdapter.updateBlock(pos);
        }

        return (T) instance;
    }

    @Override
    public <T extends io.gomint.world.block.Block> T setBlockType(Class<T> blockType) {
        BlockPosition pos = this.location.toBlockPosition();

        // Check if this block can be replaced
        if (pos.getY() < 0 || pos.getY() > 255) {
            LOGGER.warn("Invalid block placement @ {}", pos, new Exception());
            return null;
        }

        Block instance = this.world.getServer().getBlocks().get(blockType);
        if (instance != null) {
            PlacementData data = instance.calculatePlacementData(null, null, null, null, null, null);

            WorldAdapter worldAdapter = (WorldAdapter) this.location.getWorld();
            worldAdapter.setBlock(pos, this.layer, data.getBlockIdentifier().getRuntimeId());
            worldAdapter.resetTemporaryStorage(pos, this.layer);

            instance.setWorld(worldAdapter);
            instance.setLocation(this.location);

            // Check if new blockId needs tile entity
            if (instance.needsTileEntity()) {
                TileEntity tileEntityInstance = instance.createTileEntity(new NBTTagCompound(""));
                if (tileEntityInstance != null) {
                    this.world.getServer().getContext().getAutowireCapableBeanFactory().autowireBean(tileEntityInstance);
                    instance.setTileEntity(tileEntityInstance);
                    worldAdapter.storeTileEntity(pos, tileEntityInstance);
                }
            } else {
                worldAdapter.removeTileEntity(pos);
            }

            long next = instance.update(UpdateReason.BLOCK_ADDED, this.world.getServer().getCurrentTickTime(), 0f);
            if (next > this.world.getServer().getCurrentTickTime()) {
                this.world.addTickingBlock(next, this.location.toBlockPosition());
            }

            worldAdapter.updateBlock(pos);
        }

        return world.getBlockAt(pos);
    }

    @Override
    public <T extends io.gomint.world.block.Block> T setFromBlock(T apiInstance) {
        // Fast fail when location doesn't match
        if (!this.location.equals(apiInstance.getLocation())) {
            return null;
        }

        Block instance = (Block) apiInstance;
        BlockPosition pos = this.location.toBlockPosition();

        // Check if this block can be replaced
        if (pos.getY() < 0 || pos.getY() > 255) {
            LOGGER.warn("Invalid block placement @ {}", pos, new Exception());
            return null;
        }

        WorldAdapter worldAdapter = (WorldAdapter) this.location.getWorld();
        worldAdapter.setBlock(pos, this.layer, instance.identifier.getRuntimeId());
        worldAdapter.resetTemporaryStorage(pos, this.layer);

        // Check if new blockId needs tile entity
        if (instance.getTileEntity() != null) {
            worldAdapter.storeTileEntity(pos, instance.getTileEntity());
        } else {
            worldAdapter.removeTileEntity(pos);
        }

        long next = instance.update(UpdateReason.BLOCK_ADDED, this.world.getServer().getCurrentTickTime(), 0f);
        if (next > this.world.getServer().getCurrentTickTime()) {
            this.world.addTickingBlock(next, this.location.toBlockPosition());
        }

        worldAdapter.updateBlock(pos);
        return apiInstance;
    }

    @Override
    public <T extends io.gomint.world.block.Block> T copyFromBlock(T apiInstance) {
        Block instance = (Block) apiInstance;
        BlockPosition pos = this.location.toBlockPosition();

        // Check if this block can be replaced
        if (pos.getY() < 0 || pos.getY() > 255) {
            LOGGER.warn("Invalid block placement @ {}", pos, new Exception());
            return null;
        }

        WorldAdapter worldAdapter = (WorldAdapter) this.location.getWorld();
        worldAdapter.setBlock(pos, this.layer, instance.identifier.getRuntimeId());
        worldAdapter.resetTemporaryStorage(pos, this.layer);

        // Check if new blockId needs tile entity
        if (instance.getTileEntity() != null) {
            // We need to copy the tile entity and change its location
            NBTTagCompound compound = new NBTTagCompound("");
            instance.getTileEntity().toCompound(compound, SerializationReason.PERSIST);

            // Change the position
            compound.addValue("x", pos.getX());
            compound.addValue("y", pos.getY());
            compound.addValue("z", pos.getZ());

            // Construct new tile entity
            TileEntity tileEntityInstance = TileEntities.construct(this.world.getServer().getContext(), compound, instance);
            worldAdapter.storeTileEntity(pos, tileEntityInstance);
        } else {
            worldAdapter.removeTileEntity(pos);
        }

        long next = instance.update(UpdateReason.BLOCK_ADDED, this.world.getServer().getCurrentTickTime(), 0f);
        if (next > this.world.getServer().getCurrentTickTime()) {
            this.world.addTickingBlock(next, this.location.toBlockPosition());
        }

        worldAdapter.updateBlock(pos);
        return worldAdapter.getBlockAt(pos);
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
        if (!isPlaced()) {
            return null;
        }

        // Emergency checking
        if (this.tileEntity == null && needsTileEntity()) {
            LOGGER.warn("Your world has been corrupted. The blockId {} @ {} has no stored tile entity. Please fix the world {}. The blockId will be repaired now, don't expect it to work like it should!",
                this.getClass().getSimpleName(), this.location, this.world.getWorldName());

            this.tileEntity = createTileEntity(new NBTTagCompound(""));
            if (this.tileEntity != null) {
                this.world.storeTileEntity(this.location.toBlockPosition(), this.tileEntity);
            }
        }

        return (T) this.tileEntity;
    }

    @Override
    public List<AxisAlignedBB> getBoundingBox() {
        return Collections.singletonList(new AxisAlignedBB(
            this.location.getX(),
            this.location.getY(),
            this.location.getZ(),
            this.location.getX() + 1,
            this.location.getY() + 1,
            this.location.getZ() + 1
        ));
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
    public Block getSide(Facing face) {
        switch (face) {
            case DOWN:
                return this.getRelative(BlockPosition.DOWN);
            case UP:
                return this.getRelative(BlockPosition.UP);
            case NORTH:
                return this.getRelative(BlockPosition.NORTH);
            case SOUTH:
                return this.getRelative(BlockPosition.SOUTH);
            case WEST:
                return this.getRelative(BlockPosition.WEST);
            case EAST:
                return this.getRelative(BlockPosition.EAST);
            default:
                return null;
        }
    }

    /**
     * Get the side of this blockId
     *
     * @param direction which side we want to get
     * @return blockId attached to the side given
     */
    public Block getSide(Direction direction) {
        switch (direction) {
            case SOUTH:
                return this.getRelative(BlockPosition.SOUTH);
            case NORTH:
                return this.getRelative(BlockPosition.NORTH);
            case EAST:
                return this.getRelative(BlockPosition.EAST);
            case WEST:
                return this.getRelative(BlockPosition.WEST);
            default:
                return null;
        }
    }

    private Block getRelative(BlockPosition position) {
        int x = MathUtils.fastFloor( this.location.getX() ) + position.getX();
        int y = MathUtils.fastFloor( this.location.getY() ) + position.getY();
        int z = MathUtils.fastFloor( this.location.getZ() ) + position.getZ();

        return this.location.getWorld().getBlockAt(x,y,z);
    }

    /**
     * Check if this blockId can be replaced by the item in the arguments
     *
     * @param item which may replace this blockId
     * @return true if this blockId can be replaced with the item, false when not
     */
    public boolean canBeReplaced(ItemStack item) {
        return false;
    }

    /**
     * Send all blockId packets needed to display this blockId
     *
     * @param connection which should get the blockId data
     */
    public void send(PlayerConnection connection) {
        if (!isPlaced()) {
            return;
        }

        BlockPosition position = this.location.toBlockPosition();

        PacketUpdateBlock updateBlock = new PacketUpdateBlock();
        updateBlock.setPosition(position);
        updateBlock.setBlockId(this.identifier.getRuntimeId());
        updateBlock.setFlags(PacketUpdateBlock.FLAG_ALL_PRIORITY);

        connection.addToSendQueue(updateBlock);

        // Also reset tile entity when needed
        if (this.getTileEntity() != null) {
            PacketTileEntityData tileEntityData = new PacketTileEntityData();
            tileEntityData.setPosition(position);

            NBTTagCompound compound = new NBTTagCompound("");
            this.getTileEntity().toCompound(compound, SerializationReason.NETWORK);

            tileEntityData.setCompound(compound);

            connection.addToSendQueue(tileEntityData);
        }
    }

    public boolean beforePlacement(Entity entity, ItemStack item, Facing face, Location location) {
        return true;
    }

    public void afterPlacement(PlacementData data) {

    }

    /**
     * Get the final break time of a blockId in milliseconds. This applies all sorts of enchantments and effects which
     * needs to be used.
     *
     * @param item   with which the blockId should be destroyed
     * @param player which should destroy the blockId
     * @return break time in milliseconds
     */
    public long getFinalBreakTime(ItemStack item, EntityPlayer player) {
        // Get basis break time ( breaking with right tool )
        float base = (getBreakTime() / 1500F);
        float toolStrength = 1.0F;

        // Instant break
        if (base <= 0) {
            return 50;
        }

        // Check if we need a tool
        boolean foundInterface = false;

        Class<? extends ItemStack>[] interfacez = getToolInterfaces();
        if (interfacez != null) {
            for (Class<? extends ItemStack> aClass : interfacez) {
                if (aClass.isAssignableFrom(item.getClass())) {
                    toolStrength = ((ItemReduceBreaktime) item).getDivisor();
                    foundInterface = true;
                }
            }
        }

        // Sword special case
        if (!foundInterface && item instanceof ItemSword) {
            toolStrength = 1.5F;
        }

        // Check for efficiency
        if (toolStrength > 1.0F) {
            EnchantmentEfficiency enchantment = item.getEnchantment(EnchantmentEfficiency.class);
            if (enchantment != null && enchantment.getLevel() > 0) {
                toolStrength += (enchantment.getLevel() * enchantment.getLevel() + 1);
            }
        }

        // Haste effect
        int hasteAmplifier = player.getEffectAmplifier(PotionEffect.HASTE);
        if (hasteAmplifier != -1) {
            toolStrength *= 1F + (hasteAmplifier + 1) * 0.2F;
        }

        // Mining fatigue effect
        int miningFatigueAmplifier = player.getEffectAmplifier(PotionEffect.MINING_FATIGUE);
        if (miningFatigueAmplifier != -1) {
            switch (miningFatigueAmplifier) {
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
        if (player.isInsideLiquid() && player.getArmorInventory().getHelmet().getEnchantment(EnchantmentAquaAffinity.class) == null) {
            toolStrength /= 5.0F;
        }

        // When not onground
        if (!player.isOnGround()) {
            toolStrength /= 5.0F;
        }

        // Can't be broken
        float result;
        if (!foundInterface && !canBeBrokenWithHand()) {
            result = toolStrength / base / 100F;
        } else {
            result = toolStrength / base / 30F;
        }

        long time = (long) ((1F / result) * 50F);
        if (time < 50) {
            time = 50;
        }

        return time;
    }

    public Class<? extends ItemStack>[] getToolInterfaces() {
        return null;
    }

    public boolean onBreak(boolean creative) {
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
    public List<ItemStack> getDrops(ItemStack itemInHand) {
        // TODO: New system
        ItemStack drop = this.world.getServer().getItems().create(this.identifier.getBlockId(), (short) 0, (byte) 1, null);
        return Lists.newArrayList(drop);
    }

    public void onEntityCollision(Entity entity) {

    }

    public void onEntityStanding(EntityLiving entityLiving) {

    }

    boolean isCorrectTool(ItemStack itemInHand) {
        for (Class<? extends ItemStack> aClass : getToolInterfaces()) {
            if (aClass.isAssignableFrom(itemInHand.getClass())) {
                return true;
            }
        }

        return false;
    }

    public abstract float getBlastResistance();

    public void setBlockId(String blockId, String ... ignoreStateKeys) {
        this.identifier = BlockRuntimeIDs.toBlockIdentifier(blockId, this.identifier == null ? null : this.identifier.getStates(), ignoreStateKeys);
        if (isPlaced()) {
            this.updateBlock();
        }
    }

    public boolean canBeFlowedInto() {
        return false;
    }

    public Vector addVelocity(Entity entity, Vector pushedByBlocks) {
        return pushedByBlocks;
    }

    public boolean intersectsWith(AxisAlignedBB boundingBox) {
        for (AxisAlignedBB axisAlignedBB : getBoundingBox()) {
            if (axisAlignedBB.intersectsWith(boundingBox)) {
                return true;
            }
        }

        return false;
    }

    public boolean ready() {
        return this.ready;
    }

    public Object getState(String key) {
        return this.identifier.getStates().get(key);
    }

    public <S> void setState(String key, S value) {
        this.ensureIdentifier(null);
        this.identifier = BlockRuntimeIDs.change(this.identifier, key, value);
    }

    public void ensureIdentifier(String idHint) {
        if (this.identifier == null) {
            // Resolve idHint if given
            if (idHint != null) {
                this.identifier = BlockRuntimeIDs.toBlockIdentifier(idHint, null);
                return;
            }

            RegisterInfo[] annotations = this.getClass().getAnnotationsByType(RegisterInfo.class);
            if (annotations.length == 1) {
                this.identifier = BlockRuntimeIDs.toBlockIdentifier(annotations[0].sId(), null);
            } else {
                for (RegisterInfo annotation : annotations) {
                    if (annotation.def()) {
                        this.identifier = BlockRuntimeIDs.toBlockIdentifier(annotation.sId(), null);
                    }
                }
            }
        }
    }

    public int getRuntimeId() {
        return this.identifier.getRuntimeId();
    }

    public String getBlockId() {
        return this.identifier.getBlockId();
    }

}
