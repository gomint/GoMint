/*
 * Copyright (c) 2018 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.generator;

import io.gomint.taglib.AllocationLimitReachedException;
import io.gomint.taglib.NBTTagCompound;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;

import java.io.IOException;
import java.nio.ByteOrder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class GeneratorMain {

    public static void main(String[] args) throws IOException, AllocationLimitReachedException {
        // Read in the assets file
        Path assetFile = Paths.get("gomint-server", "src", "main", "resources", "assets.dat");
        byte[] data = Files.readAllBytes(assetFile);
        ByteBuf buf = PooledByteBufAllocator.DEFAULT.directBuffer();
        buf.writeBytes(data);

        // Parse the NBT from it
        NBTTagCompound root = NBTTagCompound.readFrom(buf, ByteOrder.BIG_ENDIAN);

        // Generate switches
        BlockStateSwitchGenerator stateSwitchGenerator = new BlockStateSwitchGenerator();
        stateSwitchGenerator.generateSwitchNode((List<NBTTagCompound>) ((List) root.getList("blockPalette", false)));
    }

}
