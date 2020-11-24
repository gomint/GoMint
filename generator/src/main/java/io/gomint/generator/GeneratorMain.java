/*
 * Copyright (c) 2020 Gomint team
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
import java.util.stream.Collectors;

public class GeneratorMain {

    public static void main(String[] args) throws IOException, AllocationLimitReachedException {
        // Read in the assets file
        Path assetFile = Paths.get("gomint-server", "src", "main", "resources", "assets.dat");
        System.out.println(assetFile.toAbsolutePath());

        byte[] data = Files.readAllBytes(assetFile);
        ByteBuf buf = PooledByteBufAllocator.DEFAULT.directBuffer();
        buf.writeBytes(data);

        // Parse the NBT from it
        NBTTagCompound root = NBTTagCompound.readFrom(buf, ByteOrder.BIG_ENDIAN);

        // Generate switches
        BlockStateSwitchGenerator stateSwitchGenerator = new BlockStateSwitchGenerator();
        stateSwitchGenerator.generateSwitchNode((List<NBTTagCompound>) ((List) root.getList("blockPalette", false)));

        //
        Path tileEntities = Paths.get("gomint-server", "src", "main", "java", "io", "gomint", "server", "entity", "tileentity");

        // Now we iterate over block implementations to find out whats missing
        List<String> contents = Files
            .walk(tileEntities)
            .filter(p -> !p.toFile().isDirectory())
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

        StringBuilder registry = new StringBuilder("package io.gomint.server.entity.tileentity;\n\nimport io.gomint.server.inventory.item.Items;\nimport io.gomint.server.plugin.EventCaller;\nimport io.gomint.server.world.block.Block;\nimport io.gomint.server.registry.StringRegistry;\n\npublic class Registry {\n\n  public static void register(StringRegistry<TileEntity> registry) {\n");

        for (String content : contents) {
            if (content.contains("@RegisterInfo")) {
                String className = "DJKSLAHDKLSAJDSAJ";
                registry.append("    registry.registerAdditionalConstructor(");
                for (String line : content.split("\n")) {
                    if (line.startsWith("@RegisterInfo(")) {
                        registry.append("\"")
                            .append(line.replace("@RegisterInfo(sId = \"", "").replace("\")", "").replace("\n", "").replace("\r", ""))
                            .append("\", ");
                        continue;
                    }
                    if (line.startsWith("public class ")) {
                        className = line.substring("public class ".length());
                        className = className.substring(0, className.indexOf(" "));
                        continue;
                    }
                    line = line.replace("\n", "").replace("\r", "");
                    if (line.contains("public " + className + "(") && line.endsWith("{")) {
                        String constructor = line.replace("public", "").replace("{", "").trim();
                        String[] parameters = constructor.substring(constructor.indexOf("(") + 1, constructor.indexOf(")")).trim().split(",");

                        registry.append(parameters.length).append(", in -> new ").append(className).append("( ");

                        int index = 0;
                        for (String parameter : parameters) {
                            String[] typeAndName = parameter.trim().split(" ");
                            registry.append("(").append(typeAndName[0]).append(") in[").append(index).append("], ");
                            index++;
                        }

                        registry.delete(registry.length() - 2, registry.length()).append(" ));\n");
                    }
                }
            }
        }

        Files.writeString(Paths.get("gomint-server", "src", "main", "java", "io", "gomint", "server", "entity", "tileentity", "Registry.java"), registry.append("  }\n\n}").toString());
        System.out.println();
    }

}
