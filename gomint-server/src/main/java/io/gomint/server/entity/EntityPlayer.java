/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.entity;

import io.gomint.ChatColor;
import io.gomint.GoMint;
import io.gomint.command.CommandOutput;
import io.gomint.command.PlayerCommandSender;
import io.gomint.enchant.EnchantmentKnockback;
import io.gomint.enchant.EnchantmentSharpness;
import io.gomint.player.ChatType;
import io.gomint.entity.Entity;
import io.gomint.entity.potion.PotionEffect;
import io.gomint.event.entity.EntityDamageByEntityEvent;
import io.gomint.event.entity.EntityDamageEvent;
import io.gomint.event.entity.EntityTeleportEvent;
import io.gomint.event.inventory.InventoryCloseEvent;
import io.gomint.event.inventory.InventoryOpenEvent;
import io.gomint.event.player.*;
import io.gomint.gui.Form;
import io.gomint.gui.FormListener;
import io.gomint.math.Vector;
import io.gomint.math.*;
import io.gomint.player.DeviceInfo;
import io.gomint.plugin.Plugin;
import io.gomint.server.GoMintServer;
import io.gomint.server.enchant.EnchantmentProcessor;
import io.gomint.server.entity.metadata.MetadataContainer;
import io.gomint.server.entity.passive.EntityHuman;
import io.gomint.server.entity.projectile.EntityFishingHook;
import io.gomint.server.inventory.*;
import io.gomint.server.inventory.item.ItemAir;
import io.gomint.server.inventory.item.ItemStack;
import io.gomint.server.maintenance.performance.LoginPerformance;
import io.gomint.server.network.PlayerConnection;
import io.gomint.server.network.PlayerConnectionState;
import io.gomint.server.network.packet.*;
import io.gomint.server.network.packet.PacketRespawnPosition.RespawnState;
import io.gomint.server.permission.PermissionManager;
import io.gomint.server.player.EntityVisibilityManager;
import io.gomint.server.plugin.EventCaller;
import io.gomint.server.scoreboard.Scoreboard;
import io.gomint.server.util.CallerDetectorUtil;
import io.gomint.server.util.EnumConnectors;
import io.gomint.server.world.ChunkAdapter;
import io.gomint.server.world.CoordinateUtils;
import io.gomint.server.world.LevelEvent;
import io.gomint.server.world.WorldAdapter;
import io.gomint.server.world.block.Block;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.world.Chunk;
import io.gomint.world.Gamemode;
import io.gomint.world.Particle;
import io.gomint.world.ParticleData;
import io.gomint.world.Sound;
import io.gomint.world.SoundData;
import it.unimi.dsi.fastutil.bytes.Byte2ObjectMap;
import it.unimi.dsi.fastutil.bytes.Byte2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ByteMap;
import it.unimi.dsi.fastutil.objects.Object2ByteOpenHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.net.InetSocketAddress;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * The entity implementation for players. Players are considered living entities even though they
 * do not possess an AI. But as they still move around freely and in an unpredictable fashion
 * (and because we do hope players playing on GoMint actually are living entities) EntityPlayer
 * still inherits from EntityLiving. Their attached behaviour will simply contain no AI states
 * and will not be started either.
 *
 * @author BlackyPaw
 * @version 1.0
 */
public class EntityPlayer extends EntityHuman implements io.gomint.entity.EntityPlayer, InventoryHolder, PlayerCommandSender {

    private static final Logger LOGGER = LoggerFactory.getLogger(EntityPlayer.class);

    private final PlayerConnection connection;
    private io.gomint.permission.PermissionManager permissionManager;
    private boolean isUsingDefaultPermissionManager;
    private final EntityVisibilityManager entityVisibilityManager = new EntityVisibilityManager(this);
    private int viewDistance = 4;
    private Queue<ChunkAdapter> chunkSendQueue = new LinkedBlockingQueue<>();
    private boolean hasCompletedLogin;

    // EntityPlayer Information
    private Gamemode gamemode = Gamemode.SURVIVAL;
    private AdventureSettings adventureSettings;
    private Entity hoverEntity;
    private Location respawnPosition = null;
    private Locale locale;

    // Hidden players
    private Set<Long> hiddenPlayers;

    // Container handling
    private Byte2ObjectMap<ContainerInventory> windowIds;
    private Object2ByteMap<ContainerInventory> containerIds;

    // Inventory
    private Inventory cursorInventory;
    private Inventory offhandInventory;
    private EnderChestInventory enderChestInventory;

    // Crafting
    private Inventory craftingInventory;
    private CraftingInputInventory craftingInputInventory;
    private Inventory craftingResultInventory;

    // Enchantment table
    private EnchantmentTableInventory enchantmentInputInventory;
    private Inventory enchantmentOutputInventory;
    private EnchantmentProcessor enchantmentProcessor;

    // Block break data
    private BlockPosition breakVector;
    private long startBreak;
    private long breakTime;

    // Update data
    private Set<BlockPosition> blockUpdates = new HashSet<>();
    private Location teleportPosition = null;

    // Form stuff
    private int formId;
    private Int2ObjectMap<io.gomint.server.gui.Form> forms = new Int2ObjectOpenHashMap<>();
    private Int2ObjectMap<io.gomint.server.gui.FormListener> formListeners = new Int2ObjectOpenHashMap<>();

    // Server settings
    private int serverSettingsForm = -1;

    // Entity data
    private EntityFishingHook fishingHook;
    private long lastPickupXP;
    private Location spawnLocation;

    // Item usage ticking
    private long actionStart = -1;

    // Exp
    private int xp;

    // Performance metrics
    private LoginPerformance loginPerformance;

    // Movement delay
    private Location nextMovement;
    private boolean spawnPlayers;

    // Scoreboard
    private Scoreboard scoreboard;

    private final EventCaller eventCaller;

    /**
     * Constructs a new player entity which will be spawned inside the specified world.
     *
     * @param world      The world the entity should spawn in
     * @param connection The specific player connection associated with this entity
     * @param username   The name the user has chosen
     * @param xboxId     The xbox id from xbox live which has logged in
     * @param uuid       The uuid which has been sent from the client
     * @param locale     language of the player
     */
    public EntityPlayer(WorldAdapter world,
                        PlayerConnection connection,
                        String username,
                        String xboxId,
                        UUID uuid,
                        Locale locale,
                        EventCaller eventCaller) {
        super(EntityType.PLAYER, world);
        this.connection = connection;
        this.eventCaller = eventCaller;

        // EntityHuman stuff
        this.setPlayerData(username, username, xboxId, uuid);

        this.locale = locale;
        this.adventureSettings = new AdventureSettings(this);

        // Performance stuff
        this.loginPerformance = new LoginPerformance();

        // Permission stuff
        this.permissionManager = new PermissionManager(this);
        this.isUsingDefaultPermissionManager = true;
    }

    // ==================================== ACCESSORS ==================================== //

    /**
     * Gets the view distance set by the player.
     *
     * @return The view distance set by the player
     */
    @Override
    public int getViewDistance() {
        return this.viewDistance;
    }

    /**
     * Sets the view distance used to calculate the chunk to be sent to the player.
     *
     * @param viewDistance The view distance to set
     */
    public void setViewDistance(int viewDistance) {
        int tempViewDistance = Math.min(viewDistance, this.world.getConfig().getViewDistance());
        if (this.viewDistance != tempViewDistance) {
            this.viewDistance = tempViewDistance;
        }

        if (this.connection.getState() == PlayerConnectionState.LOGIN) {
            this.connection.addToSendQueue(new PacketBiomeDefinitionList());
            this.connection.sendPlayState(PacketPlayState.PlayState.SPAWN);
            this.getLoginPerformance().setChunkStart(this.world.getServer().getCurrentTickTime());
        }

        this.connection.onViewDistanceChanged();
    }

    @Override
    public void transfer(String host, int port) {
        PacketTransfer packetTransfer = new PacketTransfer();
        packetTransfer.setAddress(host);
        packetTransfer.setPort(port);
        this.connection.addToSendQueue(packetTransfer);
    }

    @Override
    public int getPing() {
        return this.connection.getPing();
    }

    /**
     * Gets the connection associated with this player entity.
     *
     * @return The connection associated with this player entity
     */
    public PlayerConnection getConnection() {
        return this.connection;
    }

    private void updateGamemode() {
        int gameModeNumber = EnumConnectors.GAMEMODE_CONNECTOR.convert(this.gamemode).getMagicNumber();

        PacketSetGamemode packetSetGamemode = new PacketSetGamemode();
        packetSetGamemode.setGameMode(gameModeNumber == 1 ? 1 : 0);
        this.connection.addToSendQueue(packetSetGamemode);

        this.sendAdventureSettings();

        // Set fly
        this.metadataContainer.setDataFlag(MetadataContainer.DATA_INDEX, EntityFlag.CAN_FLY,
            this.gamemode == Gamemode.SPECTATOR || this.gamemode == Gamemode.CREATIVE);

        // Set invis
        if (this.gamemode == Gamemode.SPECTATOR) {
            this.setInvisible(true);
        } else {
            // Check for invis potion effect
            if (!this.hasEffect(PotionEffect.INVISIBILITY)) {
                this.setInvisible(false);
            }
        }
    }

    /**
     * Send adventure settings based on the gamemode
     */
    public void sendAdventureSettings() {
        int gameModeNumber = EnumConnectors.GAMEMODE_CONNECTOR.convert(this.gamemode).getMagicNumber();

        // Recalc adventure settings
        this.adventureSettings.setWorldImmutable(gameModeNumber == 0x03);
        this.adventureSettings.setCanFly((gameModeNumber & 0x01) > 0);
        this.adventureSettings.setNoClip(gameModeNumber == 0x03);
        this.adventureSettings.setFlying(gameModeNumber == 0x03);
        this.adventureSettings.setAttackMobs(gameModeNumber < 0x02);
        this.adventureSettings.setAttackPlayers(gameModeNumber < 0x02);
        this.adventureSettings.setNoPvP(gameModeNumber == 0x03);
        this.adventureSettings.update();
    }

    @Override
    public Gamemode getGamemode() {
        return this.gamemode;
    }

    @Override
    public void setGamemode(Gamemode gamemode) {
        this.gamemode = gamemode;
        this.updateGamemode();
    }

    @Override
    public boolean isOp() {
        return this.adventureSettings.isOperator();
    }

    @Override
    public void setOp(boolean value) {
        this.adventureSettings.setOperator(value);
        this.sendAdventureSettings();

        // Grant all permissions / notify the permission manager
        this.permissionManager.toggleOp();
    }

    @Override
    public void hidePlayer(io.gomint.entity.EntityPlayer player) {
        // Never hide myself (client crashes when this is done)
        if (player.equals(this)) {
            LOGGER.warn("You can't hide a player itself. Please tell the plugin author to remove the hidePlayer call");
            return;
        }

        EntityPlayer other = (EntityPlayer) player;

        if (this.hiddenPlayers == null) {
            this.hiddenPlayers = new HashSet<>();
        }

        this.hiddenPlayers.add(other.getEntityId());

        LOGGER.debug("Player {} hides {} from now on", this.getName(), player.getName());

        // Remove the entity clientside
        this.entityVisibilityManager.removeEntity(other);

        // Remove from player list
        PacketPlayerlist packetPlayerlist = new PacketPlayerlist();
        packetPlayerlist.setMode((byte) 1);
        packetPlayerlist.setEntries(new ArrayList<>() {{
            add(new PacketPlayerlist.Entry(other));
        }});
        getConnection().addToSendQueue(packetPlayerlist);
    }

    @Override
    public void showPlayer(io.gomint.entity.EntityPlayer player) {
        if (this.hiddenPlayers == null) {
            return;
        }

        if (this.hiddenPlayers.remove(player.getEntityId())) {
            LOGGER.debug("Player {} shows {} from now on", this.getName(), player.getName());

            EntityPlayer other = (EntityPlayer) player;

            // Send tablist and spawn packet
            PacketPlayerlist packetPlayerlist = new PacketPlayerlist();
            packetPlayerlist.setMode((byte) 0);
            packetPlayerlist.setEntries(new ArrayList<>() {{
                add(new PacketPlayerlist.Entry(other));
            }});
            getConnection().addToSendQueue(packetPlayerlist);

            // Check bounds
            Chunk playerChunk = this.getChunk();
            Chunk chunk = other.getChunk();
            if (Math.abs(playerChunk.getX() - chunk.getX()) <= this.getViewDistance() &&
                Math.abs(playerChunk.getZ() - chunk.getZ()) <= this.getViewDistance()) {
                this.entityVisibilityManager.addEntity(other);
            }
        }
    }

    @Override
    public boolean isHidden(io.gomint.entity.EntityPlayer player) {
        return this.hiddenPlayers != null && this.hiddenPlayers.contains(player.getEntityId());
    }

    @Override
    public void teleport(Location to, EntityTeleportEvent.Cause cause) {
        // Only teleport when online
        if (!isOnline()) {
            return;
        }

        EntityTeleportEvent entityTeleportEvent = new EntityTeleportEvent(this, this.getLocation(), to, cause);
        this.world.getServer().getPluginManager().callEvent(entityTeleportEvent);
        if (entityTeleportEvent.isCancelled()) {
            return;
        }

        Location from = getLocation();

        // Reset chunks
        this.connection.resetQueuedChunks();

        // Check if we need to change worlds
        if (!to.getWorld().equals(from.getWorld())) {
            // Despawn entities first
            this.entityVisibilityManager.clear();

            // Change worlds
            getWorld().removePlayer(this);
            this.setWorld((WorldAdapter) to.getWorld());
            this.world.spawnEntityAt(this, to.getX(), to.getY(), to.getZ(), to.getYaw(), to.getPitch());

            // Be sure to get rid of all loaded chunks
            this.connection.resetPlayerChunks();

            // Send all packets needed for a world switch
            this.world.playerSwitched(this);
        }

        // Check for attached entities
        if (!this.getAttachedEntities().isEmpty()) {
            Chunk chunk = this.getChunk();
            for (Entity entity : new HashSet<>(this.getAttachedEntities())) {
                if (entity instanceof EntityPlayer) {
                    EntityPlayer player = (EntityPlayer) entity;
                    Chunk playerChunk = player.getChunk();
                    if (Math.abs(playerChunk.getX() - chunk.getX()) > player.getViewDistance() &&
                        Math.abs(playerChunk.getZ() - chunk.getZ()) > player.getViewDistance()) {
                        player.getEntityVisibilityManager().removeEntity(this);
                    }
                }
            }
        }

        // Set the new location
        this.setAndRecalcPosition(to);

        // Force load the new spawn chunk
        this.getChunk(); // This getChunk uses loadChunk so it will generate or load from disc if needed

        // Move the client
        this.connection.sendMovePlayer(to);

        // Send chunks to the client
        this.connection.checkForNewChunks(from, true);
        this.fallDistance = 0;

        // Tell the movement handler to force this position to the client
        this.teleportPosition = to;
    }

    @Override
    public void addHunger(float amount) {
        PlayerFoodLevelChangeEvent foodLevelChangeEvent = new PlayerFoodLevelChangeEvent(
            this, amount
        );
        this.world.getServer().getPluginManager().callEvent(foodLevelChangeEvent);

        if (!foodLevelChangeEvent.isCancelled()) {
            super.addHunger(amount);
        } else {
            this.resendAttributes();
        }
    }

    /**
     * Queue which holds chunks to be sent to the client
     *
     * @return queue with chunks to be sent to the client
     */
    public Queue<ChunkAdapter> getChunkSendQueue() {
        return this.chunkSendQueue;
    }

    // ==================================== UPDATING ==================================== //

    @Override
    public void update(long currentTimeMS, float dT) {
        // Move first
        if (this.nextMovement != null) {
            Location from = this.getLocation();
            PlayerMoveEvent playerMoveEvent = this.eventCaller.callEvent(new PlayerMoveEvent(this, from, this.nextMovement));

            if (playerMoveEvent.isCancelled()) {
                playerMoveEvent.setTo(playerMoveEvent.getFrom());
            }

            Location to = playerMoveEvent.getTo();
            if (to.getX() != this.nextMovement.getX() || to.getY() != this.nextMovement.getY() || to.getZ() != this.nextMovement.getZ() ||
                !to.getWorld().equals(this.nextMovement.getWorld()) || to.getYaw() != this.nextMovement.getYaw() ||
                to.getPitch() != this.nextMovement.getPitch() || to.getHeadYaw() != this.nextMovement.getHeadYaw()) {
                this.teleport(to);
            } else {
                float moveX = to.getX() - from.getX();
                float moveY = to.getY() - from.getY();
                float moveZ = to.getZ() - from.getZ();

                // Try to at least move the gravitation down
                Vector moved = this.safeMove(moveX, moveY, moveZ);

                // Exhaustion
                float distance = (float) Math.sqrt(moved.getX() * moved.getX() + moved.getZ() * moved.getZ());
                if (distance > 0.01f) {
                    if (this.onGround) {
                        if (this.isSprinting()) {
                            this.exhaust((0.1f * distance), PlayerExhaustEvent.Cause.SPRINTING);
                        } else if (this.isOnGround()) {
                            this.exhaust((0.01f * distance), PlayerExhaustEvent.Cause.WALKING);
                        }
                    }
                }

                this.setPitch(to.getPitch());
                this.setYaw(to.getYaw());
                this.setHeadYaw(to.getHeadYaw());
            }

            boolean changeWorld = !to.getWorld().equals(from.getWorld());
            boolean changeXZ = (int) from.getX() != (int) to.getX() || (int) from.getZ() != (int) to.getZ();
            boolean changeY = (int) from.getY() != (int) to.getY();

            if (changeWorld || changeXZ || changeY) {
                if (changeWorld || changeXZ) {
                    this.connection.checkForNewChunks(from, false);
                }

                // Check for interaction
                Block block = from.getWorld().getBlockAt(from.toBlockPosition());
                block.gotOff(this);

                block = to.getWorld().getBlockAt(to.toBlockPosition());
                block.stepOn(this);
            }

            this.nextMovement = null;
        }

        super.update(currentTimeMS, dT);

        // Update permissions
        if (this.isUsingDefaultPermissionManager) {
            ((PermissionManager) this.permissionManager).update(currentTimeMS, dT);
        }

        if (this.isDead() || this.getHealth() <= 0) {
            return;
        }

        // Look around
        Collection<Entity> nearbyEntities = this.world.getNearbyEntities(this.boundingBox.grow(1, 0.5f, 1), this, null);
        if (nearbyEntities != null) {
            for (Entity nearbyEntity : nearbyEntities) {
                io.gomint.server.entity.Entity implEntity = (io.gomint.server.entity.Entity) nearbyEntity;
                implEntity.onCollideWithPlayer(this);
            }
        }

        // Update attributes which are flagged as dirty
        this.updateAttributes();

        // Check for sprint, skip if player is in Creative mode
        if (this.getGamemode() != Gamemode.CREATIVE) {
            if (this.getHunger() <= 6 && this.isSprinting()) {
                this.setSprinting(false);
            }
        }
    }

    @Override
    public AxisAlignedBB getBoundingBox() {
        return this.boundingBox;
    }

    @Override
    public void openInventory(io.gomint.inventory.Inventory inventory) {
        if (inventory instanceof ContainerInventory) {
            InventoryOpenEvent event = new InventoryOpenEvent(this, inventory);
            this.getWorld().getServer().getPluginManager().callEvent(event);

            if (event.isCancelled()) {
                return;
            }

            // We need to generate a window id for the client
            byte foundId = -1;
            for (byte i = WindowMagicNumbers.FIRST.getId(); i < WindowMagicNumbers.LAST.getId(); i++) {
                if (!this.windowIds.containsKey(i)) {
                    foundId = i;
                    break;
                }
            }

            // No id found?
            if (foundId == -1) {
                return;
            }

            // Trigger open
            ContainerInventory containerInventory = (ContainerInventory) inventory;
            this.windowIds.put(foundId, containerInventory);
            this.containerIds.put(containerInventory, foundId);
            containerInventory.addViewer(this, foundId);
        }
    }

    @Override
    public void closeInventory(io.gomint.inventory.Inventory inventory) {
        if (inventory instanceof ContainerInventory) {
            byte windowId = this.getWindowId((ContainerInventory) inventory);
            if (windowId != 0) {
                this.closeInventory(windowId);

                // Tell the client to close it
                PacketContainerClose packetContainerClose = new PacketContainerClose();
                packetContainerClose.setWindowId(windowId);
                this.connection.addToSendQueue(packetContainerClose);
            }
        }
    }

    /**
     * Get the virtual inventory for the cursor item
     *
     * @return the players cursor item
     */
    public Inventory getCursorInventory() {
        return this.cursorInventory;
    }

    /**
     * Get offhand inventory. This inventory only has one slot
     *
     * @return current offhand inventory
     */
    public Inventory getOffhandInventory() {
        return this.offhandInventory;
    }

    /**
     * Check for attribute updates and send them to the player if needed
     */
    private void updateAttributes() {
        PacketUpdateAttributes updateAttributes = null;

        for (AttributeInstance instance : attributes.values()) {
            if (instance.isDirty()) {
                if (updateAttributes == null) {
                    updateAttributes = new PacketUpdateAttributes();
                    updateAttributes.setEntityId(this.getEntityId());
                }

                updateAttributes.addAttributeInstance(instance);
            }
        }

        if (updateAttributes != null) {
            updateAttributes.setTick(this.getWorld().getServer().getCurrentTickTime() / 50);
            this.connection.addToSendQueue(updateAttributes);
        }
    }

    /**
     * Force send all attributes
     */
    public void resendAttributes() {
        PacketUpdateAttributes updateAttributes = new PacketUpdateAttributes();
        updateAttributes.setEntityId(this.getEntityId());
        updateAttributes.setTick(this.getWorld().getServer().getCurrentTickTime() / 50);

        for (AttributeInstance instance : attributes.values()) {
            updateAttributes.addAttributeInstance(instance);
        }

        this.connection.addToSendQueue(updateAttributes);
    }

    /**
     * Send all data which the client needs before getting chunks
     */
    public void prepareEntity() {
        this.windowIds = new Byte2ObjectOpenHashMap<>();
        this.containerIds = new Object2ByteOpenHashMap<>();

        // Inventories
        this.inventory = new PlayerInventory(this.world.getServer().getItems(), this);
        this.armorInventory = new ArmorInventory(this.world.getServer().getItems(), this);

        this.cursorInventory = new CursorInventory(this.world.getServer().getItems(), this);
        this.offhandInventory = new OffhandInventory(this.world.getServer().getItems(), this);
        this.enderChestInventory = new EnderChestInventory(this.world.getServer().getItems(),  this );

        this.craftingInventory = new CraftingInputInventory(this.world.getServer().getItems(), this);
        this.craftingInputInventory = new CraftingInputInventory(this.world.getServer().getItems(), this);
        this.craftingResultInventory = new OneSlotInventory(this.world.getServer().getItems(), this);

        this.enchantmentOutputInventory = new OneSlotInventory(this.world.getServer().getItems(), this);

        // Load from world
        if (!this.world.loadPlayer(this)) {
            // Get default spawn
            this.setAndRecalcPosition(this.getSpawnLocation() != null ? this.getSpawnLocation() : this.world.getSpawnLocation());
        }

        // Send world init data
        this.connection.sendWorldTime(this.world.getTimeAsTicks());
        this.connection.sendWorldInitialization(this.getEntityId());
        this.connection.addToSendQueue(new PacketItemComponent());
        this.connection.sendSpawnPosition();
        // this.connection.sendWorldTime(this.world.getTimeAsTicks());
        this.connection.addToSendQueue(new PacketAvailableEntityIdentifiers());

        this.connection.sendDifficulty();
        this.connection.sendCommandsEnabled();

        // Set spawn
        if (this.connection.getEntity().getSpawnLocation() == null) {
            this.connection.getEntity().setSpawnLocation(this.connection.getEntity().getWorld().getSpawnLocation());
        }

        // Send adventure settings
        this.sendAdventureSettings();

        // Attributes
        this.updateAttributes();

        this.inventory.addViewer(this);
        this.armorInventory.addViewer(this);

        this.cursorInventory.addViewer(this);
        this.offhandInventory.addViewer(this);
        this.enderChestInventory.addViewer(this);

        this.craftingInventory.addViewer(this);
        this.craftingInputInventory.addViewer(this);
        this.craftingResultInventory.addViewer(this);

        this.enchantmentOutputInventory.addViewer(this);

        // Send entity metadata
        this.sendData(this);

        this.connection.getServer().getCreativeInventory().addViewer(this);

        PacketPlayerlist playerlist = new PacketPlayerlist();
        playerlist.setMode((byte) 0);
        playerlist.setEntries(new ArrayList<>() {{
            add(new PacketPlayerlist.Entry(EntityPlayer.this));
        }});
        this.getConnection().addToSendQueue(playerlist);

        // Send all recipes
        this.connection.addToSendQueue(this.world.getServer().getRecipeManager().getCraftingRecipesBatch());

        LOGGER.debug("Did send all prepare entity data");
    }

    @Override
    protected boolean shouldMove() {
        return false;
    }

    /**
     * Check if the player can interact with the given position
     *
     * @param position    to check for
     * @param maxDistance for which we check
     * @return true if the player can interact, false if not
     */
    public boolean canInteract(Vector position, int maxDistance) {
        // Distance
        Vector eyePosition = this.getPosition().add(0, this.getEyeHeight(), 0);
        if (eyePosition.distanceSquared(position) > MathUtils.square(maxDistance)) {
            return false;
        }

        // Direction
        Vector playerPosition = this.getPosition();
        Vector2 directionPlane = this.getDirectionPlane();
        float dot = directionPlane.dot(new Vector2(eyePosition.getX(), eyePosition.getZ()));
        float dot1 = directionPlane.dot(new Vector2(playerPosition.getX(), playerPosition.getZ()));
        return (dot1 - dot) >= -(MathUtils.SQRT_3 / 2);
    }

    /**
     * Remove player from PlayerList and remove from global inventories etc.
     */
    public void cleanup() {
        this.connection.getServer().getCreativeInventory().removeViewer(this);
        this.connection.getServer().getPlayersByUUID().remove(this.getUUID());

        Block block = this.world.getBlockAt(this.getPosition().toBlockPosition());
        block.gotOff(this);

        // Remove from player list
        PacketPlayerlist packetPlayerlist = new PacketPlayerlist();
        packetPlayerlist.setMode((byte) 1);
        packetPlayerlist.setEntries(new ArrayList<>() {{
            add(new PacketPlayerlist.Entry(EntityPlayer.this));
        }});

        // Cleanup the visibility manager
        this.entityVisibilityManager.clear();

        // Check all entities
        for (WorldAdapter worldAdapter : this.connection.getServer().getWorldManager().getWorlds()) {
            worldAdapter.iterateEntities(Entity.class, entity -> {
                if (entity instanceof EntityPlayer) {
                    EntityPlayer entityPlayer = (EntityPlayer) entity;
                    if (!entityPlayer.equals(EntityPlayer.this)) {
                        entityPlayer.getConnection().addToSendQueue(packetPlayerlist);
                    }

                    // Check if player did hide this one
                    if (entityPlayer.hiddenPlayers != null) {
                        entityPlayer.hiddenPlayers.remove(getEntityId());
                    }

                    // Check if mouseover is the entity
                    if (entityPlayer.hoverEntity != null && entityPlayer.hoverEntity.equals(EntityPlayer.this)) {
                        entityPlayer.hoverEntity = null;
                    }

                    entityPlayer.entityVisibilityManager.removeEntity(EntityPlayer.this);
                } else {
                    entity.hideFor(EntityPlayer.this);
                }
            });
        }

        if (this.containerIds != null) {
            // Check all open inventories
            Object2ByteMap.FastEntrySet<ContainerInventory> fastEntrySet = (Object2ByteMap.FastEntrySet<ContainerInventory>) this.containerIds.object2ByteEntrySet();
            fastEntrySet.fastIterator().forEachRemaining(containerInventoryEntry -> containerInventoryEntry.getKey().removeViewer(EntityPlayer.this));
        }
    }

    /**
     * Get the window id of specified inventory
     *
     * @param inventory which we want to get the window id for
     * @return window id of the container
     */
    public byte getWindowId(ContainerInventory inventory) {
        return this.containerIds.getByte(inventory);
    }

    /**
     * Close a container inventory
     *
     * @param windowId which should be closed
     */
    public void closeInventory(byte windowId) {
        ContainerInventory containerInventory = this.windowIds.remove(windowId);
        if (containerInventory != null) {
            containerInventory.removeViewer(this);
            this.containerIds.removeByte(containerInventory);

            InventoryCloseEvent inventoryCloseEvent = new InventoryCloseEvent(this, containerInventory);
            this.getWorld().getServer().getPluginManager().callEvent(inventoryCloseEvent);

            PacketContainerClose packetContainerClose = new PacketContainerClose();
            packetContainerClose.setWindowId(windowId);
            this.connection.addToSendQueue(packetContainerClose);
        }
    }

    /**
     * Get a container by its id
     *
     * @param windowId which should be looked up
     * @return container inventory or null when not found
     */
    public ContainerInventory getContainerId(byte windowId) {
        return this.windowIds.get(windowId);
    }

    @Override
    public void sendMessage(String message) {
        PacketText packetText = new PacketText();
        packetText.setMessage(message);
        packetText.setDeviceId(this.getDeviceInfo().getDeviceId());
        packetText.setType(PacketText.Type.CLIENT_MESSAGE);
        this.connection.addToSendQueue(packetText);
    }

    @Override
    public void sendMessage(ChatType type, String... message) {
        PacketText packetText = new PacketText();
        packetText.setMessage(message[0]);
        packetText.setDeviceId(this.getDeviceInfo().getDeviceId());
        switch (type) {
            case TIP:
                packetText.setType(PacketText.Type.TIP_MESSAGE);
                break;
            case NORMAL:
                packetText.setType(PacketText.Type.CLIENT_MESSAGE);
                break;
            case SYSTEM:
                packetText.setType(PacketText.Type.SYSTEM_MESSAGE);
                break;
            case POPUP:
                packetText.setType(PacketText.Type.POPUP_NOTICE);

                if (message.length > 1) {
                    packetText.setSubtitle(message[1]);
                } else {
                    packetText.setSubtitle("");
                }

                break;
            default:
                break;
        }

        this.connection.addToSendQueue(packetText);
    }

    @Override
    public boolean hasPermission(String permission) {
        return this.permissionManager.hasPermission(permission);
    }

    @Override
    public boolean hasPermission(String permission, boolean defaultValue) {
        return this.permissionManager.hasPermission(permission, defaultValue);
    }

    @Override
    public void sendCommands() {
        // Send commands
        PacketAvailableCommands packetAvailableCommands = this.connection.getServer().
            getPluginManager().getCommandManager().createPacket(this);
        this.connection.addToSendQueue(packetAvailableCommands);
    }

    /**
     * Basicly a override of the {@link EntityHuman#exhaust(float)} method with a event call in it.
     *
     * @param amount of exhaustion
     * @param cause  of exhaustion
     */
    @Override
    public void exhaust(float amount, PlayerExhaustEvent.Cause cause) {
        if (this.gamemode == Gamemode.SURVIVAL) {
            PlayerExhaustEvent exhaustEvent = new PlayerExhaustEvent(this, amount, cause);
            this.world.getServer().getPluginManager().callEvent(exhaustEvent);

            if (exhaustEvent.isCancelled()) {
                this.resendAttributes();
                return;
            }

            super.exhaust(exhaustEvent.getAdditionalAmount());
        } else {
            if (this.getExhaustion() != 0) {
                this.setExhaustion(0);
            }
        }
    }

    /**
     * Handle a jump
     */
    public void jump() {
        // Jumping is only handled for exhaustion it seems
        if (this.isSprinting()) {
            this.exhaust(0.8f, PlayerExhaustEvent.Cause.SPRINT_JUMP);
        } else {
            this.exhaust(0.2f, PlayerExhaustEvent.Cause.JUMP);
        }
    }

    /**
     * Attack another entity with the item currently in hand
     *
     * @param target which should be attacked
     * @return true when damage has been dealt, false when not
     */
    public boolean attackWithItemInHand(Entity target) {
        if (target instanceof io.gomint.server.entity.Entity) {
            io.gomint.server.entity.Entity targetEntity = (io.gomint.server.entity.Entity) target;

            // Check if the target can be attacked
            if (targetEntity.canBeAttackedWithAnItem() && !targetEntity.isInvulnerableFrom(this)) {
                boolean success = false;

                // Get this entity attack damage
                EntityDamageEvent.DamageSource damageSource = EntityDamageEvent.DamageSource.ENTITY_ATTACK;
                float damage = this.getAttribute(Attribute.ATTACK_DAMAGE);

                EnchantmentSharpness sharpness = this.getInventory().getItemInHand().getEnchantment(EnchantmentSharpness.class);
                if (sharpness != null) {
                    damage += sharpness.getLevel() * 1.25f;
                }

                // Check for knockback stuff
                int knockbackLevel = 0;

                if (this.isSprinting()) {
                    knockbackLevel++;
                }

                EnchantmentKnockback knockback = this.getInventory().getItemInHand().getEnchantment(EnchantmentKnockback.class);
                if (knockback != null) {
                    knockbackLevel += knockback.getLevel();
                }

                if (damage > 0) {
                    boolean crit = this.fallDistance > 0 && !this.onGround && !this.isOnLadder() && !this.isInsideLiquid();
                    if (crit && damage > 0.0f) {
                        damage *= 1.5;
                    }

                    // Check if target can absorb this damage
                    if ((success = targetEntity.damage(new EntityDamageByEntityEvent(targetEntity, this, damageSource, damage)))) {
                        // Apply knockback
                        if (knockbackLevel > 0) {
                            // Modify target velocity
                            Vector targetVelo = targetEntity.getVelocity();
                            targetEntity.setVelocity(targetVelo.add(
                                (float) (-Math.sin(this.getYaw() * (float) Math.PI / 180.0F) * (float) knockbackLevel * 0.5F),
                                0.1f,
                                (float) (Math.cos(this.getYaw() * (float) Math.PI / 180.0F) * (float) knockbackLevel * 0.5F)));

                            // Modify our velocity / movement
                            Vector ownVelo = this.getVelocity();
                            ownVelo.setX(ownVelo.getX() * 0.6F);
                            ownVelo.setZ(ownVelo.getZ() * 0.6F);
                            this.setVelocity(ownVelo);

                            if (!this.world.getServer().getServerConfig().getVanilla().isDisableSprintReset()) {
                                this.setSprinting(false);
                            }
                        }

                        targetEntity.broadCastMotion();
                    }
                }

                this.exhaust(0.3f, PlayerExhaustEvent.Cause.ATTACK);
                return success;
            }
        }

        return false;
    }

    @Override
    public boolean damage(EntityDamageEvent damageEvent) {
        // When allowFlight is on we don't need falling damage
        if (this.adventureSettings.isCanFly() && damageEvent.getDamageSource() == EntityDamageEvent.DamageSource.FALL) {
            return false;
        }

        // Can't touch this!
        return this.gamemode != Gamemode.CREATIVE && this.gamemode != Gamemode.SPECTATOR && super.damage(damageEvent);
    }

    @Override
    protected float applyArmorReduction(EntityDamageEvent damageEvent, boolean damageArmor) {
        if (damageEvent.getDamageSource() == EntityDamageEvent.DamageSource.FALL ||
            damageEvent.getDamageSource() == EntityDamageEvent.DamageSource.VOID ||
            damageEvent.getDamageSource() == EntityDamageEvent.DamageSource.DROWNING) {
            return damageEvent.getDamage();
        }

        float damage = damageEvent.getDamage();
        float maxReductionDiff = 25 - this.armorInventory.getTotalArmorValue();
        float amplifiedDamage = damage * maxReductionDiff;
        if (damageArmor) {
            this.armorInventory.damageEvenly(damage);
        }

        return amplifiedDamage / 25.0F;
    }

    @Override
    public void attach(EntityPlayer player) {
        super.attach(player);
        this.armorInventory.addViewer(player);

        // Send death animation if needed
        if (this.getHealth() <= 0) {
            PacketEntityEvent entityEvent = new PacketEntityEvent();
            entityEvent.setEntityId(this.getEntityId());
            entityEvent.setEventId(EntityEvent.DEATH.getId());
            player.getConnection().addToSendQueue(entityEvent);
        }
    }

    @Override
    public void detach(EntityPlayer player) {
        if (this.armorInventory != null) {
            this.armorInventory.removeViewer(player);
        }

        super.detach(player);
    }

    /**
     * Respawn this player
     */
    public void respawn() {
        // Event first
        PlayerRespawnEvent event = new PlayerRespawnEvent(this, this.respawnPosition);
        this.connection.getServer().getPluginManager().callEvent(event);

        if (event.isCancelled()) {
            PacketEntityEvent entityEvent = new PacketEntityEvent();
            entityEvent.setEntityId(this.getEntityId());
            entityEvent.setEventId(EntityEvent.DEATH.getId());
            this.connection.addToSendQueue(entityEvent);

            return;
        }

        // Check if we need to despawn first
        if (this.deadTimer != -1) {
            this.despawn();
            this.deadTimer = -1;
        }

        // Reset last damage stuff
        this.lastDamageEntity = null;
        this.lastDamageSource = null;
        this.lastDamage = 0;

        // Send metadata
        this.sendData(this);

        // Resend adventure settings
        this.adventureSettings.update();

        // Reset attributes
        this.resetAttributes();
        this.resendAttributes();

        // Remove all effects
        this.removeAllEffects();

        // Check for new chunks
        this.teleport(event.getRespawnLocation());
        this.respawnPosition = null;

        // Reset motion
        this.setVelocity(new Vector(0, 0, 0));

        // Send all inventories
        this.inventory.sendContents(this.connection);
        this.offhandInventory.sendContents(this.connection);
        this.armorInventory.sendContents(this.connection);

        PacketEntityEvent entityEvent = new PacketEntityEvent();
        entityEvent.setEntityId(this.getEntityId());
        entityEvent.setEventId(EntityEvent.RESPAWN.getId());

        // Update all other players
        for (io.gomint.entity.EntityPlayer player : this.world.getPlayers()) {
            EntityPlayer implPlayer = (EntityPlayer) player;
            implPlayer.getEntityVisibilityManager().updateEntity(this, this.getChunk());
        }

        // Apply item in hand stuff
        ItemStack itemInHand = (ItemStack) this.inventory.getItemInHand();
        itemInHand.gotInHand(this);
    }

    @Override
    protected void kill() {
        super.kill();

        // Send the death screen to us
        PacketEntityEvent entityEvent = new PacketEntityEvent();
        entityEvent.setEntityId(this.id);
        entityEvent.setEventId(EntityEvent.DEATH.getId());
        this.connection.addToSendQueue(entityEvent);

        // Prepare a death message
        String deathMessage = "";
        EntityDamageEvent.DamageSource cause = this.getLastDamageSource();
        switch (cause) {
            case ENTITY_ATTACK:
                deathMessage = this.getDisplayName() + " was slain by " + this.getLastDamageEntity().getNameTag();
                break;
            case FALL:
                deathMessage = this.getDisplayName() + " fell from a high place";
                break;
            case LAVA:
                deathMessage = this.getDisplayName() + " tried to swim in lava";
                break;
            case FIRE:
                deathMessage = this.getDisplayName() + " went up in flames";
                break;
            case VOID:
                deathMessage = this.getDisplayName() + " fell out of the world";
                break;
            case CACTUS:
                deathMessage = this.getDisplayName() + " was pricked to death";
                break;
            case STARVE:
                deathMessage = this.getDisplayName() + " starved to death";
                break;
            case ON_FIRE:
                deathMessage = this.getDisplayName() + " burned to death";
                break;
            case DROWNING:
                deathMessage = this.getDisplayName() + " drowned";
                break;
            case HARM_EFFECT:
                deathMessage = this.getDisplayName() + " was killed by magic";
                break;
            case ENTITY_EXPLODE:
                deathMessage = this.getDisplayName() + " blew up";
                break;
            case PROJECTILE:
                deathMessage = this.getDisplayName() + " has been shot";
                break;
            case API:
                deathMessage = this.getDisplayName() + " was killed by setting health to 0";
                break;
            case COMMAND:
                deathMessage = this.getDisplayName() + " died";
                break;
            default:
                deathMessage = this.getDisplayName() + " died for unknown reasons";
                break;
        }

        List<io.gomint.inventory.item.ItemStack> drops = this.getDrops();

        PlayerDeathEvent event = new PlayerDeathEvent(this, deathMessage, true, drops);
        this.connection.getServer().getPluginManager().callEvent(event);

        if (event.isDropInventory()) {
            for (io.gomint.inventory.item.ItemStack drop : event.getDrops()) {
                this.world.dropItem(this.getLocation(), drop);
            }

            this.inventory.clear();
            this.offhandInventory.clear();
            this.armorInventory.clear();
        }

        this.craftingInventory.clear();
        this.craftingInputInventory.clear();
        this.craftingResultInventory.clear();

        if (event.getDeathMessage() != null && !event.getDeathMessage().isEmpty()) {
            for (io.gomint.entity.EntityPlayer player : this.world.getPlayers()) {
                player.sendMessage(event.getDeathMessage());
            }
        }

        this.respawnPosition = this.world.getSpawnLocation().add(0, this.eyeHeight, 0);

        PacketRespawnPosition packetRespawnPosition = new PacketRespawnPosition();
        packetRespawnPosition.setPosition(this.respawnPosition);
        packetRespawnPosition.setEntityId(this.getEntityId());
        packetRespawnPosition.setState(RespawnState.SEARCHING_FOR_SPAWN);
        this.getConnection().addToSendQueue(packetRespawnPosition);
    }

    private List<io.gomint.inventory.item.ItemStack> getDrops() {
        List<io.gomint.inventory.item.ItemStack> drops = new ArrayList<>();

        for (io.gomint.inventory.item.ItemStack itemStack : this.inventory.getContents()) {
            if (!(itemStack instanceof ItemAir)) {
                drops.add(itemStack);
            }
        }

        for (io.gomint.inventory.item.ItemStack itemStack : this.offhandInventory.getContents()) {
            if (!(itemStack instanceof ItemAir)) {
                drops.add(itemStack);
            }
        }

        for (io.gomint.inventory.item.ItemStack itemStack : this.armorInventory.getContents()) {
            if (!(itemStack instanceof ItemAir)) {
                drops.add(itemStack);
            }
        }

        return drops;
    }

    @Override
    protected void checkIfCollided(float movX, float movY, float movZ, float dX, float dY, float dZ) {
        // Check if we are not on ground or we moved on y axis
        if (!this.onGround || movY != 0) {
            AxisAlignedBB bb = new AxisAlignedBB(this.boundingBox.getMinX(), this.boundingBox.getMinY() - 0.2f, this.boundingBox.getMinZ(),
                this.boundingBox.getMaxX(), this.boundingBox.getMaxY(), this.boundingBox.getMaxZ());

            // Check if we collided with a block
            this.onGround = this.world.getCollisionCubes(this, bb, false) != null;
        }

        this.isCollided = this.onGround;
    }

    @Override
    public boolean isOnline() {
        return this.connection.getEntity() != null;
    }

    @Override
    public Locale getLocale() {
        return this.locale;
    }

    @Override
    public void disconnect(String reason) {
        this.connection.disconnect(reason);
    }

    // ------- GUI stuff
    public void sendServerSettings() {
        if (this.serverSettingsForm != -1) {
            io.gomint.server.gui.Form form = this.forms.get(this.serverSettingsForm);

            PacketServerSettingsResponse response = new PacketServerSettingsResponse();
            response.setFormId(this.serverSettingsForm);
            response.setJson(form.toJSON().toJSONString());
            LOGGER.debug("Sending settings form: {}", response);
            this.connection.addToSendQueue(response);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <R> FormListener<R> showForm(Form<R> form) {
        int formId = this.formId++;
        io.gomint.server.gui.Form implForm = (io.gomint.server.gui.Form) form;

        this.forms.put(formId, implForm);

        io.gomint.server.gui.FormListener formListener = new io.gomint.server.gui.FormListener<R>();

        this.formListeners.put(formId, formListener);

        // Send packet for client
        String json = implForm.toJSON().toJSONString();
        PacketModalRequest packetModalRequest = new PacketModalRequest();
        packetModalRequest.setFormId(formId);
        packetModalRequest.setJson(json);
        this.connection.addToSendQueue(packetModalRequest);

        return formListener;
    }

    @Override
    public <R> FormListener<R> setSettingsForm(Form<R> form) {
        if (this.serverSettingsForm != -1) {
            this.removeSettingsForm();
        }

        int formId = this.formId++;
        io.gomint.server.gui.Form<R> implForm = (io.gomint.server.gui.Form<R>) form;

        this.forms.put(formId, implForm);

        io.gomint.server.gui.FormListener<R> formListener = new io.gomint.server.gui.FormListener<R>();

        this.formListeners.put(formId, formListener);
        this.serverSettingsForm = formId;
        return formListener;
    }

    @Override
    public void removeSettingsForm() {
        if (this.serverSettingsForm != -1) {
            this.forms.remove(this.serverSettingsForm);
            this.formListeners.remove(this.serverSettingsForm);
            this.serverSettingsForm = -1;
        }
    }

    public void parseGUIResponse(int formId, String json) {
        // Get the listener and the form
        Form form = this.forms.get(formId);
        if (form != null) {
            // Get listener
            io.gomint.server.gui.FormListener formListener = this.formListeners.get(formId);

            if (this.serverSettingsForm != formId) {
                this.forms.remove(formId);
                this.formListeners.remove(formId);
            }

            if (json.equals("null")) {
                formListener.getCloseConsumer().accept(null);
            } else {
                io.gomint.server.gui.Form implForm = (io.gomint.server.gui.Form) form;
                Object resp = implForm.parseResponse(json);
                if (resp == null) {
                    formListener.getCloseConsumer().accept(null);
                } else {
                    formListener.getResponseConsumer().accept(resp);
                }
            }
        }
    }

    @Override
    public void despawn() {
        for (Entity entity : new HashSet<>(this.getAttachedEntities())) {
            if (entity instanceof EntityPlayer) {
                ((EntityPlayer) entity).getEntityVisibilityManager().removeEntity(this);
            }
        }
    }

    /**
     * Add xp from a orb
     *
     * @param xpAmount which should be added
     */
    public void addXP(int xpAmount) {
        this.lastPickupXP = this.world.getServer().getCurrentTickTime();
        this.setXP(this.xp + xpAmount);
    }

    /**
     * A player can only pickup xp orbs at a rate of 1 per tick
     *
     * @return
     */
    public boolean canPickupXP() {
        return this.world.getServer().getCurrentTickTime() - this.lastPickupXP >= 50;
    }

    private int calculateRequiredExperienceForLevel(int level) {
        if (level >= 30) {
            return 112 + (level - 30) * 9;
        } else if (level >= 15) {
            return 37 + (level - 15) * 5;
        } else {
            return 7 + level * 2;
        }
    }

    @Override
    public float getXPPercentage() {
        return this.getAttribute(Attribute.EXPERIENCE);
    }

    @Override
    public int getXP() {
        return this.xp;
    }

    @Override
    public void setXP(int xp) {
        // Iterate levels until we have a new xp percentage value to set
        int neededXP, tempXP = xp, level = 0;
        while (tempXP > (neededXP = calculateRequiredExperienceForLevel(getLevel()))) {
            tempXP -= neededXP;
            level++;
        }

        this.xp = xp;
        this.setAttribute(Attribute.EXPERIENCE, tempXP / (float) neededXP);
        this.setLevel(level);
    }

    protected boolean shouldTickHunger() {
        return this.gamemode == Gamemode.SURVIVAL;
    }

    @Override
    public int getLevel() {
        return (int) this.getAttribute(Attribute.EXPERIENCE_LEVEL);
    }

    @Override
    public void setLevel(int level) {
        this.setAttribute(Attribute.EXPERIENCE_LEVEL, level);
    }

    @Override
    public void playSound(Vector location, Sound sound, byte pitch, SoundData data) {
        this.world.playSound(this, location, sound, pitch, data);
    }

    @Override
    public void playSound(Vector location, Sound sound, byte pitch) {
        this.world.playSound(this, location, sound, pitch, -1);
    }

    @Override
    public void sendParticle(Vector location, Particle particle) {
        this.world.sendParticle(this, location, particle, 0);
    }

    @Override
    public void sendParticle(Vector location, Particle particle, ParticleData data) {
        this.world.sendParticle(this, location, particle, data);
    }

    @Override
    public boolean getAllowFlight() {
        return this.adventureSettings.isCanFly();
    }

    @Override
    public void setAllowFlight(boolean value) {
        this.metadataContainer.setDataFlag(MetadataContainer.DATA_INDEX, EntityFlag.CAN_FLY, value);
        this.adventureSettings.setCanFly(value);
        this.adventureSettings.update();
    }

    @Override
    public boolean getFlying() {
        return this.adventureSettings.isFlying();
    }

    @Override
    public void setFlying(boolean value) {
        this.adventureSettings.setFlying(value);
        this.adventureSettings.update();
    }

    @Override
    public void sendTitle(String title, String subtitle, long fadein, long duration, long fadeout, TimeUnit unit) {
        // NPE check
        if (title == null) {
            title = "";
        }

        if (subtitle != null && !Objects.equals(subtitle, "")) {
            PacketSetTitle subtitlePacket = new PacketSetTitle();
            subtitlePacket.setType(PacketSetTitle.TitleType.TYPE_SUBTITLE.getId());
            subtitlePacket.setText(subtitle);
            subtitlePacket.setFadeInTime((int) unit.toMillis(fadein) / 50);
            subtitlePacket.setStayTime((int) unit.toMillis(duration) / 50);
            subtitlePacket.setFadeOutTime((int) unit.toMillis(fadeout) / 50);
            this.getConnection().addToSendQueue(subtitlePacket);
        }

        PacketSetTitle titlePacket = new PacketSetTitle();
        titlePacket.setType(PacketSetTitle.TitleType.TYPE_TITLE.getId());
        titlePacket.setText(title);
        titlePacket.setFadeInTime((int) unit.toMillis(fadein) / 50);
        titlePacket.setStayTime((int) unit.toMillis(duration) / 50);
        titlePacket.setFadeOutTime((int) unit.toMillis(fadeout) / 50);
        this.getConnection().addToSendQueue(titlePacket);
    }

    @Override
    public void sendTitle(String title) {
        this.sendTitle(title, "", 1, 1, (long) 0.5, TimeUnit.SECONDS);
    }

    @Override
    public void sendTitle(String title, String subtitle) {
        this.sendTitle(title, subtitle, 1, 1, (long) 0.5, TimeUnit.SECONDS);
    }

    public void firstSpawn() {
        // Set location
        this.getConnection().sendMovePlayer(this.getLocation());

        // Set simulation speed
        PacketWorldEvent worldEvent = new PacketWorldEvent();
        worldEvent.setData(0);
        worldEvent.setEventId(LevelEvent.SIM_SPEED);
        worldEvent.setPosition(new Vector(1f, 1f, 1f));
        this.getConnection().addToSendQueue(worldEvent);

        // Spawn for others
        this.getWorld().spawnEntityAt(this, this.getPositionX(), this.getPositionY(), this.getPositionZ(), this.getYaw(), this.getPitch());

        // Now its time for the join event since the player is fully loaded
        PlayerJoinEvent event = this.eventCaller.callEvent(new PlayerJoinEvent(this, ChatColor.YELLOW + this.getDisplayName() + " joined the game."));
        if (event.isCancelled()) {
            this.connection.disconnect(event.getKickReason());
        } else {
            if (event.getJoinMessage() != null && !event.getJoinMessage().isEmpty()) {
                GoMint.instance().getPlayers().forEach((player) -> {
                    player.sendMessage(event.getJoinMessage());
                });
            }
        }

        this.hasCompletedLogin = true;

        // Send network chunk publisher packet after join
        this.world.getServer().getScheduler().schedule(() -> {
            if (isOnline()) {
                getConnection().sendNetworkChunkPublisher();
            }
        }, 250, TimeUnit.MILLISECONDS);
    }

    @Override
    public Packet createSpawnPacket(EntityPlayer receiver) {
        PacketSpawnPlayer packetSpawnPlayer = new PacketSpawnPlayer();
        packetSpawnPlayer.setUuid(this.getUUID());
        packetSpawnPlayer.setName(this.getName());
        packetSpawnPlayer.setEntityId(this.getEntityId());
        packetSpawnPlayer.setRuntimeEntityId(this.getEntityId());
        packetSpawnPlayer.setPlatformChatId(this.getDeviceInfo().getDeviceId());

        packetSpawnPlayer.setX(this.getPositionX());
        packetSpawnPlayer.setY(this.getPositionY());
        packetSpawnPlayer.setZ(this.getPositionZ());

        packetSpawnPlayer.setVelocityX(this.getMotionX());
        packetSpawnPlayer.setVelocityY(this.getMotionY());
        packetSpawnPlayer.setVelocityZ(this.getMotionZ());

        packetSpawnPlayer.setPitch(this.getPitch());
        packetSpawnPlayer.setYaw(this.getYaw());
        packetSpawnPlayer.setHeadYaw(this.getHeadYaw());

        packetSpawnPlayer.setItemInHand(this.getInventory().getItemInHand());
        packetSpawnPlayer.setMetadataContainer(this.getMetadata());
        packetSpawnPlayer.setDeviceId(this.getDeviceInfo().getDeviceId());
        packetSpawnPlayer.setBuildPlatform(this.getDeviceInfo().getOs().getId());

        return packetSpawnPlayer;
    }

    @Override
    public void preSpawn(PlayerConnection connection) {

    }

    @Override
    public void postSpawn(PlayerConnection connection) {

    }

    @Override
    public boolean isGliding() {
        return this.metadataContainer.getDataFlag(MetadataContainer.DATA_INDEX, EntityFlag.GLIDING);
    }

    @Override
    public void setGliding(boolean value) {
        this.metadataContainer.setDataFlag(MetadataContainer.DATA_INDEX, EntityFlag.GLIDING, value);
    }

    @Override
    public DeviceInfo getDeviceInfo() {
        return this.connection.getDeviceInfo();
    }

    @Override
    public InetSocketAddress getAddress() {
        return this.connection.getConnection().getAddress();
    }

    @Override
    protected void checkAfterGravity() {
        float dX = this.getMotionX();
        float dY = this.getMotionY();
        float dZ = this.getMotionZ();

        // Check if we collide with some blocks when we would move that fast
        List<AxisAlignedBB> collisionList = this.world.getCollisionCubes(this, this.boundingBox.getOffsetBoundingBox(dX, dY, dZ), false);
        if (collisionList != null) {
            // Check if we would hit a y border block
            for (AxisAlignedBB axisAlignedBB : collisionList) {
                dY = axisAlignedBB.calculateYOffset(this.boundingBox, dY);
            }

            if (Math.abs(dY) <= 0.001) {
                dY = 0;
            }

            this.getTransform().setMotion(dX, dY, dZ);
        }
    }

    @Override
    public CommandOutput dispatchCommand(String command) {
        return this.getConnection().getServer().getPluginManager().getCommandManager().dispatchCommand(this, command);
    }

    @Override
    public void setSpawnLocation(Location spawnLocation) {
        this.spawnLocation = spawnLocation;
        this.connection.sendPlayerSpawnPosition();
    }

    @Override
    public void setScoreboard(io.gomint.scoreboard.Scoreboard scoreboard) {
        this.removeScoreboard();

        this.scoreboard = (Scoreboard) scoreboard;
        this.scoreboard.showFor(this);
    }

    @Override
    public void removeScoreboard() {
        if (this.scoreboard != null) {
            this.scoreboard.hideFor(this);
            this.scoreboard = null;
        }
    }

    public void setUsingItem(boolean value) {
        if (value) {
            this.actionStart = ((GoMintServer) GoMint.instance()).getCurrentTickTime();
            this.metadataContainer.setDataFlag(MetadataContainer.DATA_INDEX, EntityFlag.ACTION, true);
        } else {
            this.actionStart = -1;
            this.metadataContainer.setDataFlag(MetadataContainer.DATA_INDEX, EntityFlag.ACTION, false);
        }
    }

    @Override
    protected void checkBlockCollisions() {
        List<io.gomint.world.block.Block> blockList = this.world.getCollisionBlocks(this, true);
        if (blockList != null) {
            for (io.gomint.world.block.Block block : blockList) {
                io.gomint.server.world.block.Block implBlock = (io.gomint.server.world.block.Block) block;
                implBlock.onEntityCollision(this);
            }
        }
    }

    public Collection<ContainerInventory> getOpenInventories() {
        return this.windowIds.values();
    }

    @Override
    public void initFromNBT(NBTTagCompound compound) {
        super.initFromNBT(compound);

        this.enderChestInventory.initFromNBT(compound.getList("EnderChestInventory", false));
    }

    @Override
    public NBTTagCompound persistToNBT() {
        NBTTagCompound compound = super.persistToNBT();

        compound.addValue("EnderChestInventory", this.enderChestInventory.persistToNBT());

        return compound;
    }

    @Override
    public boolean shouldBeSeen(EntityPlayer player) {
        return player.isSpawnPlayers() && super.shouldBeSeen(player);
    }

    public boolean knowsChunk(@Nonnull ChunkAdapter adapter) {
        return this.connection.knowsChunk(adapter.longHashCode());
    }

    public boolean knowsChunk(int posX, int posZ) {
        return this.connection.knowsChunk(CoordinateUtils.toLong(posX, posZ));
    }

    public io.gomint.permission.PermissionManager getPermissionManager() {
        return permissionManager;
    }

    public void setPermissionManager(io.gomint.permission.PermissionManager permissionManager) {
        Class<? extends Plugin> plugin = CallerDetectorUtil.getCallerPlugin();
        LOGGER.warn("Plugin {} swapped out permission manager with {}", plugin.getName(), permissionManager.getClass().getName());

        this.permissionManager = permissionManager;
        this.isUsingDefaultPermissionManager = this.permissionManager instanceof PermissionManager;
    }

    public EntityVisibilityManager getEntityVisibilityManager() {
        return entityVisibilityManager;
    }

    public AdventureSettings getAdventureSettings() {
        return adventureSettings;
    }

    public Entity getHoverEntity() {
        return hoverEntity;
    }

    public void setHoverEntity(Entity hoverEntity) {
        this.hoverEntity = hoverEntity;
    }

    public Inventory getCraftingInventory() {
        return craftingInventory;
    }

    public CraftingInputInventory getCraftingInputInventory() {
        return craftingInputInventory;
    }

    public Inventory getCraftingResultInventory() {
        return craftingResultInventory;
    }

    public EnchantmentTableInventory getEnchantmentInputInventory() {
        return enchantmentInputInventory;
    }

    public Inventory getEnchantmentOutputInventory() {
        return enchantmentOutputInventory;
    }

    public EnchantmentProcessor getEnchantmentProcessor() {
        return enchantmentProcessor;
    }

    public BlockPosition getBreakVector() {
        return breakVector;
    }

    public long getStartBreak() {
        return startBreak;
    }

    public long getBreakTime() {
        return breakTime;
    }

    public Set<BlockPosition> getBlockUpdates() {
        return blockUpdates;
    }

    public Location getTeleportPosition() {
        return teleportPosition;
    }

    public void setEnchantmentInputInventory(EnchantmentTableInventory enchantmentInputInventory) {
        this.enchantmentInputInventory = enchantmentInputInventory;
    }

    public void setEnchantmentProcessor(EnchantmentProcessor enchantmentProcessor) {
        this.enchantmentProcessor = enchantmentProcessor;
    }

    public void setBreakVector(BlockPosition breakVector) {
        this.breakVector = breakVector;
    }

    public void setStartBreak(long startBreak) {
        this.startBreak = startBreak;
    }

    public void setBreakTime(long breakTime) {
        this.breakTime = breakTime;
    }

    public void setTeleportPosition(Location teleportPosition) {
        this.teleportPosition = teleportPosition;
    }


    public void setFishingHook(EntityFishingHook fishingHook) {
        this.fishingHook = fishingHook;
    }

    public void setNextMovement(Location nextMovement) {
        this.nextMovement = nextMovement;
    }

    public void setSpawnPlayers(boolean spawnPlayers) {
        this.spawnPlayers = spawnPlayers;
    }

    public EntityFishingHook getFishingHook() {
        return fishingHook;
    }

    public Location getSpawnLocation() {
        return spawnLocation;
    }

    public long getActionStart() {
        return actionStart;
    }

    public LoginPerformance getLoginPerformance() {
        return loginPerformance;
    }

    public Location getNextMovement() {
        return nextMovement;
    }

    public boolean isSpawnPlayers() {
        return spawnPlayers;
    }

    public Scoreboard getScoreboard() {
        return scoreboard;
    }

    @Override
    public EnderChestInventory getEnderChestInventory() {
        return enderChestInventory;
    }

    public boolean hasCompletedLogin() {
        return this.hasCompletedLogin;
    }

    public void resetActionStart() {
        this.actionStart = ((GoMintServer) GoMint.instance()).getCurrentTickTime();
    }

    @Override
    public Set<String> getTags() {
        return EntityTags.PLAYER;
    }

}
