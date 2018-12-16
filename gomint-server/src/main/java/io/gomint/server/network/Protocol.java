/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.network;

import io.gomint.server.network.packet.Packet;
import io.gomint.server.network.packet.PacketAdventureSettings;
import io.gomint.server.network.packet.PacketAnimate;
import io.gomint.server.network.packet.PacketBatch;
import io.gomint.server.network.packet.PacketBlockPickRequest;
import io.gomint.server.network.packet.PacketBookEdit;
import io.gomint.server.network.packet.PacketBossBar;
import io.gomint.server.network.packet.PacketCommandRequest;
import io.gomint.server.network.packet.PacketContainerClose;
import io.gomint.server.network.packet.PacketCraftingEvent;
import io.gomint.server.network.packet.PacketDisconnect;
import io.gomint.server.network.packet.PacketEncryptionResponse;
import io.gomint.server.network.packet.PacketEntityEvent;
import io.gomint.server.network.packet.PacketEntityFall;
import io.gomint.server.network.packet.PacketEntityMetadata;
import io.gomint.server.network.packet.PacketHotbar;
import io.gomint.server.network.packet.PacketInteract;
import io.gomint.server.network.packet.PacketInventoryTransaction;
import io.gomint.server.network.packet.PacketLogin;
import io.gomint.server.network.packet.PacketMobEquipment;
import io.gomint.server.network.packet.PacketModalResponse;
import io.gomint.server.network.packet.PacketMovePlayer;
import io.gomint.server.network.packet.PacketPlayState;
import io.gomint.server.network.packet.PacketPlayerAction;
import io.gomint.server.network.packet.PacketResourcePackResponse;
import io.gomint.server.network.packet.PacketResourcePacksInfo;
import io.gomint.server.network.packet.PacketServerSettingsRequest;
import io.gomint.server.network.packet.PacketSetChunkRadius;
import io.gomint.server.network.packet.PacketSetLocalPlayerAsInitialized;
import io.gomint.server.network.packet.PacketText;
import io.gomint.server.network.packet.PacketTileEntityData;
import io.gomint.server.network.packet.PacketWorldSoundEvent;

/**
 * @author BlackyPaw
 * @version 1.0
 */
public final class Protocol {

    // CHECKSTYLE:OFF
    // MC:PE Protocol ID
    public static final int MINECRAFT_PE_BETA_PROTOCOL_VERSION = -1;
    public static final int MINECRAFT_PE_NEXT_STABLE_PROTOCOL_VERSION = -1;
    public static final int MINECRAFT_PE_PROTOCOL_VERSION = 313;
    public static final String MINECRAFT_PE_NETWORK_VERSION = "1.8.0";

    // ========================================= PACKET IDS ========================================= //
    public static final byte PACKET_BATCH = (byte) 0xfe;
    public static final byte PACKET_LOGIN = (byte) 0x01;
    public static final byte PACKET_PLAY_STATE = (byte) 0x02;
    public static final byte PACKET_ENCRYPTION_REQUEST = (byte) 0x03;
    public static final byte PACKET_ENCRYPTION_RESPONSE = (byte) 0x04;
    public static final byte PACKET_DISCONNECT = (byte) 0x05;
    public static final byte PACKET_RESOURCEPACK_INFO = (byte) 0x06;
    public static final byte PACKET_RESOURCEPACK_STACK = (byte) 0x07;
    public static final byte PACKET_RESOURCEPACK_RESPONSE = (byte) 0x08;
    public static final byte PACKET_TEXT = (byte) 0x09;
    public static final byte PACKET_WORLD_TIME = (byte) 0x0a;
    public static final byte PACKET_START_GAME = (byte) 0x0b;
    public static final byte PACKET_SPAWN_PLAYER = (byte) 0x0c;
    public static final byte PACKET_SPAWN_ENTITY = (byte) 0x0d;
    public static final byte PACKET_DESPAWN_ENTITY = (byte) 0x0e;
    public static final byte PACKET_ADD_ITEM_ENTITY = (byte) 0x0f;
    public static final byte PACKET_PICKUP_ITEM_ENTITY = (byte) 0x11;
    public static final byte PACKET_ENTITY_MOVEMENT = (byte) 0x12;
    public static final byte PACKET_MOVE_PLAYER = (byte) 0x13;
    public static final byte PACKET_UPDATE_BLOCK = (byte) 0x15;
    public static final byte PACKET_EXPLODE = (byte) 0x17;
    public static final byte PACKET_WORLD_SOUND_EVENT = (byte) 0x18;
    public static final byte PACKET_WORLD_EVENT = (byte) 0x19;
    public static final byte PACKET_BLOCK_EVENT = (byte) 0x1A;
    public static final byte PACKET_ENTITY_EVENT = (byte) 0x1B;
    public static final byte PACKET_MOB_EFFECT = (byte) 0x1C;
    public static final byte PACKET_UPDATE_ATTRIBUTES = (byte) 0x1D;
    public static final byte PACKET_INVENTORY_TRANSACTION = (byte) 0x1E;
    public static final byte PACKET_MOB_EQUIPMENT = (byte) 0x1F;
    public static final byte PACKET_MOB_ARMOR_EQUIPMENT = (byte) 0x20;
    public static final byte PACKET_INTERACT = (byte) 0x21;
    public static final byte PACKET_BLOCK_PICK_REQUEST = (byte) 0x22;
    public static final byte PACKET_PLAYER_ACTION = (byte) 0x24;
    public static final byte PACKET_ENTITY_FALL = (byte) 0x25;
    public static final byte PACKET_HURT_ARMOR = (byte) 0x26;
    public static final byte PACKET_ENTITY_METADATA = (byte) 0x27;
    public static final byte PACKET_ENTITY_MOTION = (byte) 0x28;
    public static final byte PACKET_SET_SPAWN_POSITION = (byte) 0x2B;
    public static final byte PACKET_ANIMATE = (byte) 0x2C;
    public static final byte PACKET_RESPAWN_POSITION = (byte) 0x2D;
    public static final byte PACKET_CONTAINER_OPEN = (byte) 0x2E;
    public static final byte PACKET_CONTAINER_CLOSE = (byte) 0x2F;
    public static final byte PACKET_HOTBAR = (byte) 0x30;
    public static final byte PACKET_INVENTORY_CONTENT_PACKET = (byte) 0x31;
    public static final byte PACKET_INVENTORY_SET_SLOT = (byte) 0x32;
    public static final byte PACKET_SET_CONTAINER_DATA = (byte) 0x33;
    public static final byte PACKET_CRAFTING_RECIPES = (byte) 0x34;
    public static final byte PACKET_CRAFTING_EVENT = (byte) 0x35;
    public static final byte PACKET_ADVENTURE_SETTINGS = (byte) 0x37;
    public static final byte PACKET_TILE_ENTITY_DATA = (byte) 0x38;
    public static final byte PACKET_WORLD_CHUNK = (byte) 0x3A;
    public static final byte PACKET_SET_COMMANDS_ENABLED = (byte) 0x3B;
    public static final byte PACKET_SET_DIFFICULTY = (byte) 0x3C;
    public static final byte PACKET_SET_GAMEMODE = (byte) 0x3E;
    public static final byte PACKET_PLAYER_LIST = (byte) 0x3F;
    public static final byte PACKET_SET_CHUNK_RADIUS = (byte) 0x45;
    public static final byte PACKET_CONFIRM_CHUNK_RADIUS = (byte) 0x46;
    public static final byte PACKET_BOSS_BAR = (byte) 0x4a;
    public static final byte PACKET_AVAILABLE_COMMANDS = (byte) 0x4c;
    public static final byte PACKET_COMMAND_REQUEST = (byte) 0x4d;
    public static final byte PACKET_COMMAND_OUTPUT = (byte) 0x4f;
    public static final byte PACKET_TRANSFER = (byte) 0x55;
    public static final byte PACKET_BOOK_EDIT = (byte) 0x61;
    public static final byte PACKET_MODAL_REQUEST = (byte) 0x64;
    public static final byte PACKET_MODAL_RESPONSE = (byte) 0x65;
    public static final byte PACKET_SERVER_SETTINGS_REQUEST = (byte) 0x66;
    public static final byte PACKET_SERVER_SETTINGS_RESPONSE = (byte) 0x67;
    public static final byte PACKET_REMOVE_OBJECTIVE = (byte) 0x6a;
    public static final byte PACKET_SET_OBJECTIVE = (byte) 0x6b;
    public static final byte PACKET_SET_SCORE = (byte) 0x6c;
    public static final byte PACKET_UPDATE_BLOCK_SYNCHED = (byte) 0x6e;
    public static final byte PACKET_ENTITY_RELATIVE_MOVEMENT = (byte) 0x6f;
    public static final byte PACKET_SET_SCOREBOARD_IDENTITY = (byte) 0x70;
    public static final byte PACKET_SET_LOCAL_PLAYER_INITIALIZED = (byte) 0x71;
    public static final byte PACKET_NETWORK_CHUNK_PUBLISHER_UPDATE = (byte) 0x79;
    public static final byte PACKET_SET_TITLE = (byte) 0x58;
    public static final byte PACKET_SET_COMPASS_TARGET = (byte) 0xB1;
    // CHECKSTYLE:ON

    // ========================================= PACKET METHODS ========================================= //

    private Protocol() {
        throw new AssertionError( "Cannot instantiate Protocol!" );
    }

    /**
     * Creates a new packet instance given the packet ID found inside the first byte of any
     * packet's data.
     *
     * @param id              The ID of the the packet to create
     * @return The created packet or null if it could not be created
     */
    public static Packet createPacket( byte id ) {
        switch ( id ) {
            case PACKET_TILE_ENTITY_DATA:
                return new PacketTileEntityData();

            case PACKET_SET_LOCAL_PLAYER_INITIALIZED:
                return new PacketSetLocalPlayerAsInitialized();

            case PACKET_BOOK_EDIT:
                return new PacketBookEdit();

            case PACKET_ENTITY_FALL:
                return new PacketEntityFall();

            case PACKET_BOSS_BAR:
                return new PacketBossBar();

            case PACKET_SERVER_SETTINGS_REQUEST:
                return new PacketServerSettingsRequest();

            case PACKET_MOB_EQUIPMENT:
                return new PacketMobEquipment();

            case PACKET_MODAL_RESPONSE:
                return new PacketModalResponse();

            case PACKET_ENTITY_EVENT:
                return new PacketEntityEvent();

            case PACKET_COMMAND_REQUEST:
                return new PacketCommandRequest();

            case PACKET_TEXT:
                return new PacketText();

            case PACKET_HOTBAR:
                return new PacketHotbar();

            case PACKET_LOGIN:
                return new PacketLogin();

            case PACKET_PLAY_STATE:
                return new PacketPlayState();

            case PACKET_ENCRYPTION_RESPONSE:
                return new PacketEncryptionResponse();

            case PACKET_DISCONNECT:
                return new PacketDisconnect();

            case PACKET_BATCH:
                return new PacketBatch();

            case PACKET_INVENTORY_TRANSACTION:
                return new PacketInventoryTransaction();

            case PACKET_RESOURCEPACK_INFO:
                return new PacketResourcePacksInfo();

            case PACKET_RESOURCEPACK_RESPONSE:
                return new PacketResourcePackResponse();

            case PACKET_WORLD_SOUND_EVENT:
                return new PacketWorldSoundEvent();

            case PACKET_MOVE_PLAYER:
                return new PacketMovePlayer();

            case PACKET_PLAYER_ACTION:
                return new PacketPlayerAction();

            case PACKET_ANIMATE:
                return new PacketAnimate();

            case PACKET_CONTAINER_CLOSE:
                return new PacketContainerClose();

            case PACKET_CRAFTING_EVENT:
                return new PacketCraftingEvent();

            case PACKET_ADVENTURE_SETTINGS:
                return new PacketAdventureSettings();

            case PACKET_INTERACT:
                return new PacketInteract();

            case PACKET_BLOCK_PICK_REQUEST:
                return new PacketBlockPickRequest();

            case PACKET_ENTITY_METADATA:
                return new PacketEntityMetadata();

            case PACKET_SET_CHUNK_RADIUS:
                return new PacketSetChunkRadius();

            default:
                // LOGGER.warn( "Unknown client side packetId: {}", Integer.toHexString( id & 0xFF ) );
                return null;
        }
    }

}
