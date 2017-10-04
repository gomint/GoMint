/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.entity;

import com.koloboke.collect.map.ByteObjMap;
import com.koloboke.collect.map.ObjByteMap;
import com.koloboke.collect.map.hash.HashByteObjMaps;
import com.koloboke.collect.map.hash.HashObjByteMaps;
import com.koloboke.collect.set.LongSet;
import com.koloboke.collect.set.hash.HashLongSets;
import io.gomint.GoMint;
import io.gomint.entity.ChatType;
import io.gomint.entity.Entity;
import io.gomint.entity.Player;
import io.gomint.event.player.PlayerJoinEvent;
import io.gomint.inventory.item.ItemAcaciaDoor;
import io.gomint.inventory.item.ItemWoodPlanks;
import io.gomint.math.*;
import io.gomint.math.Vector;
import io.gomint.server.entity.metadata.MetadataContainer;
import io.gomint.server.entity.passive.EntityItem;
import io.gomint.server.inventory.*;
import io.gomint.server.inventory.transaction.TransactionGroup;
import io.gomint.server.network.PlayerConnection;
import io.gomint.server.network.packet.*;
import io.gomint.server.player.PlayerSkin;
import io.gomint.server.util.EnumConnectors;
import io.gomint.server.world.ChunkAdapter;
import io.gomint.server.world.WorldAdapter;
import io.gomint.server.world.block.Block;
import io.gomint.util.Numbers;
import io.gomint.world.Gamemode;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.*;
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
public class EntityPlayer extends EntityHuman implements Player, InventoryHolder {

    private static final Logger LOGGER = LoggerFactory.getLogger( EntityPlayer.class );

    private final PlayerConnection connection;
    private int viewDistance;
    private Queue<ChunkAdapter> chunkSendQueue = new LinkedBlockingQueue<>();

    // Player Information
    private String username;
    private UUID uuid;
    private String xboxId;
    @Setter private PlayerSkin skin;
    private Gamemode gamemode = Gamemode.SURVIVAL;
    @Getter private AdventureSettings adventureSettings;
    @Getter @Setter private Entity hoverEntity;
    @Getter @Setter private boolean sneaking;

    // Hidden players
    private LongSet hiddenPlayers;

    // Inventory
    private PlayerInventory inventory;
    private ArmorInventory armorInventory;
    private Inventory craftingInventory;
    private Inventory cursorInventory;
    private Inventory craftingInputInventory;
    private Inventory craftingResultInventory;
    @Setter @Getter private TransactionGroup transactions;
    private ByteObjMap<ContainerInventory> windowIds;
    private ObjByteMap<ContainerInventory> containerIds;

    // Block break data
    @Setter @Getter private BlockPosition breakVector;
    @Setter @Getter private long startBreak;
    @Setter @Getter private long breakTime;

    /**
     * Constructs a new player entity which will be spawned inside the specified world.
     *
     * @param world      The world the entity should spawn in
     * @param connection The specific player connection associated with this entity
     * @param username   The name the user has chosen
     * @param xboxId     The xbox id from xbox live which has logged in
     * @param uuid       The uuid which has been sent from the client
     */
    public EntityPlayer( WorldAdapter world,
                         PlayerConnection connection,
                         String username,
                         String xboxId,
                         UUID uuid ) {
        super( EntityType.PLAYER, world );
        this.connection = connection;
        this.username = username;
        this.xboxId = xboxId;
        this.uuid = uuid;
        this.viewDistance = this.world.getServer().getServerConfig().getViewDistance();
        this.adventureSettings = new AdventureSettings( this );
        this.setSize( 0.6f, 1.8f );
        this.eyeHeight = 1.62f;
        this.stepHeight = 0.6f;
        this.initAttributes();

        this.setNameTagAlwaysVisible( true );
        this.setCanClimb( true );

        this.metadataContainer.putString( MetadataContainer.DATA_NAMETAG, this.username );
        this.metadataContainer.putShort( MetadataContainer.DATA_AIR, (short) 400 );
        this.metadataContainer.putShort( MetadataContainer.DATA_MAX_AIRDATA_MAX_AIR, (short) 400 );
        this.metadataContainer.putFloat( MetadataContainer.DATA_SCALE, 1.0f );
        this.metadataContainer.setDataFlag( MetadataContainer.DATA_INDEX, EntityFlag.BREATHING, true );
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
    public int getViewDistance() {
        return this.viewDistance;
    }

    @Override
    public void transfer( InetSocketAddress inetSocketAddress ) {
        String address = inetSocketAddress.getAddress().getHostAddress();
        int port = inetSocketAddress.getPort();
        PacketTransfer packetTransfer = new PacketTransfer();
        packetTransfer.setAddress( address );
        packetTransfer.setPort( port );
        this.connection.send( packetTransfer );
    }

    @Override
    public void setHealth( double amount ) {
        if( amount < 1 ) {
            amount = 0;
        }

        AttributeInstance attributeInstance = this.attributes.get( Attribute.HEALTH.getKey() );
        attributeInstance.setValue( (float) amount );
    }

    @Override
    public double getHealth( double amount ) {
        return this.attributes.get( Attribute.HEALTH.getKey() ).getValue();
    }

    @Override
    public void setFoodLevel( double amount ) {
        if( amount < 1 ) {
            amount = 0;
        }

        AttributeInstance attributeInstance = this.attributes.get( Attribute.HUNGER.getKey() );
        attributeInstance.setValue( (float) amount );
    }

    @Override
    public double getFoodLevel() {
        return this.attributes.get( Attribute.HUNGER.getKey() ).getValue();
    }

    @Override
    public void setLevel( int amount ) {
        if( amount < 1 ) {
            amount = 0;
        }

        AttributeInstance attributeInstance = this.attributes.get( Attribute.EXPERIENCE_LEVEL.getKey() );
        attributeInstance.setValue( (float) amount );
    }

    @Override
    public int getLevel() {
        return (int) this.attributes.get( Attribute.EXPERIENCE_LEVEL.getKey() ).getValue();
    }

    @Override
    public void setExperience( double amount ) {
        if( amount < 1 ) {
            amount = 0;
        }

        AttributeInstance attributeInstance = this.attributes.get( Attribute.EXPERIENCE.getKey() );
        attributeInstance.setValue( (float) amount );
    }

    @Override
    public double getExperience() {
        return this.attributes.get( Attribute.EXPERIENCE.getKey() ).getValue();
    }


    /**
     * Sets the view distance used to calculate the chunk to be sent to the player.
     *
     * @param viewDistance The view distance to set
     */
    public void setViewDistance( int viewDistance ) {
        int tempViewDistance = Math.min( viewDistance, this.getWorld().getServer().getServerConfig().getViewDistance() );
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
        packetSetGamemode.setGameMode( gameModeNumber & 0x01 );
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
    public void hidePlayer( Player player ) {
        EntityPlayer other = (EntityPlayer) player;
        if ( other.getWorld().equals( this.getWorld() ) ) {
            if ( this.hiddenPlayers == null ) {
                this.hiddenPlayers = HashLongSets.newMutableSet();
            }

            this.hiddenPlayers.add( other.getEntityId() );

            // Remove player from tablist and from ingame

        }
    }

    @Override
    public void showPlayer( Player player ) {
        if ( this.hiddenPlayers == null ) {
            return;
        }

        if ( this.hiddenPlayers.removeLong( player.getEntityId() ) ) {
            // Send tablist and spawn packet
        }
    }

    @Override
    public boolean isHidden( Player player ) {
        return this.hiddenPlayers != null && this.hiddenPlayers.contains( player.getEntityId() );
    }

    @Override
    public io.gomint.player.PlayerSkin getSkin() {
        return this.skin;
    }

    @Override
    public void teleport( Location to ) {
        Location from = getLocation();

        // Check if we need to change worlds
        if ( !to.getWorld().equals( getWorld() ) ) {
            // Change worlds first
            this.connection.sendMovePlayer( new Location( to.getWorld(), getPositionX() + 1000000, 4000, getPositionZ() + 1000000 ) );
            getWorld().removePlayer( this );
            despawn();
            setWorld( (WorldAdapter) to.getWorld() );
            this.connection.resetPlayerChunks();
        }

        this.connection.sendMovePlayer( to );

        setPosition( to );
        setPitch( to.getPitch() );
        setYaw( to.getYaw() );
        setHeadYaw( to.getHeadYaw() );

        this.connection.checkForNewChunks( from );
    }

    // ==================================== UPDATING ==================================== //

    @Override
    public void update( long currentTimeMS, float dT ) {
        super.update( currentTimeMS, dT );

        // Look around
        Collection<Entity> nearbyEntities = this.world.getNearbyEntities( this.boundingBox.grow( 1, 0.5f, 1 ), this );
        if ( nearbyEntities != null ) {
            for ( Entity nearbyEntity : nearbyEntities ) {
                if ( nearbyEntity instanceof EntityItem ) {
                    EntityItem entityItem = (EntityItem) nearbyEntity;

                    // Check if we can pick it up
                    if ( currentTimeMS > entityItem.getPickupTime() ) {
                        // Check if we have place in out inventory to store this item
                        if ( !this.inventory.hasPlaceFor( entityItem.getItemStack() ) ) {
                            continue;
                        }

                        // Consume the item
                        PacketPickupItemEntity packet = new PacketPickupItemEntity();
                        packet.setItemEntityId( entityItem.getEntityId() );
                        packet.setPlayerEntityId( this.getEntityId() );

                        for ( Player player : this.world.getPlayers() ) {
                            if ( player instanceof EntityPlayer ) {
                                ( (EntityPlayer) player ).getConnection().addToSendQueue( packet );
                            }
                        }

                        // Manipulate inventory
                        this.inventory.addItem( entityItem.getItemStack() );
                        entityItem.despawn();
                    }
                }
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
                return;
            }

            // Trigger open
            ContainerInventory containerInventory = (ContainerInventory) inventory;
            this.windowIds.put( foundId, containerInventory );
            this.containerIds.put( containerInventory, foundId );
            containerInventory.addViewer( this, foundId );
        }
    }

    /**
     * Get the players armor
     *
     * @return the players armor
     */
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
            this.connection.send( updateAttributes );
        }
    }

    /**
     * Fully init inventory and recipes and other stuff which we need to have a full loaded player
     */
    public void fullyInit() {
        this.inventory = new PlayerInventory( this );
        this.armorInventory = new ArmorInventory( this );
        this.craftingInventory = new CraftingInputInventory( this );
        this.cursorInventory = new CursorInventory( this );
        this.craftingInputInventory = new CraftingInputInventory( this );
        this.craftingResultInventory = new CursorInventory( this );
        this.windowIds = HashByteObjMaps.newMutableMap();
        this.containerIds = HashObjByteMaps.newMutableMap();
        this.connection.getServer().getCreativeInventory().addViewer( this );

        // Testing items
        // TODO: Remove anytime soon
        this.inventory.setItem( 0, GoMint.instance().createItemStack( ItemWoodPlanks.class, 12 ) );
        this.inventory.setItem( 1, GoMint.instance().createItemStack( ItemAcaciaDoor.class, 1 ) );

        // Now its time for the join event since the play is fully loaded
        PlayerJoinEvent event = this.getConnection().getNetworkManager().getServer().getPluginManager().callEvent( new PlayerJoinEvent( this ) );
        if ( event.isCancelled() ) {
            this.connection.disconnect( event.getKickReason() );
            return;
        }

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
        playerlist.setEntries( new ArrayList<PacketPlayerlist.Entry>(){{
            add( new PacketPlayerlist.Entry( uuid, getEntityId(), username, xboxId, skin ) );
        }} );
        this.getConnection().send( playerlist );

        // Spawn for others
        this.getWorld().spawnEntityAt( this, this.getPositionX(), this.getPositionY(), this.getPositionZ(), this.getYaw(), this.getPitch() );

        this.getConnection().sendWorldTime( 0 );
        this.sendData( this );
        this.getConnection().sendMovePlayer( this.getLocation() );

        // Send crafting recipes
        PacketBatch batch = new PacketBatch();
        batch.setPayload( this.connection.getEncryptionHandler().encryptInputForClient(
                this.world.getServer().getRecipeManager().getCraftingRecipesBatch().getPayload() ) );
        this.connection.send( batch );

        // Send commands
        PacketAvailableCommands packetAvailableCommands = this.connection.getServer().
                getPluginManager().getCommandManager().createPacket( this );
        this.connection.send( packetAvailableCommands );
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
        if ( eyePosition.distanceSquared( position ) > Numbers.square( maxDistance ) ) {
            return false;
        }

        // Direction
        Vector playerPosition = this.getPosition();
        Vector2 directionPlane = this.getDirectionPlane();
        float dot = directionPlane.dot( new Vector2( eyePosition.getX(), eyePosition.getZ() ) );
        float dot1 = directionPlane.dot( new Vector2( playerPosition.getX(), playerPosition.getZ() ) );
        return ( dot1 - dot ) >= -0.5f;
    }

    /**
     * Queue which holds chunks to be sent to the client
     *
     * @return queue with chunks to be sent to the client
     */
    public Queue<ChunkAdapter> getChunkSendQueue() {
        return this.chunkSendQueue;
    }

    /**
     * Remove player from PlayerList and remove from global inventories etc.
     */
    public void cleanup() {
        this.connection.getServer().getCreativeInventory().removeViewer( this );

        Block block = this.world.getBlockAt( this.getPosition().toBlockPosition() );
        block.gotOff( this );
    }

    /**
     * Get the window id of specified inventory
     *
     * @param inventory
     * @return
     */
    public byte getWindowId( ContainerInventory inventory ) {
        return this.containerIds.getByte( inventory );
    }

    public void closeInventory( byte windowId ) {
        ContainerInventory containerInventory = this.windowIds.remove( windowId );
        if ( containerInventory != null ) {
            containerInventory.removeViewer( this );
            this.containerIds.remove( containerInventory, windowId );
        }
    }

    public ContainerInventory getContainerId( byte windowId ) {
        return this.windowIds.get( windowId );
    }

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
        }

        this.connection.addToSendQueue( packetText );
    }

    @Override
    public boolean hasPermission( String permission ) {
        return true;
    }

}
