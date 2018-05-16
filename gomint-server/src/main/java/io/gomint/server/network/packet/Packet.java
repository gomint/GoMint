/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.network.packet;

import io.gomint.GoMint;
import io.gomint.inventory.item.ItemAir;
import io.gomint.inventory.item.ItemStack;
import io.gomint.jraknet.PacketBuffer;
import io.gomint.math.BlockPosition;
import io.gomint.math.Vector;
import io.gomint.server.GoMintServer;
import io.gomint.server.entity.EntityLink;
import io.gomint.server.network.type.CommandOrigin;
import io.gomint.server.util.Things;
import io.gomint.taglib.NBTReader;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.world.Gamerule;
import io.gomint.world.block.BlockFace;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteOrder;
import java.util.List;
import java.util.Map;

/**
 * @author BlackyPaw
 * @version 1.0
 */
public abstract class Packet {

    /**
     * Internal MC:PE id of this packet
     */
    protected final byte id;

    /**
     * Construct a new packet
     *
     * @param id of the packet
     */
    protected Packet( byte id ) {
        this.id = id;
    }

    /**
     * Gets the packet's ID.
     *
     * @return The packet's ID
     */
    public byte getId() {
        return this.id;
    }

    /**
     * Serializes this packet into the given buffer.
     *
     * @param buffer     The buffer to serialize this packet into
     * @param protocolID Protocol for which we request the serialization
     */
    public abstract void serialize( PacketBuffer buffer, int protocolID );

    /**
     * Deserializes this packet from the given buffer.
     *
     * @param buffer     The buffer to deserialize this packet from
     * @param protocolID Protocol for which we request deserialization
     */
    public abstract void deserialize( PacketBuffer buffer, int protocolID );

    /**
     * Returns the ordering channel to send the packet on.
     *
     * @return The ordering channel of the packet
     */
    public int orderingChannel() {
        return 0;
    }

    /**
     * Read a item stack from the packet buffer
     *
     * @param buffer from the packet
     * @return read item stack
     */
    static ItemStack readItemStack( PacketBuffer buffer ) {
        int id = buffer.readSignedVarInt();
        if ( id == 0 ) {
            return ItemAir.create( 0 );
        }

        int temp = buffer.readSignedVarInt();
        byte amount = (byte) ( temp & 0xFF );
        short data = (short) ( temp >> 8 );

        NBTTagCompound nbt = null;
        short extraLen = buffer.readLShort();
        if ( extraLen > 0 ) {
            ByteArrayInputStream bin = new ByteArrayInputStream( buffer.getBuffer(), buffer.getPosition(), extraLen );
            try {
                NBTReader nbtReader = new NBTReader( bin, ByteOrder.LITTLE_ENDIAN );
                // nbtReader.setUseVarint( true );
                nbt = nbtReader.parse();
            } catch ( IOException e ) {
                e.printStackTrace();
            }

            buffer.skip( extraLen );
        }

        // They implemented additional data for item stacks aside from nbt
        int countPlacedOn = buffer.readSignedVarInt();
        for ( int i = 0; i < countPlacedOn; i++ ) {
            buffer.readString();    // TODO: Implement proper support once we know the string values
        }

        int countCanBreak = buffer.readSignedVarInt();
        for ( int i = 0; i < countCanBreak; i++ ) {
            buffer.readString();    // TODO: Implement proper support once we know the string values
        }

        return ( (GoMintServer) GoMint.instance() ).getItems().create( id, data, amount, nbt );
    }

    /**
     * Write a item stack to the packet buffer
     *
     * @param itemStack which should be written
     * @param buffer    which should be used to write to
     */
    public static void writeItemStack( ItemStack itemStack, PacketBuffer buffer ) {
        if ( itemStack == null || itemStack instanceof ItemAir ) {
            buffer.writeSignedVarInt( 0 );
            return;
        }

        buffer.writeSignedVarInt( ( (io.gomint.server.inventory.item.ItemStack) itemStack ).getMaterial() );
        buffer.writeSignedVarInt( ( itemStack.getData() << 8 ) + ( itemStack.getAmount() & 0xff ) );

        NBTTagCompound compound = itemStack.getNbtData();
        if ( compound == null ) {
            buffer.writeLShort( (short) 0 );
        } else {
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                compound.writeTo( byteArrayOutputStream, false, ByteOrder.LITTLE_ENDIAN );
                buffer.writeLShort( (short) byteArrayOutputStream.size() );
                buffer.writeBytes( byteArrayOutputStream.toByteArray() );
            } catch ( IOException e ) {
                buffer.writeLShort( (short) 0 );
            }
        }

        // canPlace and canBreak
        buffer.writeSignedVarInt( 0 );
        buffer.writeSignedVarInt( 0 );
    }

    /**
     * Write a array of item stacks to the buffer
     *
     * @param itemStacks which should be written to the buffer
     * @param buffer     which should be written to
     */
    void writeItemStacks( ItemStack[] itemStacks, PacketBuffer buffer ) {
        if ( itemStacks == null || itemStacks.length == 0 ) {
            buffer.writeUnsignedVarInt( 0 );
            return;
        }

        buffer.writeUnsignedVarInt( itemStacks.length );

        for ( ItemStack itemStack : itemStacks ) {
            writeItemStack( itemStack, buffer );
        }
    }

    /**
     * Read in a variable amount of itemstacks
     *
     * @param buffer The buffer to read from
     * @return a list of item stacks
     */
    ItemStack[] readItemStacks( PacketBuffer buffer ) {
        int count = buffer.readUnsignedVarInt();
        ItemStack[] itemStacks = new ItemStack[count];

        for ( int i = 0; i < count; i++ ) {
            itemStacks[i] = readItemStack( buffer );
        }

        return itemStacks;
    }

    /**
     * Write a array of integers to the buffer
     *
     * @param integers which should be written to the buffer
     * @param buffer   which should be written to
     */
    void writeIntList( int[] integers, PacketBuffer buffer ) {
        if ( integers == null || integers.length == 0 ) {
            buffer.writeUnsignedVarInt( 0 );
            return;
        }

        buffer.writeUnsignedVarInt( integers.length );

        for ( Integer integer : integers ) {
            buffer.writeSignedVarInt( integer );
        }
    }

    public void writeGamerules( Map<Gamerule, Object> gamerules, PacketBuffer buffer ) {
        if ( gamerules == null ) {
            buffer.writeUnsignedVarInt( 0 );
            return;
        }

        buffer.writeUnsignedVarInt( gamerules.size() );
        gamerules.forEach( ( gamerule, value ) -> {
            buffer.writeString( gamerule.getNbtName().toLowerCase() );

            if ( gamerule.getValueType() == Boolean.class ) {
                buffer.writeByte( (byte) 1 );
                buffer.writeBoolean( (Boolean) value );
            } else if ( gamerule.getValueType() == Integer.class ) {
                buffer.writeByte( (byte) 2 );
                buffer.writeUnsignedVarInt( (Integer) value );
            } else if ( gamerule.getValueType() == Float.class ) {
                buffer.writeByte( (byte) 3 );
                buffer.writeLFloat( (Float) value );
            }
        } );
    }

    public BlockPosition readBlockPosition( PacketBuffer buffer ) {
        return new BlockPosition( buffer.readSignedVarInt(), buffer.readUnsignedVarInt(), buffer.readSignedVarInt() );
    }

    public void writeBlockPosition( BlockPosition position, PacketBuffer buffer ) {
        buffer.writeSignedVarInt( position.getX() );
        buffer.writeUnsignedVarInt( position.getY() );
        buffer.writeSignedVarInt( position.getZ() );
    }

    public void writeEntityLinks( List<EntityLink> links, PacketBuffer buffer ) {
        if ( links == null ) {
            buffer.writeUnsignedVarInt( 0 );
        } else {
            buffer.writeUnsignedVarInt( links.size() );
            for ( EntityLink link : links ) {
                buffer.writeUnsignedVarLong( link.getFrom() );
                buffer.writeUnsignedVarLong( link.getTo() );
                buffer.writeByte( link.getUnknown1() );
                buffer.writeByte( link.getUnknown2() );
            }
        }
    }

    void writeVector( Vector vector, PacketBuffer buffer ) {
        buffer.writeLFloat( vector.getX() );
        buffer.writeLFloat( vector.getY() );
        buffer.writeLFloat( vector.getZ() );
    }

    Vector readVector( PacketBuffer buffer ) {
        return new Vector( buffer.readLFloat(), buffer.readLFloat(), buffer.readLFloat() );
    }

    CommandOrigin readCommandOrigin( PacketBuffer buffer ) {
        // Seems to be 0, request uuid, 0, type (0 for player, 3 for server)
        return new CommandOrigin( buffer.readByte(), buffer.readUUID(), buffer.readByte(), buffer.readByte() );
    }

    void writeCommandOrigin( CommandOrigin commandOrigin, PacketBuffer buffer ) {
        buffer.writeByte( commandOrigin.getUnknown1() );
        buffer.writeUUID( commandOrigin.getUuid() );
        buffer.writeByte( commandOrigin.getUnknown2() );
        buffer.writeByte( commandOrigin.getType() );
    }

    BlockFace readBlockFace( PacketBuffer buffer ) {
        int value = buffer.readSignedVarInt();
        return Things.convertFromDataToBlockFace( (byte) value );
    }

}
