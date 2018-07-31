/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.entity;

import io.gomint.GoMint;
import io.gomint.command.*;
import io.gomint.enchant.EnchantmentKnockback;
import io.gomint.enchant.EnchantmentSharpness;
import io.gomint.entity.ChatType;
import io.gomint.entity.Entity;
import io.gomint.entity.potion.PotionEffect;
import io.gomint.event.entity.EntityDamageByEntityEvent;
import io.gomint.event.entity.EntityDamageEvent;
import io.gomint.event.entity.EntityTeleportEvent;
import io.gomint.event.inventory.InventoryCloseEvent;
import io.gomint.event.inventory.InventoryOpenEvent;
import io.gomint.event.player.*;
import io.gomint.gui.*;
import io.gomint.math.*;
import io.gomint.math.Vector;
import io.gomint.player.DeviceInfo;
import io.gomint.server.GoMintServer;
import io.gomint.server.command.CommandCanidate;
import io.gomint.server.command.CommandHolder;
import io.gomint.server.enchant.EnchantmentProcessor;
import io.gomint.server.entity.metadata.MetadataContainer;
import io.gomint.server.entity.passive.EntityHuman;
import io.gomint.server.entity.projectile.EntityFishingHook;
import io.gomint.server.inventory.*;
import io.gomint.server.inventory.item.ItemAir;
import io.gomint.server.inventory.item.ItemStack;
import io.gomint.server.network.PlayerConnection;
import io.gomint.server.network.PlayerConnectionState;
import io.gomint.server.network.packet.*;
import io.gomint.server.network.tcp.protocol.SendPlayerToServerPacket;
import io.gomint.server.maintenance.performance.LoginPerformance;
import io.gomint.server.permission.PermissionManager;
import io.gomint.server.player.EntityVisibilityManager;
import io.gomint.server.util.EnumConnectors;
import io.gomint.server.world.ChunkAdapter;
import io.gomint.server.world.WorldAdapter;
import io.gomint.server.world.block.Block;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.world.*;
import it.unimi.dsi.fastutil.bytes.Byte2ObjectMap;
import it.unimi.dsi.fastutil.bytes.Byte2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ByteMap;
import it.unimi.dsi.fastutil.objects.Object2ByteOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
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

    private static final Logger LOGGER = LoggerFactory.getLogger( EntityPlayer.class );

    private final PlayerConnection connection;
    private int viewDistance = 4;
    private Queue<ChunkAdapter> chunkSendQueue = new LinkedBlockingQueue<>();

    // EntityPlayer Information
    private Gamemode gamemode = Gamemode.SURVIVAL;
    @Getter
    private AdventureSettings adventureSettings;
    @Getter
    @Setter
    private Entity hoverEntity;
    @Getter
    private final PermissionManager permissionManager = new PermissionManager( this );
    @Getter
    private final EntityVisibilityManager entityVisibilityManager = new EntityVisibilityManager( this );
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

    // Crafting
    @Getter
    private Inventory craftingInventory;
    @Getter
    private Inventory craftingInputInventory;
    @Getter
    private Inventory craftingResultInventory;

    // Enchantment table
    @Setter
    @Getter
    private EnchantmentTableInventory enchantmentInputInventory;
    @Getter
    private Inventory enchantmentOutputInventory;
    @Setter
    @Getter
    private EnchantmentProcessor enchantmentProcessor;

    // Block break data
    @Setter
    @Getter
    private BlockPosition breakVector;
    @Setter
    @Getter
    private long startBreak;
    @Setter
    @Getter
    private long breakTime;

    // Update data
    @Getter
    private Set<BlockPosition> blockUpdates = new HashSet<>();
    @Getter
    @Setter
    private Location teleportPosition = null;

    // Form stuff
    private int formId;
    private Int2ObjectMap<io.gomint.server.gui.Form> forms = new Int2ObjectOpenHashMap<>();
    private Int2ObjectMap<io.gomint.server.gui.FormListener> formListeners = new Int2ObjectOpenHashMap<>();

    // Server settings
    private int serverSettingsForm = -1;

    // Entity data
    @Getter
    @Setter
    private EntityFishingHook fishingHook;
    private long lastPickupXP;
    @Getter private Location spawnLocation;

    // Item usage ticking
    @Getter
    private long actionStart = -1;

    // Exp
    private int xp;

    // Performance metrics
    @Getter
    private LoginPerformance loginPerformance;

    // Movement delay
    @Setter
    private Location nextMovement;

    @Setter @Getter
    private boolean spawnPlayers;

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
    public EntityPlayer( WorldAdapter world,
                         PlayerConnection connection,
                         String username,
                         String xboxId,
                         UUID uuid,
                         Locale locale ) {
        super( EntityType.PLAYER, world );
        this.connection = connection;

        // EntityHuman stuff
        this.setPlayerData( username, username, xboxId, uuid );

        this.locale = locale;
        this.adventureSettings = new AdventureSettings( this );

        // Performance stuff
        this.loginPerformance = new LoginPerformance();
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

    @Override
    public void transfer( String host, int port ) {
        if ( this.world.getServer().getServerConfig().getListener().isUseTCP() ) {
            SendPlayerToServerPacket sendPlayerToServerPacket = new SendPlayerToServerPacket();
            sendPlayerToServerPacket.setHost( host );
            sendPlayerToServerPacket.setPort( port );
            this.connection.getConnectionHandler().send( sendPlayerToServerPacket );
        } else {
            PacketTransfer packetTransfer = new PacketTransfer();
            packetTransfer.setAddress( host );
            packetTransfer.setPort( port );
            this.connection.addToSendQueue( packetTransfer );
        }
    }

    @Override
    public int getPing() {
        return this.connection.getPing();
    }

    /**
     * Sets the view distance used to calculate the chunk to be sent to the player.
     *
     * @param viewDistance The view distance to set
     */
    public void setViewDistance( int viewDistance ) {
        int tempViewDistance = Math.min( viewDistance, this.world.getConfig().getViewDistance() );
        if ( this.viewDistance != tempViewDistance ) {
            this.viewDistance = tempViewDistance;
        }

        if ( this.connection.getState() != PlayerConnectionState.PLAYING ) {
            this.getLoginPerformance().setChunkStart( this.world.getServer().getCurrentTickTime() );
        }

        this.connection.onViewDistanceChanged();
    }

    /**
     * Gets the connection associated with this player entity.
     *
     * @return The connection associated with this player entity
     */
    public PlayerConnection getConnection() {
        return this.connection;
    }

    @Override
    public void setGamemode( Gamemode gamemode ) {
        this.gamemode = gamemode;
        this.updateGamemode();
    }

    private void updateGamemode() {
        int gameModeNumber = EnumConnectors.GAMEMODE_CONNECTOR.convert( this.gamemode ).getMagicNumber();

        PacketSetGamemode packetSetGamemode = new PacketSetGamemode();
        packetSetGamemode.setGameMode( gameModeNumber == 1 ? 1 : 0 );
        this.connection.send( packetSetGamemode );

        this.sendAdventureSettings();

        // Set fly
        if ( this.gamemode == Gamemode.SPECTATOR || this.gamemode == Gamemode.CREATIVE ) {
            this.metadataContainer.setDataFlag( MetadataContainer.DATA_INDEX, EntityFlag.CAN_FLY, true );
        } else {
            this.metadataContainer.setDataFlag( MetadataContainer.DATA_INDEX, EntityFlag.CAN_FLY, false );
        }

        // Set invis
        if ( this.gamemode == Gamemode.SPECTATOR ) {
            this.setInvisible( true );
        } else {
            // Check for invis potion effect
            if ( !this.hasEffect( PotionEffect.INVISIBILITY ) ) {
                this.setInvisible( false );
            }
        }
    }

    /**
     * Send adventure settings based on the gamemode
     */
    public void sendAdventureSettings() {
        int gameModeNumber = EnumConnectors.GAMEMODE_CONNECTOR.convert( this.gamemode ).getMagicNumber();

        // Recalc adventure settings
        this.adventureSettings.setWorldImmutable( gameModeNumber == 0x03 );
        this.adventureSettings.setCanFly( ( gameModeNumber & 0x01 ) > 0 );
        this.adventureSettings.setNoClip( gameModeNumber == 0x03 );
        this.adventureSettings.setFlying( gameModeNumber == 0x03 );
        this.adventureSettings.setAttackMobs( gameModeNumber < 0x02 );
        this.adventureSettings.setAttackPlayers( gameModeNumber < 0x02 );
        this.adventureSettings.setNoPvP( gameModeNumber == 0x03 );
        this.adventureSettings.update();
    }

    @Override
    public Gamemode getGamemode() {
        return this.gamemode;
    }

    @Override
    public boolean isOp() {
        return this.adventureSettings.isOperator();
    }

    @Override
    public void setOp( boolean value ) {
        this.adventureSettings.setOperator( value );
        this.sendAdventureSettings();
    }

    @Override
    public void hidePlayer( io.gomint.entity.EntityPlayer player ) {
        // Never hide myself (client crashes when this is done)
        if ( player.equals( this ) ) {
            LOGGER.warn( "You can't hide a player itself. Please tell the plugin author to remove the hidePlayer call" );
            return;
        }

        EntityPlayer other = (EntityPlayer) player;

        if ( this.hiddenPlayers == null ) {
            this.hiddenPlayers = new HashSet<>();
        }

        this.hiddenPlayers.add( other.getEntityId() );

        LOGGER.debug( "Player {} hides {} from now on", this.getName(), player.getName() );

        // Remove the entity clientside
        this.entityVisibilityManager.removeEntity( other );

        // Remove from player list
        PacketPlayerlist packetPlayerlist = new PacketPlayerlist();
        packetPlayerlist.setMode( (byte) 1 );
        packetPlayerlist.setEntries( new ArrayList<PacketPlayerlist.Entry>() {{
            add( new PacketPlayerlist.Entry( other ) );
        }} );
        getConnection().addToSendQueue( packetPlayerlist );
    }

    @Override
    public void showPlayer( io.gomint.entity.EntityPlayer player ) {
        if ( this.hiddenPlayers == null ) {
            return;
        }

        if ( this.hiddenPlayers.remove( player.getEntityId() ) ) {
            LOGGER.debug( "Player {} shows {} from now on", this.getName(), player.getName() );

            EntityPlayer other = (EntityPlayer) player;

            // Send tablist and spawn packet
            PacketPlayerlist packetPlayerlist = new PacketPlayerlist();
            packetPlayerlist.setMode( (byte) 0 );
            packetPlayerlist.setEntries( new ArrayList<PacketPlayerlist.Entry>() {{
                add( new PacketPlayerlist.Entry( other ) );
            }} );
            getConnection().addToSendQueue( packetPlayerlist );

            // Check bounds
            Chunk playerChunk = this.getChunk();
            Chunk chunk = other.getChunk();
            if ( Math.abs( playerChunk.getX() - chunk.getX() ) <= this.getViewDistance() &&
                Math.abs( playerChunk.getZ() - chunk.getZ() ) <= this.getViewDistance() ) {
                this.entityVisibilityManager.addEntity( other );
            }
        }
    }

    @Override
    public boolean isHidden( io.gomint.entity.EntityPlayer player ) {
        return this.hiddenPlayers != null && this.hiddenPlayers.contains( player.getEntityId() );
    }

    public void teleport( Location to, EntityTeleportEvent.Cause cause ) {
        // Only teleport when online
        if ( !isOnline() ) {
            return;
        }

        EntityTeleportEvent entityTeleportEvent = new EntityTeleportEvent( this, this.getLocation(), to, cause );
        this.world.getServer().getPluginManager().callEvent( entityTeleportEvent );
        if ( entityTeleportEvent.isCancelled() ) {
            return;
        }

        Location from = getLocation();

        // Reset chunks
        this.connection.resetQueuedChunks();

        // Check if we need to change worlds
        if ( !to.getWorld().equals( from.getWorld() ) ) {
            // Despawn entities first
            this.entityVisibilityManager.clear();

            // Change worlds first
            getWorld().removePlayer( this );
            this.world = (WorldAdapter) to.getWorld();
            this.world.spawnEntityAt( this, to.getX(), to.getY(), to.getZ(), to.getYaw(), to.getPitch() );

            // Be sure to get rid of all loaded chunks
            this.connection.resetPlayerChunks();

            // Send all packets needed for a world switch
            this.world.playerSwitched( this );
        }

        // Check for attached entities
        if ( !this.getAttachedEntities().isEmpty() ) {
            Chunk chunk = this.getChunk();
            for ( Entity entity : new ObjectOpenHashSet<>( this.getAttachedEntities() ) ) {
                if ( entity instanceof EntityPlayer ) {
                    EntityPlayer player = (EntityPlayer) entity;
                    Chunk playerChunk = player.getChunk();
                    if ( Math.abs( playerChunk.getX() - chunk.getX() ) > player.getViewDistance() &&
                        Math.abs( playerChunk.getZ() - chunk.getZ() ) > player.getViewDistance() ) {
                        player.getEntityVisibilityManager().removeEntity( this );
                    }
                }
            }
        }

        // Set the new location
        this.setAndRecalcPosition( to );

        // Force load the new spawn chunk
        this.getChunk(); // This getChunk uses loadChunk so it will generate or load from disc if needed

        // Move the client
        this.connection.sendMovePlayer( to );

        // Send chunks to the client
        this.connection.checkForNewChunks( from, true );
        this.fallDistance = 0;

        // Tell the movement handler to force this position to the client
        this.teleportPosition = to;
    }

    @Override
    public void addHunger( float amount ) {
        PlayerFoodLevelChangeEvent foodLevelChangeEvent = new PlayerFoodLevelChangeEvent(
            this, amount
        );
        this.world.getServer().getPluginManager().callEvent( foodLevelChangeEvent );

        if ( !foodLevelChangeEvent.isCancelled() ) {
            super.addHunger( amount );
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
    public void update( long currentTimeMS, float dT ) {
        // Move first
        if ( this.nextMovement != null ) {
            Location from = this.getLocation();
            PlayerMoveEvent playerMoveEvent = this.connection.getNetworkManager().getServer().getPluginManager().callEvent(
                new PlayerMoveEvent( this, from, this.nextMovement )
            );

            if ( playerMoveEvent.isCancelled() ) {
                playerMoveEvent.setTo( playerMoveEvent.getFrom() );
            }

            Location to = playerMoveEvent.getTo();
            if ( to.getX() != this.nextMovement.getX() || to.getY() != this.nextMovement.getY() || to.getZ() != this.nextMovement.getZ() ||
                !to.getWorld().equals( this.nextMovement.getWorld() ) || to.getYaw() != this.nextMovement.getYaw() ||
                to.getPitch() != this.nextMovement.getPitch() || to.getHeadYaw() != this.nextMovement.getHeadYaw() ) {
                this.teleport( to );
            } else {
                float moveX = to.getX() - from.getX();
                float moveY = to.getY() - from.getY();
                float moveZ = to.getZ() - from.getZ();

                // Try to at least move the gravitation down
                Vector moved = this.safeMove( moveX, moveY, moveZ );

                // Exhaustion
                double distance = Math.abs( moved.getX() ) + Math.abs( moved.getY() ) + Math.abs( moved.getZ() );
                if ( this.isSprinting() ) {
                    this.exhaust( (float) ( 0.1 * distance ), PlayerExhaustEvent.Cause.SPRINTING );
                } else {
                    this.exhaust( (float) ( 0.01 * distance ), PlayerExhaustEvent.Cause.WALKING );
                }

                this.setPitch( to.getPitch() );
                this.setYaw( to.getYaw() );
                this.setHeadYaw( to.getHeadYaw() );
            }

            boolean changeWorld = !to.getWorld().equals( from.getWorld() );
            boolean changeXZ = (int) from.getX() != (int) to.getX() || (int) from.getZ() != (int) to.getZ();
            boolean changeY = (int) from.getY() != (int) to.getY();

            if ( changeWorld || changeXZ || changeY ) {
                if ( changeWorld || changeXZ ) {
                    this.connection.checkForNewChunks( from, false );
                }

                // Check for interaction
                Block block = from.getWorld().getBlockAt( from.toBlockPosition() );
                block.gotOff( this );

                block = to.getWorld().getBlockAt( to.toBlockPosition() );
                block.stepOn( this );
            }

            this.nextMovement = null;
        }

        super.update( currentTimeMS, dT );

        // Update permissions
        this.permissionManager.update( currentTimeMS, dT );

        if ( this.isDead() || this.getHealth() <= 0 ) {
            return;
        }

        // Look around
        Collection<Entity> nearbyEntities = this.world.getNearbyEntities( this.boundingBox.grow( 1, 0.5f, 1 ), this, null );
        if ( nearbyEntities != null ) {
            for ( Entity nearbyEntity : nearbyEntities ) {
                io.gomint.server.entity.Entity implEntity = (io.gomint.server.entity.Entity) nearbyEntity;
                implEntity.onCollideWithPlayer( this );
            }
        }

        // Update attributes which are flagged as dirty
        this.updateAttributes();

        // Check for sprint, skip if player is in Creative mode
        if ( this.getGamemode() != Gamemode.CREATIVE ) {
            if ( this.getHunger() <= 6 && this.isSprinting() ) {
                this.setSprinting( false );
            }
        }
    }

    @Override
    public AxisAlignedBB getBoundingBox() {
        return this.boundingBox;
    }

    @Override
    public void openInventory( io.gomint.inventory.Inventory inventory ) {
        if ( inventory instanceof ContainerInventory ) {
            InventoryOpenEvent event = new InventoryOpenEvent( this, inventory );
            this.getWorld().getServer().getPluginManager().callEvent( event );

            if ( event.isCancelled() ) {
                return;
            }

            // We need to generate a window id for the client
            byte foundId = -1;
            for ( byte i = WindowMagicNumbers.FIRST.getId(); i < WindowMagicNumbers.LAST.getId(); i++ ) {
                if ( !this.windowIds.containsKey( i ) ) {
                    foundId = i;
                    break;
                }
            }

            // No id found?
            if ( foundId == -1 ) {
                return;
            }

            // Trigger open
            ContainerInventory containerInventory = (ContainerInventory) inventory;
            this.windowIds.put( foundId, containerInventory );
            this.containerIds.put( containerInventory, foundId );
            containerInventory.addViewer( this, foundId );
        }
    }

    @Override
    public void closeInventory( io.gomint.inventory.Inventory inventory ) {
        if ( inventory instanceof ContainerInventory ) {
            byte windowId = this.getWindowId( (ContainerInventory) inventory );
            if ( windowId != 0 ) {
                this.closeInventory( windowId );

                // Tell the client to close it
                PacketContainerClose packetContainerClose = new PacketContainerClose();
                packetContainerClose.setWindowId( windowId );
                this.connection.send( packetContainerClose );
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
    public void updateAttributes() {
        PacketUpdateAttributes updateAttributes = null;

        for ( AttributeInstance instance : attributes.values() ) {
            if ( instance.isDirty() ) {
                if ( updateAttributes == null ) {
                    updateAttributes = new PacketUpdateAttributes();
                    updateAttributes.setEntityId( this.getEntityId() );
                }

                updateAttributes.addAttributeInstance( instance );
            }
        }

        if ( updateAttributes != null ) {
            this.connection.addToSendQueue( updateAttributes );
        }
    }

    /**
     * Force send all attributes
     */
    public void resendAttributes() {
        PacketUpdateAttributes updateAttributes = new PacketUpdateAttributes();
        updateAttributes.setEntityId( this.getEntityId() );

        for ( AttributeInstance instance : attributes.values() ) {
            updateAttributes.addAttributeInstance( instance );
        }

        this.connection.send( updateAttributes );
    }

    /**
     * Send all data which the client needs before getting chunks
     */
    public void prepareEntity() {
        // Send world init data
        this.connection.sendWorldTime( 0 );
        this.connection.sendWorldInitialization();
        this.connection.sendSpawnPosition();
        this.connection.sendWorldTime( 0 );
        this.connection.sendDifficulty();
        this.connection.sendCommandsEnabled();

        // Set spawn
        if ( this.connection.getEntity().getSpawnLocation() == null ) {
            this.connection.getEntity().setSpawnLocation( this.connection.getEntity().getWorld().getSpawnLocation() );
        }

        // Send adventure settings
        this.sendAdventureSettings();

        // Send commands
        this.sendCommands();

        // Attributes
        this.updateAttributes();

        // Inventories
        this.inventory = new PlayerInventory( this );
        this.armorInventory = new ArmorInventory( this );

        this.cursorInventory = new CursorInventory( this );
        this.offhandInventory = new OffhandInventory( this );

        this.craftingInventory = new CraftingInputInventory( this );
        this.craftingInputInventory = new CraftingInputInventory( this );
        this.craftingResultInventory = new OneSlotInventory( this );

        this.enchantmentOutputInventory = new OneSlotInventory( this );

        this.windowIds = new Byte2ObjectOpenHashMap<>();
        this.containerIds = new Object2ByteOpenHashMap<>();
        this.connection.getServer().getCreativeInventory().addViewer( this );

        // Send crafting recipes
        this.connection.addToSendQueue( this.world.getServer().getRecipeManager().getCraftingRecipesBatch() );

        // Send entity metadata
        this.sendData( this );

        // Send player list for yourself
        PacketPlayerlist playerlist = new PacketPlayerlist();
        playerlist.setMode( (byte) 0 );
        playerlist.setEntries( new ArrayList<PacketPlayerlist.Entry>() {{
            add( new PacketPlayerlist.Entry( EntityPlayer.this ) );
        }} );
        this.getConnection().addToSendQueue( playerlist );
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
    public boolean canInteract( Vector position, int maxDistance ) {
        // Distance
        Vector eyePosition = this.getPosition().add( 0, this.getEyeHeight(), 0 );
        if ( eyePosition.distanceSquared( position ) > MathUtils.square( maxDistance ) ) {
            return false;
        }

        // Direction
        Vector playerPosition = this.getPosition();
        Vector2 directionPlane = this.getDirectionPlane();
        float dot = directionPlane.dot( new Vector2( eyePosition.getX(), eyePosition.getZ() ) );
        float dot1 = directionPlane.dot( new Vector2( playerPosition.getX(), playerPosition.getZ() ) );
        return ( dot1 - dot ) >= -( MathUtils.SQRT_3 / 2 );
    }

    /**
     * Remove player from PlayerList and remove from global inventories etc.
     */
    public void cleanup() {
        this.connection.getServer().getCreativeInventory().removeViewer( this );
        this.connection.getServer().getPlayersByUUID().remove( this.getUUID() );

        Block block = this.world.getBlockAt( this.getPosition().toBlockPosition() );
        block.gotOff( this );

        // Remove from player list
        PacketPlayerlist packetPlayerlist = new PacketPlayerlist();
        packetPlayerlist.setMode( (byte) 1 );
        packetPlayerlist.setEntries( new ArrayList<PacketPlayerlist.Entry>() {{
            add( new PacketPlayerlist.Entry( EntityPlayer.this ) );
        }} );

        // Cleanup the visibility manager
        this.entityVisibilityManager.clear();

        // Check all entities
        for ( WorldAdapter worldAdapter : this.connection.getServer().getWorldManager().getWorlds() ) {
            worldAdapter.iterateEntities( Entity.class, entity -> {
                if ( entity instanceof EntityPlayer ) {
                    EntityPlayer entityPlayer = (EntityPlayer) entity;
                    if ( !entityPlayer.equals( EntityPlayer.this ) ) {
                        entityPlayer.getConnection().addToSendQueue( packetPlayerlist );
                    }

                    // Check if player did hide this one
                    if ( entityPlayer.hiddenPlayers != null && entityPlayer.hiddenPlayers.contains( getEntityId() ) ) {
                        entityPlayer.hiddenPlayers.remove( getEntityId() );
                    }

                    // Check if mouseover is the entity
                    if ( entityPlayer.hoverEntity != null && entityPlayer.hoverEntity.equals( EntityPlayer.this ) ) {
                        entityPlayer.hoverEntity = null;
                    }

                    entityPlayer.entityVisibilityManager.removeEntity( EntityPlayer.this );
                } else {
                    entity.hideFor( EntityPlayer.this );
                }
            } );
        }
    }

    /**
     * Get the window id of specified inventory
     *
     * @param inventory which we want to get the window id for
     * @return window id of the container
     */
    public byte getWindowId( ContainerInventory inventory ) {
        return this.containerIds.getByte( inventory );
    }

    /**
     * Close a container inventory
     *
     * @param windowId which should be closed
     */
    public void closeInventory( byte windowId ) {
        ContainerInventory containerInventory = this.windowIds.remove( windowId );
        if ( containerInventory != null ) {
            containerInventory.removeViewer( this );
            this.containerIds.removeByte( containerInventory );

            InventoryCloseEvent inventoryCloseEvent = new InventoryCloseEvent( this, containerInventory );
            this.getWorld().getServer().getPluginManager().callEvent( inventoryCloseEvent );
        }
    }

    /**
     * Get a container by its id
     *
     * @param windowId which should be looked up
     * @return container inventory or null when not found
     */
    public ContainerInventory getContainerId( byte windowId ) {
        return this.windowIds.get( windowId );
    }

    @Override
    public void sendMessage( String message ) {
        PacketText packetText = new PacketText();
        packetText.setMessage( message );
        packetText.setType( PacketText.Type.CLIENT_MESSAGE );
        this.connection.addToSendQueue( packetText );
    }

    @Override
    public void sendMessage( ChatType type, String... message ) {
        PacketText packetText = new PacketText();
        packetText.setMessage( message[0] );
        switch ( type ) {
            case TIP:
                packetText.setType( PacketText.Type.TIP_MESSAGE );
                break;
            case NORMAL:
                packetText.setType( PacketText.Type.CLIENT_MESSAGE );
                break;
            case SYSTEM:
                packetText.setType( PacketText.Type.SYSTEM_MESSAGE );
                break;
            case POPUP:
                packetText.setType( PacketText.Type.POPUP_NOTICE );

                if ( message.length > 1 ) {
                    packetText.setSubtitle( message[1] );
                } else {
                    packetText.setSubtitle( "" );
                }

                break;
            default:
                break;
        }

        this.connection.addToSendQueue( packetText );
    }

    @Override
    public boolean hasPermission( String permission ) {
        return this.permissionManager.hasPermission( permission );
    }

    /**
     * Send commands to the client
     */
    public void sendCommands() {
        // Send commands
        PacketAvailableCommands packetAvailableCommands = this.connection.getServer().
            getPluginManager().getCommandManager().createPacket( this );
        this.connection.addToSendQueue( packetAvailableCommands );
    }

    /**
     * Basicly a override of the {@link EntityHuman#exhaust(float)} method with a event call in it.
     *
     * @param amount of exhaustion
     * @param cause  of exhaustion
     */
    @Override
    public void exhaust( float amount, PlayerExhaustEvent.Cause cause ) {
        if ( this.gamemode == Gamemode.SURVIVAL ) {
            PlayerExhaustEvent exhaustEvent = new PlayerExhaustEvent( this, amount, cause );
            this.world.getServer().getPluginManager().callEvent( exhaustEvent );

            if ( exhaustEvent.isCancelled() ) {
                this.resendAttributes();
                return;
            }

            super.exhaust( exhaustEvent.getAdditionalAmount() );
        } else {
            if ( this.getExhaustion() != 0 ) {
                this.setExhaustion( 0 );
            }
        }
    }

    /**
     * Handle a jump
     */
    public void jump() {
        // Jumping is only handled for exhaustion it seems
        if ( this.isSprinting() ) {
            this.exhaust( 0.8f, PlayerExhaustEvent.Cause.SPRINT_JUMP );
        } else {
            this.exhaust( 0.2f, PlayerExhaustEvent.Cause.JUMP );
        }
    }

    /**
     * Attack another entity with the item currently in hand
     *
     * @param target which should be attacked
     * @return true when damage has been dealt, false when not
     */
    public boolean attackWithItemInHand( Entity target ) {
        if ( target instanceof io.gomint.server.entity.Entity ) {
            io.gomint.server.entity.Entity targetEntity = (io.gomint.server.entity.Entity) target;

            // Check if the target can be attacked
            if ( targetEntity.canBeAttackedWithAnItem() && !targetEntity.isInvulnerableFrom( this ) ) {
                boolean success = false;

                // Get this entity attack damage
                EntityDamageEvent.DamageSource damageSource = EntityDamageEvent.DamageSource.ENTITY_ATTACK;
                float damage = this.getAttribute( Attribute.ATTACK_DAMAGE );

                EnchantmentSharpness sharpness = this.getInventory().getItemInHand().getEnchantment( EnchantmentSharpness.class );
                if ( sharpness != null ) {
                    damage += sharpness.getLevel() * 1.25f;
                }

                // Check for knockback stuff
                int knockbackLevel = 0;

                if ( this.isSprinting() ) {
                    knockbackLevel++;
                }

                EnchantmentKnockback knockback = this.getInventory().getItemInHand().getEnchantment( EnchantmentKnockback.class );
                if ( knockback != null ) {
                    knockbackLevel += knockback.getLevel();
                }

                if ( damage > 0 ) {
                    boolean crit = this.fallDistance > 0 && !this.onGround && !this.isOnLadder() && !this.isInsideLiquid();
                    if ( crit && damage > 0.0f ) {
                        damage *= 1.5;
                    }

                    // Check if target can absorb this damage
                    if ( ( success = targetEntity.damage( new EntityDamageByEntityEvent( targetEntity, this, damageSource, damage ) ) ) ) {
                        // Apply knockback
                        if ( knockbackLevel > 0 ) {
                            // Modify target velocity
                            Vector targetVelo = targetEntity.getVelocity();
                            targetEntity.setVelocity( targetVelo.add(
                                (float) ( -Math.sin( this.getYaw() * (float) Math.PI / 180.0F ) * (float) knockbackLevel * 0.5F ),
                                0.1f,
                                (float) ( Math.cos( this.getYaw() * (float) Math.PI / 180.0F ) * (float) knockbackLevel * 0.5F ) ) );

                            // Modify our velocity / movement
                            Vector ownVelo = this.getVelocity();
                            ownVelo.setX( ownVelo.getX() * 0.6F );
                            ownVelo.setZ( ownVelo.getZ() * 0.6F );
                            this.setVelocity( ownVelo );

                            if ( !this.world.getServer().getServerConfig().getVanilla().isDisableSprintReset() ) {
                                this.setSprinting( false );
                            }
                        }

                        targetEntity.broadCastMotion();
                    }
                }

                this.exhaust( 0.3f, PlayerExhaustEvent.Cause.ATTACK );
                return success;
            }
        }

        return false;
    }

    @Override
    public boolean damage( EntityDamageEvent damageEvent ) {
        // When allowFlight is on we don't need falling damage
        if ( this.adventureSettings.isCanFly() && damageEvent.getDamageSource() == EntityDamageEvent.DamageSource.FALL ) {
            return false;
        }

        // Can't touch this!
        return this.gamemode != Gamemode.CREATIVE && this.gamemode != Gamemode.SPECTATOR && super.damage( damageEvent );
    }

    @Override
    protected float applyArmorReduction( EntityDamageEvent damageEvent, boolean damageArmor ) {
        if ( damageEvent.getDamageSource() == EntityDamageEvent.DamageSource.FALL ||
            damageEvent.getDamageSource() == EntityDamageEvent.DamageSource.VOID ||
            damageEvent.getDamageSource() == EntityDamageEvent.DamageSource.DROWNING ) {
            return damageEvent.getDamage();
        }

        float damage = damageEvent.getDamage();
        float maxReductionDiff = 25 - this.armorInventory.getTotalArmorValue();
        float amplifiedDamage = damage * maxReductionDiff;
        if ( damageArmor ) {
            this.armorInventory.damageEvenly( damage );
        }

        return amplifiedDamage / 25.0F;
    }

    @Override
    public void attach( EntityPlayer player ) {
        super.attach( player );
        this.armorInventory.addViewer( player );

        // Send death animation if needed
        if ( this.getHealth() <= 0 ) {
            PacketEntityEvent entityEvent = new PacketEntityEvent();
            entityEvent.setEntityId( this.getEntityId() );
            entityEvent.setEventId( EntityEvent.DEATH.getId() );
            player.getConnection().addToSendQueue( entityEvent );
        }
    }

    @Override
    public void detach( EntityPlayer player ) {
        this.armorInventory.removeViewer( player );
        super.detach( player );
    }

    /**
     * Respawn this player
     */
    public void respawn() {
        // Event first
        PlayerRespawnEvent event = new PlayerRespawnEvent( this, this.respawnPosition );
        this.connection.getServer().getPluginManager().callEvent( event );

        if ( event.isCancelled() ) {
            PacketEntityEvent entityEvent = new PacketEntityEvent();
            entityEvent.setEntityId( this.getEntityId() );
            entityEvent.setEventId( EntityEvent.DEATH.getId() );
            this.connection.addToSendQueue( entityEvent );

            return;
        }

        // Check if we need to despawn first
        if ( this.deadTimer != -1 ) {
            this.despawn();
            this.deadTimer = -1;
        }

        // Reset last damage stuff
        this.lastDamageEntity = null;
        this.lastDamageSource = null;
        this.lastDamage = 0;

        // Send metadata
        this.sendData( this );

        // Resend adventure settings
        this.adventureSettings.update();

        // Reset attributes
        this.resetAttributes();
        this.resendAttributes();

        // Remove all effects
        this.removeAllEffects();

        // Check for new chunks
        this.teleport( event.getRespawnLocation() );
        this.respawnPosition = null;

        // Reset motion
        this.setVelocity( new Vector( 0, 0, 0 ) );

        // Send all inventories
        this.inventory.sendContents( this.connection );
        this.offhandInventory.sendContents( this.connection );
        this.armorInventory.sendContents( this.connection );

        PacketEntityEvent entityEvent = new PacketEntityEvent();
        entityEvent.setEntityId( this.getEntityId() );
        entityEvent.setEventId( EntityEvent.RESPAWN.getId() );

        // Update all other players
        for ( io.gomint.entity.EntityPlayer player : this.world.getPlayers() ) {
            EntityPlayer implPlayer = (EntityPlayer) player;
            implPlayer.getEntityVisibilityManager().updateEntity( this, this.getChunk() );
        }

        // Apply item in hand stuff
        ItemStack itemInHand = (ItemStack) this.inventory.getItemInHand();
        itemInHand.gotInHand( this );
    }

    @Override
    protected void kill() {
        super.kill();

        // TODO: Death messages based on last damage input

        List<io.gomint.inventory.item.ItemStack> drops = this.getDrops();

        PlayerDeathEvent event = new PlayerDeathEvent( this, "", true, drops );
        this.connection.getServer().getPluginManager().callEvent( event );

        if ( event.isDropInventory() ) {
            for ( io.gomint.inventory.item.ItemStack drop : event.getDrops() ) {
                this.world.dropItem( this.getLocation(), drop );
            }

            this.inventory.clear();
            this.offhandInventory.clear();
            this.armorInventory.clear();
        }

        this.craftingInventory.clear();
        this.craftingInputInventory.clear();
        this.craftingResultInventory.clear();

        if ( event.getDeathMessage() != null && !event.getDeathMessage().isEmpty() ) {
            for ( io.gomint.entity.EntityPlayer player : this.world.getPlayers() ) {
                player.sendMessage( event.getDeathMessage() );
            }
        }

        this.respawnPosition = this.world.getSpawnLocation().add( 0, this.eyeHeight, 0 );

        PacketRespawnPosition packetRespawnPosition = new PacketRespawnPosition();
        packetRespawnPosition.setPosition( this.respawnPosition );
        this.getConnection().addToSendQueue( packetRespawnPosition );
    }

    private List<io.gomint.inventory.item.ItemStack> getDrops() {
        List<io.gomint.inventory.item.ItemStack> drops = new ArrayList<>();

        for ( io.gomint.inventory.item.ItemStack itemStack : this.inventory.getContentsArray() ) {
            if ( !( itemStack instanceof ItemAir ) ) {
                drops.add( itemStack );
            }
        }

        for ( io.gomint.inventory.item.ItemStack itemStack : this.offhandInventory.getContentsArray() ) {
            if ( !( itemStack instanceof ItemAir ) ) {
                drops.add( itemStack );
            }
        }

        for ( io.gomint.inventory.item.ItemStack itemStack : this.armorInventory.getContentsArray() ) {
            if ( !( itemStack instanceof ItemAir ) ) {
                drops.add( itemStack );
            }
        }

        return drops;
    }

    @Override
    protected void checkIfCollided( float movX, float movY, float movZ, float dX, float dY, float dZ ) {
        // Check if we are not on ground or we moved on y axis
        if ( !this.onGround || movY != 0 ) {
            AxisAlignedBB bb = new AxisAlignedBB( this.boundingBox.getMinX(), this.boundingBox.getMinY() - 0.2f, this.boundingBox.getMinZ(),
                this.boundingBox.getMaxX(), this.boundingBox.getMaxY(), this.boundingBox.getMaxZ() );

            // Check if we collided with a block
            this.onGround = this.world.getCollisionCubes( this, bb, false ) != null;
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
    public void disconnect( String reason ) {
        this.connection.disconnect( reason );
    }

    // ------- GUI stuff
    public void sendServerSettings() {
        if ( this.serverSettingsForm != -1 ) {
            io.gomint.server.gui.Form form = this.forms.get( this.serverSettingsForm );

            PacketServerSettingsResponse response = new PacketServerSettingsResponse();
            response.setFormId( this.serverSettingsForm );
            response.setJson( form.toJSON().toJSONString() );
            LOGGER.debug( "Sending settings form: {}", response );
            this.connection.addToSendQueue( response );
        }
    }

    @Override
    public <T> FormListener<T> showForm( Form form ) {
        int formId = this.formId++;
        io.gomint.server.gui.Form implForm = (io.gomint.server.gui.Form) form;

        this.forms.put( formId, implForm );

        io.gomint.server.gui.FormListener formListener = null;
        if ( form instanceof ButtonList ) {
            formListener = new io.gomint.server.gui.FormListener<String>();
        } else if ( form instanceof Modal ) {
            formListener = new io.gomint.server.gui.FormListener<Boolean>();
        } else {
            formListener = new io.gomint.server.gui.FormListener<FormResponse>();
        }

        this.formListeners.put( formId, formListener );

        // Send packet for client
        String json = implForm.toJSON().toJSONString();
        PacketModalRequest packetModalRequest = new PacketModalRequest();
        packetModalRequest.setFormId( formId );
        packetModalRequest.setJson( json );
        this.connection.addToSendQueue( packetModalRequest );

        return formListener;
    }

    @Override
    public <T> FormListener<T> setSettingsForm( Form form ) {
        if ( this.serverSettingsForm != -1 ) {
            this.removeSettingsForm();
        }

        int formId = this.formId++;
        io.gomint.server.gui.Form implForm = (io.gomint.server.gui.Form) form;

        this.forms.put( formId, implForm );

        io.gomint.server.gui.FormListener formListener = null;
        if ( form instanceof ButtonList ) {
            formListener = new io.gomint.server.gui.FormListener<String>();
        } else if ( form instanceof Modal ) {
            formListener = new io.gomint.server.gui.FormListener<Boolean>();
        } else {
            formListener = new io.gomint.server.gui.FormListener<FormResponse>();
        }

        this.formListeners.put( formId, formListener );
        this.serverSettingsForm = formId;
        return formListener;
    }

    @Override
    public void removeSettingsForm() {
        if ( this.serverSettingsForm != -1 ) {
            this.forms.remove( this.serverSettingsForm );
            this.formListeners.remove( this.serverSettingsForm );
            this.serverSettingsForm = -1;
        }
    }

    public void parseGUIResponse( int formId, String json ) {
        // Get the listener and the form
        Form form = this.forms.get( formId );
        if ( form != null ) {
            // Get listener
            io.gomint.server.gui.FormListener formListener = this.formListeners.get( formId );

            if ( this.serverSettingsForm != formId ) {
                this.forms.remove( formId );
                this.formListeners.remove( formId );
            }

            if ( json.equals( "null" ) ) {
                formListener.getCloseConsumer().accept( null );
            } else {
                io.gomint.server.gui.Form implForm = (io.gomint.server.gui.Form) form;
                Object resp = implForm.parseResponse( json );
                if ( resp == null ) {
                    formListener.getCloseConsumer().accept( null );
                } else {
                    formListener.getResponseConsumer().accept( resp );
                }
            }
        }
    }

    @Override
    public void despawn() {
        for ( Entity entity : new HashSet<>( this.getAttachedEntities() ) ) {
            if ( entity instanceof EntityPlayer ) {
                ( (EntityPlayer) entity ).getEntityVisibilityManager().removeEntity( this );
            }
        }
    }

    /**
     * Add xp from a orb
     *
     * @param xpAmount which should be added
     */
    public void addXP( int xpAmount ) {
        this.lastPickupXP = this.world.getServer().getCurrentTickTime();
        this.setXP( this.xp + xpAmount );
    }

    /**
     * A player can only pickup xp orbs at a rate of 1 per tick
     *
     * @return
     */
    public boolean canPickupXP() {
        return this.world.getServer().getCurrentTickTime() - this.lastPickupXP >= 50;
    }

    private int calculateRequiredExperienceForLevel( int level ) {
        if ( level >= 30 ) {
            return 112 + ( level - 30 ) * 9;
        } else if ( level >= 15 ) {
            return 37 + ( level - 15 ) * 5;
        } else {
            return 7 + level * 2;
        }
    }

    @Override
    public float getXPPercentage() {
        return this.getAttribute( Attribute.EXPERIENCE );
    }

    @Override
    public int getXP() {
        return this.xp;
    }

    @Override
    public void setXP( int xp ) {
        // Iterate levels until we have a new xp percentage value to set
        int neededXP, tempXP = xp, level = 0;
        while ( tempXP > ( neededXP = calculateRequiredExperienceForLevel( getXPLevel() ) ) ) {
            tempXP -= neededXP;
            level++;
        }

        this.xp = xp;
        this.setAttribute( Attribute.EXPERIENCE, tempXP / (float) neededXP );
        this.setXPLevel( level );
    }

    protected boolean shouldTickHunger() {
        return this.gamemode == Gamemode.SURVIVAL;
    }

    @Override
    public int getXPLevel() {
        return (int) this.getAttribute( Attribute.EXPERIENCE_LEVEL );
    }

    @Override
    public void setXPLevel( int level ) {
        this.setAttribute( Attribute.EXPERIENCE_LEVEL, level );
    }

    @Override
    public void playSound( Vector vector, String soundName ) {
        this.playSound( vector, soundName, 1, 1 );
    }

    @Override
    public void playSound(  Vector vector, String soundName, float pitch ) {
        this.playSound( vector, soundName, pitch, 1 );
    }

    @Override
    public void playSound( Vector vector, String soundName, float pitch, float volume ) {
        PacketPlaySound soundPacket = new PacketPlaySound();
        soundPacket.setSoundName( soundName );
        soundPacket.setPitch( pitch );
        soundPacket.setVolume( volume );
        soundPacket.setPosition( vector.toBlockPosition() );
        this.connection.addToSendQueue( soundPacket );
    }

    @Override
    public void stopSound() {
        PacketStopSound soundPacket = new PacketStopSound();
        soundPacket.setStopAll( true );
        this.connection.addToSendQueue( soundPacket );
    }

    @Override
    public void stopSound( String soundName ) {
        PacketStopSound soundPacket = new PacketStopSound();
        soundPacket.setSoundName( soundName );
        soundPacket.setStopAll( false );
        this.connection.addToSendQueue( soundPacket );
    }

    @Override
    public void sendParticle( Vector location, Particle particle ) {
        this.world.sendParticle( this, location, particle, 0 );
    }

    @Override
    public void sendParticle( Vector location, Particle particle, ParticleData data ) {
        this.world.sendParticle( this, location, particle, data );
    }

    @Override
    public void setAllowFlight( boolean value ) {
        this.metadataContainer.setDataFlag( MetadataContainer.DATA_INDEX, EntityFlag.CAN_FLY, value );
        this.adventureSettings.setCanFly( value );
        this.adventureSettings.update();
    }

    @Override
    public boolean getAllowFlight() {
        return this.adventureSettings.isCanFly();
    }

    @Override
    public void setFlying( boolean value ) {
        this.adventureSettings.setFlying( value );
        this.adventureSettings.update();
    }

    @Override
    public boolean getFlying() {
        return this.adventureSettings.isFlying();
    }

    @Override
    public void sendActionbar( String message, long fadein, long duration, long fadeout, TimeUnit unit ) {
        this.sendTitleAnimationTimes( fadein, duration, fadeout, unit );
        this.sendActionbar( message );
    }

    @Override
    public void sendActionbar( String message ) {
        PacketSetTitle titlePacket = new PacketSetTitle();
        titlePacket.setType( PacketSetTitle.TitleType.TYPE_ACTION_BAR.getId() );
        titlePacket.setText( message );
        this.getConnection().addToSendQueue( titlePacket );
    }

    @Override
    public void sendTitle( String title, String subtitle, long fadein, long duration, long fadeout, TimeUnit unit ) {
        this.sendTitleAnimationTimes( fadein, duration, fadeout, unit );
        this.sendTitle( title, subtitle );
    }

    @Override
    public void sendTitle( String title ) {
        this.sendTitle( title, "" );
    }

    @Override
    public void sendTitle( String title, String subtitle ) {
        if ( subtitle != null && !Objects.equals( subtitle, "" ) ) {
            PacketSetTitle subtitlePacket = new PacketSetTitle();
            subtitlePacket.setType( PacketSetTitle.TitleType.TYPE_SUBTITLE.getId() );
            subtitlePacket.setText( subtitle );
            this.getConnection().addToSendQueue( subtitlePacket );
        }

        if( title != null && !Objects.equals( title, "" ) ) {
            PacketSetTitle titlePacket = new PacketSetTitle();
            titlePacket.setType( PacketSetTitle.TitleType.TYPE_TITLE.getId() );
            titlePacket.setText( title );
            this.getConnection().addToSendQueue( titlePacket );
        }
    }

    @Override
    public void sendTitleAnimationTimes( long fadein, long duration, long fadeout, TimeUnit unit ) {
        PacketSetTitle titlePacket = new PacketSetTitle();
        titlePacket.setType( PacketSetTitle.TitleType.TYPE_ANIMATION_TIMES.getId() );
        titlePacket.setFadeInTime( (int) unit.toMillis( fadein ) / 50 );
        titlePacket.setStayTime( (int) unit.toMillis( duration ) / 50 );
        titlePacket.setFadeOutTime( (int) unit.toMillis( fadeout ) / 50 );
        this.getConnection().addToSendQueue( titlePacket );
    }

    @Override
    public void clearTitle() {
        PacketSetTitle packet = new PacketSetTitle();
        packet.setType( PacketSetTitle.TitleType.TYPE_CLEAR.getId() );
        this.getConnection().addToSendQueue( packet );
    }

    @Override
    public void resetTitle() {
        PacketSetTitle packet = new PacketSetTitle();
        packet.setType( PacketSetTitle.TitleType.TYPE_RESET.getId() );
        this.getConnection().addToSendQueue( packet );
    }

    public void firstSpawn() {
        this.connection.sendPlayState( PacketPlayState.PlayState.SPAWN );
        this.getConnection().sendMovePlayer( this.getLocation() );

        // Spawn for others
        this.getWorld().spawnEntityAt( this, this.getPositionX(), this.getPositionY(), this.getPositionZ(), this.getYaw(), this.getPitch() );

        // Now its time for the join event since the player is fully loaded
        PlayerJoinEvent event = this.getConnection().getNetworkManager().getServer().getPluginManager().callEvent( new PlayerJoinEvent( this ) );
        if ( event.isCancelled() ) {
            this.connection.disconnect( event.getKickReason() );
        }
    }

    @Override
    public void preSpawn( PlayerConnection connection ) {

    }

    @Override
    public void postSpawn( PlayerConnection connection ) {
        // TODO: Remove this, its a client bug in 1.2.13
        PacketEntityMetadata metadata = new PacketEntityMetadata();
        metadata.setEntityId( this.getEntityId() );
        metadata.setMetadata( this.metadataContainer );
        connection.addToSendQueue( metadata );
    }

    @Override
    public void setGliding( boolean value ) {
        this.metadataContainer.setDataFlag( MetadataContainer.DATA_INDEX, EntityFlag.GLIDING, value );
    }

    @Override
    public boolean isGliding() {
        return this.metadataContainer.getDataFlag( MetadataContainer.DATA_INDEX, EntityFlag.GLIDING );
    }

    @Override
    public DeviceInfo getDeviceInfo() {
        return this.connection.getDeviceInfo();
    }

    @Override
    protected void checkAfterGravity() {
        float dX = this.getMotionX();
        float dY = this.getMotionY();
        float dZ = this.getMotionZ();

        // Check if we collide with some blocks when we would move that fast
        List<AxisAlignedBB> collisionList = this.world.getCollisionCubes( this, this.boundingBox.getOffsetBoundingBox( dX, dY, dZ ), false );
        if ( collisionList != null ) {
            // Check if we would hit a y border block
            for ( AxisAlignedBB axisAlignedBB : collisionList ) {
                dY = axisAlignedBB.calculateYOffset( this.boundingBox, dY );
            }

            if ( Math.abs( dY ) <= 0.001 ) {
                dY = 0;
            }

            this.getTransform().setMotion( dX, dY, dZ );
        }
    }

    @Override
    public CommandOutput dispatchCommand( String command ) {
        return this.getConnection().getServer().getPluginManager().getCommandManager().dispatchCommand( this, command );
    }

    @Override
    public void setSpawnLocation( Location spawnLocation ) {
        this.spawnLocation = spawnLocation;
        this.connection.sendPlayerSpawnPosition();
    }

    public void setUsingItem( boolean value ) {
        if ( value ) {
            this.actionStart = ( (GoMintServer) GoMint.instance() ).getCurrentTickTime();
            this.metadataContainer.setDataFlag( MetadataContainer.DATA_INDEX, EntityFlag.ACTION, true );
        } else {
            this.actionStart = -1;
            this.metadataContainer.setDataFlag( MetadataContainer.DATA_INDEX, EntityFlag.ACTION, false );
        }
    }

    @Override
    protected void checkBlockCollisions() {
        List<io.gomint.world.block.Block> blockList = this.world.getCollisionBlocks( this, true );
        if ( blockList != null ) {
            for ( io.gomint.world.block.Block block : blockList ) {
                io.gomint.server.world.block.Block implBlock = (io.gomint.server.world.block.Block) block;
                implBlock.onEntityCollision( this );
            }
        }
    }

    public Collection<ContainerInventory> getOpenInventories() {
        return this.windowIds.values();
    }

    @Override
    public void initFromNBT( NBTTagCompound compound ) {
        super.initFromNBT( compound );
    }

    @Override
    public NBTTagCompound persistToNBT() {
        NBTTagCompound compound = super.persistToNBT();
        return compound;
    }

    @Override
    public boolean shouldBeSeen( EntityPlayer player ) {
        return player.isSpawnPlayers() && super.shouldBeSeen( player );
    }

}
