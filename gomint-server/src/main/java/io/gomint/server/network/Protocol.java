/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
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
import io.gomint.server.network.packet.PacketClientCacheBlobStatus;
import io.gomint.server.network.packet.PacketClientCacheStatus;
import io.gomint.server.network.packet.PacketCommandRequest;
import io.gomint.server.network.packet.PacketContainerClose;
import io.gomint.server.network.packet.PacketCraftingEvent;
import io.gomint.server.network.packet.PacketDisconnect;
import io.gomint.server.network.packet.PacketEmoteList;
import io.gomint.server.network.packet.PacketEncryptionResponse;
import io.gomint.server.network.packet.PacketEntityEvent;
import io.gomint.server.network.packet.PacketEntityFall;
import io.gomint.server.network.packet.PacketEntityMetadata;
import io.gomint.server.network.packet.PacketHotbar;
import io.gomint.server.network.packet.PacketInteract;
import io.gomint.server.network.packet.PacketInventoryTransaction;
import io.gomint.server.network.packet.PacketItemStackRequest;
import io.gomint.server.network.packet.PacketLogin;
import io.gomint.server.network.packet.PacketMobEquipment;
import io.gomint.server.network.packet.PacketModalResponse;
import io.gomint.server.network.packet.PacketMovePlayer;
import io.gomint.server.network.packet.PacketPlayState;
import io.gomint.server.network.packet.PacketPlayerAction;
import io.gomint.server.network.packet.PacketRequestChunkRadius;
import io.gomint.server.network.packet.PacketResourcePackResponse;
import io.gomint.server.network.packet.PacketResourcePacksInfo;
import io.gomint.server.network.packet.PacketRespawnPosition;
import io.gomint.server.network.packet.PacketServerSettingsRequest;
import io.gomint.server.network.packet.PacketConfirmChunkRadius;
import io.gomint.server.network.packet.PacketSetLocalPlayerAsInitialized;
import io.gomint.server.network.packet.PacketSkipable;
import io.gomint.server.network.packet.PacketText;
import io.gomint.server.network.packet.PacketTickSync;
import io.gomint.server.network.packet.PacketTileEntityData;
import io.gomint.server.network.packet.PacketViolationWarning;
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
    public static final int MINECRAFT_PE_PROTOCOL_VERSION = 419;
    public static final String MINECRAFT_PE_NETWORK_VERSION = "1.16.100";

    // ========================================= PACKET IDS ========================================= //
    public static final byte BATCH_MAGIC = (byte) 0xfe;
    public static final int PACKET_LOGIN = 0x01;
    public static final int PACKET_PLAY_STATE = 0x02;
    public static final int PACKET_ENCRYPTION_REQUEST = 0x03;
    public static final int PACKET_ENCRYPTION_RESPONSE = 0x04;
    public static final int PACKET_DISCONNECT = 0x05;
    public static final int PACKET_RESOURCEPACK_INFO = 0x06;
    public static final int PACKET_RESOURCEPACK_STACK = 0x07;
    public static final int PACKET_RESOURCEPACK_RESPONSE = 0x08;
    public static final int PACKET_TEXT = 0x09;
    public static final int PACKET_WORLD_TIME = 0x0a;
    public static final int PACKET_START_GAME = 0x0b;
    public static final int PACKET_SPAWN_PLAYER = 0x0c;
    public static final int PACKET_SPAWN_ENTITY = 0x0d;
    public static final int PACKET_DESPAWN_ENTITY = 0x0e;
    public static final int PACKET_ADD_ITEM_ENTITY = 0x0f;
    public static final int PACKET_PICKUP_ITEM_ENTITY = 0x11;
    public static final int PACKET_ENTITY_MOVEMENT = 0x12;
    public static final int PACKET_MOVE_PLAYER = 0x13;
    public static final int PACKET_RIDER_JUMP = 0x14;
    public static final int PACKET_UPDATE_BLOCK = 0x15;
    public static final int PACKET_ADD_PAINTING = 0x16;
    public static final int PACKET_TICK_SYNC = 0x17;
    public static final int PACKET_WORLD_SOUND_EVENT_V1 = 0x18;
    public static final int PACKET_WORLD_EVENT = 0x19;
    public static final int PACKET_BLOCK_EVENT = 0x1A;
    public static final int PACKET_ENTITY_EVENT = 0x1B;
    public static final int PACKET_MOB_EFFECT = 0x1C;
    public static final int PACKET_UPDATE_ATTRIBUTES = 0x1D;
    public static final int PACKET_INVENTORY_TRANSACTION = 0x1E;
    public static final int PACKET_MOB_EQUIPMENT = 0x1F;
    public static final int PACKET_MOB_ARMOR_EQUIPMENT = 0x20;
    public static final int PACKET_INTERACT = 0x21;
    public static final int PACKET_BLOCK_PICK_REQUEST = 0x22;
    public static final int PACKET_ENTITY_PICK_REQUEST = 0x23;
    public static final int PACKET_PLAYER_ACTION = 0x24;
    public static final int PACKET_ENTITY_FALL = 0x25;
    public static final int PACKET_HURT_ARMOR = 0x26;
    public static final int PACKET_ENTITY_METADATA = 0x27;
    public static final int PACKET_ENTITY_MOTION = 0x28;
    public static final int PACKET_SET_ENTITY_LINK = 0x29;
    public static final int PACKET_SET_HEALTH = 0x2A;
    public static final int PACKET_SET_SPAWN_POSITION = 0x2B;
    public static final int PACKET_ANIMATE = 0x2C;
    public static final int PACKET_RESPAWN_POSITION = 0x2D;
    public static final int PACKET_CONTAINER_OPEN = 0x2E;
    public static final int PACKET_CONTAINER_CLOSE = 0x2F;
    public static final int PACKET_HOTBAR = 0x30;
    public static final int PACKET_INVENTORY_CONTENT_PACKET = 0x31;
    public static final int PACKET_INVENTORY_SET_SLOT = 0x32;
    public static final int PACKET_SET_CONTAINER_DATA = 0x33;
    public static final int PACKET_CRAFTING_RECIPES = 0x34;
    public static final int PACKET_CRAFTING_EVENT = 0x35;
    public static final int PACKET_GUI_DATA_PICK_ITEM = 0x36;
    public static final int PACKET_ADVENTURE_SETTINGS = 0x37;
    public static final int PACKET_TILE_ENTITY_DATA = 0x38;
    public static final int PACKET_PLAYER_INPUT = 0x39;
    public static final int PACKET_WORLD_CHUNK = 0x3A;
    public static final int PACKET_SET_COMMANDS_ENABLED = 0x3B;
    public static final int PACKET_SET_DIFFICULTY = 0x3C;
    public static final int PACKET_CHANGE_DIMENSION = 0x3D;
    public static final int PACKET_SET_GAMEMODE = 0x3E;
    public static final int PACKET_PLAYER_LIST = 0x3F;
    public static final int PACKET_SIMPLE_EVENT = 0x40;
    public static final int PACKET_EVENT = 0x41;
    public static final int PACKET_SPAWN_EXPERIENCE_ORB = 0x42;
    public static final int PACKET_CLIENTBOUND_MAP_ITEM_DATA = 0x43;
    public static final int PACKET_MAP_INFO_REQUEST = 0x44;
    public static final int PACKET_REQUEST_CHUNK_RADIUS = 0x45;
    public static final int PACKET_CONFIRM_CHUNK_RADIUS = 0x46;
    public static final int PACKET_ITEM_FRAME_DROP_ITEM = 0x47;
    public static final int PACKET_GAME_RULES_CHANGED = 0x48;
    public static final int PACKET_CAMERA = 0x49;
    public static final int PACKET_BOSS_BAR = 0x4a;
    public static final int PACKET_SHOW_CREDITS = 0x4b;
    public static final int PACKET_AVAILABLE_COMMANDS = 0x4c;
    public static final int PACKET_COMMAND_REQUEST = 0x4d;
    public static final int PACKET_COMMAND_BLOCK_UPDATE = 0x4e;
    public static final int PACKET_COMMAND_OUTPUT = 0x4f;
    public static final int PACKET_UPDATE_TRADE = 0x50;
    public static final int PACKET_UPDATE_EQUIPMENT = 0x51;
    public static final int PACKET_RESOURCE_PACK_DATA_INFO = 0x52;
    public static final int PACKET_RESOURCE_PACK_CHUNK_DATA = 0x53;
    public static final int PACKET_RESOURCE_PACK_CHUNK_REQUEST = 0x54;
    public static final int PACKET_TRANSFER = 0x55;
    public static final int PACKET_PLAY_SOUND = 0x56;
    public static final int PACKET_STOP_SOUND = 0x57;
    public static final int PACKET_SET_TITLE = 0x58;
    public static final int PACKET_ADD_BEHAVIOR_TREE = 0x59;
    public static final int PACKET_STRUCTURE_BLOCK_UPDATE = 0x5a;
    public static final int PACKET_SHOW_STORE_OFFER = 0x5b;
    public static final int PACKET_PURCHASE_RECEIPT = 0x5c;
    public static final int PACKET_PLAYER_SKIN = 0x5d;
    public static final int PACKET_SUB_CLIENT_LOGIN = 0x5e;
    public static final int PACKET_AUTOMATION_CLIENT_CONNECT = 0x5f;
    public static final int PACKET_SET_LAST_HURT_BY = 0x60;
    public static final int PACKET_BOOK_EDIT = 0x61;
    public static final int PACKET_NPC_REQUEST = 0x62;
    public static final int PACKET_PHOTO_TRANSFER = 0x63;
    public static final int PACKET_MODAL_REQUEST = 0x64;
    public static final int PACKET_MODAL_RESPONSE = 0x65;
    public static final int PACKET_SERVER_SETTINGS_REQUEST = 0x66;
    public static final int PACKET_SERVER_SETTINGS_RESPONSE = 0x67;
    public static final int PACKET_SHOW_PROFILE = 0x68;
    public static final int PACKET_SET_DEFAULT_GAME_TYPE = 0x69;
    public static final int PACKET_REMOVE_OBJECTIVE = 0x6a;
    public static final int PACKET_SET_OBJECTIVE = 0x6b;
    public static final int PACKET_SET_SCORE = 0x6c;
    public static final int PACKET_LAB_TABLE = 0x6d;
    public static final int PACKET_UPDATE_BLOCK_SYNCHED = 0x6e;
    public static final int PACKET_ENTITY_RELATIVE_MOVEMENT = 0x6f;
    public static final int PACKET_SET_SCOREBOARD_IDENTITY = 0x70;
    public static final int PACKET_SET_LOCAL_PLAYER_INITIALIZED = 0x71;
    public static final int PACKET_UPDATE_SOFT_ENUM = 0x72;
    public static final int PACKET_NETWORK_STACK_LATENCY = 0x73;
    public static final int PACKET_SCRIPT_CUSTOM_EVENT = 0x75;
    public static final int PACKET_SPAWN_PARTICLE_EFFECT = 0x76;
    public static final int PACKET_AVAILABLE_ENTITY_IDENTIFIERS = 0x77;
    public static final int PACKET_WORLD_SOUND_EVENT_V2 = 0x78;
    public static final int PACKET_NETWORK_CHUNK_PUBLISHER_UPDATE = 0x79;
    public static final int PACKET_BIOME_DEFINITION_LIST = 0x7a;
    public static final int PACKET_WORLD_SOUND_EVENT = 0x7b;
    public static final int PACKET_WORLD_EVENT_GENERIC = 0x7c;
    public static final int PACKET_LECTERN_UPDATE = 0x7d;
    public static final int PACKET_VIDEO_STREAM_CONNECT = 0x7e;
    public static final int PACKET_ADD_ENTITY = 0x7f;
    public static final int PACKET_REMOVE_ENTITY = 0x80;
    public static final int PACKET_CLIENT_CACHE_STATUS = 0x81;
    public static final int PACKET_ON_SCREEN_TEXTURE_ANIMATION = 0x82;
    public static final int PACKET_MAP_CREATE_LOCKED_COPY = 0x83;
    public static final int PACKET_STRUCTURE_TEMPLATE_DATA_REQUEST = 0x84;
    public static final int PACKET_STRUCTURE_TEMPLATE_DATA_RESPONSE = 0x85;
    public static final int PACKET_UPDATE_BLOCK_PROPERTIES = 0x86;
    public static final int PACKET_CLIENT_CACHE_BLOB_STATUS = 0x87;
    public static final int PACKET_CLIENT_CACHE_MISS_RESPONSE = 0x88;
    public static final int PACKET_EDUCATION_SETTINGS = 0x89;
    public static final int PACKET_EMOTE = 0x8a;
    public static final int PACKET_MULTIPLAYER_SETTINGS = 0x8b;
    public static final int PACKET_SETTINGS_COMMAND = 0x8c;
    public static final int PACKET_ANVIL_DAMAGE = 0x8d;
    public static final int PACKET_COMPLETED_USING_ITEM = 0x8e;
    public static final int PACKET_NETWORK_SETTINGS = 0x8f;
    public static final int PACKET_PLAYER_AUTH_INPUT = 0x90;
    public static final int PACKET_CREATIVE_CONTENT = 0x91;
    public static final int PACKET_PLAYER_ENCHANT_OPTIONS =  0x92;
    public static final int PACKET_ITEM_STACK_REQUEST =  0x93;
    public static final int PACKET_ITEM_STACK_RESPONSE =  0x94;
    public static final int PACKET_PLAYER_ARMOR_DAMAGE =  0x95;
    public static final int PACKET_CODE_BUILDER =  0x96;
    public static final int PACKET_UPDATE_PLAYER_GAME_TYPE =  0x97;
    public static final int PACKET_EMOTE_LIST =  0x98;
    public static final int PACKET_POS_TRACKING_SERVER_BROADCAST =  0x99;
    public static final int PACKET_POS_TRACKING_CLIENT_REQUEST =  0x9a;
    public static final int PACKET_DEBUG_INFO =  0x9b;
    public static final int PACKET_VIOLATION_WARNING =  0x9c;
    public static final int PACKET_ITEM_COMPONENT = 0xA2;
    // CHECKSTYLE:ON

    // ========================================= PACKET METHODS ========================================= //

    private Protocol() {
        throw new AssertionError("Cannot instantiate Protocol!");
    }

    /**
     * Creates a new packet instance given the packet ID found inside the first int of any
     * packet's data.
     *
     * @param id The ID of the the packet to create
     * @return The created packet or null if it could not be created
     */
    public static Packet createPacket(int id) {
        switch (id) {
            case PACKET_ITEM_STACK_REQUEST:
                return new PacketItemStackRequest();

            case PACKET_EMOTE_LIST:
                return new PacketEmoteList();

            case PACKET_CLIENT_CACHE_BLOB_STATUS:
                return new PacketClientCacheBlobStatus();

            case PACKET_VIOLATION_WARNING:
                return new PacketViolationWarning();

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
                return new PacketSkipable(); // new PacketCraftingEvent();

            case PACKET_ADVENTURE_SETTINGS:
                return new PacketAdventureSettings();

            case PACKET_INTERACT:
                return new PacketInteract();

            case PACKET_BLOCK_PICK_REQUEST:
                return new PacketBlockPickRequest();

            case PACKET_ENTITY_METADATA:
                return new PacketEntityMetadata();

            case PACKET_CONFIRM_CHUNK_RADIUS:
                return new PacketConfirmChunkRadius();

            case PACKET_CLIENT_CACHE_STATUS:
                return new PacketClientCacheStatus();

            case PACKET_TICK_SYNC:
                return new PacketTickSync();

            case PACKET_REQUEST_CHUNK_RADIUS:
                return new PacketRequestChunkRadius();

            case PACKET_RESPAWN_POSITION:
                return new PacketRespawnPosition();

            default:
                // LOGGER.warn( "Unknown client side packetId: {}", Integer.toHexString( id & 0xFF ) );
                return null;
        }
    }

}
