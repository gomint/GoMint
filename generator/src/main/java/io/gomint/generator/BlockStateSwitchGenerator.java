/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.generator;

import io.gomint.taglib.NBTTagCompound;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.WordUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BlockStateSwitchGenerator {

    public void generateSwitchNode(List<NBTTagCompound> blockPaletteCompounds) {
        StringBuilder javaCode = new StringBuilder("package io.gomint.server.registry;\n\n")
            .append("public class SwitchBlockStateMapper")
            .append("{\n\n");

        Map<String, Integer> runtimeIds = new HashMap<>();
        Map<String, SwitchNode> states = new HashMap<>();
        Map<String, Integer> knownBlocks = new LinkedHashMap<>();

        int blockNumber = 0;
        int runtimeId = 0;

        for (NBTTagCompound compound : blockPaletteCompounds) {
            String block = compound.getString("name", "minecraft:air");

            Integer id = knownBlocks.get(block);
            if (id == null) {
                id = blockNumber++;
                knownBlocks.put(block, id);
            }

            runtimeIds.put(block, runtimeId);

            BlockIdentifier identifier = new BlockIdentifier(
                block,
                runtimeId++,
                compound.getCompound("states", false)
            );

            if (identifier.getStates() != null) {
                SwitchNode start = states.computeIfAbsent(block, s -> new SwitchNode());
                for (Map.Entry<String, Object> objectEntry : identifier.getStates().entrySet()) {
                    if (objectEntry.getKey().equals("deprecated")) {
                        continue;
                    }

                    start.setKey(objectEntry.getKey());
                    start.setType(objectEntry.getValue().getClass());
                    start = start.getOrGenerate(objectEntry.getValue());
                }

                start.setValue(identifier.getRuntimeId());
            }
        }

        int i = 0;
        for (Map.Entry<String, Integer> entry : knownBlocks.entrySet()) {
            String path = entry.getKey().split(":")[0];
            String blockId = entry.getKey().split(":")[1];
            String className = WordUtils.capitalize(blockId, '_').replaceAll("_", "");

            Map<String, String> replace = new HashMap<>();
            replace.put("NAME", className);
            replace.put("BLOCK_ID", String.valueOf(knownBlocks.get(entry.getKey())));
            replace.put("NAMESPACE", path);
            replace.put("DEFAULT", String.valueOf(runtimeIds.get(entry.getKey())));

            SwitchNode switchNode = states.get(entry.getKey());
            if (switchNode != null) {
                Set<String> methods = new HashSet<>();
                replace.put("CONTENT", switchNode.print("    ".length(), null, methods));
                replace.put("FUNCTIONS", StringUtils.join(methods, '\n'));
            } else {
                replace.put("CONTENT", "        return " + runtimeIds.get(entry.getKey()) + ";\n");
                replace.put("FUNCTIONS", "");
            }

            javaCode.append("    private static final ")
                .append("io.gomint.server.world.block.mapper.").append(path).append(".").append(className)
                .append(" O").append(i)
                .append(" = new ")
                .append("io.gomint.server.world.block.mapper.").append(path).append(".").append(className)
                .append("();\n");

            generate("block_mapper.txt", replace,
                "gomint-server/src/main/java/io/gomint/server/world/block/mapper/" + path + "/" + className + ".java");

            i++;
        }

        javaCode.append("\n    public ").append("io.gomint.server.world.block.mapper.BlockStateMapper").append(" get(int block) {\n")
            .append("        switch (block) {\n");

        for (int i1 = 0; i1 < i; i1++) {
            javaCode
                .append("        case ").append(i1).append(":\n")
                .append("            return O").append(i1).append(";\n");
        }


        javaCode.append("        }\n").append("        return null;\n").append("    }\n").append("}\n");

        String className = "io/gomint/server/registry/SwitchBlockStateMapper";

        try {
            writeIfNeeded(Paths.get("gomint-server/src/main/java/" + className + ".java"), javaCode.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeIfNeeded(Path path, String data) throws IOException {
        if (path.toFile().exists()) {
            if (!Files.readString(path).equals(data)) {
                Files.writeString(path, data);
            }
        } else {
            Files.writeString(path, data);
        }
    }

    private void generate(String templateFile, Map<String, String> data, String... outputFiles) {
        try (InputStream inputStream = new FileInputStream("generator/src/main/resources/" + templateFile)) {
            String template = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
            for (Map.Entry<String, String> entry : data.entrySet()) {
                template = template.replaceAll("%" + entry.getKey() + "%", entry.getValue());
            }

            for (String outputFile : outputFiles) {
                File file = new File(outputFile);
                file.getParentFile().mkdirs();

                writeIfNeeded(Paths.get(outputFile), template);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
