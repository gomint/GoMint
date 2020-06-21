/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world;

import io.gomint.server.util.BlockIdentifier;
import io.gomint.server.util.collection.FreezableSortedMap;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.taglib.NBTWriter;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2IntMap;
import it.unimi.dsi.fastutil.longs.Long2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author geNAZt
 * @version 1.0
 */
public class BlockRuntimeIDs {

    private static final Logger LOGGER = LoggerFactory.getLogger(BlockRuntimeIDs.class);

    private static Int2ObjectMap<BlockIdentifier> RUNTIME_TO_BLOCK = new Int2ObjectOpenHashMap<>();

    //
    private static AtomicInteger RUNTIME_ID = new AtomicInteger(0);

    //
    private static Object2ObjectMap<String, List<BlockIdentifier>> BLOCK_STATE_IDENTIFIER = new Object2ObjectLinkedOpenHashMap<>();

    // Cached packet streams
    private static ByteBuf START_GAME_BUFFER;

    public static void init(List<BlockIdentifier> blockPalette) throws IOException {
        List<Object> compounds = new ArrayList<>();

        ByteBuf data = PooledByteBufAllocator.DEFAULT.directBuffer();
        NBTWriter writer = new NBTWriter(data, ByteOrder.LITTLE_ENDIAN);
        writer.setUseVarint(true);

        for (BlockIdentifier identifier : blockPalette) {
            int runtime = RUNTIME_ID.getAndIncrement();

            RUNTIME_TO_BLOCK.put(runtime, identifier);
            identifier.setRuntimeId(runtime);

            // Store identifier by block id
            List<BlockIdentifier> identifiers = BLOCK_STATE_IDENTIFIER.computeIfAbsent(identifier.getBlockId(), s -> new ArrayList<>());
            identifiers.add(identifier);

            NBTTagCompound compound = new NBTTagCompound("");
            NBTTagCompound block = new NBTTagCompound("block");

            block.addValue("name", identifier.getBlockId());
            block.addValue("states", identifier.getNbt());
            compound.addValue("block", block);
            compounds.add(compound);
        }

        writer.write(compounds);

        START_GAME_BUFFER = data;
    }

    /**
     * Get the cached view for the start game packet
     *
     * @return correct cached view
     */
    public static ByteBuf getPacketCache() {
        return START_GAME_BUFFER;
    }

    public static BlockIdentifier toBlockIdentifier(int runtimeId) {
        return RUNTIME_TO_BLOCK.get(runtimeId);
    }

    public static BlockIdentifier toBlockIdentifier(String blockId, FreezableSortedMap<String, Object> states) {
        List<BlockIdentifier> identifiers = BLOCK_STATE_IDENTIFIER.get(blockId);
        if (identifiers == null) {
            LOGGER.error("Unknown block id: {}", blockId);
            return null;
        }

        // Check if states match
        if (states == null) {
            return identifiers.get(0);
        }

        for (BlockIdentifier identifier : identifiers) {
            if (identifier.getStates().equals(states)) {
                return identifier;
            }
        }

        LOGGER.error("Unknown states for block id: {}", blockId);
        return null;
    }

    /**
     * Get the new block identifier which holds the changes state map
     *
     * @param oldState        from which we get the diff
     * @param changingKey     key which changes
     * @param newValue        value of this key
     * @return new block identifier or null if no state has been found
     */
    public static BlockIdentifier change(BlockIdentifier oldState, String changingKey, Object newValue) {
        // Get the list of all identifiers
        List<BlockIdentifier> identifiers = BLOCK_STATE_IDENTIFIER.get(oldState.getBlockId());
        if (identifiers == null) {
            LOGGER.error("Unknown block id: {}", oldState.getBlockId());
            return null;
        }

        // Now we need to get the one which has all keys and values correct
        for (BlockIdentifier identifier : identifiers) {
            if (useableIdentifier(identifier, oldState, changingKey, newValue)) {
                return identifier;
            }
        }

        LOGGER.warn("No usable block state found for: {} -> {} -> {}", oldState, changingKey, newValue);
        return null;
    }

    private static boolean useableIdentifier(BlockIdentifier identifier, BlockIdentifier oldState, String changingKey, Object newValue) {
        // The keys should be the same
        for (Map.Entry<String, Object> entry : identifier.getStates().entrySet()) {
            Object compareValue = oldState.getStates().get(entry.getKey());
            if (entry.getKey().equals(changingKey)) {
                compareValue = newValue;
            }

            if (!compareValue.equals(entry.getValue())) {
                return false;
            }
        }

        return true;
    }

}
