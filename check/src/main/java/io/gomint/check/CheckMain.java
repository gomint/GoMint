/*
 * Copyright (c) 2018 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.check;

import io.gomint.taglib.AllocationLimitReachedException;
import io.gomint.taglib.NBTTagCompound;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;

import java.io.IOException;
import java.nio.ByteOrder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class CheckMain {

    public static void main(String[] args) throws IOException, AllocationLimitReachedException {
        // Read in the assets file
        Path assetFile = Paths.get("gomint-server", "src", "main", "resources", "assets.dat");
        byte[] data = Files.readAllBytes(assetFile);
        ByteBuf buf = PooledByteBufAllocator.DEFAULT.directBuffer();
        buf.writeBytes(data);

        // Parse the NBT from it
        NBTTagCompound root = NBTTagCompound.readFrom(buf, ByteOrder.BIG_ENDIAN);

        Map<String, Set<String>> knownBlockKeys = new HashMap<>();

        List<NBTTagCompound> blockIdentifiers = (List<NBTTagCompound>) ((List) root.getList("blockPalette", false));
        for (NBTTagCompound compound : blockIdentifiers) {
            String block = compound.getCompound("block", false).getString("name", "minecraft:air");

            knownBlockKeys.computeIfAbsent(block, s -> {
                NBTTagCompound states = compound.getCompound("block", false).getCompound("states", false);
                if (states != null) {
                    Set<String> keys = new HashSet<>();
                    for (Map.Entry<String, Object> entry : states.entrySet()) {
                        keys.add(entry.getKey());
                    }
                    return keys;
                }

                return new HashSet<>();
            });
        }

        // Now we iterate over block implementations to find out whats missing
        List<String> contents = Files
            .walk(Paths.get("gomint-server", "src", "main", "java", "io", "gomint", "server", "world", "block"))
            .filter(p -> !p.endsWith(".java"))
            .map(p -> {
                try {
                    return Files.readString(p);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return "ERROR";
            })
            .collect(Collectors.toList());

        AtomicInteger missingBlocks = new AtomicInteger(0);
        AtomicInteger missingStates = new AtomicInteger(0);

        knownBlockKeys.forEach((block, stateKeys) -> {
            for (String content : contents) {
                if (content.contains(block)) {
                    for (String stateKey : stateKeys) {
                        if (!content.contains(stateKey)) {
                            // Check for extends (we only have one level nesting here)
                            if (content.contains("extends")) {
                                int extendsIndex = content.indexOf("extends");
                                String parentClass = content.substring(extendsIndex + 7, content.indexOf(" ", extendsIndex + 9));

                                boolean found = false;
                                for (String s : contents) {
                                    if (s.contains(parentClass) && s.contains(stateKey)) {
                                        found = true;
                                        break;
                                    }
                                }

                                if (!found) {
                                    System.out.println("[X]  Missing state " + stateKey + " in block " + block);
                                    missingStates.incrementAndGet();
                                }
                            } else {
                                System.out.println("[X]  Missing state " + stateKey + " in block " + block);
                                missingStates.incrementAndGet();
                            }
                        }
                    }

                    return;
                }
            }

            System.out.println("[X] Missing block: " + block);
            missingBlocks.incrementAndGet();

            for (String stateKey : stateKeys) {
                System.out.println("[X]  Missing state " + stateKey + " in block " + block);
                missingStates.incrementAndGet();
            }
        });

        System.out.println();
        System.out.println("Missing " + missingBlocks.get() + " blocks and " + missingStates.get() + " states");
    }

}
