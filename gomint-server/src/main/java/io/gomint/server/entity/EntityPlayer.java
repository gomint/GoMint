/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.entity;

import com.koloboke.collect.ObjCursor;
import com.koloboke.collect.map.ByteObjCursor;
import io.gomint.entity.ChatType;
import io.gomint.entity.Entity;
import io.gomint.event.entity.EntityDamageByEntityEvent;
import io.gomint.event.entity.EntityDamageEvent;
import io.gomint.event.player.*;
import io.gomint.gui.*;
import io.gomint.math.*;
import io.gomint.math.Vector;
import io.gomint.server.entity.metadata.MetadataContainer;
import io.gomint.server.entity.projectile.EntityFishingHook;
import io.gomint.server.inventory.*;
import io.gomint.server.inventory.item.ItemAir;
import io.gomint.server.inventory.item.ItemStack;
import io.gomint.server.network.PlayerConnection;
import io.gomint.server.network.PlayerConnectionState;
import io.gomint.server.network.packet.*;
import io.gomint.server.permission.PermissionManager;
import io.gomint.server.player.EntityVisibilityManager;
import io.gomint.server.player.PlayerSkin;
import io.gomint.server.util.EnumConnectors;
import io.gomint.server.util.collection.*;
import io.gomint.server.world.ChunkAdapter;
import io.gomint.server.world.CoordinateUtils;
import io.gomint.server.world.WorldAdapter;
import io.gomint.server.world.block.Block;
import io.gomint.world.Gamemode;
import io.gomint.world.Sound;
import io.gomint.world.SoundData;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

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
@EqualsAndHashCode( callSuper = false, of = { "uuid" } )
@ToString( of = { "username" } )
public class EntityPlayer extends EntityHuman implements io.gomint.entity.EntityPlayer, InventoryHolder {

    private static final Logger LOGGER = LoggerFactory.getLogger( EntityPlayer.class );

    private final PlayerConnection connection;
    private int viewDistance = 4;
    private Queue<ChunkAdapter> chunkSendQueue = new LinkedBlockingQueue<>();

    // EntityPlayer Information
    private String username;
    private String displayName;
    private UUID uuid;
    private String xboxId;
    @Setter
    private PlayerSkin skin;
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
    private HiddenPlayerSet hiddenPlayers;

    // Inventory
    private PlayerInventory inventory;
    private ArmorInventory armorInventory;
    private Inventory craftingInventory;
    private Inventory cursorInventory;
    private Inventory offhandInventory;
    private Inventory craftingInputInventory;
    private Inventory craftingResultInventory;
    private ContainerObjectMap windowIds;
    private ContainerIDMap containerIds;

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
    private Queue<BlockPosition> blockUpdates = new ConcurrentLinkedQueue<>();
    @Getter
    @Setter
    private Location teleportPosition = null;

    // Form stuff
    private int formId;
    private FormIDMap forms = FormIDMap.withExpectedSize( 2 );
    private FormListenerIDMap formListeners = FormListenerIDMap.withExpectedSize( 2 );

    // Entity data
    @Getter
    @Setter
    private EntityFishingHook fishingHook;
    private long lastPickupXP;

    // Bow ticking
    @Getter
    @Setter
    private long startBow = -1;

    // Exp
    private int xp;

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
        this.username = username;
        this.displayName = username;
        this.xboxId = xboxId;
        this.uuid = uuid;
        this.locale = locale;
        this.adventureSettings = new AdventureSettings( this );
        this.setSize( 0.6f, 1.8f );
        this.eyeHeight = 1.62f;
        this.stepHeight = 0.6f;
        this.initAttributes();

        this.setNameTagAlwaysVisible( true );
        this.setCanClimb( true );

        this.metadataContainer.putString( MetadataContainer.DATA_NAMETAG, this.username );
    }

    private void initAttributes() {
        addAttribute( Attribute.HUNGER );
        addAttribute( Attribute.SATURATION );
        addAttribute( Attribute.EXHAUSTION );
        addAttribute( Attribute.EXPERIENCE_LEVEL );
        addAttribute( Attribute.EXPERIENCE );
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
        PacketTransfer packetTransfer = new PacketTransfer();
        packetTransfer.setAddress( host );
        packetTransfer.setPort( port );
        this.connection.addToSendQueue( packetTransfer );
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
            this.connection.onViewDistanceChanged();
        }
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
    public String getName() {
        return this.username;
    }

    @Override
    public UUID getUUID() {
        return this.uuid;
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

        // Recalc adventure settings
        this.adventureSettings.setWorldImmutable( gameModeNumber == 0x03 );
        this.adventureSettings.setCanFly( ( gameModeNumber & 0x01 ) > 0 );
        this.adventureSettings.setNoClip( gameModeNumber == 0x03 );
        this.adventureSettings.setFlying( gameModeNumber == 0x03 );
        this.adventureSettings.setAttackMobs( gameModeNumber < 0x02 );
        this.adventureSettings.setAttackPlayers( gameModeNumber < 0x02 );
        this.adventureSettings.setNoPvP( gameModeNumber == 0x03 );
        this.adventureSettings.update();

        // Set fly
        if ( this.gamemode == Gamemode.SPECTATOR || this.gamemode == Gamemode.CREATIVE ) {
            this.metadataContainer.setDataFlag( MetadataContainer.DATA_INDEX, EntityFlag.CAN_FLY, true );
        } else { // TODO: Check for API set fly flag
            this.metadataContainer.setDataFlag( MetadataContainer.DATA_INDEX, EntityFlag.CAN_FLY, false );
        }

        // Set invis
        if ( this.gamemode == Gamemode.SPECTATOR ) {
            this.metadataContainer.setDataFlag( MetadataContainer.DATA_INDEX, EntityFlag.INVISIBLE, true );
        } else { // TODO: Check for invis effect
            this.metadataContainer.setDataFlag( MetadataContainer.DATA_INDEX, EntityFlag.INVISIBLE, false );
        }
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
    public void hidePlayer( io.gomint.entity.EntityPlayer player ) {
        EntityPlayer other = (EntityPlayer) player;

        if ( this.hiddenPlayers == null ) {
            this.hiddenPlayers = HiddenPlayerSet.withExpectedSize( 5 );
        }

        this.hiddenPlayers.add( other.getEntityId() );

        // Remove the entity clientside
        PacketDespawnEntity packetDespawnEntity = new PacketDespawnEntity();
        packetDespawnEntity.setEntityId( other.getEntityId() );
        getConnection().addToSendQueue( packetDespawnEntity );

        // Remove from player list
        PacketPlayerlist packetPlayerlist = new PacketPlayerlist();
        packetPlayerlist.setMode( (byte) 1 );
        packetPlayerlist.setEntries( new ArrayList<PacketPlayerlist.Entry>() {{
            add( new PacketPlayerlist.Entry( other.getUUID(), other.getEntityId(), null, null, null ) );
        }} );
        getConnection().addToSendQueue( packetPlayerlist );
    }

    @Override
    public void showPlayer( io.gomint.entity.EntityPlayer player ) {
        if ( this.hiddenPlayers == null ) {
            return;
        }

        if ( this.hiddenPlayers.removeLong( player.getEntityId() ) ) {
            EntityPlayer other = (EntityPlayer) player;

            // Send tablist and spawn packet
            PacketPlayerlist packetPlayerlist = new PacketPlayerlist();
            packetPlayerlist.setMode( (byte) 0 );
            packetPlayerlist.setEntries( new ArrayList<PacketPlayerlist.Entry>() {{
                add( new PacketPlayerlist.Entry( other.getUUID(), other.getEntityId(), other.getDisplayName(), other.getXboxID(), other.getSkin() ) );
            }} );
            getConnection().addToSendQueue( packetPlayerlist );
            getConnection().addToSendQueue( other.createSpawnPacket() );
        }
    }

    @Override
    public boolean isHidden( io.gomint.entity.EntityPlayer player ) {
        return this.hiddenPlayers != null && this.hiddenPlayers.contains( player.getEntityId() );
    }

    @Override
    public io.gomint.player.PlayerSkin getSkin() {
        return this.skin;
    }

    @Override
    public void teleport( Location to ) {
        Location from = getLocation();

        this.setAndRecalcPosition( to );

        // Check if we need to change worlds
        if ( !to.getWorld().equals( from.getWorld() ) ) {
            // Change worlds first
            getWorld().removePlayer( this );
            this.world = (WorldAdapter) to.getWorld();
            this.world.spawnEntityAt( this, to.getX(), to.getY(), to.getZ(), to.getYaw(), to.getPitch() );
            this.connection.resetPlayerChunks();
            this.entityVisibilityManager.clear();
            this.connection.sendMovePlayer( new Location( to.getWorld(), getPositionX() + 1000000, 4000, getPositionZ() + 1000000 ) );

            int chunkX = CoordinateUtils.fromBlockToChunk( (int) to.getX() );
            int chunkZ = CoordinateUtils.fromBlockToChunk( (int) to.getZ() );
            this.world.movePlayerToChunk( chunkX, chunkZ, this );
        }

        this.connection.sendMovePlayer( to );
        this.fallDistance = 0;
        this.connection.checkForNewChunks( from );
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
        super.update( currentTimeMS, dT );

        // Update permissions
        this.permissionManager.update( currentTimeMS, dT );

        if ( this.isDead() || this.getHealth() <= 0 ) {
            return;
        }

        // Look around
        Collection<Entity> nearbyEntities = this.world.getNearbyEntities( this.boundingBox.grow( 1, 0.5f, 1 ), this );
        if ( nearbyEntities != null ) {
            for ( Entity nearbyEntity : nearbyEntities ) {
                io.gomint.server.entity.Entity implEntity = (io.gomint.server.entity.Entity) nearbyEntity;
                implEntity.onCollideWithPlayer( this );
            }
        }

        // Update attributes which are flagged as dirty
        this.updateAttributes();
    }

    @Override
    public AxisAlignedBB getBoundingBox() {
        return this.boundingBox;
    }

    /**
     * Get the players inventory
     *
     * @return the players inventory
     */
    @Override
    public PlayerInventory getInventory() {
        return inventory;
    }

    @Override
    public void openInventory( io.gomint.inventory.Inventory inventory ) {
        if ( inventory instanceof ContainerInventory ) {
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
                LOGGER.warn( "No free window id: " );

                ByteObjCursor<ContainerInventory> cursor = this.windowIds.cursor();
                while ( cursor.moveNext() ) {
                    LOGGER.warn( "ID " + cursor.key() + " -> " + cursor.value().getClass().getName() );
                }

                return;
            }

            // Trigger open
            ContainerInventory containerInventory = (ContainerInventory) inventory;
            this.windowIds.justPut( foundId, containerInventory );
            this.containerIds.justPut( containerInventory, foundId );
            containerInventory.addViewer( this, foundId );
        }
    }

    /**
     * Get the players armor
     *
     * @return the players armor
     */
    @Override
    public ArmorInventory getArmorInventory() {
        return armorInventory;
    }

    /**
     * Get the virtual inventory for the crafting slots
     *
     * @return the players crafting field
     */
    public Inventory getCraftingInventory() {
        return craftingInventory;
    }

    /**
     * Get the virtual inventory for the cursor item
     *
     * @return the players cursor item
     */
    public Inventory getCursorInventory() {
        return cursorInventory;
    }

    /**
     * Get the virtual inventory of the current crafting process
     *
     * @return the current crafting input inventory
     */
    public Inventory getCraftingInputInventory() {
        return craftingInputInventory;
    }

    /**
     * Get offhand inventory. This inventory only has one slot
     *
     * @return current offhand inventory
     */
    public Inventory getOffhandInventory() {
        return offhandInventory;
    }

    /**
     * Get the virtual inventory of the current crafting process
     *
     * @return the current crafting result inventory
     */
    public Inventory getCraftingResultInventory() {
        return craftingResultInventory;
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
     * Fully init inventory and recipes and other stuff which we need to have a full loaded player
     */
    public void fullyInit() {
        this.inventory = new PlayerInventory( this );
        this.armorInventory = new ArmorInventory( this );
        this.craftingInventory = new CraftingInputInventory( this );
        this.cursorInventory = new CursorInventory( this );
        this.offhandInventory = new OffhandInventory( this );
        this.craftingInputInventory = new CraftingInputInventory( this );
        this.craftingResultInventory = new CursorInventory( this );
        this.windowIds = ContainerObjectMap.withExpectedSize( 2 );
        this.containerIds = ContainerIDMap.withExpectedSize( 2 );
        this.connection.getServer().getCreativeInventory().addViewer( this );

        int gameModeNumber = EnumConnectors.GAMEMODE_CONNECTOR.convert( this.gamemode ).getMagicNumber();
        this.getAdventureSettings().setWorldImmutable( gameModeNumber == 0x03 );
        this.getAdventureSettings().setCanFly( ( gameModeNumber & 0x01 ) > 0 );
        this.getAdventureSettings().setNoClip( gameModeNumber == 0x03 );
        this.getAdventureSettings().setFlying( gameModeNumber == 0x03 );
        this.getAdventureSettings().setAttackMobs( gameModeNumber < 0x02 );
        this.getAdventureSettings().setAttackPlayers( gameModeNumber < 0x02 );
        this.getAdventureSettings().setNoPvP( gameModeNumber == 0x03 );
        this.getAdventureSettings().update();
        this.updateAttributes();
        this.connection.sendPlayState( PacketPlayState.PlayState.SPAWN );

        // Update player list
        PacketPlayerlist playerlist = new PacketPlayerlist();
        playerlist.setMode( (byte) 0 );
        playerlist.setEntries( new ArrayList<PacketPlayerlist.Entry>() {{
            add( new PacketPlayerlist.Entry( uuid, getEntityId(), displayName, xboxId, skin ) );
        }} );
        this.getConnection().send( playerlist );

        // Spawn for others
        this.getWorld().spawnEntityAt( this, this.getPositionX(), this.getPositionY(), this.getPositionZ(), this.getYaw(), this.getPitch() );

        this.getConnection().sendWorldTime( 0 );
        this.sendData( this );
        this.getConnection().sendMovePlayer( this.getLocation() );

        // Send crafting recipes
        this.connection.send( this.world.getServer().getRecipeManager().getCraftingRecipesBatch() );

        this.sendCommands();

        // Now its time for the join event since the player is fully loaded
        PlayerJoinEvent event = this.getConnection().getNetworkManager().getServer().getPluginManager().callEvent( new PlayerJoinEvent( this ) );
        if ( event.isCancelled() ) {
            this.connection.disconnect( event.getKickReason() );
        }
    }

    @Override
    public Packet createSpawnPacket() {
        PacketSpawnPlayer packetSpawnPlayer = new PacketSpawnPlayer();
        packetSpawnPlayer.setUuid( this.getUUID() );
        packetSpawnPlayer.setName( this.getName() );
        packetSpawnPlayer.setEntityId( this.getEntityId() );
        packetSpawnPlayer.setRuntimeEntityId( this.getEntityId() );

        packetSpawnPlayer.setX( this.getPositionX() );
        packetSpawnPlayer.setY( this.getPositionY() );
        packetSpawnPlayer.setZ( this.getPositionZ() );

        packetSpawnPlayer.setVelocityX( this.getMotionX() );
        packetSpawnPlayer.setVelocityY( this.getMotionY() );
        packetSpawnPlayer.setVelocityZ( this.getMotionZ() );

        packetSpawnPlayer.setPitch( this.getPitch() );
        packetSpawnPlayer.setYaw( this.getYaw() );
        packetSpawnPlayer.setHeadYaw( this.getHeadYaw() );

        packetSpawnPlayer.setItemInHand( this.getInventory().getItemInHand() );
        packetSpawnPlayer.setMetadataContainer( this.getMetadata() );
        return packetSpawnPlayer;
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
        Vector2 directionPlane = this.getDirectionVector();
        float dot = directionPlane.dot( new Vector2( eyePosition.getX(), eyePosition.getZ() ) );
        float dot1 = directionPlane.dot( new Vector2( playerPosition.getX(), playerPosition.getZ() ) );
        return ( dot1 - dot ) >= -0.5f;
    }

    /**
     * Remove player from PlayerList and remove from global inventories etc.
     */
    public void cleanup() {
        this.connection.getServer().getCreativeInventory().removeViewer( this );
        this.connection.getServer().getPlayersByUUID().remove( this.uuid );

        Block block = this.world.getBlockAt( this.getPosition().toBlockPosition() );
        block.gotOff( this );

        // Remove from player list
        PacketPlayerlist packetPlayerlist = new PacketPlayerlist();
        packetPlayerlist.setMode( (byte) 1 );
        packetPlayerlist.setEntries( new ArrayList<PacketPlayerlist.Entry>() {{
            add( new PacketPlayerlist.Entry( uuid, getEntityId(), null, null, null ) );
        }} );

        // Cleanup the visibility manager
        this.entityVisibilityManager.clear();

        // Check all other players
        for ( io.gomint.entity.EntityPlayer player : this.connection.getServer().getPlayers() ) {
            EntityPlayer entityPlayer = (EntityPlayer) player;
            if ( !entityPlayer.equals( this ) ) {
                entityPlayer.getConnection().addToSendQueue( packetPlayerlist );
            }

            // Check if player did hide this one
            if ( entityPlayer.hiddenPlayers != null && entityPlayer.hiddenPlayers.contains( getEntityId() ) ) {
                entityPlayer.hiddenPlayers.removeLong( getEntityId() );
            }

            // Check if mouseover is the entity
            if ( entityPlayer.hoverEntity != null && entityPlayer.hoverEntity.equals( this ) ) {
                entityPlayer.hoverEntity = null;
            }

            entityPlayer.entityVisibilityManager.removeEntity( this );
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
            LOGGER.info( getName() + " closing inventory " + windowId );
            containerInventory.removeViewer( this );
            this.containerIds.justRemove( containerInventory );
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

    /**
     * Xbox User ID
     *
     * @return xbox user id
     */
    @Override
    public String getXboxID() {
        return this.xboxId;
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
        this.connection.send( packetAvailableCommands );
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
            if ( targetEntity.canBeAttackedWithAnItem() ) {
                if ( !targetEntity.isInvulnerableFrom( this ) ) {
                    boolean success = false;

                    // Get this entity attack damage
                    EntityDamageEvent.DamageSource damageSource = EntityDamageEvent.DamageSource.ENTITY_ATTACK;
                    float damage = this.getAttribute( Attribute.ATTACK_DAMAGE );

                    // TODO: Enchantment modifiers

                    // Check for knockback stuff
                    int knockbackLevel = 0;

                    if ( this.isSprinting() ) {
                        knockbackLevel++;
                    }

                    // TODO: Add knockback enchantment

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
                                targetVelo.add(
                                    (float) ( -Math.sin( this.getYaw() * (float) Math.PI / 180.0F ) * (float) knockbackLevel * 0.5F ),
                                    0.1f,
                                    (float) ( Math.cos( this.getYaw() * (float) Math.PI / 180.0F ) * (float) knockbackLevel * 0.5F ) );
                                targetEntity.setVelocity( targetVelo );

                                // Modify our velocity / movement
                                Vector ownVelo = this.getVelocity();
                                ownVelo.setX( ownVelo.getX() * 0.6F );
                                ownVelo.setZ( ownVelo.getZ() * 0.6F );
                                this.setVelocity( ownVelo );
                                this.setSprinting( false );
                            }

                            targetEntity.broadCastMotion();
                        }
                    }

                    this.exhaust( 0.3f, PlayerExhaustEvent.Cause.ATTACK );
                    return success;
                }
            }
        }

        return false;
    }

    @Override
    public boolean damage( EntityDamageEvent damageEvent ) {
        // Can't touch this!
        return this.gamemode != Gamemode.CREATIVE && this.gamemode != Gamemode.SPECTATOR && super.damage( damageEvent );
    }

    @Override
    float applyArmorReduction( EntityDamageEvent damageEvent ) {
        if ( damageEvent.getDamageSource() == EntityDamageEvent.DamageSource.FALL ||
            damageEvent.getDamageSource() == EntityDamageEvent.DamageSource.VOID ||
            damageEvent.getDamageSource() == EntityDamageEvent.DamageSource.DROWNING ) {
            return damageEvent.getDamage();
        }

        float damage = damageEvent.getDamage();
        float maxReductionDiff = 25 - this.armorInventory.getTotalArmorValue();
        float amplifiedDamage = damage * maxReductionDiff;
        this.armorInventory.damageEvenly( damage );
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

        // TODO: Remove effects

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

        for ( io.gomint.inventory.item.ItemStack itemStack : this.inventory.getContents() ) {
            if ( !( itemStack instanceof ItemAir ) ) {
                drops.add( itemStack );
            }
        }

        for ( io.gomint.inventory.item.ItemStack itemStack : this.offhandInventory.getContents() ) {
            if ( !( itemStack instanceof ItemAir ) ) {
                drops.add( itemStack );
            }
        }

        for ( io.gomint.inventory.item.ItemStack itemStack : this.armorInventory.getContents() ) {
            if ( !( itemStack instanceof ItemAir ) ) {
                drops.add( itemStack );
            }
        }

        return drops;
    }

    @Override
    void checkIfCollided( float movX, float movY, float movZ, float dX, float dY, float dZ ) {
        // Check if we are not on ground or we moved on y axis
        if ( !this.onGround || movY != 0 ) {
            AxisAlignedBB bb = this.boundingBox.grow( 0, 0.2f, 0 );

            // Check if we collided with a block
            this.onGround = this.world.getCollisionCubes( this, bb, false ) != null;
        }

        this.isCollided = this.onGround;
    }

    @Override
    public String getDisplayName() {
        return this.displayName;
    }

    @Override
    public void setDisplayName( String displayName ) {
        this.displayName = displayName;
        if ( this.connection.getState() == PlayerConnectionState.PLAYING ) {
            PacketPlayerlist packetPlayerlist = new PacketPlayerlist();
            packetPlayerlist.setMode( (byte) 0 );
            packetPlayerlist.setEntries( new ArrayList<PacketPlayerlist.Entry>() {{
                add( new PacketPlayerlist.Entry( uuid, getEntityId(), displayName, xboxId, skin ) );
            }} );

            for ( io.gomint.entity.EntityPlayer player : this.connection.getServer().getPlayers() ) {
                ( (EntityPlayer) player ).connection.addToSendQueue( packetPlayerlist );
            }
        }
    }

    @Override
    public boolean isOnline() {
        return this.connection.getServer().findPlayerByUUID( this.uuid ).equals( this );
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
    @Override
    public <T> FormListener<T> showForm( Form form ) {
        int formId = this.formId++;
        io.gomint.server.gui.Form implForm = (io.gomint.server.gui.Form) form;

        this.forms.justPut( formId, implForm );

        io.gomint.server.gui.FormListener formListener = null;
        if ( form instanceof ButtonList ) {
            formListener = new io.gomint.server.gui.FormListener<String>();
        } else if ( form instanceof Modal ) {
            formListener = new io.gomint.server.gui.FormListener<Boolean>();
        } else {
            formListener = new io.gomint.server.gui.FormListener<FormResponse>();
        }

        this.formListeners.justPut( formId, formListener );

        // Send packet for client
        String json = implForm.toJSON().toJSONString();
        PacketModalRequest packetModalRequest = new PacketModalRequest();
        packetModalRequest.setFormId( formId );
        packetModalRequest.setJson( json );
        this.connection.addToSendQueue( packetModalRequest );

        return formListener;
    }

    public void parseGUIResponse( int formId, String json ) {
        // Get the listener and the form
        Form form = this.forms.get( formId );
        if ( form != null ) {
            // Get listener
            io.gomint.server.gui.FormListener formListener = this.formListeners.get( formId );

            this.forms.justRemove( formId );
            this.formListeners.justRemove( formId );

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
        ObjCursor<Entity> entityObjCursor = getAttachedEntities().cursor();
        while ( entityObjCursor.moveNext() ) {
            Entity entity = entityObjCursor.elem();
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
        while ( tempXP > ( neededXP = calculateRequiredExperienceForLevel( getLevel() ) ) ) {
            tempXP -= neededXP;
            level++;
        }

        this.xp = xp;
        this.setAttribute( Attribute.EXPERIENCE, tempXP / (float) neededXP );
        this.setLevel( level );
    }

    @Override
    public int getLevel() {
        return (int) this.getAttribute( Attribute.EXPERIENCE_LEVEL );
    }

    @Override
    public void setLevel( int level ) {
        this.setAttribute( Attribute.EXPERIENCE_LEVEL, level );
    }

    @Override
    public void playSound( Vector location, Sound sound, byte pitch, SoundData data ) {
        this.world.playSound( this, location, sound, pitch, data );
    }

    @Override
    public void playSound( Vector location, Sound sound, byte pitch ) {
        this.world.playSound( this, location, sound, pitch, -1 );
    }

}
