/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
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
import io.gomint.server.player.PlayerSkin;
import io.gomint.server.util.Things;
import io.gomint.taglib.AllocationLimitReachedException;
import io.gomint.taglib.NBTReader;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.taglib.NBTWriter;
import io.gomint.world.Gamerule;
import io.gomint.world.block.data.Facing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.ByteOrder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author BlackyPaw
 * @version 1.0
 */
public abstract class Packet {

    private static final Logger LOGGER = LoggerFactory.getLogger(Packet.class);
    private static final float BYTE_ROTATION_DIVIDOR = 360f / 256f;

    /**
     * Internal MC:PE id of this packet
     */
    protected final int id;

    /**
     * Construct a new packet
     *
     * @param id of the packet
     */
    protected Packet(int id) {
        this.id = id;
    }

    /**
     * Read a item stack from the packet buffer
     *
     * @param buffer from the packet
     * @return read item stack
     */
    public static ItemStack readItemStack(PacketBuffer buffer) {
        int id = buffer.readSignedVarInt();
        if (id == 0) {
            return ItemAir.create(0);
        }

        int temp = buffer.readSignedVarInt();
        byte amount = (byte) (temp & 0xFF);
        short data = (short) (temp >> 8);

        NBTTagCompound nbt = null;
        short extraLen = buffer.readLShort();
        if (extraLen == -1) {
            // New system uses a byte as amount of nbt tags
            byte version = buffer.readByte();

            try {
                NBTReader nbtReader = new NBTReader(buffer.getBuffer(), ByteOrder.LITTLE_ENDIAN);
                nbtReader.setUseVarint(true);
                // There is no alloc limit needed here, you can't write so much shit in 32kb, so thats ok
                nbt = nbtReader.parse();
            } catch (IOException | AllocationLimitReachedException e) {
                LOGGER.error("Could not read item stack because of NBT", e);
                return ItemAir.create(0);
            }
        }

        // They implemented additional data for item stacks aside from nbt
        int countPlacedOn = buffer.readSignedVarInt();
        for (int i = 0; i < countPlacedOn; i++) {
            buffer.readString();    // TODO: Implement proper support once we know the string values
        }

        int countCanBreak = buffer.readSignedVarInt();
        for (int i = 0; i < countCanBreak; i++) {
            buffer.readString();    // TODO: Implement proper support once we know the string values
        }

        io.gomint.server.inventory.item.ItemStack itemStack = ((GoMintServer) GoMint.instance()).getItems().create(id, data, amount, nbt);

        // New item data system?
        itemStack.readAdditionalData(buffer);

        return itemStack;
    }

    public static ItemStack readItemStackWithID(PacketBuffer buffer) {
        int id = buffer.readSignedVarInt();
        io.gomint.server.inventory.item.ItemStack serverItemStack = (io.gomint.server.inventory.item.ItemStack) readItemStack(buffer);
        if (serverItemStack != null) {
            serverItemStack.setStackId(id);
        }

        return serverItemStack;
    }

    public static void writeItemStackWithID(ItemStack itemStack, PacketBuffer buffer) {
        io.gomint.server.inventory.item.ItemStack serverItemStack = (io.gomint.server.inventory.item.ItemStack) itemStack;

        buffer.writeSignedVarInt(serverItemStack.getStackId());
        writeItemStack(itemStack, buffer);
    }

    /**
     * Write a item stack to the packet buffer
     *
     * @param itemStack which should be written
     * @param buffer    which should be used to write to
     */
    public static void writeItemStack(ItemStack itemStack, PacketBuffer buffer) {
        if (itemStack instanceof ItemAir) {
            buffer.writeSignedVarInt(0);
            return;
        }

        io.gomint.server.inventory.item.ItemStack serverItemStack = (io.gomint.server.inventory.item.ItemStack) itemStack;

        buffer.writeSignedVarInt(serverItemStack.getRuntimeID());
        buffer.writeSignedVarInt(((serverItemStack.getData() & 0x7fff) << 8) + (itemStack.getAmount() & 0xff));

        NBTTagCompound compound = serverItemStack.getNbtData();
        if (compound == null) {
            buffer.writeLShort((short) 0);
        } else {
            try {
                // Vanilla currently only writes one nbt tag (this is hardcoded)
                buffer.writeLShort((short) 0xFFFF);
                buffer.writeByte((byte) 1);

                // NBT Tag
                NBTWriter nbtWriter = new NBTWriter(buffer.getBuffer(), ByteOrder.LITTLE_ENDIAN);
                nbtWriter.setUseVarint(true);
                nbtWriter.write(compound);
            } catch (IOException e) {
                LOGGER.warn("Could not write NBT Tag", e);
            }
        }

        // canPlace and canBreak
        buffer.writeSignedVarInt(0);
        buffer.writeSignedVarInt(0);

        ((io.gomint.server.inventory.item.ItemStack) itemStack).writeAdditionalData(buffer);
    }

    public static void writeRecipeInput(ItemStack ingredient, PacketBuffer buffer) {
        if (ingredient == null) {
            buffer.writeSignedVarInt(0);
            return;
        }

        io.gomint.server.inventory.item.ItemStack impl = ((io.gomint.server.inventory.item.ItemStack) ingredient);
        int material = impl.getRuntimeID();
        buffer.writeSignedVarInt(material);
        buffer.writeSignedVarInt(impl.getData());
        buffer.writeSignedVarInt(ingredient.getAmount());
    }

    /**
     * Gets the packet's ID.
     *
     * @return The packet's ID
     */
    public int getId() {
        return this.id;
    }

    /**
     * Serializes this packet into the given buffer.
     *
     * @param buffer     The buffer to serialize this packet into
     * @param protocolID Protocol for which we request the serialization
     */
    public abstract void serialize(PacketBuffer buffer, int protocolID) throws Exception;

    /**
     * Deserializes this packet from the given buffer.
     *
     * @param buffer     The buffer to deserialize this packet from
     * @param protocolID Protocol for which we request deserialization
     */
    public abstract void deserialize(PacketBuffer buffer, int protocolID) throws Exception;

    /**
     * Returns the ordering channel to send the packet on.
     *
     * @return The ordering channel of the packet
     */
    public int orderingChannel() {
        return 0;
    }

    /**
     * Write a array of item stacks to the buffer
     *
     * @param itemStacks which should be written to the buffer
     * @param buffer     which should be written to
     */
    void writeItemStacks(ItemStack[] itemStacks, PacketBuffer buffer) {
        if (itemStacks == null || itemStacks.length == 0) {
            buffer.writeUnsignedVarInt(0);
            return;
        }

        buffer.writeUnsignedVarInt(itemStacks.length);

        for (ItemStack itemStack : itemStacks) {
            writeItemStack(itemStack, buffer);
        }
    }

    /**
     * Read in a variable amount of itemstacks
     *
     * @param buffer The buffer to read from
     * @return a list of item stacks
     */
    public static ItemStack[] readItemStacks(PacketBuffer buffer) {
        int count = buffer.readUnsignedVarInt();
        ItemStack[] itemStacks = new ItemStack[count];

        for (int i = 0; i < count; i++) {
            itemStacks[i] = readItemStack(buffer);
        }

        return itemStacks;
    }

    /**
     * Write a array of item stacks to the buffer
     *
     * @param itemStacks which should be written to the buffer
     * @param buffer     which should be written to
     */
    void writeItemStacksWithIDs(ItemStack[] itemStacks, PacketBuffer buffer) {
        if (itemStacks == null || itemStacks.length == 0) {
            buffer.writeUnsignedVarInt(0);
            return;
        }

        buffer.writeUnsignedVarInt(itemStacks.length);

        for (ItemStack itemStack : itemStacks) {
            writeItemStackWithID(itemStack, buffer);
        }
    }

    /**
     * Read in a variable amount of itemstacks
     *
     * @param buffer The buffer to read from
     * @return a list of item stacks
     */
    public static ItemStack[] readItemStacksWithIDs(PacketBuffer buffer) {
        int count = buffer.readUnsignedVarInt();
        ItemStack[] itemStacks = new ItemStack[count];

        for (int i = 0; i < count; i++) {
            itemStacks[i] = readItemStackWithID(buffer);
        }

        return itemStacks;
    }

    /**
     * Write a array of integers to the buffer
     *
     * @param integers which should be written to the buffer
     * @param buffer   which should be written to
     */
    void writeIntList(int[] integers, PacketBuffer buffer) {
        if (integers == null || integers.length == 0) {
            buffer.writeUnsignedVarInt(0);
            return;
        }

        buffer.writeUnsignedVarInt(integers.length);

        for (Integer integer : integers) {
            buffer.writeSignedVarInt(integer);
        }
    }

    public void writeGamerules(Map<Gamerule, Object> gamerules, PacketBuffer buffer) {
        if (gamerules == null) {
            buffer.writeUnsignedVarInt(0);
            return;
        }

        buffer.writeUnsignedVarInt(gamerules.size());
        gamerules.forEach((gamerule, value) -> {
            buffer.writeString(gamerule.getNbtName().toLowerCase());

            if (gamerule.getValueType() == Boolean.class) {
                buffer.writeByte((byte) 1);
                buffer.writeBoolean((Boolean) value);
            } else if (gamerule.getValueType() == Integer.class) {
                buffer.writeByte((byte) 2);
                buffer.writeUnsignedVarInt((Integer) value);
            } else if (gamerule.getValueType() == Float.class) {
                buffer.writeByte((byte) 3);
                buffer.writeLFloat((Float) value);
            }
        });
    }

    public Map<Gamerule, Object> readGamerules(PacketBuffer buffer) {
        int amount = buffer.readUnsignedVarInt();
        if (amount == 0) {
            return null;
        }

        Map<Gamerule, Object> gamerules = new HashMap<>();
        for (int i = 0; i < amount; i++) {
            String name = buffer.readString();
            byte type = buffer.readByte();

            Object val = null;
            switch (type) {
                case 1:
                    val = buffer.readBoolean();
                    break;
                case 2:
                    val = buffer.readUnsignedVarInt();
                    break;
                case 3:
                    val = buffer.readLFloat();
                    break;
            }
        }

        return gamerules;
    }

    void writeSerializedSkin(PlayerSkin skin, PacketBuffer buffer) {
        buffer.writeString(skin.getId());
        buffer.writeString(skin.getResourcePatch());
        writeSkinImageData(buffer, skin.getImageWidth(), skin.getImageHeight(), skin.getData());

        if (skin.getAnimations() != null) {
            buffer.writeLInt(skin.getAnimations().size());

            for (PlayerSkin.AnimationFrame animationObj : skin.getAnimations()) {
                writeSkinImageData(buffer, animationObj.getWidth(), animationObj.getHeight(), animationObj.getData());
                buffer.writeLInt(animationObj.getType());
                buffer.writeLFloat(animationObj.getFrames());
                buffer.writeLInt(animationObj.getExpression());
            }
        } else {
            buffer.writeLInt(0);
        }

        writeSkinImageData(buffer, skin.getCapeImageWidth(), skin.getCapeImageHeight(), skin.getCapeData());
        buffer.writeString(skin.getGeometry());
        buffer.writeString(skin.getAnimationData());
        buffer.writeBoolean(skin.isPremium());
        buffer.writeBoolean(skin.isPersona());
        buffer.writeBoolean(skin.isPersonaCapeOnClassic());
        buffer.writeString(skin.getCapeId());
        buffer.writeString(skin.getFullId());
        buffer.writeString(skin.getArmSize());
        buffer.writeString(skin.getColour());

        if (skin.getPersonaPieces() != null) {
            buffer.writeLInt(skin.getPersonaPieces().size());

            for (PlayerSkin.PersonaPiece personaPieceObj : skin.getPersonaPieces()) {
                buffer.writeString(personaPieceObj.getPieceId());
                buffer.writeString(personaPieceObj.getPieceType());
                buffer.writeString(personaPieceObj.getPackId());
                buffer.writeBoolean(personaPieceObj.isDefaultValue());
                buffer.writeString(personaPieceObj.getProductId());
            }
        } else {
            buffer.writeLInt(0);
        }

        if (skin.getPieceTintColours() != null) {
            buffer.writeLInt(skin.getPieceTintColours().size());

            for (PlayerSkin.PieceTintColor pieceTintColorObj : skin.getPieceTintColours()) {
                buffer.writeString(pieceTintColorObj.getPieceType());

                if (pieceTintColorObj.getColors() != null) {
                    buffer.writeLInt(pieceTintColorObj.getColors().size());
                    for (String color : pieceTintColorObj.getColors()) {
                        buffer.writeString(color);
                    }
                } else {
                    buffer.writeUnsignedVarInt(0);
                }
            }
        } else {
            buffer.writeLInt(0);
        }
    }

    private void writeSkinImageData(PacketBuffer buffer, int imageWidth, int imageHeight, byte[] data) {
        buffer.writeLInt(imageWidth);
        buffer.writeLInt(imageHeight);
        buffer.writeUnsignedVarInt(data.length);
        buffer.writeBytes(data);
    }

    public BlockPosition readBlockPosition(PacketBuffer buffer) {
        return new BlockPosition(buffer.readSignedVarInt(), buffer.readUnsignedVarInt(), buffer.readSignedVarInt());
    }

    public BlockPosition readSignedBlockPosition(PacketBuffer buffer) {
        return new BlockPosition(buffer.readSignedVarInt(), buffer.readSignedVarInt(), buffer.readSignedVarInt());
    }

    public void writeBlockPosition(BlockPosition position, PacketBuffer buffer) {
        buffer.writeSignedVarInt(position.getX());
        buffer.writeUnsignedVarInt(position.getY());
        buffer.writeSignedVarInt(position.getZ());
    }

    public void writeSignedBlockPosition(BlockPosition position, PacketBuffer buffer) {
        buffer.writeSignedVarInt(position.getX());
        buffer.writeSignedVarInt(position.getY());
        buffer.writeSignedVarInt(position.getZ());
    }

    public void writeEntityLinks(List<EntityLink> links, PacketBuffer buffer) {
        if (links == null) {
            buffer.writeUnsignedVarInt(0);
        } else {
            buffer.writeUnsignedVarInt(links.size());
            for (EntityLink link : links) {
                buffer.writeUnsignedVarLong(link.getFrom());
                buffer.writeUnsignedVarLong(link.getTo());
                buffer.writeByte(link.getUnknown1());
                buffer.writeByte(link.getUnknown2());
            }
        }
    }

    void writeVector(Vector vector, PacketBuffer buffer) {
        buffer.writeLFloat(vector.getX());
        buffer.writeLFloat(vector.getY());
        buffer.writeLFloat(vector.getZ());
    }

    Vector readVector(PacketBuffer buffer) {
        return new Vector(buffer.readLFloat(), buffer.readLFloat(), buffer.readLFloat());
    }

    CommandOrigin readCommandOrigin(PacketBuffer buffer) {
        // Seems to be 0, request uuid, 0, type (0 for player, 3 for server)
        return new CommandOrigin(buffer.readByte(), buffer.readUUID(), buffer.readByte(), buffer.readByte());
    }

    void writeCommandOrigin(CommandOrigin commandOrigin, PacketBuffer buffer) {
        buffer.writeByte(commandOrigin.getUnknown1());
        buffer.writeUUID(commandOrigin.getUuid());
        buffer.writeByte(commandOrigin.getUnknown2());
        buffer.writeByte(commandOrigin.getType());
    }

    Facing readBlockFace(PacketBuffer buffer) {
        int value = buffer.readSignedVarInt();
        return Things.convertFromDataToBlockFace((byte) value);
    }

    void writeByteRotation(float rotation, PacketBuffer buffer) {
        buffer.writeByte((byte) (rotation / BYTE_ROTATION_DIVIDOR));
    }

    float readByteRotation(PacketBuffer buffer) {
        return buffer.readByte() * BYTE_ROTATION_DIVIDOR;
    }

    public void serializeHeader(PacketBuffer buffer) {
        buffer.writeUnsignedVarInt(this.id);
    }

    @Override
    public String toString() {
        return "Packet{" +
            "id=" + id +
            '}';
    }

}
