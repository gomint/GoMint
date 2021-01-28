/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.entity.metadata;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.math.Vector;
import io.gomint.server.entity.EntityFlag;
import io.gomint.server.inventory.item.ItemStack;
import io.gomint.taglib.NBTTagCompound;
import it.unimi.dsi.fastutil.bytes.Byte2ObjectMap;
import it.unimi.dsi.fastutil.bytes.Byte2ObjectOpenHashMap;

import java.util.Observable;

/**
 * @author BlackyPaw
 * @author geNAZt
 * @version 2.0
 */
public class MetadataContainer extends Observable {

    /**
     * Internal byte representation for a byte meta
     */
    static final byte METADATA_BYTE = 0;

    /**
     * Internal byte representation for a short meta
     */
    static final byte METADATA_SHORT = 1;

    /**
     * Internal byte representation for a int meta
     */
    static final byte METADATA_INT = 2;

    /**
     * Internal byte representation for a float meta
     */
    static final byte METADATA_FLOAT = 3;

    /**
     * Internal byte representation for a string meta
     */
    static final byte METADATA_STRING = 4;

    /**
     * Internal byte representation for a NBTTagCompound meta
     */
    static final byte METADATA_NBT = 5;

    /**
     * Internal byte representation for a position meta
     */
    static final byte METADATA_POSITION = 6;

    /**
     * Internal byte representation for a long meta
     */
    static final byte METADATA_LONG = 7;

    /**
     * Internal byte representation for a vector meta
     */
    static final byte METADATA_VECTOR = 8;

    public static final int DATA_INDEX = 0;
    public static final int DATA_HEALTH = 1; //int (minecart/boat)
    public static final int DATA_VARIANT = 2; //int
    public static final int DATA_COLOR = 3, DATA_COLOUR = 3; //byte
    public static final int DATA_NAMETAG = 4; //string
    public static final int DATA_OWNER_EID = 5; //long
    public static final int DATA_TARGET_EID = 6; //long
    public static final int DATA_AIR = 7; //short
    public static final int DATA_POTION_COLOR = 8; //int (ARGB!)
    public static final int DATA_POTION_AMBIENT = 9; //byte
    public static final int DATA_JUMP_DURATION = 10; //long
    public static final int DATA_HURT_TIME = 11; //int (minecart/boat)
    public static final int DATA_HURT_DIRECTION = 12; //int (minecart/boat)
    public static final int DATA_PADDLE_TIME_LEFT = 13; //float
    public static final int DATA_PADDLE_TIME_RIGHT = 14; //float
    public static final int DATA_EXPERIENCE_VALUE = 15; //int (xp orb)
    public static final int DATA_MINECART_DISPLAY_BLOCK = 16; //int (id | (data << 16))
    public static final int DATA_MINECART_DISPLAY_OFFSET = 17; //int
    public static final int DATA_MINECART_HAS_DISPLAY = 18; //byte (must be 1 for minecart to show block inside)
    public static final int DATA_OLD_SWELL = 20;
    public static final int DATA_SWELL_DIR = 21;
    public static final int DATA_CHARGE_AMOUNT = 22;
    public static final int DATA_ENDERMAN_HELD_RUNTIME_ID = 23; //short
    public static final int DATA_ENTITY_AGE = 24; //short
    public static final int DATA_PLAYER_FLAGS = 26;
    public static final int DATA_PLAYER_INDEX = 27;
    public static final int DATA_PLAYER_BED_POSITION = 28; //block coords
    public static final int DATA_FIREBALL_POWER_X = 29; //float
    public static final int DATA_FIREBALL_POWER_Y = 30;
    public static final int DATA_FIREBALL_POWER_Z = 31;
    public static final int DATA_AUX_POWER = 32;
    public static final int DATA_FISH_X = 33;
    public static final int DATA_FISH_Z = 34;
    public static final int DATA_FISH_ANGLE = 35;
    public static final int DATA_POTION_AUX_VALUE = 36; //short
    public static final int DATA_LEAD_HOLDER_EID = 37; //long
    public static final int DATA_SCALE = 38;
    public static final int DATA_INTERACTIVE_TAG = 39; //string
    public static final int DATA_NPC_SKIN_ID = 40; //string
    public static final int DATA_URL_TAG = 41; //string
    public static final int DATA_MAX_AIRDATA_MAX_AIR = 42;
    public static final int DATA_MARK_VARIANT = 43; //int
    public static final int DATA_CONTAINER_TYPE = 44; //byte
    public static final int DATA_CONTAINER_BASE_SIZE = 45; //int
    public static final int DATA_CONTAINER_EXTRA_SLOTS_PER_STRENGTH = 46; //int
    public static final int DATA_BLOCK_TARGET = 47;
    public static final int DATA_WITHER_INVULNERABLE_TICKS = 48; //int
    public static final int DATA_WITHER_TARGET_1 = 49; //long
    public static final int DATA_WITHER_TARGET_2 = 50; //long
    public static final int DATA_WITHER_TARGET_3 = 51; //long
    public static final int DATA_AERIAL_ATTACK = 52;
    public static final int DATA_BOUNDINGBOX_WIDTH = 53;
    public static final int DATA_BOUNDINGBOX_HEIGHT = 54;
    public static final int DATA_FUSE_LENGTH = 55;
    public static final int DATA_RIDER_SEAT_POSITION = 56; //vector3f
    public static final int DATA_RIDER_ROTATION_LOCKED = 57; //byte
    public static final int DATA_RIDER_MAX_ROTATION = 58; //float
    public static final int DATA_RIDER_MIN_ROTATION = 59; //float
    public static final int DATA_AREA_EFFECT_CLOUD_RADIUS = 60; //float
    public static final int DATA_AREA_EFFECT_CLOUD_WAITING = 61; //int
    public static final int DATA_AREA_EFFECT_CLOUD_PARTICLE_ID = 62; //int
    public static final int DATA_SHULKER_PEEK_ID = 63; //int
    public static final int DATA_SHULKER_ATTACH_FACE = 64; //byte
    public static final int DATA_SHULKER_ATTACHED = 65; //short
    public static final int DATA_SHULKER_ATTACH_POS = 66;
    public static final int DATA_TRADING_PLAYER_EID = 67; //long
    public static final int DATA_TRADING_CAREER = 68;
    public static final int DATA_HAS_COMMAND_BLOCK = 69;
    public static final int DATA_COMMAND_BLOCK_COMMAND = 70; //string
    public static final int DATA_COMMAND_BLOCK_LAST_OUTPUT = 71; //string
    public static final int DATA_COMMAND_BLOCK_TRACK_OUTPUT = 72; //byte
    public static final int DATA_CONTROLLING_RIDER_SEAT_NUMBER = 73; //byte
    public static final int DATA_STRENGTH = 74; //int
    public static final int DATA_MAX_STRENGTH = 75; //int
    public static final int DATA_SPELL_CASTING_COLOR = 76; //int
    public static final int DATA_LIMITED_LIFE = 77;
    public static final int DATA_ARMOR_STAND_POSE_INDEX = 78; // int
    public static final int DATA_ENDER_CRYSTAL_TIME_OFFSET = 79; // int
    public static final int DATA_ALWAYS_SHOW_NAMETAG = 80; // byte
    public static final int DATA_COLOR_2 = 81; // byte
    public static final int DATA_NAME_AUTHOR = 82;
    public static final int DATA_SCORE_TAG = 83; //String
    public static final int DATA_BALLOON_ATTACHED_ENTITY = 84; // long
    public static final int DATA_PUFFERFISH_SIZE = 85;
    public static final int DATA_BUBBLE_TIME = 86;
    public static final int DATA_AGENT = 87;
    public static final int DATA_SITTING_AMOUNT = 88;
    public static final int DATA_SITTING_AMOUNT_PREVIOUS = 89;
    public static final int DATA_EATING_COUNTER = 90;
    public static final int DATA_FLAGS_EXTENDED = 91;
    public static final int DATA_LAYING_AMOUNT = 92;
    public static final int DATA_LAYING_AMOUNT_PREVIOUS = 93;
    public static final int DATA_DURATION = 94;
    public static final int DATA_SPAWN_TIME = 95;
    public static final int DATA_CHANGE_RATE = 96;
    public static final int DATA_CHANGE_ON_PICKUP = 97;
    public static final int DATA_PICKUP_COUNT = 98;
    public static final int DATA_INTERACT_TEXT = 99;
    public static final int DATA_TRADE_TIER = 100;
    public static final int DATA_MAX_TRADE_TIER = 101;
    public static final int DATA_TRADE_EXPERIENCE = 102;
    public static final int DATA_SKIN_ID = 103;
    public static final int DATA_SPAWNING_FRAMES = 104;
    public static final int DATA_COMMAND_BLOCK_TICK_DELAY = 105;
    public static final int DATA_COMMAND_BLOCK_EXECUTE_ON_FIRST_TICK = 106;
    public static final int DATA_AMBIENT_SOUND_INTERVAL = 107;
    public static final int DATA_AMBIENT_SOUND_INTERVAL_RANGE = 108;
    public static final int DATA_AMBIENT_SOUND_EVENT_NAME = 109;
    public static final int DATA_FALL_DAMAGE_MULTIPLIER = 110;
    public static final int DATA_NAME_RAW_TEXT = 111;
    public static final int DATA_CAN_RIDE_TARGET = 112;
    public static final int DATA_LOW_TIER_CURED_DISCOUNT = 113;
    public static final int DATA_HIGH_TIER_CURED_DISCOUNT = 114;
    public static final int DATA_NEARBY_CURED_DISCOUNT = 115;
    public static final int DATA_NEARBY_CURED_DISCOUNT_TIMESTAMP = 116;
    public static final int DATA_HITBOX = 117;
    public static final int DATA_IS_BUOYANT = 118;
    public static final int DATA_BUOYANCY_DATA = 119;

    private Byte2ObjectMap<MetadataValue> entries;
    private boolean dirty;

    /**
     * Constructs a new, empty metadata container.
     */
    public MetadataContainer() {
        this( 8 );
    }

    /**
     * Constructs a new, empty metadata container which may pre-allocate
     * enough internal capacities to hold at least capacity entries.
     *
     * @param capacity The capacity to pre-allocate
     */
    public MetadataContainer( int capacity ) {
        this.entries = new Byte2ObjectOpenHashMap<>( ( capacity > 32 ? 32 : capacity ) );
    }

    /**
     * Get the flag stored under the given index and flag id
     *
     * @param indexId The index of the value
     * @param flagId  The flag id used to encrypt the boolean
     * @return true when the flag has been set, false when not
     */
    public boolean getDataFlag( int indexId, EntityFlag flagId ) {
        return ( indexId == DATA_PLAYER_FLAGS ? this.getByte( indexId ) & 0xff : this.getLong( indexId ) & ( 1L << flagId.getId() ) ) > 0;
    }

    /**
     * Set the flag to the index given
     *
     * @param indexId The index of the value
     * @param flagId  The flag id used to encrypt the boolean
     * @param value   The boolean to encrypt
     */
    public void setDataFlag( int indexId, EntityFlag flagId, boolean value ) {
        if ( this.getDataFlag( indexId, flagId ) != value ) {
            if ( indexId == DATA_PLAYER_FLAGS ) {
                byte flags = this.getByte( indexId );
                flags ^= 1 << flagId.getId();
                this.putByte( indexId, flags );
            } else {
                long flags = this.getLong( indexId );
                flags ^= 1L << flagId.getId();
                this.putLong( indexId, flags );
            }
        }
    }

    /**
     * Puts the specified metadata value into the container.
     *
     * @param index The index to put the value into
     * @param value The value to put into the container
     */
    public void put( int index, MetadataValue value ) {
        this.entries.put( (byte) index, value );
        this.dirty = true;

        // Trigger observers
        this.setChanged();
        this.notifyObservers( index );
    }

    /**
     * Checks whether or not the container holds a value at the specified index.
     *
     * @param index The index of the value
     * @return Whether or not the container holds a value at the specified index
     */
    public boolean has( int index ) {
        return this.entries.containsKey( (byte) index );
    }

    /**
     * Gets the metadata value stored at the specified index.
     *
     * @param index The index where the value is stored (must be in the range of 0-31)
     * @return The value if found or null otherwise
     */
    public MetadataValue get( int index ) {
        return this.entries.get( (byte) index );
    }

    /**
     * Puts a boolean value which will be converted into a byte value internally into the container.
     *
     * @param index The index to put the value into
     * @param value The value to put into the container
     */
    public void putBoolean( int index, boolean value ) {
        this.putByte( index, (byte) ( value ? 1 : 0 ) );
    }

    /**
     * Gets a boolean stored inside the specified index.
     *
     * @param index The index of the value
     * @return The value stored at the specified index
     * @throws IllegalArgumentException Thrown in case no value is stored at the specified index or the value is not a boolean
     */
    public boolean getBoolean( int index ) {
        return ( this.getByte( index ) != 0 );
    }

    /**
     * Puts a byte value into the container.
     *
     * @param index The index to put the value into
     * @param value The value to put into the container
     */
    public void putByte( int index, byte value ) {
        this.put( index, new MetadataByte( value ) );
    }

    /**
     * Gets a byte stored inside the specified index.
     *
     * @param index The index of the value
     * @return The value stored at the specified index
     * @throws IllegalArgumentException Thrown in case no value is stored at the specified index or the value is not a byte
     */
    public byte getByte( int index ) {
        MetadataValue value = this.get( index );
        if ( value == null ) {
            throw new IllegalArgumentException( "No value stored at index " + index );
        }

        if ( value.getTypeId() != METADATA_BYTE ) {
            throw new IllegalArgumentException( "Value of different type stored at index " + index );
        }

        return ( (MetadataByte) value ).getValue();
    }

    /**
     * Puts a short value into the container.
     *
     * @param index The index to put the value into
     * @param value The value to put into the container
     */
    public void putShort( int index, short value ) {
        this.put( index, new MetadataShort( value ) );
    }

    /**
     * Gets a short stored inside the specified index.
     *
     * @param index The index of the value
     * @return The value stored at the specified index
     * @throws IllegalArgumentException Thrown in case no value is stored at the specified index or the value is not a short
     */
    public short getShort( int index ) {
        MetadataValue value = this.get( index );
        if ( value == null ) {
            throw new IllegalArgumentException( "No value stored at index " + index );
        }

        if ( value.getTypeId() != METADATA_SHORT ) {
            throw new IllegalArgumentException( "Value of different type stored at index " + index );
        }

        return ( (MetadataShort) value ).getValue();
    }

    /**
     * Puts an int value into the container.
     *
     * @param index The index to put the value into
     * @param value The value to put into the container
     */
    public void putInt( int index, int value ) {
        this.put( index, new MetadataInt( value ) );
    }

    /**
     * Gets an int stored inside the specified index.
     *
     * @param index The index of the value
     * @return The value stored at the specified index
     * @throws IllegalArgumentException Thrown in case no value is stored at the specified index or the value is not an int
     */
    public int getInt( int index ) {
        MetadataValue value = this.get( index );
        if ( value == null ) {
            throw new IllegalArgumentException( "No value stored at index " + index );
        }

        if ( value.getTypeId() != METADATA_INT ) {
            throw new IllegalArgumentException( "Value of different type stored at index " + index );
        }

        return ( (MetadataInt) value ).getValue();
    }

    /**
     * Puts an float value into the container.
     *
     * @param index The index to put the value into
     * @param value The value to put into the container
     */
    public void putFloat( int index, float value ) {
        this.put( index, new MetadataFloat( value ) );
    }

    /**
     * Gets an float stored inside the specified index.
     *
     * @param index The index of the value
     * @return The value stored at the specified index
     * @throws IllegalArgumentException Thrown in case no value is stored at the specified index or the value is not an float
     */
    public float getFloat( int index ) {
        MetadataValue value = this.get( index );
        if ( value == null ) {
            throw new IllegalArgumentException( "No value stored at index " + index );
        }

        if ( value.getTypeId() != METADATA_FLOAT ) {
            throw new IllegalArgumentException( "Value of different type stored at index " + index );
        }

        return ( (MetadataFloat) value ).getValue();
    }

    /**
     * Puts a string value into the container.
     *
     * @param index The index to put the value into
     * @param value The value to put into the container
     */
    public void putString( int index, String value ) {
        this.put( index, new MetadataString( value ) );
    }

    /**
     * Gets a string stored inside the specified index.
     *
     * @param index The index of the value
     * @return The value stored at the specified index
     * @throws IllegalArgumentException Thrown in case no value is stored at the specified index or the value is not a string
     */
    public String getString( int index ) {
        MetadataValue value = this.get( index );
        if ( value == null ) {
            throw new IllegalArgumentException( "No value stored at index " + index );
        }

        if ( value.getTypeId() != METADATA_STRING ) {
            throw new IllegalArgumentException( "Value of different type stored at index " + index );
        }

        return ( (MetadataString) value ).getValue();
    }

    /**
     * Put a NBTTagCompound value into the container.
     *
     * @param index The index to put the value into
     * @param value The value to put into the container
     */
    public void putNBT( int index, NBTTagCompound value ) {
        this.put( index, new MetadataNBT( value ) );
    }

    /**
     * Gets a NBTTagCompound stored inside the specified index.
     *
     * @param index The index of the value
     * @return The value stored at the specified index
     * @throws IllegalArgumentException Thrown in case no value is stored at the specified index or the value is not a item
     */
    public NBTTagCompound getNBT( int index ) {
        MetadataValue value = this.get( index );
        if ( value == null ) {
            throw new IllegalArgumentException( "No value stored at index " + index );
        }

        if ( value.getTypeId() != METADATA_NBT) {
            throw new IllegalArgumentException( "Value of different type stored at index " + index );
        }

        return ( (MetadataNBT) value ).getValue();
    }

    /**
     * Puts an position value into the container.
     *
     * @param index The index to put the value into
     * @param x     The x-value of the position to put into the container
     * @param y     The y-value of the position to put into the container
     * @param z     The z-value of the position to put into the container
     */
    public void putPosition( int index, int x, int y, int z ) {
        this.put( index, new MetadataPosition( x, y, z ) );
    }

    /**
     * Gets an position stored inside the specified index.
     *
     * @param index The index of the value
     * @return The value stored at the specified index
     * @throws IllegalArgumentException Thrown in case no value is stored at the specified index or the value is not an position
     */
    public Vector getPosition( int index ) {
        MetadataValue value = this.get( index );
        if ( value == null ) {
            throw new IllegalArgumentException( "No value stored at index " + index );
        }

        if ( value.getTypeId() != METADATA_POSITION ) {
            throw new IllegalArgumentException( "Value of different type stored at index " + index );
        }

        MetadataPosition position = (MetadataPosition) value;
        return new Vector( position.getX(), position.getY(), position.getZ() );
    }

    /**
     * Puts an long value into the container.
     *
     * @param index The index to put the value into
     * @param value The value to put into the container
     */
    public void putLong( int index, long value ) {
        this.put( index, new MetadataLong( value ) );
    }

    /**
     * Gets an long stored inside the specified index.
     *
     * @param index The index of the value
     * @return The value stored at the specified index
     * @throws IllegalArgumentException Thrown in case no value is stored at the specified index or the value is not an long
     */
    public long getLong( int index ) {
        MetadataValue value = this.get( index );
        if ( value == null ) {
            throw new IllegalArgumentException( "No value stored at index " + index );
        }

        if ( value.getTypeId() != METADATA_LONG ) {
            throw new IllegalArgumentException( "Value of different type stored at index " + index );
        }

        return ( (MetadataLong) value ).getValue();
    }

    /**
     * Puts an vector value into the container.
     *
     * @param index The index to put the value into
     * @param value The value to put into the container
     */
    public void putVector( int index, Vector value ) {
        this.put( index, new MetadataVector( value ) );
    }

    /**
     * Gets an vector stored inside the specified index.
     *
     * @param index The index of the value
     * @return The value stored at the specified index
     * @throws IllegalArgumentException Thrown in case no value is stored at the specified index or the value is not an vector
     */
    public Vector getVector( int index ) {
        MetadataValue value = this.get( index );
        if ( value == null ) {
            throw new IllegalArgumentException( "No value stored at index " + index );
        }

        if ( value.getTypeId() != METADATA_VECTOR ) {
            throw new IllegalArgumentException( "Value of different type stored at index " + index );
        }

        return ( (MetadataVector) value ).getValue();
    }

    /**
     * Serializes this metadata container into the specified buffer.
     *
     * @param buffer The buffer to serialize this metadata container into
     */
    public void serialize( PacketBuffer buffer ) {
        buffer.writeUnsignedVarInt( this.entries.size() );
        for ( Byte2ObjectMap.Entry<MetadataValue> entry : this.entries.byte2ObjectEntrySet() ) {
            entry.getValue().serialize( buffer, entry.getByteKey() );
        }
    }

    /**
     * Deserializes this metadata container from the specified buffer.
     *
     * @param buffer The buffer to deserialize this metadata container from
     * @return Whether or not the metadata container could be deserialized successfully
     */
    public boolean deserialize( PacketBuffer buffer ) {
        this.entries.clear();

        int size = buffer.readUnsignedVarInt();
        for ( int i = 0; i < size; i++ ) {
            int index = buffer.readUnsignedVarInt();
            int type = buffer.readUnsignedVarInt();

            MetadataValue value = null;
            switch ( type ) {
                case METADATA_BYTE:
                    value = new MetadataByte();
                    break;
                case METADATA_SHORT:
                    value = new MetadataShort();
                    break;
                case METADATA_INT:
                    value = new MetadataInt();
                    break;
                case METADATA_FLOAT:
                    value = new MetadataFloat();
                    break;
                case METADATA_STRING:
                    value = new MetadataString();
                    break;
                case METADATA_NBT:
                    value = new MetadataNBT();
                    break;
                case METADATA_POSITION:
                    value = new MetadataPosition();
                    break;
                case METADATA_LONG:
                    value = new MetadataLong();
                    break;
                case METADATA_VECTOR:
                    value = new MetadataVector();
                    break;
            }

            if ( value == null ) {
                return false;
            }

            value.deserialize( buffer );
            this.entries.put( (byte) index, value );
        }

        return true;
    }

    /**
     * Return true when the metadata changed. Also resets the dirty flag
     *
     * @return true when changed, false when not
     */
    public boolean isDirty() {
        boolean result = this.dirty;
        this.dirty = false;
        return result;
    }

    @Override
    public String toString() {
        return "MetadataContainer{" +
            "entries=" + this.entries +
            ", dirty=" + this.dirty +
            '}';
    }

}
