/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.check;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ItemCombiner {

    private static final Pattern ID_PATTERN = Pattern.compile("id = ([0-9]+)");
    private static final Pattern SID_PATTERN = Pattern.compile("sId = ([a-z_:\"]+)");

    private static final Map<String, String> REPLACER = new LinkedHashMap<>(){{
       put("silver", "light_gray");
       put("_glazed_terracotta", "");
    }};

    private static class ReadItem {
        private Path path;
        private String content;

        public ReadItem(Path path, String content) {
            this.path = path;
            this.content = content;
        }
    }

    public static void main(String[] args) throws IOException {
        String wantedToFind = "_glazed";

        List<ReadItem> itemFiles = Files
            .walk(Path.of("gomint-server", "src", "main", "java", "io", "gomint", "server", "inventory", "item"))
            .filter(path -> !path.toFile().isDirectory())
            .filter(path -> !path.endsWith(".java"))
            .map(path -> {
                try {
                    String content = Files.readString(path, StandardCharsets.UTF_8);
                    return new ReadItem(path, content);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return null;
            })
            .filter(Objects::nonNull)
            .filter(readItem -> readItem.content.contains("@Register") && readItem.content.contains(wantedToFind))
            .map(readItem -> {
                for (String line : readItem.content.split("\n")) {
                    if (line.startsWith("@Register")) {
                        return new ReadItem(readItem.path, line);
                    }
                }

                return null;
            })
            .collect(Collectors.toList());

        itemFiles.sort(new Comparator<ReadItem>() {
            @Override
            public int compare(ReadItem o1, ReadItem o2) {
                int id1 = 99999999;
                int id2 = 99999999;

                Matcher matcher = ID_PATTERN.matcher(o1.content);
                if ( matcher.find() ) {
                    id1 = Integer.parseInt(matcher.group(1));
                }

                matcher = ID_PATTERN.matcher(o2.content);
                if ( matcher.find() ) {
                    id2 = Integer.parseInt(matcher.group(1));
                }

                return Integer.compare(id1, id2);
            }
        });

        List<String> output = new ArrayList<>();
        for (ReadItem itemFile : itemFiles) {
            Matcher sIdMatcher = SID_PATTERN.matcher(itemFile.content);
            String replacement = "";
            String sId = "";

            if (sIdMatcher.find()) {
                sId = sIdMatcher.group(1);
                String blockPart = sId.split(":")[1].replace("\"", "");

                for (Map.Entry<String, String> entry : REPLACER.entrySet()) {
                    blockPart = blockPart.replace(entry.getKey(), entry.getValue());
                }

                replacement = blockPart.toUpperCase();
                output.add("    public static final String " + blockPart.toUpperCase() + " = " + sId + ";");
            }

            System.out.println(itemFile.content.replace(sId, replacement));
        }

        System.out.println();

        for (String line : output) {
            System.out.println(line);
        }
    }


}
